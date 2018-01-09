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
import com.trashboxbobylev.tormentpixeldungeon.messages.Messages;
import com.trashboxbobylev.tormentpixeldungeon.actors.Char;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.Amok;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.Sleep;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.Terror;
import com.trashboxbobylev.tormentpixeldungeon.actors.mobs.npcs.Imp;
import com.trashboxbobylev.tormentpixeldungeon.sprites.GolemSprite;
import com.trashboxbobylev.tormentpixeldungeon.sprites.CharSprite;
import com.watabou.utils.Random;
import com.watabou.utils.Bundle;

public class Golem extends Mob {
	
	{
		spriteClass = GolemSprite.class;
		
		HP = HT = 85;
		defenseSkill = 18;
		
		EXP = 12;
		maxLvl = 22;
	}

   private boolean enraged = false;
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		enraged = HP < HT / 2 && Dungeon.isChallenged();
	}
	
	@Override
	public int damageRoll() {
		return enraged ?
        Random.NormalIntRange( 25, 40 ) :
        Random.NormalIntRange( 40, 77 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 28;
	}
	
	@Override
	protected float attackDelay() {
		return 1.6f;
	}

    @Override
	public void damage( int dmg, Object src ) {
		super.damage( dmg, src );
		
		if (isAlive() && !enraged && HP < HT / 2 && Dungeon.isChallenged()) {
			enraged = true;
			spend( TICK*0.5f );
			if (Dungeon.level.heroFOV[pos]) {
				sprite.showStatus( CharSprite.NEGATIVE, Messages.get(Brute.class, "enraged") );
			}
		}
	}
	
	@Override
	public int drRoll() {
		return enraged ? 50 : 0;
	}

	@Override
	public void die( Object cause ) {
		Imp.Quest.process( this );
		
		super.die( cause );
	}
	
	{
		immunities.add( Amok.class );
		immunities.add( Terror.class );
		immunities.add( Sleep.class );
	}
}
