package com.game.scenes;

import android.view.MotionEvent;

import com.framework.IFactory;
import com.framework.core.ClickEvent;
import com.framework.core.EventManager;
import com.framework.core.Scene;
import com.framework.graphics.Button;
import com.framework.graphics.Font;
import com.framework.graphics.Image;
import com.framework.graphics.Label;
import com.framework.graphics.RenderQueue;
import com.game.events.ResetGame;
import com.game.events.StateClick;
import com.game.common.Statistics;

public class StatisticsScene extends Scene {
	private StatisticObjects sceneImages;
	private StatisticText sceneText;

	@Override
	public void onCreate(IFactory factory) {
		sceneImages = new StatisticObjects();
		sceneText = new StatisticText();
		sceneImages.initialise(factory);
		sceneText.initialise(factory);
	}

	@Override
	public void onUpdate() {
		sceneImages.update();
		sceneText.update();
	}

	@Override
	public void onRender(RenderQueue renderList) {
		sceneImages.onRender(renderList);
		sceneText.onRender(renderList);
	}

	@Override
	public void onEnter(Integer nextScene) {
		sceneText.onEnter();
	}

	@Override
	public void onTouch(MotionEvent e, int x, int y) {
		sceneImages.onTouch(e, x, y);
	}

	public class StatisticText {
		private Label waveRecord;
		private Label totalKills;
		private Label totalWaves;
		private Label totalGames;
		private Label waveInfo;
		private Label record;
		private Label header;

		public void update() {
			totalKills.update();
			totalWaves.update();
			totalGames.update();
			waveRecord.update();
			waveInfo.update();
			header.update();
			record.update();
		}

		public void onRender(RenderQueue renderList) {
			renderList.put(totalKills);
			renderList.put(totalWaves);
			renderList.put(totalGames);
			renderList.put(waveRecord);
			renderList.put(waveInfo);
			renderList.put(header);
			renderList.put(record);
		}
	
		/** initialise function just sets up this object */
		public void initialise(IFactory factory) {
			/** get the stats from the stats file on disk */
			Statistics.Statistic Stats = Statistics.get().readReadStats();
			
			/** get fonts that we have already  */
			Font medium = Font.get("medium");
			Font small = Font.get("small");
			Font large = Font.get("large");
			
			/** Setup the labels and load them at the right position */
			header = new Label(large, "Your Statistics");
			waveRecord  = new Label(large, "" + Stats.levels);
			waveInfo = new Label(medium, "Your Record");
			record = new Label(medium, "Combat Record");
			
			totalGames = new Label(small, "Total Games Played : " + Stats.totalGames);
			totalWaves = new Label(small, "Total Waves Played : "+ Stats.totalWaves);
			totalKills = new Label(small, "Total Kills : " + Stats.killCount);
			
			header.load(600, 575);
			header.translate(600 - (header.getWidth() / 2), 575);
			
			waveInfo.load(805, 400);
			record.load(180, 400);
			
			waveInfo.scale(0.35f, 0.35f);
			record.scale(0.35f, 0.35f);
			
			totalKills.load(250, 100);
			totalWaves.load(250, 200);
			totalGames.load(250, 300);
			waveRecord.load(875, 200);
			
			totalKills.translate(350 - (totalKills.getWidth() / 2), 100);
			totalWaves.translate(350 - (totalWaves.getWidth() / 2), 200);
			totalGames.translate(350 - (totalGames.getWidth() / 2), 300);
			waveRecord.translate(950 - (waveRecord.getWidth() / 2), 200);
		}
		
		/** called so we enter the scene this function should be called */
		public void onEnter() {
			/** re-read the stats */
			Statistics.Statistic Stats = Statistics.get().readReadStats();
			
			/** reload the text elements after that */
			totalKills.text("Total Kills : " + Stats.killCount);
			totalWaves.text("Total Waves Played : " + Stats.totalWaves);
			totalGames.text("Total Games Played : " + Stats.totalGames);
			waveRecord.text("" + Stats.levels);
			
			/** Load them at the current poisitions */
			totalKills.load(300, 100);
			totalWaves.load(300, 200);
			totalGames.load(300, 300);
			waveRecord.load(875, 200);
			
			/** The centre them at this position */
			totalKills.translate(350 - (totalKills.getWidth() / 2), 100);
			totalWaves.translate(350 - (totalWaves.getWidth()/2), 200);
			totalGames.translate(350 - (totalGames.getWidth() / 2), 300);
			waveRecord.translate(950 - (waveRecord.getWidth() / 2), 200);
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
		private Button resetButton;
		
		/** This is a reference to the back button texture */
		private Button backButton;
		
		/** This is a reference background texture */
		private Image background;
		
		/** This is the event to be fired when the back button has been pressed */
		private ClickEvent clickEvent;
		
		/** This is the event to be fired when the reset button has been pressed */
		private ClickEvent resetEvent;
		
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
			resetButton.update();
			background.update();
			backButton.update();
		}
	
		/** onRender will add all the renderable game.objects to the queue */
		public void onRender(RenderQueue renderList) {
			/** we make sure we push them in the right order */
			renderList.put(background);
			renderList.put(resetButton);
			renderList.put(backButton);
		}
	
		/** initialise function loads all the game.objects ready for updating and drawing*/
		public void initialise(IFactory factory) {
			/** get the background and back button from the com.framework factory */
			background = factory.request("MenuBackground");
			backButton = factory.request("BackButton");
			
			/** create and setup the reset button */
			resetButton = new Button(Font.get("medium"));
			resetButton.setText("reset Stats", 950, 75, 300, 75);
			
			/** Setup the back and reset game.events */
			resetEvent = new ClickEvent(resetButton);
			clickEvent = new ClickEvent(backButton);
			resetEvent.eventType(new ResetGame());
			clickEvent.eventType(new StateClick(1));
			
			/** Register the listeners with the event manager */
			EventManager.get().addListener(resetEvent);
			EventManager.get().addListener(clickEvent);
		}
	}
}
