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

package com.trashboxbobylev.tormentpixeldungeon.items.potions;

import com.trashboxbobylev.tormentpixeldungeon.Assets;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.Buff;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.Invisibility;
import com.trashboxbobylev.tormentpixeldungeon.actors.hero.Hero;
import com.trashboxbobylev.tormentpixeldungeon.messages.Messages;
import com.trashboxbobylev.tormentpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class PotionOfInvisibility extends Potion {

	{
		initials = 3;
	}

	@Override
	public void apply( Hero hero ) {
		setKnown();
		Buff.affect( hero, Invisibility.class, Math.round(Invisibility.DURATION+Invisibility.DURATION*level()*1.5f) );
		GLog.i( Messages.get(this, "invisible") );
		Sample.INSTANCE.play( Assets.SND_MELD );
	}
	
	@Override
	public int price() {
		return isKnown() ? 40 * quantity : super.price();
	}

}
