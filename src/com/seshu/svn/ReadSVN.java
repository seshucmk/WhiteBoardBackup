package com.seshu.svn;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.seshu.common.ClientProperties;


public class ReadSVN {
    
	public static String username;	
	public static String password;	
	public static String svnurl;
	public static String car;
	public static long startRevision;
	public static long endRevision;
	public static String excelFile;
		
    @SuppressWarnings("rawtypes")
	public static void main(String[] args) {
    	
    	System.out.println("Started..."+new Date());	
		init();
        setupLibrary();
        SVNRepository repository = null;
        
        try {
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(svnurl));
        } catch (SVNException svne) {            
            System.err
                    .println("error while creating an SVNRepository for the location '"
                            + svnurl + "': " + svne.getMessage());
            System.exit(1);
        }
        
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);
        repository.setAuthenticationManager(authManager);
        
        try {
        	if(endRevision == -1)
        		endRevision = repository.getLatestRevision();
        	System.out.println(repository.getRepositoryPath(svnurl));
        	System.out.println(repository.getLocation().getPath());
        	System.out.println(repository.getRepositoryRoot(true));
        } catch (SVNException svne) {
            System.err.println("error while fetching the latest repository revision: " + svne.getMessage());
            System.exit(1);
        }

        Collection logEntries = null;
        try {
            logEntries = repository.log(new String[] {""}, null,
                    startRevision, endRevision, true, true);

        } catch (SVNException svne) {
            System.out.println("error while collecting log information for '"
                    + svnurl + "': " + svne.getMessage());
            System.exit(1);
        }
        ArrayList<ArrayList<String>> allEntries = new ArrayList<>();
        
        //allEntries[] = new ArrayList(); 
        //ArrayList svnEntries = new ArrayList();
        ArrayList<String> rowEntries = new ArrayList<String>();
        rowEntries.add("Revision");
        rowEntries.add("Author");
        rowEntries.add("Date");
        rowEntries.add("SVN Comment");
        rowEntries.add("Modified on the same revision");
        allEntries.add(rowEntries);
        for (Iterator entries = logEntries.iterator(); entries.hasNext();) {
        	rowEntries = new ArrayList<String>();
            SVNLogEntry logEntry = (SVNLogEntry) entries.next();
            rowEntries.add(""+logEntry.getRevision());
            rowEntries.add(logEntry.getAuthor());
            rowEntries.add(""+logEntry.getDate());
            rowEntries.add(logEntry.getMessage());
            String changedFiles = "";
            int fileCount = 0;
            if (logEntry.getChangedPaths().size() > 0) {
                
                Set changedPathsSet = logEntry.getChangedPaths().keySet();

                for (Iterator changedPaths = changedPathsSet.iterator(); changedPaths
                        .hasNext();) {
                	if(fileCount >= 1)
                		changedFiles = changedFiles+",\n";
                	String changeType = "";
                    SVNLogEntryPath entryPath = (SVNLogEntryPath) logEntry
                            .getChangedPaths().get(changedPaths.next());
                    if(entryPath.getType() == 'M')
                    	changeType = "Modified";
                    if(entryPath.getType() == 'A')
                    	changeType = "Added";
                    if(entryPath.getType() == 'D')
                    	changeType = "Deleted";
                    changedFiles = changedFiles+changeType+"|"+entryPath.getPath();
                    fileCount++;
                }
            }
            rowEntries.add(changedFiles);
            allEntries.add(rowEntries);
        }
        for (Iterator entries = allEntries.iterator(); entries.hasNext();) {        	
        	ArrayList rowEntry = (ArrayList) entries.next();
        	for (Iterator line = rowEntry.iterator(); line.hasNext();) {
        		System.out.println(line.next());
        	}
        }
        //writeToExcel(allEntries, excelFile, "Data");
        System.out.println("Finished...!");
    }

    public static void init()
	{
		try{
			ClientProperties.initialize("config\\SVN.properties");
			username = ClientProperties.getProperty("username");
			password = ClientProperties.getProperty("password");
			svnurl = ClientProperties.getProperty("svnurl");
			car = ClientProperties.getProperty("car");
			startRevision = Long.parseLong(ClientProperties.getProperty("startRevision"));
			endRevision = Long.parseLong(ClientProperties.getProperty("endRevision"));
			excelFile = ClientProperties.getProperty("excelFile");
		}catch(Exception e){
			System.out.println("Exception while reading SVN properties: ");
			e.printStackTrace();
		}
	}

    private static void setupLibrary() {
        DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
        FSRepositoryFactory.setup();
    }
    
    public static String readFileToText(String filePath) throws IOException
	{
		FileReader fileReader = new FileReader(filePath);		
		BufferedReader br = new BufferedReader(fileReader);
		StringBuffer sb = new StringBuffer();
		
		String strLine = br.readLine();
		while(strLine!=null)
		{
			sb.append(strLine);
			strLine = br.readLine();
		}
		if(br != null){
			br.close();
		}		
		return sb.toString();
	}
    
    public static void writeToExcel(ArrayList<ArrayList<String>> data, String file, String sheetName){
		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);
        
        int rowCount = 0;
         
        for (ArrayList<String> line : data) {
            Row row = sheet.createRow(++rowCount);
             
            int columnCount = 0;
             
            for (Object field : line) {
                Cell cell = row.createCell(++columnCount);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }             
        }
        
        try {
        	FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            workbook.close();
        }catch(Exception e){
        	e.printStackTrace();
        }
	}
}