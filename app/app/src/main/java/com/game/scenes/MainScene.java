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
import com.framework.core.Scene;
import com.framework.core.SceneManager;
import com.framework.graphics.Font;
import com.framework.graphics.Label;
import android.view.MotionEvent;

import com.framework.graphics.RenderQueue;
import com.game.activity.MainActivity;
import com.game.actors.Enemies;
import com.game.actors.Enemy;
import com.game.common.Statistics;

/**
 *  This is the main scene for the game.
 *  All its game.objects are stored in the Main
 *  Objects class for more clear code
 *  
 * @version : final version for release
 * @author : William Taylor
 */
public class MainScene extends Scene {
	/** A reference to all the main game.objects in this scene */
	private MainObjects objects;
	
	/** A label which renders when a wave has completed */
	private Label waveDoneString;
	
	/** Just turn true when a wave has completed */
	private Boolean completed;
	
	/** A counter for the wave number */
	private Integer waveNumber = 1;
	
	/** The alpha value for the waveDoneString above */
	private Float alpha = 1F;
	
	/** The audio clip for the levels background music */
	private AudioClip audio;
	
	/** onCreate function called when the scene is to be created */
	@Override
	public void onCreate(IFactory factory) {
		/** Request the LavelObjects from the frameworks fractory */
		objects = factory.request("LevelObjects");
		objects.initialise(factory);
		
		/** get the audio from the factory & just init the variable */
		audio = factory.request("BackgroundMusic");
		completed = false;
		
		waveDoneString = new Label(Font.get("large"), "Wave Completed");
		waveDoneString.load(0, 0);
		waveDoneString.setInitialPosition(640 - waveDoneString.getWidth() / 2, 325);
	}
	
	/** Called to update game.objects, positions etc */
	@Override
	public void onUpdate() {
		/** If there are no enemys the wave has completed */
		if(objects.getRemainingEnemys() == 0) {
			/** Doing this will indicate that the waveDoneString should be drawn */
			completed = true;
		}

		objects.update();
		
		/** Play the audio this function also updates the volume */
		audio.play();
		
		/** If the wave is over... */
		if(completed) {
			/** Do a fade out effect for the waveDoneString */
			alpha -= 0.005f;
			
			/** But only if the player survivde should is show & update the string */
			if(objects.Alive()) {
				waveDoneString.setColour(1f, 1f, 1f, alpha);
				waveDoneString.update();
			}
			
			/** When the fade out effect has finished we increase the difficulty */
			if(alpha <= 0.0F) {
				/** Increment the amount of enemys that spawn as well as increase the speed */
				if(Enemies.STARTING_ENEMIES + Enemies.INCREMENT_VALUE < Enemies.MAX) {
					Enemies.STARTING_ENEMIES += Enemies.INCREMENT_VALUE;
					Enemy.SPEED += Enemy.INCREMENT;
				}
				
				/** update the statistics & call the game.objects to prepare for the next level */
				Statistics.get().newRecord(++waveNumber).waveDone();
				objects.NextLevel(waveNumber);
				
				/** reset these values as well */
				completed = false;
				alpha = 1f;
			}
		} 
	}

	/** The render function which is called when we are to redraw the scene*/
	@Override
	public void onRender(RenderQueue renderList) {
		/** Call the game.objects onRender function first */
		objects.onRender(renderList);
		
		/** Then if a wave has been completed we show push the waveDoneString as well */
		if(completed) {
			renderList.put(waveDoneString);
		} 
	}
	
	/** Called when the user touches the screen */
	@Override
	public void onTouch(MotionEvent e, int x, int y) {
		/** Just passes the data to the game.objects onTouch handler */
		objects.onTouch(e, x, y);
	}

	/** Called when the user enters the main scene */
	@Override
	public void onEnter(Integer previousScene) {
		/** First call the game.objects onEnter function handler */
		objects.onEnter();
		
		audio.restart();
		audio.setVolume(1.0F, 1.0F);
	}
	
	/** Called when we exit the scene. */
	@Override
	public void onExit(Integer nextScene) {
		/** First we call the game.objects onExit method */
		objects.onExit(nextScene);
		
		/** The we push the stats to the save file */
		Statistics.get().release();
		audio.setVolume(0F, 0F);
		
		/** If we exit back to the main menu or the game over state we reset the game */
		if(nextScene == MainActivity.GAMEOVER || nextScene == MainActivity.MENU) {
			((UpgradeState) SceneManager.get().getScene(MainActivity.UPGRADE)).reset();
			reset(nextScene);
		}
	}
	
	/** Simply resets all variables to there initial state */
	private void reset(int id) {
		Enemies.STARTING_ENEMIES = 5;
		Enemy.SPEED = 3.0F;
		objects.Reset(id);
		completed = false;
		waveNumber = 1;
		alpha = 1F;
	}
}
