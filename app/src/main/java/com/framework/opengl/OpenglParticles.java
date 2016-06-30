package com.framework.opengl;

import java.util.Random;

import com.framework.math.Matrix;
import com.framework.math.Vector2;

import static android.opengl.GLES20.*;

public class OpenglParticles {
    private final String VS = "shaders/fire_vs.glsl";
    private final String FS = "shaders/fire_fs.glsl";
    private static int TranslateID;
    private static int PositionID;
    private static int DestinationID;
    private static int DirectionID;
    private static int ProjectionID;
    private static int MatrixID;
    private static int ColourID;
    private static int TimeID;

    private OpenglBuffer buffer = new OpenglBuffer();
    private OpenglProgram program;
    private Matrix matrix = new Matrix();
    private Integer number;
    private Float time;

    private Vector2 translation = new Vector2();
    private Vector2 destination = new Vector2();
    private Vector2 position = new Vector2();

    public OpenglParticles() {
        program = OpenglShaderManager.get().getShader(VS, FS);
        number = 0;
        time = 10f;
    }

    public void initialiseParticles(int number, float x, float y) {
        float[] pos = new float[2 + (number * 2)];
        pos[0] = x;
        pos[1] = y;

        Random rand = new Random();
        rand.setSeed(rand.nextInt(100));

        this.number = number;
        for(int i = 0; i < number; i++) {
            pos[i + 2] = (float)rand.nextInt(20000)/20000;
            pos[i + 3] = (float)rand.nextInt(20000)/20000;
            pos[i + 2] *= rand.nextInt(10) + 1;
            pos[i + 3] *= rand.nextInt(10) + 1;

        }

        position.set(x, y);
        buffer.insert(pos);
    }

    public void setPosition(float x, float y) {
        translation.set(x, y);
    }

    public void reset() {
        time = 0.0f;
    }

    public void update() {
        time += 0.02f;
    }

    public void setDestinations(float x, float y) {
        destination.set(x, y);
    }

    public void startRender() {
        program.startProgram();

        if(DestinationID == 0) {
            DestinationID = program.getUniform("destination");
            DirectionID = program.getUniform("Translation");
            ProjectionID = program.getUniform("Projection");
            MatrixID = program.getUniform("ModelView");
            ColourID = program.getUniform("Colour");
            TimeID = program.getUniform("Time");

            TranslateID = program.getAttribute("Translate");
            PositionID = program.getAttribute("Position");
        }

        glEnableVertexAttribArray(TranslateID);
        glEnableVertexAttribArray(PositionID);
    }

    public void render() {
        if(time < 10.0f) {
            buffer.bindBuffer();
            glVertexAttribPointer(TranslateID, 2, GL_FLOAT, false, 8, 8);
            glVertexAttribPointer(PositionID, 2, GL_FLOAT, false, 0, 0);
            glUniform2f(DirectionID, translation.getX(), translation.getY());

            glUniformMatrix4fv(ProjectionID, 1, false, matrix.getProjection(), 0);
            glUniformMatrix4fv(MatrixID, 1, false, matrix.getModelView(), 0);
            glUniform4f(ColourID, 1.0f, 0.5f, 0.0f, 1.0f);
            glUniform2f(DestinationID, destination.getX(), destination.getY());
            glUniform1f(TimeID, time);

            glDrawArrays(GL_POINTS, 0, number);

            buffer.unbind();
        }
    }

    public void endRender() {
        glDisableVertexAttribArray(TranslateID);
        glDisableVertexAttribArray(PositionID);

        program.endProgram();
    }
}
