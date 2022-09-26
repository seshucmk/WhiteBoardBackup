package com.seshu.zip;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.seshu.common.ClientProperties;
import com.seshu.general.FileHelper;

public class IsolateFiles {
	
	public static String filesListPath = "";
	public static String outputDir = "";
	public static String zipFilesFolder = "";
	
	public static void main(String a[])	{
		init();
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br  = null;
		try{
			FileHelper fHelper = new FileHelper();
			fstream = new FileInputStream(filesListPath);
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));			
			String line = br.readLine();
			do{
				String sourceFile = zipFilesFolder+"\\"+line;
				String destinationFile = outputDir+"\\"+line;
				fHelper.copyFile(sourceFile, destinationFile);
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
	public static void init() {
		try{
			ClientProperties.initialize("config\\ZIP.properties");
			filesListPath = ClientProperties.getProperty("filesListPath");
			zipFilesFolder = ClientProperties.getProperty("zipFilesFolder");
			outputDir = ClientProperties.getProperty("outputDir");					
		}catch(Exception e){
			System.out.println("Exception while reading SVN properties: ");
			e.printStackTrace();
		}
	}
}
