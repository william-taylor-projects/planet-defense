package com.framework.opengl;

import static android.opengl.GLES20.*;

import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import com.framework.io.ResourceManager;


public class OpenglTextureManager {
    private static final int ARRAY_RESERVE_SIZE = 100;
    private static OpenglTextureManager instance;
    private ArrayList<OpenglTextureUnit> textures;

    private OpenglTextureManager() {
        textures = new ArrayList<OpenglTextureUnit>(ARRAY_RESERVE_SIZE);
    }

    public static OpenglTextureManager get() {
        if(instance == null) {
            instance = new OpenglTextureManager();
        } return instance;
    }

    public OpenglTextureUnit getSprite(String textureID) {
        Integer size = textures.size();
        for(int i = 0; i < size; i++) {
            OpenglTextureUnit unit = textures.get(i);
            if(unit.textureName.equals(textureID)) {
                return unit;
            }
        }

        return null;
    }

    public OpenglTextureUnit importTexture(String filename, String name) {
        Integer duplicate = checkForDuplicate(name);

        if(duplicate == -1) {
            ResourceManager resourceManager = ResourceManager.get();
            OpenglTextureUnit sprite = new OpenglTextureUnit();

            Bitmap spriteData = BitmapFactory.decodeStream(resourceManager.getResource(filename));
            sprite.textureGL_ID = new int[1];
            sprite.textureName = name;
            sprite.height = spriteData.getHeight();
            sprite.width = spriteData.getWidth();

            glGenTextures(1, sprite.textureGL_ID , 0);
            glBindTexture(GL_TEXTURE_2D, sprite.textureGL_ID[0]);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

            GLUtils.texImage2D(GL_TEXTURE_2D, 0, spriteData, 0);
            textures.add(sprite);
            spriteData.recycle();
            return sprite;
        } else {
            return textures.get(duplicate);
        }
    }

    public void releaseTextures() {
        for(OpenglTextureUnit sprite : textures) {
            glDeleteTextures(1, sprite.textureGL_ID, 0);
        }
    }

    public void clear() {
        textures.clear();
    }

    private int checkForDuplicate(String name) {
        int arraySize = textures.size();
        int ID = -1;
        for(int i = 0; i < arraySize; i++) {
            if(textures.get(i).textureName.compareToIgnoreCase(name) == 0) {
                ID = i;
                break;
            }
        }

        return ID;
    }
}
