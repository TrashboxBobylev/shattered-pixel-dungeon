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

package com.trashboxbobylev.tormentpixeldungeon.actors.mobs.npcs;

import com.trashboxbobylev.tormentpixeldungeon.Dungeon;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.Buff;
import com.trashboxbobylev.tormentpixeldungeon.effects.CellEmitter;
import com.trashboxbobylev.tormentpixeldungeon.effects.particles.ElmoParticle;
import com.trashboxbobylev.tormentpixeldungeon.items.Heap;
import com.trashboxbobylev.tormentpixeldungeon.items.Item;
import com.trashboxbobylev.tormentpixeldungeon.messages.Messages;
import com.trashboxbobylev.tormentpixeldungeon.scenes.GameScene;
import com.trashboxbobylev.tormentpixeldungeon.sprites.ShopkeeperSprite;
import com.trashboxbobylev.tormentpixeldungeon.windows.WndBag;
import com.trashboxbobylev.tormentpixeldungeon.windows.WndTradeItem;

public class Shopkeeper extends NPC {

	{
		spriteClass = ShopkeeperSprite.class;

		properties.add(Property.IMMOVABLE);
	}
	
	@Override
	protected boolean act() {

		throwItem();
		
		sprite.turnTo( pos, Dungeon.hero.pos );
		spend( TICK );
		return true;
	}
	
	@Override
	public void damage( int dmg, Object src ) {
		flee();
	}
	
	@Override
	public void add( Buff buff ) {
		flee();
	}
	
	public void flee() {
		for (Heap heap: Dungeon.level.heaps.values()) {
			if (heap.type == Heap.Type.FOR_SALE) {
				CellEmitter.get( heap.pos ).burst( ElmoParticle.FACTORY, 4 );
				heap.destroy();
			}
		}
		
		destroy();
		
		sprite.killAndErase();
		CellEmitter.get( pos ).burst( ElmoParticle.FACTORY, 6 );
	}
	
	@Override
	public boolean reset() {
		return true;
	}
	
	public static WndBag sell() {
		return GameScene.selectItem( itemSelector, WndBag.Mode.FOR_SALE, Messages.get(Shopkeeper.class, "sell"));
	}
	
	private static WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect( Item item ) {
			if (item != null) {
				WndBag parentWnd = sell();
				GameScene.show( new WndTradeItem( item, parentWnd ) );
			}
		}
	};

	@Override
	public boolean interact() {
		sell();
		return false;
	}
}
