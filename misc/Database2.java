package database;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

//import node.Node;

public class Database2 {
	private static final String Database_File = "HyPeerWeb.sqlite";
	private final String Database_URL = "jdbc:sqlite:" + Database_File;
	
	private static Database2 database = null;
	
	private File createStatements;
	private Connection connection;
	
	private Database2() {
		initialize();
	}
	
	public static Database2 getSingleton() {
		if(database == null) {
			database = new Database2();
		}
		return database;
	}
	
	public void getDatabaseConnection() {
		
	}
	
	private void initialize() {
		
		this.createStatements = new File("createStatmentes.txt").getAbsoluteFile();
		
		try 
		{
			Class.forName("org.sqlite.JDBC");
		}
		
		catch (ClassNotFoundException e1) 
		{
			e1.printStackTrace();
		}
		
		try 
		{
			this.connection = DriverManager.getConnection(this.Database_URL);
		} 
		
		catch (SQLException e) 
		{
			System.out.println("Cannot connect to the Database");
			e.printStackTrace();
			return;
		}
		createDatabase();
		return;
	}
	
	private void createDatabase() {
		Statement stmt = null;
		Scanner myScanner = null;
		
		try {
			myScanner = new Scanner(this.createStatements);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		try {
			stmt = connection.createStatement();
			stmt.setQueryTimeout(30);
			
			while(myScanner.hasNextLine()) {
				
				stmt.executeUpdate(myScanner.nextLine());
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try
			{
				if(connection != null)
				{
					connection.close();
				}
			}
			catch(SQLException e)
			{
				System.err.println(e);
			}
		}
	}
}
