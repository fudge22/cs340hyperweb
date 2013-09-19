import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


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
	public Node getNode(int webID) throws DatabaseException, SQLException {	
		// instantiate local variables
		int id, height, foldID, surFoldID, invSurFoldID;
		Node node;
		String query = "SELECT * FROM Nodes WHERE webID = ?";
		PreparedStatement stat = null;
		ResultSet rs = null;
		node = null;
		
		stat = db.getConnection().prepareStatement(query);
		// set the variables in the query
		stat.setInt(1, webID);

		rs = stat.executeQuery();

		// if the result set is not empty, create a Batch to return
		if (rs.next()) {
			id = rs.getInt("webID");
    		height = rs.getInt("height");
    		foldID = rs.getInt("foldID");
    		surFoldID = rs.getInt("surFoldID");
    		invSurFoldID = rs.getInt("invSurFoldID");
    		node = new Node(id, height, foldID, surFoldID, invSurFoldID);
		}
		// clean up and close
		rs.close();
		stat.close();
		
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
		String query = "INSERT INTO Nodes (webID, height, foldID, surrgFoldID, invSurrgFoldID) VALUES (?,?,?,?,?)";
		PreparedStatement stat = null;
		
		stat =  db.getConnection().prepareStatement(query);
		// set the variables in the query
        stat.setInt(1, node.getWebID());
        stat.setInt(2, node.getHeight());
        stat.setInt(3, node.getFold().getWebID());
        stat.setInt(4, node.getSurrogateFold().getWebID());
        stat.setInt(5, node.getInvSurrogateFold().getWebID());
        
        stat.executeUpdate();
        
        stat.close();  
        
		return true;
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
		String query = "UPDATE Nodes SET height = ?, foldID = ?, surFoldID = ?, invSurFoldID = ? WHERE webID = ?";
		PreparedStatement stat = null;
		
		stat =  db.getConnection().prepareStatement(query);
		// set the variables in the query
        stat.setInt(1, node.getHeight());
        stat.setInt(2, node.getFoldID());
        stat.setInt(3, node.getSurFoldID());
        stat.setInt(4, node.getInvSurFoldID());
        
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

	
	public void addNeighbors(int webid, List<int> neighbors){
		String query = "INSERT INTO Neighbors (webID, height, neighborID) VALUES (?,?)";
		PreparedStatement stat = null;
		
		Iterator<String> iterator = neighbors.iterator();
		while (iterator.hasNext()) {
		stat =  db.getConnection().prepareStatement(query);
		// set the variables in the query
        stat.setInt(1, webid);
        stat.setInt(2, neighbors.next());
        
		
		
		
        stat.executeUpdate();
        stat.close();  
        }
		return true;
	
	
	}
	public void deleteNode(Node node) {
	
		String [] queries = new String[3];
		query[0] = "delete from Node where webID = ?";
		query[1] = "delete from Neighbors where webID = ?";
		query[2] = "delete from SurNeighbors where webID = ?";
		for (int x= 0; x<= 2; x++) {
		PreparedStatement stat = null;
		ResultSet rs = null;
		node = null;
		
		stat = db.getConnection().prepareStatement(query[x]);
		// set the variables in the query
		stat.setInt(1, webID);

		rs = stat.executeUpdate();
		}
}
}