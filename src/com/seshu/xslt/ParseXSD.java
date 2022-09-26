package com.seshu.xslt;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import oracle.xml.parser.v2.XMLElement;
import oracle.xml.parser.v2.XMLText;

public class ParseXSD {
    public static void main(String args[]) { 
        try { 
            // parse the document
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File("C:\\Projects\\Seshu\\WhiteBoard\\input\\xsl\\ProcessAdvanceShipNotice.xsd")); 
            //NodeList list = doc.getElementsByTagName("xs:element"); 
            //doc.
            Element element = (Element)doc.getChildNodes().item(2);            
            NodeList list = element.getChildNodes();//ElementsByTagName("xs:element");
            //NodeList elements = (NodeList)all.item(2);
            //loop to print data
            for(int i = 0 ; i < list.getLength(); i++)
            {
            	Node node = list.item(i);
            	getNode(node);
            	//System.out.println(first.getNodeName());
            	if(node.hasAttributes())
                {
            		//System.out.println(first.getNodeName());
                    /*String nm = first.getAttribute("name"); 
                    System.out.println(nm); 
                    String nm1 = first.getAttribute("type"); 
                    System.out.println(nm1);*/ 
                }
            }
            //System.out.println("ddd");
        } 
        catch (ParserConfigurationException e) 
        {
            e.printStackTrace();
        }
        catch (SAXException e) 
        { 
            e.printStackTrace();
        }
        catch (IOException ed) 
        {
            ed.printStackTrace();
        }
    }
    
    public static String getNode(Node node) {
    	System.out.println(node.getNodeName()+": "+node.getNodeValue());
    	if(node.hasChildNodes()) {    		
    		return getNode(node);
    	}
    	else
    		return node.getNodeName();
    }
}