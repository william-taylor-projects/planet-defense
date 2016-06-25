package com.planetDefense;

import android.content.Context;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Vector;
import java.io.*;

/**
 * This is a resource manager that should be used
 * to grab files in the assets folder.
 * 
 * @version 08/02/2014
 * @author William Taylor
 */
public class ResourceFile {
	/** A buffered reader for reading a file */
	private BufferedReader in;
	/** Just the filename */
	private String Filename;
	
	/**
	 * A basic constructor that just tell where to read the file.
	 * @param filename The location of the file.
	 */
	public ResourceFile(String filename) {
		Filename = filename;
	}
	
	/**
	 * Allows the user to make sure the file exists.
	 * @return a boolean to say if it does exist.
	 */
	public boolean Exists() {
		Context c = ResourceManager.Get().GetContext();
		return(new File(c.getFilesDir(), Filename).exists());
	}
	
	/**
	 * Reads the file and packs each line in a vector.
	 * 
	 * @return returns a vector where each element is a line/
	 */
	public Vector<String> ReadFile() {
		Scanner File = ResourceManager.Get().GetFile(Filename);	
		Vector<String> lines = new Vector<String>();	
		while(true) {
			String line = null;
			try {
				line = File.nextLine();
			} catch (NoSuchElementException e) {
				File.close();
				break;
			}

			if(!line.isEmpty() && line.charAt(0) != '#') {
				int last = line.indexOf(' ');
				if(last > 0) {
					lines.add(line.substring(0, last));
				} 
			} else if(!line.isEmpty() && line.charAt(0) != '#' && line.charAt(0) != ' ') {
				lines.add(line);
			} else {
				continue;
			}
		} return lines;
	}
	
	/**
	 * Writes each object in the objects array the to the file.
	 * @param objects an array containing all the data.
	 */
	public void Write(Vector<String> objects) {
		// Grab the games context
		Context c = ResourceManager.Get().GetContext();
		try {		
			BufferedWriter out = new BufferedWriter(new FileWriter(new File(c.getFilesDir(), Filename)));
			for(String o : objects) {
				if(o != null) {
					out.write(o);
					out.newLine();
				}
			} out.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	/** Just prepates the file to read	 */
	public void PrepareToRead() {
		Context c = ResourceManager.Get().GetContext();
		try {
			in = new BufferedReader(new FileReader(new File(c.getFilesDir(), Filename)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/** Closes the file after you have pushed the data to the file.	 */
	public void Close() {
		try {
			in.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the next line in the file.
	 * @return a string containing the file.
	 */
	public String ReadLine() {		
		try {
			return in.readLine();
		} catch (Exception e) {
			System.out.println(e.toString());
		} return "";
	}
}
