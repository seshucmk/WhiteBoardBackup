package com.seshu.general;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.json.JSONObject;
import org.json.XML;

import com.seshu.zip.UnZip;

public class FileHelper {
	
	public String returnExpectedLine(String filePath, String searchString)
	{
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br  = null;
		String expectedLine = "";
		try{			
			fstream = new FileInputStream(filePath);	
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));			
			String line = br.readLine();
			do{
				if(line.contains(searchString)){
					expectedLine = line;
					break;
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
		return expectedLine;		
	}
	public String[] returnAllLines(String filePath)
	{
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br  = null;
		String content = "";
		try{			
			fstream = new FileInputStream(filePath);	
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));			
			String line = br.readLine();
			
			do{
				content = content+","+line;
				line = br.readLine();				
			}while(line != null);			
			br.close();
			in.close();
			fstream.close();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}/*
		if(!content.contains(","))
			content = ",";*/
		return content.split(",");		
	}
	
	public String returnFileContent(String filePath)
	{
		StringBuffer sb = new StringBuffer();
		try{
			FileReader fileReader = new FileReader(filePath);		
			BufferedReader br = new BufferedReader(fileReader);
			String line = br.readLine();
			while(line!=null)
			{
				sb.append(line);
				line = br.readLine();
			}
			if(br != null){
				br.close();
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return sb.toString();		
	}
	public void copyFile(String fromFilePath, String toFilePath){
		
		try{
		File sourceFile = new File(fromFilePath);
		File destinationFile = new File(toFilePath);

		FileInputStream fileInputStream = new FileInputStream(sourceFile);
		FileOutputStream fileOutputStream = new FileOutputStream(destinationFile);

		int bufferSize;
		byte[] bufffer = new byte[512];
		while ((bufferSize = fileInputStream.read(bufffer)) > 0) {
		    fileOutputStream.write(bufffer, 0, bufferSize);
		}
		fileInputStream.close();
		fileOutputStream.close();
		}catch(Exception e){
			e.printStackTrace();			
		}
	}
	
	public String convertJSonToXML(String jsonContent){
		
		String xmlContent = "";
		try{
			JSONObject json = new JSONObject(jsonContent);
			xmlContent = XML.toString(json);
		}catch(Exception e){
			e.printStackTrace();			
		}
		return xmlContent;
	}
	
	public String convertXMLToJSon(String xmlContent){
		
		String jsonContent = "";
		try{
			JSONObject json = XML.toJSONObject(xmlContent);
			jsonContent = json.toString();			
		}catch(Exception e){
			e.printStackTrace();			
		}
		return jsonContent;
	}
	
	public boolean extractZipFile(String zipFilesDir, String outputDir) {
		boolean status = false;
		try {
			File dir = new File(zipFilesDir);
	        if(dir.isDirectory()){
	        	File files[] = dir.listFiles();
				for(int i=0; i < files.length; i++){					
					String outFile = files[i].getName().substring(0, files[i].getName().indexOf("."));
					UnZip unzip = new UnZip();
			        unzip.unzip(files[i].getAbsolutePath(), outputDir + outFile);
				}
				status = true;
			}
		}catch(Exception e) {
			status = false;
		}
		return status;
	}
	
	public void writeToFile(StringBuffer content, String fileName) {
		try {
			FileWriter fstream = new FileWriter(fileName);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(content.toString());
			out.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
