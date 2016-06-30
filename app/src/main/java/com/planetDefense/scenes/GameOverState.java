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
import com.planetDefense.activity.MainActivity;
import com.planetDefense.objects.WaveNumber;
import com.planetDefense.events.StateClick;


public class GameOverState extends Scene {
	private static final String TIP = "Tip : One should buy perks first and ships second.";
	private static final String GRADE = "Your Grade";
	private static final String WAVE = "Waves Survivied";
	private static final String HEADER = "Game Over";

	private GameOverText textItems;
	private Button backButton;
	private Image background;
	private MainObjects objects;
	private ClickEvent clickEvent;

	@Override
	public void onCreate(IFactory factory) {
		/** Request the game.objects we need that are stored in the factory */
		background = factory.request("MenuBackground");
		backButton = factory.request("BackButton");
		objects = factory.request("LevelObjects");
		
		/** Initialise the inner class */
		textItems = new GameOverText();
		textItems.initialise(factory);
		
		/** Setup the back button event & register it with the EventManager */
		clickEvent = new Click(backButton);
		clickEvent.eventType(new StateClick(MainActivity.MENU));
		EventManager.get().addListener(clickEvent);
	}

	/** Called when we enter the scene */
	@Override
	public void onEnter(Integer previousScene) {
		/** onEnter method prepares all game.objects for a new game*/
		objects.onEnter();
	}
	
	/** Called when we exit the scene */
	@Override
	public void onExit(Integer nextScene) {
		/** Reset resets the UI in the main scene */
		objects.Reset(MainActivity.MENU);
		
		/** reset the text items for when we return */
		textItems.reset();
	}
	
	/** Called when the scene is going to be rendered */
	@Override
	public void onRender(RenderQueue renderList) {
		/** simple add the background and the back button to the queue */
		renderList.put(background);
		renderList.put(backButton);

        /** then call the inner classes onRender Method  */
		textItems.onRender(renderList);
	}

	/** Called before the render function to alter object positions */
	@Override
	public void onUpdate() {
		/** Update the positions of the background text elements etc */
		background.update();
		textItems.update();		
		backButton.update();
	}
	
	/** Called when the user touches the screen */
	@Override
	public void onTouch(MotionEvent e, int x, int y) {
		clickEvent.OnTouch(e, x, y);
	}
	
	/**
	 * GameOverText : inner class that manages all the text
	 * elements in the current scene.
	 * 
	 * @author William Taylor
	 */
	public class GameOverText {
		/** The drawable element that shows what wave the user got to */
		private WaveNumber waveNumber;
		
		/** The drawable element that is the title for the scene */
		private Label headerText;
		
		/** The drawable element that shows a tip */
		private Label tipText;
		
		/** The drawable element that shows what the wave number represents */
		private Label waveText;
		
		/** The drawable element  that shows what the grade character represents */
		private Label gradeText;
		
		/** The drawable element that shows the users grade */
		private Label grade;
		public void initialise(IFactory factory) {
			/** Get all the fonts needed for all text elements */
			Font s = Font.get("medium");
            Font e = Font.Get("large");
            Font t = Font.Get("tiny");

			/** Request this object from the factory */
			waveNumber = factory.request("WaveText");

			/** Initialise game.objects */
			headerText = new Label(e, HEADER);
			gradeText = new Label(s, GRADE);
			waveText = new Label(s, WAVE);
			tipText = new Label(t, TIP);
			grade = new Label(e);

			/** Load text at the following positions */
			headerText.load(385, 600);
			gradeText.load(800, 400);
			waveText.load(150, 400);

			grade.text(" N/A  ");
			grade.load(890, 200);

			/** Centre the tip text at the centre of the screen */
			tipText.load(0, 0);
			tipText.setInitialPosition(640 - tipText.getWidth() / 2, 0);
		}
		
		/** Called when the current scene is exited to reset text elements */
		public void reset() {
			grade.text("N/A");
			grade.load(890, 200);
		}

		public void update() {
			/** Translate waveNumber to the new position */
			waveNumber.SetCenter();

			/** If the grade text doesnt match the grade the user got we need to reload it */
			if(grade.getText().compareToIgnoreCase(waveNumber.asGrade()) != 0) {
				/** Load the new grade as a string */
				grade.text(waveNumber.asGrade());
				grade.load(0, 0);
				grade.setInitialPosition(925 - grade.getWidth() / 2, 200);

				/** Shade the text dependant on the grade achieved */
				String c = waveNumber.asColour();

				/** Compare strings and set the right colour */
				if(c.compareToIgnoreCase("ORANGE") == 0) grade.setColour(.5f, .2f, 0f, 1f);
				if(c.compareToIgnoreCase("YELLOW") == 0) grade.setColour(.5f, .5f, 0f, 1f);
				if(c.compareToIgnoreCase("GREEN") == 0) grade.setColour(0f, 1f, 0f, 1f);
				if(c.compareToIgnoreCase("BLUE") == 0) grade.setColour(0f, 0f, 1f, 1f);
				if(c.compareToIgnoreCase("GOLD") == 0) grade.setColour(1f, 1f, 0f, 1f);
				if(c.compareToIgnoreCase("RED") == 0) grade.setColour(1f, 0f, 0f, 1f);
			}


			/** Update the position of all text items */
			waveNumber.Update();
			headerText.update();
			gradeText.update();
			waveText.update();
			tipText.update();
			grade.update();
		}

		
		/** Simply pushes all text elements to the render queue */
		public void onRender(RenderQueue renderList) {
			/** order here doesnt matter as the background has already been inserted */
			renderList.put(waveNumber.Text);
			renderList.put(headerText);
			renderList.put(gradeText);
			renderList.put(waveText);
			renderList.put(tipText);
			renderList.put(grade);
		}
	}
}
