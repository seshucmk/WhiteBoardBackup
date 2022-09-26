package com.seshu.scs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class SCSRanking {

	public static void main(String a[])
	{
		Connection con = null;
		PreparedStatement st = null;
		String fds = "";
		String content = "";
		SCSRanking ranking = new SCSRanking();
		System.out.println("Started...");
		int i = 0;
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");			
			con = DriverManager.getConnection("jdbc:jtds:sqlserver://inhynsyaddanap1:1433/SCS","seshu","seshu");
			st = con.prepareStatement("SELECT FDS, DOCDATA FROM FDS");				
			ResultSet rsContent = st.executeQuery();				
			while(rsContent.next()) {
				i++;
				fds =  rsContent.getString(1);
				content =  rsContent.getString(2);
				String top3 = ranking.getTopProcesses(content);
				ranking.updateFDSProcess(fds, top3);
				if(i%100 == 0)
					System.out.print(i+".");
				if(i%1000 == 0)
					System.out.print("\n");
			}
			st.close();			
			con.close();
			System.out.println("\nDone!");
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	public String getTopProcesses(String content) {
		String output = "";
		// CODES
		Connection con = null;
		PreparedStatement st = null;
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");			
			con = DriverManager.getConnection("jdbc:jtds:sqlserver://inhynsyaddanap1:1433/SCS","seshu","seshu");			
			String list = "";
			String code = "";
			content = content.toUpperCase();
			st = con.prepareStatement("SELECT LISTNAME, CODE FROM CODES");				
			ResultSet rs = st.executeQuery();
			String currentProcess = "";
			int processRank = 0;
			TreeMap<String, String> processMap = new TreeMap<String, String>(Collections.reverseOrder());
			while(rs.next()) {				
				list =  rs.getString(1).toUpperCase();
				code =  rs.getString(2).toUpperCase();
				
				if(!list.equals(currentProcess)) {
					
					if(currentProcess.trim().length() != 0 && processRank > 0) {
						String newNum = "000000".substring(0, 5-(processRank+"").length())+processRank;
						processMap.put(newNum+currentProcess, currentProcess);					
					}
					currentProcess = list;
					processRank = 0;
				}
				
				String temp[] = content.split(code);
				int codeRank = 0;
				if(temp.length > 1)
					codeRank = temp.length;
				processRank = processRank + codeRank;
			}
			st.close();			
			con.close();
			
			int i = 0;
			for (Map.Entry<String, String> entry : processMap.entrySet()) {
				output =  output+entry.getValue();
				i++;
				if(i == 1) // Change here to increase the top count
					break;
				else
					output =  output+"|";				
		    }
		}catch(Exception e) {
			e.printStackTrace();
		}
		return output;
	}
	
	public void updateFDSProcess(String fds, String process) {		
		Connection con = null;
		PreparedStatement st = null;
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");			
			con = DriverManager.getConnection("jdbc:jtds:sqlserver://inhynsyaddanap1:1433/SCS","seshu","seshu");
			st = con.prepareStatement("UPDATE FDS SET PROCESS = ? WHERE FDS = ?");				
			st.setString(1, process);
			st.setString(2, fds);
			st.executeUpdate();
			st.close();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
