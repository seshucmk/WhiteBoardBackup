package com.seshu.pdf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class ReadHindiPDFFile {

	public static void main(String[] args) throws IOException {


        File f=new File("C:\\pdf\\Hindi HW.pdf");
        InputStream is = new FileInputStream(f);
        BufferedReader in = new BufferedReader(new InputStreamReader(is, "ISO 8859-5"));
        String aLine;
        String content = "";
        while ((aLine = in.readLine()) != null) {
        	//System.out.println(aLine);
        	content = content + aLine;
        }
        writeToFile(content, "C:\\pdf\\Hindi HW.txt");
		/*
		 * OutputStream oos = new
		 * FileOutputStream("C:\\pdf\\Hindi HW.txt");
		 * 
		 * byte[] buf = new byte[8192];
		 * 
		 * 
		 * 
		 * int c = 0;
		 * 
		 * while ((c = is.read(buf, 0, buf.length)) > 0) { oos.write(buf, 0, c);
		 * oos.flush(); } //System.out.println(oos.toString()); oos.close();
		 * System.out.println("stop"); is.close();
		 */

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
}