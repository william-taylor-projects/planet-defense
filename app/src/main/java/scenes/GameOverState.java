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
import objects.WaveNumber;
import events.StateClick;

/**
 *  Game over state which displays the
 *  players result
 *  
 * @version : final version for release
 * @author : William Taylor
 */
public class GameOverState extends Scene {
	/** some final strings that change the text displayed in the scene */
	private static final String TIP = "Tip : One should buy perks first and ships second.";
	private static final String GRADE = "Your Grade";
	private static final String WAVE = "Waves Survivied";
	private static final String HEADER = "Game Over";
	
	/** Inner classes instance for the text items that the scene will use */
	private GameOverText textItems;
	
	/** A reference to the back button that is drawn for the user */
	private GameButton backButton;
	
	/** A reference to the background for the scene */
	private GameImage background;
	
	/** A reference to the objects in the main scene so we can reset them */
	private MainObjects objects;
	
	/** the event that is fired when the back button is pressed */
	private Click clickEvent;
		
	/** onCreate method is called when we need to load the assets for this scene */
	@Override
	public void onCreate(IFactory factory) {
		/** Request the objects we need that are stored in the factory */
		background = factory.Request("MenuBackground");
		backButton = factory.Request("BackButton");
		objects = factory.Request("LevelObjects");
		
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
		/** onEnter method prepares all objects for a new game*/
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
		renderList.pushRenderable(background);
		renderList.pushRenderable(backButton);
		
		/** then call the inner classes onRender Method  */
		textItems.onRender(renderList);
	}

	/** Called before the render function to alter object positions */
	@Override
	public void onUpdate() {
		/** Update the positions of the background text elements etc */
		background.update();
		textItems.update();		
		backButton.Update();
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
		private LabelLine headerText;
		
		/** The drawable element that shows a tip */
		private LabelLine tipText;
		
		/** The drawable element that shows what the wave number represents */
		private LabelLine waveText;
		
		/** The drawable element  that shows what the grade character represents */
		private LabelLine gradeText;
		
		/** The drawable element that shows the users grade */
		private LabelLine grade;
		
		/**
		 * Initialise function just sets up the obejct ready for
		 * immediate use.
		 * 
		 * @param factory a reference to the frameworks factory
		 */
		public void initialise(IFactory factory) {
			/** Get all the fonts needed for all text elements */
			LabelEngine s = LabelEngine.Get("medium");
			LabelEngine e = LabelEngine.Get("large");
			LabelEngine t = LabelEngine.Get("tiny");
			
			/** Request this object from the factory */
			waveNumber = factory.Request("WaveText");
			
			/** Initialise objects */
			headerText = new LabelLine(e, HEADER);
			gradeText = new LabelLine(s, GRADE);
			waveText = new LabelLine(s, WAVE);
			tipText = new LabelLine(t, TIP);
			grade = new LabelLine(e);
			
			/** Load text at the following positions */
			headerText.Load(385, 600);
			gradeText.Load(800, 400);
			waveText.Load(150, 400);
			
			grade.Text(" N/A  ");
			grade.Load(890, 200);
		
			/** Centre the tip text at the centre of the screen */
			tipText.Load(0, 0);
			tipText.SetInitialPosition(640 - tipText.GetWidth()/2, 0);
		}
		
		/** Called when the current scene is exited to reset text elements */
		public void reset() {
			grade.Text("N/A");
			grade.Load(890, 200);
		}

		public void update() {
			/** Translate waveNumber to the new position */
			waveNumber.SetCenter();
			
			/** If the grade text doesnt match the grade the user got we need to reload it */
			if(grade.getText().compareToIgnoreCase(waveNumber.asGrade()) != 0) {
				/** Load the new grade as a string */
				grade.Text(waveNumber.asGrade());
				grade.Load(0, 0);
				grade.SetInitialPosition(925 - grade.GetWidth()/2, 200);

				/** Shade the text dependant on the grade achieved */
				String c = waveNumber.asColour();
				
				/** Compare strings and set the right colour */
				if(c.compareToIgnoreCase("ORANGE") == 0) grade.SetColour(.5f, .2f, 0f, 1f);
				if(c.compareToIgnoreCase("YELLOW") == 0) grade.SetColour(.5f, .5f, 0f, 1f);
				if(c.compareToIgnoreCase("GREEN") == 0) grade.SetColour(0f, 1f, 0f, 1f);
				if(c.compareToIgnoreCase("BLUE") == 0) grade.SetColour(0f, 0f, 1f, 1f);
				if(c.compareToIgnoreCase("GOLD") == 0) grade.SetColour(1f, 1f, 0f, 1f); 
				if(c.compareToIgnoreCase("RED") == 0) grade.SetColour(1f, 0f, 0f, 1f);
			}
			
			
			/** Update the position of all text items */
			waveNumber.Update();
			headerText.Update();
			gradeText.Update();
			waveText.Update();
			tipText.Update();
			grade.Update();
		}

		
		/** Simply pushes all text elements to the render queue */
		public void onRender(RenderQueue renderList) {
			/** order here doesnt matter as the background has already been inserted */
			renderList.pushRenderable(waveNumber.Text);
			renderList.pushRenderable(headerText);
			renderList.pushRenderable(gradeText);
			renderList.pushRenderable(waveText);
			renderList.pushRenderable(tipText);
			renderList.pushRenderable(grade);
		}
	}
}
