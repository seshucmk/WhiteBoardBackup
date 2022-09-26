package com.seshu.word;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.POIXMLProperties;
import org.apache.poi.hpsf.Section;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class DocReader {

	public static void readDocFile(String fileName) {

		try {
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());

			HWPFDocument doc = new HWPFDocument(fis);

			WordExtractor we = new WordExtractor(doc);

			List<Section> list = we.getDocSummaryInformation().getSections();
			String[] paragraphs = we.getParagraphText();
			
			System.out.println("Total no of paragraph "+paragraphs.length);
			for (String para : paragraphs) {
				System.out.println(para.toString());
			}
			we.close();
			//doc.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void readDocxFile(String fileName) {

		try {
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());

			XWPFDocument document = new XWPFDocument(fis);
			POIXMLProperties properties = document.getProperties();
			//System.out.println(document.getProperties().getCoreProperties().getCreator());
			
			List<XWPFParagraph> paragraphs = document.getParagraphs();
			
			System.out.println("Total no of paragraph "+paragraphs.size());
			for (XWPFParagraph para : paragraphs) {
				System.out.println(para.getText());
			}
			document.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		readDocxFile("C:\\Projects\\Seshu\\WhiteBoard\\input\\word\\DES-020-TS-BTA007-Validate Packing Slip Number On ASN Close.doc");

		//readDocFile("C:\\Projects\\Seshu\\WhiteBoard\\input\\word\\DES-020-TS-BTA007-Validate Packing Slip Number On ASN Close.doc");

	}
}