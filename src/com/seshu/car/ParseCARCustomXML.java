package com.seshu.car;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ParseCARCustomXML {

	public static String fileName = "C:\\Projects\\Seshu\\WhiteBoard\\input\\CAR\\test.xml"; 
	public static void main(String[] args) {

		parseXML(fileName, false, "");
	}

	public static void parseXML(String xml, boolean isContent, String fileName) {

		ByteArrayInputStream pXMLStream = null;
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		Document myDocument = null;
		String encoding = null;
		TreeMap<String, String> map = new TreeMap<String, String>();
		String reference = "";
		String description = "";
		
		try {
			if(!isContent) {
				fileName = xml;
				xml = readXMLFile(xml);				
			}
			int start = xml.indexOf("encoding='");
			if (start > -1) {
				encoding = xml.substring(start + 10, xml.indexOf("'", start + 11));
				pXMLStream = new ByteArrayInputStream(xml.getBytes(encoding));
			} else {
				pXMLStream = new ByteArrayInputStream(xml.getBytes());
			}

			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();

			myDocument = builder.parse(pXMLStream);
			if (myDocument.hasChildNodes()) {
				NodeList nodeList = myDocument.getChildNodes();
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node tempNode = nodeList.item(i);
					if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
						if (tempNode.getNodeName().equalsIgnoreCase("customization")) {
							NamedNodeMap nodeMap = tempNode.getAttributes();
							for (int j = 0; j < nodeMap.getLength(); j++) {
								Node node = nodeMap.item(j);
								String name = node.getNodeName();
								String value = node.getNodeValue();
								if (name != null && name.equalsIgnoreCase("reference")) {
									//System.out.println("Reference: "+ value);
									reference = value;
									break;
								}
							}
						NodeList custChildNodes = tempNode.getChildNodes();
						for (int k = 0; k < custChildNodes.getLength(); k++) {
							Node custChildNode = custChildNodes.item(k);
							if(custChildNode.getNodeName().equalsIgnoreCase("description")) {
								//System.out.println("Description: "+custChildNode.getTextContent());
								description = custChildNode.getTextContent();
							}
							if (custChildNode.getNodeName().equalsIgnoreCase("resource")) {
								NodeList resourceChildNodes = custChildNode.getChildNodes();
								for (int m = 0; m < resourceChildNodes.getLength(); m++) {
									Node resourceChildNode = resourceChildNodes.item(m);
									if (resourceChildNode.getNodeType() == Node.ELEMENT_NODE) {
										if (resourceChildNode.getNodeName().equalsIgnoreCase("svn-location")) {
											File f = new File(resourceChildNode.getTextContent());
											//System.out.println(f.getName()+": "+resourceChildNode.getTextContent());
											map.put(f.getName(), resourceChildNode.getTextContent());										
										}
									}
								}
							}
						}
						}
					}
				}
			}
		
			// Insert data
			Connection con = null;
			PreparedStatement st = null;
			String carName = fileName.substring(fileName.lastIndexOf("\\")+1, fileName.length());
			
			try{
				Class.forName("net.sourceforge.jtds.jdbc.Driver");			
				con = DriverManager.getConnection("jdbc:jtds:sqlserver://inhynsyaddanap2:1433/SCS","seshu","seshu");
				for (Map.Entry<String, String> entry : map.entrySet()) {
					String component = entry.getKey();
					boolean carExists = false;
					st = con.prepareStatement("SELECT COUNT(1) FROM CAR WHERE COMPONENT = ? AND FILENAME = ? ");
					st.setString(1, component);
					st.setString(2, carName);
					ResultSet rs = st.executeQuery();
					
					if(rs.next() && rs.getInt(1) > 0) {
						carExists = true;
					}
					st.close();
					if(!carExists) {
						st = con.prepareStatement("INSERT INTO CAR(FILENAME, REFERENCE, COMPONENT, LINK, FDS, DESCRIPTION)"
								+ " VALUES (?, ?, ?, ?, ?, ?)");
						st.setString(1, carName);
						st.setString(2, reference);
						st.setString(3, component);
						st.setString(4, entry.getValue());
						st.setString(5, "FDS0000001");
						st.setString(6, description);
						st.executeUpdate();
						st.close();
					}
				}
				con.close();
			} catch(Exception e){
				System.out.println(fileName);
				System.out.println(e.getMessage());
			}
			finally {
				
			}
		} catch (Exception e) {
			//e.printStackTrace();
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
			if (tempNode.getNodeType() == Node.ELEMENT_NODE && tempNode.getNodeName().equalsIgnoreCase("description")) {
				System.out.println(tempNode.getTextContent());
			}
			if (tempNode.getNodeType() == Node.TEXT_NODE) {
				if (tempNode.getNodeValue() != null) {
					// System.out.println(tempNode.getNodeValue());
				}
			}
			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				String nodePath = printParentNode(tempNode);
				// System.out.println(nodePath);
				if (tempNode.hasAttributes()) {
					// get attributes names and values
					NamedNodeMap nodeMap = tempNode.getAttributes();
					for (int i = 0; i < nodeMap.getLength(); i++) {
						Node node = nodeMap.item(i);
						// System.out.println(nodePath+"//@"+node.getNodeName());

						Node namedNode = nodeMap.item(i);
						if (node.getNodeValue() != null) {
							// System.out.println(node.getNodeValue());
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