package com.framework.opengl;

import com.framework.IRenderable;
import com.framework.math.Matrix;
import com.framework.math.Vector2;

import static android.opengl.GLES20.*;

public class OpenglImage implements IRenderable {
    private static final String SHADERS_IMAGE_VS_GLSL = "shaders/image_vs.glsl";
    private static final String SHADERS_IMAGE_FS_GLSL = "shaders/image_fs.glsl";
    private static int ProjectionID = 0;
    private static int PositionID = 0;
    private static int MatrixID = 0;
    private static int ColorID = 0;

    private OpenglBuffer buffer = new OpenglBuffer();
    private OpenglTextureUnit sprite;
    private OpenglProgram program;

    private Vector2 realPosition = new Vector2();
    private Vector2 translation = new Vector2();
    private Vector2 position = new Vector2();
    private Vector2 scale = new Vector2(1,1);
    private Vector2 size = new Vector2();

    private float[] Color = new float[4];
    private Matrix matrix = new Matrix();
    private String filename;

    public OpenglImage() {
        program = OpenglShaderManager.get().getShader(SHADERS_IMAGE_VS_GLSL, SHADERS_IMAGE_FS_GLSL);
        setShade(1f, 1f, 1f, 1f);
    }

    public OpenglTextureUnit getSprite() {
        return sprite;
    }

    public void setSprite(OpenglTextureUnit s) {
        sprite = s;
    }

    public String getFilename() {
        return filename;
    }

    public void reset() {
        translation.set(0, 0);
    }

    public void load(String filename, String name) {
        sprite = OpenglTextureManager.get().importTexture(filename, name);
        this.filename = filename;
    }

    public boolean isVisible() {
        realPosition.set(position);
        realPosition.setX(translation.getX());
        realPosition.setY(translation.getY());

        if(realPosition.getY() + size.getY() >= 0 && realPosition.getY() <= 800) {
            return true;
        } return false;
    }

    @Override
    public void render() {
        if(isVisible()) {
            beginProgram();
            startRender();
            endProgram();
        }
    }

    public void translateTo(float x, float y, float s) {
        Vector2 position = getPosition();

        if(x != position.getX() || y != position.getY()) {
            Vector2 vec = new Vector2();

            float tx = x - position.getX();
            float ty = 0.0f;

            if(y != position.getY() && y <= position.getY()) {
                ty = y - position.getY();
            } else if(y != position.getY() && y > position.getY()) {
                ty = y - position.getY();
            }

            vec.set(tx, ty);
            vec.normalise();

            translation.setX(vec.getX() * s);
            translation.setY(vec.getX() * s);
        }
    }

    public void update(int angle) {
        matrix.pushIdentity();
        matrix.translate(translation.getX(), translation.getY());
        matrix.rotate(angle, position.getX() + (size.getX()/2), position.getY() + (size.getY()/2));
        matrix.scale(scale.getX(), scale.getY());
    }

    public void shift(float x, float y, int div) {
        if(x != 0.0f && y != 0.0f) {
            translation.setX((int)(x - (position.getX() + translation.getX()))/div);
            translation.setY((int)(y - (position.getY() + translation.getY()))/div);
        }
    }

    public void translate(float x, float y) {
        translation.setX(x);
        translation.setY(y);
    }

    public void translateOnce(float x, float y) {
        translation.set(0, 0);
        translation.setX(x);
        translation.setY(y);
    }

    public void setPosition(float x, float y, float w, float h, float t, float z, float b, float c) {
        float[] pos = new float[16];

        pos[0] = x;		pos[1] = y;		pos[2] = t;	pos[3] = c;
        pos[4] = x;		pos[5] = y+h;	pos[6] = t;	pos[7] = z;
        pos[8]= x+w;	pos[9] = y;		pos[10]= b;	pos[11]= c;
        pos[12]= x+w;	pos[13]= y+h;	pos[14]= b;	pos[15]= z;

        position.set(x, y);
        size.set(w, h);

        buffer.insert(pos);
    }

    public void setScale(float x, float y) {
        scale.set(x, y);
    }

    public void setShade(float r, float g, float b, float a) {
        Color[0] = r;	Color[1] = g;
        Color[2] = b;	Color[3] = a;
    }

    public void setPosition(float x, float y, float w, float h) {
        setPosition(x, y, w, h, 0, 0, 1, 1);
    }

    public Vector2 getPosition() {
        realPosition.set(position);
        realPosition.setX(translation.getX());
        realPosition.setY(translation.getY());
        return realPosition;
    }

    public float getRotation() {
        return(matrix.getRotation());
    }

    public Vector2 getSize() {
        return(size);
    }

    public Vector2 getScale() {
        return(scale);
    }

    public void beginProgram() {
        program.startProgram();

        if(ProjectionID == 0 && PositionID == 0 && MatrixID == 0 && ColorID == 0)
        {
            ProjectionID = program.getUniform("Projection");
            PositionID = program.getAttribute("position");
            MatrixID = program.getUniform("ModelView");
            ColorID = program.getUniform("ColorShade");
        }

        glEnableVertexAttribArray(PositionID);
    }

    public void startRender() {
        buffer.bindBuffer();

        glBindTexture(GL_TEXTURE_2D, sprite.textureGL_ID[0]);
        glVertexAttribPointer(PositionID, 4,  GL_FLOAT, false, 0, 0);
        glUniformMatrix4fv(ProjectionID, 1, false, matrix.getProjection(), 0);
        glUniformMatrix4fv(MatrixID, 1, false, matrix.getModelView(), 0);
        glUniform4fv(ColorID, 1, Color,  0);
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
    }

    public void endProgram() {
        glDisableVertexAttribArray(PositionID);
        program.endProgram();
    }
}
