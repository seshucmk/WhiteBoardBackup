package com.seshu.zip;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import com.seshu.common.ClientProperties;

public class ReadZipFiles {
	
    public static String zipFile;
    public static String zipFilesFolder;	
	public static String outputDir;
	public static int bufferSize;

    public static void main(String args[]) throws IOException {
        init();
        File dir = new File(zipFilesFolder);
        if(dir.isDirectory()){
			String fileNames[] = dir.list();
			int size = fileNames.length;
			for(int i = 0; i<size; i++){
				zipFile = fileNames[i];
				readUsingZipInputStream();
			}
        }        
    }
    
    public static void init()
	{
		try{
			ClientProperties.initialize("config\\ZIP.properties");
			//zipFile = ClientProperties.getProperty("zipFile");
			zipFilesFolder = ClientProperties.getProperty("zipFilesFolder");
			outputDir = ClientProperties.getProperty("outputDir");
			bufferSize = Integer.parseInt(ClientProperties.getProperty("bufferSize"));			
		}catch(Exception e){
			System.out.println("Exception while reading SVN properties: ");
			e.printStackTrace();
		}
	}

    private static void readUsingZipInputStream() throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(zipFilesFolder+"\\"+zipFile));
        final ZipInputStream is = new ZipInputStream(bis);

        try {
            ZipEntry entry;
            while ((entry = is.getNextEntry()) != null) {
            	if(entry.getName().contains(".svn") || entry.getName().contains("wm_PL")
            			|| entry.getName().contains("wm_pl")
            			|| entry.getName().contains("RF_PL")){
            		
            	}
            	else if(entry.getName().contains(".java") || entry.getName().contains(".sql") || entry.getName().contains(".ebm"))
            		//System.out.printf("File: %s Size %d  Modified on %TD %n", entry.getName(), entry.getSize(), new Date(entry.getTime()));
            		System.out.println(zipFile +"\t"+ entry.getName());
                /*if(entry.getName().contains(".xml") || entry.getName().contains(".XML")){
                	extractEntry(entry, is);
                	break;
                }*/
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