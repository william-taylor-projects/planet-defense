package com.framework.graphics;

import java.util.*;

import com.framework.IRenderable;
import com.framework.io.TextFile;

public class Paragraph implements IRenderable {
    private static final int RESERVED_ARRAY_MEMORY = 15;

    private ArrayList<Label> labels = new ArrayList<Label>(RESERVED_ARRAY_MEMORY);
    private ArrayList<String> lines = new ArrayList<String>(RESERVED_ARRAY_MEMORY);
    private TextFile textFile;
    private Font textFont;

    public Paragraph(Font font, String filename) {
        textFile = new TextFile(filename);
        lines = textFile.GetLines();
        textFont = font;
    }

    public void add(ArrayList<Object> objects) {
        for(Label l : labels) {
            objects.add(l);
        }
    }

    public void setColour(float r, float g, float b, float a) {
        for(Label l : labels) {
            l.setColour(r, g, b, a);
        }
    }

    public void load(int x, int y) {
        int NewY = y;
        for(String line : lines) {
            Label label = new Label(textFont, line);
            label.load(x, NewY);
            NewY += label.getRawGL().GetHeight();
            labels.add(label);
        }
    }

    public void scale(float x, float y) {
        for(Label label : labels) {
            label.scale(x, y);
        }
    }

    public void update() {
        for(Label label : labels) {
            label.update();
        }
    }

    public int getLineNumber() {
        return labels.size();
    }

    public Label getLine(int i) {
        return labels.get(i);
    }

    @Override
    public void render() {
        for(Label label : labels) {
            label.render();
        }
    }
}
