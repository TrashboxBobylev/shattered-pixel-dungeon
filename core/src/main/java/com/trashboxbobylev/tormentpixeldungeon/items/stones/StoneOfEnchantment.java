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

package com.trashboxbobylev.tormentpixeldungeon.items.stones;

import com.trashboxbobylev.tormentpixeldungeon.effects.Enchanting;
import com.trashboxbobylev.tormentpixeldungeon.effects.Speck;
import com.trashboxbobylev.tormentpixeldungeon.items.Item;
import com.trashboxbobylev.tormentpixeldungeon.items.armor.Armor;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.Weapon;
import com.trashboxbobylev.tormentpixeldungeon.messages.Languages;
import com.trashboxbobylev.tormentpixeldungeon.messages.Messages;
import com.trashboxbobylev.tormentpixeldungeon.sprites.ItemSpriteSheet;
import com.trashboxbobylev.tormentpixeldungeon.utils.GLog;
import com.trashboxbobylev.tormentpixeldungeon.windows.WndBag;

public class StoneOfEnchantment extends InventoryStone {
	
	{
		mode = WndBag.Mode.ENCHANTABLE;
		image = ItemSpriteSheet.STONE_TIWAZ;
	}
	
	@Override
	protected void onItemSelected(Item item) {
		
		if (item instanceof Weapon) {
			
			((Weapon)item).enchant();
			
		} else {
			
			((Armor)item).inscribe();
			
		}
		
		curUser.sprite.emitter().start( Speck.factory( Speck.LIGHT ), 0.1f, 5 );
		Enchanting.show( curUser, item );
		
		//FIXME add this to translations
		if (Messages.lang() == Languages.ENGLISH) {
			GLog.w("your %s glows in the dark", item.name());
		}
		
	}
}
