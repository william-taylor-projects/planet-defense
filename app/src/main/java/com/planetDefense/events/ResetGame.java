package com.planetDefense.events;

import com.framework.*;
import com.framework.core.SceneManager;
import com.framework.dialogs.MessageBox;
import com.planetDefense.activity.MainActivity;
import com.planetDefense.objects.Statistics;

public class ResetGame implements IEvent, IUiEvent {
	private static final String MESSAGE = "Are you sure you want to reset your stats";
	private static final String TITLE = "Reset statistics ?";

	private static final SceneManager scenes = SceneManager.get();

	@Override
	public void onActivate(Object data) {
		MessageBox message = new MessageBox();
		message.setMessage(MESSAGE);
		message.setTitle(TITLE);
		message.onAccept(this);
		message.EnableYesNo();
		message.show(false);
	}

	@Override
	public void onUiEvent() {
		Statistics.get().resetStats();
		scenes.switchTo(MainActivity.MENU);
	}

	@Override
	public void update() {
		;
	}
}
