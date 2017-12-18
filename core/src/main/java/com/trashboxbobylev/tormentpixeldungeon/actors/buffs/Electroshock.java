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

package com.trashboxbobylev.tormentpixeldungeon.actors.buffs;

import com.trashboxbobylev.tormentpixeldungeon.Dungeon;
import com.trashboxbobylev.tormentpixeldungeon.effects.Lightning;
import com.trashboxbobylev.tormentpixeldungeon.messages.Messages;
import com.trashboxbobylev.tormentpixeldungeon.ui.BuffIndicator;
import com.trashboxbobylev.tormentpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class Electroshock extends Buff {

	{
		type = buffType.NEGATIVE;
	}
	
	protected int pos;
    public float left;
	
	private static final String POS	= "pos";
    private static final String LEFT = "left";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( POS, pos );
        bundle.put( LEFT, left );
		
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		pos = bundle.getInt( POS );
        left = bundle.getFloat( LEFT );
	}

    public void set( float duration ) {
		this.left = Math.max(duration, left);
	}

	public void extend( float duration ) {
		this.left += duration;
	}
	
	@Override
	public int icon() {
		return BuffIndicator.ELECTROSHOCK;
	}
	
	@Override
	public String toString() {
		return Messages.get(this, "name");
	}
	
	@Override
	public boolean act() {
		if (target.isAlive()) {		
			damage = Random.NormalIntRange((Dungeon.depth/5), Dungeon.depth);
            if (Dungeon.level.water[pos]) damage *= 2;
            if (target.pos != pos) {
                pos = target.pos;
                damage *= 2;
            }	
target.damage( damage * (Dungeon.isChallenged() ? 2 : 1), this );
				if (target.sprite.visible) {
					target.sprite.parent.add( new Lightning( target.pos, target.pos));
			}
				
				if (target == Dungeon.hero && !target.isAlive()) {
					Dungeon.fail( getClass() );
					GLog.n( Messages.get(this, "ondeath") );
				}
				
				spend( TICK );
                if (left <= TICK){
                    detach();
                } else left -= TICK;
			}
			else {
			
			detach();
			
		}
		
		return true;
	}

	@Override
	public String heroMessage() {
		return Messages.get(this, "heromsg");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", left);
	}
}
