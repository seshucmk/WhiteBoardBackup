package com.seshu.general;

public class ApiClientVO {
	String XLSFile= "";
	String procName= "";
	String XMLFile = "";
	String dbString= "";
	int noofRecPerXml;
	String host= "";
	String fromEmailId= "";
	String toEmailId= "";
	String warehouseId= "";
	String xmlFolder = "";
	String archiveFolder= "";
	String errorFolder= "";
	String instanceName= "";
	String dBServerName = "";
	String dbUserName= "";
	String dbPassword = "";
	String dbType = "";
	String sendMail = "";
	public String getInstanceName() {
		return instanceName;
	}
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	public String getdBServerName() {
		return dBServerName;
	}
	public void setdBServerName(String dBServerName) {
		this.dBServerName = dBServerName;
	}
	public String getdBUserName() {
		return dbUserName;
	}
	public void setdBUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}
	public String getdBPassword() {
		return dbPassword;
	}
	public void setdBPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	public String getArchiveFolder() {
		return archiveFolder;
	}
	public void setArchiveFolder(String archiveFolder) {
		this.archiveFolder = archiveFolder;
	}
	public String getErrorFolder() {
		return errorFolder;
	}
	public void setErrorFolder(String errorFolder) {
		this.errorFolder = errorFolder;
	}
	public String getXmlFolder() {
		return xmlFolder;
	}
	public void setXmlFolder(String xmlFolder) {
		this.xmlFolder = xmlFolder;
	}
	public String getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getXLSFile() {
		return XLSFile;
	}
	public void setXLSFile(String xLSFile) {
		XLSFile = xLSFile;
	}
	public String getProcName() {
		return procName;
	}
	public void setProcName(String procName) {
		this.procName = procName;
	}
	public String getXMLFile() {
		return XMLFile;
	}
	public void setXMLFile(String xMLFile) {
		XMLFile = xMLFile;
	}
	public String getDbString() {
		return dbString;
	}
	public void setDbString(String dbString) {
		this.dbString = dbString;
	}
	public int getNoofRecPerXml() {
		return noofRecPerXml;
	}
	public void setNoofRecPerXml(int noofRecPerXml) {
		this.noofRecPerXml = noofRecPerXml;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getFromEmailId() {
		return fromEmailId;
	}
	public void setFromEmailId(String fromEmailId) {
		this.fromEmailId = fromEmailId;
	}
	public String getToEmailId() {
		return toEmailId;
	}
	public void setToEmailId(String toEmailId) {
		this.toEmailId = toEmailId;
	}

	public String getDBType() {
		return dbType;
	}
	public void setDBType(String dbType) {
		this.dbType = dbType;
	}
	public String getSendMail() {
		return sendMail;
	}
	public void setSendMail(String sendMail) {
		this.sendMail = sendMail;
	}
}
