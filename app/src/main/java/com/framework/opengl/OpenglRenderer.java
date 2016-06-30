package com.framework.opengl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;

import com.framework.core.EventManager;
import com.framework.graphics.RenderQueue;
import com.framework.core.SceneManager;

public class OpenglRenderer implements Renderer {
    private EventManager eventManager;
    private SceneManager sceneManager;
    private RenderQueue renderQueue;

    public OpenglRenderer() {
        eventManager = EventManager.get();
        sceneManager = SceneManager.get();
        renderQueue = new RenderQueue();
    }

    @Override
    public void onDrawFrame(GL10 arg0) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        eventManager.update();
        sceneManager.update(renderQueue);
        renderQueue.renderObjects();
        sceneManager.change();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        OpenglTextureManager.get().clear();
        OpenglShaderManager.get().clear();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }
}
