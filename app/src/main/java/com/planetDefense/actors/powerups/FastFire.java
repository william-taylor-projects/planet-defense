package com.planetDefense.actors.powerups;

import com.framework.opengl.OpenglImage;
import com.planetDefense.actors.Missiles;

import java.util.TimerTask;
import java.util.Timer;

public class FastFire extends PowerUp {
	public class TimeUp extends TimerTask {
		@Override
		public void run() {
			Missiles.DELAY *= 2;
		}	
	}
	
	private OpenglImage Sprite;
	private Integer Length = 10000;
	private Boolean Visible;
	private Boolean Used;
	
	public FastFire() {
		super();
		
		Sprite = new OpenglImage();
		Sprite.load("sprites/quick.png", "increase");
		Sprite.setPosition(-100, -100, 50, 50);

		this.Visible = true;
		this.Used = false;
	}
	
	public void setLength(Integer length) {
		Length = length;
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
			Used = !Used;
			return true;
		} return false;
	}

	@Override
	public void onPickUp() {
		if(super.active) {
			new Timer().schedule(new TimeUp(), Length);
			Missiles.DELAY /= 2;
			Used = true;
			active = false;
		}
	}
	
	public void Use() {
		if(super.active) {
			Missiles.DELAY /= 2;
			Used = true;
			active = false;
		}
	}
	
	public void Release() {
		if(super.active) {
			Missiles.DELAY *= 2;
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
