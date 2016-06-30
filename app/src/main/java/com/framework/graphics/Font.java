package com.framework.graphics;

import java.io.InputStream;
import java.util.ArrayList;

import org.w3c.dom.Document;

import com.framework.io.ResourceManager;
import com.framework.io.XmlReader;
import com.framework.opengl.OpenglImage;

public class Font {
    private static final int RESERVED_SPACE = 100;
    private static ArrayList<Font> fonts = new ArrayList<Font>(RESERVED_SPACE);

    private XmlReader xmlParser;
    private InputStream stream;
    private Document doc;
    private OpenglImage sprite;
    private String texture;
    private String name;

    public Font(String xmlFile, String texture_filename, String font_name) {
        boolean duplicateFound = false;
        for(Font e : fonts) {
            if(e.name.equalsIgnoreCase(name)) {
                duplicateFound = true;
                break;
            }
        }

        if(!duplicateFound) {
            stream = ResourceManager.get().getResource(xmlFile);

            xmlParser = new XmlReader();
            doc = xmlParser.getDocument(stream);

            sprite = new OpenglImage();
            sprite.load(texture_filename, texture_filename);
            texture = texture_filename;
            name = font_name;

            fonts.add(this);
        }
    }

    public static Font get(String name) {
        for(Font e : fonts) {
            if(e.name.equalsIgnoreCase(name)) {
                return e;
            }
        } return null;
    }

    public String getTextureFilename() {
        return texture;
    }

    public XmlReader getParser() {
        return xmlParser;
    }

    public Document getDocument() {
        return doc;
    }

    public InputStream getStream() {
        return stream;
    }

    public static void clear() {
        fonts.clear();
    }
}
