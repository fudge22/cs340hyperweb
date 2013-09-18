package database;

import java.sql.Connection;

import node.Node;

public class DBAccess {
	private static Database2 database2 = null;
	
	
	public Database2 getSingleton() {
		if(database2 == null) {
			database2 = new Database2();
		}
		return database2;
	}
	
	private Connection getConnection () {
		return null;
	}
	
	private void addNode(Node node) {
		
	}
	
	private void updateNeighbors(Node node) {
		
	}
	
	private void deleteNode(Node node) {
		
	}
	
	private void removeNeighbors(Node node) {
		
	}

}
