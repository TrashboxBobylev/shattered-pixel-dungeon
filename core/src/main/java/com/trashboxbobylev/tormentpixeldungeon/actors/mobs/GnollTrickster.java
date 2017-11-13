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

import com.trashboxbobylev.tormentpixeldungeon.Dungeon;
import com.trashboxbobylev.tormentpixeldungeon.actors.Char;
import com.trashboxbobylev.tormentpixeldungeon.actors.blobs.Blob;
import com.trashboxbobylev.tormentpixeldungeon.actors.blobs.Fire;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.Buff;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.Burning;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.Poison;
import com.trashboxbobylev.tormentpixeldungeon.actors.mobs.npcs.Ghost;
import com.trashboxbobylev.tormentpixeldungeon.items.Generator;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.missiles.CurareDart;
import com.trashboxbobylev.tormentpixeldungeon.mechanics.Ballistica;
import com.trashboxbobylev.tormentpixeldungeon.scenes.GameScene;
import com.trashboxbobylev.tormentpixeldungeon.sprites.GnollTricksterSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class GnollTrickster extends Gnoll {

	{
		spriteClass = GnollTricksterSprite.class;

		HP = HT = 20;
		defenseSkill = 5;

		EXP = 5;

		state = WANDERING;

		loot = Generator.random(CurareDart.class);
		lootChance = 1f;

		properties.add(Property.MINIBOSS);
	}

	private int combo = 0;

	@Override
	public int attackSkill( Char target ) {
		return 16;
	}

	@Override
	protected boolean canAttack( Char enemy ) {
		Ballistica attack = new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE);
		return !Dungeon.level.adjacent(pos, enemy.pos) && attack.collisionPos == enemy.pos;
	}

	@Override
	public int attackProc( Char enemy, int damage ) {
		damage = super.attackProc( enemy, damage );
		//The gnoll's attacks get more severe the more the player lets it hit them
		combo++;
		int effect = Random.Int(4)+combo;

		if (effect > 2) {

			if (effect >=6 && enemy.buff(Burning.class) == null){

				if (Dungeon.level.flamable[enemy.pos])
					GameScene.add(Blob.seed(enemy.pos, 4, Fire.class));
				Buff.affect(enemy, Burning.class).reignite( enemy );

			} else
				Buff.affect( enemy, Poison.class).set((effect-2) * Poison.durationFactor(enemy));

		}
		return damage;
	}

	@Override
	protected boolean getCloser( int target ) {
		combo = 0; //if he's moving, he isn't attacking, reset combo.
		if (state == HUNTING) {
			return enemySeen && getFurther( target );
		} else {
			return super.getCloser( target );
		}
	}

	@Override
	public void die( Object cause ) {
		super.die( cause );

		Ghost.Quest.process();
	}

	private static final String COMBO = "combo";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle(bundle);
		bundle.put(COMBO, combo);
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		combo = bundle.getInt( COMBO );
	}

}
