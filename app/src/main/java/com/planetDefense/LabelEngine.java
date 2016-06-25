package com.planetDefense;

import java.io.InputStream;
import java.util.LinkedList;

import org.w3c.dom.Document;


public class LabelEngine {
	private static LinkedList<LabelEngine> Fonts;
	private XMLReader XmlParser;
	private InputStream Stream;
	private Document DocFile;
	private GL_Image Sprite;
	private String Texture;
	private String Name;
	
	public LabelEngine(String xmlFile, String texture, String name) {
		if(Fonts == null) {
			 Fonts = new LinkedList<LabelEngine>();
		}
		
		XmlParser = new XMLReader();
		Sprite = new GL_Image();
		Sprite.load(texture, texture);
		
		Stream = ResourceManager.Get().GetResource(xmlFile);	
		DocFile = XmlParser.getDocument(Stream);
		Texture = texture;
		Name = name;
		
		int Duplicates = 0;
		for(LabelEngine e : Fonts) {
			if(e.Name.equalsIgnoreCase(name)) {
				Duplicates++;
			}
		}			
		
		if(Duplicates == 0) {
			Fonts.add(this);
		}
	}
	
	public static LabelEngine Get(String name) {
		for(LabelEngine e : Fonts) {
			if(e.Name.equalsIgnoreCase(name)) {
				return e;
			}
		} return null;
	}
	
	public String GetTexture() {
		return Texture;
	}
	
	public XMLReader GetParser() {
		return XmlParser;
	}
	
	public Document GetDocument() {
		return DocFile;
	}
	
	public InputStream GetStream() {
		return Stream;
	}

	public static void Clear() {
		Fonts.clear();
		Fonts = new LinkedList<LabelEngine>();
	}
}
