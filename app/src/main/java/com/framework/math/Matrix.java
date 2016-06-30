package com.framework.math;

public class Matrix {
    private float[] projectionMatrix;
    private float[] modelViewMatrix;
    private float rotation = 0.0f;

    public Matrix() {
        projectionMatrix = new float[16];
        modelViewMatrix = new float[16];
        ortho(0, 0, 1280, 800);
        pushIdentity();
    }

    public Matrix(Integer width, Integer height) {
        projectionMatrix = new float[16];
        modelViewMatrix = new float[16];
        ortho(0, 0, width, height);
        pushIdentity();
    }

    public void rotate(float angle, int centreX, int centreY) {
        translate(centreX, centreY);
        android.opengl.Matrix.rotateM(modelViewMatrix, 0, angle, 0.0f, 0.0f, 1.0f);
        translate(-centreX, -centreY);
        rotation = angle;
    }

    public void rotate(float angle, float centreX, float centreY) {
        translate(centreX, centreY);
        android.opengl.Matrix.rotateM(modelViewMatrix, 0, angle, 0.0f, 0.0f, 1.0f);
        translate(-centreX, -centreY);
        rotation = angle;
    }

    public float[] getProjection() {
        return projectionMatrix;
    }

    public float[] getModelView() {
        return modelViewMatrix;
    }

    public float getRotation() {
        return rotation;
    }

    public void ortho(int x, int y, int w, int h) {
        android.opengl.Matrix.orthoM(projectionMatrix, 0, x, w, y, h, -1.0f, 1.0f);
    }

    public void ortho(int w, int h) {
        android.opengl.Matrix.orthoM(projectionMatrix, 0, 0, w, 0, h, -1.0f, 1.0f);
    }

    public void scale(float x, float y) {
        android.opengl.Matrix.scaleM(modelViewMatrix, 0, x, y, 1.0f);
    }

    public void translate(int x, int y) {
        android.opengl.Matrix.translateM(modelViewMatrix, 0, x, y, 0.0f);
    }

    public void translate(float x, float y) {
        android.opengl.Matrix.translateM(modelViewMatrix, 0, x, y, 0.0f);
    }

    public void pushIdentity()	{
        android.opengl.Matrix.setIdentityM(modelViewMatrix, 0);
    }
}
