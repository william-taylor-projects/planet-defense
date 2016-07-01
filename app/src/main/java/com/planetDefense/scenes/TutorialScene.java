package com.planetDefense.scenes;

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
import com.planetDefense.TaloniteRenderer.RenderQueue;
import android.view.MotionEvent;

import com.planetDefense.activity.MainActivity;
import com.planetDefense.events.UsePowerUp;
import java.util.*;
import com.planetDefense.objects.*;


/**
 *  This is the tutorial scene which 
 *  is played on first startup. It can
 *  be played again through the menu scene
 *  
 * @version : final version for release
 * @author : William Taylor
 */
public class TutorialScene extends Scene {
	private static final Integer TIMER_START_DELAY = 5000;
	private static final Integer TIMER_DELAY = 6000;
	private static final Integer START = 0;
	private static final Integer END = 6;
	
	/** A reference to the inner class which holds all image textures */
	private TutorialObjects imageItems;
	
	/** A reference to the inner class that holds all the text items */
	private TutorialText textItems;
	
	/** A reference to the MainObjects class that holds all the game.objects for the main scene */
	private MainObjects objects;
	
	/** The background for the tutorial scene */
	private GameImage background;

	/** used as a lock to stop the timer continually firing */
	private Boolean stop = true;
	
	/** The tutorials state expressed as a Integer */
	private Integer state = START;
	
	/** A timer for the tutorial */
	private Timer timer;
	
	/** The create method which is called when the scene is created */
	@Override
	public void onCreate(IFactory factory) {
		/** First get these assets from the factory */
		background = factory.Request("LevelBackground");
		objects = factory.Request("LevelObjects");
		
		/** The initialise the following inner classes */
		textItems = new TutorialText();
		textItems.initialise(factory);
		textItems.reload();
		
		imageItems = new TutorialObjects();
		imageItems.initialise(factory);
		imageItems.reload();
	}

	/** update function simply updates all elements in the scene */
	@Override
	public void onUpdate() {
		/** If the user has finished the tutorial make sure to go to menu */
		if(state >= END) {
			SceneManager.get().SwitchTo(MainActivity.MENU);
		}
		
		/** update the inner classes and the background */
		imageItems.update();
		background.update();
		textItems.update();
		
		/** if we sould re-fire a new timer */
		if(stop) {
			/** start the time and block future execution */
			stop = false;
			StartTimer();
		}
	}
	
	/** onRender function simply pushes the drawable assets to the queue */
	@Override
	public void onRender(RenderQueue renderList) {
		/** First add the backgorund to the queue */
		renderList.pushRenderable(background);
		
		/** The call the onRender functions for the items */
		imageItems.onRender(renderList);
		textItems.onRender(renderList);
	}
	
	/** onTouch event handler */
	@Override
	public void onTouch(MotionEvent e, int x, int y) {
		/** Just call the inner classes handle which will deal with the event */
		imageItems.onTouch(e, x, y);
	}
	
	/** onEnter handler for when the user enters the state */
	@Override
	public void onEnter(Integer nextScene){	
		/** disable the timer block */
		stop = true;
	}
	
	/** onExit handler for when the user exits the state */
	@Override
	public void onExit(Integer nextScene) {
		/** Cancel and purge the timer */
		timer.cancel();
		timer.purge();
		
		/** Reset all game.objects just incase the user does the tutorial again */
		imageItems.reload();
		textItems.reload();

		/** reset all other variables */		
		objects.Reset(MainActivity.MENU);
		imageItems.resetObject();
		state = START;
	}
	
	/** start time function begins a timer that will increment the tutorials stage */
	private void StartTimer() {		
		/** get the right delay value */
		Integer time = TIMER_DELAY;
		if(state == START) {
			time = TIMER_START_DELAY;
		}
		
		/** start the timer */
		timer = new Timer();
		timer.schedule(new TimerTask() {	
			@Override
			public void run() {
				state++;
				
				imageItems.reload();
				textItems.reload();
				stop = true;
			}
		}, time);
	}
	
	/**
	 * The inner class that controls and manages all 
	 * the text elements in this scene
	 * 
	 * @author William Taylor
	 */
	public class TutorialText {
		/** The header text for the scene */
		private LabelLine headerText;
		
		/** should we reload the text value */
		private Boolean reload = false;

		/** initialise function that loads the header text and gets the font needed */
		public void initialise(IFactory factory) {
			/** Here we just load the text */
			headerText = new LabelLine(LabelEngine.Get("Medium"), "Welcome To Planet Defense");
			headerText.Load(450, 700);
		}
		
		/** update function called when we need to update the text */
		public void update() {
			/** if we need to reload the text*/
			if(reload) {
				/** load this string */
				switch(state) {
				
					case 0: headerText.Text("Welcome to Planet Defense"); break;
					case 1: headerText.Text("Move the ship by touching the sceen at various points"); break;
					case 2: headerText.Text("Your goal is to defend the planet from enemys"); break;
					case 3: headerText.Text("You must hold down on the screen to shoot and move"); imageItems.enableMissiles(); break;
					case 4: headerText.Text("Power Ups can spawn on the map so look out.");  break;
					case 5: headerText.Text("Good Luck & Have Fun"); break;
						
						/** if we go out of range dont show any text */
						default: headerText.Text(""); break;
				} 
				
				/** load and centre the text */
				headerText.Load(640, 700);
				headerText.Translate(640 - (headerText.GetWidth() / 2), 700);
				
				/** make sure we dont reload immediatly */
				reload = false;
			}
			
			/** update the text with all the new changes */
			headerText.Update();
		}
		
		/** onRender function just pushes the header text to the queue */
		public void onRender(RenderQueue renderList) {
			renderList.pushRenderable(headerText);
		}

		/** set stage method just updates what stage we are in the tutorial */
		public void reload() {			
			/** reload the text on the next call to update */
			reload = true;
		}
	}
	
	/**
	 * A inner class that manages all the other big elements
	 * that are to be drawn in this scene
	 * 
	 * @author William Taylor
	 */
	public class TutorialObjects {		
		/** The powerup to be spawned in stage 4 */
		private FastFire powerUp;
		
		/** The event to be fired by that powerup */
		private UsePowerUp event;
		
		/** The listener for the collision between the powerup and the player */
		private Collision listener;
		
		/** The missiles that the player can fire in stage 3 */
		private Missiles missiles;
		
		/** The planet which is shown in stage 2 */
		private Earth planet;
		
		/** The enemy which is shown in stage 2 */
		private Enemy enemy;
		
		/** & the player himself */
		private Ship player;
		

		/** This function should be called during a state change */
		public void reload() {
			/** if we are at this stage of the scene */
			if(state >= 2) {
				/** spawn an enemy */
				enemy.spawn(0);
			}
			
			/** if at this stage we should drop an item */
			if(state == 4) {
				/** drop item */
				powerUp.dropItem(200, 300);
			}
		}

		/** This is called when we get to a certain state */
		public void enableMissiles() {
			/** When we do we reset all the missiles fired */
			missiles.ResetObject();
		}

		/** initalise function just setups all the elements in the scene */
		public void initialise(IFactory factory) {
			/** get the following assets from the factory */
			missiles = factory.Request("Missiles");
			planet = factory.Request("Earth");
			player = factory.Request("Ship");
			
			/** Just init all other variables */
			powerUp = new FastFire();
			powerUp.setLength(3000);
			powerUp.enable();
			
			event = new UsePowerUp(powerUp);
			
			listener = new Collision();
			listener.surfaces(player.GetSprite(), powerUp.getSprite());
			listener.eventType(event);

			enemy = new Enemy();
			enemy.initialise(factory);
			enemy.setSpeed(3.0F);
			
			/** register the event with the even manager */
			EventManager.get().addListener(listener);
		}

		/** just updates all game.objects in the scene */
		public void update() {
			/** update these game.objects */
			powerUp.update();
			player.Update();
			
			/** if the powerUp has been used we should hide it */
			if(powerUp.used()) {
				powerUp.hide();
			}
			
			/** if we are at this state the following game.objects need to be updated*/
			if(state >= 2) {
				planet.update();
				enemy.update();
			}
			
			/** if we are at this state the following game.objects need to be updated*/
			if(state >= 3) {
				missiles.Update();
			}
		}

		/** onRender function just pushes all renderable targets to the queue */
		public void onRender(RenderQueue renderList) {
			/** push powerup first */
			renderList.pushRenderable(powerUp.getSprite());
			
			/** The push all other game.objects if we are in the correct state */
			if(state >= 2) {
				planet.draw(renderList);
				if(state < 3) {
					enemy.Draw(renderList);
				}
			}
			
			/** The push all other game.objects if we are in the correct state */
			if(state >= 3) {
				missiles.draw(renderList);
			} player.draw(renderList);
		}
		
		/** Just handles the thouch event which is thrown by the user */
		public void onTouch(MotionEvent e, int x, int y) {
			player.OnTouch(e, x, y);
			if(state >= 3) {
				missiles.OnTouch(e, x, y);
			}
		}

		/** reset function just resets all game.objects contained in the inner class */
		public void resetObject() {
			/** remove and hide the power up */
			this.powerUp.enable();
			this.powerUp.hide();
			
			/** Then just reset all the other game.objects */
			this.missiles.ResetObject();
			this.player.ResetObject();
			this.planet.resetObject();
			this.planet.repair();
		}
	}
}
