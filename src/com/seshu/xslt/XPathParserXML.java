package com.seshu.xslt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.HashMap;

public class XPathParserXML {

	public static void main(String[] args) {

		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br = null;
		TreeSet<String> nodeSet = new TreeSet<String>();
		HashMap<Integer, String> endTags = new HashMap<Integer, String>();
		try {
			fstream = new FileInputStream("C:\\Projects\\Seshu\\WhiteBoard\\input\\xsl\\asn_nodes.txt");
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));
			String line = br.readLine();
			String tag = "<ProcessAdvanceShipNotice>";
			String content = tag;
			nodeSet.add(content);
			int key = 1;
			endTags.put(key, tag);
			String prevLine = "";
			do {
				if (line.contains("//")) {					
					String tags[] = line.split("//");
					String temp1 = "";
					boolean newLine = false;
					String endTag = "";
					for(int i=0; i<tags.length; i++) {	
						
						if(i==0 && !prevLine.contains(line)) {
							newLine = true;
							if(line.contains("@")) {
								endTag = tags[tags.length-1];
							}
						}
						
						if(tags[i].contains("@")) {
							//temp1 = temp1.substring(0, temp1.length()-1)+" "+tags[i].substring(1, tags[i].length())+"=\"\">";
							content = content.substring(0, content.length()-1)+" "+tags[i].substring(1, tags[i].length())+"=\"\">";
						}else {							
							temp1 = temp1+"<"+tags[i]+">";
							if(!nodeSet.contains(temp1)) {
								nodeSet.add(temp1);
								content = content+"<"+tags[i]+">";
								key++;
								endTags.put(key, tags[i]);
							}
						}
					}					
				}
				prevLine = line;
				line = br.readLine();
			} while (line != null);
			br.close();
			in.close();
			fstream.close();
			
			
			FileWriter writer = new FileWriter("C:\\Projects\\Seshu\\WhiteBoard\\input\\xsl\\asn_out20210630.xml");
			BufferedWriter out = new BufferedWriter(writer);
			out.write(content);
			out.flush();
			out.close();
			fstream.close();
			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}