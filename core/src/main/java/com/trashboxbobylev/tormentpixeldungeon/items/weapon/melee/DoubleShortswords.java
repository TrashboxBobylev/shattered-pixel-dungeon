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

package com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee;

import com.trashboxbobylev.tormentpixeldungeon.actors.Char;
import com.trashboxbobylev.tormentpixeldungeon.actors.hero.Hero;
import com.trashboxbobylev.tormentpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class DoubleShortswords extends MeleeWeapon {

    public String mode = "ACCURACY";
    public static final String AC_ACCURACY	= "CHANGE TO 'ACCURACY'";
    public static final String AC_SPEED	= "CHANGE TO 'SPEED'";

	{
		image = ItemSpriteSheet.DOUBLE_SHORTSWORDS;

		tier = 7;
		DLY = mode == "SPEED" ? 0.5f : 1f; //2x speed on speed mode, else normal
        ACC = mode == "ACCURACY" ? 1.3f : 1f; //+30% acc in accuracy mode, else normal
	}

  	@Override
	public ArrayList<String> actions(Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		if (isEquipped(hero)) actions.add( MODE == " ACCURACY" ? AC_SPEED : AC_ACCURACY );
		return actions;
	}

	@Override
	public void execute( Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals( AC_SPEED )) mode = "SPEED";
	    else if (action.equals( AC_ACCURACY )) mode = "ACCURACY";
	}

	@Override
	public int max(int lvl) {
		return  Math.round(
(mode == "SPEED" ? 2.5f : 4f)
                *(tier+1)) +     //20-32 base, down from 40
				lvl*Math.round(
(mode == "SPEED" ? 0.5f : 1f)
                *(tier+1));  //+4-+8 per level, down from +8
	}

	@Override
	public int defenseFactor( Char owner ) {
		return 6;	//6 extra defence
	}

    private static final String MODE	= "mode";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( MODE, mode );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle(bundle);
		mode = bundle.getString( MODE );
	}
}
