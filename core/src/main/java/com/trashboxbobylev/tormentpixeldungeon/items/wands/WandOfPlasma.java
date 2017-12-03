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

package com.trashboxbobylev.tormentpixeldungeon.items.wands;

import com.trashboxbobylev.tormentpixeldungeon.Dungeon;
import com.trashboxbobylev.tormentpixeldungeon.actors.Actor;
import com.trashboxbobylev.tormentpixeldungeon.actors.Char;
import com.trashboxbobylev.tormentpixeldungeon.effects.SpellSprite;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.enchantments.Grim;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.MagesStaff;
import com.trashboxbobylev.tormentpixeldungeon.mechanics.Ballistica;
import com.trashboxbobylev.tormentpixeldungeon.messages.Messages; 
import com.trashboxbobylev.tormentpixeldungeon.sprites.ItemSpriteSheet;
import com.trashboxbobylev.tormentpixeldungeon.utils.GLog;

import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class WandOfPlasma extends DamageWand {

	{
		image = ItemSpriteSheet.WAND_WARDING;
	}

	public int min(int lvl){
		return 4+lvl;
	}

	public int max(int lvl){
		return 16+8*lvl;
	}
	
	@Override
	protected void onZap( Ballistica bolt ) {
				
		Char ch = Actor.findChar( bolt.collisionPos );
		if (ch != null) {

			processSoulMark(ch, chargesPerCast());
			ch.damage(damageRoll(), this);

			ch.sprite.burst(0x006894, (level()+2) * 10);
		}

        int damage = Random.IntRange(0, level()+2);
		curUser.damage(damage, this);

		if (!curUser.isAlive()){
			Dungeon.fail( getClass() );
			GLog.n( Messages.get(this, "ondeath") );
		}
	}

	@Override
	public void onHit(MagesStaff staff, Char attacker, Char defender, int damage) {
		new Grim().proc( staff, attacker, defender, damage);

        if (!defender.isAlive()) {
        for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
			Char ch = Actor.findChar( defender.pos + PathFinder.NEIGHBOURS8[i] );
			if (ch != null && ch.isAlive()) {
				int damage_expl = Math.max( 0,  Random.NormalIntRange(0, ((level()+1)*2) - (ch.drRoll() / 2) ));
				ch.damage( damage_expl, this );
				if (ch == attacker && !ch.isAlive()) {
	                Dungeon.fail( getClass() );
			        GLog.n( Messages.get(this, "ondeath") );
				}
			}
		}
        }
	}
	
	protected int initialCharges() {
		return 2;
	}

    @Override
	public void staffFx(MagesStaff.StaffParticle particle) {
		particle.color(0x006894);
		particle.am = 0.6f;
		particle.setLifespan(0.75f);
		particle.acc.set(0, +10);
		particle.speed.polar(-Random.Float(3.1415926f), 7f);
		particle.setSize(0f, 3f);
		particle.sizeJitter = 1f;
		particle.shuffleXY(1f);
		float dst = Random.Float(1f);
		particle.x -= dst;
		particle.y += dst;
	}

}
