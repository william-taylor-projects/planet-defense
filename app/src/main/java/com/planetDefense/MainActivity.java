package com.planetDefense;

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
import com.planetDefense.EventManager;
import com.planetDefense.ExitEvent;
import com.planetDefense.ITaloniteActivity;
import com.planetDefense.SceneManager;
import com.planetDefense.TaloniteGame;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.app.Activity;
import android.os.Bundle;
import scenes.*;

/**
 *  The main and only activity for the application.
 *  
 * @version : final version for release
 * @author : William Taylor
 */
public class MainActivity extends Activity implements ITaloniteActivity {
	/** State ID's used for switching between states. */
	public static final int STATISTICS = 6;
	public static final int TUTORIAL = 0;
	public static final int GAMEOVER = 5;
	public static final int UPGRADE = 4;
	public static final int LEVEL = 2;
	public static final int INFO = 3;
	public static final int MENU = 1;
	
	/** The frameworks instance of the game */
	private TaloniteGame game;
	
	/** Activity on create function overriden to initialise the game */
	@Override
	protected void onCreate(Bundle inst) {
		super.onCreate(inst);
		
		/** Get the frameworks instance & initialise */
		game = (TaloniteGame)getApplicationContext();
		game.setupWindow(this);
		game.start(this);
	}
	
	/** on pause handler to mute the music when the game has been paused */
	@Override
	protected void onPause() {	
		/** call the supers mathod as dictated by android documentation */
		super.onPause();
		
		/** & call the frameworks onPause method*/
		game.onPause();
		
		AudioClip.MasterVolume = 0.0F;
	}
	
	/** on pause handler to enable the music when the game has been resumed */
	@Override
	public void onResume() {
		/** call the supers mathod as dictated by android documentation */
		super.onResume();
		
		/** & call the frameworks onResume method */
		game.onResume();
		
		AudioClip.MasterVolume = 1.0F;
	}
	
	/** key handler to the activity, events are passed to the game object */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Just posts a quit message if the user presses the back key
	    if(keyCode == KeyEvent.KEYCODE_BACK) {
	        EventManager.get().triggerEvent(new ExitEvent(), null);
	        return true;
	    }
	    
	    // indicate that the program has not handled the event
	    return false;
	}
	
	/** The onTouch event which passes the data to the framework */
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		/** Pass event information to the game */
		if(game.hasInitialised() && game.touchEvent(e)) {
			return true;
		} return false;
	}

	/** This function is called when the game wishes to create the game states */
	@Override
	public void setupStates(SceneManager scenes) {
		/** Setup game scenes */
		scenes.createScene(new LoadScreen());
		scenes.createScene(new TutorialScene());
		scenes.createScene(new MenuScene());
		scenes.createScene(new MainScene());
		scenes.createScene(new InformationScene());
		scenes.createScene(new UpgradeState());
		scenes.createScene(new GameOverState());
		scenes.createScene(new StatisticsScene());
	}
}
