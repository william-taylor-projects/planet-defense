package com.framework.graphics;

import com.framework.IRenderable;
import com.framework.math.Vector2;
import com.framework.opengl.OpenglImage;

public class Image implements IRenderable {
    private OpenglImage Sprite;
    private String filename;

    public Image(String ID) {
        this.filename = ID;
        Sprite = new OpenglImage();
        Sprite.load(ID, ID);
    }

    public Object getRawObject() {
        return this.Sprite;
    }

    public void setPosition(int x, int y, int w, int h) {
        Sprite.setPosition(x, y, w, h);
    }

    public String getFilename() {
        return filename;
    }

    public Vector2 getPosition() {
        return(Sprite.getPosition());
    }

    public Vector2 getSize() {
        return(Sprite.getSize());
    }

    public void update() {
        Sprite.update(0);
    }

    public void setTexture(String str) {
        Sprite.load(str, str);
        this.filename = str;
    }

    public void reset() {
        Sprite.reset();
    }

    public void shade(float r, float g, float b, float a) {
        Sprite.setShade(r, g, b, a);
    }

    public void translate(float x, float y) {
        Sprite.translate(x, y);
    }

    public void draw() {
        if(Sprite.isVisible()) {
            Sprite.render();
        }
    }

    public void reload() {

    }

    @Override
    public void render() {
        Sprite.render();
    }
}
