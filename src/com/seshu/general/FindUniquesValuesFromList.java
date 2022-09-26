package com.seshu.general;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;

public class FindUniquesValuesFromList {
	
	public static void main(String a[])
	{
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br  = null;
		try{
			fstream = new FileInputStream("C:\\Projects\\Seshu\\WhiteBoard\\input\\all\\fields.txt");
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));			
			String line = br.readLine();
			HashSet<String> set = new HashSet<String>();
			do{
				set.add(line);
				line = br.readLine();				
			}while(line != null);				
			br.close();
			in.close();			
			fstream.close();
			Iterator<String> itr = set.iterator();
			while(itr.hasNext())
				System.out.println(itr.next());
		}
		catch(Exception e){
			e.printStackTrace();
		}		
	}
}
