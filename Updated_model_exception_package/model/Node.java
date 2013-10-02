package model;
import java.util.ArrayList;
import java.util.List;

import exceptions.WebIDException;


public class Node {
	
	private WebID webID;
	private int height;
	private int foldID;
	private int surrogateFoldID;
	private int invSurrogateFoldID;
	private List<WebID> neighbors;
	private List<WebID> surNeighbors;
	
	private NodeState addNodeState;
	
	public Node(WebID webID, int height, int foldID, int surrogateFoldID, int invSurrogateFoldID, List<WebID> neighbors, List<WebID> surNeighbors) {
		this.webID = webID;
		this.height = height;
		this.foldID = foldID;
		this.surrogateFoldID = surrogateFoldID;
		this.invSurrogateFoldID = invSurrogateFoldID;
		this.neighbors = neighbors;
		this.surNeighbors = surNeighbors;
				
	}
	
	public Node(WebID webID) {
		this.webID = webID;
		this.height = -1;
		this.foldID = -1;
		this.surrogateFoldID = -1;
		this.invSurrogateFoldID = -1;
	}

	public WebID getWebID() {
		return webID;
	}

	public void setWebID(WebID webID) {
		this.webID = webID;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getFoldID() {
		return foldID;
	}

	public void setFoldID(int foldID) {
		this.foldID = foldID;
	}

	public int getSurrogateFoldID() {
		return surrogateFoldID;
	}

	public void setSurrogateFoldID(int surrogateFoldID) {
		this.surrogateFoldID = surrogateFoldID;
	}

	public int getInvSurrogateFoldID() {
		return invSurrogateFoldID;
	}

	public void setInvSurrogateFoldID(int invSurrogateFoldID) {
		this.invSurrogateFoldID = invSurrogateFoldID;
	}

	public List<WebID> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(List<WebID> neighbors) {
		this.neighbors = neighbors;
	}

	public List<WebID> getSurNeighbors() {
		return surNeighbors;
	}

	public void setSurNeighbors(List<WebID> surNeighbors) {
		this.surNeighbors = surNeighbors;
	}
	
	/* "State Checker" Methods 
	   --------------------------------------------------------------*/
	private List<WebID> checkSurrogateNeighbors() {
		
		ArrayList<WebID> surNeighborsList = new ArrayList<WebID>();
		WebID neighborID;
		WebID surNeighborID;
		
		for (int i = 0; i < this.webID.numberOfBits(); i++) {
			try {
				// flip one bit of the checking node's webID to get a neighborID
				neighborID = new WebID(this.webID.getValue() ^ ((int)Math.pow(2, i)));
				
				if (getNode(neighborID) == null) {
					// the parent node neighbor will never be null, so we can get the parents of the
					// other would-be neighbors by removing the highest order 1 bit
					// because of the above condition, I'm assuming every neighborIDValue that makes
					// it to this point will begin with a 1 and have the same number of bits as the
					// this.webID; therefore XOR the highest bit with the proper power of two to cancel it
					surNeighborID = new WebID(neighborID.getValue() ^ ((int)Math.pow(2, this.webID.numberOfBits())));
					surNeighborsList.add(surNeighborID);
				}
			} catch (WebIDException e) {
				e.printStackTrace();
			}
		}
			
		return surNeighborsList;
				
	}
	
	private List<WebID> checkLowerNeighbors() {
		
		ArrayList<WebID> lowerNeighborsList = new ArrayList<WebID>();
		
		for (WebID neighborID : neighbors) {
			if (getNode(neighborID).height < this.height) {
				lowerNeighborsList.add(neighborID);
			}
		}
		
		return lowerNeighborsList;
	}
	
	
	
	/* Inner Classes 
	   --------------------------------------------------------------*/
	
	/*
	 *  Keeps track of the state of the node relating to its
	 *  ability to become a parent to a child node. Based on
	 *  its state, the addNode() function will behave differently
	 */
	private static abstract class NodeState {
		
		protected abstract Node addNode();
	}
	
	/* 
	 * a node is in the "Slippery Slope" state if any of these 
	 * conditions are true: 
	 * - it has neighbors who are a lower height
	 * - it has surrogate neighbors
	 * - it has a surrogate fold
	 * 
	 * the node will change to the "Insertable" state when
	 * none of the above conditions are true
	 */
	private static class SlipperySlope extends NodeState {
		
		private static SlipperySlope instance = null;
		
		public static SlipperySlope getSingleton() {
			if (instance == null) {
				instance = new SlipperySlope();
			}
			return instance;
		}
		
		private SlipperySlope() {
			
		}

		@Override
		protected Node addNode() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	/* 
	 * a node is in the "Insertable" state if ALL of these 
	 * conditions are true: 
	 * - it has no neighbors who are a lower height
	 * - it has no surrogate neighbors
	 * - it has no surrogate fold
	 * 
	 * the node will change to the "Slippery Slope" state when
	 * any of the above conditions are false
	 */
	private static class Insertable extends NodeState {

		private static Insertable instance = null;
		
		public static Insertable getSingleton() {
			if (instance == null) {
				instance = new Insertable();
			}
			return instance;
		}
		
		private Insertable() {
			
		}
		
		@Override
		protected Node addNode() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

}
