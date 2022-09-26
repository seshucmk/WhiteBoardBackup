package com.seshu.general;

import java.io.File;


public class FileNamesList {
	
	public static void main(String args[])
	  {	
		  try{
			  // ClientProperties.initialize("Config\\FileProperties.properties");
			  // String filePath = ClientProperties.getProperty("listpath");		  
			  File dir = new File("C:\\Projects\\PL\\Custom\\Documents\\TDS");
			  if(dir.isDirectory())
			  {
				  File files[] = dir.listFiles();
				  for(int i=0; i < files.length; i++)
					  System.out.println(files[i].getName());
			  }
			  
		  }catch (Exception e){
			  System.err.println("Error: " + e.getMessage());
		  }	  
	}	
}
