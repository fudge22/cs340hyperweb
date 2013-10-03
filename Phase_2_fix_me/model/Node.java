package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import exceptions.WebIDException;
import exceptions.DatabaseException;
import database.Database;
import simulation.NodeInterface;


public class Node implements NodeInterface{
	
	private static HashMap<WebID, Node> nodes;
	private WebID webID;
	private int height;
	private WebID foldID;
	private WebID surrogateFoldID;
	private WebID invSurrogateFoldID;
	private List<WebID> neighbors;
	private List<WebID> surNeighbors;
	private List<WebID> invSurNeighbors;
	
	private NodeState nodeState;
	private FoldState foldState;
	
	public Node(WebID webID, int height, WebID foldID, WebID surrogateFoldID, WebID invSurrogateFoldID, 
					List<WebID> neighbors, List<WebID> surNeighbors, List<WebID> invSurNeighbors, 
					int foldStateInt, int nodeStateInt) {
		this.webID = webID;
		this.height = height;
		this.foldID = foldID;
		this.surrogateFoldID = surrogateFoldID;
		this.invSurrogateFoldID = invSurrogateFoldID;
		this.neighbors = neighbors;
		this.surNeighbors = surNeighbors;
		this.invSurNeighbors = invSurNeighbors;
		switch (foldStateInt) {
		case 0:
			this.foldState = new StableFold();
			break;
		case 1:
			this.foldState = new UnstableSF();
			break;
		case 2:
			this.foldState = new UnstableISF();
			break;
		}
		switch (nodeStateInt) {
		case 0:
			this.nodeState = new Insertable();
			break;
		case 1:
			this.nodeState = new SlipperySlope();
			break;
		}
				
	}
	
	public Node(WebID WebID, int height) {
		this.webID = WebID;
		this.height = height;
	}

	public static void initialize() {

		nodes = new HashMap<WebID, Node>();
	}
	
	public static Node getNode(WebID id) {
		return nodes.get(id);// if null, return null node
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

	public WebID getFoldID() {
		return foldID;
	}

	public void setFoldID(WebID foldID) {
		this.foldID = foldID;
	}

	public WebID getSurrogateFoldID() {
		return surrogateFoldID;
	}

	public void setSurrogateFoldID(WebID surrogateFoldID) {
		this.surrogateFoldID = surrogateFoldID;
	}

	public WebID getInvSurrogateFoldID() {
		return invSurrogateFoldID;
	}

	public void setInvSurrogateFoldID(WebID invSurrogateFoldID) {
		this.invSurrogateFoldID = invSurrogateFoldID;
	}

	public List<WebID> getNeighborList() {
		return neighbors;
	}

	public void setNeighborList(List<WebID> neighbors) {
		this.neighbors = neighbors;
	}

	public List<WebID> getSurNeighborList() {
		return surNeighbors;
	}

	public void setSurNeighborList(List<WebID> surNeighbors) {
		this.surNeighbors = surNeighbors;
	}
	
	public List<WebID> getInvSurNeighborList() {
		return invSurNeighbors;
	}

	public void setInvSurNeighborList(List<WebID> invSurNeighbors) {
		this.invSurNeighbors = invSurNeighbors;
	}
	
	public void setNodeState(NodeState nodeState) {
		this.nodeState = nodeState;
	}
	
	public FoldState getFoldState(){
		return foldState;	
	}
	
	public int getFoldStateInt() {
		return foldState.getFoldStateInt();
	}
	
	public NodeState getNodeState() {
		return nodeState;
	}
	
	public int getNodeStateInt() {
		return nodeState.getNodeStateInt();
	}
	
	public void setParent(WebID id) {
		
	}
	
	
	private Node insertChildNode() throws WebIDException {
		
		this.increaseHeight();
		
		WebID childID = this.getChildNodeID();
		
		
		Node child = new Node(childID, this.height);
		child.updateAllNeighborTypes();
		this.foldState.updateFold(this);
		//WebID childID = new WebID();
		return child;
	}
	
	private void increaseHeight() {
		this.height++;
	}

	private void broadcastNodeStateChange() {
		
	}
	
	/* Required Validator Methods 
	   --------------------------------------------------------------*/
	
	// Woodfield's interface method (lower case d)
	@Override
	public int getWebId() {

		return webID.getValue();
	}
	
	@Override
	public NodeInterface[] getSurrogateNeighbors() {

		NodeInterface[] surNeighborArray = new Node[surNeighbors.size()];
		int counter = 0;
		for (WebID x : surNeighbors) {
			surNeighborArray[counter] = getNode(x);
			counter++;
		}

		return surNeighborArray;
	}

	@Override
	public NodeInterface[] getInverseSurrogateNeighbors() {
		NodeInterface[] invSurNeighborArray = new Node[invSurNeighbors.size()];
		int counter = 0;
		for (WebID x : invSurNeighbors) {
			invSurNeighborArray[counter] = getNode(x);
			counter++;
		}

		return invSurNeighborArray;

	}

	@Override
	public NodeInterface getFold() {

		return getNode(foldID);
		// return null;// foldID;//use hash set to get
	}

	@Override
	public NodeInterface getSurrogateFold() {

		return getNode(surrogateFoldID);

	}

	@Override
	public NodeInterface getInverseSurrogateFold() {

		return getNode(invSurrogateFoldID);

	}

	// originally brian and I thought that
	// you couldn't store the parent
	// after though I have realized that the
	// parent can be stored, a node doesn't
	// know it's child
	// after all 1 01 001 0001 all are
	// represented by 1 and their parent is
	// 0
	@Override
	public NodeInterface getParent() {
		
		WebID parentNode = null;
		
		try {
			parentNode = new WebID(this.webID.getValue() ^ ((int)Math.pow(2, this.height)));
		} catch (WebIDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return getNode(parentNode);
	}

	@Override
	public int compareTo(NodeInterface node) {
		// jamie - i am not sure if this completely works not, but I am assuming
		// so

		// compare both webid

		if (webID.getValue() < node.getWebId())
			return 1;
		else if (webID.getValue() < node.getWebId())
			return -1;
		else
			return 0;
	}

	@Override
	public NodeInterface[] getNeighbors() {

		NodeInterface[] neighborArray = new Node[neighbors.size()];
		int counter = 0;
		for (WebID x : neighbors) {
			neighborArray[counter] = getNode(x);
			counter++;
		}

		return neighborArray;
	}

	public static NodeInterface[] allNodes() {

		Node[] nodeArray = new Node[nodes.size()];
		nodes.values().toArray(nodeArray); // the toArray() function fills in
											// the array nodeArray
		return nodeArray;

	}

	public static void addNode() throws WebIDException {
		Node addedNode = null;
		if (nodes.size() == 0) {

			addedNode = addToEmptyHyperWeb();// base case with no nodes in the
												// hyperweb

		} else if (nodes.size() == 1) {
			addedNode = addSecondNode();// base case with only one node in the
										// hyperweb

		} else {
				
			addToHyperWeb(); 
			
		}
		
		if (addedNode != null) {
			try {
				Database.getInstance().getDatabaseAccessor().addNode(addedNode);
			} catch (DatabaseException e) {

				e.printStackTrace();
			} catch (SQLException e) {

				e.printStackTrace();
			}

		}

		// foldState.updateNode();
	}

	
	public void addNeighbor(WebID neighborId) {

		this.neighbors.add(neighborId);
	}

	public static Node addToHyperWeb() throws WebIDException {
		Random generator =  new Random();
		int randomStart = generator.nextInt(nodes.size());
		//function to find closest node
		
		Node insertPoint = getNode(new WebID(randomStart));
		
		insertPoint.getNodeState().addToNode(insertPoint);
		
		ArrayList<WebID> neighbors = new ArrayList<WebID>();
		ArrayList<WebID> surNeighbors = new ArrayList<WebID>();
		ArrayList<WebID> invSurNeighbors = new ArrayList<WebID>();
		//get parent id and check the state
		
		

		//getNode(0).getFoldState().addNode(); Is this what we are going for
		
		nodes.put(new WebID(0), new Node(new WebID(0), 0, null, null, null, neighbors, surNeighbors,
				invSurNeighbors, 0, 0));// we inserted an empty list
													// into surNeighbors so it
													// always exists in the
													// future
		nodes.get(0).setParent(null); // it has no parent
		return nodes.get(0);
	}
	
	public static Node addToEmptyHyperWeb() throws WebIDException {
		ArrayList<WebID> neighbors = new ArrayList<WebID>();
		ArrayList<WebID> surNeighbors = new ArrayList<WebID>();
		ArrayList<WebID> invSurNeighbors = new ArrayList<WebID>();
		// right now we have been using negative ids(well -1) to represent null
		// integers. This works well as long as we don't actually insert
		// them into the hashmap. The hashmap will always return a null for a
		// key it does not have.

		nodes.put(new WebID(0), new Node(new WebID(0), 0, null, null, null, neighbors, surNeighbors,
				invSurNeighbors, 0, 0));// we inserted an empty list
													// into surNeighbors so it
													// always exists in the
													// future
		nodes.get(0).setParent(null); // it has no parent
		return nodes.get(0);
	}

	public static Node addSecondNode() throws WebIDException {
		ArrayList<WebID> neighbors = new ArrayList<WebID>();
		ArrayList<WebID> surNeighbors = new ArrayList<WebID>();
		ArrayList<WebID> invSurNeighbors = new ArrayList<WebID>();
		neighbors.add(new WebID(0));

		nodes.put(new WebID(0), new Node(new WebID(0), 0, null, null, null, neighbors, surNeighbors,
				invSurNeighbors, 0, 0));
		nodes.get(0).setHeight(1);
		nodes.get(1).setHeight(1);
		nodes.get(0).addNeighbor(new WebID(1));
		nodes.get(0).setFoldID(new WebID(1));
		nodes.get(1).setParent(new WebID(0));

		return nodes.get(1);

	}

	public static void loadHyperWeb(HashMap<Integer, Node> loadHyperWeb) throws WebIDException {
		nodes = Database.getInstance().getDatabaseAccessor().loadHyperWeb();

	}

	public void setFoldStatus(FoldState fstate) {

		foldState = fstate;
	}

	public WebID getChildNodeID() throws WebIDException {

		return new WebID((1 << height) | webID.getValue());
	}
	
	
	/* "State Checker" Methods 
	   --------------------------------------------------------------*/
	@SuppressWarnings("unused")
	private void updateAllNeighborTypes() {
		
		ArrayList<WebID> surNeighborsList = new ArrayList<WebID>();
		ArrayList<WebID> neighborsList = new ArrayList<WebID>();
		WebID neighborID;
		WebID surNeighborID;
		
		for (int i = 0; i < this.webID.numberOfBits(); i++) {
			try {
				// flip one bit of the checking node's webID to get a neighborID
				neighborID = new WebID(this.webID.getValue() ^ ((int)Math.pow(2, i)));
				
				if (getNode(neighborID) != null) {
					neighborsList.add(neighborID);
				} else {
					// the parent node neighbor will never be null, so we can get the parents of the
					// other would-be neighbors by removing the highest order 1 bit
					// because of the above condition, I'm assuming every neighborID that makes
					// it to this point will begin with a 1 and have the same number of bits as the
					// this.webID; therefore XOR the highest bit with the proper power of two to cancel it
					surNeighborID = new WebID(neighborID.getValue() ^ ((int)Math.pow(2, this.webID.numberOfBits()-1)));
					surNeighborsList.add(surNeighborID);
				}
			} catch (WebIDException e) {
				e.printStackTrace();
			}
		}
		
		// update the class variables
		neighbors = neighborsList;
		surNeighbors = surNeighborsList;
				
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
	private abstract class NodeState {
		
		public abstract void addToNode(Node insertPointNode) throws WebIDException;
		public abstract int getNodeStateInt();
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
	private class SlipperySlope extends NodeState {

		@Override
		public void addToNode(Node insertPointNode) {
			/*
			 * if SN
			 * 		insert at SN
			 * if SF
			 * 		insert at SF
			 * if LN
			 * 		insert at LN
			 * N
			 * NN
			 */
			
		}

		@Override
		public int getNodeStateInt() {
			// TODO Auto-generated method stub
			return 1;
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
	private class Insertable extends NodeState {
		
		@Override
		public void addToNode(Node insertPointNode) throws WebIDException {
			/*
			 * insert
			 * if LN
			 * 		changeState(SS)
			 * else
			 * 		broadcast stateChange(Insertable)
			 */
			insertPointNode.insertChildNode();
			if (Node.this.checkLowerNeighbors().size() > 0) {
				Node.this.setNodeState(new SlipperySlope());
				
			}
			else {
				Node.this.broadcastNodeStateChange();
			}
			
		}

		@Override
		public int getNodeStateInt() {
			// TODO Auto-generated method stub
			return 0;
		}
	}
	
	private abstract class FoldState {
		
		//Code to update the folds of a node when a child is added to this node
		public abstract void updateFold (Node node) throws WebIDException;
	
		public abstract int getFoldStateInt();
		/*
		 * Questions to ask:
		 * 
		 * do we update from one side to the other, and the only reason we have three states 
		 * 	is so we can insert from any node?
		 */
	}
	
	private class StableFold extends FoldState  {
		
		public void updateFold(Node node) {
			//Start by getting the child node
			Node child = Node.getNode(node.getWebID());// + some bitwise operations)
			
			//give the child the fold of the parent node
			child.setFoldID(node.getFoldID());
			
			//Change the fold of node's old fold to the child
			Node.getNode(node.getFoldID()).setFoldID(child.getWebID());
			
			//next set node's surrogate Fold to its old fold
			node.setSurrogateFoldID(node.getFoldID());
			
			//Change the Surrogatefold of node's old fold to node
			Node.getNode(node.getFoldID()).setInvSurrogateFoldID(node.getWebID());
			//setInverseSurrogateFold(node);
			
			//change the status of the old fold
			Node.getNode(node.getFoldID()).setFoldStatus(new UnstableISF());
			
			//change the fold status of node
			node.setFoldStatus(new UnstableSF());
			
			//Set my fold to null
			node.setFoldID(null);
			
			return;
		}

		@Override
		public int getFoldStateInt() {
			return 0;
		}
	}
	
	//Unstable Inverse Surrogate Fold
	//this naming convention beacuse the node would have a fold and an inverse surrogate fold
	private class UnstableISF extends FoldState {
		
		/*
		 * In this function, we assume that the node being updated has had a child added and that the 
		 * 	node has both a fold and an inverse surrogate fold
		 */
		public void updateFold(Node node) throws WebIDException {
			Node child = Node.getNode(node.getChildNodeID());
			
			//make the fold of the child the inverse surrogate fold of node
			child.setFoldID(node.getInvSurrogateFoldID());
			
			//tell node's inverse surrogate fold that it has a new fold
			Node.getNode(node.getInvSurrogateFoldID()).setFoldID(child.getWebID());
			
			//tell node's inverse surrogate fold to remove its surrogate fold
			Node.getNode(node.getInvSurrogateFoldID()).setSurrogateFoldID(null);
			
			//tell node's inverse surrogate fold to update its foldState
			Node.getNode(node.getInvSurrogateFoldID()).setFoldStatus(new StableFold()); 
			
			//make node forget it's inverse surrogate fold
			node.setInvSurrogateFoldID(null);
			
			//update node's state to stable
			node.setFoldStatus(new StableFold());
			
		}

		@Override
		public int getFoldStateInt() {
			return 2;
		}
	}
	
	private class UnstableSF extends FoldState {
		/*
		 * (non-Javadoc)
		 * @see node.FoldState#updateFold(node.Node)
		 * In this function, we know that the node does not have a fold but does instead have an 
		 * 	surrogate fold. We want to make the HyperWeb as effecient as possible, thus when we
		 * 	get to this node, we see that there is a hole and we can insert here as the node's
		 * 	fold 
		 * 
		 * The purpose here now is to add a fold to node instead of a surrogate fold
		 */
		public void updateFold(Node node) throws WebIDException {
			
			 //get the child of the surrogate fold of node
			 WebID childID = Node.getNode(node.getSurrogateFoldID()).getChildNodeID(); /*<- get child would be a bitwise 
			 function	that would append the 1 to the front of the binary representation of 
			 the surrogate fold*/
			  
			 //set the fold of node to the child
			 node.setFoldID(childID);
			  
			 //set the fold of the child to this node
			 Node.getNode(childID).setFoldID(node.getWebID());
			  
			 //tell the surrogate fold of node to forget the inverse surrogate fold
			 Node.getNode(node.getSurrogateFoldID()).setInvSurrogateFoldID(null);
			 
			 //change the state of node's surrogate fold
			 Node.getNode(node.getSurrogateFoldID()).setFoldStatus(new StableFold());
			  
			 //have node forget its surrogate fold
			 node.setSurrogateFoldID(null);
			 
			 //update node's status
			 node.setFoldStatus(new StableFold());
			 
			return;
		}

		@Override
		public int getFoldStateInt() {
			return 1;
		}

	}
}
