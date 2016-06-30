package com.framework.core;

import java.util.ArrayList;

import com.framework.IFactory;
import com.framework.ISceneLoader;
import com.framework.graphics.RenderQueue;

public class SceneManager {
    private static SceneManager instance;
    private ArrayList<Scene> scenes;
    private ISceneLoader first;
    private IFactory factory;
    private Integer current;
    private Boolean loaded;
    private int nextScene;

    public static SceneManager get() {
        if(instance == null) {
            instance = new SceneManager();
        } return instance;
    }

    private SceneManager() {
        scenes = new ArrayList<Scene>();
        factory = new Factory();
        loaded = false;
        nextScene = 0;
        current = 0;
    }

    public Scene getCurrent() {
        if(this.first != null) {
            if(!first.hasLoaded()) {
                ((Scene)first).onEnter(0);
                return (Scene)first;
            } else if(first.hasLoaded() && !loaded) {
                ((Scene)first).onCreate(factory);
            }
        }

        if(!loaded) {
            for(Scene s : scenes) {
                s.onCreate(factory);
            } loaded = true;
            scenes.get(0).onEnter(0);
        }

        if(current < scenes.size()) {
            return scenes.get(current);
        } else {
            return null;
        }
    }

    public Scene getFirst() {
        return (Scene)first;
    }

    public void createScene(Scene scene) {
        if(scene instanceof ISceneLoader) {
            first = (ISceneLoader)scene;
        } else {
            scenes.add(scene);
        }
    }

    public void switchTo(int i) {
        nextScene = i;
    }

    public void startFrom(int i) {
        nextScene = i;
        current = i;
    }

    public Integer getLocation() {
        return current;
    }

    public IFactory getFactory() {
        return factory;
    }

    public boolean hasLoaded() {
        return loaded;
    }

    public Scene getScene(Integer sceneID) {
        if(sceneID < scenes.size()) {
            return scenes.get(sceneID);
        }
        return null;
    }

    public void change() {
        if(current != nextScene) {
            Integer previous = current;
            scenes.get(current).onExit(nextScene);
            current = nextScene;
            scenes.get(current).onEnter(previous);
        }
    }

    public void clear() {
        factory = new Factory();
        scenes.clear();
        loaded = false;
        current = 0;
    }

    public void update(RenderQueue renderQueue) {
        Scene currentScene = getCurrent();
        if(currentScene != null) {
            currentScene.onUpdate();
            for(Scene scene : scenes) {
                scene.inBackground();
            }

            currentScene.onRender(renderQueue);
        }
    }
}