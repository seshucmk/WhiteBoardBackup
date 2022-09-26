package com.seshu.general;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;



public class ExtractFiles {
	
	public static void main(String a[])
	{
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br  = null;
		try{
			// C:\Projects\BTA\APIClient\BTA\DuplicatePO
			fstream = new FileInputStream("C:\\CoE\\BrownThomas\\PostGoLive\\04012018_Prod_SOStatus_INC0097929\\Transfers.txt");
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));			
			String line = br.readLine();
			do{		
				/*String columns[] = line.split("@@");
				String item = columns[0];
				String dropid = columns[1];
				String qty = columns[2];
				String asn = columns[3];*/
				List list;
				String dirPath = "C:\\CoE\\BrownThomas\\PostGoLive\\04012018_Prod_SOStatus_INC0097929\\sostatus";
				String destDirPath = "C:\\CoE\\BrownThomas\\PostGoLive\\04012018_Prod_SOStatus_INC0097929\\investigate";
				StringBuffer buffer = new StringBuffer();
				try{
					File dir = new File(dirPath);
					if(dir.isDirectory()){
						String fileNames[] = dir.list();
						int size = fileNames.length;
						for(int i = 0; i<size; i++){
							String fileContent = readXMLFile(dirPath+"\\"+fileNames[i]);
							String fileName = fileNames[i];
							if(fileName.contains(line)){
								try{
								/*InputStream is = null;
							    OutputStream os = null;
							    File newFile = new File(destDirPath+"/"+fileNames[i]);
							    try {*/
							        /*is = new FileInputStream(new File(dirPath+"\\"+fileNames[i]));
							        os = new FileOutputStream(newFile);
							        byte[] buffer = new byte[1024];
							        int length;
							        while ((length = is.read(buffer)) > 0) {
							            os.write(buffer, 0, length);
							        }*/
							        
							        Files.copy(new File(dirPath+"\\"+fileNames[i]).toPath(),
							        		new File(destDirPath+"\\"+fileNames[i]).toPath(),
							                StandardCopyOption.REPLACE_EXISTING);
								
							   /* String fileContent = readXMLFile(dirPath+"\\"+fileNames[i]);
							    buffer.append(fileContent);*/
							    System.out.println("Done");							    
								}
							    catch(Exception e){
									e.printStackTrace();
								}
							    finally {
							        /*is.close();
							        os.close();*/
							    } 
							}
						}
						//writeToFile(buffer.toString(), destDirPath+"\\Result.xml");
					}					
				}
				catch(Exception e){
					e.printStackTrace();
				}
				//
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
	public static String readXMLFile(String filePath) throws IOException
	{
		FileReader fileReader = new FileReader(filePath);		
		BufferedReader br = new BufferedReader(fileReader);
		StringBuffer sb = new StringBuffer();
		
		String strLine = br.readLine();
		while(strLine!=null)
		{
			sb.append(strLine);
			strLine = br.readLine();
		}
		if(br != null){
			br.close();
		}		
		return sb.toString();
	}
}
