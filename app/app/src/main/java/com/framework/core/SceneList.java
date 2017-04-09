package com.framework.core;

import android.view.MotionEvent;

import com.framework.IFactory;

public abstract class SceneList {
    public void onTouch(MotionEvent e, int x, int y) {}
    public void onEnter() {}
    public void onExit() {}

    public abstract void initialise(IFactory factory);
    public abstract void update();
}
