package com.seshu.general;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashSet;

public class DuplicateLines {

	public static void main(String a[]) {
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br = null;
		long headerValue = 1002140000;
		try {
			String lineMsg = readFile("C:\\Projects\\BTA\\APIClient\\inputs\\asnline.xml");
			String asn = readFile("C:\\Projects\\BTA\\APIClient\\inputs\\asnfull.xml");			
			int itemCount = 0;
			fstream = new FileInputStream("C:\\Projects\\BTA\\APIClient\\inputs\\items.txt");
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));
			String line = br.readLine();			
			StringBuffer sb = new StringBuffer();
			do {
				itemCount++;
				if (line != null && line.trim().length() != 0) {
					lineMsg = lineMsg.replaceAll("ItemValue", line);
					sb = sb.append(lineMsg+"\n");
					lineMsg = lineMsg.replaceAll(line, "ItemValue");
				}
				if(itemCount%100 == 0){
					long currentHeader = headerValue++;
					asn = asn.replaceAll("HeaderValue", ""+currentHeader);
					StringBuffer asnfull = new StringBuffer(asn.replaceAll("<AdvanceShipNoticeItem>Item</AdvanceShipNoticeItem>", sb.toString()));			
					writeToFile(asnfull.toString(), "C:\\Projects\\BTA\\APIClient\\inputs\\outasn\\bulkasn_"+currentHeader+".xml");
					asn = asn.replaceAll(""+currentHeader, "HeaderValue");
				}
				line = br.readLine();
			} while (line != null);

			br.close();
			in.close();
			fstream.close();
			
			asn = asn.replaceAll("HeaderValue", ""+headerValue++);
			StringBuffer asnfull = new StringBuffer(asn.replaceAll("<AdvanceShipNoticeItem>Item</AdvanceShipNoticeItem>", sb.toString()));			
			writeToFile(asnfull.toString(), "C:\\Projects\\BTA\\APIClient\\inputs\\outasn\\bulkasn_"+headerValue+".xml");
			//asn = asn.replaceAll(""+headerValue, "HeaderValue");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String readFile(String filePath) throws IOException {
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
	public static void writeToFile(String content, String fileName) {
		/*********************************************************************
		 * Programmer : Seshu
		 * Created    : 15.03.2017
		 * Purpose    : Poll for EDI messages and convert them into ASN BODs
		 * 				1. Get the file stream based on the given file name
		 * 				2. Build BOD XML message and write to the file system		 * 				
		 * 				3. Close the resources. Ex BufferedWriter 
		 * Reference  : BTA - EDIASN Interface
		 ***********************************************************************
		 * Modification History
		 * 24.03.2017 Mounika: Modified to convert EDI message to BOD
		 ***********************************************************************/
		try {			
			File file = new File(fileName);
			file.createNewFile();
			FileWriter fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(content);
			out.close();
		} catch (Exception e) {
		}
	}
}
