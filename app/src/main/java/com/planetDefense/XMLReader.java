package com.planetDefense;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.xml.sax.SAXException;
import org.xml.sax.InputSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

/**
 *  This class simply reads a xml file and
 *  returns it as a Document File.
 *  
 *  Used in the framework to read fonts.
 * 
 * @version 08/02/2014
 * @author William Taylor
 */
public class XMLReader {
	/** No variables the class passes and variable created back as a return value */
	
	/**
	 * This is the main function it simply reads the file.
	 * i think this class should be extended in future.
	 * 
	 * @param inputStream A stream to the xml file
	 * @return The file as a document object
	 */
    public Document getDocument(InputStream inputStream) {
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document = null;
        
        // Can cause errors
        try {
            DocumentBuilder db = factory.newDocumentBuilder();
            InputSource inputSource = new InputSource(inputStream);
            document = db.parse(inputSource);
        } catch (ParserConfigurationException e) {  
        	System.out.println(e.toString());
        } catch (SAXException e) {
        	System.out.println(e.toString());
        } catch (IOException e) {
        	System.out.println(e.toString());
        }
        
        return document;
    }
 
    /**
     * 
     * @param item The element in the file.
     * @param name The name of the object to return
     * @return it returns the first instance of said object.
     */
    public String getValue(Element item, String name) {
    	// Get first element in the fire we dont want to skip any.
        NodeList nodes = item.getElementsByTagName(name);
        return this.getTextNodeValue(nodes.item(0));
    }
 
    /**
     * Goes through each child element in a xml file.
     * and returns the value contained as a string.
     * This is used so we dont ever cast the value.
     * 
     * @param node The value to get from a list of elements
     * @return The elements value as a string.
     */
    private final String getTextNodeValue(Node node) {
        Node child;
        if (node != null) {
            if (node.hasChildNodes()) {
                child = node.getFirstChild();
                // Go through all child nodes.
                while(child != null) {
                    if (child.getNodeType() == Node.TEXT_NODE) {
                        return child.getNodeValue();
                    }
                    child = child.getNextSibling();
                }
            }
        }
        // Indicates fail.
        return null;
    }
}
