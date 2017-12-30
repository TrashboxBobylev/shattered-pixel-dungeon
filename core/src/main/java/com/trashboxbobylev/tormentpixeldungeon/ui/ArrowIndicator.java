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

package com.trashboxbobylev.tormentpixeldungeon.ui;

import com.trashboxbobylev.tormentpixeldungeon.Dungeon;
import com.trashboxbobylev.tormentpixeldungeon.items.Item;
import com.trashboxbobylev.tormentpixeldungeon.items.Arrow;
import com.trashboxbobylev.tormentpixeldungeon.scenes.PixelScene;

public class ArrowIndicator extends Tag {
	
	private ItemSlot slot;
	
	public ArrowIndicator() {
		super( 0x964F49);
		
		setSize( 24, 24 );
		
		visible = true;
	}
	
	@Override
	protected void createChildren() {
		super.createChildren();
		
		slot = new ItemSlot();
		add( slot );
	}
	
	@Override
	protected void layout() {
		super.layout();
		
    	slot.setRect( x + 2, y + 3, width - 2, height - 6 );
        PixelScene.align(slot);
	}
	
	@Override
	public void update() {
	    Item arrow = new Arrow(Dungeon.arrows);
        
        slot.item(arrow);
        slot.enable(Dungeon.hero.ready);
        PixelScene.align(slot);
		super.update();
	}
}
