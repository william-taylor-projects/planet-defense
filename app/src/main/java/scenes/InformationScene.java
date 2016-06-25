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
import events.StateClick;

/**
 *  The information state which just shows
 *  who made the game and a little msg for
 *  the user.
 *  
 * @version : final version for release
 * @author : William Taylor
 */
public class InformationScene extends Scene {
	private static final String CREDITS_FILE = "strings/credits.txt";
	private static final String CREATOR_FILE = "strings/makers.txt";
	
	/** The drawable paragraph which shows the creators of the app  */
	private LabelParagraph creators;
	
	/** The drawable paragraph which shows the credits  */
	private LabelParagraph credits;
	
	/** The header/title for the scene */
	private LabelLine headerText;
	
	/** The back button which returns the user to the main menu */
	private GameButton backButton;
	
	/** The background for the scene */
	private GameImage background;
	
	/** The event that is fired by the backbutton */
	private Click event;

	/** Called when we are creating the scene */
	@Override
	public void onCreate(IFactory factory) {
		/** Get the fonts needed for the strings */
		LabelEngine e = LabelEngine.Get("large");
		LabelEngine t = LabelEngine.Get("tiny");
		
		/** Get these assets from the framework factory */
		background = factory.Request("MenuBackground");
		backButton = factory.Request("BackButton");
		
		/** Setup the event so when the backButton is pressed we return to the menu */
		event = new Click(backButton);
		event.eventType(new StateClick(MainActivity.MENU));
		EventManager.get().addListener(event);	
		
		/** Setup the header for the scene */
		headerText = new LabelLine(e, "Credits");
		headerText.Load(500, 570);
		
		/** Setup both paragraphs */
		creators = new LabelParagraph(t, CREATOR_FILE);
		credits = new LabelParagraph(t, CREDITS_FILE);
		creators.load(800, 250);
		credits.load(150, 225);
	}
	
	/** called when the scene needs to be rendered.*/
	@Override
	public void onRender(RenderQueue renderList) {
		/** Push all items we want rendered to the renderList queue */
		renderList.pushRenderable(background);
		renderList.pushRenderable(headerText);
		renderList.pushRenderable(creators);
		renderList.pushRenderable(credits);
		renderList.pushRenderable(backButton);
	}
	
	/** Called before the scene is drawn to update the positions of objects */
	@Override
	public void onUpdate() {
		/** Update the positions off all objects */
		background.update();
		headerText.Update();
		backButton.Update();
		creators.update();
		credits.update();
	}
	
	/** Called when a touch event has occured  */
	@Override
	public void onTouch(MotionEvent e, int x, int y) {
		/** We pass the data to the event */
		event.OnTouch(e, x, y);
	}
}
  