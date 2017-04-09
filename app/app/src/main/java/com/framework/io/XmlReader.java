package com.framework.io;

import android.util.Log;

import javax.xml.parsers.*;
import org.xml.sax.*;
import org.w3c.dom.*;
import java.io.*;

public class XmlReader {
    public Document getDocument(InputStream inputStream) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document = null;

        try {
            DocumentBuilder db = factory.newDocumentBuilder();
            InputSource inputSource = new InputSource(inputStream);
            document = db.parse(inputSource);
        } catch (ParserConfigurationException e) {
            Log.e(XmlReader.class.getName(), e.toString());
        } catch (SAXException e) {
            Log.e(XmlReader.class.getName(), e.toString());
        } catch (IOException e) {
            Log.e(XmlReader.class.getName(), e.toString());
        }
        
        return document;
    }

    public String getValue(Element item, String name) {
        NodeList nodes = item.getElementsByTagName(name);
        return this.getTextNodeValue(nodes.item(0));
    }

    private String getTextNodeValue(Node node) {
        Node child;
        if (node != null) {
            if (node.hasChildNodes()) {
                child = node.getFirstChild();
                while(child != null) {
                    if (child.getNodeType() == Node.TEXT_NODE) {
                        return child.getNodeValue();
                    }
                    child = child.getNextSibling();
                }
            }
        }
        return null;
    }
}
