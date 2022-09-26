package com.seshu.general;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;

import com.seshu.common.ClientProperties;



public class ExtractUnique {
	
	public static void main(String a[])
	{
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br  = null;
		try{
			ClientProperties.initialize("config\\General.properties");
			HashSet<String> hs = new HashSet<String>();
			fstream = new FileInputStream(ClientProperties.getProperty("targetfile"));
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));			
			String line = br.readLine();
			do{
				hs.add(line);									
				line = br.readLine();				
			}while(line != null);				
			br.close();
			in.close();
			
			fstream.close();
			Iterator<String> itr = hs.iterator();
			while(itr.hasNext()){
				
				System.out.println(itr.next());
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
