package com.seshu.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ReadPDFFile {

	public static void main(String[] args) throws IOException {


        File f=new File("C:\\pdf\\Hindi HW.pdf");

        OutputStream oos = new FileOutputStream("C:\\pdf\\Hindi HW.txt");

        byte[] buf = new byte[8192];

        InputStream is = new FileInputStream(f);

        int c = 0;

        while ((c = is.read(buf, 0, buf.length)) > 0) {
            oos.write(buf, 0, c);
            oos.flush();
        }
        //System.out.println(oos.toString());
        oos.close();
        System.out.println("stop");
        is.close();

    }
}