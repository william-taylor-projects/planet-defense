package com.planetDefense.events;

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

import com.planetDefense.activity.MainActivity;

/**
 *  The earth death event handles when 
 *  a planet is destroyed
 *  
 * @version : final version for release
 * @author : William Taylor
 */
public class ResetGame implements IEvent, UiEvent {
	/** Some settings for this event */
	private static final String MESSAGE = "Are you sure you want to reset your stats";
	private static final String TITLE = "Reset statistics ?";
	
	/** Reference to the scene manager provided by the com.framework */
	private static final SceneManager scenes;
	
	/** Static initialiser to grab the scenemanager */
	static {
		scenes = SceneManager.get();
	}

	@Override
	public void onActivate(Object data) {
		/** Make sure the user meant it show a msg box to comfirm */
		MessageBox message = new MessageBox();
		
		message.setMessage(MESSAGE);
		message.setTitle(TITLE);
		message.onAccept(this);
		message.EnableYesNo();
		message.show(false);
	}
	
	/** Event called when the user pressed ok on the messagebox */
	@Override
	public void onUiEvent() {
		Statistics.get().resetStats();
		scenes.SwitchTo(MainActivity.MENU);
	}

	/** Not needed for this type of event */
	@Override
	public void update() {
		;
	}
}
