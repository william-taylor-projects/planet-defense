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
 *  The earth death event handles when 
 *  a planet is destroyed
 *  
 * @version : final version for release
 * @author : William Taylor
 */
public class MuteEvent implements IEvent {
	/** Event handler for the event */
	@Override
	public void onActivate(Object data) {
		/** If the volume is on mute it */
		if(AudioClip.MasterVolume > 0.0f) {
			AudioClip.MasterVolume = 0.0f;
		} else {
			/** Else turn the audio on */
			AudioClip.MasterVolume = 1.0f;
		}
	}

	/** Not needed for me */
	@Override
	public void update() {
		;
	}
}
