package com.planetDefense.actors.powerups;

import com.framework.opengl.OpenglImage;

public abstract class PowerUp {
	protected Boolean active;
	
	public abstract void dropItem(int x, int y);
	public abstract void onPickUp();
	public abstract void update();
	public abstract void hide();
	public abstract void draw();
	
	public abstract OpenglImage getSprite();
	public abstract Boolean hidden();
	public abstract Boolean used();
	
	public PowerUp() {
		active = false;
	}
	
	public void disable() {
		active = false;
	}
	
	public void enable() {
		active = true;
	}
}
