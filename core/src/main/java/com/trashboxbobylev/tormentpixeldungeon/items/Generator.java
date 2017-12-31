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

package com.trashboxbobylev.tormentpixeldungeon.items;

import com.trashboxbobylev.tormentpixeldungeon.Dungeon;
import com.trashboxbobylev.tormentpixeldungeon.ShatteredPixelDungeon;
import com.trashboxbobylev.tormentpixeldungeon.items.armor.Armor;
import com.trashboxbobylev.tormentpixeldungeon.items.armor.CompositeArmor;
import com.trashboxbobylev.tormentpixeldungeon.items.armor.ClothArmor;
import com.trashboxbobylev.tormentpixeldungeon.items.armor.LeatherArmor;
import com.trashboxbobylev.tormentpixeldungeon.items.armor.MailArmor;
import com.trashboxbobylev.tormentpixeldungeon.items.armor.PlateArmor;
import com.trashboxbobylev.tormentpixeldungeon.items.armor.ScaleArmor;
import com.trashboxbobylev.tormentpixeldungeon.items.artifacts.AlchemistsToolkit;
import com.trashboxbobylev.tormentpixeldungeon.items.artifacts.Artifact;
import com.trashboxbobylev.tormentpixeldungeon.items.artifacts.CapeOfThorns;
import com.trashboxbobylev.tormentpixeldungeon.items.artifacts.ChaliceOfBlood;
import com.trashboxbobylev.tormentpixeldungeon.items.artifacts.CloakOfShadows;
import com.trashboxbobylev.tormentpixeldungeon.items.artifacts.DriedRose;
import com.trashboxbobylev.tormentpixeldungeon.items.artifacts.ExperienceBelt;
import com.trashboxbobylev.tormentpixeldungeon.items.artifacts.EtherealChains;
import com.trashboxbobylev.tormentpixeldungeon.items.artifacts.HornOfPlenty;
import com.trashboxbobylev.tormentpixeldungeon.items.artifacts.LloydsBeacon;
import com.trashboxbobylev.tormentpixeldungeon.items.artifacts.MasterThievesArmband;
import com.trashboxbobylev.tormentpixeldungeon.items.artifacts.SandalsOfNature;
import com.trashboxbobylev.tormentpixeldungeon.items.artifacts.TalismanOfForesight;
import com.trashboxbobylev.tormentpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.trashboxbobylev.tormentpixeldungeon.items.artifacts.UnstableSpellbook;
import com.trashboxbobylev.tormentpixeldungeon.items.bags.Bag;
import com.trashboxbobylev.tormentpixeldungeon.items.food.Food;
import com.trashboxbobylev.tormentpixeldungeon.items.food.MysteryMeat;
import com.trashboxbobylev.tormentpixeldungeon.items.food.Pasty;
import com.trashboxbobylev.tormentpixeldungeon.items.potions.Potion;
import com.trashboxbobylev.tormentpixeldungeon.items.potions.PotionOfExperience;
import com.trashboxbobylev.tormentpixeldungeon.items.potions.PotionOfFrost;
import com.trashboxbobylev.tormentpixeldungeon.items.potions.PotionOfHealing;
import com.trashboxbobylev.tormentpixeldungeon.items.potions.PotionOfInvisibility;
import com.trashboxbobylev.tormentpixeldungeon.items.potions.PotionOfLevitation;
import com.trashboxbobylev.tormentpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.trashboxbobylev.tormentpixeldungeon.items.potions.PotionOfMight;
import com.trashboxbobylev.tormentpixeldungeon.items.potions.PotionOfMindVision;
import com.trashboxbobylev.tormentpixeldungeon.items.potions.PotionOfParalyticGas;
import com.trashboxbobylev.tormentpixeldungeon.items.potions.PotionOfPurity;
import com.trashboxbobylev.tormentpixeldungeon.items.potions.PotionOfStrength;
import com.trashboxbobylev.tormentpixeldungeon.items.potions.PotionOfToxicGas;
import com.trashboxbobylev.tormentpixeldungeon.items.rings.Ring;
import com.trashboxbobylev.tormentpixeldungeon.items.rings.RingOfAccuracy;
import com.trashboxbobylev.tormentpixeldungeon.items.rings.RingOfElements;
import com.trashboxbobylev.tormentpixeldungeon.items.rings.RingOfEnergy;
import com.trashboxbobylev.tormentpixeldungeon.items.rings.RingOfEvasion;
import com.trashboxbobylev.tormentpixeldungeon.items.rings.RingOfForce;
import com.trashboxbobylev.tormentpixeldungeon.items.rings.RingOfFuror;
import com.trashboxbobylev.tormentpixeldungeon.items.rings.RingOfHaste;
import com.trashboxbobylev.tormentpixeldungeon.items.rings.RingOfMight;
import com.trashboxbobylev.tormentpixeldungeon.items.rings.RingOfSharpshooting;
import com.trashboxbobylev.tormentpixeldungeon.items.rings.RingOfTenacity;
import com.trashboxbobylev.tormentpixeldungeon.items.rings.RingOfWealth;
import com.trashboxbobylev.tormentpixeldungeon.items.scrolls.Scroll;
import com.trashboxbobylev.tormentpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.trashboxbobylev.tormentpixeldungeon.items.scrolls.ScrollOfLullaby;
import com.trashboxbobylev.tormentpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.trashboxbobylev.tormentpixeldungeon.items.scrolls.ScrollOfMagicalInfusion;
import com.trashboxbobylev.tormentpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.trashboxbobylev.tormentpixeldungeon.items.scrolls.ScrollOfPsionicBlast;
import com.trashboxbobylev.tormentpixeldungeon.items.scrolls.ScrollOfRage;
import com.trashboxbobylev.tormentpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.trashboxbobylev.tormentpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.trashboxbobylev.tormentpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.trashboxbobylev.tormentpixeldungeon.items.scrolls.ScrollOfTerror;
import com.trashboxbobylev.tormentpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.trashboxbobylev.tormentpixeldungeon.items.wands.Wand;
import com.trashboxbobylev.tormentpixeldungeon.items.wands.WandOfBlastWave;
import com.trashboxbobylev.tormentpixeldungeon.items.wands.WandOfCorruption;
import com.trashboxbobylev.tormentpixeldungeon.items.wands.WandOfDisintegration;
import com.trashboxbobylev.tormentpixeldungeon.items.wands.WandOfFireblast;
import com.trashboxbobylev.tormentpixeldungeon.items.wands.WandOfFrost;
import com.trashboxbobylev.tormentpixeldungeon.items.wands.WandOfLightning;
import com.trashboxbobylev.tormentpixeldungeon.items.wands.WandOfMagicMissile;
import com.trashboxbobylev.tormentpixeldungeon.items.wands.WandOfPlasma;
import com.trashboxbobylev.tormentpixeldungeon.items.wands.WandOfPrismaticLight;
import com.trashboxbobylev.tormentpixeldungeon.items.wands.WandOfRegrowth;
import com.trashboxbobylev.tormentpixeldungeon.items.wands.WandOfTransfusion;
import com.trashboxbobylev.tormentpixeldungeon.items.wands.WandOfVenom;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.Weapon;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.AssassinsBlade;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.BattleAxe;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Dagger;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Dirk;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Flail;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Glaive;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Greataxe;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Greatshield;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Greatsword;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.HandAxe;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Knuckles;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Katars;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Longsword;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Mace;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.MagesStaff;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Quarterstaff;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.RoundShield;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.RunicBlade;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Sai;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Scimitar;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Shortsword;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Spear;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Sword;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.WarHammer;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Whip;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.WornShortsword;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.NinjaSword;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.DoubleDagger;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Halberd;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.FightingKnifes;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.JjanGo;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.GalacticSword;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Interhammer;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Electroglaive;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.DoubleShortswords;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.ChlorateEruption;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.WoodenBow;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.ExactBow;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.SimpleCrossbow;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.PrinceCrossbow;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.AdamantineBow;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.missiles.Boomerang;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.missiles.CurareDart;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.missiles.Dart;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.missiles.IncendiaryDart;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.missiles.Javelin;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.missiles.Shuriken;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.missiles.Tamahawk;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.missiles.CobaltScytle;
import com.trashboxbobylev.tormentpixeldungeon.plants.BlandfruitBush;
import com.trashboxbobylev.tormentpixeldungeon.plants.Blindweed;
import com.trashboxbobylev.tormentpixeldungeon.plants.Dreamfoil;
import com.trashboxbobylev.tormentpixeldungeon.plants.Earthroot;
import com.trashboxbobylev.tormentpixeldungeon.plants.Fadeleaf;
import com.trashboxbobylev.tormentpixeldungeon.plants.Firebloom;
import com.trashboxbobylev.tormentpixeldungeon.plants.Icecap;
import com.trashboxbobylev.tormentpixeldungeon.plants.Plant;
import com.trashboxbobylev.tormentpixeldungeon.plants.Rotberry;
import com.trashboxbobylev.tormentpixeldungeon.plants.Sorrowmoss;
import com.trashboxbobylev.tormentpixeldungeon.plants.Starflower;
import com.trashboxbobylev.tormentpixeldungeon.plants.Stormvine;
import com.trashboxbobylev.tormentpixeldungeon.plants.Sungrass;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Generator {

	public enum Category {
		WEAPON	( 6,    Weapon.class ),
		WEP_T1	( 0,    Weapon.class),
		WEP_T2	( 0,    Weapon.class),
		WEP_T3	( 0,    Weapon.class),
		WEP_T4	( 0,    Weapon.class),
		WEP_T5	( 0,    Weapon.class),
        WEP_T6  ( 0,    Weapon.class),
        WEP_T7 (  0,    Weapon.class),
		ARMOR	( 4,    Armor.class ),
		POTION	( 20,   Potion.class ),
		SCROLL	( 20,   Scroll.class ),
		WAND	( 3,    Wand.class ),
		RING	( 1,    Ring.class ),
		ARTIFACT( 1,    Artifact.class),
		SEED	( 0,    Plant.Seed.class ),
		FOOD	( 0,    Food.class ),
		GOLD	( 25,   Gold.class ),
        MISC   ( 30,    Item.class );
		
		public Class<?>[] classes;
		public float[] probs;
		
		public float prob;
		public Class<? extends Item> superClass;
		
		private Category( float prob, Class<? extends Item> superClass ) {
			this.prob = prob;
			this.superClass = superClass;
		}
		
		public static int order( Item item ) {
			for (int i=0; i < values().length; i++) {
				if (values()[i].superClass.isInstance( item )) {
					return i;
				}
			}
			
			return item instanceof Bag ? Integer.MAX_VALUE : Integer.MAX_VALUE - 1;
		}
		
		private static final float[] INITIAL_ARTIFACT_PROBS = new float[]{ 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1};
		
		static {
			GOLD.classes = new Class<?>[]{
					Gold.class };
			GOLD.probs = new float[]{ 1 };
			
			SCROLL.classes = new Class<?>[]{
					ScrollOfIdentify.class,
					ScrollOfTeleportation.class,
					ScrollOfRemoveCurse.class,
					ScrollOfUpgrade.class,
					ScrollOfRecharging.class,
					ScrollOfMagicMapping.class,
					ScrollOfRage.class,
					ScrollOfTerror.class,
					ScrollOfLullaby.class,
					ScrollOfMagicalInfusion.class,
					ScrollOfPsionicBlast.class,
					ScrollOfMirrorImage.class };
			SCROLL.probs = new float[]{ 30, 10, 20, 0, 15, 15, 12, 8, 8, 0, 4, 10 };
			
			POTION.classes = new Class<?>[]{
					PotionOfHealing.class,
					PotionOfExperience.class,
					PotionOfToxicGas.class,
					PotionOfParalyticGas.class,
					PotionOfLiquidFlame.class,
					PotionOfLevitation.class,
					PotionOfStrength.class,
					PotionOfMindVision.class,
					PotionOfPurity.class,
					PotionOfInvisibility.class,
					PotionOfMight.class,
					PotionOfFrost.class };
			POTION.probs = new float[]{ 45, 4, 15, 10, 15, 10, 0, 20, 12, 10, 0, 10 };
			
			//TODO: add last ones when implemented
			WAND.classes = new Class<?>[]{
					WandOfMagicMissile.class,
					WandOfLightning.class,
					WandOfDisintegration.class,
					WandOfFireblast.class,
					WandOfVenom.class,
					WandOfBlastWave.class,
					//WandOfLivingEarth.class,
					WandOfFrost.class,
					WandOfPrismaticLight.class,
					WandOfPlasma.class,
					WandOfTransfusion.class,
					WandOfCorruption.class,
					WandOfRegrowth.class };
			WAND.probs = new float[]{ 5, 4, 4, 4, 4, 3, /*3,*/ 3, 3, 2, 3, 3, 3 };
			
			//see generator.randomWeapon
			WEAPON.classes = new Class<?>[]{};
			WEAPON.probs = new float[]{};
			
			WEP_T1.classes = new Class<?>[]{
					WornShortsword.class,
					Knuckles.class,
					Dagger.class,
					MagesStaff.class,
					Boomerang.class,
					Dart.class
			};
			WEP_T1.probs = new float[]{ 1, 1, 1, 0, 0, 1 };
			
			WEP_T2.classes = new Class<?>[]{
					Shortsword.class,
					HandAxe.class,
					Spear.class,
					Quarterstaff.class,
					Dirk.class,
                    WoodenBow.class,
					IncendiaryDart.class
			};
			WEP_T2.probs = new float[]{ 6, 5, 5, 4, 4, 4, 6};
			
			WEP_T3.classes = new Class<?>[]{
					Sword.class,
					Mace.class,
					Scimitar.class,
					RoundShield.class,
					Sai.class,
					Whip.class,
                    ExactBow.class,
					Shuriken.class,
					CurareDart.class
			};
			WEP_T3.probs = new float[]{ 6, 5, 5, 4, 4, 4, 4, 6, 6 };
			
			WEP_T4.classes = new Class<?>[]{
					Longsword.class,
					BattleAxe.class,
					Flail.class,
					RunicBlade.class,
                    SimpleCrossbow.class,
					AssassinsBlade.class,
                    Katars.class,
					Javelin.class
			};
			WEP_T4.probs = new float[]{ 5, 5, 5, 4, 4, 4, 4, 5 };
			
			WEP_T5.classes = new Class<?>[]{
					Greatsword.class,
					WarHammer.class,
					Glaive.class,
					Greataxe.class,
					Greatshield.class,
                    PrinceCrossbow.class,
					Tamahawk.class
			};
			WEP_T5.probs = new float[]{ 6, 5, 4, 4, 4, 4, 6 };

			WEP_T6.classes = new Class<?>[]{
					NinjaSword.class,
					DoubleDagger.class,
					Halberd.class,
					FightingKnifes.class,
					JjanGo.class,
                    AdamantineBow.class,
					CobaltScytle.class
			};
			WEP_T6.probs = new float[]{ 6, 4, 4, 4, 4, 5, 6 };
			
            WEP_T7.classes = new Class<?>[]{
					GalacticSword.class,
					Interhammer.class,
					Electroglaive.class,
					DoubleShortswords.class,
					ChlorateEruption.class,
					CobaltScytle.class
			};
			WEP_T7.probs = new float[]{ 6, 5, 5, 4, 3, 6 };

			//see Generator.randomArmor
			ARMOR.classes = new Class<?>[]{
					ClothArmor.class,
					LeatherArmor.class,
					MailArmor.class,
					ScaleArmor.class,
					PlateArmor.class,
                    CompositeArmor.class,
                    CompositeArmor.class };
			ARMOR.probs = new float[]{ 0, 0, 0, 0, 0, 0, 0};
			
			FOOD.classes = new Class<?>[]{
					Food.class,
					Pasty.class,
					MysteryMeat.class };
			FOOD.probs = new float[]{ 4, 1, 0 };
			
			RING.classes = new Class<?>[]{
					RingOfAccuracy.class,
					RingOfEvasion.class,
					RingOfElements.class,
					RingOfForce.class,
					RingOfFuror.class,
					RingOfHaste.class,
					RingOfEnergy.class,
					RingOfMight.class,
					RingOfSharpshooting.class,
					RingOfTenacity.class,
					RingOfWealth.class};
			RING.probs = new float[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
			
			ARTIFACT.classes = new Class<?>[]{
					CapeOfThorns.class,
					ChaliceOfBlood.class,
					CloakOfShadows.class,
					HornOfPlenty.class,
					MasterThievesArmband.class,
					SandalsOfNature.class,
					TalismanOfForesight.class,
					TimekeepersHourglass.class,
					UnstableSpellbook.class,
					AlchemistsToolkit.class, //currently removed from drop tables, pending rework.
					DriedRose.class,
					LloydsBeacon.class,
					EtherealChains.class,
                    ExperienceBelt.class
			};
			ARTIFACT.probs = INITIAL_ARTIFACT_PROBS.clone();
			
			SEED.classes = new Class<?>[]{
					Firebloom.Seed.class,
					Icecap.Seed.class,
					Sorrowmoss.Seed.class,
					Blindweed.Seed.class,
					Sungrass.Seed.class,
					Earthroot.Seed.class,
					Fadeleaf.Seed.class,
					Rotberry.Seed.class,
					BlandfruitBush.Seed.class,
					Dreamfoil.Seed.class,
					Stormvine.Seed.class,
					Starflower.Seed.class};
			SEED.probs = new float[]{ 12, 12, 12, 12, 12, 12, 12, 0, 3, 12, 12, 1 };

            MISC.classes = new Class<?>[]{Arrow.class};
            MISC.probs = new float[]{1};
		}
	}

	private static final float[][] floorSetTierProbs = new float[][] {
			{0, 70, 20,  8,  2, 0, 0},
			{0, 25, 50, 18,  5, 2, 0},
			{0, 10, 35, 40, 10, 5, 0},
			{0,  0, 10, 50, 25, 15, 0},
			{0,  0,  8, 20, 42, 30, 0},
            {0,  0,  0, 0, 10, 75, 15}
	};
	
	private static HashMap<Category,Float> categoryProbs = new LinkedHashMap<>();
	
	public static void reset() {
		for (Category cat : Category.values()) {
			categoryProbs.put( cat, cat.prob );
		}
	}
	
	public static Item random() {
		Category cat = Random.chances( categoryProbs );
		if (cat == null){
			reset();
			cat = Random.chances( categoryProbs );
		}
		categoryProbs.put( cat, categoryProbs.get( cat ) - 1);
		return random( cat );
	}
	
	public static Item random( Category cat ) {
		try {
			
			switch (cat) {
			case ARMOR:
				return randomArmor();
			case WEAPON:
				return randomWeapon();
			case ARTIFACT:
				Item item = randomArtifact();
				//if we're out of artifacts, return a ring instead.
				return item != null ? item : random(Category.RING);
			default:
				return ((Item)cat.classes[Random.chances( cat.probs )].newInstance()).random();
			}
			
		} catch (Exception e) {

			ShatteredPixelDungeon.reportException(e);
			return null;
			
		}
	}
	
	public static Item random( Class<? extends Item> cl ) {
		try {
			
			return ((Item)cl.newInstance()).random();
			
		} catch (Exception e) {

			ShatteredPixelDungeon.reportException(e);
			return null;
			
		}
	}

	public static Armor randomArmor(){
		return randomArmor(Dungeon.depth / 5);
	}
	
	public static Armor randomArmor(int floorSet) {

		floorSet = (int)GameMath.gate(0, floorSet, floorSetTierProbs.length-1);

		try {
			Armor a = (Armor)Category.ARMOR.classes[Random.chances(floorSetTierProbs[floorSet])].newInstance();
			a.random();
			return a;
		} catch (Exception e) {
			ShatteredPixelDungeon.reportException(e);
			return null;
		}
	}

	public static final Category[] wepTiers = new Category[]{
			Category.WEP_T1,
			Category.WEP_T2,
			Category.WEP_T3,
			Category.WEP_T4,
			Category.WEP_T5,
            Category.WEP_T6,
            Category.WEP_T7
	};

	public static Weapon randomWeapon(){
		return randomWeapon(Dungeon.depth / 5);
	}
	
	public static Weapon randomWeapon(int floorSet) {

		floorSet = (int)GameMath.gate(0, floorSet, floorSetTierProbs.length-1);

		try {
			Category c = wepTiers[Random.chances(floorSetTierProbs[floorSet])];
			Weapon w = (Weapon)c.classes[Random.chances(c.probs)].newInstance();
			w.random();
			return w;
		} catch (Exception e) {
			ShatteredPixelDungeon.reportException(e);
			return null;
		}
	}

	//enforces uniqueness of artifacts throughout a run.
	public static Artifact randomArtifact() {

		try {
			Category cat = Category.ARTIFACT;
			int i = Random.chances( cat.probs );

			//if no artifacts are left, return null
			if (i == -1){
				return null;
			}
			
			Class<?extends Artifact> art = (Class<? extends Artifact>) cat.classes[i];

			if (removeArtifact(art)) {
				Artifact artifact = art.newInstance();
				
				artifact.random();
				
				return artifact;
			} else {
				return null;
			}

		} catch (Exception e) {
			ShatteredPixelDungeon.reportException(e);
			return null;
		}
	}

	public static boolean removeArtifact(Class<?extends Artifact> artifact) {
		if (spawnedArtifacts.contains(artifact))
			return false;

		Category cat = Category.ARTIFACT;
		for (int i = 0; i < cat.classes.length; i++)
			if (cat.classes[i].equals(artifact)) {
				if (cat.probs[i] == 1){
					cat.probs[i] = 0;
					spawnedArtifacts.add(artifact);
					return true;
				} else
					return false;
			}

		return false;
	}

	//resets artifact probabilities, for new dungeons
	public static void initArtifacts() {
		Category.ARTIFACT.probs = Category.INITIAL_ARTIFACT_PROBS.clone();
		spawnedArtifacts = new ArrayList<>();
	}

	private static ArrayList<Class<?extends Artifact>> spawnedArtifacts = new ArrayList<>();
	
	private static final String GENERAL_PROBS = "general_probs";
	private static final String SPAWNED_ARTIFACTS = "spawned_artifacts";
	
	public static void storeInBundle(Bundle bundle) {
		Float[] genProbs = categoryProbs.values().toArray(new Float[0]);
		float[] storeProbs = new float[genProbs.length];
		for (int i = 0; i < storeProbs.length; i++){
			storeProbs[i] = genProbs[i];
		}
		bundle.put( GENERAL_PROBS, storeProbs);
		
		bundle.put( SPAWNED_ARTIFACTS, spawnedArtifacts.toArray(new Class[0]));
	}

	public static void restoreFromBundle(Bundle bundle) {
		if (bundle.contains(GENERAL_PROBS)){
			float[] probs = bundle.getFloatArray(GENERAL_PROBS);
			for (int i = 0; i < probs.length; i++){
				categoryProbs.put(Category.values()[i], probs[i]);
			}
		} else {
			reset();
		}
		
		initArtifacts();
		if (bundle.contains(SPAWNED_ARTIFACTS)){
			for ( Class<?extends Artifact> artifact : bundle.getClassArray(SPAWNED_ARTIFACTS) ){
				removeArtifact(artifact);
			}
		//pre-0.6.1 saves
		} else if (bundle.contains("artifacts")) {
			String[] names = bundle.getStringArray("artifacts");
			Category cat = Category.ARTIFACT;

			for (String artifact : names)
				for (int i = 0; i < cat.classes.length; i++)
					if (cat.classes[i].getSimpleName().equals(artifact))
						cat.probs[i] = 0;
		}
	}
}
