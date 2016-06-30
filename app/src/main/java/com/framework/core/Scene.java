package com.framework.core;

import android.view.MotionEvent;

import com.framework.IFactory;
import com.framework.graphics.RenderQueue;

public abstract class Scene {
    public void onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){}
    public void onRender(RenderQueue renderList) {}
    public void onTouch(MotionEvent e, int x, int y) {}
    public void onLongPress(MotionEvent e, int x, int y) {}
    public void onEnter(Integer previousState) {}
    public void onCreate(IFactory factory) {}
    public void onExit(Integer nextState) {}
    public void inBackground() {}
    public void onUpdate() {}
}
