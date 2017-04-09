package com.framework.audio;

import android.media.MediaPlayer;

import com.framework.io.ResourceManager;

public class AudioClip {
    public static Float masterVolume = 1f;
    private MediaPlayer player;
    private Float rightVolume;
    private Float leftVolume;
    private Boolean alwaysOn;

    public AudioClip(Integer rawID) {
        player = MediaPlayer.create(ResourceManager.get().getContext(), rawID);
        rightVolume = 1.0f;
        leftVolume = 1.0f;
        alwaysOn = false;
    }

    public AudioClip play() {
        setVolume(leftVolume, rightVolume);
        if(!player.isPlaying()) {
            player.start();
        }

        return this;
    }

    public AudioClip alwaysOn() {
        alwaysOn = true;
        return this;
    }

    public AudioClip play(float v) {
        setVolume(v, v);
        play();

        return this;
    }

    public AudioClip pause() {
        if(player.isPlaying()) {
            player.pause();
        }

        return this;
    }

    public AudioClip stop() {
        if(player.isPlaying()) {
            player.stop();
        }

        return this;
    }

    public AudioClip restart() {
        player.seekTo(0);
        return this;
    }

    public AudioClip setVolume(float l, float r) {
        rightVolume = r;
        leftVolume = l;

        if(alwaysOn) {
            player.setVolume(leftVolume, rightVolume);
        } else {
            player.setVolume(leftVolume * masterVolume, rightVolume * masterVolume);
        }

        return this;
    }

    public AudioClip setLoop(Boolean loop) {
        player.setLooping(loop);
        return this;
    }
}
