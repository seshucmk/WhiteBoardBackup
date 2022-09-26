package com.seshu.scs;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.seshu.common.ClientProperties;

/*
 * Look at General.properties file for all properties.
 * 
 */

public class ExtractDumpData {
	
	private static String zeros = "0000000000";
	private static String amerPattern = "AMER";
	private static String amerCoePattern = "AMER\\AMERICAS_PSO _COE_Works";
	private static String emeaPattern = "EMEA";
	private static String apacPattern = "APAC";
	private static String base = "C:\\SESHU\\TEST\\";	
	private static String fds = "FDS";
	private static String fds1 = "ANA-050";
	private static String fds2 = "IPM-140";
	private static String fds3 = "FBR";
	private static String tds = "TDS";	
	private static String ts0 = "TECHSPECS";
	private static String ts1 = "\\TS\\";
	private static String ts2 = "\\TS ";
	private static String ts3 = "TECH SPECS";
	private static String ts4 = "TECH SPEC";
	private static String ts5 = "TECHSPEC";
	private static String ts6 = "TECH-SPEC";
	private static String ts7 = "TECHNICAL DESIGN";
	private static String ts8 = "DES-020";
	private static String ts9 = "TECHDESIGN";
	private static String ts10 = "TECH DESIGN";
	private static String int1 = "\\Integration\\";
	private static String integration = "INT";	
	private static String trs = "TRS";
	private static String testResults1 = "TEST RESULT";
	private static String testResults2 = "TEST_RESULT";
	private static String testResults3 = "TEST RESULT";
	private static String testResults4 = "TESTRESULT";
	private static String testResults5 = "TEST_CASES";
	private static String testResults6 = "TEST-CASES";
	private static String testResults7 = "TEST CASES";
	private static String testResults8 = "TESTPLAN";
	private static String testResults9 = "TEST_PLAN";
	private static String testResults10 = "TES-020";
	private static String testResults11 = "TES-070";
	private static String carFile1 = "CUST";
	private static String carFile2 = ".ZIP";
	private static String car = "CAR";
	private static String rn1 = "RELEASE NOTES";
	private static String rnts = "RNS";
	private static String ds1 = "DEPLOYMENT STEPS";
	private static String ds2 = "DEPLOYMENTPROCEDURE";
	private static String dps = "DPS";
	private static String sr1 = "SERVICE_RECORD";
	private static String srd = "SRD";
	private static String pt1 = "PATCHRELEASE";
	private static String pt2 = "DIFF.DOC";
	private static String patch = "PAT";
	private static String SCS_DELIMITER = "|SCS|";
	private static int rankingCount = 1;
	
	public static void main(String a[])
	{
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br  = null;
		TreeSet<String> customerSet = new TreeSet<String>();
		TreeMap<String, String> uniqueMap = new TreeMap<String, String>();
		TreeMap<String, String> entries = new TreeMap<String, String>();
		TreeMap<String, String> carMap = new TreeMap<String, String>();
		TreeSet<String> leftOverDocs = new TreeSet<String>();
		Connection con = null;
		try{
			ClientProperties.initialize("config\\General.properties");
			fstream = new FileInputStream(ClientProperties.getProperty("scsFilePath"));
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));			
			String actualLine = br.readLine();
			String line = "";			
			String customer = "";
			String docType = "OTH";
			String doc = "";			
			String docDesc = "";
			String region = "";
			String customerFrom = "";
			String reference = "NONE";
			System.out.println("Start...");
			int counter = 0;
			try{
				Class.forName("net.sourceforge.jtds.jdbc.Driver");			
				con = DriverManager.getConnection("jdbc:jtds:sqlserver://inhynsyaddanap1:1433/SCS","seshu","seshu");				
			}
			catch(Exception e){
				System.out.println("Error loading jdbc driver in main(): "+e.getMessage());
			}
			int currentMaxKey = getMaxKey(con);
			do{
				// Show progress...
				counter++;
				if(counter%100 == 0)
					System.out.print(counter+".");
				if(counter%1000 == 0)
					System.out.print("\n");
				
				docType = "";
				if(actualLine != null) {
					line = actualLine.toUpperCase();
				}
				doc = actualLine.substring(actualLine.lastIndexOf("\\")+1, actualLine.length());
				if(line.contains(base+amerPattern) || line.contains(base+amerCoePattern)) {
					region = amerPattern;
					customerFrom = base+amerPattern;
				}
				if(line.contains(base+emeaPattern)) {
					region = emeaPattern;
					customerFrom = base+emeaPattern;
				}
				if(line.contains(base+apacPattern)) {
					region = apacPattern;
					customerFrom = base+apacPattern;
				}
				
				String excludeBase = line.substring(customerFrom.length()+1, line.length());
				customer = excludeBase.substring(0, excludeBase.indexOf("\\"));
				
				if(region == null || region.trim().length() == 0
						|| customer == null || customer.trim().length() == 0
						|| doc.trim().startsWith("~") 
						|| doc.trim().startsWith(".~")
						|| doc.trim().endsWith(".svn-base")) {
					actualLine = br.readLine();
					continue;
				}
				
				if(line.contains(fds1) || line.contains(fds)
						|| line.contains(fds2) || line.contains(fds3)) {
					docType = fds;
				}
				if(line.contains(tds) || line.contains(ts0) 
						|| line.contains(ts1) || line.contains(ts2)
						|| line.contains(ts3) || line.contains(ts4)
						|| line.contains(ts5) || line.contains(ts6)
						|| line.contains(ts7) || line.contains(ts8)
						|| line.contains(ts9) || line.contains(ts10)) {
					docType = tds;
				}
				if(line.contains(testResults1) || line.contains(testResults2) 
						|| line.contains(testResults3) || line.contains(testResults4) 
						|| line.contains(testResults5) || line.contains(testResults6)
						|| line.contains(testResults7) || line.contains(testResults8)
						|| line.contains(testResults9) || line.contains(testResults10)
						|| line.contains(testResults11)) {
					docType = trs;
				}
				
				// Specific to PL start
				if(customer.equalsIgnoreCase("PaulLange") && line.contains(ts8)){
					docType = fds;
				}
				if(customer.equalsIgnoreCase("PaulLange") && 
						(line.contains("DES-020-TS") || line.contains("DES-20-TS")
								|| line.contains("ANA-050_TS"))){
					docType = tds;
				}
				// Specific to PL end
				if(line.contains(carFile1) && line.contains(carFile2)) {
					docType = car;					
				}
				if(line.contains(int1)) {
					docType = integration;
				}
				if(line.contains(rn1)) {
					docType = rnts;
				}
				if(line.contains(ds1) || line.contains(ds2)) {
					docType = dps;
				}
				if(line.contains(sr1)) {
					docType = srd;
				}
				if(line.contains(pt1) || line.contains(pt2)) {
					docType = patch;
				}
				if(docType.trim().length() == 0) {
					docType = "OTH";
				}
				// New Format - start
				// Ex: C:\Seshu\test\EMEA\BrownThomas\Developments\BTA002\ANA-050-BTA002-ASN Print Label Function Changes_v1.0.doc
				// C:\Seshu\test\EMEA\BrownThomas\Developments\BTA002\custBTA002_17017_BTA.zip
				if(line.contains(fds1+"-")) {
					int start = line.indexOf("ANA-050-");
					String part = line.substring(start+8, line.length());
					if(part.contains("-")) {
						reference = part.substring(0, part.indexOf("-"));
					}
				}
				if(line.contains(ts8+"-")) {
					int start = line.indexOf("DES-020-");
					String part = line.substring(start+8, line.length());
					if(part.contains("-")) {
						reference = part.substring(0, part.indexOf("-"));
					}
				}
				// New Format - end
				
				String entryValue = region+"\t"+customer+"\t"+docType+"\t"+doc;				
				entries.put(actualLine, entryValue);
				
				if(docType.equals(car)) {
					if(isCARFile(actualLine)) {
						carMap.put(customer+doc.substring(0, doc.indexOf("_")), 
								customer+SCS_DELIMITER+actualLine);						
					}
					actualLine = br.readLine();
					continue;
				}
				
				PreparedStatement st = null;
				try {					
					boolean customerExists = false;
					if(customerSet.contains(region+customer))
						customerExists = true;
					
					if(!customerExists) {
						customerExists = false; // Check DB
						st = con.prepareStatement("SELECT COUNT(1) FROM CUSTOMER WHERE CUSTOMER = ? AND REGION = ? ");
						st.setString(1, customer);
						st.setString(2, region);
						ResultSet rs = st.executeQuery();
						
						if(rs.next() && rs.getInt(1) > 0) {
							customerExists = true;
						}
						st.close();
						// Insert Customer data
						if(!customerExists) {
							st = con.prepareStatement("INSERT INTO CUSTOMER(CUSTOMER, REGION)" + " VALUES (?, ?)");
							st.setString(1, customer);
							st.setString(2, region);
							st.executeUpdate();
							st.close();
							customerSet.add(region+customer);
						}
					}
					// Check if record already exist in database
					boolean dbRecordExists = false;
					if(!docType.equals(car)) {					
						st = con.prepareStatement("SELECT COUNT(1) FROM CONTENT WHERE CUSTOMER = ? "
								+ " AND FILENAME = ? AND TYPE = ? ");
						st.setString(1, customer);						
						st.setString(2, doc);
						st.setString(3, docType);
						ResultSet rs = st.executeQuery();
						
						if(rs.next() && rs.getInt(1) > 0) {
							dbRecordExists = true;
						}
						rs.close();
						st.close();						
					}
					if(dbRecordExists) {
						actualLine = br.readLine();
						continue;
					}
					String docData = getWordDocumentData(actualLine);					
					if(docData == null || docData.trim().length() == 0) {
						leftOverDocs.add(actualLine+"\n");
						actualLine = br.readLine();
						continue;
					}
					
					if(doc.indexOf(".") > 2)
						docDesc = doc.substring(0, doc.indexOf(".")-2);
					else
						docDesc = doc.substring(0, doc.indexOf("."));
					
					int acceptableLength = (98*(docDesc.length()))/100; // Eliminate duplicates by approximate truncation of description...							
					String hashKey = region+"-"+customer+"-"+docType+"-"+docDesc.substring(0, acceptableLength);
					
					boolean entryExists = false;
					String oldKey = uniqueMap.get(hashKey);
					if(oldKey != null && oldKey.trim().length() != 0) {
						entryExists = true;
					}
					String key = zeros.substring(0, zeros.length()-(currentMaxKey+"").length())+currentMaxKey;
					currentMaxKey++;				
					if(entryExists && dbRecordExists) {
						// Update data
						st = con.prepareStatement("UPDATE CONTENT SET DOCDATA = ?, FILENAME = ? WHERE DOCKEY = ? ");
						st.setString(1, docData);
						st.setString(2, doc);
						st.setString(3, oldKey);
					}else if(!dbRecordExists){
						// Insert FDS data
						String process = getTopProcesses(docData);
						st = con.prepareStatement("INSERT INTO CONTENT(CUSTOMER, DOCKEY, REFERENCE, FILENAME, DOCDATA, PROCESS, TYPE)"
								+ " VALUES (?, ?, ?, ?, ?, ?, ?)");
						st.setString(1, customer);
						st.setString(2, key);
						st.setString(3, reference);						
						st.setString(4, doc);
						st.setString(5, docData);
						st.setString(6, process);
						st.setString(7, docType);
					}
					if(st != null)
						st.executeUpdate();
					
					uniqueMap.put(hashKey, key);
					
				}catch(Exception e) {
					System.out.println("DB error in main(): "+e.getMessage());
				}finally {
					if(st != null) {
						st.close();
					}				
				}
				
				actualLine = br.readLine();
				
			}while(actualLine != null);
			
			br.close();
			in.close();
			fstream.close();		
			
			if(con != null) {
				con.close();
			}
			
			FileWriter fst = new FileWriter(ClientProperties.getProperty("scsOutputFile"));
		    BufferedWriter out = new BufferedWriter(fst);
		    for (Map.Entry<String, String> entry : entries.entrySet()) {		         
		         out.write(entry.getValue()+"\n");
		         out.flush();
		    }
		    out.close();
		    counter = 0;
		    System.out.println("\nProcessing CAR files...");
		    for (Map.Entry<String, String> entry : carMap.entrySet()) {		    	
		    	counter++;
				if(counter%100 == 0)
					System.out.print(counter+".");
				if(counter%1000 == 0)
					System.out.print("\n");
		    	String mapValue = entry.getValue();
		    	String customerValue = mapValue.substring(0, mapValue.indexOf(SCS_DELIMITER));
		    	String fileNameValue = mapValue.substring(mapValue.indexOf(SCS_DELIMITER)+SCS_DELIMITER.length(), mapValue.length());
			    processCARFile(customerValue, fileNameValue);	
		    }
		    FileWriter fwDocs = new FileWriter(ClientProperties.getProperty("leftOverDocs"));
		    BufferedWriter docsOut = new BufferedWriter(fwDocs);
		    Iterator<String> itr = leftOverDocs.iterator();
		    while(itr.hasNext()) {
		    	docsOut.write(itr.next());
		    	docsOut.flush();
		    }
		    docsOut.close();
		    System.out.println("\nDone!");
		}
		catch(Exception e){
			System.out.println("Error in main(): "+e.getMessage());
		}
	}
	
	public static boolean isCARFile(String carFileName) throws IOException {
    	boolean carFile = false;
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(carFileName));
        final ZipInputStream is = new ZipInputStream(bis);

        try {
            ZipEntry entry;
            while ((entry = is.getNextEntry()) != null) {
            	if(!entry.isDirectory() && entry.getName().startsWith("custom") 
            			&& entry.getName().contains(".xml")) {
            		carFile = true;
            		break;
            	}
            }
            bis.close();
            is.close();
        }catch(Exception e) {
        	System.out.println("Error in isCARFile(): "+e.getMessage());
        }
        return carFile;
    }
	
	public static void processCARFile(String customer, String carFileName) {

		String customXML = "";
        BufferedInputStream bis = null;
        ZipInputStream is = null;

        try {
        	bis = new BufferedInputStream(new FileInputStream(carFileName));
        	is = new ZipInputStream(bis);
            ZipEntry entry;
            int read = 0;
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((entry = is.getNextEntry()) != null) {
            	if(!entry.isDirectory() && entry.getName().startsWith("custom") 
            			&& entry.getName().contains(".xml")) {
            		while ((read = is.read(buffer, 0, buffer.length)) > 0)
            		      baos.write(buffer, 0, read);
            		break;
            	}
            }
            customXML = baos.toString("UTF-8");
            is.close();
        }catch(Exception e) {
        	System.out.println("Error reading CAR in processCARFile(): "+e.getMessage());
        }
        
		ByteArrayInputStream pXMLStream = null;
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		Document myDocument = null;
		String encoding = null;
		TreeMap<String, String> map = new TreeMap<String, String>();
		String reference = "";
		String description = "";
		
		try {
			
			int start = customXML.indexOf("encoding='");
			if (start > -1) {
				encoding = customXML.substring(start + 10, customXML.indexOf("'", start + 11));
				pXMLStream = new ByteArrayInputStream(customXML.getBytes(encoding));
			} else {
				pXMLStream = new ByteArrayInputStream(customXML.getBytes());
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
									reference = value;
									break;
								}
							}
						NodeList custChildNodes = tempNode.getChildNodes();
						for (int k = 0; k < custChildNodes.getLength(); k++) {
							Node custChildNode = custChildNodes.item(k);
							if(custChildNode.getNodeName().equalsIgnoreCase("description")) {
								description = custChildNode.getTextContent();
							}
							if (custChildNode.getNodeName().equalsIgnoreCase("resource")) {
								NodeList resourceChildNodes = custChildNode.getChildNodes();
								for (int m = 0; m < resourceChildNodes.getLength(); m++) {
									Node resourceChildNode = resourceChildNodes.item(m);
									if (resourceChildNode.getNodeType() == Node.ELEMENT_NODE) {
										if (resourceChildNode.getNodeName().equalsIgnoreCase("svn-location")) {
											File f = new File(resourceChildNode.getTextContent());
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
			String carName = carFileName.substring(carFileName.lastIndexOf("\\")+1, carFileName.length());
			
			try{
				Class.forName("net.sourceforge.jtds.jdbc.Driver");			
				con = DriverManager.getConnection("jdbc:jtds:sqlserver://inhynsyaddanap1:1433/SCS","seshu","seshu");
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
						st = con.prepareStatement("INSERT INTO CAR(FILENAME, REFERENCE, COMPONENT, LINK, DOCKEY, DESCRIPTION, CUSTOMER)"
								+ " VALUES (?, ?, ?, ?, ?, ?, ?)");
						st.setString(1, carName);
						st.setString(2, reference);
						st.setString(3, component);
						st.setString(4, entry.getValue());
						st.setString(5, "NONE");
						st.setString(6, description);
						st.setString(7, customer);
						st.executeUpdate();
						st.close();
					}
				}
				con.close();
			} catch(Exception e){
				System.out.println("DB error in processCARFile(): "+e.getMessage());
			}
			finally {
				
			}
		} catch (Exception e) {
			System.out.println("Error in processCARFile(): "+e.getMessage());
		}
	}
	
	public static String getWordDocumentData(String fileName) {		
		String docText = "";
		try {		
			HWPFDocument doc = getWordDocFormat(fileName);
			
			if(doc != null) {
				WordExtractor we = new WordExtractor(doc);
				docText = we.getText();				
				we.close();
				//doc.close();
			}
			else {
				XWPFDocument docx = getWordDocXFormat(fileName);
				if(docx != null) {					
					XWPFWordExtractor we = new XWPFWordExtractor(docx);
					docText = we.getText();
					we.close();
					docx.close();
				}
			}
			docText = trimDocumentData(docText);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return docText;
	}
	
	public static HWPFDocument getWordDocFormat(String fileName) {
		HWPFDocument doc = null;
		try {
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());
			doc = new HWPFDocument(fis);
		}catch(Exception e){		
			//System.out.println(e.getMessage());
		}
		return doc;
	}
	
	public static XWPFDocument getWordDocXFormat(String fileName) {
		XWPFDocument docx = null;
		try {
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());
			docx = new XWPFDocument(fis);
		}catch(Exception e){
			//System.out.println(e.getMessage());
		}
		return docx;
	}
	
	public static String trimDocumentData(String docText) {
		String trimmedData = "";
		String lines[] = docText.split("\n");
		for(String line: lines) {
			if(line != null && line.trim().length() != 0)
				trimmedData = trimmedData+line+"\n";
		}
		
		return trimmedData;
	}
	
	public static String getTopProcesses(String content) {
		String output = "";
		// CODES
		Connection con = null;
		PreparedStatement st = null;
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");			
			con = DriverManager.getConnection("jdbc:jtds:sqlserver://inhynsyaddanap1:1433/SCS","seshu","seshu");			
			String list = "";
			String code = "";
			content = content.toUpperCase();
			st = con.prepareStatement("SELECT LISTNAME, CODE FROM CODES");				
			ResultSet rs = st.executeQuery();
			String currentProcess = "";
			int processRank = 0;
			TreeMap<String, String> processMap = new TreeMap<String, String>(Collections.reverseOrder());
			while(rs.next()) {				
				list =  rs.getString(1).toUpperCase();
				code =  rs.getString(2).toUpperCase();
				
				if(!list.equals(currentProcess)) {
					
					if(currentProcess.trim().length() != 0 && processRank > 0) {
						String newNum = "000000".substring(0, 5-(processRank+"").length())+processRank;
						processMap.put(newNum+currentProcess, currentProcess);					
					}
					currentProcess = list;
					processRank = 0;
				}
				
				String temp[] = content.split(code);
				int codeRank = 0;
				if(temp.length > 1)
					codeRank = temp.length;
				processRank = processRank + codeRank;
			}
			st.close();			
			con.close();
			
			int i = 0;
			for (Map.Entry<String, String> entry : processMap.entrySet()) {
				output =  output+entry.getValue();
				i++;
				if(i == rankingCount) // Change here to increase the top count
					break;
				else
					output =  output+"|";				
		    }
		}catch(Exception e) {
			e.printStackTrace();
		}
		return output;
	}
	
	public static int getMaxKey(Connection con) {
		String key = "";
		try{
			PreparedStatement st = con.prepareStatement(" SELECT MAX(DOCKEY) FROM CONTENT ");			
			ResultSet rs = st.executeQuery();			
			if(rs.next()) {
				key = rs.getString(1);
			}
			rs.close();
			st.close();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}		
		if(key == null || key.trim().length() == 0)
			return 1;
		else
			return Integer.parseInt(key)+1;
	}
}
