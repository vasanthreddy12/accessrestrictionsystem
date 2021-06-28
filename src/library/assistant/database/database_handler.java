package library.assistant.database;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class database_handler {
	private static database_handler handler;
	private static final String DB_URL = "jdbc:derby:database;create=true";
	private static Connection conn= null;
	private static Statement stmt =null;
	
	public database_handler() {
		System.out.println("started executing the database_handler");
		createConnection();
		
	}
	
	void createConnection() {
		try {
			try {
				System.out.println("creating connection");
				Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn=DriverManager.getConnection(DB_URL);
                        System.out.println("connection created");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		setupTableuser1();
		
	}
	
	void setupTableuser1() {
		String TABLE_NAME = "user1";
		try {
			System.out.println("checking for table, if exits or not");

			stmt= conn.createStatement();
			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
			
			if(tables.next()) {
				System.out.println("Table "+TABLE_NAME+" already exists, ready to go");
			}else {
				System.out.println("creating a table");
				stmt.execute("create table user1(email_id Varchar(40) primary key," + 
						"person_id Varchar(13) not null," + 
						"name Varchar(45) not null," + 
						"contact_no Varchar(10) not null," + 
						"timings Integer not null," + 
						"inside Integer not null)");
				System.out.println("Table "+TABLE_NAME+" created, ready to go");
				String shutdownurl = "jdbc:derby:;shutdown=true";
				DriverManager.getConnection(shutdownurl);
			}
			
			
		} catch (SQLException e) {
			if(e.getSQLState().equals("XJ015")) {
				System.out.println("Database shutdown normally");
			}else {
				System.err.println(e.getMessage()+" ... setup Database");
			}
			
		}
		
		
	}
        
        
        
            public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        }
        catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        }
        finally {
        }
        return result;
    }

    public boolean execAction(String qu) {
        try {
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return false;
        }
        finally {
        }
    }
    
    
	
}
