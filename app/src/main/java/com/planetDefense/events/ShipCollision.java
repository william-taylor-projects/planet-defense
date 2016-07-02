package com.planetDefense.events;

import java.util.TimerTask;
import java.util.Timer;

import com.framework.IEvent;
import com.framework.core.CollisionEvent;
import com.framework.math.Vector2;
import com.framework.opengl.OpenglImage;
import com.planetDefense.objects.Explosion;
import com.planetDefense.objects.Enemies;
import com.planetDefense.objects.Ship;


public class ShipCollision implements IEvent, CollisionEvent.CollisionArray {
	private Explosion effect;
	private Enemies enemy;
	private Ship ship;
	private int num;

	public ShipCollision(Enemies enemy, Ship ship) {
		this.enemy = enemy;
		this.ship = ship;
	}

	@Override
	public void onActivate(Object data) {
		if(enemy != null && ship != null) {
			OpenglImage Object = (OpenglImage)enemy.get(num).GetRawObject();
			OpenglImage Sprite = (OpenglImage)ship.GetRawObject();
			
			Vector2 p = Object.getPosition();
            Vector2 s = Object.getSize();
			
			effect = enemy.get(num).getEffect();
			effect.DrawAt(p.getX() + s.getX()/2, p.getY() + s.getY()/2);
			effect.Reset();
			
			Sprite.setShade(1f, 0.1f, 0.1f, 1f);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
                    OpenglImage Sprite = (OpenglImage)ship.GetRawObject();
					Sprite.setShade(1f, 1f, 1f, 1f);
				}
			}, 750);
			
			enemy.get(num).resetObject();
			ship.TakeDamage();
		}
	}

	@Override
	public void update() {

	}

	@Override
	public void collisionID(Integer ID) {
		this.num = ID;
	}
}
