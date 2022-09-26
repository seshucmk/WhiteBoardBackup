package com.seshu.socket;

import java.net.Socket;

public class CheckPort {

	public static void main(String a[]) {
		Socket s = null;
	    try {
	        s = new Socket("plosxidev01.paul-lange.plnet", 40000);
	        System.out.println("Connected to port: "+s.isConnected());
	    }
	    catch (Exception e){
	    	System.out.println("Could not connect. Error: "+e.getMessage());
	    }
	    finally {
	        if(s != null) {
	            try {
	            	s.close();
	            }
	            catch(Exception e){
	            	e.printStackTrace();
	            }
	        }
	    }		
	}
}
