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

package com.trashboxbobylev.tormentpixeldungeon.items.journal;

import com.trashboxbobylev.tormentpixeldungeon.Assets;
import com.trashboxbobylev.tormentpixeldungeon.actors.hero.Hero;
import com.trashboxbobylev.tormentpixeldungeon.items.Item;
import com.trashboxbobylev.tormentpixeldungeon.journal.Document;
import com.trashboxbobylev.tormentpixeldungeon.scenes.GameScene;
import com.trashboxbobylev.tormentpixeldungeon.sprites.ItemSpriteSheet;
import com.trashboxbobylev.tormentpixeldungeon.windows.WndJournal;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

public abstract class DocumentPage extends Item {
	
	{
		image = ItemSpriteSheet.MASTERY;
	}

	public abstract Document document();
	
	private String page;
	
	public void page( String page ){
		this.page = page;
	}
	
	public String page(){
		return page;
	}
	
	@Override
	public final boolean doPickUp(Hero hero) {
		GameScene.pickUpJournal(this, hero.pos);
		GameScene.flashJournal();
		WndJournal.last_index = 0;
		document().addPage(page);
		Sample.INSTANCE.play( Assets.SND_ITEM );
		hero.spendAndNext( TIME_TO_PICK_UP );
		return true;
	}
	
	private static final String PAGE = "page";
	
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put( PAGE, page() );
	}
	
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		page = bundle.getString( PAGE );
	}
}
