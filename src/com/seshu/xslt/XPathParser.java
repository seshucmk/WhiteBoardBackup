package com.seshu.xslt;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XPathParser {
   
   public static void main(String[] args) {
      
      try {
         File inputFile = new File("C:\\Projects\\Seshu\\WhiteBoard\\input\\xsl\\asn_nodesPath_out.txt");
         Document doc = null;
         // Getting error: java.util.ServiceConfigurationError: javax.xml.xpath.XPathFactory: Provider oracle.xml.xpath.JAXPXPathFactory not found
         XPath xPath =  XPathFactory.newInstance().newXPath();

         String expression = "ProcessAdvanceShipNotice//DataArea//AdvanceShipNotice";	        
         NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(
            doc, XPathConstants.NODESET);
         System.out.println("");
         
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}