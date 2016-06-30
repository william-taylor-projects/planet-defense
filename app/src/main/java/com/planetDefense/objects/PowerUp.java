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

public abstract class PowerUp {
	protected Boolean active;
	
	public abstract void DropItem(int x, int y);
	public abstract void onPickUp();
	public abstract void Update();
	public abstract void Hide();
	public abstract void Draw();
	
	public abstract GL_Image GetSprite();
	public abstract Boolean Hidden();
	public abstract Boolean Used();
	
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
