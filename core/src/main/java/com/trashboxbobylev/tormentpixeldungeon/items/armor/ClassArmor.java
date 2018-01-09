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

package com.trashboxbobylev.tormentpixeldungeon.items.armor;

import com.trashboxbobylev.tormentpixeldungeon.actors.Char;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.Buff;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.Invisibility;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.Recharging;
import com.trashboxbobylev.tormentpixeldungeon.actors.hero.Hero;
import com.trashboxbobylev.tormentpixeldungeon.items.BrokenSeal;
import com.trashboxbobylev.tormentpixeldungeon.messages.Messages;
import com.trashboxbobylev.tormentpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

abstract public class ClassArmor extends Armor {

	private static final String AC_SPECIAL = "SPECIAL";
    public int charges;
    public float patrialCharge;
    public int maxCharges;
	
	{
		levelKnown = true;
		cursedKnown = true;
		defaultAction = AC_SPECIAL;

        charges = 0;
        maxCharges = 100;

		bones = false;
	}

	private int armorTier;
	
	public ClassArmor() {
		super( 6, 6 );
	}
	
	public static ClassArmor upgrade ( Hero owner, Armor armor ) {
		
		ClassArmor classArmor = null;
		
		switch (owner.heroClass) {
		case WARRIOR:
			classArmor = new WarriorArmor();
			BrokenSeal seal = armor.checkSeal();
			if (seal != null) {
				classArmor.affixSeal(seal);
			}
			break;
		case ROGUE:
			classArmor = new RogueArmor();
			break;
		case MAGE:
			classArmor = new MageArmor();
			break;
		case HUNTRESS:
			classArmor = new HuntressArmor();
			break;
        case ARCHER:
            classArmor = new ArcherArmor();
            break;
		}
		
		classArmor.level(armor.level());
		classArmor.armorTier = armor.tier;
		classArmor.inscribe( armor.glyph );
		classArmor.identify();
		
		return classArmor;
	}

    @Override
    public String status() {
        return Messages.format("%d%%", charges);
    }

    @Override
    public void activate(Char ch){
        super.activate(ch);
        Charger charger = new Charger();
        charger.attachTo(ch);
    }

	private static final String ARMOR_TIER	= "armortier";
    private static final String CHARGE = "charge";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( ARMOR_TIER, armorTier );
        bundle.put( CHARGE, charges);
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		
		armorTier = bundle.getInt( ARMOR_TIER );
        charges = bundle.getInt( CHARGE );
	}
	
	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		if (charges >= maxCharges && isEquipped( hero )) {
			actions.add( AC_SPECIAL );
		}
		return actions;
	}
	
	@Override
	public void execute( Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals(AC_SPECIAL)) {
			
			if (charges < 100) {
				GLog.w( Messages.get(this, "low_hp") );
			} else if (!isEquipped( hero )) {
				GLog.w( Messages.get(this, "not_equipped") );
			} else {
				curUser = hero;
				Invisibility.dispel();
				doSpecial();
                charges = 0;
			}
			
		}
	}

	abstract public void doSpecial();

	@Override
	public int STRReq(int lvl) {
		lvl = Math.max(0, lvl);
		float effectiveTier = armorTier;
		if (glyph != null) effectiveTier += glyph.tierSTRAdjust();
		effectiveTier = Math.max(0, effectiveTier);

		//strength req decreases at +1,+3,+6,+10,etc.
		return (8 + Math.round(effectiveTier * 2)) - (int)(Math.sqrt(8 * lvl + 1) - 1)/2;
	}

	@Override
	public int DR(int lvl){
		int effectiveTier = armorTier;
		if (glyph != null) effectiveTier += glyph.tierDRAdjust();
		effectiveTier = Math.max(0, effectiveTier);

		return effectiveTier * (3 + lvl);
	}
	
    public class Charger extends Buff {

        @Override
        public boolean attachTo(Char target) {
            super.attachTo(target);

            return true;
        }

        @Override
        public boolean act() {
             if (charges < maxCharges) {
                 patrialCharge += (float) 0.5f * level();
                 charges++;

                 if (patrialCharge >= 1) {
                     charges+=patrialCharge;
                     patrialCharge = 0;
                 }
      
                 for (Recharging bonus: target.buffs(Recharging.class)) {
                     charges++;
                 }

                 if (charges >= maxCharges) {
                     GLog.p(Messages.get(ClassArmor.class, "full_charged"));
                 }
           }
           updateQuickslot();
           spend(TICK);
           return true;
         }
   }

	@Override
	public boolean isIdentified() {
		return true;
	}
	
	@Override
	public int price() {
		return 0;
	}

}
