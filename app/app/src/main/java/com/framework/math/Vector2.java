package com.framework.math;

public class Vector2 {
    private float x;
    private float y;

    public Vector2(float x, float y) {
        set(x, y);
    }

    public Vector2(Vector2 vector) {
        set(vector);
    }

    public Vector2() {
        this(0, 0);
    }

    public void normalise()	{
        float length = (float)Math.sqrt((x * x) + (y * y));
        x = x / length;
        y = y / length;
    }

    public void set(Vector2 vector) {
        x = vector.getX();
        y = vector.getY();
    }

    @Override
    public boolean equals(Object value) {
        if(value instanceof Vector2) {
            Vector2 v = (Vector2)value;
            return (x == v.x && y == v.y);
        } else {
            return(false);
        }
    }

    public static float distance(Vector2 a, Vector2 b) {
        Float x = a.getX() - b.getX();
        Float y = a.getY() - b.getY();

        return(float)(Math.sqrt((x*x)) + (y*y));
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static float rotateTo(Vector2 position, Vector2 size) {
        double distance_x = (position.getX() + size.getX() / 2) - 615;
        double distance_y = 375 - (position.getY() + size.getY() / 2);
        double angle = 0.0f;

        if(distance_x >= 0 && distance_y >= 0) {
            angle = 360 - (float)Math.toDegrees(Math.atan(distance_x/distance_y));
        } else if(distance_x < 0 && distance_y > 0) {
            angle = (float)Math.toDegrees(Math.atan(distance_x/distance_y));
            angle *= -1;
        } else if(distance_x < 0 && distance_y < 0) {
            angle =  180 - (float)Math.toDegrees(Math.atan(distance_x/distance_y));
        } else {
            angle = 180 - (float)Math.toDegrees(Math.atan(distance_x/distance_y));
        }

        return(float)angle;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x += x;
    }

    public void setY(float y) {
        this.y += y;
    }
}
