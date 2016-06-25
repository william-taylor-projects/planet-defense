package com.planetDefense;

/**
 * A simple matrix class to hide how you interact 
 * with matrices with opengl es. As well as fixing 
 * so errors that arnt normally dealt with.
 * 
 * For instance also rotating at the center 
 * of the object.
 * 
 * @version 08/02/2014
 * @author William Taylor
 */
public class Matrix {
	/** Projection Matrix, it is set anyway */
	private float[] ProjectionMatrix;
	/** ModelView Matrix, for transforming the object */
	private float[] ModelViewMatrix;
	/** A variable to keep track of the Rotation */
	private float Rotation = 0.0f;
	
	/**
	 * Default constructor to initialise variables and set the projection matrix
	 */
	public Matrix() {
		ProjectionMatrix = new float[16];
		ModelViewMatrix = new float[16];
		Ortho(0, 0, 1280, 800);
		LoadIdentity();
	}
	
	/**
	 * A alternative that allows users to set the projection matrix.
	 * @param width Width of the drawing surface
	 * @param height Height of the drawing Surface
	 */
	public Matrix(Integer width, Integer height) {
		ProjectionMatrix = new float[16];
		ModelViewMatrix = new float[16];
		Ortho(0, 0, width, height);
		LoadIdentity();
	}
	
	/**
	 * A simple rotate function.
	 * 
	 * @param The angle to rotate by (in degrees)
	 * @param centreX The centre of the object (x).
	 * @param centreY The centre of the object (y).
	 */
	public void Rotate(float angle, int centreX, int centreY) {
		Translate(centreX, centreY);
		android.opengl.Matrix.rotateM(ModelViewMatrix, 0, angle, 0.0f, 0.0f, 1.0f);
		Translate(-centreX, -centreY);
		Rotation = angle;
	}
	
	/**
	 * A simple rotate function.
	 * 
	 * @param angle The angle to rotate by (in degrees)
	 * @param centreX The centre of the object (as a float)
	 * @param centreY  The centre of the object (as a float)
	 */
	public void Rotate(float angle, float centreX, float centreY) {
		Translate(centreX, centreY);
		android.opengl.Matrix.rotateM(ModelViewMatrix, 0, angle, 0.0f, 0.0f, 1.0f);
		Translate(-centreX, -centreY);
		Rotation = angle;
	}
	
	/**
	 * A get matrix function that returns the whole matrix which is 
	 * is equal to the ModelView matrix * ProjectionMatrix;
	 * 
	 * @return The combined matrix.
	 */
	public float[] GetProjection() {
		return ProjectionMatrix;
	}
	
	public float[] GetModelView() {
		return ModelViewMatrix;
	}
	
	/** Simple get rotation function */
	public float GetRotation() {
		return Rotation;
	}
	
	/** A simple replacement function for setting the orth projection matrix */
	public void Ortho(int x, int y, int w, int h) {
		android.opengl.Matrix.orthoM(ProjectionMatrix, 0, x, w, y, h, -1.0f, 1.0f);
	}
	
	/** Overloaded alternative for the original Orth function*/
	public void Ortho(int w, int h) {
		android.opengl.Matrix.orthoM(ProjectionMatrix, 0, 0, w, 0, h, -1.0f, 1.0f);
	}
	
	/** A simple replacement function for scaling a object*/
	public void Scale(float x, float y) {
		android.opengl.Matrix.scaleM(ModelViewMatrix, 0, x, y, 1.0f);
	}
	
	/** A simple replacement function for translating a object*/
	public void Translate(int x, int y) {
		android.opengl.Matrix.translateM(ModelViewMatrix, 0, x, y, 0.0f);
	}
	
	/** Alternative just incase floats are needed */
	public void Translate(float x, float y) {
		android.opengl.Matrix.translateM(ModelViewMatrix, 0, x, y, 0.0f);
	}
	
	/** A simple function that resets the Modelview matrix */
	public void LoadIdentity()	{
		android.opengl.Matrix.setIdentityM(ModelViewMatrix, 0);
	}
}
