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

package com.trashboxbobylev.tormentpixeldungeon.items.weapon;

import com.trashboxbobylev.tormentpixeldungeon.Badges;
import com.trashboxbobylev.tormentpixeldungeon.ShatteredPixelDungeon;
import com.trashboxbobylev.tormentpixeldungeon.Dungeon;
import com.trashboxbobylev.tormentpixeldungeon.actors.Char;
import com.trashboxbobylev.tormentpixeldungeon.actors.hero.Hero;
import com.trashboxbobylev.tormentpixeldungeon.actors.hero.HeroClass;
import com.trashboxbobylev.tormentpixeldungeon.items.Item;
import com.trashboxbobylev.tormentpixeldungeon.items.KindOfWeapon;
import com.trashboxbobylev.tormentpixeldungeon.items.rings.RingOfFuror;
import com.trashboxbobylev.tormentpixeldungeon.items.rings.RingOfSharpshooting;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.curses.Annoying;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.curses.Displacing;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.curses.Exhausting;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.curses.Fragile;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.curses.Sacrificial;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.curses.Wayward;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.enchantments.Blazing;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.enchantments.Chilling;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.enchantments.Dazzling;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.enchantments.Eldritch;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.enchantments.Impulse;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.enchantments.Grim;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.enchantments.Lucky;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.enchantments.Projecting;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.enchantments.Shocking;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.enchantments.Stunning;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.enchantments.Unstable;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.enchantments.Vampiric;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.enchantments.Venomous;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.enchantments.Vorpal;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.trashboxbobylev.tormentpixeldungeon.messages.Messages;
import com.trashboxbobylev.tormentpixeldungeon.sprites.ItemSprite;
import com.trashboxbobylev.tormentpixeldungeon.utils.GLog;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

abstract public class Weapon extends KindOfWeapon {

	private static final int HITS_TO_KNOW    = 20;

	private static final String TXT_TO_STRING		= "%s :%d";

	public float    ACC = 1f;	// Accuracy modifier
	public float	DLY	= 1f;	// Speed modifier
	public int      RCH = 1;    // Reach modifier (only applies to melee hits)

	public enum Imbue {
		NONE	(1.0f, 1.00f),
		LIGHT	(0.7f, 0.67f),
		HEAVY	(1.5f, 1.67f);

		private float damageFactor;
		private float delayFactor;

		Imbue(float dmg, float dly){
			damageFactor = dmg;
			delayFactor = dly;
		}

		public int damageFactor(int dmg){
			return Math.round(dmg * damageFactor);
		}

		public float delayFactor(float dly){
			return dly * delayFactor;
		}
	}
	public Imbue imbue = Imbue.NONE;

	private int hitsToKnow = HITS_TO_KNOW;
	
	public Enchantment enchantment;
	
	@Override
	public int proc( Char attacker, Char defender, int damage ) {
		
		if (enchantment != null) {
			damage = enchantment.proc( this, attacker, defender, damage );
		}
		
		if (!levelKnown) {
			if (--hitsToKnow <= 0) {
				identify();
				GLog.i( Messages.get(Weapon.class, "identify") );
				Badges.validateItemLevelAquired( this );
			}
		}

		return damage;
	}

	private static final String UNFAMILIRIARITY	= "unfamiliarity";
	private static final String ENCHANTMENT		= "enchantment";
	private static final String IMBUE			= "imbue";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( UNFAMILIRIARITY, hitsToKnow );
		bundle.put( ENCHANTMENT, enchantment );
		bundle.put( IMBUE, imbue );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		if ((hitsToKnow = bundle.getInt( UNFAMILIRIARITY )) == 0) {
			hitsToKnow = HITS_TO_KNOW;
		}
		enchantment = (Enchantment)bundle.get( ENCHANTMENT );
		imbue = bundle.getEnum( IMBUE, Imbue.class );
	}
	
	@Override
	public float accuracyFactor( Char owner ) {
		
		int encumbrance = 0;
		
		if( owner instanceof Hero ){
			encumbrance = STRReq() - ((Hero)owner).STR();
		}

        int encrumModifier = Dungeon.isChallenged() ? 7 : 3;
		if (hasEnchant(Wayward.class))
			encumbrance = Math.max(encrumModifier, encumbrance+encrumModifier);

		float ACC = this.ACC;
		
		if (this instanceof MissileWeapon) {
			ACC *= RingOfSharpshooting.accuracyMultiplier( owner );
		}

		return encumbrance > 0 ? (float)(ACC / Math.pow( 1.5, encumbrance )) : ACC;
	}
	
	@Override
	public float speedFactor( Char owner ) {

		int encumbrance = 0;
		if (owner instanceof Hero) {
			encumbrance = STRReq() - ((Hero)owner).STR();
			if (this instanceof MissileWeapon && ((Hero)owner).heroClass == HeroClass.HUNTRESS) {
				encumbrance -= 2;
			}
		}

		float DLY = imbue.delayFactor(this.DLY);

		DLY = RingOfFuror.modifyAttackDelay(DLY, owner);

		return (encumbrance > 0 ? (float)(DLY * Math.pow( 1.2, encumbrance )) : DLY);
	}

	@Override
	public int reachFactor(Char owner) {
		return hasEnchant(Projecting.class) ? Math.round(RCH*1f + 1f + level()/5f) : RCH;
	}

	@Override
	public int damageRoll( Char owner ) {
		
		int damage = super.damageRoll( owner );
		
		if (owner instanceof Hero &&
				(this instanceof MeleeWeapon
				|| (this instanceof MissileWeapon && ((Hero)owner).heroClass == HeroClass.HUNTRESS))) {
			int exStr = ((Hero)owner).STR() - STRReq();
			if (exStr > 0) {
				damage += Random.IntRange( 0, exStr );
			}
		}
		
		return imbue.damageFactor(damage);
	}

	public int STRReq(){
		return STRReq(level());
	}

	public abstract int STRReq(int lvl);
	
	@Override
	public Item upgrade() {
		return upgrade(false);
	}
	
	public Item upgrade(boolean enchant ) {

		if (enchant && (enchantment == null || enchantment.curse())){
			enchant( Enchantment.random() );
		} else if (!enchant && Random.Float() > Math.pow(0.9, level())){
			enchant(null);
		}
		
		cursed = false;
		
		return super.upgrade();
	}
	
	@Override
	public String name() {
		return enchantment != null && (cursedKnown || !enchantment.curse()) ? enchantment.name( super.name() ) : super.name();
	}
	
	@Override
	public Item random() {
		float roll = Random.Float();
		if (roll < 0.25f){
			//25% chance to be level 0 and cursed
			enchant(Enchantment.randomCurse());
			cursed = true;
			return this;
		} else if (roll < 0.75f){
			//40% chance to be level 0
		} else if (roll < 0.90f){
			//15% chance to be +1
			upgrade(1);
		} else if (roll < 0.95f){
			//5% chance to be +2
			upgrade(2);
		} else if (roll < 0.96f){
            //1% chance to be +3
            upgrade(3);
        } else if (roll < 0.963f){
            //0.3% chance to be +4
            upgrade(4);
        } else if (roll < 0.9638f){
            //0.08% chance to be +5
            upgrade(5);
        }

		//if not cursed, 10% chance to be enchanted (7.5% overall)
		if (Random.Int(10) == 0)
			enchant();

		return this;
	}
	
	public Weapon enchant( Enchantment ench ) {
		enchantment = ench;
		return this;
	}

	public Weapon enchant() {

		Class<? extends Enchantment> oldEnchantment = enchantment != null ? enchantment.getClass() : null;
		Enchantment ench = Enchantment.random();
		while (ench.getClass() == oldEnchantment) {
			ench = Enchantment.random();
		}

		return enchant( ench );
	}

	public boolean hasEnchant(Class<?extends Enchantment> type) {
		return enchantment != null && enchantment.getClass() == type;
	}

	public boolean hasGoodEnchant(){
		return enchantment != null && !enchantment.curse();
	}

	public boolean hasCurseEnchant(){		return enchantment != null && enchantment.curse();
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return enchantment != null && (cursedKnown || !enchantment.curse()) ? enchantment.glowing() : null;
	}

	public static abstract class Enchantment implements Bundlable {

		private static final Class<?>[] enchants = new Class<?>[]{
			Blazing.class, Venomous.class, Vorpal.class, Shocking.class,
			Chilling.class, Eldritch.class, Lucky.class, Projecting.class, Unstable.class, Dazzling.class, Impulse.class, 
			Grim.class, Stunning.class, Vampiric.class,};
		private static final float[] chances= new float[]{
			10, 10, 10, 10,
			5, 5, 5, 5, 5, 5, 5,
			2, 2, 2 };

		private static final Class<?>[] curses = new Class<?>[]{
				Annoying.class, Displacing.class, Exhausting.class, Fragile.class, Sacrificial.class, Wayward.class
		};
			
		public abstract int proc( Weapon weapon, Char attacker, Char defender, int damage );

		public String name() {
			if (!curse())
				return name( Messages.get(this, "enchant"));
			else
				return name( Messages.get(Item.class, "curse"));
		}

		public String name( String weaponName ) {
			return Messages.get(this, "name", weaponName);
		}

		public String desc() {
			return Messages.get(this, "desc");
		}

		public boolean curse() {
			return false;
		}

		@Override
		public void restoreFromBundle( Bundle bundle ) {
		}

		@Override
		public void storeInBundle( Bundle bundle ) {
		}
		
		public abstract ItemSprite.Glowing glowing();
		
		@SuppressWarnings("unchecked")
		public static Enchantment random() {
			try {
				return ((Class<Enchantment>)enchants[ Random.chances( chances ) ]).newInstance();
			} catch (Exception e) {
				ShatteredPixelDungeon.reportException(e);
				return null;
			}
		}

		@SuppressWarnings("unchecked")
		public static Enchantment randomCurse(){
			try {
				return ((Class<Enchantment>)Random.oneOf(curses)).newInstance();
			} catch (Exception e) {
				ShatteredPixelDungeon.reportException(e);
				return null;
			}
		}
		
	}
}
