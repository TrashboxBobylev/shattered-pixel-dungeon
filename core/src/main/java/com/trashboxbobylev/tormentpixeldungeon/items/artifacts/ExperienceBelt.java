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

package com.trashboxbobylev.tormentpixeldungeon.items.artifacts;

import com.trashboxbobylev.tormentpixeldungeon.Dungeon;
import com.trashboxbobylev.tormentpixeldungeon.sprites.ItemSpriteSheet;
import com.trashboxbobylev.tormentpixeldungeon.utils.GLog;

public class ExperienceBelt extends Artifact {

    private static final int BOLT_UPGRADE_SCALE = 75;

    {
        image = ItemSpriteSheet.ARTIFACT_BELT

        levelCap = 5;
    }

    @Override
	public String desc() {
		String desc = super.desc();

		if ( isEquipped (Dungeon.hero) )
			desc += "\n\n" + Messages.get(this, "desc_worn");

		return desc;
	}

    	@Override
	protected ArtifactBuff passiveBuff() {
		return new ExpObtain();
	}

    public class ExpObtain extends ArtifactBuff {
        public void obtain(int expObt){
            exp += expObt;
            if (exp >= level()*BOLT_UPGRADE_SCALE){
                upgrade();
				GLog.p(Messages.get(this, "levelup"));
                exp = 0;
            }
        }
	}
}