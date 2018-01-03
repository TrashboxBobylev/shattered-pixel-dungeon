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

import com.trashboxbobylev.tormentpixeldungeon.Assets;
import com.trashboxbobylev.tormentpixeldungeon.Badges;
import com.trashboxbobylev.tormentpixeldungeon.Dungeon;
import com.trashboxbobylev.tormentpixeldungeon.actors.Actor;
import com.trashboxbobylev.tormentpixeldungeon.actors.Char;
import com.trashboxbobylev.tormentpixeldungeon.actors.blobs.Blob;
import com.trashboxbobylev.tormentpixeldungeon.actors.blobs.GooWarn;
import com.trashboxbobylev.tormentpixeldungeon.actors.blobs.ToxicGas;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.Buff;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.LockedFloor;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.Ooze;
import com.trashboxbobylev.tormentpixeldungeon.effects.CellEmitter;
import com.trashboxbobylev.tormentpixeldungeon.effects.Speck;
import com.trashboxbobylev.tormentpixeldungeon.effects.particles.ElmoParticle;
import com.trashboxbobylev.tormentpixeldungeon.effects.Pushing;
import com.trashboxbobylev.tormentpixeldungeon.items.artifacts.LloydsBeacon;
import com.trashboxbobylev.tormentpixeldungeon.items.keys.SkeletonKey;
import com.trashboxbobylev.tormentpixeldungeon.items.scrolls.ScrollOfPsionicBlast;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.enchantments.Grim;
import com.trashboxbobylev.tormentpixeldungeon.messages.Messages;
import com.trashboxbobylev.tormentpixeldungeon.scenes.GameScene;
import com.trashboxbobylev.tormentpixeldungeon.sprites.CharSprite;
import com.trashboxbobylev.tormentpixeldungeon.sprites.GooSprite;
import com.trashboxbobylev.tormentpixeldungeon.sprites.GooShadeSprite;
import com.trashboxbobylev.tormentpixeldungeon.ui.BossHealthBar;
import com.trashboxbobylev.tormentpixeldungeon.utils.BArray;
import com.trashboxbobylev.tormentpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;

public class Goo extends Mob {

	{
		HP = HT = 100;
		EXP = 20;
		defenseSkill = 8;
		spriteClass = GooSprite.class;

		loot = new LloydsBeacon();
		lootChance = 0.333f;

		properties.add(Property.BOSS);
		properties.add(Property.DEMONIC);
	}

	private int pumpedUp = 0;

	@Override
	public int damageRoll() {
		int min = 1;
		int max = (HP*2 <= HT) ? 15 : 10;
		if (pumpedUp > 0) {
			pumpedUp = 0;
			PathFinder.buildDistanceMap( pos, BArray.not( Dungeon.level.solid, null ), 2 );
			for (int i = 0; i < PathFinder.distance.length; i++) {
				if (PathFinder.distance[i] < Integer.MAX_VALUE)
					CellEmitter.get(i).burst(ElmoParticle.FACTORY, 10);
			}
			Sample.INSTANCE.play( Assets.SND_BURNING );
			return Random.NormalIntRange( min*3, max*3 );
		} else {
			return Random.NormalIntRange( min, max );
		}
	}

	@Override
	public int attackSkill( Char target ) {
		int attack = 10;
		if (HP*2 <= HT) attack = 15;
		if (pumpedUp > 0) attack *= 2;
		return attack;
	}

	@Override
	public int defenseSkill(Char enemy) {
		return (int)(super.defenseSkill(enemy) * ((HP*2 <= HT)? 1.5 : 1));
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 2);
	}

	@Override
	public boolean act() {

		if (Dungeon.level.water[pos] && HP < HT) {
			sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			if (HP*2 == HT) {
				BossHealthBar.bleed(false);
				((GooSprite)sprite).spray(false);
			}
			HP++;
		}

		return super.act();
	}

	@Override
	protected boolean canAttack( Char enemy ) {
		return (pumpedUp > 0) ? distance( enemy ) <= 2 : super.canAttack(enemy);
	}

	@Override
	public int attackProc( Char enemy, int damage ) {
		damage = super.attackProc( enemy, damage );
		if (Random.Int( 3 ) == 0) {
			Buff.affect( enemy, Ooze.class );
			enemy.sprite.burst( 0x000000, 5 );
		}

		if (pumpedUp > 0) {
			Camera.main.shake( 3, 0.2f );
		}

		return damage;
	}

	@Override
	protected boolean doAttack( Char enemy ) {
		if (pumpedUp == 1) {
			((GooSprite)sprite).pumpUp();
			PathFinder.buildDistanceMap( pos, BArray.not( Dungeon.level.solid, null ), 2 );
			for (int i = 0; i < PathFinder.distance.length; i++) {
				if (PathFinder.distance[i] < Integer.MAX_VALUE)
					GameScene.add(Blob.seed(i, 2, GooWarn.class));
			}
			pumpedUp++;

			spend( attackDelay() );

			return true;
		} else if (pumpedUp >= 2 || Random.Int( (HP*2 <= HT) ? 2 : 5 ) > 0) {

			boolean visible = Dungeon.level.heroFOV[pos];

			if (visible) {
				if (pumpedUp >= 2) {
					((GooSprite) sprite).pumpAttack();
				}
				else
					sprite.attack( enemy.pos );
			} else {
				attack( enemy );
			}

			spend( attackDelay() );

			return !visible;

		} else {

			pumpedUp++;

			((GooSprite)sprite).pumpUp();

			for (int i=0; i < PathFinder.NEIGHBOURS9.length; i++) {
				int j = pos + PathFinder.NEIGHBOURS9[i];
				if (!Dungeon.level.solid[j]) {
					GameScene.add(Blob.seed(j, 2, GooWarn.class));
				}
			}

			if (Dungeon.level.heroFOV[pos]) {
				sprite.showStatus( CharSprite.NEGATIVE, Messages.get(this, "!!!") );
				GLog.n( Messages.get(this, "pumpup") );
			}

			spend( attackDelay() );

			return true;
		}
	}

	@Override
	public boolean attack( Char enemy ) {
		boolean result = super.attack( enemy );
		pumpedUp = 0;
        if (Dungeon.isChallenged()) spawnShade();
		return result;
	}

    private void spawnShade(){
        ArrayList<Integer> spawnPoints = new ArrayList<>();
		
		for (int i=0; i < PathFinder.NEIGHBOURS8.length; i++) {
			int p = pos + PathFinder.NEIGHBOURS8[i];
			if (Actor.findChar( p ) == null && (Dungeon.level.passable[p] || Dungeon.level.avoid[p])) {
				spawnPoints.add( p );
			}
		}
		
		if (spawnPoints.size() > 0) {
			Shade shade = new Shade();
			shade.pos = Random.element( spawnPoints );
			
			GameScene.add( shade );
			Actor.addDelayed( new Pushing( shade, pos, shade.pos ), -1 );
		}

		for (Mob mob : Dungeon.level.mobs) {
			if (mob instanceof Shade) {
				mob.aggro( Dungeon.hero );
			}
		}
    }

	@Override
	protected boolean getCloser( int target ) {
		pumpedUp = 0;
		return super.getCloser( target );
	}
	
	@Override
	public void move( int step ) {
		Dungeon.level.seal();
		super.move( step );
	}

	@Override
	public void damage(int dmg, Object src) {
       HashSet<Mob> shades = new HashSet<>();

		for (Mob mob : Dungeon.level.mobs)
			if (mob instanceof Shade)
				shades.add( mob );

		for (Mob shade : shades)
			shade.beckon( pos );

		dmg >>= shades.size();

		boolean bleeding = (HP*2 <= HT);
		super.damage(dmg, src);
		if ((HP*2 <= HT) && !bleeding){
			BossHealthBar.bleed(true);
			sprite.showStatus(CharSprite.NEGATIVE, Messages.get(this, "enraged"));
			((GooSprite)sprite).spray(true);
			yell(Messages.get(this, "gluuurp"));
		}
		LockedFloor lock = Dungeon.hero.buff(LockedFloor.class);
		if (lock != null) lock.addTime(dmg*2);
	}

	@Override
	public void die( Object cause ) {
		
		super.die( cause );
		
		Dungeon.level.unseal();
		
		GameScene.bossSlain();
		Dungeon.level.drop( new SkeletonKey( Dungeon.depth ), pos ).sprite.drop();
		
		Badges.validateBossSlain();
		
		yell( Messages.get(this, "defeated") );
	}
	
	@Override
	public void notice() {
		super.notice();
		BossHealthBar.assignBoss(this);
		yell( Messages.get(this, "notice") );
	}

	private final String PUMPEDUP = "pumpedup";

	@Override
	public void storeInBundle( Bundle bundle ) {

		super.storeInBundle( bundle );

		bundle.put( PUMPEDUP , pumpedUp );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {

		super.restoreFromBundle( bundle );

		pumpedUp = bundle.getInt( PUMPEDUP );
		if (state != SLEEPING) BossHealthBar.assignBoss(this);
		if ((HP*2 <= HT)) BossHealthBar.bleed(true);

	}

    public static class Shade extends Mob {
        {
		HP = HT = 25;
		defenseSkill = 14;
		spriteClass = GooShadeSprite.class;

        properties.add(Property.MINIBOSS);
		properties.add(Property.DEMONIC);
	    }

    @Override
	public int attackProc( Char enemy, int damage ) {
		damage = super.attackProc( enemy, damage );
		if (Random.Int( 2 ) == 0) {
			Buff.affect( enemy, Ooze.class );
			enemy.sprite.burst( 0x000000, 5 );
		}

		return damage;
	}

   	@Override
	public boolean act() {

		if (Dungeon.level.water[pos] && HP < HT) {
			sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			if (HP*2 == HT) {
				((GooSprite)sprite).spray(false);
            }
			HP++;
		}

		return super.act();
	}
      
      @Override
      public int damageRoll(){
          return Random.NormalIntRange(4, 10);
      }

    @Override
	public int attackSkill( Char target ) {
		int attack = 20;
		if (HP*2 <= HT) attack = 30;
		return attack;
    }

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 1);
	}

    }
	
	{
		resistances.add( ToxicGas.class );
		resistances.add( Grim.class );
		resistances.add( ScrollOfPsionicBlast.class );
	}
}
