package com.seshu.general;

public class AppendZeros {
	
	public static void main(String a[])
	{
		int number = 234;		
		System.out.println("00000".substring(0, 5-(number+"").length())+number);
		System.out.println(Integer.parseInt("000234"));
	}
}
