package com.game.events;

import com.framework.IEvent;
import com.framework.core.EventManager;
import com.framework.math.Vector2;
import com.game.actors.Earth;
import com.game.actors.Enemy;
import com.game.actors.Explosion;
import com.game.actors.Ship;

public class EarthCollision implements IEvent {
	private static final EventManager eventManager = EventManager.get();
	private Explosion effect;
	private Earth earth;
	private Enemy enemy;
	private Ship player;

	public EarthCollision(Enemy enemy, Earth earth, Ship player) {
		this.player = player;
		this.earth = earth;
		this.enemy = enemy;
	}

	@Override
	public void onActivate(Object data) {
		if(enemy != null && earth != null && player != null) {
			Vector2 position = earth.getSprite().getPosition();
			Vector2 size = earth.getSprite().getSize();

			float x = position.getX() + (size.getX() / 2);
			float y = position.getY() + (size.getY() / 2);

			effect = enemy.getEffect();
			effect.drateAt(x, y);
			effect.reset();

			earth.takeDamage(10);
			enemy.resetObject();

			if(earth.getHealth() <= 0) {
				EarthDeath event = new EarthDeath(earth, player);
				eventManager.triggerEvent(event, null);
			}
		}
	}

	@Override
	public void update() {

	}
}
