package com.planetDefense.events;

import com.framework.IEvent;
import com.planetDefense.objects.PowerUp;

public class UsePowerUp implements IEvent {
	private PowerUp powerUp;

	public UsePowerUp(PowerUp powerUp) {
		ChangePowerUp(powerUp);
	}

	public void ChangePowerUp(PowerUp p) {
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
