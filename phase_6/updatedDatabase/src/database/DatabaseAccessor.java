package database;

import java.rmi.ServerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
		int height = -1, foldStateInt= -1, nodeUpStateInt= -1, nodeDownStateInt= -1;
		WebID id = null, foldID = null, surFoldID = null, invSurFoldID = null, currentChildID = null;
		List<WebID> invSurNeighbors = new ArrayList<WebID>(); 
		List<WebID> neighbors = new ArrayList<WebID>(); 
		List<WebID> surNeighbors = new ArrayList<WebID>(); 
		Set<WebID> neighborDown = new HashSet<WebID>();
		Set<WebID> doubleNeighborDown = new HashSet<WebID>();
		Set<WebID> selfUp = new HashSet<WebID>();
		Set<WebID> neighborUp = new HashSet<WebID>();
		Set<WebID> doubleNeighborUp = new HashSet<WebID>();
		Set<WebID> lowerNeighbors = new HashSet<WebID>();
		Set<WebID> higherNeighbors = new HashSet<WebID>();
		Set<WebID> selfDown = new HashSet<WebID>();
		
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
			id = new WebID(rs.getInt("webID"));
	    	height = rs.getInt("height");
	    	foldID = new WebID(rs.getInt("foldID"));
	    	surFoldID = new WebID(rs.getInt("surFoldID"));
	    	invSurFoldID = new WebID(rs.getInt("invSurFoldID"));
	    	foldStateInt = rs.getInt("foldState");
	    	nodeUpStateInt = rs.getInt("upState");
	    	nodeDownStateInt=  rs.getInt("downState");
	    	currentChildID =  new WebID(rs.getInt("currentChild"));
		}catch (SQLException e){
			e.printStackTrace();
		}finally {
			if (rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stat != null){
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
		}
	    		
		
		try{
	    	query = "SELECT neighborID FROM Neighbors WHERE webID = ?";
	    	stat = null;
	    	rs = null;
	    	node = null;
	    	stat = db.getConnection().prepareStatement(query);
	    	// set the variables in the query
	    	stat.setInt(1, webID.getValue());
	    	rs = stat.executeQuery();
	    	while (rs.next()){
	    		neighbors.add(new WebID(rs.getInt("neighborID")));
	    	}
		}catch (SQLException e){
			e.printStackTrace();
		}finally {
			if (rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stat != null){
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
		}	
	    	
	    try{
	    	query = "SELECT surNeighborID FROM SurNeighbors WHERE webID = ?";
	    	stat = null;
	    	rs = null;
	    	node = null;
	    	stat = db.getConnection().prepareStatement(query);
	    	// set the variables in the query
	    	stat.setInt(1, webID.getValue());
	    	rs = stat.executeQuery();
	    	
	    	while (rs.next()){
	    		surNeighbors.add(new WebID(rs.getInt("surNeighborID")));
	    	}
	    }catch (SQLException e){
			e.printStackTrace();
		}finally {
			if (rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stat != null){
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
		}	
	    
	    
	    try{
	    	query = "SELECT invSurNeighborID FROM InvSurNeighbors WHERE webID = ?";
	    	stat = null;
	    	rs = null;
	    	node = null;
	    	stat = db.getConnection().prepareStatement(query);
	    	stat.setInt(1, webID.getValue());
	    	rs = stat.executeQuery();
	    	while (rs.next()){
	    		invSurNeighbors.add(new WebID(rs.getInt("webID")));
	    	}
	    }catch (SQLException e){
			e.printStackTrace();
		}finally {
			if (rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stat != null){
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
		}
	    
	    
	    try{
	    	query = "SELECT selfDownID FROM SelfDown WHERE webID = ?";
	    	stat = null;
	    	rs = null;
	    	node = null;
	    	stat = db.getConnection().prepareStatement(query);
	    	stat.setInt(1, webID.getValue());
	    	rs = stat.executeQuery();
	    	while (rs.next()){
	    		selfDown.add(new WebID(rs.getInt("webID")));
	    	}
	    }catch (SQLException e){
			e.printStackTrace();
		}finally {
			if (rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stat != null){
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
		}
	    
	    try{
	    	query = "SELECT doubleNeighborDownID FROM DoubleNeighborDown WHERE webID = ?";
	    	stat = null;
	    	rs = null;
	    	node = null;
	    	stat = db.getConnection().prepareStatement(query);
	    	stat.setInt(1, webID.getValue());
	    	rs = stat.executeQuery();
	    	while (rs.next()){
	    		selfDown.add(new WebID(rs.getInt("doubleNeighborDownID")));
	    	}
	    }catch (SQLException e){
			e.printStackTrace();
		}finally {
			if (rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stat != null){
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
		}
	    
	    try{
	    	query = "SELECT selfUpID FROM SelfUp WHERE webID = ?";
	    	stat = null;
	    	rs = null;
	    	node = null;
	    	stat = db.getConnection().prepareStatement(query);
	    	stat.setInt(1, webID.getValue());
	    	rs = stat.executeQuery();
	    	while (rs.next()){
	    		selfDown.add(new WebID(rs.getInt("selfUpID")));
	    	}
	    }catch (SQLException e){
			e.printStackTrace();
		}finally {
			if (rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stat != null){
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
		}
	    
	    try{
	    	query = "SELECT neighborUpID FROM NeighborUp WHERE webID = ?";
	    	stat = null;
	    	rs = null;
	    	node = null;
	    	stat = db.getConnection().prepareStatement(query);
	    	stat.setInt(1, webID.getValue());
	    	rs = stat.executeQuery();
	    	while (rs.next()){
	    		selfDown.add(new WebID(rs.getInt("neighborUpID")));
	    	}
	    }catch (SQLException e){
			e.printStackTrace();
		}finally {
			if (rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stat != null){
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
		}

	    try{
	    	query = "SELECT doubleNeighborUpID FROM DoubleNeighborUp WHERE webID = ?";
	    	stat = null;
	    	rs = null;
	    	node = null;
	    	stat = db.getConnection().prepareStatement(query);
	    	stat.setInt(1, webID.getValue());
	    	rs = stat.executeQuery();
	    	while (rs.next()){
	    		selfDown.add(new WebID(rs.getInt("doubleNeighborUpID")));
	    	}
	    }catch (SQLException e){
			e.printStackTrace();
		}finally {
			if (rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stat != null){
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
		}

	    try{
	    	query = "SELECT lowerNeighborID FROM lowerNeighbors WHERE webID = ?";
	    	stat = null;
	    	rs = null;
	    	node = null;
	    	stat = db.getConnection().prepareStatement(query);
	    	stat.setInt(1, webID.getValue());
	    	rs = stat.executeQuery();
	    	while (rs.next()){
	    		selfDown.add(new WebID(rs.getInt("lowerNeighborID")));
	    	}
	    }catch (SQLException e){
			e.printStackTrace();
		}finally {
			if (rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stat != null){
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
		}
	    try{
	    	query = "SELECT higherNeighborID FROM HigherNeighbors WHERE webID = ?";
	    	stat = null;
	    	rs = null;
	    	node = null;
	    	stat = db.getConnection().prepareStatement(query);
	    	stat.setInt(1, webID.getValue());
	    	rs = stat.executeQuery();
	    	while (rs.next()){
	    		selfDown.add(new WebID(rs.getInt("higherNeighborID")));
	    	}
	    }catch (SQLException e){
			e.printStackTrace();
		}finally {
			if (rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stat != null){
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
		}
	    
		node = new Node(id,  height, foldID, surFoldID, invSurFoldID, neighbors, surNeighbors, invSurNeighbors, 
				selfDown, neighborDown, doubleNeighborDown, selfUp, neighborUp, doubleNeighborUp, lowerNeighbors, 
				higherNeighbors, currentChildID, foldStateInt, nodeDownStateInt, nodeUpStateInt);
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
		boolean returnvalue = false;
		String query = "INSERT INTO Nodes (webID, height, foldID, surFoldID, invSurFoldID, foldState, currentChild, downState, upState) VALUES (?,?,?,?,?,?,?,?,?)";
		PreparedStatement stat = null;
		try {
			stat =  db.getConnection().prepareStatement(query);
			// set the variables in the query
			stat.setInt(1, node.getWebID().getValue());
			stat.setInt(2, node.getHeight());
			try{
				stat.setInt(3, node.getFoldID().getValue());
			}
			catch(NullPointerException npe){
				//the Fold ID is null
			}
			try{
				stat.setInt(4, node.getSurrogateFoldID().getValue());
			}
			catch(NullPointerException npe){
				//the Fold ID is null
			}
			try{
				stat.setInt(5, node.getInvSurrogateFoldID().getValue());
			}
			catch(NullPointerException npe){
				//the Fold ID is null
			}
			stat.setInt(6, node.getFoldStateInt());
			try{
				stat.setInt(7, node.getCurrentChild().getValue());
			}
			catch(NullPointerException npe){
				//the Fold ID is null
			}
			stat.setInt(8, node.getDownStateInt());
			stat.setInt(9, node.getUpStateInt());
			if(stat.executeUpdate() >= 1) {
				returnvalue = true;
			}
		}catch (SQLException e){
			e.printStackTrace();
			return false;
		}finally {
			if (stat != null){
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
		}
		
		List<WebID> surNeighborList = node.getSurNeighborList();
		for(int index = 0; index < surNeighborList.size();index++){
			query = "INSERT INTO SurNeighbors (webID, surNeighborID) VALUES (?,?)";
			stat = null;
			try {
				stat =  db.getConnection().prepareStatement(query);
				// set the variables in the query
				stat.setInt(1, node.getWebID().getValue());
				stat.setInt(2, surNeighborList.get(index).getValue());
				if(stat.executeUpdate() >= 1) {
					returnvalue = true;
				}
			}catch (SQLException e){
				e.printStackTrace();
				return false;
			}finally {
				if (stat != null){
					try {
						stat.close();
					} catch (SQLException e) {
						e.printStackTrace();
					} 
				}
			}
		}
		
		List<WebID> neighborList = node.getNeighborList();
		for(int index = 0; index < surNeighborList.size();index++){
			query = "INSERT INTO Neighbors (webID, neighborID) VALUES (?,?)";
			stat = null;
			try {
				stat =  db.getConnection().prepareStatement(query);
				// set the variables in the query
				stat.setInt(1, node.getWebID().getValue());
				stat.setInt(2, neighborList.get(index).getValue());
				if(stat.executeUpdate() >= 1) {
					returnvalue = true;
				}
			}catch (SQLException e){
				e.printStackTrace();
				return false;
			}finally {
				if (stat != null){
					try {
						stat.close();
					} catch (SQLException e) {
						e.printStackTrace();
					} 
				}
			}
		}
		
		List<WebID> invSurNeighborList = node.getInvSurNeighborList();
		for(int index = 0; index < surNeighborList.size();index++){
			query = "INSERT INTO InvSurNeighbors (webID, invSurNeighborID) VALUES (?,?)";
			stat = null;
			try {
				stat =  db.getConnection().prepareStatement(query);
				// set the variables in the query
				stat.setInt(1, node.getWebID().getValue());
				stat.setInt(2, invSurNeighborList.get(index).getValue());
				if(stat.executeUpdate() >= 1) {
					returnvalue = true;
				}
			}catch (SQLException e){
				e.printStackTrace();
				return false;
			}finally {
				if (stat != null){
					try {
						stat.close();
					} catch (SQLException e) {
						e.printStackTrace();
					} 
				}
			}
		}
		
		Set<WebID> selfDownSet = node.getSelfDownSet();
		Iterator<WebID> iter = selfDownSet.iterator();
		while(iter.hasNext()){
			query = "INSERT INTO SelfDown (webID, selfDownID) VALUES (?,?)";
			stat = null;
			try {
				stat =  db.getConnection().prepareStatement(query);
				// set the variables in the query
				stat.setInt(1, node.getWebID().getValue());
				stat.setInt(2, iter.next().getValue());
				if(stat.executeUpdate() >= 1) {
					returnvalue = true;
				}
			}catch (SQLException e){
				e.printStackTrace();
				return false;
			}finally {
				if (stat != null){
					try {
						stat.close();
					} catch (SQLException e) {
						e.printStackTrace();
					} 
				}
			}
		}
		
		Set<WebID> selfUpSet = node.getSelfUpSet();
		iter = selfUpSet.iterator();
		while(iter.hasNext()){
			query = "INSERT INTO SelfUp (webID, selfUpID) VALUES (?,?)";
			stat = null;
			try {
				stat =  db.getConnection().prepareStatement(query);
				// set the variables in the query
				stat.setInt(1, node.getWebID().getValue());
				stat.setInt(2, iter.next().getValue());
				if(stat.executeUpdate() >= 1) {
					returnvalue = true;
				}
			}catch (SQLException e){
				e.printStackTrace();
				return false;
			}finally {
				if (stat != null){
					try {
						stat.close();
					} catch (SQLException e) {
						e.printStackTrace();
					} 
				}
			}
		}
		
		Set<WebID> neighborDownSet = node.getNeighborDownSet();
		iter = neighborDownSet.iterator();
		while(iter.hasNext()){
			query = "INSERT INTO NeighborDown (webID, neighborDown) VALUES (?,?)";
			stat = null;
			try {
				stat =  db.getConnection().prepareStatement(query);
				// set the variables in the query
				stat.setInt(1, node.getWebID().getValue());
				stat.setInt(2, iter.next().getValue());
				if(stat.executeUpdate() >= 1) {
					returnvalue = true;
				}
			}catch (SQLException e){
				e.printStackTrace();
				return false;
			}finally {
				if (stat != null){
					try {
						stat.close();
					} catch (SQLException e) {
						e.printStackTrace();
					} 
				}
			}
		}
		
		Set<WebID> doubleNeighborDownSet = node.getDoubleNeighborDownSet();
		iter = doubleNeighborDownSet.iterator();
		while(iter.hasNext()){
			query = "INSERT INTO DoubleNeighborDown (webID, doubleNeighborDownID) VALUES (?,?)";
			stat = null;
			try {
				stat =  db.getConnection().prepareStatement(query);
				// set the variables in the query
				stat.setInt(1, node.getWebID().getValue());
				stat.setInt(2, iter.next().getValue());
				if(stat.executeUpdate() >= 1) {
					returnvalue = true;
				}
			}catch (SQLException e){
				e.printStackTrace();
				return false;
			}finally {
				if (stat != null){
					try {
						stat.close();
					} catch (SQLException e) {
						e.printStackTrace();
					} 
				}
			}
		}
		
		Set<WebID> neighborUpSet = node.getNeighborUpSet();
		iter = neighborUpSet.iterator();
		while(iter.hasNext()){
			query = "INSERT INTO NeighborUp (webID, neighborUpID) VALUES (?,?)";
			stat = null;
			try {
				stat =  db.getConnection().prepareStatement(query);
				// set the variables in the query
				stat.setInt(1, node.getWebID().getValue());
				stat.setInt(2, iter.next().getValue());
				if(stat.executeUpdate() >= 1) {
					returnvalue = true;
				}
			}catch (SQLException e){
				e.printStackTrace();
				return false;
			}finally {
				if (stat != null){
					try {
						stat.close();
					} catch (SQLException e) {
						e.printStackTrace();
					} 
				}
			}
		}
		
		Set<WebID> doubleNeighborUpSet = node.getDoubleNeighborUpSet();
		iter = doubleNeighborUpSet.iterator();
		while(iter.hasNext()){
			query = "INSERT INTO DoubleNeighborUp (webID, doubleNeighborUpID) VALUES (?,?)";
			stat = null;
			try {
				stat =  db.getConnection().prepareStatement(query);
				// set the variables in the query
				stat.setInt(1, node.getWebID().getValue());
				stat.setInt(2, iter.next().getValue());
				if(stat.executeUpdate() >= 1) {
					returnvalue = true;
				}
			}catch (SQLException e){
				e.printStackTrace();
				return false;
			}finally {
				if (stat != null){
					try {
						stat.close();
					} catch (SQLException e) {
						e.printStackTrace();
					} 
				}
			}
		}
		
		Set<WebID> lowerNeighborsSet = node.getLowerNeighborsSet();
		iter = lowerNeighborsSet.iterator();
		while(iter.hasNext()){
			query = "INSERT INTO LowerNeighbors (webID, lowerNeighborID) VALUES (?,?)";
			stat = null;
			try {
				stat =  db.getConnection().prepareStatement(query);
				// set the variables in the query
				stat.setInt(1, node.getWebID().getValue());
				stat.setInt(2, iter.next().getValue());
				if(stat.executeUpdate() >= 1) {
					returnvalue = true;
				}
			}catch (SQLException e){
				e.printStackTrace();
				return false;
			}finally {
				if (stat != null){
					try {
						stat.close();
					} catch (SQLException e) {
						e.printStackTrace();
					} 
				}
			}
		}
		
		Set<WebID> higherNeighborsSet = node.getHigherNeighborsSet();
		iter = higherNeighborsSet.iterator();
		while(iter.hasNext()){
			query = "INSERT INTO HigherNeighbors (webID, higherNeighborID) VALUES (?,?)";
			stat = null;
			try {
				stat =  db.getConnection().prepareStatement(query);
				// set the variables in the query
				stat.setInt(1, node.getWebID().getValue());
				stat.setInt(2, iter.next().getValue());
				if(stat.executeUpdate() >= 1) {
					returnvalue = true;
				}
			}catch (SQLException e){
				e.printStackTrace();
				return false;
			}finally {
				if (stat != null){
					try {
						stat.close();
					} catch (SQLException e) {
						e.printStackTrace();
					} 
				}
			}
		}
		
		return returnvalue;
	}
	public void clearHyperWeb(){
		
		String [] queries = new String[12];
		queries[0] = "DELETE FROM Nodes";
		queries[1] = "DELETE FROM Neighbors";
		queries[2] = "DELETE FROM SurNeighbors";
		queries[3] = "DELETE FROM InvSurNeighbors";
		queries[4] = "DELETE FROM SelfDown";
		queries[5] = "DELETE FROM NeighborDown";
		queries[6] = "DELETE FROM DoubleNeighborDown";
		queries[7] = "DELETE FROM SelfUp";
		queries[8] = "DELETE FROM NeighborUp";
		queries[9] = "DELETE FROM DoubleNeighborUp";
		queries[10] = "DELETE FROM LowerNeighbors";
		queries[11] = "DELETE FROM HigherNeighbors";
		
		for (int x = 0; x < queries.length; x++) {
			PreparedStatement stat = null;
			
			try {
				stat = db.getConnection().prepareStatement(queries[x]);
				stat.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public HashMap<WebID, Node> loadHyperWeb() throws WebIDException {
		//query for all webids in hyperweb
		//select webID from Nodes;
		HashMap<WebID, Node> nodes = new HashMap<WebID, Node>();
		
		String query = "SELECT webID, height, foldID, surFoldID, invSurFoldID, foldState, currentChild, downState, upState FROM Nodes";
		PreparedStatement stat = null;
		ResultSet rs = null;
		
		try {
			
			
			stat = db.getConnection().prepareStatement(query);
			
			rs = stat.executeQuery();
			while (rs.next()){
				int tempWebID = rs.getInt("webID");
				
				Node node = new Node(new WebID(tempWebID),rs.getInt("height"));
				node.setFoldState(rs.getInt("foldState"));
				node.setUpState(rs.getInt("upState"));
				node.setDownState(rs.getInt("downState"));
				node.setFoldID(new WebID(rs.getInt("foldState")));
				node.setSurrogateFoldID(new WebID(rs.getInt("surFoldID")));
				node.setInvSurrogateFoldID(new WebID(rs.getInt("invSurFoldID")));
				node.setFoldID(new WebID(rs.getInt("foldID")));
				node.setCurrentChild(new WebID(rs.getInt("currentChild")));
				
				String secondQuery = "SELECT neighborID FROM Neighbors WHERE webID = ?";
		    	PreparedStatement secondStat = null;
				ResultSet secondRS = null;
				try{
					secondStat = db.getConnection().prepareStatement(secondQuery);
			    	// set the variables in the query
					secondStat.setInt(1, tempWebID);
					secondRS = secondStat.executeQuery();
			    	while (secondRS.next()){
			    		node.addNeighbor(new WebID(secondRS.getInt("neighborID")));
			    	}
				}catch (SQLException e){
					e.printStackTrace();
				}finally {
					if (secondRS != null){
						try {
							secondRS.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					if (secondStat != null){
						try {
							secondStat.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				
				secondQuery = "SELECT surNeighborID FROM SurNeighbors WHERE webID = ?";
		    	secondStat = null;
				secondRS = null;
				try{
					secondStat = db.getConnection().prepareStatement(secondQuery);
			    	// set the variables in the query
					secondStat.setInt(1, tempWebID);
					secondRS = secondStat.executeQuery();
			    	while (secondRS.next()){
			    		node.addSurNeighbor(new WebID(secondRS.getInt("surNeighborID")));
			    	}
				}catch (SQLException e){
					e.printStackTrace();
				}finally {
					if (secondRS != null){
						try {
							secondRS.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					if (secondStat != null){
						try {
							secondStat.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				
				secondQuery = "SELECT invSurNeighborID FROM InvSurNeighbors WHERE webID = ?";
		    	secondStat = null;
				secondRS = null;
				try{
					secondStat = db.getConnection().prepareStatement(secondQuery);
			    	// set the variables in the query
					secondStat.setInt(1, tempWebID);
					secondRS = secondStat.executeQuery();
			    	while (secondRS.next()){
			    		node.addInvSurNeighbor(new WebID(secondRS.getInt("invSurNeighborID")));
			    	}
				}catch (SQLException e){
					e.printStackTrace();
				}finally {
					if (secondRS != null){
						try {
							secondRS.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					if (secondStat != null){
						try {
							secondStat.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				
				secondQuery = "SELECT neighborDown FROM NeighborDown WHERE webID = ?";
		    	secondStat = null;
				secondRS = null;
				try{
					secondStat = db.getConnection().prepareStatement(secondQuery);
			    	// set the variables in the query
					secondStat.setInt(1, tempWebID);
					secondRS = secondStat.executeQuery();
					Set<WebID> neighborDown = new HashSet<WebID>();
					while (secondRS.next()){
						neighborDown.add(new WebID(secondRS.getInt("neighborDown")));
			    	}
			    	node.setNeighborDownSet(neighborDown);
				}catch (SQLException e){
					e.printStackTrace();
				}finally {
					if (secondRS != null){
						try {
							secondRS.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					if (secondStat != null){
						try {
							secondStat.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				
				secondQuery = "SELECT selfDownID FROM selfDown WHERE webID = ?";
		    	secondStat = null;
				secondRS = null;
				try{
					secondStat = db.getConnection().prepareStatement(secondQuery);
			    	// set the variables in the query
					secondStat.setInt(1, tempWebID);
					secondRS = secondStat.executeQuery();
					Set<WebID> selfDown = new HashSet<WebID>();
					while (secondRS.next()){
						selfDown.add(new WebID(secondRS.getInt("selfDownID")));
			    	}
			    	node.setSelfDownSet(selfDown);
				}catch (SQLException e){
					e.printStackTrace();
				}finally {
					if (secondRS != null){
						try {
							secondRS.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					if (secondStat != null){
						try {
							secondStat.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				
				secondQuery = "SELECT selfUpID FROM selfUp WHERE webID = ?";
		    	secondStat = null;
				secondRS = null;
				try{
					secondStat = db.getConnection().prepareStatement(secondQuery);
			    	// set the variables in the query
					secondStat.setInt(1, tempWebID);
					secondRS = secondStat.executeQuery();
					Set<WebID> selfUp = new HashSet<WebID>();
					while (secondRS.next()){
						selfUp.add(new WebID(secondRS.getInt("selfUpID")));
			    	}
			    	node.setSelfDownSet(selfUp);
				}catch (SQLException e){
					e.printStackTrace();
				}finally {
					if (secondRS != null){
						try {
							secondRS.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					if (secondStat != null){
						try {
							secondStat.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				
				secondQuery = "SELECT doubleNeighborDownID FROM DoubleNeighborDown WHERE webID = ?";
		    	secondStat = null;
				secondRS = null;
				try{
					secondStat = db.getConnection().prepareStatement(secondQuery);
			    	// set the variables in the query
					secondStat.setInt(1, tempWebID);
					secondRS = secondStat.executeQuery();
					Set<WebID> doubleNeighborDown = new HashSet<WebID>();
					while (secondRS.next()){
						doubleNeighborDown.add(new WebID(secondRS.getInt("doubleNeighborDownID")));
			    	}
			    	node.setDoubleNeighborDownSet(doubleNeighborDown);
				}catch (SQLException e){
					e.printStackTrace();
				}finally {
					if (secondRS != null){
						try {
							secondRS.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					if (secondStat != null){
						try {
							secondStat.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				
				secondQuery = "SELECT doubleNeighborUpID FROM DoubleNeighborUp WHERE webID = ?";
		    	secondStat = null;
				secondRS = null;
				try{
					secondStat = db.getConnection().prepareStatement(secondQuery);
			    	// set the variables in the query
					secondStat.setInt(1, tempWebID);
					secondRS = secondStat.executeQuery();
					Set<WebID> doubleNeighborUp = new HashSet<WebID>();
					while (secondRS.next()){
						doubleNeighborUp.add(new WebID(secondRS.getInt("doubleNeighborUpID")));
			    	}
			    	node.setDoubleNeighborUpSet(doubleNeighborUp);
				}catch (SQLException e){
					e.printStackTrace();
				}finally {
					if (secondRS != null){
						try {
							secondRS.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					if (secondStat != null){
						try {
							secondStat.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				
				secondQuery = "SELECT neighborUpID FROM NeighborUp WHERE webID = ?";
		    	secondStat = null;
				secondRS = null;
				try{
					secondStat = db.getConnection().prepareStatement(secondQuery);
			    	// set the variables in the query
					secondStat.setInt(1, tempWebID);
					secondRS = secondStat.executeQuery();
					Set<WebID> neighborUp = new HashSet<WebID>();
					while (secondRS.next()){
						neighborUp.add(new WebID(secondRS.getInt("neighborUpID")));
			    	}
			    	node.setNeighborUpSet(neighborUp);
				}catch (SQLException e){
					e.printStackTrace();
				}finally {
					if (secondRS != null){
						try {
							secondRS.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					if (secondStat != null){
						try {
							secondStat.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				
				secondQuery = "SELECT lowerNeighborID FROM LowerNeighbors WHERE webID = ?";
		    	secondStat = null;
				secondRS = null;
				try{
					secondStat = db.getConnection().prepareStatement(secondQuery);
			    	// set the variables in the query
					secondStat.setInt(1, tempWebID);
					secondRS = secondStat.executeQuery();
					Set<WebID> lowerNeighbors = new HashSet<WebID>();
					while (secondRS.next()){
						lowerNeighbors.add(new WebID(secondRS.getInt("lowerNeighborID")));
			    	}
			    	node.setLowerNeighborsSet(lowerNeighbors);
				}catch (SQLException e){
					e.printStackTrace();
				}finally {
					if (secondRS != null){
						try {
							secondRS.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					if (secondStat != null){
						try {
							secondStat.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				
				secondQuery = "SELECT higherNeighborID FROM HigherNeighbors WHERE webID = ?";
		    	secondStat = null;
				secondRS = null;
				try{
					secondStat = db.getConnection().prepareStatement(secondQuery);
			    	// set the variables in the query
					secondStat.setInt(1, tempWebID);
					secondRS = secondStat.executeQuery();
					Set<WebID> higherNeighbors = new HashSet<WebID>();
					while (secondRS.next()){
						higherNeighbors.add(new WebID(secondRS.getInt("higherNeighborID")));
			    	}
			    	node.setHigherNeighborsSet(higherNeighbors);
				}catch (SQLException e){
					e.printStackTrace();
				}finally {
					if (secondRS != null){
						try {
							secondRS.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					if (secondStat != null){
						try {
							secondStat.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				
				nodes.put(node.getWebID(), node);// TODO Update this statement to correctly correlate webIDs with nodes
			}
			
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}finally {
			if (rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stat != null){
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
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