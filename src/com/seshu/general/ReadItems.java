package com.seshu.general;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;



public class ReadItems {
	
	public static void main(String a[])
	{
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br  = null;
		String dirPath = "C:\\Projects\\BTA\\APIClient\\load";
		try{
			File dir = new File(dirPath);
			HashSet hs = new HashSet();
			if(dir.isDirectory()){
				String fileNames[] = dir.list();
				int size = fileNames.length;
				for(int i = 0; i<size; i++){
					fstream = new FileInputStream(dirPath+"\\"+fileNames[i]);
					in = new DataInputStream(fstream);
					br = new BufferedReader(new InputStreamReader(in));			
					String line = br.readLine();
					String allGuids = "'";
					do{
						if(line.contains("<LineNumber>")){
							int start = line.indexOf("<LineNumber>")+12;
							int end = line.indexOf("</LineNumber>");					
							//System.out.println(line.substring(start, end));
							hs.add(line.substring(start, end));
						}			
						line = br.readLine();				
					}while(line != null);
				
					br.close();
					in.close();
					fstream.close();
				}
			}
			Iterator itr = hs.iterator();
			while(itr.hasNext())
				System.out.println(itr.next());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
