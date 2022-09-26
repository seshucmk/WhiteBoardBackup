package com.seshu.word;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.seshu.common.ClientProperties;

public class MSWordReader {
	
	public static String wordFilesPath = "";
	public static void main(String[] args) {

		init();
		String wordContent = "";
		File dir = new File(wordFilesPath);
		if(dir.isDirectory()){
			String fileNames[] = dir.list();			
			int size = fileNames.length;
			
			for(int i = 0; i<size; i++){				
				String nameLowerCase = fileNames[i].toLowerCase();
				if(nameLowerCase.endsWith(".doc") || 
						nameLowerCase.endsWith(".docx") ||
						nameLowerCase.endsWith(".docm")) {
					wordContent = getWordDocumentData(wordFilesPath+fileNames[i]);
					insertData(wordContent);
					System.out.println(wordContent);
					//wordContent = wordContent.replaceAll("'", ".");
					//System.out.println(wordContent);
					if(fileNames[i].contains("Allocate Strategy"))
						System.out.println(wordContent);
				}
			}			
		}
		//System.out.println(wordContent);
	}
	
	public static void init() {
		try{
			ClientProperties.initialize("config\\General.properties");
			wordFilesPath = ClientProperties.getProperty("testWordFilesPath");
			//wordFilesPath = ClientProperties.getProperty("WordFilesPath");
		}catch(Exception e){
			System.out.println("Exception during initialization..."+ e.getMessage());			
		}
	}

	public static String getWordDocumentData(String fileName) {		
		String docText = "";
		if(fileName.contains("WMS060 - Changes on location hold v0.4.doc")) {
			System.out.println("");
		}
		try {		
			HWPFDocument doc = getWordDocFormat(fileName);
			
			if(doc != null) {
				WordExtractor we = new WordExtractor(doc);
				docText = we.getText();				
				we.close();
				//doc.close();
			}
			else {
				XWPFDocument docx = getWordDocXFormat(fileName);
				if(docx != null) {					
					XWPFWordExtractor we = new XWPFWordExtractor(docx);
					docText = we.getText();
					we.close();
					docx.close();
				}
			}
			docText = trimDocumentData(docText);
		}catch(Exception e) {
			System.out.println("**************** Error reading document ********* "+e.getMessage());
		}
		return docText;
	}
	
	public static HWPFDocument getWordDocFormat(String fileName) {
		HWPFDocument doc = null;
		try {
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());
			doc = new HWPFDocument(fis);
		}catch(Exception e){		
			//System.out.println(e.getMessage());
		}
		return doc;
	}
	
	public static XWPFDocument getWordDocXFormat(String fileName) {
		XWPFDocument docx = null;
		try {
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());
			docx = new XWPFDocument(fis);
		}catch(Exception e){
			//System.out.println(e.getMessage());
		}
		return docx;
	}
	
	public static String trimDocumentData(String docText) {
		String trimmedData = "";
		String lines[] = docText.split("\n");
		for(String line: lines) {
			if(line != null && line.trim().length() != 0)
				trimmedData = trimmedData+line+"\n";
		}
		
		return trimmedData;
	}
	
	public static void insertData(String data) {
		Connection con = null;
		PreparedStatement st = null;
				
		try{
			Class.forName("net.sourceforge.jtds.jdbc.Driver");			
			con = DriverManager.getConnection("jdbc:jtds:sqlserver://inhynsyaddanap1:1433/SCS","seshu","seshu");
			st = con.prepareStatement("INSERT INTO TEST(DOC) VALUES (?)");
			st.setString(1, data);
			st.executeUpdate();
			st.close();
			con.close();
		} catch(Exception e){
			System.out.println("DB error in processCARFile(): "+e.getMessage());
		}
		finally {			
		}	
	}
}