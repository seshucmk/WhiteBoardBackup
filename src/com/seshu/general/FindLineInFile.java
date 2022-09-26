package com.seshu.general;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;

import com.seshu.common.ClientProperties;

/*
 * Look at General.properties file for all properties.
 * 
 */

public class FindLineInFile {
	
	public static void main(String a[])
	{
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br  = null;
		HashSet<String> hs = new HashSet<String>();
		try{
			ClientProperties.initialize("config\\General.properties");
			String targetFile = readFile(ClientProperties.getProperty("targetfile"));
			fstream = new FileInputStream(ClientProperties.getProperty("sourcefile"));
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));			
			String line = br.readLine();
			do{
				if(targetFile.contains(line))
					hs.add(line);
				line = br.readLine();				
			}while(line != null);
			br.close();
			in.close();
			fstream.close();		
			
			Iterator<String> itr = hs.iterator();
			while(itr.hasNext())
				System.out.println(itr.next());
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
