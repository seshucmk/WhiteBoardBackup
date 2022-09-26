package com.seshu.general;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ReadEDIASNLine {
	
	public static void main(String a[])
	{
		String file = "C:\\Projects\\Seshu\\WhiteBoard\\input\\BTA\\11_1533122092423_BTEDIASN.xml";
		try{
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));			
			String line = br.readLine();
			do{
				if(line.contains("<ShippedQuantity unitCode")){
					System.out.println(line);					
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