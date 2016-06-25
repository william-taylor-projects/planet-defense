package  com.planetDefense;

import java.util.Vector;
import android.util.Log;

/**
 * A simple error logger isnt widely used but
 * is used for debugging shaders.
 * 
 * @version 08/02/2014
 * @author William Taylor
 */
public class ErrorLog {
	/** A vector to contain all error logs */
	private Vector<String> OutputFile;
	/** A static instance as this is a singleton */
	private static ErrorLog Instance;

	/**
	 * Basic Constructor that is used by the get
	 * method when the object is requested.
	 */
	private ErrorLog() {
		OutputFile = new Vector<String>();
		OutputFile.add("- V1.0");
	}
	
	/**
	 * A Simple get function as thi class is a singleton
	 * @return The singletons instance
	 */
	public static ErrorLog get() {
		if(Instance == null) {
			Instance = new ErrorLog();
		} return Instance;
	}
	
	/**
	 * A simple print function that iterates through
	 * the vector and prints each message accordingly.
	 */
	public void Print() {
		for(int i = 0; i < OutputFile.size(); i++) {
			if(i == 0) {
				Log.e("Error Logger", OutputFile.get(i));
			} else {
				Log.e("Error " + i, OutputFile.get(i));
			}
		}
	}
	
	/**
	 * A simple write function that pushes 
	 * the string into the vector.
	 * 
	 * @param data The error to be written
	 */
	public void Write(String data) {
		OutputFile.add(data);
	}
}
