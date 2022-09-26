package com.seshu.general;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Vector;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class InsertExcelFileData {
	public static void main(String[] args) {
		String fileName = "F:\\book.xlsx";
		Vector dataHolder = read(fileName);
		saveToDatabase(dataHolder);
	}

	public static Vector read(String fileName) {
		Vector cellVectorHolder = new Vector();
		try {
			FileInputStream myInput = new FileInputStream(fileName);
			// POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
			XSSFWorkbook myWorkBook = new XSSFWorkbook(myInput);
			XSSFSheet mySheet = myWorkBook.getSheetAt(0);
			Iterator rowIter = mySheet.rowIterator();
			while (rowIter.hasNext()) {
				XSSFRow myRow = (XSSFRow) rowIter.next();
				Iterator cellIter = myRow.cellIterator();
				Vector cellStoreVector = new Vector();
				while (cellIter.hasNext()) {
					XSSFCell myCell = (XSSFCell) cellIter.next();
					cellStoreVector.addElement(myCell);
				}
				cellVectorHolder.addElement(cellStoreVector);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cellVectorHolder;
	}

	private static void saveToDatabase(Vector dataHolder) {
		String Client = "";
		String Page = "";
		String AccessDate = "";
		String ProcessTime = "";
		String Bytes = "";
		for (int i = 0; i < dataHolder.size(); i++) {
			Vector cellStoreVector = (Vector) dataHolder.elementAt(i);
			for (int j = 0; j < cellStoreVector.size(); j++) {
				XSSFCell myCell = (XSSFCell) cellStoreVector.elementAt(j);
				String st = myCell.toString();
				Client = st;
				Page = st.substring(0);
				System.out.print(st);
			}
			try {
				Class.forName("com.jnetdirect.jsql.JSQLDriver").newInstance();
				Connection con = DriverManager.getConnection("jdbc:JSQLConnect://12.33.44.55/database=Environment",
						"root", "root");
				Statement stat = con.createStatement();
				int k = stat.executeUpdate("insert into items(ColumnName, STORERKEY, SKU, ALTSKU, PACKKEY, "
						+ "VENDOR, DEFAULTUOM, TYPE, UDF1, UDF2, UDF3, UDF4, UDF5, ADDDATE, ADDWHO, EDITDATE, EDITWHO) values('"
						+ Client + "','" + Page + "','" + AccessDate + "','" + ProcessTime + "','" + Bytes + "'");
				System.err.print(k);

				System.out.println("Data is inserted");
				stat.close();
				con.close();
			} catch (Exception e) {
			}
		}
	}
}