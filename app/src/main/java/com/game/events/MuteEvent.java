package com.game.events;

import com.framework.IEvent;
import com.framework.audio.AudioClip;

public class MuteEvent implements IEvent {
    @Override
    public void onActivate(Object data) {
        if(AudioClip.masterVolume > 0.0f) {
            AudioClip.masterVolume = 0.0f;
        } else {
            AudioClip.masterVolume = 1.0f;
        }
    }

    @Override
    public void update() {

    }
}
