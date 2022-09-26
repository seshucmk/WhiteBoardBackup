package com.seshu.general;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class StringTest {

	public static void main(String a[]) {
		/*
		 * String test = "H1|testing|success|"; System.out.println(test.substring(0,
		 * 2));
		 */

		String descr = "C:\\Seshu\\test\\EMEA\\PaulLange\\FBRs\\DES-020-PL18_Design_Specification_DeliveryNoteReport v45-V_09092019.docm";
		int start = descr.indexOf("DES-020-");
		String part = descr.substring(start+8, descr.length());
		if(part.contains("-")) {			
			String ref = part.substring(0, part.indexOf("-"));
			System.out.println(ref);
			if(ref.contains(" ")) {
				ref = ref.substring(0, ref.indexOf(" "));
			}
			if(ref.contains("_")) {
				ref = ref.substring(0, ref.indexOf("_"));
			}
			System.out.println(ref);
		}
		descr = "C:\\Seshu\\test\\EMEA\\BrownThomas\\Developments\\BTA002\\custBTA002_17017_BTA.zip";
		start = descr.indexOf("cust");
		part = descr.substring(start+4, descr.length());
		if(part.contains("_")) {
			String ref = part.substring(0, part.indexOf("_"));
			System.out.println(ref);
		}
		
		
		/*
		 * Date dt1 = new Date(); SimpleDateFormat sdf = new
		 * SimpleDateFormat("yyyyMMddHHmmssSSS"); System.out.println(sdf.format(new
		 * Date())); try{ String current = new java.io.File( "." ).getCanonicalPath();
		 * System.out.println("Current dir:"+current); System.out.println(descr); String
		 * descr = "Avisabor-Indústria Agro-Alimentar, S.A."; try{ byte[] ptext =
		 * descr.getBytes("ISO-8859-1"); String newUTF = new String(ptext,
		 * "ISO-8859-1"); System.out.println(newUTF);
		 * 
		 * String baseText[] = {"ReceiptTo", "DateTo", "ReceiptFrom", "ASNs",
		 * "PLArchiveRPT_ReceiptFrom", "Owner"}; String archiveParamText = ""; for(int
		 * i=0; i < baseText.length; i++){ if(baseText[i].contains("PLArchiveRPT")){
		 * archiveParamText = baseText[i]; break; } } HashMap paramsAndValues = new
		 * HashMap(); paramsAndValues.put("ReceiptTo", "800092020");
		 * paramsAndValues.put("ReceiptFrom", "600092020"); String archiveParams[] =
		 * archiveParamText.split("_"); String archiveFileName = ""; for(int i=0; i <
		 * archiveParams.length; i++){ if(archiveParams[i] != null &&
		 * !archiveParams[i].contains("PLArchiveRPT")){ for(int j=0; j <
		 * baseText.length; j++){ if(baseText[j].equalsIgnoreCase(archiveParams[i])){
		 * if(paramsAndValues.get(baseText[j]) != null) archiveFileName =
		 * archiveFileName.concat(paramsAndValues.get(baseText[j]).toString()); break; }
		 * } } } System.out.println("File Name: "+archiveFileName); }catch(Exception e){
		 * e.printStackTrace(); }
		 */

	}
}
