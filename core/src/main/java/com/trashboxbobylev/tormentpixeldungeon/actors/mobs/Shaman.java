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
import com.trashboxbobylev.tormentpixeldungeon.effects.particles.SparkParticle;
import com.trashboxbobylev.tormentpixeldungeon.items.Generator;
import com.trashboxbobylev.tormentpixeldungeon.items.Item;
import com.trashboxbobylev.tormentpixeldungeon.items.armor.glyphs.Potential;
import com.trashboxbobylev.tormentpixeldungeon.items.wands.WandOfLightning;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.enchantments.Shocking;
import com.trashboxbobylev.tormentpixeldungeon.mechanics.Ballistica;
import com.trashboxbobylev.tormentpixeldungeon.messages.Messages;
import com.trashboxbobylev.tormentpixeldungeon.sprites.CharSprite;
import com.trashboxbobylev.tormentpixeldungeon.sprites.ShamanSprite;
import com.trashboxbobylev.tormentpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class Shaman extends Mob implements Callback {

	private static final float TIME_TO_ZAP	= 1f;
	
	{
		spriteClass = ShamanSprite.class;
		
		HP = HT = 18;
		defenseSkill = 8;
		
		EXP = 6;
		maxLvl = 14;
		
		loot = Generator.Category.SCROLL;
		lootChance = 0.33f;
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 2, 8 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 11;
	}
	
	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 4);
	}
	
	@Override
	protected boolean canAttack( Char enemy ) {
		return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
	}
	
	@Override
	protected boolean doAttack( Char enemy ) {

		if (Dungeon.level.distance( pos, enemy.pos ) <= 1) {
			
			return super.doAttack( enemy );
			
		} else {
			
			boolean visible = fieldOfView[pos] || fieldOfView[enemy.pos];
			if (visible) {
				sprite.zap( enemy.pos );
			}
			
			spend( TIME_TO_ZAP );
			
			if (hit( this, enemy, true )) {
				int dmg = Random.NormalIntRange(3, 10);
				if (Dungeon.level.water[enemy.pos] && !enemy.flying) {
					dmg *= 1.5f;
				}
				enemy.damage( dmg, this );
				
				enemy.sprite.centerEmitter().burst( SparkParticle.FACTORY, 3 );
				enemy.sprite.flash();
				
				if (enemy == Dungeon.hero) {
					
					Camera.main.shake( 2, 0.3f );
					
					if (!enemy.isAlive()) {
						Dungeon.fail( getClass() );
						GLog.n( Messages.get(this, "zap_kill") );
					}
				}
			} else {
				enemy.sprite.showStatus( CharSprite.NEUTRAL,  enemy.defenseVerb() );
			}
			
			return !visible;
		}
	}
	
	@Override
	public void call() {
		next();
	}

    @Override
    public void die(Object cause){
        super.die(cause);
        
        if (Random.Int(50) == 0 && Dungeon.isChallenged()) {
Dungeon.level.drop(Generator.random(WandOfLighting. class), pos).sprite.drop();
        
        }
    }

	{
		resistances.add( WandOfLightning.class );
		resistances.add( Shocking.class );
		resistances.add( Potential.class );
	}
}
