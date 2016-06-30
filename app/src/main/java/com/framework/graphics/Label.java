package com.framework.graphics;

import java.util.ArrayList;

import com.framework.IRenderable;
import com.framework.math.Vector2;
import com.framework.opengl.OpenglText;

public class Label implements IRenderable {
    private OpenglText label;
    private Integer spacing;
    private String line;

    public Label(Font engine, String text) {
        label = new OpenglText(engine);
        line = text;

        spacing = 2;
    }

    public Label(Font engine) {
        label = new OpenglText(engine);
    }

    public String getText() {
        return line;
    }

    public OpenglText getRawGL() {
        return label;
    }

    public void reset() {
        label.reset();
    }

    public Vector2 getPosition() {
        return label.getPosition();
    }

    public void scale(float x, float y) {
        spacing = (int)(spacing * x);
    }

    public void setInitialPosition(float x, float y) {
        label.setInitialPosition(x, y);
    }

    public int getWidth() {
        return label.GetWidth();
    }

    public void setColour(float r, float g, float b, float a) {
        label.setColour(r, g, b, a);
    }

    public void translate(float x, float y) {
        label.translate(x, y);
    }

    public void text(String str) {
        line = str;
    }

    public void load(int x, int y) {
        label.load(line, x, y);
    }

    public void update() {
        label.update(5);
    }

    public void update(int spacing) {
        label.update(spacing);
    }

    public void load(int x, int y, ArrayList<Vector2> positions) {
        label.load(line, x, y, positions);
    }

    public void Load(int x, int y, ArrayList<String> strings, ArrayList<Vector2> positions) {
        label.load(x, y, strings, positions);
    }

    @Override
    public void render() {
        if(label.isVisible()) {
            label.render();
        }
    }
}
