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

import com.trashboxbobylev.tormentpixeldungeon.sprites.ItemSpriteSheet;
import com.trashboxbobylev.tormentpixeldungeon.messages.Messages;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.Weapon;
import com.trashboxbobylev.tormentpixeldungeon.items.wands.Wand;
import com.trashboxbobylev.tormentpixeldungeon.Dungeon;
import com.trashboxbobylev.tormentpixeldungeon.mechanics.Ballistica;
import com.trashboxbobylev.tormentpixeldungeon.actors.hero.Hero;
import com.trashboxbobylev.tormentpixeldungeon.scenes.GameScene;
import com.trashboxbobylev.tormentpixeldungeon.actors.Char;
import com.trashboxbobylev.tormentpixeldungeon.actors.Actor;
import com.trashboxbobylev.tormentpixeldungeon.items.Arrow;
import com.trashboxbobylev.tormentpixeldungeon.sprites.MissileSprite;
import com.trashboxbobylev.tormentpixeldungeon.actors.hero.HeroSubClass;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.Bless;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.EarthImbue;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.FireImbue;
import com.trashboxbobylev.tormentpixeldungeon.sprites.CharSprite;
import com.trashboxbobylev.tormentpixeldungeon.utils.GLog;
import com.trashboxbobylev.tormentpixeldungeon.Assets;
import com.trashboxbobylev.tormentpixeldungeon.items.Heap;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.Invisibility;
import com.trashboxbobylev.tormentpixeldungeon.scenes.CellSelector;
import com.trashboxbobylev.tormentpixeldungeon.ui.QuickSlotButton;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;
import com.watabou.utils.Callback;

import java.util.ArrayList;

public class Bow extends MeleeWeapon {

    private static final String AC_SHOT = "SHOT";

    {
       defaultAction = AC_SHOT;
       usesTargeting = true;
       
       DLY = 1.5f; //0.66 speed
       ACC = 0.4f*rangedACC();

       tier = 0;
    }

    public int minRanged(){
        return minRanged(level());
    }

    public int minRanged(int lvl){
        return tier + lvl;
    }

    public float rangedACC(){
        return 1f;
    }

    public int maxRanged(){
        return maxRanged(level());
    }

    public int maxRanged(int lvl){
        return 5*(tier+1) +//base
                     lvl*(tier+1); //scaling
    }

    @Override
    public int min(int lvl){
        return (tier-1) + lvl;
    }

    @Override
    public int max(int lvl){
        return Math.round(maxRanged(lvl)*0.4f);
    }

    @Override
	public String info() {

		String info = desc();

		if (levelKnown) {
			info += "\n\n" + Messages.get(Bow.class, "stats_known", tier, imbue.damageFactor(minRanged()), imbue.damageFactor(maxRanged()), imbue.damageFactor(min()), imbue.damageFactor(max()),STRReq());
			if (STRReq() > Dungeon.hero.STR()) {
				info += " " + Messages.get(Weapon.class, "too_heavy");
			} else if (Dungeon.hero.STR() > STRReq()){
				info += " " + Messages.get(Weapon.class, "excess_str", Dungeon.hero.STR() - STRReq());
			}
		} else {
			info += "\n\n" + Messages.get(Bow.class, "stats_unknown", tier, minRanged(0), maxRanged(0), min(0), max(0), STRReq(0));
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

    protected int collisionProperties = Ballistica.PROJECTILE;
	
	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		if (isEquipped(hero)) actions.add( AC_SHOT );
		return actions;
	}
	
	@Override
	public void execute( Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals( AC_SHOT )) {
			
			curUser = hero;
			curItem = this;
			GameScene.selectCell( zapper );
			
		}
	}

    protected static CellSelector.Listener zapper = new  CellSelector.Listener() {
		
		@Override
		public void onSelect( Integer target ) {
			
			if (target != null && Dungeon.arrows > 0) {
				
				//FIXME this safety check shouldn't be necessary
				//it would be better to eliminate the curItem static variable.
				final Bow curBow;
				if (curItem instanceof Bow) {
					curBow = (Bow) Bow.curItem;
				} else {
					return;
                }

                Dungeon.arrows--;
                final Arrow arrow = new Arrow();

				final Ballistica shot = new Ballistica( curUser.pos, target, curBow.collisionProperties);
				final int cell = shot.collisionPos;
				
				if (target == curUser.pos || cell == curUser.pos) {
					GLog.i( Messages.get(Wand.class, "self_target") );
					return;
				}

				curUser.sprite.zap(cell);

				//attempts to target the cell aimed at if something is there, otherwise targets the collision pos.
				if (Actor.findChar(target) != null)
					QuickSlotButton.target(Actor.findChar(target));
				else
					QuickSlotButton.target(Actor.findChar(cell));
					
			    curUser.busy();
                
                			((MissileSprite) curUser.sprite.parent.recycle(MissileSprite.class)).
					reset(curUser.sprite,
							cell,
							arrow,
							new Callback() {
						@Override
						public void call() {
							Char ch = Actor.findChar(shot.collisionPos);
		if (ch != null){
			curBow.shoot(ch);
            Invisibility.dispel();
		}
        else {Heap heap = Dungeon.level.drop( arrow, cell );
		if (!heap.isEmpty()) heap.sprite.drop( cell);
        }
							curUser.spendAndNext(curBow.DLY);
						}
					});
				
			}
		}
		
		@Override
		public String prompt() {
			return Messages.get(Wand.class, "prompt");
		}
	};

    public boolean shoot( Char enemy){
if (enemy == null || !enemy.isAlive()) return false;
		
		boolean visibleFight = Dungeon.level.heroFOV[curUser.pos] || Dungeon.level.heroFOV[enemy.pos];
		
		if (this.hit( enemy)) {
			
			// FIXME
			int dr = ((Hero)curUser).subClass ==
				HeroSubClass.SNIPER ? 0 : enemy.drRoll();
			
			int dmg = Random.NormalIntRange(minRanged(), maxRanged());
			int effectiveDamage = Math.max( dmg - dr, 0 );
			
			effectiveDamage = curUser.attackProc( enemy, effectiveDamage );
			effectiveDamage = enemy.defenseProc( curUser, effectiveDamage );

			if (visibleFight) {
				Sample.INSTANCE.play( Assets.SND_HIT, 1, 1, Random.Float( 0.8f, 1.25f ) );
			}

			// If the enemy is already dead, interrupt the attack.
			// This matters as defence procs can sometimes inflict self-damage, such as armor glyphs.
			if (!enemy.isAlive()){
				return true;
			}

			enemy.damage( effectiveDamage, this );

			if (curUser.buff(FireImbue.class) != null)
				curUser.buff(FireImbue.class).proc(enemy);
			if (curUser.buff(EarthImbue.class) != null)
				curUser.buff(EarthImbue.class).proc(enemy);

			enemy.sprite.bloodBurstA( curUser.sprite.center(), effectiveDamage );
			enemy.sprite.flash();

			if (!enemy.isAlive() && visibleFight) {
					GLog.i( Messages.capitalize(Messages.get(Char.class, "defeat", enemy.name)) );
			}
			
			return true;
			
		} else {
			
			if (visibleFight) {
				String defense = enemy.defenseVerb();
				enemy.sprite.showStatus( CharSprite.NEUTRAL, defense );
				
				Sample.INSTANCE.play(Assets.SND_MISS);
			}
			
			return false;
			
		}
	}
	
	public boolean hit( Char defender ) {
		float acuRoll = Random.Float( curUser.attackSkill( defender )*2.5f );
		float defRoll = Random.Float( defender.defenseSkill( curUser ) );
		if (curUser.buff(Bless.class) != null) acuRoll *= 2f;
		if (defender.buff(Bless.class) != null) defRoll *= 2f;
		return acuRoll >= defRoll;
	}
}
