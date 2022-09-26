package com.seshu.xslt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.seshu.general.FileHelper;

public class ParseXSL {

	static HashSet<String> nodeSet = new HashSet<String>();
	public static void main(String[] args) {

		parseXML("C:\\Projects\\Seshu\\WhiteBoard\\input\\xsl\\asn_generated_sample.xml");
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
			Iterator<String> itr = nodeSet.iterator();
			System.out.println(nodeSet.size());
			String content = "";
			while(itr.hasNext()) {
				String temp = itr.next();
				content = content.concat(temp+"\n");			
			
				FileWriter fstream = new FileWriter("C:\\Projects\\Seshu\\WhiteBoard\\input\\xsl\\asn_nodesPath_out1.txt");
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(content);
				out.flush();
				out.close();
				fstream.close();
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
			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				String nodePath = printParentNode(tempNode);				
				//System.out.println(nodePath);
				nodeSet.add(nodePath);
				if (tempNode.hasAttributes()) {
					// get attributes names and values
					NamedNodeMap nodeMap = tempNode.getAttributes();
					for (int i = 0; i < nodeMap.getLength(); i++) {
						Node node = nodeMap.item(i);
						//System.out.println(nodePath+"//@"+node.getNodeName());
						nodeSet.add(nodePath+"//@"+node.getNodeName());
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