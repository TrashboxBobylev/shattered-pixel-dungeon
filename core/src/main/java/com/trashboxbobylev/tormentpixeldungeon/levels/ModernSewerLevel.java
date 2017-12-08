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

package com.trashboxbobylev.tormentpixeldungeon.levels;

import com.trashboxbobylev.tormentpixeldungeon.Assets;
import com.trashboxbobylev.tormentpixeldungeon.Dungeon;
import com.trashboxbobylev.tormentpixeldungeon.actors.mobs.npcs.Ghost;
import com.trashboxbobylev.tormentpixeldungeon.effects.Ripple;
import com.trashboxbobylev.tormentpixeldungeon.items.DewVial;
import com.trashboxbobylev.tormentpixeldungeon.levels.painters.Painter;
import com.trashboxbobylev.tormentpixeldungeon.levels.painters.SewerPainter;
import com.trashboxbobylev.tormentpixeldungeon.levels.traps.AlarmTrap;
import com.trashboxbobylev.tormentpixeldungeon.levels.traps.ChillingTrap;
import com.trashboxbobylev.tormentpixeldungeon.levels.traps.ConfusionTrap;
import com.trashboxbobylev.tormentpixeldungeon.levels.traps.FlockTrap;
import com.trashboxbobylev.tormentpixeldungeon.levels.traps.OozeTrap;
import com.trashboxbobylev.tormentpixeldungeon.levels.traps.ShockingTrap;
import com.trashboxbobylev.tormentpixeldungeon.levels.traps.SummoningTrap;
import com.trashboxbobylev.tormentpixeldungeon.levels.traps.TeleportationTrap;
import com.trashboxbobylev.tormentpixeldungeon.levels.traps.ToxicTrap;
import com.trashboxbobylev.tormentpixeldungeon.levels.traps.WornDartTrap;
import com.trashboxbobylev.tormentpixeldungeon.messages.Messages;
import com.trashboxbobylev.tormentpixeldungeon.scenes.GameScene;
import com.trashboxbobylev.tormentpixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.ColorMath;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class ModernSewerLevel extends RegularLevel {

	{
		color1 = 0x48763c;
		color2 = 0x59994a;
	}
	
	@Override
	protected int standardRooms() {
		//10 to 14, average 12
		return 10+Random.chances(new float[]{6, 4, 2, 1});
	}
	
	@Override
	protected int specialRooms() {
		//2 to 4, average 3
		return 2+Random.chances(new float[]{4, 4, 4, 2});
	}
	
	@Override
	protected Painter painter() {
		return new SewerPainter()
				.setWater(feeling == Feeling.WATER ? 0.85f : 0.30f, 5)
				.setGrass(feeling == Feeling.GRASS ? 0.80f : 0.20f, 4)
				.setTraps(nTraps(), trapClasses(), trapChances());
	}
	
	@Override
	public String tilesTex() {
		return Assets.TILES_MODERN_SEWERS;
	}
	
	@Override
	public String waterTex() {
		return Assets.WATER_SEWERS;
	}
	
	@Override
	protected Class<?>[] trapClasses() {
		return Dungeon.depth == 1 ?
				new Class<?>[]{ WornDartTrap.class } :
				new Class<?>[]{ ChillingTrap.class, ShockingTrap.class, ToxicTrap.class, WornDartTrap.class,
						AlarmTrap.class, OozeTrap.class,
						ConfusionTrap.class, FlockTrap.class, SummoningTrap.class, TeleportationTrap.class };
}

	@Override
	protected float[] trapChances() {
		return Dungeon.depth == 1 ?
				new float[]{1} :
				new float[]{8, 8, 8, 8,
						4, 4,
						2, 2, 2, 2};
	}
	
	@Override
	protected void createItems() {
		if (!Dungeon.LimitedDrops.DEW_VIAL.dropped()) {
			addItemToSpawn( new DewVial() );
			Dungeon.LimitedDrops.DEW_VIAL.drop();
		}
		
		super.createItems();
	}
	
	@Override
	public String tileName( int tile ) {
		switch (tile) {
			case Terrain.WATER:
				return Messages.get(SewerLevel.class, "water_name");
			default:
				return super.tileName( tile );
		}
	}
	
	@Override
	public String tileDesc(int tile) {
		switch (tile) {
			case Terrain.EMPTY_DECO:
				return Messages.get(SewerLevel.class, "empty_deco_desc");
			case Terrain.BOOKSHELF:
				return Messages.get(SewerLevel.class, "bookshelf_desc");
			default:
				return super.tileDesc( tile );
		}
	}
}