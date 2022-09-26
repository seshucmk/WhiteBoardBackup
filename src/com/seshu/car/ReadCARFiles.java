package com.seshu.car;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.seshu.common.ClientProperties;

public class ReadCARFiles {
	
    public static String carFile;

    public static void main(String args[]) throws IOException {
        init();
		readUsingZipInputStream();    
    }
    
    public static void init()
	{
		try{
			ClientProperties.initialize("config\\General.properties");
			carFile = ClientProperties.getProperty("carFile");			
		}catch(Exception e){
			System.out.println("Error loading properties"+e.getMessage());
		}
	}

    private static void readUsingZipInputStream() throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(carFile));
        final ZipInputStream is = new ZipInputStream(bis);

        try {
            ZipEntry entry;
            int read = 0;
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((entry = is.getNextEntry()) != null) {
            	if(!entry.isDirectory() && entry.getName().startsWith("custom") 
            			&& entry.getName().contains(".xml")) {
            		while ((read = is.read(buffer, 0, buffer.length)) > 0)
            		      baos.write(buffer, 0, read);
            		break;
            	}
            }
            String content = baos.toString("UTF-8");
            //System.out.println(content);
        }catch(Exception e) {
        	
        }finally {
            is.close();
        }
    }
    
    public static String getCARCustomXML(String carFileName) throws IOException {
    	String content = "";
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(carFileName));
        final ZipInputStream is = new ZipInputStream(bis);

        try {
            ZipEntry entry;
            int read = 0;
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((entry = is.getNextEntry()) != null) {
            	if(!entry.isDirectory() && entry.getName().startsWith("custom") 
            			&& entry.getName().contains(".xml")) {
            		while ((read = is.read(buffer, 0, buffer.length)) > 0)
            		      baos.write(buffer, 0, read);
            		break;
            	}
            }
            content = baos.toString("UTF-8");
            //System.out.println(content);
        }catch(Exception e) {
        	
        }finally {
            is.close();
        }
        return content;
    }
}