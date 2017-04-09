package com.framework.opengl;

import static android.opengl.GLES20.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class OpenglBuffer {
    private float[] glBuffer;
    private int[] glID;

    public OpenglBuffer() {
        glID = new int[1];
    }

    public void insert(float[] data) {
        if(glID[0] == 0) {
            ByteBuffer Buffer = ByteBuffer.allocateDirect(data.length * 4);
            Buffer.order(ByteOrder.nativeOrder());
            glBuffer = data;

            FloatBuffer Data = Buffer.asFloatBuffer();
            Data.put(data);
            Data.position(0);

            glGenBuffers(1, glID, 0);
            glBindBuffer(GL_ARRAY_BUFFER, glID[0]);
            glBufferData(GL_ARRAY_BUFFER, data.length * 4, Data, GL_STATIC_DRAW);
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        }
    }

    public void reinsert(float[] data) {
        ByteBuffer Buffer = ByteBuffer.allocateDirect(data.length * 4);
        Buffer.order(ByteOrder.nativeOrder());
        glBuffer = data;

        FloatBuffer Data = Buffer.asFloatBuffer();
        Data.put(data);
        Data.position(0);

        glBindBuffer(GL_ARRAY_BUFFER, glID[0]);
        glBufferData(GL_ARRAY_BUFFER, data.length * 4, Data, GL_DYNAMIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void bindBuffer() {
        glBindBuffer(GL_ARRAY_BUFFER, glID[0]);
    }

    public void release() {
        glDeleteBuffers(1, glID, 0);
    }

    public float[] getGlBuffer() {
        return glBuffer;
    }

    public float getGlID() {
        return glID[0];
    }
}

