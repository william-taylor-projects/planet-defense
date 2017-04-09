package com.framework.opengl;

import com.framework.math.Matrix;
import com.framework.math.Vector2;

import static android.opengl.GLES20.*;

public class OpenglLine {
    private OpenglProgram program;
    private OpenglBuffer buffer;
    private Matrix matrix;
    private Vector2 translate;
    private float[] position;
    private float[] colour;

    public OpenglLine() {
        program = OpenglShaderManager.get().getShader("shaders/line_vs.glsl", "shaders/line_fs.glsl");
        buffer = new OpenglBuffer();
        matrix = new Matrix();
        colour = new float[4];

        translate = new Vector2(0, 0);
    }

    public void setPosition(float... positions) {
        position = new float[positions.length];

        for(int i = 0; i < positions.length; i++) {
            this.position[i] = positions[i];
        }

        buffer.insert(position);
    }

    public void translate(float x, float y) {
        translate.setX(x);
        translate.setY(y);
    }

    public void update() {
        matrix.pushIdentity();
        matrix.translate(translate.getX(), translate.getY());
    }

    public void reset() {
        matrix.pushIdentity();
        translate.set(0, 0);
    }

    public void setColour(float r, float g, float b, float a) {
        colour[0] = r;
        colour[1] = g;
        colour[2] = b;
        colour[3] = a;
    }

    public float[] getColour() {
        return colour;
    }

    public void setLineWidth(float size) {
        glLineWidth(size);
    }

    public void render() {
        program.startProgram();
        buffer.bindBuffer();

        int arrayID = program.getAttribute("position");
        int colourID = program.getUniform("colour");
        int projectionID = program.getUniform("Projection");
        int matrixID = program.getUniform("ModelView");

        glEnableVertexAttribArray(arrayID);
        glUniformMatrix4fv(matrixID, 1, false, matrix.getModelView(), 0);
        glUniformMatrix4fv(projectionID, 1, false, matrix.getProjection(), 0);
        glUniform4fv(colourID, 1, colour, 0);
        glVertexAttribPointer(arrayID, 2, GL_FLOAT, false, 0, 0);
        glDrawArrays(GL_LINE_STRIP, 0, position.length / 2);
        glDisableVertexAttribArray(arrayID);

        buffer.unbind();
        program.endProgram();
    }
}
