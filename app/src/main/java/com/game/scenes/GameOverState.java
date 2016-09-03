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
import com.game.activity.MainActivity;
import com.game.actors.hud.WaveNumber;
import com.game.events.StateClick;

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
		background = factory.request("MenuBackground");
		backButton = factory.request("BackButton");
		objects = factory.request("LevelObjects");

		textItems = new GameOverText();
		textItems.initialise(factory);

		clickEvent = new ClickEvent(backButton);
		clickEvent.eventType(new StateClick(MainActivity.MENU));
		EventManager.get().addListener(clickEvent);
	}

	@Override
	public void onEnter(Integer previousScene) {
		objects.onEnter();
	}

	@Override
	public void onExit(Integer nextScene) {
		objects.Reset(MainActivity.MENU);
		textItems.reset();
	}

	@Override
	public void onRender(RenderQueue renderList) {
		renderList.put(background);
		renderList.put(backButton);

		textItems.onRender(renderList);
	}

	@Override
	public void onUpdate() {
		background.update();
		textItems.update();		
		backButton.update();
	}

	@Override
	public void onTouch(MotionEvent e, int x, int y) {
		clickEvent.OnTouch(e, x, y);
	}

	public class GameOverText {
		private WaveNumber waveNumber;
		private Label headerText;
		private Label tipText;
		private Label waveText;
		private Label gradeText;
		private Label grade;

		public void initialise(IFactory factory) {
			Font s = Font.get("medium");
            Font e = Font.get("large");
            Font t = Font.get("tiny");

			waveNumber = factory.request("WaveText");
			headerText = new Label(e, HEADER);
			gradeText = new Label(s, GRADE);
			waveText = new Label(s, WAVE);
			tipText = new Label(t, TIP);
			grade = new Label(e);

			headerText.load(385, 600);
			gradeText.load(800, 400);
			waveText.load(150, 400);

			grade.text(" N/A  ");
			grade.load(890, 200);

			tipText.load(0, 0);
			tipText.setInitialPosition(640 - tipText.getWidth() / 2, 0);
		}

		public void reset() {
			grade.text("N/A");
			grade.load(890, 200);
		}

		public void update() {
			waveNumber.SetCenter();

			if(grade.getText().compareToIgnoreCase(waveNumber.asGrade()) != 0) {
				grade.text(waveNumber.asGrade());
				grade.load(0, 0);
				grade.setInitialPosition(925 - grade.getWidth() / 2, 200);

				String c = waveNumber.asColour();

				if(c.compareToIgnoreCase("ORANGE") == 0) grade.setColour(.5f, .2f, 0f, 1f);
				if(c.compareToIgnoreCase("YELLOW") == 0) grade.setColour(.5f, .5f, 0f, 1f);
				if(c.compareToIgnoreCase("GREEN") == 0) grade.setColour(0f, 1f, 0f, 1f);
				if(c.compareToIgnoreCase("BLUE") == 0) grade.setColour(0f, 0f, 1f, 1f);
				if(c.compareToIgnoreCase("GOLD") == 0) grade.setColour(1f, 1f, 0f, 1f);
				if(c.compareToIgnoreCase("RED") == 0) grade.setColour(1f, 0f, 0f, 1f);
			}

			waveNumber.Update();
			headerText.update();
			gradeText.update();
			waveText.update();
			tipText.update();
			grade.update();
		}


		public void onRender(RenderQueue renderList) {
			renderList.put(waveNumber.getWaveText());
			renderList.put(headerText);
			renderList.put(gradeText);
			renderList.put(waveText);
			renderList.put(tipText);
			renderList.put(grade);
		}
	}
}
