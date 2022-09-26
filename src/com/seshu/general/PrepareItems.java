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

public class PrepareItems {
	
	public static void main(String a[])
	{
		try {
			System.out.println("Started...");
			String dirPath = "C:\\Projects\\BTA\\APIClient\\load\\out";			
			FileInputStream fstream = null;
			DataInputStream in = null;
			BufferedReader br = null;			
			String line = "";			
			fstream = new FileInputStream("C:\\Projects\\BTA\\APIClient\\load\\items.txt");
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));			
			line = br.readLine();					
			do{
				String xmlMessage = readXMLFile("C:\\Projects\\BTA\\APIClient\\load\\itemtemplate\\ItemMaster-BOD.xml");
				xmlMessage = xmlMessage.replaceAll("AR01", line);
				long dateTime = new Date().getTime();
				writeToFile(xmlMessage, dirPath+"\\"+line+"_"+dateTime+".xml");
				line = br.readLine();				
			}while(line != null);				
			br.close();
			in.close();
			fstream.close();
			System.out.println("Done...");
		}
		catch(Exception e){
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
