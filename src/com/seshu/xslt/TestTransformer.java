package com.seshu.xslt;

import java.io.File;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class TestTransformer {
    public static void main(String[] args) {
        
    	try{
    	TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("C:\\Projects\\Seshu\\WhiteBoard\\input\\xsl\\ProcessAdvanceShipNotice.xsl"));
        Transformer transformer = factory.newTransformer(xslt);

        Source text = new StreamSource(new File("C:\\Projects\\Seshu\\WhiteBoard\\input\\xsl\\asn_out12.xml"));
        transformer.transform(text, new StreamResult(new File("C:\\Projects\\Seshu\\WhiteBoard\\input\\xsl\\asn_generated_out.xml")));
        System.out.println("done...");
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
}