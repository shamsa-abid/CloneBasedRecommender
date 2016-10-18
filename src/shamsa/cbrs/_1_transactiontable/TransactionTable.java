package shamsa.cbrs._1_transactiontable;
import java.sql.*;

import Utilities.Constants;

import java.io.*;

public class TransactionTable {
	
	public static void main (String[] args) {
        try {
        	Class.forName("org.sqlite.JDBC");
        	//the database file in which the detected clones are saved
        	
            //String url = "jdbc:sqlite:D:\\Downloads\\MethodClones\\JMC-master\\JMC\\newmethodclones.db";
            String url = Constants.DATABASE_URL;
            Connection conn = DriverManager.getConnection(url,"","");
            Statement stmt = conn.createStatement();
            ResultSet rs;
 
            //rs = stmt.executeQuery("SELECT file FROM methods WHERE id = 2");
            String query = "select file,cloneID"+" "+
            "from"+
            "(" +
            "select clones.cloneID, clones.methodID ,methods.file " +
            "from clones " +
            "left join methods " +
            "on clones.methodID = methods.id " +
            "where methods.file is not NULL " +
            "order by methods.file " +
            ") as t " +
            "order by file;";
            
            rs = stmt.executeQuery(query);
            String previousFilename = "";
            String currentFileName = "";
            String transaction ="";
            String previousCloneID = "";
            String currentCloneID = "";
            //String cloneID = "";
            
            while ( rs.next() ) 
            {
            	currentFileName = rs.getString("file");
            	currentCloneID = rs.getString("cloneID");
            	
            	if (currentFileName.equalsIgnoreCase(previousFilename))//same file
            	{
            		//The following if condition avoids the issue of clones in the same file, only one cloneID per file is reported
            		if (!currentCloneID.equalsIgnoreCase(previousCloneID)) 
            		{
	            		transaction = transaction.concat(" " + currentCloneID);
            		}           		           		
            		
            	}
            		
            	else//new file
            	{
            		if(!transaction.contentEquals(""))
            		{
            		System.out.println(transaction);
            		}
                    transaction= "";                    	
	            	      
	                transaction = transaction.concat(currentCloneID);              
               
            	}
            	previousCloneID = currentCloneID;
                previousFilename = rs.getString("file");
                
            }
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}
