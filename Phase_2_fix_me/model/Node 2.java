package model;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import exceptions.WebIDException;
import exceptions.DatabaseException;
import database.Database;
import simulation.NodeInterface;

import java.util.HashMap;


import model.HyperWeb;
import model.Node;
import model.WebID;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import simulation.Validator;
import exceptions.WebIDException;

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
	
	private ArrayList<WebID> nodesWithHoles; 
	
	
	
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
		
		
		
		
		this.invSurNeighbors = new ArrayList<WebID>();
	}

	public static void initialize() {

		nodes = new HashMap<WebID, Node>();
	}
	
	public static Node getNode(WebID id) {
		return nodes.get(id);// if null, return null node
	}
	
	@Override
	public boolean equals(Object o) {
		Node other = (Node) o;
		return this.webID.equals(other.webID);
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
	//loop through neighbors and let them know that you are their neighbor
	private void informNeighbors(){
	
		
		for (WebID w : neighbors){
			
			getNode(w).addNeighbor(this.getWebID());
		}
		
	}
	private void informSurNeighbors(){
		Node thisone = this;
		for (WebID w : surNeighbors){
			Node awesome = null;
			try{
				 awesome = getNode(w);
			getNode(w).addInvSurNeighbor(this.getWebID());
			}
			catch(NullPointerException e){
				
				e.printStackTrace();
			System.out.println();
			}
		}
		
	}
	private void addInvSurNeighbor(WebID surNeighbor) {
	
	
		this.invSurNeighbors.add(surNeighbor);
	}



		
	
	private Node insertChildNode() throws WebIDException {
		
		
		//where child should go
		System.out.println("P: " + Node.this.getWebId() + " C: " + Node.this.getChildNodeID().getValue());
		WebID childID = this.getChildNodeID(); 
		
		//create child with increased height
		Node child = new Node(childID, this.height + 1);
		//put child in hashmap
		nodes.put(childID, child);
		//add neighbors and surrogate neighbors to child node
		child.updateAllNeighborTypes();
		
		
		Node parent = this;
		//parent.addNeighbor(parent.getWebID());
		//inform the child's new neighbors to add the child as a neighbor
		//child.informNeighbors();
		//
		parent.informInvSurNeighbors(child);
		child.informSurNeighbors();
		child.informNeighbors();
		//parent.getFoldState().updateFold(child,parent);
		parent.increaseHeight(); // moved his lower
		
		if (findHole() == null){
		child.broadcastNodeStateChange();
		parent.broadcastNodeStateChange();
		}
		else{
			
			child.setNodeState(new SlipperySlope());
			parent.setNodeState(new SlipperySlope());
		}
		
		return child;
	}
	
	//finds all of the inverse surrogate neighbors and deletes their surrogate neighbors
	//and then deletes the inverse surrogate neighbors
	//This is used when a child is inserted
	private void informInvSurNeighbors(Node child) {
		
		for (WebID w : invSurNeighbors){
			//getNode(w).addNeighbor(child.getWebID());
			
			getNode(w).surNeighbors.remove(this.getWebID());
			
		}
		invSurNeighbors.clear();
	}

	private void increaseHeight() {
		this.height++;
	}

	/*
	 *  There is a condition that exists when a node is inserted into a hyperweb
	 *  and that node fills a hole, meaning that all of the nodes neighbors already
	 *  exist, in which the nodes neighbors and the nodes neighbors neighbors will
	 *  be in a slippery slope state prior to the insert. Once the node is inserted
	 *  and fills that hole, that node, its neighbors, and its neighbors neighbors
	 *  will then all need to be in the insertable state.
	 */
	private void broadcastNodeStateChange() {
		Node neighbor, nNeighbor;
		HashSet<Node> visited = new HashSet<Node>();
		
		this.setNodeState(new Insertable());
		// for each neighbor
		System.out.println("broadcast from: " + this.getWebId());
		for (WebID neighborID : this.neighbors) {
			neighbor = getNode(neighborID);
			visited.add(neighbor);
			// for each neighbors neighbor
			for (WebID nNeighborID : neighbor.neighbors) {
				nNeighbor = getNode(nNeighborID);
				visited.add(nNeighbor);
			}
		}
		
		for (Node n : visited) {
			System.out.println("visit: " + n.getWebID().getValue());
			n.setNodeState(new Insertable());
		}
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
			parentNode = new WebID(this.webID.getValue() ^ ((int)Math.pow(2, this.height-1)));
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
//			try {
//				Database.getInstance().getDatabaseAccessor().addNode(addedNode);
//			} catch (DatabaseException e) {
//
//				e.printStackTrace();
//			} catch (SQLException e) {
//
//				e.printStackTrace();
//			}

		}

		
	printHyperWeb();//prints all of the nodes information in the hyperweb
	}

	private static void printHyperWeb(){
		String n = "";
		for (WebID w : nodes.keySet()){
			Node n1 = getNode(w);
			n += "WebID: " + w.getValue() + "\n";
			n += "Height: " + getNode(w).getHeight() + "\n";
			
			if (n1.getFoldState() != null){
				n += "FoldState: " + n1.getFoldState().getClass().getName() + "\n";
			}
			else {
				n += "FoldState: null\n";
			}
			
			if (n1.getNodeState() != null){
				n += "NodeState: " + n1.getNodeState().getClass().getName() + "\n";

				
			}else {
				n += "NodeState: null\n";
			}
			
			if (n1.getFold() != null){
				n += "FoldID: " + getNode(w).getFoldID().getValue() + "\n";
			}
			else{
				n += "FoldID: null \n";

			}
			
			if (n1.getSurrogateFoldID() != null){
			n += "SurrogateFoldID: " + getNode(w).getSurrogateFoldID().getValue() + "\n";
			}
			else{
				n += "SurrogateFoldID: null\n";
			}
			
			if (n1.getInvSurrogateFoldID() != null){
			n += "InverseSurrogateFold: " + getNode(w).getInvSurrogateFoldID().getValue() + "\n";
			}
			else{
				n += "InverseSurrogateFold: null \n";
			}
			n += "Neighbors:\n";
			for (WebID neighbor : getNode(w).getNeighborList()){
				n += "Neighbor: " + neighbor.getValue() + "\n";
				
			}
			n += "Inverse Surrogate Neighbors:\n";
			for (WebID invNeighbor : getNode(w).getInvSurNeighborList()){
				n += "Inverse Neighbor: " + invNeighbor.getValue() + "\n";
				
			}
			n += "Surrogate Neighbors:\n";
			for (WebID surNeighbor : getNode(w).getSurNeighborList()){
				n += "Surrogate Neighbor: " + surNeighbor.getValue() + "\n";
				
			}
			n += "\n";
			
			

			
		}
		n += "\n----------------------------------------------------------------------------------------------------\n";
		System.out.println(n);
	}
	
	public void addNeighbor(WebID neighborId) {

		this.neighbors.add(neighborId);
	}

	public static Node addToHyperWeb() throws WebIDException {
		Random generator =  new Random();
		int randomInsertionPoint = generator.nextInt(nodes.size()-1);
		//possibly will have to change logic later
		
	
		int count = 0;
		WebID insertPointID = new WebID(0);
		for (WebID id : nodes.keySet()) {
			if (count == randomInsertionPoint) {
				insertPointID = id;
				break;
			}
			count++;
		}
		//function to find closest node
		System.out.println("insert point: " + insertPointID.getValue());
		
		Node insertPoint = getNode(insertPointID);
		System.out.println("insert point 2 " + insertPoint.getWebId());
		insertPoint.getNodeState().addToNode(insertPoint);
		
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
		nodes.get(new WebID(0)); // it has no parent
		return nodes.get(new WebID(0));
	}

	public static Node addSecondNode() throws WebIDException {
		ArrayList<WebID> neighbors = new ArrayList<WebID>();
		ArrayList<WebID> surNeighbors = new ArrayList<WebID>();
		ArrayList<WebID> invSurNeighbors = new ArrayList<WebID>();
		WebID firstId = new WebID(0);
		WebID secondId = new WebID(1);
		
	

		nodes.put(secondId, new Node(secondId, 0, null, null, null, neighbors, surNeighbors,
				invSurNeighbors, 0, 0));
		nodes.get(secondId).addNeighbor(firstId);
		nodes.get(firstId).setHeight(1);
		nodes.get(secondId).setFoldID(firstId);
		nodes.get(secondId).setHeight(1);
		nodes.get(firstId).addNeighbor(secondId);
		nodes.get(firstId).setFoldID(secondId);
		nodes.get(secondId).setParent(firstId);
		
		
		return nodes.get(secondId);

	}

	public static void loadHyperWeb(HashMap<WebID, Node> loadHyperWeb) throws WebIDException {
		nodes = Database.getInstance().getDatabaseAccessor().loadHyperWeb();

	}

	public void setFoldStatus(FoldState fstate) {

		foldState = fstate;
	}

	public WebID getChildNodeID() throws WebIDException {

		

			return new WebID(((int)Math.pow(2, this.height-1)) | webID.getValue());
		
	}
	public WebID getParentNodeID() {
				return new WebID(this.webID.getValue() ^ Integer.highestOneBit(this.webID.getValue()));
		 	}
	
	/* "State Checker" Methods 
	   --------------------------------------------------------------*/
	private void updateAllNeighborTypes() {
		
		ArrayList<WebID> surNeighborsList = new ArrayList<WebID>();
		ArrayList<WebID> neighborsList = new ArrayList<WebID>();
		WebID neighborID;
		WebID surNeighborID;
		
		for (int i = 0; i < this.height; i++) {
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
					//surNeighborID = new WebID(neighborID.getValue() ^ ((int)Math.pow(2, this.webID.numberOfBits()-1)));
					//surNeighborID = new WebID(neighborID.getValue() ^ Integer.highestOneBit(neighborID.getValue()));
					Node neighbor = new Node(neighborID, this.height);
					surNeighborID = neighbor.getParentNodeID();
					
					/*try{
					getNode(surNeighborID).getFold();
					}
					catch (NullPointerException e){
						System.out.println("caller: " + this.getWebId());
						e.printStackTrace();
					}*/
					if (getNode(surNeighborID) != null)
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
	
	private Node findHole(){
		
		Node parent = null;
		if (surNeighbors.size() > 0){
			
			parent = getNode(surNeighbors.get(0));
			
		}
		else if (surrogateFoldID != null){
			parent = getNode(surrogateFoldID);
			
		}
		else if (checkLowerNeighbors().size() > 0){
			
			parent = getNode(checkLowerNeighbors().get(0));
		}
		return parent;
	}


	private Node checkAllNeighborTypes(){
		Node parent = null;
		for (WebID w: neighbors){
			parent = getNode(w).findHole();
			
			if (parent == null){
				
				for (WebID won: getNode(w).getNeighborList()){
					parent = getNode(won).findHole();
					
					if (parent != null){
						return parent;
					}
				}
				
			}
			else{
				return parent;
			}
			
			
		}
		return parent;
	}
	
	
	
	/* Inner Classes 
	   --------------------------------------------------------------*/
		
	/*
	 *  Keeps track of the state of the node relating to its
	 *  ability to become a parent to a child node. Based on
	 *  its state, the addNode() function will behave differently
	 */
	private abstract class NodeState {
		
		public abstract void addToNode(Node parent) throws WebIDException;
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

		@SuppressWarnings("unused")
		@Override
		public void addToNode(Node parent) throws WebIDException {
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
			
			
			//need to check neighbors neighbors as well
			System.out.println("slip slope " + Node.this.getWebId());

			Node receivingParent = parent.checkAllNeighborTypes();
		
			if (receivingParent == null){
				System.err.println("you need a valid parent for a slipperySlope");
				
			}
			else{
				
				Node child = receivingParent.insertChildNode();
				
				//child.setNodeState(new SlipperySlope());
				//receivingParent.setNodeState(new SlipperySlope());
			}
		
		}

		@Override
		public int getNodeStateInt() {
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
		public void addToNode(Node parent) throws WebIDException {
			/*
			 * insert
			 * if LN
			 * 		changeState(SS)
			 * else
			 * 		broadcast stateChange(Insertable)
			 */
			parent.insertChildNode();
			
			
				
				
				
		
			

		}

		@Override
		public int getNodeStateInt() {
			return 0;
		}
	}
	
	private abstract class FoldState {
		
		//Code to update the folds of a node when a child is added to this node
		public abstract void updateFold (Node child, Node parent) throws WebIDException;
	
		public abstract int getFoldStateInt();
		/*
		 * Questions to ask:
		 * 
		 * do we update from one side to the other, and the only reason we have three states 
		 * 	is so we can insert from any node?
		 */
	}
	
	private class StableFold extends FoldState  {
		
		public void updateFold(Node child, Node parent) throws WebIDException {

			//Start by getting the child node
//			WebID temp = Node.this.getChildNodeID();
//			WebID w1 = Node.this.getWebID();
//			Node child = Node.getNode(Node.this.getChildNodeID());// + some bitwise operations)
			
			WebID childId = Node.this.getChildNodeID();
			WebID parentId = Node.this.getWebID();
			WebID parentsFoldId = getNode(parentId).getFoldID();
			
			
			//Node child = getNode(childId);
			//Node parent = getNode(parentId);
			Node parentsFold = getNode(parentsFoldId);
			
			
			
			//give the child the fold of the parent node
			child.setFoldID(parentsFoldId);
			
			//Change the fold of node's old fold to the child
			//Node.getNode(Node.this.getFoldID()).setFoldID(child.getWebID());
			parentsFold.setFoldID(childId);
			
			//next set node's surrogate Fold to its old fold
			//Node.this.setSurrogateFoldID(Node.this.getFoldID());
			parent.setSurrogateFoldID(parentsFoldId);
			
			parentsFold.setInvSurrogateFoldID(parentId);
			//Change the Surrogatefold of node's old fold to node
			//Node.getNode(Node.this.getFoldID()).setInvSurrogateFoldID(Node.this.getWebID());
			//setInverseSurrogateFold(node);
			
			//change the status of the old fold
			//Node.getNode(Node.this.getFoldID()).setFoldStatus(new UnstableISF());
			parentsFold.setFoldStatus(new UnstableISF());
			child.setFoldStatus(new StableFold());
			
			//change the fold status of node
			
			//Node.this.setFoldStatus(new UnstableSF());
			parent.setFoldStatus(new UnstableSF());
			//Set my fold to null
			parent.setFoldID(null);
			

			return;
		}

		@Override
		public int getFoldStateInt() {
			return 0;
		}

		
		
	}
	
	//Unstable Inverse Surrogate Fold
	//this naming convention because the node would have a fold and an inverse surrogate fold
	private class UnstableISF extends FoldState {
		
		/*
		 * In this function, we assume that the node being updated has had a child added and that the 
		 * 	node has both a fold and an inverse surrogate fold
		 */
		public void updateFold(Node child, Node parent) throws WebIDException {
			
			//WebID childId = Node.this.getChildNodeID(); //this is giving the next child;
			
			
			WebID parentId = Node.this.getWebID();
			WebID parentsFoldId = getNode(parentId).getFoldID();
			
			//Node parent2 = Node.this;
			//Node child = getNode(childId);
			//Node parent = Node.this;
			//Node parentsFold = getNode(parentsFoldId);
			//
			//make the fold of the child the inverse surrogate fold of node
			
			
			child.setFoldID(parent.getInvSurrogateFoldID());//geting null in call
			//child.setFoldID(parentsFoldId);
			//parent.setFoldID(null);
			//System.out.println("childid: " + childId.getValue());
			//System.out.println( " parentid: " + parentId.getValue()); 
			//System.out.println("?: " + Node.this.
			//System.out.println(" foldid: " + parentsFoldId.getValue());
			
			//tell node's inverse surrogate fold that it has a new fold
			Node.getNode(parent.getInvSurrogateFoldID()).setFoldID(child.getWebID());
			
			
			
			//tell node's inverse surrogate fold to remove its surrogate fold
			Node.getNode(parent.getInvSurrogateFoldID()).setSurrogateFoldID(null);
			
			//tell node's inverse surrogate fold to update its foldState
			Node.getNode(parent.getInvSurrogateFoldID()).setFoldStatus(new StableFold()); 
			
			//make node forget it's inverse surrogate fold
			parent.setInvSurrogateFoldID(null);
			
			//update node's state to stable
			parent.setFoldStatus(new StableFold());
			child.setFoldStatus(new StableFold());
			
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
		public void updateFold(Node child, Node parent) throws WebIDException {
			//WebID childId = Node.this.getChildNodeID();
			
			System.out.println("should I be called? parent: " + parent.getWebId() + " child: " + child.getWebId());
			WebID parentId = Node.this.getWebID();
			WebID parentsFoldId = getNode(parentId).getFoldID();
			
			// WebID childID = Node.getNode(Node.this.getSurrogateFoldID()).getChildNodeID(); /*<- get child would be a bitwise 
//*/
			//Node child = getNode(childID);
			//Node parent = getNode(parentId);
			//Node parentsFold = getNode(parentsFoldId);
			 //get the child of the surrogate fold of node
			 //function	that would append the 1 to the front of the binary representation of 
			 //the surrogate fold*/
			  
			 //set the fold of node to the child
			 Node.this.setFoldID(child.getWebID());
			  
			 //set the fold of the child to this node
			 Node.getNode(child.getWebID()).setFoldID(Node.this.getWebID());
			  
			 //tell the surrogate fold of node to forget the inverse surrogate fold
			 Node.getNode(Node.this.getSurrogateFoldID()).setInvSurrogateFoldID(null);
			 
			 //change the state of node's surrogate fold
			 Node.getNode(Node.this.getSurrogateFoldID()).setFoldStatus(new StableFold());
			  
			 //have node forget its surrogate fold
			 Node.this.setSurrogateFoldID(null);
			 
			 //update node's status
			 Node.this.setFoldStatus(new StableFold());
			 child.setFoldStatus(new StableFold());
			 
			return;
		}

		@Override
		public int getFoldStateInt() {
			return 1;
		}

		

	}
	
	/* Unit Tests for Node Class methods
	   --------------------------------------------------------------*/
	
	public static class NodeMethodsTests {

		

			private static HyperWeb hw;
			private static Validator v;
			
			@BeforeClass
			public static void setUpBeforeClass() {
				hw = new HyperWeb();	
				v = new Validator(hw);
			}

			@AfterClass
			public static void tearDownAfterClass() {
			}

			@Before
			public void setUp() throws Exception {
			}

			@After
			public void tearDown() throws Exception {
			}
			
//			this.increaseHeight();
//			
//			WebID childID = this.getChildNodeID();
//			
//			
//			Node child = new Node(childID, this.height);
//			nodes.put(childID, child);
//			
//			child.updateAllNeighborTypes();
//			FoldState f = this.foldState;
//			//this.foldState.updateFold();
//			Node parent = (Node) child.getParent();
//			this.nodeState = parent.nodeState;
//			//WebID childID = new WebID();
//			
//			//we need to inform the neighbors of their new neighbor
//			//and inform surrogate neighbors of their new inverse surrogate neighbor
//			child.informNeighbors();
//			child.informSurNeighbors();
//			parent.informInvSurNeighbors();
//			
//			
//			return child;
			
			@Test
			public void testUpdateFold() {
				
				
			}
			
			@Test
			public void testInformNeighbors() {
				hwSetup5();
				
				Node n1 = getNode(new WebID(1));
				assertTrue(n1.neighbors.size() == 2);
				
				Node n5 = new Node(new WebID(5), 3);
				n5.neighbors.add(n1.webID);
				n5.informNeighbors();
				
				assertTrue(n1.neighbors.size() == 3);
				assertTrue(n1.neighbors.contains(n5.webID));
				
				hwSetup3();
				
				n1 = getNode(new WebID(1));
				Node n4 = getNode(new WebID(4));
				Node n7 = getNode(new WebID(7));
				
				n1.updateAllNeighborTypes();
				n4.updateAllNeighborTypes();
				n7.updateAllNeighborTypes();
				
				assertTrue(n1.neighbors.size() == 2);
				assertTrue(n4.neighbors.size() == 2);
				assertTrue(n7.neighbors.size() == 2);
				
				n5 = new Node(new WebID(5), 3);
				n5.neighbors.add(n1.webID);
				n5.neighbors.add(n4.webID);
				n5.neighbors.add(n7.webID);
				n5.informNeighbors();
				
				assertTrue(n1.neighbors.size() == 3);
				assertTrue(n1.neighbors.contains(n5.webID));
				assertTrue(n4.neighbors.size() == 3);
				assertTrue(n4.neighbors.contains(n5.webID));
				assertTrue(n7.neighbors.size() == 3);
				assertTrue(n7.neighbors.contains(n5.webID));
			}
			
			@Test
			public void testInformSurNeighbors() {
				hwSetup5();
				
				Node n0 = getNode(new WebID(0));
				Node n3 = getNode(new WebID(3));
				
				assertTrue(n0.invSurNeighbors.size() == 0);
				assertTrue(n3.invSurNeighbors.size() == 0);
				
				Node n5 = new Node(new WebID(5), 3);
				n5.surNeighbors.add(n0.webID);
				n5.surNeighbors.add(n3.webID);
				n5.informSurNeighbors();
				
				assertTrue(n0.invSurNeighbors.size() == 1);
				assertTrue(n0.invSurNeighbors.contains(n5.webID));
				assertTrue(n3.invSurNeighbors.size() == 1);
				assertTrue(n3.invSurNeighbors.contains(n5.webID));
				
				hwSetup3();
				
				n0 = getNode(new WebID(0));
				n3 = getNode(new WebID(3));
				
				n0.updateAllNeighborTypes();
				n3.updateAllNeighborTypes();
				
				assertTrue(n0.invSurNeighbors.size() == 0);
				assertTrue(n3.invSurNeighbors.size() == 0);
				
				n5 = new Node(new WebID(5), 3);
				assertTrue(n5.surNeighbors.size() == 0);
				n5.informNeighbors();
				
				assertTrue(n0.invSurNeighbors.size() == 0);
				assertTrue(n3.invSurNeighbors.size() == 0);
			}
			
			@Test
			public void testInformInvSurNeighbors() {
				
			}
			
			@Test
			public void testIncreaseHeight() {
				Node n = new Node(new WebID(0), 1);
				int h = n.height;
				n.increaseHeight();
				assertEquals(h+1, n.height);
			}
			
			@Test
			public void testGetParent() {
				Node.initialize();
				
				// children
				Node n1 = new Node(new WebID(2), 2);
				Node n2 = new Node(new WebID(4), 3);
				Node n3 = new Node(new WebID(8), 4);
				Node n4 = new Node(new WebID(16), 5);
				Node n5 = new Node(new WebID(13), 4);
				Node n6 = new Node(new WebID(6), 3);
				Node n7 = new Node(new WebID(31), 5);
				Node n8 = new Node(new WebID(33), 6);
				
				// parents
				Node n11 = new Node(new WebID(0), 1);
				Node n22 = new Node(new WebID(0), 2);
				Node n33 = new Node(new WebID(0), 3);
				Node n44 = new Node(new WebID(0), 4);
				Node n55 = new Node(new WebID(5), 3);
				Node n66 = new Node(new WebID(2), 2);
				Node n77 = new Node(new WebID(15), 4);
				Node n88 = new Node(new WebID(1), 5);
				
				// getParent uses getNode(), put the parents in nodes
				nodes.put(n11.webID, n11);
				nodes.put(n22.webID, n22);
				nodes.put(n33.webID, n33);
				nodes.put(n44.webID, n44);
				nodes.put(n55.webID, n55);
				nodes.put(n66.webID, n66);
				nodes.put(n77.webID, n77);
				nodes.put(n88.webID, n88);
				
				assertEquals(n11.webID.getValue(), n1.getParent().getWebId());
				assertEquals(n22.webID.getValue(), n2.getParent().getWebId());
				assertEquals(n33.webID.getValue(), n3.getParent().getWebId());
				assertEquals(n44.webID.getValue(), n4.getParent().getWebId());
				assertEquals(n55.webID.getValue(), n5.getParent().getWebId());
				assertEquals(n66.webID.getValue(), n6.getParent().getWebId());
				assertEquals(n77.webID.getValue(), n7.getParent().getWebId());
				assertEquals(n88.webID.getValue(), n8.getParent().getWebId());
			}
			
			@Test
			public void testGetChildNodeID() {
				Node n1 = new Node(new WebID(0), 2);
				Node n2 = new Node(new WebID(0), 3);
				Node n3 = new Node(new WebID(0), 4);
				Node n4 = new Node(new WebID(0), 5);
				Node n5 = new Node(new WebID(5), 4);
				Node n6 = new Node(new WebID(2), 3);
				Node n7 = new Node(new WebID(15), 5);
				Node n8 = new Node(new WebID(1), 6);
				
				assertEquals(new WebID(2), n1.getChildNodeID());
				assertEquals(new WebID(4), n2.getChildNodeID());
				assertEquals(new WebID(8), n3.getChildNodeID());
				assertEquals(new WebID(16), n4.getChildNodeID());
				assertEquals(new WebID(13), n5.getChildNodeID());
				assertEquals(new WebID(6), n6.getChildNodeID());
				assertEquals(new WebID(31), n7.getChildNodeID());
				assertEquals(new WebID(33), n8.getChildNodeID());
				
			}
			
			@Test
			public void testUpdateAllNeighborTypes() {
				hwSetup5();
				Node n1 = getNode(new WebID(1));
				
				assertTrue(n1.neighbors.size() == 2);
				assertTrue(n1.surNeighbors.size() == 0);
				assertTrue(n1.neighbors.contains(new WebID(0)));
				assertTrue(n1.neighbors.contains(new WebID(3)));
				
				hwSetup1();
				Node n4 = getNode(new WebID(4));
				
				n4.updateAllNeighborTypes();
				
				assertTrue(n4.neighbors.size() == 2);
				assertTrue(n4.surNeighbors.size() == 1);
				assertTrue(n4.neighbors.contains(new WebID(6)));
				assertTrue(n4.neighbors.contains(new WebID(0)));
				assertTrue(n4.surNeighbors.contains(new WebID(1)));
				
				hwSetup2();
				Node n7 = getNode(new WebID(7));
				
				n7.updateAllNeighborTypes();
				
				assertTrue(n7.neighbors.size() == 1);
				assertTrue(n7.surNeighbors.size() == 2);
				assertTrue(n7.neighbors.contains(new WebID(3)));
				assertTrue(n7.surNeighbors.contains(new WebID(2)));
				assertTrue(n7.surNeighbors.contains(new WebID(1)));
				
				hwSetup3();
				Node n6 = getNode(new WebID(6));
				n7 = getNode(new WebID(7));
				
				n6.updateAllNeighborTypes();
				n7.updateAllNeighborTypes();
				
				assertTrue(n6.neighbors.size() == 3);
				assertTrue(n6.surNeighbors.size() == 0);
				assertTrue(n6.neighbors.contains(new WebID(4)));
				assertTrue(n6.neighbors.contains(new WebID(7)));
				assertTrue(n6.neighbors.contains(new WebID(2)));
				
				assertTrue(n7.neighbors.size() == 2);
				assertTrue(n7.surNeighbors.size() == 1);
				assertTrue(n7.neighbors.contains(new WebID(3)));
				assertTrue(n7.neighbors.contains(new WebID(6)));
				assertTrue(n7.surNeighbors.contains(new WebID(1)));
			}

			@Test
			public void testBroadcastNodeStateChange() {
				hwSetup4();
				Node n7 = getNode(new WebID(7));
				n7.broadcastNodeStateChange();
				Node n3 = getNode(new WebID(3));
				n3.broadcastNodeStateChange();
				
				Node n;
				for (WebID id : nodes.keySet()) {
					n = getNode(id);
					assertEquals(n.getNodeStateInt(), 0);
				}
			}
			
			private void hwSetup1() {
				Node.initialize();
				
				Node n0 = new Node(new WebID(0), 3);
				Node n1 = new Node(new WebID(1), 2);
				Node n2 = new Node(new WebID(2), 3);
				Node n3 = new Node(new WebID(3), 2);
				Node n4 = new Node(new WebID(4), 3);
				Node n6 = new Node(new WebID(6), 3);
				
				nodes.put(n0.webID, n0);
				nodes.put(n1.webID, n1);
				nodes.put(n2.webID, n2);
				nodes.put(n3.webID, n3);
				nodes.put(n4.webID, n4);
				nodes.put(n6.webID, n6);
			}
			private void hwSetup2() {
				Node.initialize();
				
				Node n0 = new Node(new WebID(0), 3);
				Node n1 = new Node(new WebID(1), 2);
				Node n2 = new Node(new WebID(2), 2);
				Node n3 = new Node(new WebID(3), 3);
				Node n4 = new Node(new WebID(4), 3);
				Node n7 = new Node(new WebID(7), 3);
				
				nodes.put(n0.webID, n0);
				nodes.put(n1.webID, n1);
				nodes.put(n2.webID, n2);
				nodes.put(n3.webID, n3);
				nodes.put(n4.webID, n4);
				nodes.put(n7.webID, n7);
			}
			private void hwSetup3() {
				Node.initialize();
				
				Node n0 = new Node(new WebID(0), 3);
				Node n1 = new Node(new WebID(1), 2);
				Node n2 = new Node(new WebID(2), 3);
				Node n3 = new Node(new WebID(3), 3);
				Node n4 = new Node(new WebID(4), 3);
				Node n6 = new Node(new WebID(6), 3);
				Node n7 = new Node(new WebID(7), 3);
				
				nodes.put(n0.webID, n0);
				nodes.put(n1.webID, n1);
				nodes.put(n2.webID, n2);
				nodes.put(n3.webID, n3);
				nodes.put(n4.webID, n4);
				nodes.put(n6.webID, n6);
				nodes.put(n7.webID, n7);
			}
			// perfect 3 dimensional hypercube
			// used for testBroadcastNodeStateChange
			private void hwSetup4() {
				Node.initialize();
				
				Node n0 = new Node(new WebID(0), 3);
				Node n1 = new Node(new WebID(1), 3);
				Node n2 = new Node(new WebID(2), 3);
				Node n3 = new Node(new WebID(3), 3);
				Node n4 = new Node(new WebID(4), 3);
				Node n5 = new Node(new WebID(5), 3);
				Node n6 = new Node(new WebID(6), 3);
				Node n7 = new Node(new WebID(7), 3);
				
				nodes.put(n0.webID, n0);
				nodes.put(n1.webID, n1);
				nodes.put(n2.webID, n2);
				nodes.put(n3.webID, n3);
				nodes.put(n4.webID, n4);
				nodes.put(n5.webID, n5);
				nodes.put(n6.webID, n6);
				nodes.put(n7.webID, n7);
				
				n0.updateAllNeighborTypes();
				n1.updateAllNeighborTypes();
				n2.updateAllNeighborTypes();
				n3.updateAllNeighborTypes();
				n4.updateAllNeighborTypes();
				n5.updateAllNeighborTypes();
				n6.updateAllNeighborTypes();
				n7.updateAllNeighborTypes();
				
				n0.setNodeState(n0.new SlipperySlope());
				n1.setNodeState(n1.new SlipperySlope());
				n2.setNodeState(n2.new SlipperySlope());
				n3.setNodeState(n3.new Insertable());
				n4.setNodeState(n4.new SlipperySlope());
				n5.setNodeState(n5.new SlipperySlope());
				n6.setNodeState(n6.new SlipperySlope());
				n7.setNodeState(n7.new Insertable());
			}
			// 2 dimensional hypercube
			private void hwSetup5() {
				Node.initialize();
				
				Node n0 = new Node(new WebID(0), 2);
				Node n1 = new Node(new WebID(1), 2);
				Node n2 = new Node(new WebID(2), 2);
				Node n3 = new Node(new WebID(3), 2);
				
				nodes.put(n0.webID, n0);
				nodes.put(n1.webID, n1);
				nodes.put(n2.webID, n2);
				nodes.put(n3.webID, n3);
				
				n0.updateAllNeighborTypes();
				n1.updateAllNeighborTypes();
				n2.updateAllNeighborTypes();
				n3.updateAllNeighborTypes();
			}

	}

	}

