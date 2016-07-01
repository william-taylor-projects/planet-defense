package com.planetDefense.events;

import com.framework.IEvent;
import com.planetDefense.objects.*;

public class EarthDeath implements IEvent {
	private Earth earth;
	private Ship ship;

	public EarthDeath(Earth earth, Ship ship) {
		this.earth = earth;
		this.ship = ship;
	}

	@Override
	public void onActivate(Object data) {
		earth.kill();
		ship.Kill();
	}

	@Override
	public void update() {

	}
}