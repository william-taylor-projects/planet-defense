package com.planetDefense.scenes;

import android.view.MotionEvent;

import com.framework.IFactory;
import com.framework.core.ClickEvent;
import com.framework.core.EventManager;
import com.framework.core.Scene;
import com.framework.graphics.Button;
import com.framework.graphics.Font;
import com.framework.graphics.Image;
import com.framework.graphics.Label;
import com.framework.graphics.Paragraph;
import com.framework.graphics.RenderQueue;
import com.planetDefense.activity.MainActivity;
import com.planetDefense.events.StateClick;

public class InformationScene extends Scene {
	private static final String CREDITS_FILE = "strings/credits.txt";
	private static final String CREATOR_FILE = "strings/makers.txt";

	private Paragraph creators;
	private Paragraph credits;
	private Label headerText;
	private Button backButton;
	private Image background;
	private ClickEvent event;

	@Override
	public void onCreate(IFactory factory) {
        Font e = Font.get("large");
		Font t = Font.get("tiny");

		background = factory.request("MenuBackground");
		backButton = factory.request("BackButton");

		event = new ClickEvent(backButton);
		event.eventType(new StateClick(MainActivity.MENU));
		EventManager.get().addListener(event);
		
		/** Setup the header for the scene */
		headerText = new Label(e, "Credits");
		headerText.load(500, 570);
		
		/** Setup both paragraphs */
		creators = new Paragraph(t, CREATOR_FILE);
		credits = new Paragraph(t, CREDITS_FILE);
		creators.load(800, 250);
		credits.load(150, 225);
	}

	@Override
	public void onRender(RenderQueue renderList) {
		renderList.put(background);
		renderList.put(headerText);
        renderList.put(creators);
		renderList.put(credits);
		renderList.put(backButton);
    }

	@Override
	public void onUpdate() {
		background.update();
		headerText.update();
		backButton.update();
		creators.update();
		credits.update();
	}

	@Override
	public void onTouch(MotionEvent e, int x, int y) {
		event.OnTouch(e, x, y);
	}
}
  