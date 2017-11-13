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

package com.trashboxbobylev.tormentpixeldungeon.items.weapon.enchantments;

import com.trashboxbobylev.tormentpixeldungeon.actors.Char;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.Blindness;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.Buff;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.Cripple;
import com.trashboxbobylev.tormentpixeldungeon.effects.Speck;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.Weapon;
import com.trashboxbobylev.tormentpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Random;

public class Dazzling extends Weapon.Enchantment {

	private static ItemSprite.Glowing YELLOW = new ItemSprite.Glowing( 0xFFFF00 );

	@Override
	public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
		// lvl 0 - 20%
		// lvl 1 - 33%
		// lvl 2 - 43%
		int level = Math.max( 0, weapon.level() );

		if (Random.Int( level + 5 ) >= 4) {

			Buff.prolong( defender, Blindness.class, Random.Float( 1f, 1f + level ) );
			Buff.prolong( defender, Cripple.class, Random.Float( 1f, 1f + level/2f ) );
			defender.sprite.emitter().burst(Speck.factory(Speck.LIGHT), 6 );

		}

		return damage;
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return YELLOW;
	}

}