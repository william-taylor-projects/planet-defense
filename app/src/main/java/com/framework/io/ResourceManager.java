package com.framework.io;

import android.content.res.AssetManager;
import android.content.Context;
import android.util.Log;

import java.io.InputStream;
import java.util.Scanner;

public class ResourceManager {
    private static ResourceManager instance;
    private static Context gameContext;

    public static ResourceManager get() {
        if(instance == null) {
            instance = new ResourceManager();
        } return instance;
    }

    public InputStream getResource(String filename) {
        AssetManager manager = gameContext.getAssets();
        InputStream stream = null;
        try {
            stream = manager.open(filename);
        } catch (Exception e) {
            Log.e(ResourceManager.class.getName(), e.toString());
        }
        return stream;
    }

    public Scanner getFile(String filename) {
        Scanner scanner = null;
        try {
            scanner =  new Scanner(getResource(filename));
        }  catch (Exception e) {
            Log.e(ResourceManager.class.getName(), e.toString());
        }
        return scanner;
    }

    public void initialise(Context c) {
        gameContext = c;
    }

    public Context getContext() {
        return gameContext;
    }
}
