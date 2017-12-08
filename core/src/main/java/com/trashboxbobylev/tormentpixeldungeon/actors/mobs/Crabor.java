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

package com.trashboxbobylev.tormentpixeldungeon.actors.mobs;

import com.trashboxbobylev.tormentpixeldungeon.actors.Char;
import com.trashboxbobylev.tormentpixeldungeon.items.food.MysteryMeat;
import com.trashboxbobylev.tormentpixeldungeon.sprites.CraborSprite;
import com.watabou.utils.Random;

public class Crabor extends Mob {

	{
		spriteClass = CraborSprite.class;
		
		HP = HT = 150;
		defenseSkill = 40;
		baseSpeed = 2f;
		
		EXP = 20;
		maxLvl = 45;
		
		loot = new MysteryMeat();
		lootChance = 0.0167f;
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 12, 49 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 55;
	}
	
	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 14);
	}
}
