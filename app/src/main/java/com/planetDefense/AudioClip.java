package  com.planetDefense;

import android.media.MediaPlayer;

/**
 *  A simple class that plays an audio file
 *  And all media files can be altered using
 *  the said class.
 *  
 *  Should update to a more fleshed out
 *  audio system for aegis
 * 
 * @version 28/01/2014
 * @author William Taylor
 */

public class AudioClip {
	/** Static variable which alters all audioclips volume	*/
	public static Float MasterVolume = 1f;
	
	/** MediaPlayer class to play audio	*/
	private MediaPlayer Player;
	
	/** Volume for left speaker */
	private Float RightVolume;
	
	/** Volume for left speaker */
	private Float LeftVolume;
	
	private Boolean AlwaysOn;
	
	/**
	 * Constructor that sets up the class and for reading 
	 * the audio file
	 * 
	 * @param rawID The ID to the audio file in the raw folder.
	 */
	public AudioClip(Integer rawID) {
		Player = MediaPlayer.create(ResourceManager.Get().GetContext(), rawID);
		RightVolume = 1.0f;
		LeftVolume = 1.0f;
		AlwaysOn = false;
	}
	
	/**
	 * Plays the file using preset audio volumes.
	 */
	public void Play() {
		setVolume(LeftVolume, RightVolume);
		if(!Player.isPlaying()) {
			Player.start();
		}		
	}	
	
	public void alwaysOn() {
		AlwaysOn= true;
	}
	
	/**
	 * Plays the audio file at a set volume.
	 * @param v The volume to play at
	 */
	public void Play(float v) {
		setVolume(v, v);
		Play();
	}
	
	/**
	 * Pauses the current audio file
	 */
	public void Pause() {
		if(Player.isPlaying()) {
			Player.pause();
		}
	}
	
	/**
	 * Stops and resets the playing audio sound
	 */
	public void Stop() {
		if(Player.isPlaying()) {
			Player.stop();
		}
	}
	
	public void Restart() {
		Player.seekTo(0);
	}
	
	/**
	 * Sets the volume for the sound
	 * @param l Left speaker volume.
	 * @param r Right speaker volume.
	 */
	public AudioClip setVolume(float l, float r) {
		RightVolume = r;
		LeftVolume = l;
	
		if(AlwaysOn) {
			Player.setVolume(LeftVolume, RightVolume);
		} else {
			Player.setVolume(LeftVolume * MasterVolume, RightVolume * MasterVolume);
		}
		
		return this;
	}
	
	/**
	 * Simple enables the audio clip to loop if needed.
	 * @param loop should the clip loop
	 */
	public AudioClip setLoop(Boolean loop) {
		Player.setLooping(loop);
		return this;
	}
}
