package com.game.events;

import com.framework.IEvent;
import com.framework.IUiEvent;
import com.framework.core.SceneManager;
import com.framework.dialogs.MessageBox;
import com.game.activity.MainActivity;
import com.game.common.Statistics;

public class ResetGame implements IEvent, IUiEvent {
    private static final SceneManager scenes = SceneManager.get();
    private static final String MESSAGE = "Are you sure you want to reset your stats";
    private static final String TITLE = "reset statistics ?";

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

    }
}
