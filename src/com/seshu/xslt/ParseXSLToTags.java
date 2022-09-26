package com.seshu.xslt;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ParseXSLToTags {

	public static void main(String[] args) {

		parseXML("C:\\Projects\\Seshu\\WhiteBoard\\input\\xsl\\ProcessAdvanceShipNotice.xsl");
	}

	public static void parseXML(String fileName) {

		ByteArrayInputStream pXMLStream = null;
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		Document myDocument = null;
		String encoding = null;

		try {
			String xmlText = readXMLFile(fileName);
			int start = xmlText.indexOf("encoding='");
			if (start > -1) {
				encoding = xmlText.substring(start + 10, xmlText.indexOf("'", start + 11));
				pXMLStream = new ByteArrayInputStream(xmlText.getBytes(encoding));
			} else {
				pXMLStream = new ByteArrayInputStream(xmlText.getBytes());
			}

			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();

			myDocument = builder.parse(pXMLStream);
			if (myDocument.hasChildNodes()) {
				printNote(myDocument.getChildNodes());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String readXMLFile(String filePath) throws IOException {
		FileReader fileReader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		StringBuffer sb = new StringBuffer();

		String strLine = br.readLine();
		while (strLine != null) {
			sb.append(strLine);
			strLine = br.readLine();
		}
		if (br != null) {
			br.close();
		}
		return sb.toString();

	}

	private static void printNote(NodeList nodeList) {

		for (int count = 0; count < nodeList.getLength(); count++) {

			Node tempNode = nodeList.item(count);
			if (tempNode.getNodeType() == Node.ELEMENT_NODE
					&& tempNode.getNodeName().equalsIgnoreCase("description")) {				
				//System.out.println(tempNode.getTextContent());
			}
			if(tempNode.getNodeType() == Node.TEXT_NODE) {				
				if(tempNode.getNodeValue() != null) {
					//System.out.println(tempNode.getNodeValue());
				}
			}
			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				String nodePath = printParentNode(tempNode);
				//System.out.println(nodePath);
				if (tempNode.hasAttributes()) {
					// get attributes names and values
					NamedNodeMap nodeMap = tempNode.getAttributes();
					for (int i = 0; i < nodeMap.getLength(); i++) {
						Node node = nodeMap.item(i);
						System.out.println(nodePath+"//@"+node.getNodeName());
						
						Node namedNode = nodeMap.item(i);
						if(node.getNodeValue() != null) {
							System.out.println(node.getNodeValue());
						}
					}
				}
				if (tempNode.hasChildNodes()) {
					printNote(tempNode.getChildNodes());
				}				
			}

		}

	}

	private static String printParentNode(Node node) {
		String parentString = "";
		if (node.getParentNode() != null && (!node.getParentNode().getNodeName().equalsIgnoreCase("#document"))) {
			parentString = printParentNode(node.getParentNode()) + "/" + parentString + "/" + node.getNodeName();
		} else
			parentString = parentString + node.getNodeName();
		return parentString;
	}
}