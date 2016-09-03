package com.game.activity;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.app.Activity;
import android.os.Bundle;

import com.framework.IGameActivity;
import com.framework.audio.AudioClip;
import com.framework.core.*;

import com.game.scenes.*;

public class MainActivity extends Activity implements IGameActivity {
    public static final int STATISTICS = 6;
    public static final int TUTORIAL = 0;
    public static final int GAMEOVER = 5;
    public static final int UPGRADE = 4;
    public static final int LEVEL = 2;
    public static final int INFO = 3;
    public static final int MENU = 1;

    private GameObject game;

    @Override
    protected void onCreate(Bundle inst) {
        super.onCreate(inst);

        game = (GameObject)getApplicationContext();
        game.setupWindow(this);
        game.start(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        game.onPause();
        AudioClip.masterVolume = 0.0F;
    }

    @Override
    public void onResume() {
        super.onResume();
        game.onResume();
        AudioClip.masterVolume = 1.0F;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            EventManager.get().triggerEvent(new ExitEvent(), null);
            return true;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(game.hasInitialised() && game.touchEvent(e)) {
            return true;
        } return false;
    }

    @Override
    public void setupStates(SceneManager scenes) {
        scenes.createScene(new LoadScreen());
        scenes.createScene(new TutorialScene());
        scenes.createScene(new MenuScene());
        scenes.createScene(new MainScene());
        scenes.createScene(new InformationScene());
        scenes.createScene(new UpgradeState());
        scenes.createScene(new GameOverState());
        scenes.createScene(new StatisticsScene());
    }
}
