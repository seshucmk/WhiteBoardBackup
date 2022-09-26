package com.seshu.general;

import java.io.*;
import java.util.*;

public class CompareFiles {
    public static void main(String[] args) throws FileNotFoundException {
    	String fileSet1[] = null;
    	String fileSet2[] = null;
    	int count = 0;
    	String first = "", second = "";
    	String diffFiles = "";
    	try{
			File dir1 = new File("C:\\Seshu\\test\\Compare\\WMS");
			File dir2 = new File("C:\\Seshu\\test\\Compare\\SVN");
	    	if(dir1.isDirectory()){
	    		fileSet1 = dir1.list();
	    	}
	    	if(dir2.isDirectory()){
	    		fileSet2 = dir2.list();
	    	}
	    	count = fileSet1.length;
			for(int i = 0; i<count; i++){
				boolean dif = false;
				Scanner input1 = new Scanner(new File(dir1.getPath()+"\\"+fileSet1[i]));//read first file		        
		        Scanner input2 = new Scanner(new File(dir2.getPath()+"\\"+fileSet2[i]));//read second file
		        while(input1.hasNextLine() && input2.hasNextLine()){
		            first = input1.nextLine();   
		            second = input2.nextLine(); 

		            if(!first.equals(second)){
		            	dif = true;
		            }
		            if(dif) {
		            	diffFiles = diffFiles+fileSet1[i]+"\n";
		            	break;
		            }
		        }
		        System.out.print((i*100/count)+"%");
		        System.out.print("\r") ;
			}
    	}catch(Exception e) {
    		
    	}
    	System.out.println("\r"+diffFiles);
    }
}