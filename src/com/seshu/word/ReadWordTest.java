package com.seshu.word;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;


public class ReadWordTest {


    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream("C:\\Projects\\Seshu\\WhiteBoard\\input\\word\\ANA-050-BTA028-Batch Picking and Sorting changes.docx");
            POIFSFileSystem fileSystem = new POIFSFileSystem(fis);            
            org.apache.poi.xwpf.extractor.XWPFWordExtractor oleTextExtractor =
            new XWPFWordExtractor(new XWPFDocument(fis));
            System.out.print(oleTextExtractor.getText());            
        } catch (Exception e) {
                e.printStackTrace();
        }
    }

}