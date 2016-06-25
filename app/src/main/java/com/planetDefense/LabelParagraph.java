package com.planetDefense;

import java.util.*;

public class LabelParagraph {
	private LinkedList<LabelLine> Labels = new LinkedList<LabelLine>();
	private LinkedList<String> Lines = new LinkedList<String>();
	
	private LabelEngine Engine;
	private LabelFile File;
	
	public LabelParagraph(LabelEngine e, String filename) {
		File = new LabelFile(filename);
		Lines = File.GetLines();
		Engine = e;
	}
	
	public void add(Vector<Object> objects) {
		for(LabelLine l : Labels) {
			objects.add(l);
		}
	}
	
	public void setColour(float r, float g, float b, float a) {
		for(LabelLine l : Labels) {
			l.SetColour(r, g, b, a);
		}
	}
	 
	public void load(int x, int y) {
		int NewY = y;
		for(String line : Lines) {
			LabelLine label = new LabelLine(Engine, line);
			label.Load(x, NewY);
			NewY += label.GetRawGL().GetHeight();
			Labels.add(label);
		}
	}
	
	public void scale(float x, float y) {
		for(LabelLine Label : Labels) {
			Label.Scale(x, y);
		}
	}
	
	public void update() {
		for(LabelLine Label : Labels) {
			Label.Update();
		}
	}
	
	public void draw() {
		for(LabelLine Label : Labels) {
			Label.Draw();
		}
	}

	public int getLineNumber() {
		return Labels.size();
	}
	
	public LabelLine getLine(int i) {
		return Labels.get(i);
	}
}
