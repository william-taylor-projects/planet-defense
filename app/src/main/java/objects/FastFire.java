package objects;

import java.util.TimerTask;
import java.util.Timer;
import com.planetDefense.*;

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
public class FastFire extends PowerUp {
	public class TimeUp extends TimerTask {
		@Override
		public void run() {
			Missiles.DELAY *= 2;
		}	
	}
	
	private GL_Image Sprite;
	private Integer Length = 10000;
	private Boolean Visible;
	private Boolean Used;
	
	public FastFire() {
		super();
		
		Sprite = new GL_Image();
		Sprite.load("sprites/quick.png", "increase");
		Sprite.setPosition(-100, -100, 50, 50);

		this.Visible = true;
		this.Used = false;
	}
	
	public void setLength(Integer length) {
		Length = length;
	}
	
	@Override
	public void DropItem(int x, int y) {
		Sprite.shift(x, y, 1);
		Visible = true;
	}

	@Override
	public void Update() {
		Sprite.update(0);
	}

	@Override
	public void Draw() {
		Sprite.render();
	}

	@Override
	public Boolean Used() {
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
	public void Hide() {
		Visible = false;
		Sprite.reset();
		Used = false;
	}

	@Override
	public GL_Image GetSprite() {
		return Sprite;
	}

	@Override
	public Boolean Hidden() {
		return(Visible);
	}
}
