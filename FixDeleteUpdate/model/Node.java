package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import database.Database;
import simulation.NodeInterface;
import states.*;

public class Node implements NodeInterface {

	// debug flag
	private static boolean debug = false;
	// instance variables
	private static HashMap<WebID, Node> nodes;
	private WebID webID;
	private int height;
	private WebID foldID;
	private WebID surrogateFoldID;
	private WebID invSurrogateFoldID;
	private List<WebID> neighbors;
	private List<WebID> surNeighbors;
	private List<WebID> invSurNeighbors;
	private FoldState foldState;
	
	// state manager variables
	private DownState downState;
	private UpState upState;
	private Set<WebID> selfDown;
	private Set<WebID> neighborDown;
	private Set<WebID> doubleNeighborDown;
	private Set<WebID> selfUp;
	private Set<WebID> neighborUp;
	private Set<WebID> doubleNeighborUp;
	private Set<WebID> lowerNeighbors;
	private Set<WebID> higherNeighbors;

	private WebID currentChild;
	
	
	// constructors
	public Node(WebID webID, int height, WebID foldID, WebID surrogateFoldID,
				WebID invSurrogateFoldID, List<WebID> neighbors, List<WebID> surNeighbors, 
				List<WebID> invSurNeighbors, Set<WebID> selfDown, Set<WebID> neighborDown, 
				Set<WebID> doubleNeighborDown, Set<WebID> selfUp, Set<WebID> neighborUp, 
				Set<WebID> doubleNeighborUp, Set<WebID> lowerNeighbors, Set<WebID> higherNeighbors,
				WebID currentChild, int foldStateInt, int downStateInt, int upStateInt) {
		this.webID = webID;
		this.height = height;
		this.foldID = foldID;
		this.surrogateFoldID = surrogateFoldID;
		this.invSurrogateFoldID = invSurrogateFoldID;
		this.neighbors = neighbors;
		this.surNeighbors = surNeighbors;
		this.invSurNeighbors = invSurNeighbors;
		this.selfDown = selfDown;
		this.neighborDown = neighborDown;
		this.doubleNeighborDown = doubleNeighborDown;
		this.selfUp = selfUp;
		this.neighborUp = neighborUp;
		this.doubleNeighborUp = doubleNeighborUp;
		this.lowerNeighbors = lowerNeighbors;
		this.higherNeighbors = higherNeighbors;
		this.currentChild = currentChild;
		
		switch (foldStateInt) {
		case 0:
			this.foldState = StableFold.getSingleton();
			break;
		case 1:
			this.foldState = UnstableSF.getSingleton();
			break;
		case 2:
			this.foldState = UnstableISF.getSingleton();
			break;
		}
		
		switch (downStateInt) {
		case 0:
			this.downState = Insertable.getSingleton();
			break;
		case 1:
			this.downState = SlipperySlope.getSingleton();
			break;
		}
		
		switch (upStateInt) {
		case 0:
			this.upState = Deletable.getSingleton();
			break;
		case 1:
			this.upState = Incline.getSingleton();
			break;
		}

	}

	public Node(WebID WebID, int height) {
		this.webID = WebID;
		this.height = height;
		this.foldID = null;
		this.surrogateFoldID = null;
		this.invSurrogateFoldID = null;
		this.neighbors = new ArrayList<WebID>();
		this.surNeighbors = new ArrayList<WebID>();
		this.invSurNeighbors = new ArrayList<WebID>();
		this.selfDown = new HashSet<WebID>();
		this.neighborDown = new HashSet<WebID>();
		this.doubleNeighborDown = new HashSet<WebID>();
		this.selfUp = new HashSet<WebID>();
		this.neighborUp = new HashSet<WebID>();
		this.doubleNeighborUp = new HashSet<WebID>();
		this.lowerNeighbors = new HashSet<WebID>();
		this.higherNeighbors = new HashSet<WebID>();
		this.downState = Insertable.getSingleton();
		this.upState = Deletable.getSingleton();
		this.foldState = StableFold.getSingleton();
		this.currentChild = null;
	}

	// getters and setters
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

	public FoldState getFoldState() {
		return foldState;
	}
	
	public void setFoldState(FoldState foldState) {

		this.foldState = foldState;
	}

	public int getFoldStateInt() {
		return foldState.getFoldStateInt();
	}

	public DownState getDownState() {
		return downState;
	}
	
	public void setDownState(DownState downState) {
		this.downState = downState;
	}

	public int getDownStateInt() {
		return downState.getDownStateInt();
	}
	
	public UpState getUpState() {
		return upState;
	}
	
	public void setUpState(UpState upState) {
		this.upState = upState;
	}

	public int getUpStateInt() {
		return upState.getUpStateInt();
	}
	
	@Override
	public boolean equals(Object o) {
		Node other = (Node) o;
		return this.webID.equals(other.webID);
	}

	public static void initialize() {
		nodes = new HashMap<WebID, Node>();
	}
	
	public static HashMap<WebID, Node> getNodeMap() {
		return nodes;
	}
	
	public static Node getNode(WebID id) {
		return nodes.get(id);// if null, return null node
	}

	// loop through neighbors and let them know that you are their neighbor
	public void informNeighbors() {
		for (WebID w : neighbors) {
			getNode(w).addNeighbor(this.getWebID());
		}

	}

	public void informSurNeighbors() {
		for (WebID w : surNeighbors) {
			getNode(w).addInvSurNeighbor(this.getWebID());
		}

	}

	// finds all of the inverse surrogate neighbors and deletes their surrogate
	// neighbors
	// and then deletes the inverse surrogate neighbors
	// This is used when a child is inserted
	public void informInvSurNeighbors() {
		// copy the invSurNeighbors and loop on the copy
		// so we can remove from invSurNeighbors directly
		List<WebID> invSurNeighborList = new ArrayList<WebID>();
		invSurNeighborList.addAll(invSurNeighbors);
		
		Node invSurN, surN;
		for (WebID w : invSurNeighborList) {
			invSurN = getNode(w);
			surN = getNode(this.getWebID());
			
			invSurN.removeSurNeighbor(surN.getWebID());
			surN.removeInvSurNeighbor(invSurN.getWebID());
		}
	}

	public Node insertChildNode() {

		WebID childID = this.getChildNodeID();

		Node child = new Node(childID, this.height);
		
		nodes.put(childID, child);
		
		this.increaseHeight();
		child.increaseHeight();
		
		child.updateAllNeighborTypes();
		Node parent = this;

		// we need to inform the neighbors of their new neighbor
		// and inform surrogate neighbors of their new inverse surrogate
		// neighbor
		child.informNeighbors();
		child.informSurNeighbors();
		parent.informInvSurNeighbors();

		parent.foldState.updateFoldOf(parent, child);
		
		parent.setCurrentChild(childID);
		
		return child;
	}

	public void increaseHeight() {
		this.height++;
		this.updateNeighborHeightRelations();
	}
	
	public void decreaseHeight() {
		this.height--;
		this.updateNeighborHeightRelations();
	}

	public ArrayList<HashSet<Node>> getAll2Hops() {
		Node neighbor, nNeighbor;
		ArrayList<HashSet<Node>> results = new ArrayList<HashSet<Node>>();
		HashSet<Node> neighborList = new HashSet<Node>();
		HashSet<Node> nNeighborList = new HashSet<Node>();

		// for each neighbor
		for (WebID neighborID : this.neighbors) {
			neighbor = getNode(neighborID);
			
			neighborList.add(neighbor);
			// for each neighbors neighbor
			for (WebID nNeighborID : neighbor.neighbors) {
				if (!nNeighborID.equals(this.webID)) {
					nNeighbor = getNode(nNeighborID);
					nNeighborList.add(nNeighbor);
				}
			}
		}
		results.add(neighborList);
		results.add(nNeighborList);
		return results;
	}

	
	
	/*
	 * Required Validator Methods
	 * --------------------------------------------------------------
	 */

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
	}

	@Override
	public NodeInterface getSurrogateFold() {
		return getNode(surrogateFoldID);

	}

	@Override
	public NodeInterface getInverseSurrogateFold() {
		return getNode(invSurrogateFoldID);

	}
	
	@Override
	public NodeInterface getParent() {
		WebID parentNode = null;

//		parentNode = new WebID(this.webID.getValue() ^ ((int) Math.pow(2, this.height - 1))); incorrect
		
// Need to flip the highest one bit instead
parentNode = new WebID(this.webID.getValue() ^ Integer.highestOneBit(this.webID.getValue())); 
	
		

		return getNode(parentNode);
	}

	@Override
	public int compareTo(NodeInterface node) {
		if (webID.getValue() < node.getWebId()) {
			return 1;
		}
		else if (webID.getValue() < node.getWebId()) {
			return -1;
		}
		else {
			return 0;
		}
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

	public static void addNode() {
		if (nodes.size() == 0) {
			addToEmptyHyperWeb();// base case with no nodes in the
												// hyperweb
		} else if (nodes.size() == 1) {
			addSecondNode();// base case with only one node in the
										// hyperweb
		} else {
			addToHyperWeb();
		}

		printHyperWeb();// prints all of the nodes information in the hyperweb
	}
	
	private static void emptyHyperWeb() {
		// TODO Auto-generated method stub
		
	}
	
	private static void debugInfo(String info) {
		if (debug) {
			System.out.println(info);
		}
		System.out.println();
	}

	private static void printHyperWeb() {
		if (debug) {
			String n = "";
			for (WebID w : nodes.keySet()) {
				Node n1 = getNode(w);
				n += "WebID: " + w.getValue() + "\n";
				n += "Height: " + getNode(w).getHeight() + "\n";
	
				
				if (n1.getFoldState() != null) {
					n += "UpState: " + n1.getUpState().getClass().getName()
							+ "\n";
				} else {
					n += "UpState: null\n";
				}
				if (n1.currentChild != null){
					n += "Current Child: " + n1.currentChild.getValue() + "\n";
					}
					else{
						n += "Current Child: null\n";
					}
				
				if (n1.getFoldState() != null) {
					n += "FoldState: " + n1.getFoldState().getClass().getName()
							+ "\n";
				} else {
					n += "FoldState: null\n";
				}
	
				if (n1.getDownState() != null) {
					n += "NodeState: " + n1.getDownState().getClass().getName()
							+ "\n";
	
				} else {
					n += "NodeState: null\n";
				}
	
				if (n1.getFold() != null) {
					n += "FoldID: " + getNode(w).getFoldID().getValue() + "\n";
				} else {
					n += "FoldID: null \n";
	
				}
	
				if (n1.getSurrogateFoldID() != null) {
					n += "SurrogateFoldID: "
							+ getNode(w).getSurrogateFoldID().getValue() + "\n";
				} else {
					n += "SurrogateFoldID: null\n";
				}
	
				if (n1.getInvSurrogateFoldID() != null) {
					n += "InverseSurrogateFold: "
							+ getNode(w).getInvSurrogateFoldID().getValue() + "\n";
				} else {
					n += "InverseSurrogateFold: null \n";
				}
				n += "Neighbors:\n";
				for (WebID neighbor : getNode(w).getNeighborList()) {
					n += "Neighbor: " + neighbor.getValue() + "\n";
	
				}
				n += "Inverse Surrogate Neighbors:\n";
				for (WebID invNeighbor : getNode(w).getInvSurNeighborList()) {
					n += "Inverse Neighbor: " + invNeighbor.getValue() + "\n";
	
				}
				n += "Surrogate Neighbors:\n";
				for (WebID surNeighbor : getNode(w).getSurNeighborList()) {
					n += "Surrogate Neighbor: " + surNeighbor.getValue() + "\n";
	
				}
				n += "\n";
	
			}
			n += "\n----------------------------------------------------------------------------------------------------\n";
			System.out.println(n);
		}
	}

	public void addNeighbor(WebID neighborId) {
		this.neighbors.add(neighborId);
	}

	public static Node addToEmptyHyperWeb() {

		nodes.put(new WebID(0), new Node(new WebID(0), 0));
		
		nodes.get(new WebID(0)).setUpState(Deletable.getSingleton());
		
		return nodes.get(new WebID(0));
	}

	public static Node addSecondNode() {
		WebID firstID = new WebID(0);
		WebID secondID = new WebID(1);

		nodes.put(secondID, new Node(secondID, 0));
		
		Node first = nodes.get(firstID);
		Node second = nodes.get(secondID);
		
		first.addNeighbor(secondID);
		first.setFoldID(secondID);
		first.setHeight(1);
		
		second.addNeighbor(firstID);
		second.setFoldID(firstID);
		second.setHeight(1);
		
		first.setDownState(Insertable.getSingleton());
		second.setDownState(Insertable.getSingleton());
		
		first.setUpState(Incline.getSingleton());
		second.setUpState(Deletable.getSingleton());
		
		first.setFoldState(StableFold.getSingleton());
		second.setFoldState(StableFold.getSingleton());
		
		first.setCurrentChild(second.getWebID());

		return second;

	}
	
	public static Node addToHyperWeb() {
		Node insertPoint = getRandomNode();
		
		debugInfo("Trying to insert at " + insertPoint.getWebId());
		insertPoint.getDownState().addToNode(insertPoint);
		
		return nodes.get(0);
	}
	private static WebID getNearNode(WebID bigNumber, WebID startID){
		
		
		WebID start = startID;
		int highest = start.getNumberBitsInCommon(bigNumber);
	
		boolean reached = false;
		while (!reached ){ //maybe this is wrong
		HashSet<WebID> relations = new HashSet<WebID>();
				
		Node check = Node.getNode(start);
		if (check == null){
			System.err.println("error");
		}
		relations.size();
		check.getNeighborList();
		relations.addAll(check.getNeighborList());
		relations.addAll(check.getSurNeighborList());
		relations.addAll(check.getInvSurNeighborList());
		if (check.getFoldID() != null)
			relations.add(check.getFoldID());
		if (check.getSurrogateFoldID() != null)
			relations.add(check.getSurrogateFoldID());
		reached = true;
		for (WebID w : relations){
			int number  = w.getNumberBitsInCommon(bigNumber);
			if (number > highest){
				start = w;
				
				reached = false;
				
				highest = number;
			}
			
		}
		
		}
		return start;
	}
	
	public static Node getRandomNode() {
		Random generator = new Random();
		int randomInsertionPoint = generator.nextInt(2147483647);
		
		WebID insertPointID = null;
		
		for (WebID id : nodes.keySet()) {
			insertPointID = id;
			break;
		}
		if (insertPointID == null){
			System.out.println("error");
		}
		Node insertPoint  = Node.getNode(getNearNode(new WebID( randomInsertionPoint),insertPointID));
		// function to find closest node

		
		return insertPoint;
	}

	/**
	 * The function called by the HyperWeb that will removed a node from the hyperWeb
	 * we assume that the hyperweb is not empty when we call the getNode function, as
	 * we should not be able to remove from an empty hyperweb
	 * 
	 * there will be special cases if the highest node available to be disconnected has
	 * a webID of either one or zero
	 */
	public static void removeNode() {
		Node randomStartNode = getRandomNode();
		Node randomDeleteNode = getRandomNode();
		
		debugInfo("Starting at node " + randomStartNode.getWebId());
		
		Node disconnectPoint = randomStartNode.getUpState().findEdgeNodeFrom(randomStartNode);
		WebID disconnectID = disconnectPoint.getWebID();
		
		debugInfo("Trying to disconnect " + disconnectPoint.getWebId());
		debugInfo("Trying to delete " + randomDeleteNode.getWebId());
		
		// if the node we are trying to delete is an edge node, just delete it
		if (randomDeleteNode.getUpState().equals(Deletable.getSingleton())) {
			disconnectPoint.disconnect();
		} else {
			// disconnect and replace deleted
			disconnectPoint.disconnect();
			disconnectPoint.getConnectionsFrom(randomDeleteNode);
			nodes.put(randomDeleteNode.getWebID(), disconnectPoint);
			
		}
		nodes.remove(disconnectID);
		
		printHyperWeb();

	}
	private void getConnectionsFrom(Node n) {
		
		this.webID = n.webID;
		this.height = n.height;
		this.foldID = n.foldID;
		this.surrogateFoldID = n.surrogateFoldID;
		this.invSurrogateFoldID = n.invSurrogateFoldID;
		this.neighbors = n.neighbors;
		this.surNeighbors = n.surNeighbors;
		this.invSurNeighbors = n.invSurNeighbors;
		this.selfDown = n.selfDown;
		this.neighborDown = n.neighborDown;
		this.doubleNeighborDown = n.doubleNeighborDown;
		this.selfUp = n.selfUp;
		this.neighborUp = n.neighborUp;
		this.doubleNeighborUp = n.doubleNeighborUp;
		this.lowerNeighbors = n.lowerNeighbors;
		this.higherNeighbors = n.higherNeighbors;
		this.upState = n.upState;
		this.downState = n.downState;
		this.foldState = n.foldState;
		this.currentChild = n.currentChild;
		
	}
	
	public void disconnect() {
		WebID parentID = this.getParentNodeID();
		Node parent = getNode(parentID);
		
		parent.decreaseHeight();
		
		parent.setCurrentChild(parent.getCurrentChild());
		
		ArrayList<WebID> neighborList = new ArrayList<WebID>();
		ArrayList<WebID> surNeighborList = new ArrayList<WebID>();
		neighborList.addAll(this.neighbors);
		surNeighborList.addAll(this.surNeighbors);
		// set parent of this node as surNeighbor to my neighbors 
		for (WebID neighborID : neighborList) {
			if (!neighborID.equals(parentID)) {
				getNode(neighborID).addSurNeighbor(parentID);
				getNode(parentID).addInvSurNeighbor(neighborID);
			}
			getNode(neighborID).removeHigherNeighbor(this.getWebID());
			getNode(neighborID).removeLowerNeighbor(this.getWebID());
			getNode(neighborID).neighbors.remove(this.getWebID());
		}
		// remove all invSurNeighbors that point to this node
		for (WebID surNeighborID : surNeighborList) {
			getNode(surNeighborID).removeInvSurNeighbor(this.getWebID());
		}
		this.getFoldState().removeFoldsOf(this);
		
	}
	
	public static void loadHyperWeb(HashMap<WebID, Node> loadHyperWeb) {
		nodes = Database.getInstance().getDatabaseAccessor().loadHyperWeb();

	}

	// assume this.height < the new height of the child
	// in other words, the parent's height has NOT already been incremented
	public WebID getChildNodeID() {
		// no height means no children or no neighbors
		if (this.height == 0) {
			return null;
		}
		return new WebID(((int) Math.pow(2, this.height)) | webID.getValue());
	}

	public WebID getParentNodeID() {
		return new WebID(this.webID.getValue() ^ Integer.highestOneBit(this.webID.getValue()));
	}

	
	/*
	 * "State Checker" Methods
	 * --------------------------------------------------------------
	 */
	public void updateAllNeighborTypes() {
		WebID neighborID;
		WebID surNeighborID;

		for (int i = 0; i < this.height; i++) {
			// flip one bit of the checking node's webID to get a neighborID
			neighborID = new WebID(this.webID.getValue() ^ ((int) Math.pow(2, i)));

			if (getNode(neighborID) != null) {
				this.addNeighbor(neighborID);
			} else {
				// the parent node neighbor will never be null, so we can get the parents of the
				// other would-be neighbors by removing the highest order 1 bit.
				// because of the above condition, I'm assuming every neighborID that makes
				// it to this point will begin with a 1 and have the same number of bits as the
				// this.webID; therefore XOR the highest bit to cancel it
				Node neighbor = new Node(neighborID, this.height);
				surNeighborID = neighbor.getParentNodeID();
				if (getNode(surNeighborID) != null) {
					this.addSurNeighbor(surNeighborID);
				}
			}
		}

	}
	
	public void updateNeighborHeightRelations() {
		Node neighbor;
		for (WebID neighborID : this.getNeighborList()) {
			neighbor = getNode(neighborID);
			// after increase from add - child will only have equal neighbors and 
			// parent will only have equal or lower neighbors
			// after decrease from delete - parent will only have equal or higher neighbors
			if (this.getHeight() == neighbor.getHeight()) {
				this.removeLowerNeighbor(neighborID);
				this.removeHigherNeighbor(neighborID);
				neighbor.removeLowerNeighbor(this.getWebID());
				neighbor.removeHigherNeighbor(this.getWebID());
			}
			// only happens to parent on increase
			else if (this.getHeight() > neighbor.getHeight()) {
				this.addLowerNeighbor(neighborID);
				neighbor.addHigherNeighbor(this.getWebID());
			}
			// only happens on decrease
			else {
				this.addHigherNeighbor(neighborID);
				neighbor.addLowerNeighbor(this.getWebID());
			}
		}
	}
	
	// Down Pointer Management Methods
	
	public void addSurNeighbor(WebID id) {
		this.surNeighbors.add(id);
		this.selfDown.add(id);
		this.updateDownState();
		this.addDownStatusTwoHopsAway();
	}
	
	public void removeSurNeighbor(WebID id) {
		this.surNeighbors.remove(id);
		this.selfDown.remove(id);
		this.updateDownState();
		if (!hasDownPointers()) {
			this.removeDownStatusTwoHopsAway();
		}
	}
	
	public void addSurFold(WebID id) {
		// remove an old surrogate fold if necessary
		this.selfDown.remove(this.surrogateFoldID);
		this.surrogateFoldID = id;
		this.selfDown.add(id);
		this.updateDownState();
		this.addDownStatusTwoHopsAway();
	}
	
	public void removeSurFold(WebID id) {
		this.surrogateFoldID = null;
		this.selfDown.remove(id);
		this.updateDownState();
		if (!hasDownPointers()) {
			this.removeDownStatusTwoHopsAway();
		}
	}
	
	public void addLowerNeighbor(WebID id) {
		this.lowerNeighbors.add(id);
		this.selfDown.add(id);
		this.updateDownState();
		this.addDownStatusTwoHopsAway();
	}
	
	public void removeLowerNeighbor(WebID id) {
		this.lowerNeighbors.remove(id);
		this.selfDown.remove(id);
		this.updateDownState();
		if (!hasDownPointers()) {
			this.removeDownStatusTwoHopsAway();
		}
	}
	
	public WebID slideDown() {
		if (this.selfDown.size() > 0) {
			return this.selfDown.iterator().next();
		}
		else if (this.neighborDown.size() > 0) {
			return this.neighborDown.iterator().next();
		}
		else if (this.doubleNeighborDown.size() > 0) {
			return this.doubleNeighborDown.iterator().next();
		}
		else {
			System.err.println("No lower node found in slippery");
			return null;
		}
	}
	
	public void updateDownState() {
		if (this.knowsDownPointers()) {
			this.setDownState(SlipperySlope.getSingleton());
		} else {
			this.setDownState(Insertable.getSingleton());
		}
	}
	
	public boolean hasDownPointers() {
		return (this.selfDown.size() > 0);
	}
	
	public boolean knowsDownPointers() {
		return (this.selfDown.size() > 0 ||
				this.neighborDown.size() > 0 ||
				this.doubleNeighborDown.size() > 0);
	}
	
	public void addDownStatusTwoHopsAway() {
		ArrayList<HashSet<Node>> twoHopsAway = new ArrayList<HashSet<Node>>();
		// get ALL neighbors and neighbors neighbors
		twoHopsAway = this.getAll2Hops();
		
		HashSet<Node> neighborList = twoHopsAway.get(0);
		HashSet<Node> doubleNeighborList = twoHopsAway.get(1);
		
		// loop through neighbors
		for (Node n : neighborList) {
			// we don't want to update neighbors with lower heights than us
			if (this.getHeight() > n.getHeight()) {
				continue;
			}
			n.neighborDown.add(this.getWebID());
			n.updateDownState();
		}
		
		// loop through neighbors neighbors
		for (Node n : doubleNeighborList) {
			// we don't want to update neighbors with lower heights than us
			if (this.getHeight() > n.getHeight()) {
				continue;
			}
			n.doubleNeighborDown.add(this.getWebID());
			n.updateDownState();
		} 
		
	}
	
	public void removeDownStatusTwoHopsAway() {
		ArrayList<HashSet<Node>> twoHopsAway = new ArrayList<HashSet<Node>>();
		// get ALL neighbors and neighbors neighbors
		twoHopsAway = this.getAll2Hops();
		
		HashSet<Node> neighborList = twoHopsAway.get(0);
		HashSet<Node> doubleNeighborList = twoHopsAway.get(1);
		
		// loop through neighbors
		for (Node n : neighborList) {
			// we don't want to update neighbors with lower heights than us
			if (this.getHeight() > n.getHeight()) {
				continue;
			}
			n.neighborDown.remove(this.getWebID());
			n.updateDownState();
		}
		
		// loop through neighbors neighbors
		for (Node n : doubleNeighborList) {
			// we don't want to update neighbors with lower heights than us
			if (this.getHeight() > n.getHeight()) {
				continue;
			}
			n.doubleNeighborDown.remove(this.getWebID());
			n.updateDownState();
		} 
		
	}
	
	// Up Pointer Management Methods
	
	public void addInvSurNeighbor(WebID id) {
		this.invSurNeighbors.add(id);
		this.selfUp.add(id);
		this.updateUpState();
		this.addUpStatusTwoHopsAway();
	}
	
	public void removeInvSurNeighbor(WebID id) {
		this.invSurNeighbors.remove(id);
		this.selfUp.remove(id);
		this.updateUpState();
		if (!hasUpPointers()) {
			this.removeUpStatusTwoHopsAway();
		}
	}
	
	public void addInvSurFold(WebID id) {
		// remove an old inv surrogate fold if necessary
		this.selfUp.remove(this.invSurrogateFoldID);
		this.invSurrogateFoldID = id;
		this.selfUp.add(id);
		this.updateUpState();
		this.addUpStatusTwoHopsAway();
	}
	
	public void removeInvSurFold(WebID id) {
		this.invSurrogateFoldID = null;
		this.selfUp.remove(id);
		this.updateUpState();
		if (!hasUpPointers()) {
			this.removeUpStatusTwoHopsAway();
		}
	}
	
	public void addHigherNeighbor(WebID id) {
		this.higherNeighbors.add(id);
//		this.selfUp.add(id);
//		this.updateUpState();
//		this.addUpStatusTwoHopsAway();
	}
	
	public void removeHigherNeighbor(WebID id) {
		this.higherNeighbors.remove(id);
//		this.selfUp.remove(id);
//		this.updateUpState();
//		if (!hasUpPointers()) {
//			this.removeUpStatusTwoHopsAway();
//		}
	}
	
	public void setCurrentChild(WebID childID) {
		this.currentChild = childID;
		this.updateUpState();
		
	}
	public WebID getCurrentChild(){
		int numLeadingZero = this.height - (Integer.SIZE - Integer.numberOfLeadingZeros(this.getWebId()));
		if (numLeadingZero > 0){
			return new WebID(((int) Math.pow(2, this.height - 1 )) | webID.getValue());
		}
		return null;
	}
	public WebID slideUp() {
		if (this.currentChild != null) {
			return this.currentChild;
		}
		
		else if (this.selfUp.size() > 0) {
			return this.selfUp.iterator().next();
		}
		else if (this.neighborUp.size() > 0) {
			return this.neighborUp.iterator().next();
		}
		else if (this.doubleNeighborUp.size() > 0) {
			return this.doubleNeighborUp.iterator().next();
		}
		
		else {
			System.err.println("No higher node found in incline");
			return null;
		}
	}
	
	public void updateUpState() {
		if (this.knowsUpPointers()) {
			this.setUpState(Incline.getSingleton());
		} else {
			this.setUpState(Deletable.getSingleton());
		}
	}
	
	public boolean hasUpPointers() {
		return (this.selfUp.size() > 0);
	}
	
	public boolean knowsUpPointers() {
		return (this.selfUp.size() > 0 ||
				this.neighborUp.size() > 0 ||
				this.doubleNeighborUp.size() > 0 ||
				this.currentChild != null);
	}
	
	public void addUpStatusTwoHopsAway() {
		ArrayList<HashSet<Node>> twoHopsAway = new ArrayList<HashSet<Node>>();
		// get ALL neighbors and neighbors neighbors
		twoHopsAway = this.getAll2Hops();
		
		HashSet<Node> neighborList = twoHopsAway.get(0);
		HashSet<Node> doubleNeighborList = twoHopsAway.get(1);
		
		// loop through neighbors
		for (Node n : neighborList) {
			// we don't want to update neighbors with higher heights than us
			if (this.getHeight() < n.getHeight() || n.getParentNodeID().equals(this.getWebID())) {
				continue;
			}
			n.neighborUp.add(this.getWebID());
			n.updateUpState();
		}
		
		// loop through neighbors neighbors
		for (Node n : doubleNeighborList) {
			// we don't want to update neighbors with higher heights than us
			if (this.getHeight() < n.getHeight()) {
				continue;
			}
			n.doubleNeighborUp.add(this.getWebID());
			n.updateUpState();
		} 
		
	}
	
	public void removeUpStatusTwoHopsAway() {
		ArrayList<HashSet<Node>> twoHopsAway = new ArrayList<HashSet<Node>>();
		// get ALL neighbors and neighbors neighbors
		twoHopsAway = this.getAll2Hops();
		
		HashSet<Node> neighborList = twoHopsAway.get(0);
		HashSet<Node> doubleNeighborList = twoHopsAway.get(1);
		
		// loop through neighbors
		for (Node n : neighborList) {
			// we don't want to update neighbors with higher heights than us
			if (this.getHeight() < n.getHeight()) {
				continue;
			}
			n.neighborUp.remove(this.getWebID());
			n.updateUpState();
		}
		
		// loop through neighbors neighbors
		for (Node n : doubleNeighborList) {
			// we don't want to update neighbors with higher heights than us
			if (this.getHeight() < n.getHeight()) {
				continue;
			}
			n.doubleNeighborUp.remove(this.getWebID());
			n.updateUpState();
		} 
		
	}

	
}


	