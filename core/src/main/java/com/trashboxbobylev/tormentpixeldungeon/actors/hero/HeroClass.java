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

package com.trashboxbobylev.tormentpixeldungeon.actors.hero;

import com.trashboxbobylev.tormentpixeldungeon.Assets;
import com.trashboxbobylev.tormentpixeldungeon.Badges;
import com.trashboxbobylev.tormentpixeldungeon.Dungeon;
import com.trashboxbobylev.tormentpixeldungeon.items.BrokenSeal;
import com.trashboxbobylev.tormentpixeldungeon.items.ArmorKit;
import com.trashboxbobylev.tormentpixeldungeon.items.armor.ClothArmor;
import com.trashboxbobylev.tormentpixeldungeon.items.artifacts.CloakOfShadows;
import com.trashboxbobylev.tormentpixeldungeon.items.food.Food;
import com.trashboxbobylev.tormentpixeldungeon.items.potions.PotionOfHealing;
import com.trashboxbobylev.tormentpixeldungeon.items.potions.PotionOfMindVision;
import com.trashboxbobylev.tormentpixeldungeon.items.potions.PotionOfParalyticGas;
import com.trashboxbobylev.tormentpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.trashboxbobylev.tormentpixeldungeon.items.scrolls.ScrollOfMagicalInfusion;
import com.trashboxbobylev.tormentpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.trashboxbobylev.tormentpixeldungeon.items.wands.WandOfPlasma;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Dagger;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Knuckles;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.MagesStaff;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.WornShortsword;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.OldBow;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.NinjaSword;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.DoubleDagger;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Halberd;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.FightingKnifes;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.JjanGo;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.missiles.Boomerang;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.missiles.Dart;
import com.trashboxbobylev.tormentpixeldungeon.messages.Messages;
import com.watabou.utils.Bundle;

public enum HeroClass {

	WARRIOR( "warrior" ),
	MAGE( "mage" ),
	ROGUE( "rogue" ),
	HUNTRESS( "huntress" ),
    ARCHER( "archer" );

	private String title;

	HeroClass( String title ) {
		this.title = title;
	}

	public void initHero( Hero hero ) {

		hero.heroClass = this;

		initCommon( hero );

		switch (this) {
			case WARRIOR:
				initWarrior( hero );
				break;

			case MAGE:
				initMage( hero );
				break;

			case ROGUE:
				initRogue( hero );
				break;

			case HUNTRESS:
				initHuntress( hero );
				break;

            case ARCHER:
                initArcher( hero );
                break;
		}
		
	}

	private static void initCommon( Hero hero ) {
			(hero.belongings.armor = new ClothArmor()).identify();
        new ArmorKit().collect();
			new Food().identify().collect();
	}

	public Badges.Badge masteryBadge() {
		switch (this) {
			case WARRIOR:
				return Badges.Badge.MASTERY_WARRIOR;
			case MAGE:
				return Badges.Badge.MASTERY_MAGE;
			case ROGUE:
				return Badges.Badge.MASTERY_ROGUE;
			case HUNTRESS:
				return Badges.Badge.MASTERY_HUNTRESS;
             case ARCHER:
				return Badges.Badge.MASTERY_ARCHER;
		}
		return null;
	}

	private static void initWarrior( Hero hero ) {
		(hero.belongings.weapon = new WornShortsword()).identify();
		Dart darts = new Dart( 8 );
		darts.identify().collect();

        for (int count = 0; count < 500; count++) {
            ScrollOfMagicalInfusion scroll = new ScrollOfMagicalInfusion();
            scroll.collect();
        }

		if ( Badges.isUnlocked(Badges.Badge.TUTORIAL_WARRIOR) ){
			if (true)
				hero.belongings.armor.affixSeal(new BrokenSeal());
			Dungeon.quickslot.setSlot(0, darts);
		} else {
			if (true) {
				BrokenSeal seal = new BrokenSeal();
				seal.collect();
				Dungeon.quickslot.setSlot(0, seal);
			}
			Dungeon.quickslot.setSlot(1, darts);
		}

		new PotionOfHealing().identify();
	}

	private static void initMage( Hero hero ) {
		MagesStaff staff;

		if ( Badges.isUnlocked(Badges.Badge.TUTORIAL_MAGE) ){
			staff = new MagesStaff(new WandOfPlasma());
		} else {
			staff = new MagesStaff();
			new WandOfPlasma().identify().collect();
		}

		(hero.belongings.weapon = staff).identify();
		hero.belongings.weapon.activate(hero);

		Dungeon.quickslot.setSlot(0, staff);

		new ScrollOfUpgrade().identify();
	}

	private static void initRogue( Hero hero ) {
		(hero.belongings.weapon = new Dagger()).identify();

		CloakOfShadows cloak = new CloakOfShadows();
		(hero.belongings.misc1 = cloak).identify();
		hero.belongings.misc1.activate( hero );

		Dart darts = new Dart( 8 );
		darts.identify().collect();

		Dungeon.quickslot.setSlot(0, cloak);
		Dungeon.quickslot.setSlot(1, darts);

		new ScrollOfMagicMapping().identify();
	}

	private static void initHuntress( Hero hero ) {

		(hero.belongings.weapon = new Knuckles()).identify();
		Boomerang boomerang = new Boomerang();
		boomerang.identify().collect();

		Dungeon.quickslot.setSlot(0, boomerang);

		new PotionOfMindVision().identify();
	}

    private static void initArcher( Hero hero ) {
        
		
		OldBow oldbow = new OldBow();
	    hero.belongings.weapon = oldbow.identify();
        Dungeon.arrows = 50;

		Dungeon.quickslot.setSlot(0, oldbow);

		new PotionOfParalyticGas().identify();
	}
	
	public String title() {
		return Messages.get(HeroClass.class, title);
	}
	
	public String spritesheet() {
		
		switch (this) {
		case WARRIOR:
			return Assets.WARRIOR;
		case MAGE:
			return Assets.MAGE;
		case ROGUE:
			return Assets.ROGUE;
		case HUNTRESS:
			return Assets.HUNTRESS;
        case ARCHER:
            return Assets.ARCHER;
		}
		
		return null;
	}
	
	public String[] perks() {
		
		switch (this) {
		case WARRIOR:
			return new String[]{
					Messages.get(HeroClass.class, "warrior_perk1"),
					Messages.get(HeroClass.class, "warrior_perk2"),
					Messages.get(HeroClass.class, "warrior_perk3"),
					Messages.get(HeroClass.class, "warrior_perk4"),
					Messages.get(HeroClass.class, "warrior_perk5"),
			};
		case MAGE:
			return new String[]{
					Messages.get(HeroClass.class, "mage_perk1"),
					Messages.get(HeroClass.class, "mage_perk2"),
					Messages.get(HeroClass.class, "mage_perk3"),
					Messages.get(HeroClass.class, "mage_perk4"),
					Messages.get(HeroClass.class, "mage_perk5"),
			};
		case ROGUE:
			return new String[]{
					Messages.get(HeroClass.class, "rogue_perk1"),
					Messages.get(HeroClass.class, "rogue_perk2"),
					Messages.get(HeroClass.class, "rogue_perk3"),
					Messages.get(HeroClass.class, "rogue_perk4"),
					Messages.get(HeroClass.class, "rogue_perk5"),
			};
		case HUNTRESS:
			return new String[]{
					Messages.get(HeroClass.class, "huntress_perk1"),
					Messages.get(HeroClass.class, "huntress_perk2"),
					Messages.get(HeroClass.class, "huntress_perk3"),
					Messages.get(HeroClass.class, "huntress_perk4"),
					Messages.get(HeroClass.class, "huntress_perk5"),
			};
            case ARCHER:
			return new String[]{
					Messages.get(HeroClass.class, "archer_perk1"),
					Messages.get(HeroClass.class, "archer_perk2"),
					Messages.get(HeroClass.class, "archer_perk3"),
					Messages.get(HeroClass.class, "archer_perk4"),
			};
		}
		
		return null;
	}

	private static final String CLASS	= "class";
	
	public void storeInBundle( Bundle bundle ) {
		bundle.put( CLASS, toString() );
	}
	
	public static HeroClass restoreInBundle( Bundle bundle ) {
		String value = bundle.getString( CLASS );
		return value.length() > 0 ? valueOf( value ) : ROGUE;
	}
}
