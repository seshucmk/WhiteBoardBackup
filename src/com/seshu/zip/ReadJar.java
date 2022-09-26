package com.seshu.zip;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ReadJar {
	
    public static void main(String args[]) throws IOException {
    	List<String> classNames = new ArrayList<String>();
    	ZipInputStream zip = new ZipInputStream(new FileInputStream("C:\\Projects\\Seshu\\WhiteBoard\\input\\zip\\test.jar"));
    	for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
    	    if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
    	        // This ZipEntry represents a class. Now, what class does it represent?
    	        String className = entry.getName().replace('/', '.'); // including ".class"
    	        classNames.add(className.substring(0, className.length() - ".class".length()));
    	    }
    	}
    	Iterator<String> itr = classNames.iterator();
		while(itr.hasNext()){
			System.out.println(itr.next());
		}
    	zip.close();
    }
}