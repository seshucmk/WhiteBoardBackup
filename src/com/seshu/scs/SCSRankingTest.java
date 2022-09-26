package com.seshu.scs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class SCSRankingTest {

	public static void main(String a[])
	{
		Connection con = null;
		PreparedStatement st = null;
		String content = "";
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");			
			con = DriverManager.getConnection("jdbc:jtds:sqlserver://inhynsyaddanap2:1433/SCS","seshu","seshu");
			st = con.prepareStatement("SELECT DOCDATA FROM FDS WHERE FDS = 'FDS0000459'");				
			ResultSet rsContent = st.executeQuery();				
			if(rsContent.next()) {
				content =  rsContent.getString(1);
			}
			st.close();
			
			// CODES
			String list = "";
			String code = "";
			content = content.toUpperCase();
			st = con.prepareStatement("SELECT LISTNAME, CODE FROM CODES");				
			ResultSet rs = st.executeQuery();
			String currentProcess = "";
			int processRank = 0;
			TreeMap<String, Integer> processMap = new TreeMap<String, Integer>(Collections.reverseOrder());
			TreeMap<String, Integer> codeMap = new TreeMap<String, Integer>();
			while(rs.next()) {				
				list =  rs.getString(1).toUpperCase();
				code =  rs.getString(2).toUpperCase();
				
				if(!list.equals(currentProcess)) {
					currentProcess = list;
					processRank = 0;
				}
				
				String temp[] = content.split(code);
				int codeRank = 0;
				if(temp.length > 1)
					codeRank = temp.length;
				processRank = processRank + codeRank;
				processMap.put(currentProcess, processRank);
				codeMap.put(currentProcess+"-"+code, codeRank);
			}
			st.close();			
			con.close();
						
			TreeMap<String, String> sortedMap = new TreeMap<String, String>(Collections.reverseOrder());
			for (Map.Entry<String, Integer> entry : processMap.entrySet()) {		         
		        String currentKey =  entry.getKey();
		        Integer currentNumber = entry.getValue();
		        System.out.println(currentKey+": "+currentNumber);
		        String newNum = "000000".substring(0, 5-(currentNumber+"").length())+currentNumber;
		        sortedMap.put(newNum+currentKey, currentKey);
		    }
			int i = 0;
			String output = "";
			for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
				output =  output+entry.getValue();
				i++;
				if(i == 3)
					break;
				else
					output =  output+"|";				
		    }
			System.out.println(output);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
