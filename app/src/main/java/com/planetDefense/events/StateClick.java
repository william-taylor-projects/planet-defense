package com.planetDefense.events;

import com.framework.IEvent;
import com.framework.audio.AudioClip;
import com.framework.core.SceneManager;

public class StateClick implements IEvent {
    private static final SceneManager scenes = SceneManager.get();
    private static final Integer NONE_SELECTED = -1;
    private static final Float VOLUME = 0.25F;

    private Integer state = NONE_SELECTED;

    public StateClick(int i) {
        state = i;
    }

    @Override
    public void onActivate(Object data) {
        if(!state.equals(NONE_SELECTED)) {
            AudioClip audio = new AudioClip(com.planetDefense.R.raw.click);
            audio.play(VOLUME);
            scenes.switchTo(state);
        }
    }

    @Override
    public void update() {

    }
}
