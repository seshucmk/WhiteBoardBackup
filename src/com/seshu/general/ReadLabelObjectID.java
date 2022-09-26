package com.seshu.general;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;



public class ReadLabelObjectID {
	
	public static void main(String a[])
	{
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br  = null;
		
		try{					
			String errorFile = "C:\\Projects\\Seshu\\WhiteBoard\\input\\pl\\wm_pl_de_messages.ebm";			
			fstream = new FileInputStream(errorFile);	
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));			
			String line = br.readLine();
			do{
				String searchValue = "<attribute name=\"object_id\" value=\"";
				if(line.contains(searchValue)){		
					int start = line.indexOf(searchValue) + searchValue.length();
					int end = start+32;
					String guid = line.substring(start, end);
					System.out.println(guid);
				}			
				line = br.readLine();				
			}while(line != null);
			
			br.close();
			in.close();
			fstream.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
