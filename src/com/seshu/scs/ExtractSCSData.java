package com.seshu.scs;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import com.seshu.common.ClientProperties;
import com.seshu.general.FileHelper;

/*
 * Look at General.properties file for all properties.
 * 
 */

public class ExtractSCSData {
	
	public static void main(String a[])
	{
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br  = null;
		ArrayList<String> hs = new ArrayList<String>();
		try{
			ClientProperties.initialize("config\\General.properties");
			String linePattern = "directory of";
			fstream = new FileInputStream(ClientProperties.getProperty("scsFilePath"));
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));			
			String actualLine = br.readLine();
			String line = "";
			boolean fbrLine = false;
			String header = "";
			do{
				if(actualLine != null) {
					line = actualLine.toLowerCase();
				}
				
				if(line.contains(linePattern)) {
					header = line;
					fbrLine = false;
				}
				if((line.contains(linePattern) 
						&& (line.contains("fbr") || line.contains("fds")) 
						|| fbrLine)) {					
					if(line.contains(".doc") || line.contains(".docx") || line.contains(".docm")) {
						hs.add(header+"\t"+line);
					}
					fbrLine = true;
				}
				actualLine = br.readLine();				
			}while(actualLine != null);
			br.close();
			in.close();
			fstream.close();		
			
			Iterator<String> itr = hs.iterator();
			StringBuffer sb = new StringBuffer();
			while(itr.hasNext()) {
				String current = itr.next();
				sb.append(current+"\n");
				System.out.println(current);
			}
			FileHelper helper = new FileHelper();
			helper.writeToFile(sb, ClientProperties.getProperty("scsOutputFile"));
			
			System.out.println("Total Records: "+hs.size());
		}
		catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	public static String readFile(String filePath) throws IOException
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
		}return sb.toString();
	}
}
