package com.planetDefense.events;

import com.framework.IEvent;
import com.framework.opengl.OpenglImage;
import com.planetDefense.objects.*;

public class BulletCollision implements IEvent {
	private Explosion explosion;
	private Missiles missiles;
	private Integer number;
	private Enemy enemy;
	private Ship ship;

	public void provideData(Missiles missiles, Enemy enemy, Integer number, Ship ship) {
		this.missiles = missiles;
		this.number = number;
		this.enemy = enemy;
		this.ship = ship;
	}

	@Override
	public void onActivate(Object data) {
		if(missiles != null && enemy != null && ship != null) {
			OpenglImage sprite = missiles.GetMissile(number);

			float x = sprite.getPosition().getX() + sprite.getSize().getX()/2;
			float y = sprite.getPosition().getY() + sprite.getSize().getY()/2;

			explosion = enemy.getEffect();
			explosion.DrawAt(x, y);
			explosion.Reset();

			this.missiles.ResetMissile(number);
			this.enemy.resetObject();
			this.ship.AddCash();

			Statistics.get().enemyDown();
			sprite.reset();
		}
	}

	@Override
	public void update() {
		;
	}
}
