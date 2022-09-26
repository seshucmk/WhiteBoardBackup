package com.seshu.zip;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.seshu.common.ClientProperties;

public class ReadZip {
	
    public static String zipFile;	
	public static String outputDir;
	public static int bufferSize;

    public static void main(String args[]) throws IOException {
        init();
    	//readUsingZipFile();
        //readUsingZipInputStream();
        //unZipEntries();
        File dir = new File("C:\\Projects\\Seshu\\WhiteBoard\\input\\zip\\FLD");
        if(dir.isDirectory()){
        	File files[] = dir.listFiles();
			for(int i=0; i < files.length; i++){
				//System.out.println(files[i].getName());
				String outFile = files[i].getName().substring(0, files[i].getName().indexOf("."));
				//System.out.println(files[i].getName().substring(0, files[i].getName().indexOf(".")));
				//System.out.println(files[i].getAbsolutePath());
				UnZip unzip = new UnZip();
		        unzip.unzip(files[i].getAbsolutePath(), "C:\\Projects\\Seshu\\WhiteBoard\\output\\zip\\FLD\\" + outFile);
			}			
		}
        //UnZip unzip = new UnZip();
        //unzip.unzip("C:\\Projects\\Seshu\\WhiteBoard\\input\\zip\\patch1102_1928883_606465.zip", "C:\\Projects\\Seshu\\WhiteBoard\\output\\zip\\patch1102_1928883_606465");
    }
    
    public static void init()
	{
		try{
			ClientProperties.initialize("config\\ZIP.properties");
			zipFile = ClientProperties.getProperty("zipFile");
			outputDir = ClientProperties.getProperty("outputDir");
			bufferSize = Integer.parseInt(ClientProperties.getProperty("bufferSize"));			
		}catch(Exception e){
			System.out.println("Exception while reading SVN properties: ");
			e.printStackTrace();
		}
	}

    /*private static void readUsingZipFile() throws IOException {
        final ZipFile file = new ZipFile(zipFile);
        System.out.println("Iterating over zip file : " + zipFile);

        try {
            final Enumeration<? extends ZipEntry> entries = file.entries();
            while (entries.hasMoreElements()) {
                final ZipEntry entry = entries.nextElement();
                System.out.printf("File: %s Size %d  Modified on %TD %n", entry.getName(), entry.getSize(), new Date(entry.getTime()));
                if(entry.getName().contains(".xml") || entry.getName().contains(".XML")){
                	extractEntry(entry, file.getInputStream(entry));
                	break;
                }
            }
            System.out.printf("Zip file %s extracted successfully in %s", zipFile, outputDir);
        } finally {
            file.close();
        }

    }*/

    private static void readUsingZipInputStream() throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(zipFile));
        final ZipInputStream is = new ZipInputStream(bis);

        try {
            ZipEntry entry;
            while ((entry = is.getNextEntry()) != null) {
                System.out.printf("File: %s Size %d  Modified on %TD %n", entry.getName(), entry.getSize(), new Date(entry.getTime()));
                if(entry.getName().contains(".xml") || entry.getName().contains(".XML")){
                	extractEntry(entry, is);
                	break;
                }
            }
        } finally {
            is.close();
        }

    }
    
    private static void unZipEntries() throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(zipFile));
        final ZipInputStream is = new ZipInputStream(bis);

        try {
            ZipEntry entry;
            while ((entry = is.getNextEntry()) != null) {                
               	extractEntry(entry, is);
               	break;
            }
        } finally {
            is.close();
        }

    }

    private static void extractEntry(final ZipEntry entry, InputStream is) throws IOException {
        String exractedFile = outputDir + entry.getName();
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(exractedFile);
            final byte[] buf = new byte[bufferSize];
            int length;

            while ((length = is.read(buf, 0, buf.length)) >= 0) {
                fos.write(buf, 0, length);
            }

        } catch (IOException ioex) {
            fos.close();
        }

    }

}