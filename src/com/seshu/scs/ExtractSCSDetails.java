package com.seshu.scs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.TreeMap;

import com.seshu.common.ClientProperties;
import com.seshu.car.ParseCARCustomXML;
import com.seshu.car.ReadCARFiles;
import com.seshu.word.MSWordReader;

/*
 * Look at General.properties file for all properties.
 * 
 */

public class ExtractSCSDetails {
	
	private static String zeros = "0000000";
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
	
	public static void main(String a[])
	{
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br  = null;
		TreeMap<String, String> hs = new TreeMap<String, String>();
		Connection con = null;
		//int records = 0;
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
			String header = "Region\tCustomer\tDocumentType\tDocumentKey\tDescription\tDocument";
			int fdsCount = 0;
			int tdsCount = 0;
			int trsCount = 0;
			int othCount = 0;
			System.out.println("Start...");
			
			try{
				Class.forName("net.sourceforge.jtds.jdbc.Driver");			
				con = DriverManager.getConnection("jdbc:jtds:sqlserver://inhynsyaddanap2:1433/SCS","seshu","seshu");				
			}
			catch(Exception e){
				//e.printStackTrace();
				System.out.println(e.getMessage());
			}
			do{
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
						|| doc.trim().startsWith(".~")) {
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
								
				if(doc.indexOf(".") > 2)
					docDesc = doc.substring(0, doc.indexOf(".")-2);
				else
					docDesc = doc.substring(0, doc.indexOf("."));
				
				int acceptableLength = (60*(docDesc.length()))/100; // Eliminate duplicates by approximate truncation of description...
				
				String key = "";
				if(docType.equals(fds)) {
					fdsCount++;
					key = docType + zeros.substring(0, zeros.length()-(fdsCount+"").length())+fdsCount;
				}else if(docType.equals(tds)) {
					tdsCount++;
					key = docType + zeros.substring(0, zeros.length()-(tdsCount+"").length())+tdsCount;
				}else if(docType.equals(trs)) {
					trsCount++;
					key = docType + zeros.substring(0, zeros.length()-(trsCount+"").length())+trsCount;					
				}else {
					othCount++;
					key = docType + zeros.substring(0, zeros.length()-(othCount+"").length())+othCount;
				}
				
				String hashKey = region+"-"+customer+"-"+docType+"-"+key+"-"+docDesc.substring(0, acceptableLength);
				String hashValue = region+"\t"+customer+"\t"+docType+"\t"+key+"\t"+docDesc+"\t"+doc;
				
				// Testing Area		
				/*
				if (docType.equals(car)) {
					if (records > 5)
						break;
					records++;

					System.out.println(actualLine);
					String customXML = ReadCARFiles.getCARCustomXML(actualLine);
					ParseCARCustomXML.parseXML(customXML, true, actualLine);
					actualLine = br.readLine();
					continue;
				}
				 
				if(!docType.equals(rnts)) {
					actualLine = br.readLine();
					continue;
				}
				System.out.println(actualLine);
				if(records > 1)
					break;
				records++;
				*/
				// Testing Area
				hs.put(hashKey, hashValue);
				if(docType.equals(car)) {
					String customXML = ReadCARFiles.getCARCustomXML(actualLine);
					ParseCARCustomXML.parseXML(customXML, true, actualLine);
					actualLine = br.readLine();
					continue;
				}
				PreparedStatement st = null;
				try {
					// Insert Customer data
					boolean customerExists = false;
					st = con.prepareStatement("SELECT COUNT(1) FROM CUSTOMER WHERE CUSTOMER = ? ");
					st.setString(1, customer);
					ResultSet rs = st.executeQuery();
					
					if(rs.next() && rs.getInt(1) > 0) {
						customerExists = true;
					}
					st.close();
					if(!customerExists) {
						st = con.prepareStatement("INSERT INTO CUSTOMER(CUSTOMER, REGION)" + " VALUES (?, ?)");
						st.setString(1, customer);
						st.setString(2, region);
						st.executeUpdate();
						st.close();
					}
					
					String docData = MSWordReader.getWordDocumentData(actualLine);
					if(docType.equals(fds)) {
						// Insert FDS data						
						st = con.prepareStatement("INSERT INTO FDS(CUSTOMER, FDS, REFERENCE, TITLE, FILENAME, DOCDATA)"
								+ " VALUES (?, ?, ?, ?, ?, ?)");
						st.setString(1, customer);
						st.setString(2, key);
						st.setString(3, "NONE");
						st.setString(4, docDesc);
						st.setString(5, doc);
						st.setString(6, docData);						
					}else if(docType.equals(tds)) {
						// Insert TDS data						
						st = con.prepareStatement("INSERT INTO TDS(TDS, FDS, FILENAME, TITLE, DOCDATA, CUSTOMER)"
								+ " VALUES (?, ?, ?, ?, ?, ?)");
						st.setString(1, key);
						st.setString(2, "FDS0000001");
						st.setString(3, doc);
						st.setString(4, docDesc);						
						st.setString(5, docData);
						st.setString(6, customer);
					}else if(docType.equals(trs)) {
						// Insert TRS data
						st = con.prepareStatement("INSERT INTO TRS(TRS, FDS, FILENAME, TITLE, DOCDATA, CUSTOMER)"
								+ " VALUES (?, ?, ?, ?, ?, ?)");
						st.setString(1, key);
						st.setString(2, "FDS0000001");
						st.setString(3, doc);
						st.setString(4, docDesc);
						st.setString(5, docData);
						st.setString(6, customer);
						
					}else if (!docType.equals(car)){
						st = con.prepareStatement("INSERT INTO MISC(DOCKEY, CUSTOMER, FILENAME, TITLE, DOCDATA, REFERENCE, REFERENCETYPE)"
								+ " VALUES (?, ?, ?, ?, ?, ?, ?)");
						st.setString(1, key);
						st.setString(2, customer);
						st.setString(3, doc);
						st.setString(4, docDesc);
						st.setString(5, docData);				
						st.setString(6, "NONE");
						st.setString(7, "NONE");
					}						
					st.executeUpdate();
					
				}catch(Exception e) {
					//e.printStackTrace();
					System.out.println(actualLine);
					System.out.println(e.getMessage());
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
			
			FileWriter fst = new FileWriter(ClientProperties.getProperty("scsOutputFile"));
		    BufferedWriter out = new BufferedWriter(fst);
		    out.write(header+"\n");
		    for (Map.Entry<String, String> entry : hs.entrySet()) {		         
		         out.write(entry.getValue()+"\n");
		         out.flush();
		    }

		    out.close();   
		    System.out.println("Done!");
		}
		catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if(con != null)
					con.close();
			}catch(Exception e) {
				
			}
		}
	}	
}
