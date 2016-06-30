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

import com.planetDefense.activity.MainActivity;

/**
 *  The loading process that is started
 *  in the LoadScreen state
 *  
 * @version : final version for release
 * @author : William Taylor
 */
public class LoadingProcess {
	/** A reference to the frameworks factory */
	private IFactory factory;
	
	/** A reference to the scene manager */
	private SceneManager sceneMgr;
	
	/** The basic constructor which just takes the factory as a reference */
	public LoadingProcess(IFactory f) {	
		this.sceneMgr = SceneManager.get();
		this.factory = f;
	}
	
	/** setupAssets function that loads all shared assets */
	public void setupAssets() {
		try {
			/** Load all fonts needed for the game */
			LabelEngine medium = new LabelEngine("xml/MediumText.xml", "sprites/MediumText.png", "medium");
			LabelEngine large = new LabelEngine("xml/largeText.xml", "sprites/largeText.png", "large");
			
				/** LabelEngine class catches its own instance to store multiple fonts for re-se */
				new LabelEngine("xml/SmallText.xml", "sprites/smallText.png", "small");
				new LabelEngine("xml/text.xml", "sprites/tinyText.png", "tiny");
			
			/** Load file and see if the game has been played before */
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
