package com.seshu.xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ParseXMLToXPaths {

	static TreeSet<String> nodeSetMain = new TreeSet<String>();
	public static void main(String[] args) {

		parseXML("C:\\Projects\\Seshu\\WhiteBoard\\input\\xsl\\ASN-Sample-Out-ESB.xml");
		String allTags = "";
		Iterator<String> itrPrint = nodeSetMain.iterator();
		try {
			while(itrPrint.hasNext()) {
				allTags = allTags+itrPrint.next()+"\n";					
			}
			System.out.println(allTags);
			FileWriter writer = new FileWriter("C:\\Projects\\Seshu\\WhiteBoard\\input\\xsl\\xml_paths.txt");
			BufferedWriter out = new BufferedWriter(writer);
			out.write(allTags);
			out.flush();
			out.close();
		}catch(Exception e) {
			
		}
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

	private static void printNote(NodeList nodeList) {
		
		for (int count = 0; count < nodeList.getLength(); count++) {

			Node tempNode = nodeList.item(count);
			// Print complete path
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				String nodePath = printParentNode(tempNode);
				//nodeSetMain.add(nodePath);
				
				if (tempNode.hasAttributes()) {
					NamedNodeMap nodeMap = tempNode.getAttributes();
					for (int i = 0; i < nodeMap.getLength(); i++) {
						Node node = nodeMap.item(i);
						//nodeSetMain.add(nodePath+"//@"+node.getNodeName()); 					
					}
				}
				if (tempNode.hasChildNodes()) {
					printNote(tempNode.getChildNodes());
				}
			}
			// Print only value tag path
			if (!tempNode.hasChildNodes() && tempNode.getNodeName() != null && !tempNode.getNodeName().equals("#text")) {
				nodeSetMain.add(printParentNode(tempNode.getParentNode())+"//"+tempNode.getNodeName());
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
}