package com.seshu.general;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class GetDataFromDatabase {
	
	public static void main(String a[])
	{
		try{
			Class.forName("net.sourceforge.jtds.jdbc.Driver");			
			Connection con = DriverManager.getConnection("jdbc:jtds:sqlserver://<server>:1433/db","sa","pswd");			
			Statement callStmt = con.createStatement();
			ResultSet rs = callStmt.executeQuery("exec procedure");
			if(rs.next()){
				System.out.println(rs.getString(1));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}	
}
