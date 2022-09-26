package com.seshu.general;

public class SearchString {
	
	public static void main(String a[])
	{
		String searchContent = "<SSCC>0031234567M1103</SSCC> <DocumentID>M1103-PACK</DocumentID> <Contents /> <CostCentre />";
		System.out.println(searchContent.indexOf("<DocumentID>"));
		System.out.println(searchContent.indexOf("</DocumentID>"));
		System.out.println(searchContent.substring(41, 51));
	}
}
