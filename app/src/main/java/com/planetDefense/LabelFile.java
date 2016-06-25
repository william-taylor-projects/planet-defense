package com.planetDefense;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class LabelFile {
	private LinkedList<String> Lines = new LinkedList<String>();
	
	public LabelFile(String filename) {
		Scanner scanner = ResourceManager.Get().GetFile(filename);
		String line = null;
		
		while(true) {
			try {
				line = scanner.nextLine();
			} catch (NoSuchElementException e) {
				scanner.close();
				break;
			}
			
			if(line.length() > 0 && line.charAt(0) != '#') {
				Lines.add(line);
			}
		}
	}
	
	public LinkedList<String> GetLines() {
		LinkedList<String> ReversedLines = new LinkedList<String>();
		for(int i = Lines.size() - 1; i >= 0; --i) {
			ReversedLines.add(Lines.get(i));
		} return ReversedLines;
	}
}
