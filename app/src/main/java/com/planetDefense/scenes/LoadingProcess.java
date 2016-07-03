package com.planetDefense.scenes;

import com.framework.IFactory;
import com.framework.audio.AudioClip;
import com.framework.core.SceneManager;
import com.framework.graphics.Button;
import com.framework.graphics.Font;
import com.framework.graphics.Image;
import com.framework.graphics.Label;
import com.planetDefense.activity.MainActivity;
import com.planetDefense.common.Statistics;

public class LoadingProcess {
	private IFactory factory;
	private SceneManager sceneMgr;

	public LoadingProcess(IFactory f) {	
		this.sceneMgr = SceneManager.get();
		this.factory = f;
	}

	public void setupAssets() {
		try {
			Font medium = new Font("xml/MediumText.xml", "sprites/MediumText.png", "medium");
			Font large = new Font("xml/largeText.xml", "sprites/largeText.png", "large");
			Font small = new Font("xml/SmallText.xml", "sprites/smallText.png", "small");
			Font tiny = new Font("xml/text.xml", "sprites/tinyText.png", "tiny");

			if(Statistics.get().isFirstTimePlaying()) {
				// if so start at the tutorial 
				sceneMgr.startFrom(MainActivity.TUTORIAL);
			} else {
				// otherwise start at the menu
				sceneMgr.startFrom(MainActivity.MENU);
			} 

			Label text = new Label(large);
			text.text("Planet Defense");
			text.load(0, 0);
			text.translate(640 - text.getWidth() / 2, 600);
				
			/** Load the back button for the game */
			Button backButton = new Button(medium);
			backButton.setText("Back", 100, 670, 300, 100);
		
			/** Load the menu music */
			AudioClip audio = new AudioClip(com.planetDefense.R.raw.level);
			audio.setVolume(1.0F, 1.0F);
			audio.setLoop(true);
			
			Image background = new Image("sprites/menu.bmp");
			background.setPosition(0, 0, 1280, 800);
		
			/** Stack all shared assets into the com.framework factory */
			factory.stack(background, "MenuBackground");
			factory.stack(backButton, "BackButton");
			factory.stack(audio, "BackgroundMusic");
			factory.stack(text, "MenuText");
			
			/** A stack the MainObjects Container */
			factory.stackContainer(new MainObjects(), "LevelObjects");
		} catch(Exception e) {
			System.err.println(e.getLocalizedMessage());
			System.err.println(e.getMessage());
		}
	}
}
