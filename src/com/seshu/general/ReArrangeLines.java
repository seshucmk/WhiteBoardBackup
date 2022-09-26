package com.seshu.general;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ReArrangeLines {
	
	public static void main(String a[])
	{
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br  = null;
		FileInputStream fstream2 = null;
		DataInputStream in2 = null;
		BufferedReader br2  = null;
		
		try{
			fstream = new FileInputStream("C:\\Projects\\Seshu\\WhiteBoard\\lsv\\arrange\\Seq-Columns.txt");
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));			
			String line = br.readLine();			
			do{			
			fstream2 = new FileInputStream("C:\\Projects\\Seshu\\WhiteBoard\\lsv\\arrange\\CXADJUSTMENTDETAIL-RawData.txt");
				try{
					line = line.toUpperCase();
					in2 = new DataInputStream(fstream2);
					br2 = new BufferedReader(new InputStreamReader(in2));			
					String rawLine = br2.readLine();
					do{
						rawLine = rawLine.toUpperCase();
						if(rawLine.contains("["+line+"]")){							
							rawLine = rawLine.replaceAll(",", "");
							rawLine = rawLine.replaceAll(line, "");	
							rawLine = rawLine.substring(3, rawLine.length());
							System.out.println(line+"\t"+rawLine);
							break;
						}					
						rawLine = br2.readLine();
					}while(rawLine != null);
					br2.close();
					in2.close();			
					fstream2.close();
				}
				catch(Exception e){
					e.printStackTrace();
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