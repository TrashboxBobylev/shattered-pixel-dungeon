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

import com.trashboxbobylev.tormentpixeldungeon.actors.Actor;
import com.trashboxbobylev.tormentpixeldungeon.actors.Char;
import com.trashboxbobylev.tormentpixeldungeon.actors.hero.Hero;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.Invisibility;
import com.trashboxbobylev.tormentpixeldungeon.effects.Beam;
import com.trashboxbobylev.tormentpixeldungeon.mechanics.Ballistica;
import com.trashboxbobylev.tormentpixeldungeon.scenes.GameScene;
import com.trashboxbobylev.tormentpixeldungeon.scenes.CellSelector;
import com.trashboxbobylev.tormentpixeldungeon.messages.Messages;
import com.trashboxbobylev.tormentpixeldungeon.items.wands.Wand;
import com.trashboxbobylev.tormentpixeldungeon.ui.QuickSlotButton;
import com.trashboxbobylev.tormentpixeldungeon.tiles.DungeonTilemap;
import com.trashboxbobylev.tormentpixeldungeon.sprites.ItemSpriteSheet;
import com.trashboxbobylev.tormentpixeldungeon.utils.GLog;

import java.util.ArrayList;

public class GalacticSword extends MeleeWeapon {

    public static final String AC_ZAP = "SHOOT";

	{
		image = ItemSpriteSheet.GALACTIC_SWORD;

		tier = 7;

        defaultAction = AC_ZAP;
		usesTargeting = true;
	}

	protected int collisionProperties = Ballistica.MAGIC_BOLT;
	
	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_ZAP );

		return actions;
	}
	
	@Override
	public void execute( Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals( AC_ZAP )) {
			
			curUser = hero;
			curItem = this;
			GameScene.selectCell( zapper );
			
		}
	}

    protected static CellSelector.Listener zapper = new  CellSelector.Listener() {
		
		@Override
		public void onSelect( Integer target ) {
			
			if (target != null) {
				
				//FIXME this safety check shouldn't be necessary
				//it would be better to eliminate the curItem static variable.
				final GalacticSword curSword;
				if (curSword instanceof GalacticSword) {
					curSword = (GalacticSword) GalacticSword.curItem;
				} else {
					return;
                }

				final Ballistica shot = new Ballistica( curUser.pos, target, curSword.collisionProperties);
				int cell = shot.collisionPos;
				
				if (target == curUser.pos || cell == curUser.pos) {
					GLog.i( Messages.get(Wand.class, "self_target") );
					return;
				}

				curUser.sprite.zap(cell);

				//attempts to target the cell aimed at if something is there, otherwise targets the collision pos.
				if (Actor.findChar(target) != null)
					QuickSlotButton.target(Actor.findChar(target));
				else
					QuickSlotButton.target(Actor.findChar(cell));
					
			    curUser.busy();
                
                curUser.sprite.parent.add(
				new Beam.GalacticRay(curUser.sprite.center(), DungeonTilemap.raisedTileCenterToWorld(shot.collisionPos)));
                curUser.spendAndNext(curSword.DLY*2f);
					
					Invisibility.dispel();

                   	Char ch = Actor.findChar(shot.collisionPos);
		if (ch != null){
			ch.damage(curSword.damageRoll(ch)*1.5f, curSword);
		}
				
			}
		}
		
		@Override
		public String prompt() {
			return Messages.get(Wand.class, "prompt");
		}
	};
	

}
