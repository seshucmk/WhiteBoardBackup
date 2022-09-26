package com.seshu.jsonvsxml;

import org.json.JSONObject;
import org.json.XML;

import com.seshu.general.FileHelper;

public class JSON2XML {
	
	public static void main(String a[])
	{
		FileHelper dataFromFile = new FileHelper();
		try{					
			String jsonContent = dataFromFile.returnFileContent("input\\json\\test.json");
			JSONObject json = new JSONObject(jsonContent);
			String xml = XML.toString(json);		     
		    System.out.println("<Message>"+xml+"</Message>");
		    String jsonOut = XML.toJSONObject(xml).toString();
		    System.out.println(jsonOut);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
