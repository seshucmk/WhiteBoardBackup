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
import java.util.HashSet;
import java.util.Iterator;



public class FindItems {
	
	public static void main(String a[])
	{
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br  = null;
		try{
			fstream = new FileInputStream("C:\\Projects\\BTA\\APIClient\\BTA\\DropIds\\asnsnew.txt");
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));			
			String line = br.readLine();
			HashSet hs = new HashSet();
			do{		
				String columns[] = line.split("@@");				
				String item = columns[0];
				String dropid = columns[1];
				String qty = columns[2];
				String asndp = columns[3];
				String asnpd = columns[4];
				
				if(line.contains("NULL")){
					if(asnpd.equalsIgnoreCase("NULL")){
						if(!asndp.equalsIgnoreCase("NULL"))
							asnpd = asndp;
						else
							asnpd = dropid;
					}
				}
				
				String dirPath = "C:\\Projects\\BTA\\APIClient\\BTA\\foundfiles";				
				StringBuffer buffer = new StringBuffer();
				try{
					File dir = new File(dirPath);
					if(dir.isDirectory()){
						String fileNames[] = dir.list();
						int size = fileNames.length;
						for(int i = 0; i<size; i++){
							if(fileNames[i].contains(asnpd)){
								String fileContent = readXMLFile(dirPath+"\\"+fileNames[i]);
								// <item_id>124330221</item_id><unit_qty>1.0</unit_qty>
								if(fileContent.contains(item)){
									System.out.println(item+"\t"+dropid+"\t"+qty+"\t"+"900"+asnpd+"\t<item_id>"+item+"</item_id><unit_qty>"+qty+"</unit_qty>"+"\tYes\t"+fileNames[i]);
									//System.out.println("Item "+item+" exist in dropid "+dropid+" of file " +fileNames[i]);
								}
								else{
									System.out.println(item+"\t"+dropid+"\t"+qty+"\t"+"900"+asnpd+"\t<item_id>"+item+"</item_id><unit_qty>"+qty+"</unit_qty>"+"\tNo\t"+fileNames[i]);
									//System.out.println("Item "+item+" does not exist in dropid "+dropid+" of file " +fileNames[i]);
								}
							}
							else{
								//hs.add(asnpd);
								hs.add(item);
								
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
			/*Iterator itr = hs.iterator();
			while(itr.hasNext())
				System.out.println(itr.next());*/
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
