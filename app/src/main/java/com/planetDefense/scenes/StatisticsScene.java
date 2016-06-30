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
import com.planetDefense.Statistics.Statistic;
import android.view.MotionEvent;

import com.planetDefense.events.ResetGame;
import com.planetDefense.events.StateClick;

/**
 *  This is the statistics scene which keeps track
 *  of all the important records.
 *  
 * @version : final version for release
 * @author : William Taylor
 */
public class StatisticsScene extends Scene {
	/** A reference to all the game.scenes images */
	private StatisticObjects sceneImages;
	
	/** A reference to all the scene text elements */
	private StatisticText sceneText;

	/** Called when the scene is created */
	@Override
	public void onCreate(IFactory factory) {
		/** setup and init game.objects */
		sceneImages = new StatisticObjects();
		sceneText = new StatisticText();
		sceneImages.initialise(factory);
		sceneText.initialise(factory);
	}
	
	/** Called just before we render to update positions */
	@Override
	public void onUpdate() {
		/** Just call the classes update functions */
		sceneImages.update();
		sceneText.update();
	}
	
	/** Called when the scene is to be rendered */
	@Override
	public void onRender(RenderQueue renderList) {
		/** Again call the classes onRender functions */
		sceneImages.onRender(renderList);
		sceneText.onRender(renderList);
	}
	
	/** Called when the user enters this state */
	@Override
	public void onEnter(Integer nextScene) {
		sceneText.onEnter();
	}
	
	/** Called when the user touches the screen */
	@Override
	public void onTouch(MotionEvent e, int x, int y) {
		/** Just call the onTouch event for the images */
		sceneImages.onTouch(e, x, y);
	}
	

	/**
	 * Inner class that controls all the main text
	 * elements in the current scene.
	 * 
	 * @author William Taylor
	 */
	public class StatisticText {
		/** The users wave record as a drawable texture */
		private LabelLine waveRecord;
		
		/** The users total kills as a drawable texture */
		private LabelLine totalKills;
		
		/** The users total waves as a drawable texture */
		private LabelLine totalWaves;
		
		/** The users total games as a drawable texture */
		private LabelLine totalGames;
		
		/** The users wave information as a drawable texture */
		private LabelLine waveInfo;
		
		/** The users wave record as a drawable texture */
		private LabelLine record;
		
		/** The title drawable texture */
		private LabelLine header;
		
		/** The update function which just updates each element */
		public void update() {
			/** Update all strings we dont really need to do anything else */
			totalKills.Update();		
			totalWaves.Update();
			totalGames.Update();
			waveRecord.Update();
			waveInfo.Update();
			header.Update();
			record.Update();
		}
		
		/** onRender Function which just adds each label to the queue */
		public void onRender(RenderQueue renderList) {
			renderList.pushRenderable(totalKills);
			renderList.pushRenderable(totalWaves);
			renderList.pushRenderable(totalGames);
			renderList.pushRenderable(waveRecord);
			renderList.pushRenderable(waveInfo);
			renderList.pushRenderable(header);
			renderList.pushRenderable(record);
		}
	
		/** initialise function just sets up this object */
		public void initialise(IFactory factory) {
			/** get the stats from the stats file on disk */
			Statistic Stats = Statistics.get().readReadStats();
			
			/** get fonts that we have already  */
			LabelEngine medium = LabelEngine.Get("medium");
			LabelEngine small = LabelEngine.Get("small");
			LabelEngine large = LabelEngine.Get("large");
			
			/** Setup the labels and load them at the right position */
			header = new LabelLine(large, "Your Statistics");
			waveRecord  = new LabelLine(large, "" + Stats.levels);
			waveInfo = new LabelLine(medium, "Your Record");
			record = new LabelLine(medium, "Combat Record");
			
			totalGames = new LabelLine(small, "Total Games Played : " + Stats.totalGames);
			totalWaves = new LabelLine(small, "Total Waves Played : "+ Stats.totalWaves);
			totalKills = new LabelLine(small, "Total Kills : " + Stats.killCount);
			
			header.Load(600, 575);
			header.Translate(600 - (header.GetWidth()/2), 575);
			
			waveInfo.Load(805, 400);
			record.Load(180, 400);
			
			waveInfo.Scale(0.35f, 0.35f);
			record.Scale(0.35f, 0.35f);
			
			totalKills.Load(250, 100);
			totalWaves.Load(250, 200);
			totalGames.Load(250, 300);
			waveRecord.Load(875, 200);
			
			totalKills.Translate(350 - (totalKills.GetWidth()/2), 100);
			totalWaves.Translate(350 - (totalWaves.GetWidth()/2), 200);
			totalGames.Translate(350 - (totalGames.GetWidth()/2), 300);
			waveRecord.Translate(950 - (waveRecord.GetWidth()/2), 200);	
		}
		
		/** called so we enter the scene this function should be called */
		public void onEnter() {
			/** re-read the stats */
			Statistic Stats = Statistics.get().readReadStats();
			
			/** reload the text elements after that */
			totalKills.Text("Total Kills : " + Stats.killCount);
			totalWaves.Text("Total Waves Played : "+ Stats.totalWaves);
			totalGames.Text("Total Games Played : " + Stats.totalGames);
			waveRecord.Text("" + Stats.levels);
			
			/** Load them at the current poisitions */
			totalKills.Load(300, 100);
			totalWaves.Load(300, 200);
			totalGames.Load(300, 300);
			waveRecord.Load(875, 200);
			
			/** The centre them at this position */
			totalKills.Translate(350 - (totalKills.GetWidth()/2), 100);
			totalWaves.Translate(350 - (totalWaves.GetWidth()/2), 200);
			totalGames.Translate(350 - (totalGames.GetWidth()/2), 300);
			waveRecord.Translate(950 - (waveRecord.GetWidth()/2), 200);	
		}
	}
	

	/**
	 * This is a inner class again however
	 * it controls the buttons and images.
	 *  
	 * @author William Taylor
	 */
	public class StatisticObjects {
		/** This is a reference to the reset button texture */
		private GameButton resetButton;
		
		/** This is a reference to the back button texture */
		private GameButton backButton;
		
		/** This is a reference background texture */
		private GameImage background;
		
		/** This is the event to be fired when the back button has been pressed */
		private Click clickEvent;
		
		/** This is the event to be fired when the reset button has been pressed */
		private Click resetEvent;
		
		/** The function will handle touch game.events*/
		public void onTouch(MotionEvent e, int x, int y) {
			/** & send them to the button game.events */
			if(e.getAction() == MotionEvent.ACTION_DOWN) {
				clickEvent.OnTouch(e, x, y);
				resetEvent.OnTouch(e, x, y);
			}
		}
	
		/** update function which will update the game.objects */
		public void update() {
			resetButton.Update();
			background.update();
			backButton.Update();
		}
	
		/** onRender will add all the renderable game.objects to the queue */
		public void onRender(RenderQueue renderList) {
			/** we make sure we push them in the right order */
			renderList.pushRenderable(background);
			renderList.pushRenderable(resetButton);
			renderList.pushRenderable(backButton);
		}
	
		/** initialise function loads all the game.objects ready for updating and drawing*/
		public void initialise(IFactory factory) {
			/** get the background and back button from the com.framework factory */
			background = factory.Request("MenuBackground");
			backButton = factory.Request("BackButton");
			
			/** create and setup the reset button */
			resetButton = new GameButton(LabelEngine.Get("medium"));
			resetButton.SetText("Reset Stats", 950, 75, 300, 75);
			
			/** Setup the back and reset game.events */
			resetEvent = new Click(resetButton);
			clickEvent = new Click(backButton);
			resetEvent.eventType(new ResetGame());
			clickEvent.eventType(new StateClick(1));
			
			/** Register the listeners with the event manager */
			EventManager.get().addListener(resetEvent);
			EventManager.get().addListener(clickEvent);
		}
	}
}
