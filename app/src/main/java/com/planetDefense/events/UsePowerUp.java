package com.planetDefense.events;

import com.framework.IEvent;
import com.planetDefense.actors.powerups.PowerUp;

public class UsePowerUp implements IEvent {
    private PowerUp powerUp;

    public UsePowerUp(PowerUp powerUp) {
        changePowerUp(powerUp);
    }

    public void changePowerUp(PowerUp p) {
        powerUp = p;
    }

    @Override
    public void onActivate(Object data) {
        powerUp.onPickUp();
    }

    @Override
    public void update() {

    }
}
