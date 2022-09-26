package com.seshu.general;

public class JsonStringTest {
	
	public static void main(String a[])
	{	
		String receiptkey = "0000000221";
		String storerkey = "TRC";
		String infokey = "0000005521";
		double qty = 34;
		
		String json = "{ \"receiptkey\":\""+receiptkey+"\","
				+ "\"storerkey\":\""+storerkey+"\","
				+ "\"infokey\":\""+infokey+"\","
				+ "\"qty\":\""+qty+"\","
				+ "\"storerkey\":\""+storerkey+"\"}";
		System.out.println(json);
		
	}
}
