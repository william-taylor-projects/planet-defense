package com.planetDefense.objects;

/**
 * Copyright (c) 2014 - William Taylor <wi11berto@yahoo.co.uk>
 *
 *	This software is provided 'as-is', without any express or implied warranty. 
 *  In no event will the authors be held liable for any damages arising from 
 *  the use of this software. Permission is granted to anyone to use this 
 *  software for any purpose, including commercial applications, and to 
 *  alter it and redistribute it freely, subject to the following 
 *  restrictions:
 *
 *	1. The origin of this software must not be misrepresented; 
 *     you must not claim that you wrote the original software. 
 *	   If you use this software in a product, an acknowledgment 
 *     in the product documentation would be appreciated 
 *     but is not required.
 *
 *  2. Altered source versions must be plainly marked as such, 
 *     and must not be misrepresented as being the original 
 *     software.
 *  
 *  3. This notice may not be removed or altered 
 *     from any source distribution.
 *     
 */
import com.framework.opengl.OpenglImage;

import java.util.TimerTask;
import java.util.Timer;

public class MoveFast extends PowerUp {
	private class TimeOut extends TimerTask {
		@Override
		public void run() {
			Player.EnableDamage();
			Player.EnableShots();
			Ship.MOVEMENT_ADD = 0;
		}
	}
	
	private OpenglImage Sprite;
	private Boolean Used;
	private Boolean Visible;
	private Timer Timer;
	private Ship Player;
	
	public MoveFast(Ship Player) {
		super();
		
		Sprite = new OpenglImage();
		Sprite.load("sprites/fast.png", "fast");
		Sprite.setPosition(-100, -100, 50, 50);
		
		Timer = new Timer();
		
		this.Player = Player;
		this.Used = false;
		this.Visible = true;
	}
	
	@Override
	public void dropItem(int x, int y) {
		Sprite.shift(x, y, 1);
		Visible = true;
	}

	@Override
	public void update() {
		Sprite.setShade(1f, 1f, 0f, 1f);
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
			Player.DisableDamage();
			Player.DisableShots();
			
			Timer.schedule(new TimeOut(), 6000);
			Ship.MOVEMENT_ADD = 50;
			super.active = false;
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
