package database;

import java.sql.Connection;

import node.Node;

public class HyperWebAction {
	private static Database2 database = null;
	
	
	public void getSingleton() {
		database = Database2.getSingleton();
		
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
