package com.seshu.general;

import java.util.Date;
import java.util.StringTokenizer;

import com.seshu.word.MSWordReader;

public class Test {
	
	public static void main(String a[])
	{
		/*
		 * FileHelper helper = new FileHelper(); String content = MSWordReader.
		 * getWordDocumentData("C:\\Seshu\\test\\AMER\\Nova\\FBR's\\May19-2014 Unitary Picking - New proposal by anderson oliveira v1.docx"
		 * ); // May19-2014 Unitary Picking - New proposal by anderson oliveira v1.docx
		 * //String content =
		 * helper.returnFileContent("C:\\Seshu\\test\\AMER\\Nova\\FBR's\\test.txt");
		 * System.out.println(content); String str =
		 * "Brown Thomas"+"|SCS|"+"C://Seshu//DDFF//ddd"; String t1 = str.substring(0,
		 * str.indexOf("|SCS|")); String t2 = str.substring(str.indexOf("|SCS|")+5,
		 * str.length()); System.out.println(t1);
		 */
		/*StringTokenizer tokens = new StringTokenizer("0000000234##00001", "##");
		System.out.println(tokens.nextToken());
		System.out.println(tokens.nextToken());
		System.out.println(Long.MAX_VALUE);*/
		try {
		String current = new java.io.File( "." ).getCanonicalPath(); 
        System.out.println("Current dir:"+current);
		}catch(Exception e) {}
	}
}
