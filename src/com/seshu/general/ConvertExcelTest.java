package com.seshu.general;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ConvertExcelTest {
	HashMap<String, String> errorMasterMap = new HashMap<String, String>();
	int totalNoofErrorRecords = 0;
	public static int totalNoOfRows = 0;
	int recordCount;

	public ConvertExcelTest() {
		recordCount = 0;
	}
	
	public static void main(String a[]) {
		try{
			new ConvertExcelTest().displayFromExcel();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void displayFromExcel() throws SQLException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		InputStream inputStream = null;
		String excelErrorMessage = "";
		POIFSFileSystem fileSystem = null;
		try {
			// Initializing the XML document
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			Element rootElement = document.createElement("RootElement"); // proc name
			document.appendChild(rootElement);
			try
		    {
		        inputStream = new FileInputStream ("C:\\Projects\\Seshu\\WhiteBoard\\input\\excel\\allrows.xls");
		    }
		    catch (FileNotFoundException e)
		    {
		        System.out.println ("File not found in the specified path.");
		        e.printStackTrace ();
		    }
			fileSystem = new POIFSFileSystem(inputStream);
			HSSFWorkbook workBook = new HSSFWorkbook(fileSystem);
			HSSFSheet sheet = workBook.getSheetAt(0);
			ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
			int rowStartNo = sheet.getFirstRowNum();
			int rowEndNo = sheet.getLastRowNum();
			int phisicalNoofRows = sheet.getPhysicalNumberOfRows();
			totalNoOfRows = phisicalNoofRows - 2;
						
			for (int i = rowStartNo; i <= rowEndNo; i++) {
				if (i != 1) {
			
					rowStartNo = i;

					HSSFRow row = sheet.getRow(i);

					ArrayList<String> rowData = new ArrayList<String>();
					int firstCellNo = row.getFirstCellNum();
					int lastCellNo = row.getLastCellNum();
					for (int j = firstCellNo + 1; j < lastCellNo; j++) {

						HSSFCell cell = row.getCell((short) j);
						if (cell != null) {
							switch (cell.getCellType()) {
							case HSSFCell.CELL_TYPE_NUMERIC: {
								// NUMERIC CELL TYPE
								rowData.add(cell.getNumericCellValue() + "");
								break;
							}
							case HSSFCell.CELL_TYPE_STRING:

							{
								String richTextString = cell.getStringCellValue();
								rowData.add(richTextString.trim());
								break;
							}
							case HSSFCell.CELL_TYPE_BLANK: {
								rowData.add(cell.getStringCellValue() + "");
								break;
							}
							default: {
								// types other than String and Numeric.
								rowData.add("");
								break;
							}
							}
						} else {
							rowData.add("");
						}

						firstCellNo++;
					}
					rowStartNo++;
					data.add(rowData);					
				}				
			} // end for
			generateXmlForHeader(rootElement, document, data, factory, builder, excelErrorMessage);			
			workBook.close();
		} catch (IOException e) {
			System.out.println("IOException " + e.getMessage());
		} catch (ParserConfigurationException e) {
			System.out.println("ParserConfigurationException " + e.getMessage());
		} catch (TransformerConfigurationException e) {
			System.out.println("TransformerConfigurationException " + e.getMessage());
		} catch (TransformerException e) {
			System.out.println("TransformerException " + e.getMessage());
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void generateXmlForHeader(Element rootElement, Document document, ArrayList<ArrayList<String>> data,
			DocumentBuilderFactory factory, DocumentBuilder builder, String excelErrorMessage)
			throws TransformerException, ParserConfigurationException {
		
		int numOfProduct = data.size();
		String headerString = "";
		for (int i = 1; i < numOfProduct; i++) {
			Element productElement = document.createElement("Item");
			rootElement.appendChild(productElement);
			int index = 0;
			for (String s : data.get(i)) {
				headerString = data.get(0).get(index);
				Element headerElement = document.createElement(headerString);
				productElement.appendChild(headerElement);
				if (headerString != null && "STORERKEY".equalsIgnoreCase(headerString)) {
					headerElement.appendChild(document.createTextNode(s));
				} else {
					headerElement.appendChild(document.createTextNode(s));
				}
				index++;
			}
			recordCount++;
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer1 = tf.newTransformer();
			transformer1.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			StringWriter writer = new StringWriter();
			transformer1.transform(new DOMSource(document), new StreamResult(writer));
			String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
			System.out.println(output);
		}
		
	}
}