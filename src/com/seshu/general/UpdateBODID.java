package com.seshu.general;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

public class UpdateBODID {
	
	public static void main(String a[])
	{
		try {
			String dirPath = "C:\\Projects\\BTA\\APIClient\\BTA\\so";
			String newDirPath = "C:\\Projects\\BTA\\APIClient\\BTA\\newso";
			File dir = new File(dirPath);
			if(dir.isDirectory()){
				String fileNames[] = dir.list();
				int size = fileNames.length;
				Random r = new Random(10000);
				for(int i = 0; i<size; i++){			
					String xmlMessage = readXMLFile(dirPath+"\\"+fileNames[i]);
					int newNumber = r.nextInt(10000);
					xmlMessage = xmlMessage.replaceAll("</BODID>", ";"+newNumber+"</BODID>");					
					writeToFile(xmlMessage, newDirPath+"\\"+fileNames[i]);
				}
			}
		}catch(Exception e){
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
