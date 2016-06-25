package com.planetDefense;

/**
 *  A class that simply manages a 2D vector point
 *  sadly java doesnt allow operator overloading
 *  No Cookies for you java !!!.
 *  
 *  As a result this class is very simple and 
 *  doesnt have a list of complex math functions
 *  however this should be added in the future.
 * 
 * @version 08/02/2014
 * @author William Taylor
 */
public class Vector2D {
	/** A float position to track the x position	*/
	private float PositionX;
	/** A float position to track the y position	*/
	private float PositionY;
	
	/**
	 * Basic copy deconstructor.
	 * @param vec a existing vector to copy.
	 */
	public Vector2D(Vector2D vec) {
		Set(vec);
	}
	
	public void normalise()	{
		float length = (float)Math.sqrt((PositionX * PositionX) + (PositionY * PositionY));
	
		PositionX = (float)PositionX/length;
		PositionY = (float)PositionY/length;
	}
	
	public void Set(Vector2D vec) {
		PositionX = vec.x();
		PositionY = vec.y();
	}
	
	public static Float Distance(Vector2D a, Vector2D b) {
		Float x = a.x() - b.x();
		Float y = a.y() - b.y();

		return(float)(Math.sqrt((x*x)) + (y*y));
	}
	
	/**
	 * Basic Constructor
	 */
	public Vector2D() {
		this(0, 0);
	}
	
	/**
	 * A constructor if the values want to be set first
	 * @param x	The initial value for PositionX;
	 * @param y The initial value for PositionX;
	 */
	public Vector2D(float x, float y) {
		Set(x, y);
	}
	
	/**
	 * A simple set function to set both positions
	 * @param x The new x position
	 * @param y The new y position
	 */
	public void Set(float x, float y) {
		PositionX = x;
		PositionY = y;
	}
	
	/**
	 *  function that will return the angle to get
	 *  the position pointing in that direction.
	 * 
	 * @param Position The position of the object
	 * @param Size The Size
	 * @return The angle.
	 */
	public static float RotateTo(Vector2D Position, Vector2D Size) {
		double xdist = (Position.x() + (float)Size.x()/2) - 615;
		double ydist = 375 - (Position.y() + (float)Size.y()/2);
		double Angle = 0.0f;
		
		if(xdist >= 0 && ydist >= 0) {
			Angle = 360 - (float)Math.toDegrees(Math.atan(xdist/ydist));
		} else if(xdist < 0 && ydist > 0) {
			Angle = (float)Math.toDegrees(Math.atan(xdist/ydist));
			Angle *= -1;
		} else if(xdist < 0 && ydist < 0) {
			Angle =  180 - (float)Math.toDegrees(Math.atan(xdist/ydist));
		} else {
			Angle = 180 - (float)Math.toDegrees(Math.atan(xdist/ydist));
		} 

		return(float)Angle;
	}
	
	/** Basic get x function */
	public float x() {
		return PositionX;
	}
	
	/** Basic get y function */
	public float y() {
		return PositionY;
	}
	
	/** Basic set x function */
	public void x(float x) {
		PositionX += x;
	}
	
	/** Basic set y function */
	public void y(float y) {
		PositionY += y;
	}
}
