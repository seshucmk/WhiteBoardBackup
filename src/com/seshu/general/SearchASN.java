package com.seshu.general;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SearchASN {
	
	public static void main(String a[])
	{
		FileHelper getData = new FileHelper();
		String searchListFile = "C:\\Projects\\Seshu\\WhiteBoard\\input\\BTA\\ASNs.txt";
		String asns[] = getData.returnAllLines(searchListFile);
		String dirPath = "C:\\Projects\\Seshu\\WhiteBoard\\input\\BTA\\asns2";
		String outDir = "C:\\Projects\\Seshu\\WhiteBoard\\output\\BTA\\found";
		String existing = "'11002958042', '11002958967', '11002958968', '11002958969', '11002958970', '11002958972', '11002958973', '11002958974', '11003029702', '11003029703', '11003029704', '11003029705', '11003029706', '11003029708', '11003029709', '11003029710', '11003029711', '11003029712', '11003029713', '11003029714', '11003029715', '11003029717', '11003029718', '21002959811', '21002959812', '21002959813', '21002959814', '21002959815', '21002959816', '21002959817', '21002959818', '21002959819', '21002959820', '21002959821', '21002959828', '31002959829', '31002959830', '31002959831', '31002959832', '31002959833', '31002959834', '31002959835', '31002959836', '31002959837', '31002959838'";
		HashMap<String, String> hm = new HashMap<String, String>();
		try{
			File dir = new File(dirPath);
			int readCount = 0;
			if(dir.isDirectory()){
				String fileNames[] = dir.list();
				int size = fileNames.length;
				for(int i = 0; i<size; i++){
					String content = getData.returnFileContent(dirPath+"\\"+fileNames[i]);
					for(int j=0; j<asns.length; j++){
						if(asns[j] != null && asns[j].trim().length() != 0 && 
								!asns[j].equals(",") && content.contains(asns[j]) 
								&& !existing.contains(asns[j])){							
							hm.put(asns[j], fileNames[i]);
							//System.out.println(asns[j]+" | "+fileNames[i]);
						}
					}
					readCount++;
				}
				Iterator it = hm.entrySet().iterator();
			    while (it.hasNext()) {
			        Map.Entry pair = (Map.Entry)it.next();
			        System.out.println(pair.getKey() +" | "+ pair.getValue());
			        getData.copyFile(dirPath+"\\"+pair.getValue(), outDir+"\\"+pair.getValue());
			        it.remove(); // avoids a ConcurrentModificationException
			    }
				System.out.println("Read "+readCount);
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
}
