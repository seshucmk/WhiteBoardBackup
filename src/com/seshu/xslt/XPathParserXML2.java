package com.seshu.xslt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

public class XPathParserXML2 {

	public static void main(String[] args) {

		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br = null;
		TreeSet<String> nodeSet = new TreeSet<String>();
		TreeSet<String> lineSet = new TreeSet<String>();
		//TreeMap<String, String> endTags = new TreeMap<String, String>();
		TreeMap<Integer, String> endTagLevels = new TreeMap<Integer, String>();
		
		try {
			fstream = new FileInputStream("C:\\Projects\\Seshu\\WhiteBoard\\input\\xsl\\asn_nodes.txt");
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));
			String line = br.readLine();
			lineSet.add(line);
			String tag = "<ProcessAdvanceShipNotice>";
			String content = tag;
			nodeSet.add(content);
			int key = 1;
			endTagLevels.put(key, "ProcessAdvanceShipNotice");
			do {				
				if (line.contains("//")) {
					String sortLine = line;
					if(line.contains("@"))
						sortLine = line.substring(0, line.indexOf("@")-2);
					if(!lineSet.contains(sortLine)) {
						endTagLevels.put(key++, sortLine);
						lineSet.add(sortLine);
					}
					// sort out the content
					if(endTagLevels.size() > 1) {
						for(int i = endTagLevels.size()-1; i > 1; i--) {
							if(!endTagLevels.containsKey(i))
								continue;
							String prevLine = endTagLevels.get(i);
							if(sortLine.contains(prevLine)) {
								break;
							}else {
								String tags[] = prevLine.split("//");
								content = content + "</"+tags[tags.length-1]+">";								
								lineSet.remove(endTagLevels.get(i));								
								endTagLevels.replace(i, endTagLevels.get(i), sortLine);		
								endTagLevels.remove(i+1);
								key--;
							}
						}
					}
					String tags[] = line.split("//");					
					String temp1 = "";					
					for(int i=0; i<tags.length; i++) {						
						if(tags[i].contains("@")) {
							content = content.substring(0, content.length()-1)+" "+tags[i].substring(1, tags[i].length())+"=\"\">";
						}else {							
							temp1 = temp1+"<"+tags[i]+">";
							if(!nodeSet.contains(temp1)) {
								nodeSet.add(temp1);
								content = content+"<"+tags[i]+">";								
							}
						}
					}					
				}
				line = br.readLine();
			} while (line != null);
			br.close();
			in.close();
			fstream.close();
			
			FileWriter writer = new FileWriter("C:\\Projects\\Seshu\\WhiteBoard\\input\\xsl\\asn_out12.xml");
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