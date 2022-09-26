package com.seshu.general;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.cli.CommandLine;

public class ExcelToJsonConverterConfig {

	private String sourceFile;
	private boolean parsePercentAsFloats = false;
	private boolean omitEmpty = false;
	private boolean pretty = false;
	private boolean fillColumns = false;
	private int numberOfSheets = 0;
	private int rowLimit = 0; // 0 -> no limit
	private int rowOffset = 0;
	private DateFormat formatDate = null;



	//public static ExcelToJsonConverterConfig create(CommandLine cmd) {
	public static ExcelToJsonConverterConfig create() {
		ExcelToJsonConverterConfig config = new ExcelToJsonConverterConfig();

		/*if(cmd.hasOption("s")) {
			config.sourceFile = cmd.getOptionValue("s");
		}

		if(cmd.hasOption("df")) {
			config.formatDate = new SimpleDateFormat(cmd.getOptionValue("df"));
		}
		if (cmd.hasOption("n")) {
			config.setNumberOfSheets(Integer.valueOf(cmd.getOptionValue("n")));
		}

		if (cmd.hasOption("l")) {
			config.setRowLimit(Integer.valueOf(cmd.getOptionValue("l")));
		}
		if (cmd.hasOption("o")) {
			config.setRowOffset(Integer.valueOf(cmd.getOptionValue("o")));
		}*/
		config.sourceFile = "C:\\Projects\\Seshu\\WhiteBoard\\input\\excel\\shipto-2records.xls";
		config.formatDate = new SimpleDateFormat("dd/MM/yy HH:mm");
		config.setNumberOfSheets(Integer.valueOf("1"));
		config.setRowLimit(Integer.valueOf("50000"));
		config.setRowOffset(Integer.valueOf("1"));
		config.parsePercentAsFloats = true;
		config.omitEmpty = true;
		config.pretty = true;
		config.fillColumns = true;

		
		return config;
	}
	
	public String valid() {
		if(sourceFile==null) {
			return "Source file may not be empty.";
		}
		File file = new File(sourceFile);
		if(!file.exists()) {
			return "Source file does not exist.";
		}
		if(!file.canRead()) {
			return "Source file is not readable.";
		}

		return null;
	}
	
	// GET/SET

	public int getRowLimit() {
		return rowLimit;
	}

	public void setRowLimit(int rowLimit) {
		this.rowLimit = rowLimit;
	}

	public int getRowOffset() {
		return rowOffset;
	}

	public void setRowOffset(int rowOffset) {
		this.rowOffset = rowOffset;
	}

	public int getNumberOfSheets()
	{
		return numberOfSheets;
	}
	public void setNumberOfSheets(int numberOfSheets)
	{
		this.numberOfSheets = numberOfSheets;
	}
	public String getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	public boolean isParsePercentAsFloats() {
		return parsePercentAsFloats;
	}

	public void setParsePercentAsFloats(boolean parsePercentAsFloats) {
		this.parsePercentAsFloats = parsePercentAsFloats;
	}

	public boolean isOmitEmpty() {
		return omitEmpty;
	}

	public void setOmitEmpty(boolean omitEmpty) {
		this.omitEmpty = omitEmpty;
	}

	public DateFormat getFormatDate() {
		return formatDate;
	}

	public void setFormatDate(DateFormat formatDate) {
		this.formatDate = formatDate;
	}

	public boolean isPretty() {
		return pretty;
	}

	public void setPretty(boolean pretty) {
		this.pretty = pretty;
	}

	public boolean isFillColumns() {
		return fillColumns;
	}

	public void setFillColumns(boolean fillColumns) {
		this.fillColumns = fillColumns;
	}
}