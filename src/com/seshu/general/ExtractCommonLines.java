package com.seshu.general;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;

import com.seshu.common.ClientProperties;

public class ExtractCommonLines {
	
	public static void main(String a[])
	{
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br  = null;
		FileInputStream fstream2 = null;
		DataInputStream in2 = null;
		BufferedReader br2  = null;
		HashSet<String> all = new HashSet<String>();
		HashSet<String> existing = new HashSet<String>();
		
		try{
			ClientProperties.initialize("config\\General.properties");
			fstream = new FileInputStream(ClientProperties.getProperty("sourcefile"));
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));			
			String sourceLine = br.readLine();
			do{			
			fstream2 = new FileInputStream(ClientProperties.getProperty("targetfile"));
				try{
					in2 = new DataInputStream(fstream2);
					br2 = new BufferedReader(new InputStreamReader(in2));			
					String targetLine = br2.readLine();					
					
					do{
						all.add(targetLine);
						if(targetLine.contains(sourceLine)){							
							System.out.println(targetLine);
							existing.add(targetLine);
							break;
						}					
						targetLine = br2.readLine();
					}while(targetLine != null);
					br2.close();
					in2.close();			
					fstream2.close();
				}
				catch(Exception e){
					e.printStackTrace();
				}
				sourceLine = br.readLine();	
			}while(sourceLine != null);				
			br.close();
			in.close();			
			fstream.close();
			
			System.out.println("\n@@@@@@@@\nAll Patches: "+all.size());
			System.out.println("\n@@@@@@@@\nExisting Patches: "+existing.size());
			all.removeAll(existing);
			System.out.println("\n@@@@@@@@\nAll Patches: "+all.size());
			System.out.println("\n@@@@@@@@\nExisting Patches: "+existing.size());
			Iterator<String> itr = all.iterator();
			System.out.println("@@@@@@@@@@@@@ New Patches @@@@@@@@@@@@@@@@@@@");
			while(itr.hasNext())
				System.out.println(itr.next());
		}	
		catch(Exception e){
			e.printStackTrace();
		}		
	}
}