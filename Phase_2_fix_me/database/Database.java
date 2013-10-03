package database;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

import exceptions.DatabaseException;

/**
 * Initializes the database and stores information about the database connection 
 * and the data access classes that can be used to manipulate the information in 
 * the database
 * @author Mychal Calderon
 */
public class Database {
	
	private static Database db = null;
	private DatabaseAccessor dbAccessor;
	private Connection connection;
	private static String dbUrl;
	
	// singleton model
	public static Database getInstance() {
		if (db == null) {
			db = new Database();
			// new instance needs to be initialized
		}
		return db;
	}
	
	// private constructor, no other class can access it
	private Database() {
		if (dbUrl != null) {
			try {
				this.connection = DriverManager.getConnection(dbUrl);
			} catch (SQLException e) {
				e.printStackTrace();
				this.connection = null;
			}
		} else {
			this.connection = null;
		}
		this.dbAccessor = new DatabaseAccessor(this);
	}
	
	/**
	 * Load the SQLite database driver
	 * @throws ServerException
	 */
	public static void initialize() throws DatabaseException {
		// Load the SQLite database driver
		try {
			// register the driver 
			String driverName = "org.sqlite.JDBC";
			Class.forName(driverName);
			File currentDir = new File("./");
			String dbName = currentDir.getAbsolutePath() + "/database/indexer_server.sqlite";
			String jdbcDef = "jdbc:sqlite";
			dbUrl = jdbcDef + ":" + dbName;
			// which will produce a legitimate Url for SqlLite JDBC :
			// jdbc:sqlite:myDatabase.db
		} catch (ClassNotFoundException e1) {
			System.out.println("Database driver class could not be found.");
			throw new DatabaseException();
		} 
	}
	
	public DatabaseAccessor getDatabaseAccessor() {
		return dbAccessor;
	}
	
	public Connection getConnection() {
		try {
			if (connection.isClosed()) {
				this.connection = DriverManager.getConnection(dbUrl);
			} 
		} catch (SQLException e) {
			e.printStackTrace();
			this.connection = null;
		}
		return this.connection;
	}
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Open a connection to the database and start a transaction
	 * @throws ServerException
	 */
	public void startTransaction() {
		
		// turn off auto commit to enable grouping transactions
		try {
			this.getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Commit or rollback the transaction and close the connection
	 * @param commit
	 */
	public void endTransaction(boolean commit) {
		// Commit or rollback the transaction
		this.connection = this.getConnection();
		if (this.connection != null) {
			if (commit == true) {
				try {
					this.connection.commit();
				} catch (SQLException e) {
					// rollback the commit if something went wrong
					try {
		                System.err.print("Transaction is being rolled back");
		                this.connection.rollback();
		            } catch(SQLException excep) {
		            	excep.printStackTrace();
		            }
				}
			} else {
				// rollback the commit if false was passed into the method
				try {
					this.connection.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			// reset auto commit to true and close the connection
			try {
				this.connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Drops all tables in the database and recreates them
	 * @param file the file which contains the sql statements for creating the database
	 */
	public void buildTables(File file) throws SQLException {
		try {
			BufferedReader reader = new BufferedReader( new FileReader (file));
		    String line = null;
		    Statement stat = this.getConnection().createStatement();

		    while( (line = reader.readLine()) != null ) {
			    stat.executeUpdate(line);
		    }
		    
		    reader.close();
		    
		 // clean up and close
	        stat.close(); 
		    
		} catch (FileNotFoundException e1) {
			System.out.println("The sql file used to generate the database tables could not be found.");
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	    
	}
	
}
