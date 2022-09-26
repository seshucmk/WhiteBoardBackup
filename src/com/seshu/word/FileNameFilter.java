package com.seshu.word;

import java.io.File;
import java.io.FilenameFilter;

public class FileNameFilter implements FilenameFilter {
	
	public boolean accept(File dir, String name) {
		boolean accept = false;
		String nameLowerCase = name.toLowerCase();
		if(nameLowerCase.endsWith(".doc") || 
				nameLowerCase.endsWith(".docx") ||
				nameLowerCase.endsWith(".docm")) {
			accept = true;
		}
		return accept;
	}
}