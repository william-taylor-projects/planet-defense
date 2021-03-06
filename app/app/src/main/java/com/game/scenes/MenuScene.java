package com.game.scenes;

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
import com.framework.IFactory;
import com.framework.audio.AudioClip;
import com.framework.core.ClickEvent;
import com.framework.core.EventManager;
import com.framework.core.ExitEvent;
import com.framework.core.Scene;
import android.view.MotionEvent;

import com.framework.graphics.Button;
import com.framework.graphics.Font;
import com.framework.graphics.Image;
import com.framework.graphics.Label;
import com.framework.graphics.RenderQueue;
import com.game.activity.MainActivity;
import com.game.events.MuteEvent;
import com.game.events.StateClick;

public class MenuScene extends Scene {
	private Image background;
	private MenuButtons buttons;
	private Button quit;
	private Button tutorial;
	private Label title;
	private AudioClip audio;
	private AudioClip music;
	private ClickEvent tutorialEvent;
	private ClickEvent event;
	
	/** onCreate method is called when the scene is created */
	@Override
	public void onCreate(IFactory factory) {
		/** Request the following assets are pulled from the frameworks factory */
		background = factory.request("MenuBackground");
		music = factory.request("BackgroundMusic");
		quit = factory.request("BackButton");
		title = factory.request("MenuText");
		
		/** initialise the inner button class */
		buttons = new MenuButtons();
		buttons.initialise(factory);
		
		/** Setup the tutorial button and its event */
		tutorial = new Button(Font.get("Small"));
		tutorial.setText("View Tutorial ?", 150, 0, 200, 50);
	
		tutorialEvent = new ClickEvent(tutorial);
		tutorialEvent.eventType(new StateClick(MainActivity.TUTORIAL));
		
		/** Setup the quit event */
		event = new ClickEvent(quit);
		event.eventType(new ExitEvent());
		
		/** Setup the background music */
		audio = new AudioClip(com.game.R.raw.menu);
		audio.setVolume(1.0f, 1.0f);
		audio.setLoop(true);	
		
		/** Register these listeners so they are tracked and executed */
		EventManager.get().addListener(tutorialEvent);
		EventManager.get().addListener(event);
	}
	
	/** update method called just before rendering */
	@Override
	public void onUpdate() {
		/** Just update all the individual game.objects */
		background.update();
		tutorial.update();
		buttons.update();
		title.update();
		quit.update();
		
		/** We play the audio and calling this on each update will also change the volume */
		audio.play();
	}
	
	/** onRender called when we are rendering the scene */
	@Override
	public void onRender(RenderQueue renderList) {
		/** Just add all the game.objects to the queue */
		renderList.put(background);
		renderList.put(tutorial);
		renderList.put(title);
		renderList.put(quit);
	
		/** Then call the onRender function for the buttons inner class */
		buttons.onRender(renderList);
	}
	
	/** Handle the onTouch event which is called when the user presses a button */
	@Override
	public void onTouch(MotionEvent e, int x, int y) {
		/** if the button event is a press event  */
		if(e.getAction() == MotionEvent.ACTION_DOWN) {
			/** Provide the data to the listeners */
			tutorialEvent.OnTouch(e, x, y);
			buttons.onTouch(e, x, y);
			event.OnTouch(e, x, y);
		}
	}
	
	/** Called when the scene is exited  */
	@Override
	public void onExit(Integer nextScene) {
		/** If we go to the main level stop the menu background music */
		if(nextScene == MainActivity.LEVEL) {
			audio.setVolume(0.0F, 0.0F);
			music.restart();
		}
		
		/** remove the listener from the EventManager */
		EventManager.get().removeListener(event);
	}
	
	/** */
	@Override
	public void onEnter(Integer previousScene) {
		/** When enter the scene re-add the listener */
		EventManager.get().addListener(event);
		
		if(previousScene == MainActivity.LEVEL) {
			audio.setVolume(1.0F, 1.0F);
			audio.restart();
		}
	}
	
	/**
	 * A inner class which keeps all the buttons
	 * in a container that is easy to use.
	 * 
	 * @author William Taylor
	 */
	public class MenuButtons {
		/** The array of menu buttons Play Credits etc */
		private Button[] buttons = new Button[3];
		
		/** The array of game.events trigged by the button array above */
		private ClickEvent[] events = new ClickEvent[3];
		
		/** The mute button */
		private Button mute;
		
		/** The mute event for the button above  */
		private ClickEvent muteEvent;
	
		/** The initialise function which sets up the object */
		public void initialise(IFactory factory) {		
			/** get the font for the buttons */
			Font medium = Font.get("medium");
			for(int i = 0; i < 3; i++) {
				buttons[i] = new Button(medium);
				events[i] = new ClickEvent(buttons[i]);
			}
			
			/** Setup the mute button */
			mute = new Button();
			mute.setSprite("sprites/audio.png", 1180, 0, 100, 100);
			
			/** Setup its event when pressed */
			muteEvent = new ClickEvent(mute);
			muteEvent.eventType(new MuteEvent());
			
			/** Set the button size etc */
			buttons[0].setText("Start Game", 640, 425, 200, 100);
			buttons[1].setText("Statistics", 640, 250, 200, 100);
			buttons[2].setText("Credits", 640, 75, 200, 100);
			
			/** Give them there game.events */
			events[0].eventType(new StateClick(MainActivity.LEVEL));
			events[1].eventType(new StateClick(MainActivity.STATISTICS));
			events[2].eventType(new StateClick(MainActivity.INFO));
			
			/** add the game.events to the evetnabager */
			EventManager.get().addListeners(events);
			EventManager.get().addListener(muteEvent);
		}
		
		/** The onTouch handler that just send the data to the game.events */
		public void onTouch(MotionEvent e, int x, int y) {
			if(e.getAction() == MotionEvent.ACTION_DOWN) {
				muteEvent.OnTouch(e, x, y);
				for(int i = 0; i < 3; i++) {
					events[i].OnTouch(e, x, y);
				}
			}
		}
		
		/** The update function just updates all buttons */
		public void update() {
			mute.update();
			/** Just go through the array and update the button simples */
			for(int i = 0; i < 3; i++) {
				buttons[i].update();
			}
		}
		
		/** The onRender function just pushes each button to the renderQueue */
		public void onRender(RenderQueue renderQueue) {
			renderQueue.put(mute);
			
			/** Just iterate through the array and push to the queue */
			for(int i = 0; i < 3; i++) {
				renderQueue.put(buttons[i]);
			}
		}
	}
}
