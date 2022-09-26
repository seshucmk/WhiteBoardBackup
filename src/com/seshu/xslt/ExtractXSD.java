package com.seshu.xslt;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ExtractXSD {
    public static void main(String args[]) {
    	FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br  = null;
		String elementLine = "";
		String tokens[] = null;
		try{			
			fstream = new FileInputStream("C:\\Projects\\Seshu\\WhiteBoard\\input\\xsl\\asn_generated_sample.xml");	
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));			
			String line = br.readLine();
			do{
				if(line.contains("<xs:element")){
					elementLine = line;
					tokens = elementLine.split("\"");
					System.out.println("<"+tokens[1]+">"+"</"+tokens[1]+">");
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