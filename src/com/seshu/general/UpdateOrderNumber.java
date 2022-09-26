package com.seshu.general;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class UpdateOrderNumber {
	
	public static void main(String a[])
	{
		String currentFile = "";
		try {
			String dirPath = "C:\\Projects\\BTA\\APIClient\\BTA\\so";
			String newDirPath = "C:\\Projects\\BTA\\APIClient\\BTA\\sofiles";
			
			File dir = new File(dirPath);
			long seq = 1100000000;
			if(dir.isDirectory()){
				String fileNames[] = dir.list();
				int size = fileNames.length;
				Random r = new Random();
				for(int i = 0; i<size; i++){
					currentFile = fileNames[i];
					String xmlMessage = readXMLFile(dirPath+"\\"+fileNames[i]);
					int randomNumber = r.nextInt(11000);
					
					// Update WH number					
					xmlMessage = xmlMessage.replaceAll("<BODID>test-nid:test:ARN:100:", "<BODID>test-nid:test:ARN:900:");
					xmlMessage = xmlMessage.replaceAll("<LocationID>100</LocationID>", "<LocationID>900</LocationID>");
					xmlMessage = xmlMessage.replaceAll("location=\"100\"", "location=\"900\"");
					xmlMessage = xmlMessage.replaceAll(">100</ID>", ">900</ID>");
					
					// Update Order Number
					int bodidIndex = xmlMessage.indexOf("<BODID>test-nid:test:ARN:");
					String orderNumber = xmlMessage.substring(bodidIndex+31, bodidIndex+41);
					xmlMessage = xmlMessage.replaceAll(orderNumber, seq+"");
					
					// Update item
					boolean lineExist = false;
					if(xmlMessage.contains("<LineNumber>")){
						int lineNumberStart = xmlMessage.indexOf("<LineNumber>");
						int lineNumberEnd = xmlMessage.indexOf("</LineNumber>");
						String item = xmlMessage.substring(lineNumberStart+12, lineNumberEnd);
						xmlMessage = xmlMessage.replaceAll(item, "AR01");
						lineExist = true;
					}
					
					// Update BODID by appending a random number
					if(lineExist){
						//xmlMessage = xmlMessage.replaceAll("</BODID>", ";"+randomNumber+"</BODID>");					
						writeToFile(xmlMessage, newDirPath+"\\"+randomNumber+"_"+fileNames[i]);
						seq++;
					}				
					
					if(i%100 == 0)
						System.out.print(".");
					if(i%1000 == 0)
						System.out.println(i+"\n.");
				}
				System.out.println("\nDone");
			}
		}catch(Exception e){
			System.out.println("Current File: "+ currentFile);
			e.printStackTrace();			
		}
		
	}
	
	public static void writeToFile(String content, String fileName) {
		try {
			FileWriter fstream = new FileWriter(fileName);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(content);
			out.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public static String readXMLFile(String filePath) throws IOException
	{
		FileReader fileReader = new FileReader(filePath);		
		BufferedReader br = new BufferedReader(fileReader);
		StringBuffer sb = new StringBuffer();
		
		String strLine = br.readLine();
		while(strLine!=null)
		{
			sb.append(strLine);
			strLine = br.readLine();
		}
		if(br != null){
			br.close();
		}		
		return sb.toString();
	}
}
