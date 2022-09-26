package com.seshu.general;

public class FileDirectoryCheck {

	public static void main(String a[]) {
		try {
			String current = new java.io.File(".").getCanonicalPath();
			System.out.println("Current dir:" + current);
		} catch (Exception e) {
		}
	}
}
