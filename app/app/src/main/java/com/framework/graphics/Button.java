package com.framework.graphics;

import com.framework.IRenderable;
import com.framework.core.GameObject;
import com.framework.math.Vector2;
import com.framework.opengl.OpenglImage;
import com.framework.opengl.OpenglText;

public class Button implements IRenderable {
    private Boolean spriteEnabled = false;
    private Boolean textEnabled = false;
    private Boolean loadText = false;
    private String labelFilename;
    private Vector2 position;
    private Vector2 size;

    private OpenglImage sprite;
    private OpenglText text;

    public Button(Font e) {
        sprite = new OpenglImage();
        text = new OpenglText(e);
        loadText = false;
    }

    public Button() {
        sprite = new OpenglImage();
    }

    public void setTexture(String str) {
        if(spriteEnabled) {
            sprite.load(str, str);
        }
    }

    public void setText(String string, float x, float y, float w, float h) {
        // disable the input and start loading the text
        GameObject.disableInput();
        text.load(string, 0, 0);

        // then calculate the original position
        Float halfX = (float)(text.GetWidth()/2);
        text.setInitialPosition(x - halfX, y);
        text.update(0);

        // if the sprite isnt enabled set its position to the texts
        if(!spriteEnabled) {
            // this is so when we return the position of the button we return the position of the text instead
            sprite.setPosition(x - halfX, y, w + halfX, h);
        }

        // re enable the input & enabled the text
        GameObject.enableInput();
        textEnabled = true;
    }

    public void setText(String string, float x, float y) {
        text.load(string, 0, 0);
        text.setInitialPosition(x - text.GetWidth()/2, y);
        text.update(0);

        textEnabled = true;
    }

    public void setSprite(String string, float x, float y, float w, float h) {
        position = new Vector2(x, y);
        size = new Vector2(w, h);
        sprite.load(string, string);
        sprite.setPosition(x, y, w, h);
        spriteEnabled = true;
    }

    public void pushText(OpenglText e) {
        this.text = e;
    }

    public void setTextColour(float r, float g, float b, float a) {
        if(text != null) {
            text.setColour(r, g, b, a);
        }
    }

    public Vector2 getPosition() {
        return sprite.getPosition();
    }

    public void addText(String str) {
        labelFilename = str;
        loadText = true;
    }

    public void reset() {
        sprite.reset();
        text.reset();
    }

    public void translate(float x, float y) {
        Vector2 vec = text.getTranslate();

        if(spriteEnabled) {
            sprite.translate(x, y);
        }

        if(textEnabled) {
            text.translate(vec.getX() + x, vec.getY() + y);
        }
    }

    public void hideText() {
        textEnabled = false;
    }

    public void update() {
        if(loadText) {
            textEnabled = true;
            loadText = false;

            text.load(labelFilename, 0, 0);
            text.setColour(0, 0, 0, 1);

            float tx = sprite.getPosition().getX() + sprite.getSize().getX()/2 - text.GetWidth()/2;
            float ty = sprite.getPosition().getY() + sprite.getSize().getY()/2 - text.GetHeight()/2;
            float x = position.getX() + size.getX()/2 - text.GetWidth()/2;
            float y = position.getY() + size.getY()/2 - text.GetHeight()/2;

            text.setInitialPosition(x, y);
            text.translate(tx, ty);

        }

        if(spriteEnabled) {
            sprite.update(0);
        }

        if(textEnabled) {
            text.update(0);
        }
    }

    public Vector2 getSize() {
        return(sprite.getSize());
    }

    public String getFilename() {
        return sprite.getFilename();
    }

    public OpenglImage getImage() {
        if(!spriteEnabled) {
            return null;
        } else {
            return sprite;
        }
    }

    public OpenglText getText() {
        if(!textEnabled) {
            return null;
        } else {
            return this.text;
        }
    }

    @Override
    public void render() {
        if(spriteEnabled && sprite.isVisible()) {
            sprite.render();
        }

        if(textEnabled && text.isVisible()) {
            text.render();
        }
    }
}
