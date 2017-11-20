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
import com.trashboxbobylev.tormentpixeldungeon.items.wands.WandOfBlastWave;
import com.trashboxbobylev.tormentpixeldungeon.mechanics.Ballistica;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.Weapon;
import com.trashboxbobylev.tormentpixeldungeon.sprites.ItemSprite;
import com.trashboxbobylev.tormentpixeldungeon.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class Impulse extends Weapon.Enchantment {

	private static ItemSprite.Glowing KSENO = new ItemSprite.Glowing( 0x006894 );
	
	@Override
	public int proc( Weapon weapon, Char attacker, Char defender, int damage ) {
		// lvl 0 - 16%
		// lvl 1 - 24%
		// lvl 2 - 33%
		int level = Math.max( 0, weapon.level() );
		
		if (Random.Int( level + 6 ) >= 4) {
			
			int oppositeHero = defender.pos + (defender.pos - attacker.pos);
			Ballistica trajectory = new Ballistica(defender.pos, oppositeHero, Ballistica.MAGIC_BOLT);
			WandOfBlastWave.throwChar(defender, trajectory, 2);

		}

		return damage;
	}
	
	@Override
	public Glowing glowing() {
		return KSENO;
	}

}