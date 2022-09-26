package com.seshu.zip;

import java.io.File;
import java.io.IOException;

import com.seshu.common.ClientProperties;

public class ExtractZipFiles {
	
	private static GeneralDTO generalDTO;

    public static void main(String args[]) throws IOException {
    	init();
        File dir = new File(generalDTO.getZipFilesInput());
        if(dir.isDirectory()){
        	File files[] = dir.listFiles();
			for(int i=0; i < files.length; i++){
				String outFile = files[i].getName().substring(0, files[i].getName().indexOf("."));
				UnZip unzip = new UnZip();
		        unzip.unzip(files[i].getAbsolutePath(), generalDTO.getZipFilesOutput() + outFile);
			}			
		}
    }
    
    public static void init(){
		try{
			generalDTO = ClientProperties.getInstance();			
		}catch(Exception e){
			System.out.println("Exception while reading properties: ");
			e.printStackTrace();
		}
	}
}