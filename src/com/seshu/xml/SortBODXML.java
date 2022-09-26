package com.seshu.xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SortBODXML {
	static TreeSet<String> nodeSetMain = new TreeSet<String>();
	// Constants
	static String inputFile = "C:\\Projects\\Seshu\\WhiteBoard\\input\\xsl\\shipTo.xml";
	static String outputFile = "C:\\Projects\\Seshu\\WhiteBoard\\input\\xsl\\shipTo_Out.xml";
	static String tagsFile = "C:\\Projects\\Seshu\\WhiteBoard\\input\\xsl\\shipTo_alltags.txt";
	static String bod = "SyncShipToPartyMaster";
	
	public static void main(String[] args) {
		
		TreeSet<String> nodeSet = new TreeSet<String>();
		TreeSet<String> lineSet = new TreeSet<String>();
		TreeMap<Integer, String> endTagLevels = new TreeMap<Integer, String>();
		String content = "";
		try {
			parseXML(inputFile);			
			Iterator<String> itr = nodeSetMain.iterator();
			if(!itr.hasNext())
				return;
			
			// Print all tags...start
			boolean print = true;
			if(print) {			
				String allTags = "";
				Iterator<String> itrPrint = nodeSetMain.iterator();
				while(itrPrint.hasNext()) {
					allTags = allTags+itrPrint.next()+"\n";					
				}
				System.out.println(allTags);
				FileWriter writer = new FileWriter(tagsFile);
				BufferedWriter out = new BufferedWriter(writer);
				out.write(allTags);
				out.flush();
				out.close();
			}
			// Print all tags...end
			content = "<"+bod+">";
			nodeSet.add(content);
			int key = 1;
			lineSet.add(bod);
			endTagLevels.put(key++, bod);
			do {			
				String line = itr.next();				
				if (line.contains("//")) {
					String sortLine = line;
					if(line.contains("@"))
						sortLine = line.substring(0, line.indexOf("@")-2);
					if(!lineSet.contains(sortLine)) {
						endTagLevels.put(key++, sortLine);
						lineSet.add(sortLine);
					}
					// sort out the content
					if(endTagLevels.size() > 1) {
						for(int i = endTagLevels.size()-1; i > 1; i--) {
							if(!endTagLevels.containsKey(i))
								continue;
							String prevLine = endTagLevels.get(i);
							if(sortLine.contains(prevLine)) {
								break;
							}else {
								String tags[] = prevLine.split("//");
								content = content + "</"+tags[tags.length-1]+">";								
								lineSet.remove(endTagLevels.get(i));								
								endTagLevels.replace(i, endTagLevels.get(i), sortLine);		
								endTagLevels.remove(i+1);
								key--;
							}
						}
					}
					String tags[] = line.split("//");					
					String temp1 = "";					
					for(int i=0; i<tags.length; i++) {						
						if(tags[i].contains("@")) {
							content = content.substring(0, content.length()-1)+" "+tags[i].substring(1, tags[i].length())+"=\"\">";
						}else {							
							temp1 = temp1+"<"+tags[i]+">";
							if(!nodeSet.contains(temp1)) {
								nodeSet.add(temp1);
								content = content+"<"+tags[i]+">";								
							}
						}
					}					
				}
				if(!itr.hasNext() && endTagLevels.size() > 0) {
					for(int i = endTagLevels.size(); i > 0; i--) {
						if(!endTagLevels.containsKey(i))
							continue;
						String lastLine = endTagLevels.get(i);
						String tags[] = lastLine.split("//");
						content = content + "</"+tags[tags.length-1]+">";								
						lineSet.remove(endTagLevels.get(i));		
						endTagLevels.remove(i);
					}
				}
			} while (itr.hasNext());
			
			FileWriter writer = new FileWriter(outputFile);
			BufferedWriter out = new BufferedWriter(writer);
			out.write(content);
			out.flush();
			out.close();		
			System.out.println("Done!");
		} catch (Exception e) {
			e.printStackTrace();
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
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				String nodePath = printParentNode(tempNode);
				nodeSetMain.add(nodePath);
				
				if (tempNode.hasAttributes()) {
					NamedNodeMap nodeMap = tempNode.getAttributes();
					for (int i = 0; i < nodeMap.getLength(); i++) {
						Node node = nodeMap.item(i);
						nodeSetMain.add(nodePath+"//@"+node.getNodeName());						
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