package com.seshu.general;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;



public class ReadBDRItems {
	
	public static void main(String a[])
	{
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br  = null;
		String dirPath = "C:\\Projects\\BTA\\APIClient\\BDR";
		try{
			File dir = new File(dirPath);
			HashSet hs = new HashSet();
			
			HashSet bodid = new HashSet();
			HashSet status = new HashSet();
			HashSet item = new HashSet();
			String details = "";
			String items = "";
			if(dir.isDirectory()){
				String fileNames[] = dir.list();
				int size = fileNames.length;
				for(int i = 0; i<size; i++){
					details = details+"\n";
					items = items+"\n";
					fstream = new FileInputStream(dirPath+"\\"+fileNames[i]);
					in = new DataInputStream(fstream);
					br = new BufferedReader(new InputStreamReader(in));			
					String line = br.readLine();
					String allGuids = "'";
					String previousText = "";
					//details = details+fileNames[i];
					do{
						/*if(line.contains("<BODID>")){
							//System.out.println(line);
							details = details+"\t"+line;
							int start = line.indexOf("<LineNumber>")+12;
							int end = line.indexOf("</LineNumber>");					
							//System.out.println(line.substring(start, end));
							hs.add(line.substring(start, end));
						}
						if(line.contains("<Code>")){
							//System.out.println(line);
							details = details+"\t"+line;
						}*/
						if (previousText.equals("ItemTag")){
							items = items+"\n"+line;
							previousText = "";
						}
						if(line.contains("<ItemID>")){
							//System.out.println(line);
							previousText = "ItemTag";							
						}
						if(line.contains("ShippedBaseUOMQuantity")){
							//System.out.println(line);
							items = items+"\t"+line;							
						}
						
						line = br.readLine();				
					}while(line != null);
				
					br.close();
					in.close();
					fstream.close();					
				}
				System.out.println(items);
				//System.out.println(details);
			}
			/*Iterator itr = hs.iterator();
			while(itr.hasNext())
				System.out.println(itr.next());*/
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
