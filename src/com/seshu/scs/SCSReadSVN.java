package com.seshu.scs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;


public class SCSReadSVN {
    
	public static String username = "user";	
	public static String password = "pswd";	
	public static String svnurl = "svnurl";
		
    @SuppressWarnings("rawtypes")
	public static void main(String[] args) {
    	
    	System.out.println("Started...");
        setupLibrary();
        SVNRepository repository = null;      
        Collection logEntries = null;
        try {
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(svnurl));
            ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);
            repository.setAuthenticationManager(authManager);
            long endRevision = repository.getLatestRevision();
            logEntries = repository.log(new String[] {""}, null, 0, endRevision, true, true);
            
            ArrayList<String> rowEntries = new ArrayList<String>();
            rowEntries.add("Revision\tAuthor\tDate\tComments");
            for (Iterator entries = logEntries.iterator(); entries.hasNext();) {            	
                SVNLogEntry logEntry = (SVNLogEntry) entries.next();
                rowEntries.add(""+logEntry.getRevision()+"\t"+logEntry.getAuthor()+"\t"+logEntry.getDate()+"\t"+logEntry.getMessage());
            }
            for (Iterator line = rowEntries.iterator(); line.hasNext();) {
        		System.out.println(line.next());
        	}
        } catch (SVNException svne) {            
            System.out.println(svne.getMessage());
            System.exit(1);
        }
        System.out.println("Finished...!");
    }
    
    private static void setupLibrary() {
        DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
        FSRepositoryFactory.setup();
    }    
}