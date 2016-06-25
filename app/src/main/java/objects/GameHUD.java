package objects;

/**
 * Copyright (c) 2014 - William Taylor <wi11berto@yahoo.co.uk>
 *
 *	This software is provided 'as-is', without any express or implied warranty. 
 *  In no event will the authors be held liable for any damages arising from 
 *  the use of this software. Permission is granted to anyone to use this 
 *  software for any purpose, including commercial applications, and to 
 *  alter it and redistribute it freely, subject to the following 
 *  restrictions:
 *
 *	1. The origin of this software must not be misrepresented; 
 *     you must not claim that you wrote the original software. 
 *	   If you use this software in a product, an acknowledgment 
 *     in the product documentation would be appreciated 
 *     but is not required.
 *
 *  2. Altered source versions must be plainly marked as such, 
 *     and must not be misrepresented as being the original 
 *     software.
 *  
 *  3. This notice may not be removed or altered 
 *     from any source distribution.
 *     
 */
import com.planetDefense.*;
import com.planetDefense.TaloniteRenderer.RenderQueue;

import events.StateClick;

import android.view.MotionEvent;

public class GameHUD implements IContainer {
	private UpgradeButton UpgradeButton;
	private PlayerHealth PlayerHealth;
	private EarthHealth EarthHealth;
	private BackButton BackButton;
	private GameImage BackPlate;
	private WaveNumber Number;
	private PlayerCash Cash;
	
	public GameHUD() {
		UpgradeButton = new UpgradeButton();
		PlayerHealth = new PlayerHealth();
		EarthHealth = new EarthHealth();
		BackButton = new BackButton();
		Number = new WaveNumber();
		Cash = new PlayerCash();
		
		BackPlate = new GameImage("sprites/health outline.png");
		BackPlate.setPosition(-20, 625, 375, 175);
	}
	
	public void ResetObject() {
		Number.ResetObject();
	}
	
	public void Initialise(IFactory factory) {
		PlayerHealth.Initialise(factory);
		EarthHealth.Initialise(factory);
	}
	
	public void Update() {
		UpgradeButton.Update();
		PlayerHealth.Update();
		EarthHealth.Update();
		BackButton.Update();
		BackPlate.update();
		Number.SetLeft();
		Number.Update();
		Cash.Update();
	}
	
	public void OnTouch(MotionEvent e, float x, float y) {
		UpgradeButton.OnTouch(e, x, y);
		BackButton.Event.OnTouch(e, x, y);
	}
	
	@Override
	public void StackSubObjects(IFactory factory) {
		factory.Stack(Number, "WaveText");
	}
	
	public void SetLevel(int i) {
		Number.SetLevel(i);
	}

	public void draw(RenderQueue renderList) {
		renderList.pushRenderable(BackButton.Sprite);
		renderList.pushRenderable(BackPlate);
		renderList.pushRenderable(PlayerHealth.Sprite);
		renderList.pushRenderable(EarthHealth.Sprite);
		renderList.pushRenderable(UpgradeButton.Sprite);
		renderList.pushRenderable(Number.Text);
		renderList.pushRenderable(Cash.Text);
	}
	
	public class BackButton {
		public GameButton Sprite;
		public Click Event;

		public BackButton() {
			Sprite = new GameButton(LabelEngine.Get("small"));
			Sprite.SetText("Quit", 1225, 10, 200, 65);
			
			Event = new Click(Sprite);
			Event.eventType(new StateClick(MainActivity.MENU));
			EventManager.get().addListener(Event);
		}
		
		public void Update() {
			Sprite.Update();
		}
		
		public void OnTouch(MotionEvent e, float x, float y) {
			Event.OnTouch(e, x, y);
		}
	}
	
	public class EarthHealth {
		public GL_Image Sprite;
		public Earth Earth;
		
		public EarthHealth() {
			Sprite = new GL_Image();
			Sprite.load("sprites/health.png", "Health");
			Sprite.setPosition(0, 750, 300, 20);
		}

		public Object GetRawObject() {
			return Sprite;
		}

		public void Update() {
			Integer Health = Earth.getHealth();
			Float x = (float)Health/100;
			
			Sprite.setScale(x, 1.0f);
			Sprite.shift(0, 750, 1);
			Sprite.update(0);
		}

		public void Initialise(IFactory factory) {
			Earth = factory.Request("Earth");
		}
	}
	
	public class PlayerCash {
		private LabelLine Text;
		private Integer Cash;
		
		public PlayerCash() {
			Text = new LabelLine(LabelEngine.Get("small"), "$0");
			Text.Load(10, 665);
			Text.SetColour(1f, 0f, 0f, 1f);	
			Cash = 0;
		}
		
		public void Update() {
			if(Cash != Ship.CASH) {
				Cash = Ship.CASH;
				Text.Text("$" + Ship.CASH);
				Text.Load(10, 665);
				Text.SetColour(1f, 0f, 0f, 1f);
			} Text.Update();
		}
	}
	
	public class PlayerHealth {
		public GL_Image Sprite;
		public Ship Player;
		
		public PlayerHealth() {
			Sprite = new GL_Image();
			Sprite.load("sprites/health.png", "Health");
			Sprite.setPosition(0, 720, 225, 20);
		}
		
		public void Initialise(IFactory factory) {
			Player = factory.Request("Ship");
		}
		
		public Object GetRawObject() {
			return Sprite;
		}

		public void Update() {
			int Health = Player.GetHealth();
			float x = (float)Health/100;
			
			Sprite.setScale(x, 1.0f);
			Sprite.shift(0, 720, 1);
			Sprite.update(0);
		}
	}
	
	
	public class UpgradeButton {
		public GameButton Sprite;
		public Click Event;

		public UpgradeButton() {
			Sprite = new GameButton(LabelEngine.Get("small"));
			Sprite.SetText("Upgrades", 1185, 725, 300, 65);
		
			Event = new Click(Sprite);
			Event.eventType(new StateClick(4));
			EventManager.get().addListener(Event);
		}
		
		public void Update() {
			Sprite.Update();
		}
		
		public void OnTouch(MotionEvent e, float x, float y) {
			Event.OnTouch(e, x, y);
		}
	}
}

