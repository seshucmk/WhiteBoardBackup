package com.seshu.xslt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.HashMap;

public class XPathParserXML1 {

	public static void main(String[] args) {

		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br = null;
		TreeSet<String> nodeSet = new TreeSet<String>();
		TreeMap<String, String> endTags = new TreeMap<String, String>();
		TreeMap<Integer, String> endTagLevels = new TreeMap<Integer, String>();
		
		try {
			fstream = new FileInputStream("C:\\Projects\\Seshu\\WhiteBoard\\input\\xsl\\asn_nodes.txt");
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));
			String line = br.readLine();
			String tag = "<ProcessAdvanceShipNotice>";
			String content = tag;
			nodeSet.add(content);			
			endTags.put("ProcessAdvanceShipNotice", "</ProcessAdvanceShipNotice>");
			String prevLine = "";
			String prevLineEndTag = "", lineEndTag = "";
			String endLine = "";
			do {
				if(prevLine.trim().length() != 0) {					
					if(prevLine.contains("@"))
						prevLine = prevLine.substring(0, prevLine.indexOf("@")-2);
					String prevTags[] = prevLine.split("//");
					prevLineEndTag = prevTags[prevTags.length-1];
				}
				if(prevLine.trim().length() != 0 && !line.contains(prevLineEndTag)) {
					//content = content + "</"+prevLineEndTag+">";
				}
				if (line.contains("//")) {					
					String tags[] = line.split("//");
					if(line.contains("@")) {
						
					}else {
						//endLine
					}
					String temp1 = "";					
					for(int i=0; i<tags.length; i++) {						
						if((i == tags.length - 1) && !line.contains("@")) {							
							lineEndTag = tags[i];			
							if(!prevLineEndTag.equalsIgnoreCase(tags[i]))
								content = content + "</"+prevLineEndTag+">";
						}
						else if(i == 0 && prevLine.trim().length() != 0 && !prevLine.contains(line)) {
							String tempLine = prevLine;
							if(prevLine.contains("@"))
								tempLine = prevLine.substring(0, prevLine.indexOf("@")-2);
							endTags.put(tempLine, "</"+lineEndTag+">");							
						}
						else if(tags[i].contains("@")) {
							//temp1 = temp1.substring(0, temp1.length()-1)+" "+tags[i].substring(1, tags[i].length())+"=\"\">";
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
				if(!line.contains("@") && !prevLine.contains(line)) {
					//content = content + "</"+prevLineEndTag+">";					
				}
				
				prevLine = line;
				line = br.readLine();
			} while (line != null);
			br.close();
			in.close();
			fstream.close();
			
			Iterator it = endTags.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        System.out.println(pair.getKey() +" | "+ pair.getValue());
		    }
			FileWriter writer = new FileWriter("C:\\Projects\\Seshu\\WhiteBoard\\input\\xsl\\asn_out12.xml");
			BufferedWriter out = new BufferedWriter(writer);
			out.write(content);
			out.flush();
			out.close();
			fstream.close();
			//System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}