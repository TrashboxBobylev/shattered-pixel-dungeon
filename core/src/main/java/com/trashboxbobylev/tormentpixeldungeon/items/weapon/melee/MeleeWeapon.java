/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2017 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee;

import com.trashboxbobylev.tormentpixeldungeon.Assets;
import com.trashboxbobylev.tormentpixeldungeon.Dungeon;
import com.trashboxbobylev.tormentpixeldungeon.Badges;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.Invisibility;
import com.trashboxbobylev.tormentpixeldungeon.actors.hero.Hero;
import com.trashboxbobylev.tormentpixeldungeon.windows.WndBag;
import com.trashboxbobylev.tormentpixeldungeon.scenes.GameScene;
import com.trashboxbobylev.tormentpixeldungeon.items.Item;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.Weapon;
import com.trashboxbobylev.tormentpixeldungeon.messages.Messages;

import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

public class MeleeWeapon extends Weapon {
	
	public int tier;
    public boolean reforged;

    public static final String AC_UPGRADE = "UPGRADE";

	@Override
	public int min(int lvl) {
		return  tier +  //base
				lvl;    //level scaling
	}

	@Override
	public int max(int lvl) {
		return  5*(tier+1) +    //base
				lvl*(tier+1);   //level scaling
	}

    public static final int[] highTierStrReq = {3, 4, 5, 6, 7, 8, 9};

	public int STRReq(int lvl){
		lvl = Math.max(0, lvl);
		//strength req decreases at +1,+3,+6,+10,etc.
		return (8 + Math.min(tier * 2, 12) + (tier > 6 ? getHighStrReq(tier - 6) : 0)) - (int)(Math.sqrt(8 * lvl + 1) - 1)/2 + (imbue == Weapon.Imbue.HEAVY ? 1 : (imbue == Weapon.Imbue.LIGHT ? -1 : 0));
	}

    public static int getHighStrReq(int highTier){
        int result = 0;
        for (int i = 0; i < highTier; i++) result += highTierStrReq[i-1];
        return result;
    }

	@Override
	public ArrayList<String> actions(Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		if (tier > 6 && reforged == false) actions.add( AC_UPGRADE );
		return actions;
	}

    @Override
	public void execute( Hero hero, String action ){
        super.execute(hero, action);
        if (action == AC_UPGRADE){
        curItem = this;
 GameScene.selectItem( itemSelector, WndBag.Mode.WEAPON, Messages.get(MeleeWeapon.class, "reforge"));
       }
    }

	@Override
	public String info() {

		String info = desc();

		if (levelKnown) {
			info += "\n\n" + Messages.get(MeleeWeapon.class, "stats_known", tier, imbue.damageFactor(min()), imbue.damageFactor(max()), STRReq());
			if (STRReq() > Dungeon.hero.STR()) {
				info += " " + Messages.get(Weapon.class, "too_heavy");
			} else if (Dungeon.hero.STR() > STRReq()){
				info += " " + Messages.get(Weapon.class, "excess_str", Dungeon.hero.STR() - STRReq());
			}
		} else {
			info += "\n\n" + Messages.get(MeleeWeapon.class, "stats_unknown", tier, min(0), max(0), STRReq(0));
			if (STRReq(0) > Dungeon.hero.STR()) {
				info += " " + Messages.get(MeleeWeapon.class, "probably_too_heavy");
			}
		}

		String stats_desc = Messages.get(this, "stats_desc");
		if (!stats_desc.equals("")) info+= "\n\n" + stats_desc;

		switch (imbue) {
			case LIGHT:
				info += "\n\n" + Messages.get(Weapon.class, "lighter");
				break;
			case HEAVY:
				info += "\n\n" + Messages.get(Weapon.class, "heavier");
				break;
			case NONE:
		}

		if (enchantment != null && (cursedKnown || !enchantment.curse())){
			info += "\n\n" + Messages.get(Weapon.class, "enchanted", enchantment.name());
			info += " " + Messages.get(enchantment, "desc");
		}

		if (cursed && isEquipped( Dungeon.hero )) {
			info += "\n\n" + Messages.get(Weapon.class, "cursed_worn");
		} else if (cursedKnown && cursed) {
			info += "\n\n" + Messages.get(Weapon.class, "cursed");
		}
		
		return info;
	}
	
	@Override
	public int price() {
		int price = 20 * tier;
		if (hasGoodEnchant()) {
			price *= 1.5;
		}
		if (cursedKnown && (cursed || hasCurseEnchant())) {
			price /= 2;
		}
		if (levelKnown && level() > 0) {
			price *= (level() + 1);
		}
		if (price < 1) {
			price = 1;
		}
		return price;
	}

    protected static WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect( Item item ) {
			
			if (item != null) {
			Weapon w = (Weapon) item;
			boolean wasCursed = w.cursed;
            int level = w.level();

			for (int i = 0; i < w.level(); i++) curItem.upgrade();
            if (wasCursed) curItem.cursed = true;

            item.detach(Dungeon.hero.belongings.backpack);
		
		Badges.validateItemLevelAquired( curItem );
				Sample.INSTANCE.play( Assets.SND_EVOKE );
				Invisibility.dispel();
				
                ((MeleeWeapon)curItem).reforged = true;
			} 
		}
	};

}
