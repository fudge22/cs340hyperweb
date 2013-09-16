package database;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import node.Node;

public class Database {

	// The singleton statement required by the class
	private static final Database database = new Database();
	private static final String DatabaseFile = null;
	private static final String Database_URL = "jdbc:sqlite://localhost/";
	private File createStatements;
	
	public Database() {
		
	}
	
	
	/*
	 * Add a node to the database
	 * It shouldn't have a return value as we should have done the logic
	 * of where to place the node
	 * @param node: the node to be placed into the database, containing its info of
	 * neighbor nodes and surrogate neighbors
	 */
	
	public void addNode(Node node) {
		initialize();
	}
	
	public void initialize() {
		this.createStatements = new File("createStatements.txt").getAbsoluteFile();
		Connection conn = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
		}
		
		catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("Connecting to the database");
		
		try {
			conn = DriverManager.getConnection(Database_URL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		createDatabase(conn);
	}
	
	private void createDatabase(Connection conn) {
		Statement stmt = null;
		Scanner myScanner = null;
		
		try {
			myScanner = new Scanner(this.createStatements);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		try {
			stmt = conn.createStatement();
			stmt.setQueryTimeout(30);
			
			while(myScanner.hasNextLine()) {
				stmt.executeUpdate(myScanner.nextLine());
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try
			{
				if(conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException e)
			{
				System.err.println(e);
			}
		}
	}
}
