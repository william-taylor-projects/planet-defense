package com.planetDefense;

import java.util.Vector;

public class LabelLine {
	private Integer Spacing;
	private GL_Text Label;
	private String Line;
	
	public LabelLine(LabelEngine engine, String text) {
		Label = new GL_Text(engine);
		Line = text;
		
		Spacing = 2;
	}
	
	public LabelLine(LabelEngine engine) {
		Label = new GL_Text(engine);
	}
	
	public String getText() {
		return Line;
	}
	
	public GL_Text GetRawGL() {
		return Label;
	}
	
	public void Reset() {
		Label.Reset();
	}
	
	public Vector2D getPosition() {
		return Label.getPosition();
	}
	
	public void Scale(float x, float y) {
		Spacing = (int)(Spacing * x);
		Label.Scale(x, y);
	}

	public void SetInitialPosition(float x, float y) {
		Label.SetInitialPosition(x, y);
	}
	
	public int GetWidth() {
		return Label.GetWidth();
	}
	
	public void SetColour(float r, float g, float b, float a) {
		Label.SetColour(r, g, b, a);
	}
	
	public void Translate(float x, float y) {
		Label.Translate(x, y);
	}
	
	public void Text(String str) {
		Line = str;
	}
	
	public void Load(int x, int y) {
		Label.Load(Line, x, y);
	}
	
	public void Update() {
		Label.Update(5);
	}
	
	public void Update(int spacing) {
		Label.Update(spacing);
	}

	public void Draw() {
		if(Label.isVisible()) {
			Label.Draw();
		}
	}
	
	public void Load(int x, int y, Vector<Vector2D> positions) {
		Label.Load(Line, x, y, positions);
	}

	public void Load(int x, int y, Vector<String> strings, Vector<Vector2D> positions) {
		Label.Load(x, y, strings, positions);
	}
}
