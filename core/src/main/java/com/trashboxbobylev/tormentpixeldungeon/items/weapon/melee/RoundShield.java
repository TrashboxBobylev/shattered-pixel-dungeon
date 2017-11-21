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
import com.trashboxbobylev.tormentpixeldungeon.sprites.CharSprite;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.Buff;
import com.trashboxbobylev.tormentpixeldungeon.sprites.ItemSpriteSheet;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.Guard;
import com.trashbpxbobylev.tormentpixeldungeon. messages.Messages;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class RoundShield extends MeleeWeapon {

    private final static String AC_GUARD = "GUARD";

	{
		image = ItemSpriteSheet.ROUND_SHIELD;

        defaultAction = AC_GUARD;

		tier = 3;
	}

	@Override
	public int max(int lvl) {
		return  3*(tier+1) +    //12 base, down from 20
				lvl*(tier-1);   //+2 per level, down from +4
	}

   	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions( hero );
		actions.add(AC_GUARD);
		return actions;
	}

    @Override
	public void execute( Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals(AC_GUARD)) {
            hero.sprite.showStatus(CharSprite.DEFAULT, Messages.get(Hero.class, "guard"));
            Buff.affect(hero, Guard.class).level(Random.IntRange(0, defenseFactor(hero)));
            hero.spendAndNext(isEquipped(hero) ? 1f : 3f);
		}
	}

	@Override
	public int defenseFactor( Char owner ) {
		return 5+2*level();     //5 extra defence, plus 2 per level;
	}
}