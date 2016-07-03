package com.planetDefense.actors.powerups;

import com.framework.opengl.OpenglImage;
import com.planetDefense.actors.Enemies;
import com.planetDefense.actors.Ship;

public class Nuke extends PowerUp {
	private OpenglImage Sprite;
	private Boolean Visible;
	private Boolean Used;
	private Enemies enemies;
	
	public Nuke(Enemies enemies) {
		super();
		
		Sprite = new OpenglImage();
		Sprite.load("sprites/nuke.png", "nuke");
		Sprite.setPosition(-100, -100, 50, 50);

		this.Visible = true;
		this.enemies = enemies;
		this.Used = false;
	}
	
	@Override
	public void dropItem(int x, int y) {
		Sprite.shift(x, y, 1);
		Visible = true;
	}

	@Override
	public void update() {
		Sprite.update(0);
	}

	@Override
	public void draw() {
		Sprite.render();
	}

	@Override
	public Boolean used() {
		if(Used) {
			Used = false;
			return true;
		} return false;
	}

	@Override
	public void onPickUp() {
		if(super.active) {
			enemies.killEnemies();
			Ship.CASH += 500;
			active = false;
			Used = true;
		}
	}

	@Override
	public void hide() {
		Visible = false;
		Sprite.reset();
		Used = false;
	}

	@Override
	public OpenglImage getSprite() {
		return Sprite;
	}

	@Override
	public Boolean hidden() {
		return(Visible);
	}
}
