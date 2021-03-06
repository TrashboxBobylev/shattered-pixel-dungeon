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
import com.trashboxbobylev.tormentpixeldungeon.sprites.ItemSpriteSheet;

public class FightingKnifes extends MeleeWeapon {

	{
		image = ItemSpriteSheet.FIGHTING_KNIFES;

		tier = 6;
		DLY = 0.33f; //3x speed
	}

	@Override
	public int max(int lvl) {
		return  Math.round(1.75f*(tier+1)) +     //12 base, down from 35
				lvl*(tier-4);  //+2 per level, down from +7
	}

	@Override
	public int defenseFactor( Char owner ) {
		return 5;	//5 extra defence
	}
}
