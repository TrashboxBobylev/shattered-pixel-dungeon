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

import com.trashboxbobylev.tormentpixeldungeon.actors.Char;
import com.trashboxbobylev.tormentpixeldungeon.actors.hero.Hero;
import com.trashboxbobylev.tormentpixeldungeon.actors.mobs.Mob;
import com.trashboxbobylev.tormentpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class DoubleDagger extends MeleeWeapon {

	{
		image = ItemSpriteSheet.DOUBLE_DAGGER;

		tier = 6;
	}

	@Override
	public int max(int lvl) {
		return  3*(tier+1) +    //21 base, down from 35
				lvl*(tier+1);   //scaling unchanged
	}
	
	@Override
	public int damageRoll(Char owner) {
		if (owner instanceof Hero) {
			Hero hero = (Hero)owner;
			Char enemy = hero.enemy();
			if (enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)) {
				//deals 2x damage on surprise attack
				int damage = imbue.damageFactor(Random.NormalIntRange(
						min(),
						max()));
                damage *= 2;
				int exStr = hero.STR() - STRReq();
				if (exStr > 0) {
					damage += (Random.IntRange(0, exStr))*2;
				}
				return damage;
			}
		}
		return super.damageRoll(owner);
	}

}
