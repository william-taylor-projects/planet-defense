package com.framework.core;

import android.view.GestureDetector.OnGestureListener;
import android.view.View;
import android.support.v4.view.GestureDetectorCompat;
import android.opengl.GLSurfaceView;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Activity;
import android.os.Build;

import com.framework.IFactory;
import com.framework.IGameActivity;
import com.framework.io.ResourceManager;
import com.framework.opengl.OpenglRenderer;

@SuppressWarnings("deprecation")
@SuppressLint("NewApi")
public class GameObject extends Application implements OnGestureListener {
    public static Activity Activity;

    private final Integer SURFACE_HEIGHT = 800;
    private final Integer SURFACE_WIDTH = 1280;
    private GestureDetectorCompat detector;
    private IGameActivity aegisActivity;
    private GLSurfaceView renderingThread;
    private Activity gameActivity;

    private Float height = 1.0f;
    private Float width = 1.0f;
    private Boolean landscape;
    private static Boolean disable;

    public void start(IGameActivity activity) {
        detector = new GestureDetectorCompat(getApplicationContext(), this);

        ResourceManager.get().initialise(getApplicationContext());
        DisplayMetrics Window = new DisplayMetrics();
        landscape = true;
        disable = false;

        renderingThread = new GLSurfaceView(this);
        renderingThread.setEGLContextClientVersion(2);
        renderingThread.setRenderer(new OpenglRenderer());
        renderingThread.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        renderingThread.setPreserveEGLContextOnPause(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            renderingThread.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);
        }

        gameActivity.getWindowManager().getDefaultDisplay().getMetrics(Window);
        gameActivity.setContentView(renderingThread);

        height = (float)Window.heightPixels/SURFACE_HEIGHT;
        width = (float)Window.widthPixels/SURFACE_WIDTH;

        aegisActivity = activity;
        aegisActivity.setupStates(SceneManager.get());
    }

    public void makePortrait() {
        landscape = false;
    }

    public IFactory getFactory() {
        return SceneManager.get().getFactory();
    }

    public Boolean hasInitialised() {
        return SceneManager.get().hasLoaded();
    }

    public void setupWindow(Activity activity) {
        gameActivity = activity;
        gameActivity.requestWindowFeature(0x1);
        gameActivity.getWindow().setFlags(0x400, 0x400);

        Activity = activity;
    }

    public boolean touchEvent(MotionEvent e) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            renderingThread.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);
        }

        if(!disable) {
            detector.onTouchEvent(e);

            if(landscape && e != null) {
                if((e != null) && (height != 0) && (width != 0)) {
                    int y = (int)((SURFACE_HEIGHT) - (e.getY() / height));
                    int x = (int)(e.getX() / width);

                    SceneManager.get().getCurrent().onTouch(e, x, y);
                    return true;
                }
            } else {
                if(height != 0 && width != 0) {
                    int y = (int)((SURFACE_HEIGHT) - (e.getY() / height));
                    int x = (int)(e.getX() / width);

                    SceneManager.get().getCurrent().onTouch(e, x, y);
                    return true;
                }
            }
        }
        return true;
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            renderingThread.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);
        }

        Scene scene = SceneManager.get().getCurrent();

        if(velocityX*velocityX > velocityY*velocityY) {
            scene.onFling(e1, e2, velocityX, 0);
            return true;
        } else {
            scene.onFling(e1, e2, 0, velocityY);
            return true;
        }
    }

    @Override
    public void onLongPress(MotionEvent e) {
        int y = (int)((SURFACE_HEIGHT) - (e.getY() / height));
        int x = (int)(e.getX() / width);

        SceneManager.get().getCurrent().onLongPress(e, x, y);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        ;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    public void onPause() {
        renderingThread.onPause();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            renderingThread.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);
        }
    }

    public void onResume() {
        renderingThread.onResume();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            renderingThread.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);
        }
    }

    public static void enableInput() {
        disable = false;
    }

    public static void disableInput() {
        disable = true;
    }
}