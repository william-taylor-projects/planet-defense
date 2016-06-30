package com.planetDefense.events;

import com.framework.IEvent;
import com.framework.opengl.OpenglImage;
import com.planetDefense.objects.*;

public class BulletCollision implements IEvent {
	private Explosion explosion;
	private Missiles missiles;
	private Enemy enemy;
	private Integer number;
	private Ship ship;

	public void provideData(Missiles missiles, Enemy enemy, Integer number, Ship ship) {
		this.missiles = missiles;
		this.number = number;
		this.enemy = enemy;
		this.ship = ship;
	}

	@Override
	public void onActivate(Object data) {
		/** Make sure that the data has been passed to the event handler */
		if(missiles != null && enemy != null && ship != null) {
			/** Get the missile that hit the enemy */
			OpenglImage sprite = missiles.GetMissile(number);
			
			/** Get centre position to play the explosion at */
			float x = sprite.getPosition().x() + sprite.getSize().x()/2;
			float y = sprite.getPosition().y() + sprite.getSize().y()/2;
			
			/** Setup the effect to be drawn */
			explosion = enemy.getEffect();
			explosion.DrawAt(x, y);
			explosion.Reset();
			
			/** Reset the enemy so it can spawn again */
			this.missiles.ResetMissile(number);
			this.enemy.resetObject();
			this.ship.AddCash();
			
			/** Updates stats */
			Statistics.get().enemyDown();
			
			/** reset missile */
			sprite.reset();
		}
	}

	/** Not needed for this type of event */
	@Override
	public void update() {
		;
	}
}
