package com.planetDefense;

import com.planetDefense.GL_TextureManager.Sprite;
import java.util.Vector;

import static android.opengl.GLES20.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class GL_Text {
	private final String vs = "shaders/text_vs.glsl";
	private final String fs = "shaders/text_fs.glsl";
	private Integer TextLength;
	private Integer TextHeight;
	
	private float[] colour = new float[4];
		
	private GL_Buffer PositionBuffer = new GL_Buffer();
	private GL_Program Shader;
	
	private Vector2D InitialTranslate = new Vector2D(0, 0);
	private Vector2D Translate = new Vector2D();
	
	private LabelEngine Font;
	private Sprite Sheet;
	private Matrix  matrix = new Matrix();
	private Boolean Loaded = false;
	private Integer Count;
	private String string = "";
	
	public GL_Text(LabelEngine labelEngine) {
		Shader = new GL_Program();
		Shader.PushShaders(vs, fs);
		
		matrix.LoadIdentity();
		SetColour(1, 1, 1, 1);
		Font = labelEngine;		
	}
	
	public void SetInitialPosition(float x, float y) {
		this.InitialTranslate.Set(x, y);
		this.Translate.Set(x, y);
	}
	
	public String getString() {
		return string;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void Scale(float x, float y) {
		
	}
	
	/**
	 * 
	 * @param string
	 * @param x
	 * @param y
	 */
	public void Load(String string, int x, int y) {
		Document document = Font.GetDocument();
		XMLReader parser = Font.GetParser();
		this.string = string;
	
		NodeList nodeList = document.getElementsByTagName("character");
		char[] letters = string.toCharArray();
		
		Count = letters.length * 6;
		TextHeight = 0;
		TextLength = 0;
		
		float[] data = new float[24 * letters.length];
		
		int tex_x = 0;
		int tex_y = 0;
		int b = 0;
		
		Sheet = GL_TextureManager.get().GetSprite(Font.GetTexture());
		  
		for(int i = 0; i < letters.length; i++) {
			int Value = (int)letters[i];
			Element e = (Element)nodeList.item(Value - 32);
			
			int x2 = Integer.parseInt(parser.getValue(e, "x"));
		    int y2 = Integer.parseInt(parser.getValue(e, "y"));
		    int h2 = Integer.parseInt(parser.getValue(e, "height"));
		    int w2 = Integer.parseInt(parser.getValue(e, "width"));
		    
		    float realX = (float)x2/Sheet.Width;
		    float realY = (float)y2/Sheet.Height;
		    float realW = (float)(x2 + w2)/Sheet.Width;
		    float realH = (float)(y2 + h2)/Sheet.Height;
		    
		    data[b + 0] = tex_x;	 	data[b + 2] = realX;
		    data[b + 1] = tex_y;	 	data[b + 3] = realH;
		    data[b + 4] = tex_x;	 	data[b + 6] = realX;
		    data[b + 5] = tex_y+h2;		data[b + 7] = realY;
		    data[b + 8] = tex_x+w2;	 	data[b + 10] = realW;
		    data[b + 9] = tex_y;	 	data[b + 11] = realH;	
		    
		    data[b + 12] = tex_x+w2;	data[b + 14] = realW;
		    data[b + 13] = tex_y+h2;	data[b + 15] = realY;
		    data[b + 16] = tex_x+w2;	data[b + 18] = realW;
		    data[b + 17] = tex_y;		data[b + 19] = realH;
		    data[b + 20] = tex_x;		data[b + 22] = realX;
		    data[b + 21] = tex_y+h2;	data[b + 23] = realY;
		    
		    TextLength = (int)data[b + 12];
		    TextHeight = h2;
		    
		    tex_x += w2;		    
		    b += 24;
		}
		
		if(Loaded) {
			PositionBuffer.reInsert(data);
		} else {
			PositionBuffer.insert(data);
			Loaded = true;
		}
		
		InitialTranslate.Set(x, y);
	    Translate.Set(x, y); 
	}
	
	public void Load(String string, int x, int y, Vector<Vector2D> positions) {
		Document document = Font.GetDocument();
		XMLReader parser = Font.GetParser();
	
		NodeList nodeList = document.getElementsByTagName("character");
		char[] letters = string.toCharArray();
		
		Count = letters.length * 6;
		TextHeight = 0;
		TextLength = 0;
		
		float[] data = new float[24 * letters.length];
		
		int b = 0;
		
		Sheet = GL_TextureManager.get().GetSprite(Font.GetTexture());
		  
		for(int i = 0; i < letters.length; i++) {
			int Value = (int)letters[i];
			Element e = (Element)nodeList.item(Value - 32);
			
			int x2 = Integer.parseInt(parser.getValue(e, "x"));
		    int y2 = Integer.parseInt(parser.getValue(e, "y"));
		    int h2 = Integer.parseInt(parser.getValue(e, "height"));
		    int w2 = Integer.parseInt(parser.getValue(e, "width"));
		    
		    float realX = (float)x2/Sheet.Width;
		    float realY = (float)y2/Sheet.Height;
		    float realW = (float)(x2 + w2)/Sheet.Width;
		    float realH = (float)(y2 + h2)/Sheet.Height;
		    
		    Vector2D position = positions.get(i);
		   
		    float tex_x = position.x() - (w2/2);
		    float tex_y = position.y();
		    
		    data[b + 0] = tex_x;	 	data[b + 2] = realX;
		    data[b + 1] = tex_y;	 	data[b + 3] = realH;
		    data[b + 4] = tex_x;	 	data[b + 6] = realX;
		    data[b + 5] = tex_y+h2;		data[b + 7] = realY;
		    data[b + 8] = tex_x+w2;	 	data[b + 10] = realW;
		    data[b + 9] = tex_y;	 	data[b + 11] = realH;	
		    
		    data[b + 12] = tex_x+w2;	data[b + 14] = realW;
		    data[b + 13] = tex_y+h2;	data[b + 15] = realY;
		    data[b + 16] = tex_x+w2;	data[b + 18] = realW;
		    data[b + 17] = tex_y;		data[b + 19] = realH;
		    data[b + 20] = tex_x;		data[b + 22] = realX;
		    data[b + 21] = tex_y+h2;	data[b + 23] = realY;
		    
		    TextLength = (int)data[b + 12];
		    TextHeight = h2;
		    		    
		    b += 24;
		}
		
		if(Loaded) {
			PositionBuffer.reInsert(data);
		} else {
			PositionBuffer.insert(data);
			Loaded = true;
		}
		
		InitialTranslate.Set(x, y);
	    Translate.Set(x, y); 
	}
	
	public boolean isVisible() {
		if(Translate.y() + TextHeight >= 0 && Translate.y() <= 800) {
			return true;
		} return false;
	}

	public void SetColour(float r, float g, float b, float a) {
		colour[0] = r;
		colour[1] = g;
		colour[2] = b;
		colour[3] = a;
	}
	
	
	public Vector2D getPosition() {
		return Translate;
	}
	
	public void Update(int spacing) {
		this.matrix.LoadIdentity();
		this.matrix.Translate(Translate.x(), Translate.y());
	}
	
	static int ProjectionID = 0;
	static int PositionID = 0;
	static int MatrixID = 0;
	static int ColourID = 0;
	
	public void Draw() {
		PositionBuffer.bindBuffer();
		Shader.startProgram();
		
		glBindTexture(GL_TEXTURE_2D, Sheet.TexID[0]);
		
		if(ProjectionID == 0 && PositionID == 0 && MatrixID == 0)
		{
			PositionID = Shader.getAttribute("position");
			ProjectionID = Shader.getUniform("Projection");
			MatrixID = Shader.getUniform("ModelView");
			ColourID = Shader.getUniform("Shade");
		}
		
		glEnableVertexAttribArray(PositionID);
		glVertexAttribPointer(PositionID, 4,  GL_FLOAT, false, 0, 0);
		glUniformMatrix4fv(ProjectionID, 1, false, matrix.GetProjection(), 0);
		glUniformMatrix4fv(MatrixID, 1, false, matrix.GetModelView(), 0);
		glUniform4fv(ColourID, 1, colour, 0);
		
		glDrawArrays(GL_TRIANGLE_FAN, 0, Count);
		glDisableVertexAttribArray(PositionID);
	}

	/** */
	public void Translate(float x, float y) {
		Translate.Set(x, y);
	}
	
	public void Reset() {
		this.matrix.LoadIdentity();
		this.matrix.Translate(InitialTranslate.x(), InitialTranslate.y());
		Translate.Set(InitialTranslate);
		
	}
	
	public Vector2D getTranslate() {
		return Translate;
	}
	
	public int GetWidth() {
		return TextLength;
	}
	
	public int GetHeight() {
		return TextHeight;
	}
	
	public void beginProgram() {
		Shader.startProgram();
	
		if(ProjectionID == 0 && PositionID == 0 && MatrixID == 0)
		{
			PositionID = Shader.getAttribute("position");		
			ProjectionID = Shader.getUniform("Projection");
			MatrixID = Shader.getUniform("ModelView");
			ColourID = Shader.getUniform("Shade");
		}
		
		glUniformMatrix4fv(ProjectionID, 1, false, matrix.GetProjection(), 0);
	}
	
	public void startRender() {		
		glBindTexture(GL_TEXTURE_2D, Sheet.TexID[0]);
		
		PositionBuffer.bindBuffer();
		glVertexAttribPointer(PositionID, 4,  GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(PositionID);
		
		glUniformMatrix4fv(MatrixID, 1, false, matrix.GetModelView(), 0);
		glUniform4fv(ColourID, 1, colour, 0);
		glDrawArrays(GL_TRIANGLES, 0, Count);
	}
	
	public void endProgram() {
		glDisableVertexAttribArray(PositionID);
		Shader.endProgram();
	}

	public void Load(int x, int y, Vector<String> strings, Vector<Vector2D> positions) {
		Document document = Font.GetDocument();
		XMLReader parser = Font.GetParser();
	
		NodeList nodeList = document.getElementsByTagName("character");
	
		Integer stringLength = 0;
		for(int i = 0; i < strings.size(); i++) {
			stringLength += strings.get(i).length();
		}
		
		float[] data = new float[24 * stringLength];
		Count = stringLength * 6;

		TextHeight = 0;
		TextLength = 0;
		
		Sheet = GL_TextureManager.get().GetSprite(Font.GetTexture());    
	    int b = 0;
		for(int i = 0; i < strings.size(); i++) {
			char[] letters = strings.get(i).toCharArray();			
			
			Integer width = 0;
			for(int z = 0; z < letters.length; z++) {
				Element e = (Element)nodeList.item((int)letters[z] - 32);
			    int w2 = Integer.parseInt(parser.getValue(e, "width"));
			    width += w2;			   
			}

			  
		    Vector2D position = positions.get(i);
		
		    float tex_x = position.x() - (width/2);
		    float tex_y = position.y();
			    
			for(int z = 0; z < letters.length; z++) {
				Element e = (Element)nodeList.item((int)letters[z] - 32);
				
				int x2 = Integer.parseInt(parser.getValue(e, "x"));
			    int y2 = Integer.parseInt(parser.getValue(e, "y"));
			    int h2 = Integer.parseInt(parser.getValue(e, "height"));
			    int w2 = Integer.parseInt(parser.getValue(e, "width"));
			    		    
			    float realX = (float)x2/Sheet.Width;
			    float realY = (float)y2/Sheet.Height;
			    float realW = (float)(x2 + w2)/Sheet.Width;
			    float realH = (float)(y2 + h2)/Sheet.Height;			  
			    
			    data[b + 0] = tex_x;	 	data[b + 2] = realX;
			    data[b + 1] = tex_y;	 	data[b + 3] = realH;
			    data[b + 4] = tex_x;	 	data[b + 6] = realX;
			    data[b + 5] = tex_y+h2;		data[b + 7] = realY;
			    data[b + 8] = tex_x+w2;	 	data[b + 10] = realW;
			    data[b + 9] = tex_y;	 	data[b + 11] = realH;	
			    
			    data[b + 12] = tex_x+w2;	data[b + 14] = realW;
			    data[b + 13] = tex_y+h2;	data[b + 15] = realY;
			    data[b + 16] = tex_x+w2;	data[b + 18] = realW;
			    data[b + 17] = tex_y;		data[b + 19] = realH;
			    data[b + 20] = tex_x;		data[b + 22] = realX;
			    data[b + 21] = tex_y+h2;	data[b + 23] = realY;
			    
			    TextLength = (int)data[b + 12];
			    TextHeight = h2;
			    		
			    tex_x += w2;
			    b += 24;
			}
		}
		
		if(Loaded) {
			PositionBuffer.reInsert(data);
		} else {
			PositionBuffer.insert(data);
			Loaded = true;
		}
		
		InitialTranslate.Set(x, y);
	    Translate.Set(x, y); 
	}
}
