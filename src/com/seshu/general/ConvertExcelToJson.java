package com.seshu.general;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

//"dd.MM.yy"
public class ConvertExcelToJson {
	
	public static void main(String a[]){
		ExcelToJsonConverterConfig config = ExcelToJsonConverterConfig.create();
		try{
			ExcelWorkbook excelData = new ConvertExcelToJson(config).convert(config);
			//System.out.println(excelData.toJson(true));
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public ExcelToJsonConverterConfig config = null;
	
	public ConvertExcelToJson(ExcelToJsonConverterConfig config) {
		this.config = config;
	}
	
	public ExcelWorkbook convert(ExcelToJsonConverterConfig config) throws InvalidFormatException, IOException {
		return new ConvertExcelToJson(config).convert();
	}
	
	public ExcelWorkbook convert()
			throws InvalidFormatException, IOException {
		ExcelWorkbook book = new ExcelWorkbook();

		InputStream inp = new FileInputStream(config.getSourceFile());
		BufferedInputStream bis = new BufferedInputStream(inp);
		
		Workbook wb = WorkbookFactory.create(bis);

		book.setFileName(config.getSourceFile());
		int loopLimit =  wb.getNumberOfSheets();
		if (config.getNumberOfSheets() > 0 && loopLimit > config.getNumberOfSheets()) {
			loopLimit = config.getNumberOfSheets();
		}
		int rowLimit 			= config.getRowLimit();
		int startRowOffset 		= config.getRowOffset();
		int currentRowOffset 	= -1;
		int totalRowsAdded 		= 0;


		for (int i = 0; i < loopLimit; i++) {
			Sheet sheet = wb.getSheetAt(i);
			if (sheet == null) {
				continue;
			}
			ExcelWorksheet tmp = new ExcelWorksheet();
			ArrayList<Object> headerData = new ArrayList<Object>();
			
			tmp.setName(sheet.getSheetName());
        	for(int j=sheet.getFirstRowNum(); j<=sheet.getLastRowNum(); j++) {
	    		Row row = sheet.getRow(j);
	    		if(row==null) {
	    			continue;
	    		}
	    		boolean hasValues = false;
	    		ArrayList<Object> rowData = new ArrayList<Object>();
	    		StringBuffer jsonData = new StringBuffer();
	    		for(int k=0; k<=row.getLastCellNum(); k++) {
	    			Cell cell = row.getCell(k);
	    			if(cell!=null) {
	    				Object value = cellToObject(cell);
	    				hasValues = hasValues || value!=null;
	    				rowData.add(value);	 
	    				if(j==0 && k != 0){
	    					String headerColumn = value.toString().toLowerCase();
	    					headerData.add(headerColumn);
	    				}	    				
	    			} else {
                        rowData.add(null);
                    }
	    		}
	    		if(hasValues||!config.isOmitEmpty()) {
					currentRowOffset++;
	    			if (rowLimit > 0 && totalRowsAdded == rowLimit) {
	    				break;
					}
					if (startRowOffset > 0 && currentRowOffset < startRowOffset) {
	    				continue;
					}
	    			tmp.addRow(rowData);
	    			if(j > 1){
    					for(int hd = 0;hd < headerData.size(); hd++){
    						jsonData.append("\""+headerData.get(hd).toString()+"\""+": "+"\""+rowData.get(hd+1)+"\"");
    						if(hd+1 != headerData.size()){
    							jsonData.append(",");
    						}
    					}
    				}
	    			if(jsonData.toString().length() != 0){
	    				//System.out.println("{\n"+jsonData.toString()+"\n}"+"\n*********** End of message ******\n");
	    				//System.out.println("{"+jsonData.toString()+"}");
	    				System.out.println(callRestAPI("{"+jsonData.toString()+"}"));
	    			}
	    			totalRowsAdded++;
	    			jsonData = new StringBuffer();
	    		}
	    	}
        	if(config.isFillColumns()) {
        		tmp.fillColumns();
        	}
			book.addExcelWorksheet(tmp);
		}

		return book;
	}
	
	private Object cellToObject(Cell cell) {

		int type = cell.getCellType();
		
		if(type == Cell.CELL_TYPE_STRING) {
			return cleanString(cell.getStringCellValue());
		}
		
		if(type == Cell.CELL_TYPE_BOOLEAN) {
			return cell.getBooleanCellValue();
		}
		
		if(type == Cell.CELL_TYPE_NUMERIC) {
			
			if (cell.getCellStyle().getDataFormatString().contains("%")) {
		        return cell.getNumericCellValue() * 100;
		    }
			
			return numeric(cell);
		}
		
		if(type == Cell.CELL_TYPE_FORMULA) {
	        switch(cell.getCachedFormulaResultType()) {
	            case Cell.CELL_TYPE_NUMERIC:
	    			return numeric(cell);
	            case Cell.CELL_TYPE_STRING:
	    			return cleanString(cell.getRichStringCellValue().toString());
	        }
		}
		
		return null;

	}
	
	private String cleanString(String str) {
		return str.replace("\n", "").replace("\r", "");
	}

	private Object numeric(Cell cell) {
		if(HSSFDateUtil.isCellDateFormatted(cell)) {
			if(config.getFormatDate()!=null) {
				return config.getFormatDate().format(cell.getDateCellValue());	
			}
			return cell.getDateCellValue();
		}
		return Double.valueOf(cell.getNumericCellValue());
	}
	private String callRestAPI(String input){
		try {
			System.out.println("Calling REST API...");
			System.out.println("Input: "+input);
			URL url = new URL("svnurl");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("username", "seshu");
			conn.setRequestProperty("password", "seshu");
			conn.setRequestProperty("Tenant", "test");		

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return "";
	}
}