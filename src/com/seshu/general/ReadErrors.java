package com.seshu.general;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;



public class ReadErrors {
	
	public static void main(String a[])
	{
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br  = null;
		
		try{					
			String errorFile = "Config\\errors.txt";			
			fstream = new FileInputStream(errorFile);	
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));			
			String line = br.readLine();
			String allGuids = "'";
			do{
				if(line.contains("'form_widget', '")){
					int i = line.indexOf("'form_widget', '");
					int start = i + 16;
					int end = start + 32;
					String guid = line.substring(start, end);
					if(!allGuids.contains(guid)){
						allGuids = allGuids + guid + "','";
					}
				}			
				line = br.readLine();				
			}while(line != null);
			System.out.println(allGuids);
			
			br.close();
			in.close();
			fstream.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
