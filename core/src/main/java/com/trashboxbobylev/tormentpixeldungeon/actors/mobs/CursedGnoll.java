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
import com.trashboxbobylev.tormentpixeldungeon.effects.particles.ShadowParticle;
import com.trashboxbobylev.tormentpixeldungeon.items.Gold;
import com.trashboxbobylev.tormentpixeldungeon.sprites.CursedGnollSprite;
import com.watabou.utils.Random;

public class CursedGnoll extends Mob {
	
	{
		spriteClass = CursedGnollSprite.class;
		
		HP = HT = 60;
		defenseSkill = 20;
		
		EXP = 12;
		maxLvl = 24;
		
		loot = Gold.class;
		lootChance = 0.75f;
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 16, 28 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 30;
	}
	
	@Override
	public int drRoll() {
		return 13;
	}

    @Override
	public int attackProc( Char enemy, int damage ) {
		damage = super.attackProc( enemy, damage );
		if (Random.Int( 2 ) == 0) {
			int heal = Random.NormalIntRange(damage / 2, damage);
            heal = Math.min(HT - HP, heal);

            HP += heal;
            
            sprite.emitter().burst( ShadowParticle.UP, 5 );
		}
		
		return damage;
	}

    @Override
    public float speed(){
        return Random.Float( 0.4f, 2.5f);
    }
}
