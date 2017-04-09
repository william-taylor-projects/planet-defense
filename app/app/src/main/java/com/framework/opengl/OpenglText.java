package com.framework.opengl;

import static android.opengl.GLES20.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import com.framework.IRenderable;
import com.framework.graphics.Font;
import com.framework.math.Matrix;
import com.framework.math.Vector2;
import com.framework.io.XmlReader;


public class OpenglText implements IRenderable {
    private static final String SHADERS_TEXT_VS_GLSL = "shaders/text_vs.glsl";
    private static final String SHADERS_TEXT_FS_GLSL = "shaders/text_fs.glsl";
    private static int PROJECTION_ID = 0;
    private static int POSITION_ID = 0;
    private static int MATRIX_ID = 0;
    private static int COLOUR_ID = 0;

    private float[] colour = new float[4];
    private Vector2 initialTranslate = new Vector2(0, 0);
    private Vector2 translate = new Vector2();
    private Matrix matrix = new Matrix();
    private Boolean loaded = false;
    private String string = "";

    private OpenglBuffer positionBuffer = new OpenglBuffer();
    private OpenglTextureUnit sheet;
    private OpenglProgram shader;
    private Integer textLength;
    private Integer textHeight;
    private Integer count;
    private Font font;

    public OpenglText(Font labelEngine) {
        shader = new OpenglProgram();
        shader.pushShaders(SHADERS_TEXT_VS_GLSL, SHADERS_TEXT_FS_GLSL);
        matrix.pushIdentity();
        font = labelEngine;

        setColour(1, 1, 1, 1);
    }

    public void setInitialPosition(float x, float y) {
        this.initialTranslate.set(x, y);
        this.translate.set(x, y);
    }

    public String getString() {
        return string;
    }

    public void load(String string, int x, int y) {
        Document document = font.getDocument();
        XmlReader parser = font.getParser();
        this.string = string;

        NodeList nodeList = document.getElementsByTagName("character");
        char[] letters = string.toCharArray();

        count = letters.length * 6;
        textHeight = 0;
        textLength = 0;

        float[] data = new float[24 * letters.length];

        int tex_x = 0;
        int tex_y = 0;
        int b = 0;

        sheet = OpenglTextureManager.get().getSprite(font.getTextureFilename());

        for(int i = 0; i < letters.length; i++) {
            int Value = (int)letters[i];
            Element e = (Element)nodeList.item(Value - 32);

            int x2 = Integer.parseInt(parser.getValue(e, "x"));
            int y2 = Integer.parseInt(parser.getValue(e, "y"));
            int h2 = Integer.parseInt(parser.getValue(e, "height"));
            int w2 = Integer.parseInt(parser.getValue(e, "width"));

            float realX = (float)x2/ sheet.width;
            float realY = (float)y2/ sheet.height;
            float realW = (float)(x2 + w2)/ sheet.width;
            float realH = (float)(y2 + h2)/ sheet.height;

            data[b + 0] = tex_x;	 	data[b + 2] = realX;
            data[b + 1] = tex_y;	 	data[b + 3] = realH;
            data[b + 4] = tex_x;	 	data[b + 6] = realX;
            data[b + 5] = tex_y+h2;		data[b + 7] = realY;
            data[b + 8] = tex_x+w2;	 	data[b + 10] = realW;
            data[b + 9] = tex_y;	 	data[b + 11] = realH;

            data[b + 12] = tex_x+w2;	data[b + 14] = realW;
            data[b + 13] = tex_y+h2;	data[b + 15] = realY;
            data[b + 16] = tex_x+w2;	data[b + 18] = realW;
            data[b + 17] = tex_y;		data[b + 19] = realH;
            data[b + 20] = tex_x;		data[b + 22] = realX;
            data[b + 21] = tex_y+h2;	data[b + 23] = realY;

            textLength = (int)data[b + 12];
            textHeight = h2;

            tex_x += w2;
            b += 24;
        }

        if(loaded) {
            positionBuffer.reinsert(data);
        } else {
            positionBuffer.insert(data);
            loaded = true;
        }

        initialTranslate.set(x, y);
        translate.set(x, y);
    }

    public void load(String string, int x, int y, ArrayList<Vector2> positions) {
        Document document = font.getDocument();
        XmlReader parser = font.getParser();

        NodeList nodeList = document.getElementsByTagName("character");
        char[] letters = string.toCharArray();

        count = letters.length * 6;
        textHeight = 0;
        textLength = 0;

        float[] data = new float[24 * letters.length];

        int b = 0;

        sheet = OpenglTextureManager.get().getSprite(font.getTextureFilename());

        for(int i = 0; i < letters.length; i++) {
            int Value = (int)letters[i];
            Element e = (Element)nodeList.item(Value - 32);

            int x2 = Integer.parseInt(parser.getValue(e, "x"));
            int y2 = Integer.parseInt(parser.getValue(e, "y"));
            int h2 = Integer.parseInt(parser.getValue(e, "height"));
            int w2 = Integer.parseInt(parser.getValue(e, "width"));

            float realX = (float)x2/ sheet.width;
            float realY = (float)y2/ sheet.height;
            float realW = (float)(x2 + w2)/ sheet.height;
            float realH = (float)(y2 + h2)/ sheet.height;

            Vector2 position = positions.get(i);

            float tex_x = position.getX() - (w2/2);
            float tex_y = position.getY();

            data[b + 0] = tex_x;	 	data[b + 2] = realX;
            data[b + 1] = tex_y;	 	data[b + 3] = realH;
            data[b + 4] = tex_x;	 	data[b + 6] = realX;
            data[b + 5] = tex_y+h2;		data[b + 7] = realY;
            data[b + 8] = tex_x+w2;	 	data[b + 10] = realW;
            data[b + 9] = tex_y;	 	data[b + 11] = realH;

            data[b + 12] = tex_x+w2;	data[b + 14] = realW;
            data[b + 13] = tex_y+h2;	data[b + 15] = realY;
            data[b + 16] = tex_x+w2;	data[b + 18] = realW;
            data[b + 17] = tex_y;		data[b + 19] = realH;
            data[b + 20] = tex_x;		data[b + 22] = realX;
            data[b + 21] = tex_y+h2;	data[b + 23] = realY;

            textLength = (int)data[b + 12];
            textHeight = h2;

            b += 24;
        }

        if(loaded) {
            positionBuffer.reinsert(data);
        } else {
            positionBuffer.insert(data);
            loaded = true;
        }

        initialTranslate.set(x, y);
        translate.set(x, y);
    }

    public boolean isVisible() {
        if(translate.getY() + textHeight >= 0 && translate.getY() <= 800) {
            return true;
        } return false;
    }

    public void setColour(float r, float g, float b, float a) {
        colour[0] = r;
        colour[1] = g;
        colour[2] = b;
        colour[3] = a;
    }


    public Vector2 getPosition() {
        return translate;
    }

    public void update(int spacing) {
        this.matrix.pushIdentity();
        this.matrix.translate(translate.getX(), translate.getY());
    }

    public void translate(float x, float y) {
        translate.set(x, y);
    }

    public void reset() {
        matrix.pushIdentity();
        matrix.translate(initialTranslate.getX(), initialTranslate.getY());

        translate.set(initialTranslate);

    }

    public Vector2 getTranslate() {
        return translate;
    }

    public int GetWidth() {
        return textLength;
    }

    public int GetHeight() {
        return textHeight;
    }

    public void beginProgram() {
        shader.startProgram();

        if(PROJECTION_ID == 0 && POSITION_ID == 0 && MATRIX_ID == 0) {
            PROJECTION_ID = shader.getUniform("Projection");
            POSITION_ID = shader.getAttribute("position");
            MATRIX_ID = shader.getUniform("ModelView");
            COLOUR_ID = shader.getUniform("Shade");
        }

        glUniformMatrix4fv(PROJECTION_ID, 1, false, matrix.getProjection(), 0);
    }

    public void startRender() {
        glBindTexture(GL_TEXTURE_2D, sheet.textureGL_ID[0]);

        positionBuffer.bindBuffer();
        glVertexAttribPointer(POSITION_ID, 4,  GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(POSITION_ID);

        glUniformMatrix4fv(MATRIX_ID, 1, false, matrix.getModelView(), 0);
        glUniform4fv(COLOUR_ID, 1, colour, 0);
        glDrawArrays(GL_TRIANGLES, 0, count);
    }

    public void endProgram() {
        glDisableVertexAttribArray(POSITION_ID);
        shader.endProgram();
    }

    public void load(int x, int y, ArrayList<String> strings, ArrayList<Vector2> positions) {
        Document document = font.getDocument();
        XmlReader parser = font.getParser();

        NodeList nodeList = document.getElementsByTagName("character");

        Integer stringLength = 0;
        for(int i = 0; i < strings.size(); i++) {
            stringLength += strings.get(i).length();
        }

        float[] data = new float[24 * stringLength];
        count = stringLength * 6;

        textHeight = 0;
        textLength = 0;

        sheet = OpenglTextureManager.get().getSprite(font.getTextureFilename());
        int b = 0;
        for(int i = 0; i < strings.size(); i++) {
            char[] letters = strings.get(i).toCharArray();

            Integer width = 0;
            for(int z = 0; z < letters.length; z++) {
                Element e = (Element)nodeList.item((int)letters[z] - 32);
                int w2 = Integer.parseInt(parser.getValue(e, "width"));
                width += w2;
            }


            Vector2 position = positions.get(i);

            float tex_x = position.getX() - (width/2);
            float tex_y = position.getY();

            for(int z = 0; z < letters.length; z++) {
                Element e = (Element)nodeList.item((int)letters[z] - 32);

                int x2 = Integer.parseInt(parser.getValue(e, "x"));
                int y2 = Integer.parseInt(parser.getValue(e, "y"));
                int h2 = Integer.parseInt(parser.getValue(e, "height"));
                int w2 = Integer.parseInt(parser.getValue(e, "width"));

                float realX = (float)x2/ sheet.width;
                float realY = (float)y2/ sheet.height;
                float realW = (float)(x2 + w2)/ sheet.width;
                float realH = (float)(y2 + h2)/ sheet.height;

                data[b + 0] = tex_x;	 	data[b + 2] = realX;
                data[b + 1] = tex_y;	 	data[b + 3] = realH;
                data[b + 4] = tex_x;	 	data[b + 6] = realX;
                data[b + 5] = tex_y+h2;		data[b + 7] = realY;
                data[b + 8] = tex_x+w2;	 	data[b + 10] = realW;
                data[b + 9] = tex_y;	 	data[b + 11] = realH;

                data[b + 12] = tex_x+w2;	data[b + 14] = realW;
                data[b + 13] = tex_y+h2;	data[b + 15] = realY;
                data[b + 16] = tex_x+w2;	data[b + 18] = realW;
                data[b + 17] = tex_y;		data[b + 19] = realH;
                data[b + 20] = tex_x;		data[b + 22] = realX;
                data[b + 21] = tex_y+h2;	data[b + 23] = realY;

                textLength = (int)data[b + 12];
                textHeight = h2;

                tex_x += w2;
                b += 24;
            }
        }

        if(loaded) {
            positionBuffer.reinsert(data);
        } else {
            positionBuffer.insert(data);
            loaded = true;
        }

        initialTranslate.set(x, y);
        translate.set(x, y);
    }

    @Override
    public void render() {
        if(isVisible()) {
            beginProgram();
            startRender();
            endProgram();
        }
    }
}
