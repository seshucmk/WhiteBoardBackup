package com.seshu.zip;

public class GeneralDTO {
	
	private String filesListPath;
	private String outputDir;
	private String targetFile;	
	private String sourceFile;
	private String zipFilesInput;
	private String zipFilesOutput;
	private int bufferSize;
	
	public int getBufferSize() {
		return bufferSize;
	}
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	public String getFilesListPath() {
		return filesListPath;
	}
	public void setFilesListPath(String filesListPath) {
		this.filesListPath = filesListPath;
	}
	public String getOutputDir() {
		return outputDir;
	}
	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}
	public String getTargetFile() {
		return targetFile;
	}
	public void setTargetFile(String targetFile) {
		this.targetFile = targetFile;
	}
	public String getSourceFile() {
		return sourceFile;
	}
	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}
	public String getZipFilesInput() {
		return zipFilesInput;
	}
	public void setZipFilesInput(String zipFilesInput) {
		this.zipFilesInput = zipFilesInput;
	}
	public String getZipFilesOutput() {
		return zipFilesOutput;
	}
	public void setZipFilesOutput(String zipFilesOutput) {
		this.zipFilesOutput = zipFilesOutput;
	}	
}
