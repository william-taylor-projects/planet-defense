package events;

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

/**
 *  State Click event which is called when 
 *  we switch states
 *  
 * @version : final version for release
 * @author : William Taylor
 */
public class StateClick implements IEvent {
	/** Settings and the manager needed for this event */
	private static final Integer NONE_SELECTED = -1;
	private static final Float VOLUME = 0.25F;
	private static final SceneManager scenes;
	
	/** The scene to go to */
	private Integer state = NONE_SELECTED;
	
	/** Static initialiser to grab managers */
	static {
		scenes = SceneManager.get();
	}
	
	/**
	 * Basic constructor sets up which state we should move to via ID
	 * @param i
	 */
	public StateClick(int i) {
		state = i;
	}

	/** Event handler */
	@Override
	public void onActivate(Object data) {
		if(state != NONE_SELECTED) {
			new AudioClip(com.planetDefense.R.raw.click).Play(VOLUME);
			scenes.SwitchTo(state);
		}
	}
	
	/** Update function */
	@Override
	public void update() {
		;
	}
}
