package database;

import java.rmi.ServerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import simulation.*;
import model.*;
import exceptions.DatabaseException;
import exceptions.WebIDException;


/**
 * Performs all database access needed for functions related to the HyPeerWeb
 * @author Mychal Calderon
 */
public class DatabaseAccessor {

	private Database db;
	
	public DatabaseAccessor (Database db) {
		this.db = db;
	}
	
	/**
	 * Get the specified Node by webID
	 * 
	 * @param webID
	 * @return valid Node object
	 * @throws DatabaseException
	 */
	public Node getNode(WebID webID) throws DatabaseException, SQLException, WebIDException {	
		// instantiate local variables
		int height,  foldStateInt, nodeStateInt;
		WebID id, foldID, surFoldID, invSurFoldID;
		Node node;
		String query = "SELECT * FROM Nodes WHERE webID = ?";
		PreparedStatement stat = null;
		ResultSet rs = null;
		node = null;
		try {
			stat = db.getConnection().prepareStatement(query);
			// set the variables in the query
			stat.setInt(1, webID.getValue());
	
			rs = stat.executeQuery();
	
			
			if (rs.next()) {
				id = new WebID(rs.getInt("webID"));
	    		height = rs.getInt("height");
	    		foldID = new WebID(rs.getInt("foldID"));
	    		surFoldID = new WebID(rs.getInt("surFoldID"));
	    		invSurFoldID = new WebID(rs.getInt("invSurFoldID"));
	    		//foldState, insertableState, selfFlat, neighborsFlat, nofneighborsFlat
	    		foldStateInt = rs.getInt("foldState");
	    		nodeStateInt = rs.getInt("insertableState");
	    	
	    		rs.close(); //this might not work. I only want to find neighbors if the webid was found, but I also need to close the connection to make a new query
	    		//having it in this if statment seems odd though.
	    		stat.close();
	    		
	    		query = "SELECT neighborID FROM Neighbors WHERE webID = ?";
	    		stat = null;
	    		rs = null;
	    		node = null;
	    		
	    		stat = db.getConnection().prepareStatement(query);
	    		// set the variables in the query
	    		stat.setInt(1, webID.getValue());
	
	    		rs = stat.executeQuery();
	    		List<WebID> neighbors = new ArrayList<WebID>(); 
	    		while (rs.next()){
	    			
	    			neighbors.add(new WebID(rs.getInt("neighborID")));
	    		}
	    		rs.close(); //this might not work. I only want to find neighbors if the webid was found, but I also need to close the connection to make a new query
	    		//having it in this if statment seems odd though.
	    		stat.close();
	    		query = "SELECT surID FROM SurNeighbors WHERE webID = ?";
	    		stat = null;
	    		rs = null;
	    		node = null;
	    		
	    		stat = db.getConnection().prepareStatement(query);
	    		// set the variables in the query
	    		stat.setInt(1, webID.getValue());
	
	    		rs = stat.executeQuery();
	    		List<WebID> surNeighbors = new ArrayList<WebID>(); 
	    		while (rs.next()){
	    			
	    			surNeighbors.add(new WebID(rs.getInt("neighborID")));
	    		}
	    		rs.close(); 
	    		stat.close();
	    		
	    		List<WebID> invSurNeighbors = new ArrayList<WebID>(); 
	    		query = "SELECT webID FROM SurNeighbors WHERE surID = ?";
	    		stat = null;
	    		rs = null;
	    		node = null;
	    		
	    		stat = db.getConnection().prepareStatement(query);
	    		
	    		stat.setInt(1, webID.getValue());
	
	    		rs = stat.executeQuery();
	    	
	    		while (rs.next()){
	    			
	    			invSurNeighbors.add(new WebID(rs.getInt("webID")));
	    		}
	    		rs.close(); 
	    		stat.close();
	    		
	    		// TODO fix constructor of commented line below
//	    		node = new Node(id, height, foldID, surFoldID, invSurFoldID, neighbors, surNeighbors, invSurNeighbors, foldStateInt, nodeStateInt);
	    		node = null; // TODO remove this once above fixed
			
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// clean up and close

		
		
		//do new query for neighbors
		
		return node;
	}
	
	/**
	 * Add the Node to the database
	 * 
	 * @param node the node being added
	 * @return true if the node was added
	 * @throws DatabaseException
	 */
	public boolean addNode(Node node) throws DatabaseException, SQLException  {	
		// instantiate local variables
		
		//"insertableState" INTEGER, "selfFlat" INTEGER, "neighborsFlat" INTEGER, "nofneighborsFlat"
		String query = "INSERT INTO Nodes (webID, height, foldID, surFoldID, invSurFoldID, foldState, insertableState, selfFlat, neighborsFlat, nofneighborsFlat) VALUES (?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement stat = null;
		
		
		
		stat =  db.getConnection().prepareStatement(query);
		// set the variables in the query
        stat.setInt(1, node.getWebID().getValue());
        stat.setInt(2, node.getHeight());
        stat.setInt(3, node.getFoldID().getValue());
        stat.setInt(4, node.getSurrogateFoldID().getValue());
        stat.setInt(5, node.getInvSurrogateFoldID().getValue());
        stat.setInt(6, node.getFoldStateInt());//foldstate
        stat.setInt(7, node.getDownStateInt());//insertableState
        stat.executeUpdate();
        
        stat.close();  
        
		return true;
	}
	public void clearHyperWeb(){
		
		String [] queries = new String[3];
		queries[0] = "DELETE FROM Nodes";
		queries[1] = "DELETE FROM Neighbors";
		queries[2] = "DELETE FROM SurNeighbors";
		for (int x = 0; x < queries.length; x++) {
			PreparedStatement stat = null;
			
			try {
				stat = db.getConnection().prepareStatement(queries[x]);
				stat.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// set the variables in the query
			
	
		
		}
	
		
	}
	
	public HashMap<WebID, Node> loadHyperWeb() throws WebIDException { //return hashmap of integers to nodes
		//query for all webids in hyperweb
		//select webID from Nodes;
		HashMap<WebID, Node> nodes = new HashMap<WebID, Node>();
	
		try {
			String query = "select webID from Nodes";
			PreparedStatement stat = null;
			ResultSet rs = null;
			
			stat = db.getConnection().prepareStatement(query);
			
			rs = stat.executeQuery();
			
				while (rs.next()){
					nodes.put(new WebID(rs.getInt("webID")), null);
				}
			
				rs.close();
				stat.close();
			
				//Map<String, Object> map = ...;

				for (WebID i : nodes.keySet()) {
				    nodes.put(i, getNode(i));
				}
			
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		

	
		
		return nodes;
		
	}
	/**
	 * Sends the information of a Node object to the database to update
	 * 
	 * @param node Node object to update
	 * @return true if the node was updated successfully
	 * @throws DatabaseException
	 */
	public boolean updateNode(Node node) throws DatabaseException, SQLException  {
		// instantiate local variables
		//, "foldState" INTEGER, "insertableState" INTEGER, "selfFlat" INTEGER, "neighborsFlat" INTEGER, "nofneighborsFlat" INTEGER);

		String query = "UPDATE Nodes SET height = ?, foldID = ?, surFoldID = ?, invSurFoldID = ? WHERE webID = ?, foldState = ?, insertableState = ?, selfFlat = ?, neighborsFlat = ?, nofneighborsFlat = ?";
		PreparedStatement stat = null;
		
		stat =  db.getConnection().prepareStatement(query);
		// set the variables in the query
        stat.setInt(1, node.getHeight());
        stat.setInt(2, node.getFoldID().getValue());
        stat.setInt(3, node.getSurrogateFoldID().getValue());
        stat.setInt(4, node.getInvSurrogateFoldID().getValue());
        stat.setInt(5, node.getFoldStateInt());//foldstate
        stat.setInt(6, node.getDownStateInt());//insertableState
        stat.executeUpdate();
        
    	// clean up and close
        stat.close();  
        
		return true;
	}
	
	/**
	 * Query all records from the database by batchID and return them. The returned
	 * results are ordered by recordNum.
	 * @param batchID
	 * @return list of Record objects
	 * @throws ServerException
	 */
	public List<Integer> getNeighbors(int webID) throws DatabaseException, SQLException  {	
		// instantiate local variables
		int neighborWebID;
		List<Integer> allNodes = new ArrayList<Integer>();
		String query = "SELECT * FROM Neighbors WHERE webID = ?";
		PreparedStatement stat = null;
		ResultSet rs = null;

		stat = db.getConnection().prepareStatement(query);
		stat.setInt(1, webID);
		rs = stat.executeQuery();

		// iterate through the results and create a list of Node objects
		while (rs.next()) {
			neighborWebID = rs.getInt("neighborID");
			allNodes.add(neighborWebID);
		}

		// clean up and close
		rs.close();
		stat.close();
		
		if (allNodes.size() == 0) {
			allNodes = null;
		}
                
		return allNodes;
	}
	
	public boolean addNeighbors(int webid, List<Integer> neighbors) throws DatabaseException, SQLException {
		String query = "INSERT INTO Neighbors (webID, neighborID) VALUES (?,?)";
		PreparedStatement stat = null;
		
		for (int i = 0; i < neighbors.size(); i++) {
			stat =  db.getConnection().prepareStatement(query);
			// set the variables in the query
	        stat.setInt(1, webid);
	        stat.setInt(2, neighbors.get(i));
	        
	        stat.executeUpdate();
	        stat.close();  
	        }
		
		/* I couldn't get this to work? fix it later if  you want, 
		 * for loop above does the same - Mychal
		 */
		/*
		Iterator<Integer> iterator = neighbors.iterator();
		while (iterator.hasNext()) {
		stat =  db.getConnection().prepareStatement(query);
		// set the variables in the query
        stat.setInt(1, webid);
        stat.setInt(2, neighbors.next());
		
        stat.executeUpdate();
        stat.close();  
        }*/
		
		return true;
	
	
	}
	public boolean deleteNode(Node node) throws DatabaseException, SQLException {
	
		String [] queries = new String[3];
		queries[0] = "DELETE FROM Nodes WHERE webID = ?";
		queries[1] = "DELETE FROM Neighbors WHERE webID = ?";
		queries[2] = "DELETE FROM SurNeighbors WHERE webID = ?";
		for (int x = 0; x < queries.length; x++) {
			PreparedStatement stat = null;
			
			stat = db.getConnection().prepareStatement(queries[x]);
			// set the variables in the query
			stat.setInt(1, node.getWebID().getValue());
	
			stat.executeUpdate();
		}
		return true;
	}

}