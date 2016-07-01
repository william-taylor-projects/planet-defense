package com.planetDefense.scenes;

import com.framework.IFactory;
import com.framework.audio.AudioClip;
import com.framework.core.SceneManager;
import com.framework.graphics.Font;
import com.planetDefense.activity.MainActivity;

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
				sceneMgr.StartFrom(MainActivity.TUTORIAL);
			} else {
				// otherwise start at the menu
				sceneMgr.StartFrom(MainActivity.MENU);
			} 
			
			/** Load the games title */
			LabelLine text = new LabelLine(large);
			text.Text("Planet Defense");
			text.Load(0, 0);
			text.Translate(640 - text.GetWidth()/2, 600);
				
			/** Load the back button for the game */
			GameButton backButton = new GameButton(medium);
			backButton.SetText("Back", 100, 670, 300, 100);
		
			/** Load the menu music */
			AudioClip audio = new AudioClip(com.planetDefense.R.raw.level);
			audio.setVolume(1.0F, 1.0F);
			audio.setLoop(true);
			
			GameImage background = new GameImage("sprites/menu.bmp");
			background.setPosition(0, 0, 1280, 800);
		
			/** Stack all shared assets into the com.framework factory */
			factory.Stack(background, "MenuBackground");
			factory.Stack(backButton, "BackButton");
			factory.Stack(audio, "BackgroundMusic");
			factory.Stack(text, "MenuText");
			
			/** A stack the MainObjects Container */
			factory.StackContainer(new MainObjects(), "LevelObjects");
		} catch(Exception e) {
			System.err.println(e.getLocalizedMessage());
			System.err.println(e.getMessage());
		}
	}
}
