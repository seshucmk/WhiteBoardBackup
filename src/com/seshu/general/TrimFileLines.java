package com.seshu.general;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class TrimFileLines {
	
	static int lineLength = 60;  
	public static void main(String a[])
	{
		try {
			System.out.println("Started...");			
			String content = "";
			FileInputStream fstream = null;
			DataInputStream in = null;
			BufferedReader br = null;			
			String line = "";			
			fstream = new FileInputStream("C:\\Projects\\Seshu\\WhiteBoard\\input\\msk\\ShipTo-Address1.txt");
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));			
			line = br.readLine();					
			do{
				if(line != null && line.length() > lineLength) {
					System.out.println("Before trim...@"+line );
					line = line.substring(0, lineLength);
					System.out.println("After trim...@"+line );
				}
				content = content+line+"\n";
				line = br.readLine();				
			}while(line != null);				
			br.close();
			in.close();
			fstream.close();
			writeToFile(content, "C:\\Projects\\Seshu\\WhiteBoard\\input\\msk\\ShipTo-trimmed-Address1.txt");
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
	
}
