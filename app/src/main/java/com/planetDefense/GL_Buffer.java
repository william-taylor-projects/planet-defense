package  com.planetDefense;

import static android.opengl.GLES20.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * A wrapper for the opengles VBO object.
 * This is a 2D implementation.
 * 
 * @version 08/02/2014
 * @author William Taylor
 */
public class GL_Buffer {
	/** Buffer ID & Data stored	*/
	private float[] Data;
	private int[] ID;
	
	/**
	 * Basic Constructor
	 */
	public GL_Buffer() {
		ID = new int[1];
	}
	
	/**
	 * 
	 * @param data
	 */
	public void insert(float[] data) {
		if(ID[0] == 0) {
			ByteBuffer Buffer = ByteBuffer.allocateDirect(data.length * 4);
			Buffer.order(ByteOrder.nativeOrder());
			Data = data;
			
			FloatBuffer Data = Buffer.asFloatBuffer();
			Data.put(data);
			Data.position(0);
	
			glGenBuffers(1, ID, 0);
			glBindBuffer(GL_ARRAY_BUFFER, ID[0]);
			glBufferData(GL_ARRAY_BUFFER, data.length * 4, Data, GL_STATIC_DRAW);
			glBindBuffer(GL_ARRAY_BUFFER, 0);
		}
	}
	
	/**
	 * 
	 * @param data
	 */
	public void reInsert(float[] data) {
		ByteBuffer Buffer = ByteBuffer.allocateDirect(data.length * 4);
		Buffer.order(ByteOrder.nativeOrder());
		Data = data;
		
		FloatBuffer Data = Buffer.asFloatBuffer();
		Data.put(data);
		Data.position(0);

		glBindBuffer(GL_ARRAY_BUFFER, ID[0]);
		glBufferData(GL_ARRAY_BUFFER, data.length * 4, Data, GL_DYNAMIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * 
	 */
	public void unbind() {
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * 
	 */
	public void bindBuffer() {
		glBindBuffer(GL_ARRAY_BUFFER, ID[0]);
	}
	
	/**
	 * 
	 */
	public void release() {
		glDeleteBuffers(1, ID, 0);
	}
	
	/**
	 * 
	 * @return
	 */
	public float[] getData() {
		return Data;
	}
	
	/**
	 * 
	 * @return
	 */
	public float getID() {
		return ID[0];
	}
}

