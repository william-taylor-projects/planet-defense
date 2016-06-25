package objects;

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
import com.planetDefense.*;


public class Nuke extends PowerUp {
	private GL_Image Sprite;
	private Boolean Visible;
	private Boolean Used;
	private Enemys enemys;
	
	public Nuke(Enemys enemys) {
		super();
		
		Sprite = new GL_Image();
		Sprite.load("sprites/nuke.png", "nuke");
		Sprite.setPosition(-100, -100, 50, 50);

		this.Visible = true;
		this.enemys = enemys;
		this.Used = false;
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
			Used = false;
			return true;
		} return false;
	}

	@Override
	public void onPickUp() {
		if(super.active) {
			enemys.KillEnemys();
			Ship.CASH += 500;
			active = false;
			Used = true;
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
