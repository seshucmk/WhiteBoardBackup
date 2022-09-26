package com.seshu.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.seshu.zip.GeneralDTO;

public class ClientProperties
{
	private static Properties properties = new Properties();
	private static GeneralDTO generalDTO;
	private static String configFile = "config\\General.properties";

	public static void initialize(String strFile) throws IOException
	{
		FileInputStream xmPropsStream = new FileInputStream(strFile);
		properties.load(xmPropsStream);
	}
	
	public static void initialize() throws IOException
	{
		FileInputStream xmPropsStream = new FileInputStream(configFile);
		properties.load(xmPropsStream);
	}

	public static String getProperty(String propertyName)
	{
		String propertyValue = properties.getProperty(propertyName);
		if (propertyValue != null)
		{
			propertyValue = propertyValue.trim();
		}
		else{
			propertyValue = "";
		}
		return propertyValue;
	}
	
	public static void setProperty(String propertyName, String value)
	{
		properties.setProperty(propertyName, value);		
	}
	
	public static GeneralDTO getInstance() {
		try {
			initialize();
			generalDTO = new GeneralDTO();
			generalDTO.setBufferSize(Integer.parseInt(getProperty("bufferSize")));
			generalDTO.setFilesListPath(getProperty("filesListPath"));
			generalDTO.setOutputDir(getProperty("outputDir"));
			generalDTO.setTargetFile(getProperty("targetFile"));
			generalDTO.setSourceFile(getProperty("sourceFile"));
			generalDTO.setZipFilesInput(getProperty("zipFilesInput"));
			generalDTO.setZipFilesOutput(getProperty("zipFilesOutput"));
		}catch(Exception e) {	
			e.printStackTrace();
		}
		return generalDTO;
	}
}
