package database;

import java.sql.Connection;

import node.Node;

public class HyperWebAction {
	private static Database database = null;
	
	
	public void getSingleton() {
		database = Database2.getSingleton();
		
	}
	
	/*private Connection getConnection () {
		return null;
	}*/
	
	
	public void addNode(Node node) {
		//do three inserts
		//check if exists?
		
		if (db.getDatabaseAccessor().getNode(node.getWebId()) == null){
			db.getDatabaseAccessor().addNode();
		}
		
		
	}
	public void deleteNode(Node node) {
		//check that it exists
		//notify its neighbors
		//notify it's fold
		//notify its inverse surrogate neighbors
		//notify its inverse surrogate fold
		db.getDatabaseAccessor().deleteNode();
	//notify neighbors
		
		
	}
	public void updateNode(Node node) {
		//do three inserts
		db.getDatabaseAccessor().updateNode();
		
	}
	private void updateNeighbors(Node node) {
		
	}
	
	
	private void removeNeighbors(Node node) {
		
	}
	

}
