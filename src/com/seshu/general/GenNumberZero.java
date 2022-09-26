package com.seshu.general;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class GenNumberZero {
	
	public static void main(String a[])
	{
		String zeros = "000000";
		String seqContent = "";
		int count = 10;
		for(int i=900000; i<=999999; i++){
			String strValue = i+"";
			//System.out.println(strValue);
			String value = zeros.substring(0, 6-(strValue.length()));
			seqContent = seqContent.concat(value+i+"\n");
			if(i%10000 == 0)
				System.out.print(".");
			if(i%100000 == 0 || i == 999999){
				System.out.print(value+i+"\n");
				String seqFile = "C:\\Projects\\BTA\\APIClient\\BTA\\barcodes\\Seq"+count+".txt";
				writeToFile(seqContent, seqFile);
				seqContent = "";
				count++;
			}				
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
