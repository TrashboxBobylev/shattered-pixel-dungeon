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

package com.trashboxbobylev.tormentpixeldungeon.scenes;

import com.trashboxbobylev.tormentpixeldungeon.Assets;
import com.trashboxbobylev.tormentpixeldungeon.Badges;
import com.trashboxbobylev.tormentpixeldungeon.Chrome;
import com.trashboxbobylev.tormentpixeldungeon.ShatteredPixelDungeon;
import com.trashboxbobylev.tormentpixeldungeon.effects.BadgeBanner;
import com.trashboxbobylev.tormentpixeldungeon.items.DewVial;
import com.trashboxbobylev.tormentpixeldungeon.items.Item;
import com.trashboxbobylev.tormentpixeldungeon.items.Stylus;
import com.trashboxbobylev.tormentpixeldungeon.items.Torch;
import com.trashboxbobylev.tormentpixeldungeon.items.armor.PlateArmor;
import com.trashboxbobylev.tormentpixeldungeon.items.artifacts.CloakOfShadows;
import com.trashboxbobylev.tormentpixeldungeon.items.artifacts.DriedRose;
import com.trashboxbobylev.tormentpixeldungeon.items.artifacts.EtherealChains;
import com.trashboxbobylev.tormentpixeldungeon.items.artifacts.HornOfPlenty;
import com.trashboxbobylev.tormentpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.trashboxbobylev.tormentpixeldungeon.items.artifacts.UnstableSpellbook;
import com.trashboxbobylev.tormentpixeldungeon.items.food.Blandfruit;
import com.trashboxbobylev.tormentpixeldungeon.items.food.Food;
import com.trashboxbobylev.tormentpixeldungeon.items.potions.PotionOfHealing;
import com.trashboxbobylev.tormentpixeldungeon.items.rings.RingOfEnergy;
import com.trashboxbobylev.tormentpixeldungeon.items.rings.RingOfMight;
import com.trashboxbobylev.tormentpixeldungeon.items.rings.RingOfWealth;
import com.trashboxbobylev.tormentpixeldungeon.items.stones.StoneOfEnchantment;
import com.trashboxbobylev.tormentpixeldungeon.items.wands.WandOfCorruption;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Dagger;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Flail;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Greataxe;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Longsword;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee.Quarterstaff;
import com.trashboxbobylev.tormentpixeldungeon.messages.Messages;
import com.trashboxbobylev.tormentpixeldungeon.sprites.CharSprite;
import com.trashboxbobylev.tormentpixeldungeon.sprites.ItemSprite;
import com.trashboxbobylev.tormentpixeldungeon.sprites.ItemSpriteSheet;
import com.trashboxbobylev.tormentpixeldungeon.ui.Archs;
import com.trashboxbobylev.tormentpixeldungeon.ui.ExitButton;
import com.trashboxbobylev.tormentpixeldungeon.ui.Icons;
import com.trashboxbobylev.tormentpixeldungeon.ui.RenderedTextMultiline;
import com.trashboxbobylev.tormentpixeldungeon.ui.ScrollPane;
import com.trashboxbobylev.tormentpixeldungeon.ui.Window;
import com.trashboxbobylev.tormentpixeldungeon.windows.WndTitledMessage;
import com.watabou.input.Touchscreen;
import com.watabou.noosa.Camera;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Image;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.TouchArea;
import com.watabou.noosa.ui.Component;

import java.util.ArrayList;

//TODO: update this class with relevant info as new versions come out.
public class ChangesScene extends PixelScene {

	private final ArrayList<ChangeInfo> infos = new ArrayList<>();

	@Override
	public void create() {
		super.create();

		int w = Camera.main.width;
		int h = Camera.main.height;

		RenderedText title = PixelScene.renderText( Messages.get(this, "title"), 9 );
		title.hardlight(Window.TITLE_COLOR);
		title.x = (w - title.width()) / 2 ;
		title.y = 4;
		align(title);
		add(title);

		ExitButton btnExit = new ExitButton();
		btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
		add( btnExit );

		NinePatch panel = Chrome.get(Chrome.Type.TOAST);

		int pw = 135 + panel.marginLeft() + panel.marginRight() - 2;
		int ph = h - 16;

		panel.size( pw, ph );
		panel.x = (w - pw) / 2f;
		panel.y = title.y + title.height();
		align( panel );
		add( panel );

		ScrollPane list = new ScrollPane( new Component() ){

			@Override
			public void onClick(float x, float y) {
				for (ChangeInfo info : infos){
					if (info.onClick( x, y )){
						return;
					}
				}
			}

		};
		add( list );
		
		Component content = list.content();
		content.clear();

		float posY = 0;
		float nextPosY = 0;
		boolean second =false;
		for (ChangeInfo info : infos){
			if (info.major) {
				posY = nextPosY;
				second = false;
				info.setRect(0, posY, panel.innerWidth(), 0);
				content.add(info);
				posY = nextPosY = info.bottom();
			} else {
				if (!second){
					second = true;
					info.setRect(0, posY, panel.innerWidth()/2f, 0);
					content.add(info);
					nextPosY = info.bottom();
				} else {
					second = false;
					info.setRect(panel.innerWidth()/2f, posY, panel.innerWidth()/2f, 0);
					content.add(info);
					nextPosY = Math.max(info.bottom(), nextPosY);
					posY = nextPosY;
				}
			}
		}


		content.setSize( panel.innerWidth(), (int)Math.ceil(posY) );

		list.setRect(
				panel.x + panel.marginLeft(),
				panel.y + panel.marginTop() - 1,
				panel.innerWidth(),
				panel.innerHeight() + 2);
		list.scrollTo(0, 0);

		Archs archs = new Archs();
		archs.setSize( Camera.main.width, Camera.main.height );
		addToBack( archs );

		fadeIn();
	}

	@Override
	protected void onBackPressed() {
		ShatteredPixelDungeon.switchNoFade(TitleScene.class);
	}

	private static class ChangeInfo extends Component {

		protected ColorBlock line;

		private RenderedText title;
		private boolean major;

		private RenderedTextMultiline text;

		private ArrayList<ChangeButton> buttons = new ArrayList<>();

		public ChangeInfo( String title, boolean majorTitle, String text){
			super();
			
			if (majorTitle){
				this.title = PixelScene.renderText( title, 9 );
				line = new ColorBlock( 1, 1, 0xFF222222);
				add(line);
			} else {
				this.title = PixelScene.renderText( title, 6 );
				line = new ColorBlock( 1, 1, 0xFF333333);
				add(line);
			}
			major = majorTitle;

			add(this.title);

			if (text != null && !text.equals("")){
				this.text = PixelScene.renderMultiline(text, 6);
				add(this.text);
			}

		}

		public void hardlight( int color ){
			title.hardlight( color );
		}

		public void addButton( ChangeButton button ){
			buttons.add(button);
			add(button);

			button.setSize(16, 16);
			layout();
		}

		public boolean onClick( float x, float y ){
			for( ChangeButton button : buttons){
				if (button.inside(x, y)){
					button.onClick();
					return true;
				}
			}
			return false;
		}

		@Override
		protected void layout() {
			float posY = this.y + 2;
			if (major) posY += 2;

			title.x = x + (width - title.width()) / 2f;
			title.y = posY;
			PixelScene.align( title );
			posY += title.baseLine() + 2;

			if (text != null) {
				text.maxWidth((int) width());
				text.setPos(x, posY);
				posY += text.height();
			}

			float posX = x;
			float tallest = 0;
			for (ChangeButton change : buttons){

				if (posX + change.width() >= right()){
					posX = x;
					posY += tallest;
					tallest = 0;
				}

				//centers
				if (posX == x){
					float offset = width;
					for (ChangeButton b : buttons){
						offset -= b.width();
						if (offset <= 0){
							offset += b.width();
							break;
						}
					}
					posX += offset / 2f;
				}

				change.setPos(posX, posY);
				posX += change.width();
				if (tallest < change.height()){
					tallest = change.height();
				}
			}
			posY += tallest + 2;

			height = posY - this.y;
			
			if (major) {
				line.size(width(), 1);
				line.x = x;
				line.y = y+2;
			} else if (x == 0){
				line.size(1, height());
				line.x = width;
				line.y = y;
			} else {
				line.size(1, height());
				line.x = x;
				line.y = y;
			}
		}
	}

	//not actually a button, but functions as one.
	private static class ChangeButton extends Component {

		protected Image icon;
		protected String title;
		protected String message;

		public ChangeButton( Image icon, String title, String message){
			super();
			
			this.icon = icon;
			add(this.icon);

			this.title = Messages.titleCase(title);
			this.message = message;

			layout();
		}

		public ChangeButton( Item item, String message ){
			this( new ItemSprite(item), item.name(), message);
		}

		protected void onClick() {
			ShatteredPixelDungeon.scene().add(new ChangesWindow(new Image(icon), title, message));
		}

		@Override
		protected void layout() {
			super.layout();

			icon.x = x + (width - icon.width) / 2f;
			icon.y = y + (height - icon.height) / 2f;
			PixelScene.align(icon);
		}
	}
	
	private static class ChangesWindow extends WndTitledMessage {
	
		public ChangesWindow( Image icon, String title, String message ) {
			super( icon, title, message);
			
			add( new TouchArea( chrome ) {
				@Override
				protected void onClick( Touchscreen.Touch touch ) {
					hide();
				}
			} );
			
		}
		
	}
}
