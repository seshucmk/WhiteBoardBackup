package com.seshu.socket;

import java.io.DataOutputStream;
import java.net.Socket;

public class TestSocketClient {

	public static void main(String a[]) {
		try {
			Socket s = new Socket("localhost", 5066);
			DataOutputStream dout = new DataOutputStream(s.getOutputStream());
			dout.writeUTF("Hello Server");
			dout.flush();
			dout.close();
			s.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
