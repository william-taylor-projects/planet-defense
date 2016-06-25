package scenes;

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
import com.planetDefense.*;
import objects.Enemys;
import objects.Enemy;

/**
 *  This is the main scene for the game.
 *  All its objects are stored in the Main
 *  Objects class for more clear code
 *  
 * @version : final version for release
 * @author : William Taylor
 */
public class MainScene extends Scene {
	/** A reference to all the main objects in this scene */
	private MainObjects objects;
	
	/** A label which renders when a wave has completed */
	private LabelLine waveDoneString;
	
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
		objects = factory.Request("LevelObjects");
		objects.Initialise(factory);
		
		/** get the audio from the factory & just init the variable */
		audio = factory.Request("BackgroundMusic"); 
		completed = false;
		
		waveDoneString = new LabelLine(LabelEngine.Get("large"), "Wave Completed");
		waveDoneString.Load(0, 0);
		waveDoneString.SetInitialPosition(640 - waveDoneString.GetWidth()/2, 325);
	}
	
	/** Called to update objects, positions etc */
	@Override
	public void onUpdate() {
		/** If there are no enemys the wave has completed */
		if(objects.getRemainingEnemys() == 0) {
			/** Doing this will indicate that the waveDoneString should be drawn */
			completed = true;
		} objects.Update();
		
		/** Play the audio this function also updates the volume */
		audio.Play();
		
		/** If the wave is over... */
		if(completed) {
			/** Do a fade out effect for the waveDoneString */
			alpha -= 0.005f;
			
			/** But only if the player survivde should is show & update the string */
			if(objects.Alive()) {
				waveDoneString.SetColour(1f, 1f, 1f, alpha);
				waveDoneString.Update();
			}
			
			/** When the fade out effect has finished we increase the difficulty */
			if(alpha <= 0.0F) {
				/** Increment the amount of enemys that spawn as well as increase the speed */
				if(Enemys.STARTING_ENEMYS + Enemys.INCREMENT_VALUE < Enemys.MAX) {
					Enemys.STARTING_ENEMYS += Enemys.INCREMENT_VALUE;
					Enemy.SPEED += Enemy.INCREMENT;
				}
				
				/** Update the statistics & call the objects to prepare for the next level */
				Statistics.get().newRecord(++waveNumber).waveDone();
				objects.NextLevel(waveNumber);
				
				/** Reset these values as well */
				completed = false;
				alpha = 1f;
			}
		} 
	}

	/** The render function which is called when we are to redraw the scene*/
	@Override
	public void onRender(RenderQueue renderList) {
		/** Call the objects onRender function first */
		objects.onRender(renderList);
		
		/** Then if a wave has been completed we show push the waveDoneString as well */
		if(completed) {
			renderList.pushRenderable(waveDoneString);
		} 
	}
	
	/** Called when the user touches the screen */
	@Override
	public void onTouch(MotionEvent e, int x, int y) {
		/** Just passes the data to the objects onTouch handler */
		objects.onTouch(e, x, y);
	}

	/** Called when the user enters the main scene */
	@Override
	public void onEnter(Integer previousScene) {
		/** First call the objects onEnter function handler */
		objects.onEnter();
		
		audio.Restart();
		audio.setVolume(1.0F, 1.0F);
	}
	
	/** Called when we exit the scene. */
	@Override
	public void onExit(Integer nextScene) {
		/** First we call the objects onExit method */
		objects.onExit(nextScene);
		
		/** The we push the stats to the save file */
		Statistics.get().release();
		audio.setVolume(0F, 0F);
		
		/** If we exit back to the main menu or the game over state we reset the game */
		if(nextScene == MainActivity.GAMEOVER || nextScene == MainActivity.MENU) {
			((UpgradeState)SceneManager.get().getScene(MainActivity.UPGRADE)).reset();
			reset(nextScene);
		}
	}
	
	/** Simply resets all variables to there initial state */
	private void reset(int id) {
		Enemys.STARTING_ENEMYS = 5;
		Enemy.SPEED = 3.0F;
		objects.Reset(id);
		completed = false;
		waveNumber = 1;
		alpha = 1F;
	}
}
