package model;


import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import Phase6.GlobalObjectId;
import Phase6.LocalObjectId;
import Phase6.ObjectDB;
import Phase6.PortNumber;
import database.Database;
import simulation.NodeInterface;
import states.*;
import visitor.BroadcastVisitor;
import visitor.Contents;
import visitor.Parameters;
import visitor.SendVisitor;

public class Node implements NodeInterface, Serializable {

	// debug flag

	
	
	// instance variables
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6055635090486424898L;
	private WebID webID;
	private int height;
	private WebID foldID;
	private WebID surrogateFoldID;
	private WebID invSurrogateFoldID;
	private ArrayList<WebID> neighbors;
	private ArrayList<WebID> surNeighbors;
	private ArrayList<WebID> invSurNeighbors;
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
	
	private Contents contents = new Contents();
	
	public GlobalObjectId gid  = new GlobalObjectId();
	
	// constructors
	public Node(WebID webID, int height, WebID foldID, WebID surrogateFoldID,
				WebID invSurrogateFoldID,ArrayList<WebID> neighbors,ArrayList<WebID> surNeighbors, 
				ArrayList<WebID> invSurNeighbors, Set<WebID> selfDown, Set<WebID> neighborDown, 
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
	public Node(){
		//set up for node proxy, but seems kind of useless
		
	}
public HashSet<WebID> getAllUpRelations(){ // made for validaing up pointers
		
		HashSet<WebID> relations = new HashSet<WebID>();
		relations.addAll(getInvSurNeighborList());
	
		if (this.getInvSurrogateFoldID() != null)
			relations.add(getInvSurrogateFoldID() );

		return relations;

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

	public ArrayList<WebID> getNeighborList() {
		return neighbors;
	}

	public void setNeighborList(ArrayList<WebID> neighbors) {
		this.neighbors = neighbors;
	}

	public ArrayList<WebID> getSurNeighborList() {
		return surNeighbors;
	}

	public void setSurNeighborList(ArrayList<WebID> surNeighbors) {
		this.surNeighbors = surNeighbors;
	}

	public ArrayList<WebID> getInvSurNeighborList() {
		return invSurNeighbors;
	}

	public void setInvSurNeighborList(ArrayList<WebID> invSurNeighbors) {
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

	
	

	// loop through neighbors and let them know that you are their neighbor
	public void informNeighbors() {
		for (WebID w : neighbors) {
			w.getNode().addNeighbor(this.getWebID());
		}

	}

	public void informSurNeighbors() {
		for (WebID w : surNeighbors) {
			w.getNode().addInvSurNeighbor(this.getWebID());
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
			invSurN = w.getNode();
			surN = this.getWebID().getNode();
			
			invSurN.removeSurNeighbor(surN.getWebID());
			surN.removeInvSurNeighbor(invSurN.getWebID());
		}
	}

	public Node insertChildNode() {
//		if (debug) {
//			currentPrintState += "Starting at: " + startPointInfo + "\n";
//			currentPrintState += "Inserting child at: " + insertPointInfo + "\n";
//			currentPrintState += "Child inserted: " + insertedNodeInfo + "\n";	
//		}

		WebID childID = this.getChildNodeID();

		Node child = new Node(childID, this.height);
		
		HyperWeb.getSingleton().putWrapper(childID, child);
		
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
		
		//insertPointInfo = parent.getWebId();
		//insertedNodeInfo = child.getWebId();
		
		parent.updatePointers();
		child.updatePointers();

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
		for (WebID neighborID : this.getNeighborList()) {
			neighbor = neighborID.getNode();
			
			neighborList.add(neighbor);
			// for each neighbors neighbor
			for (WebID nNeighborID : neighbor.getNeighborList()) {
				if (!nNeighborID.equals(this.webID)) {
					nNeighbor = nNeighborID.getNode();
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
			surNeighborArray[counter] = x.getNode();
			counter++;
		}
		return surNeighborArray;
	}

	@Override
	public NodeInterface[] getInverseSurrogateNeighbors() {
		NodeInterface[] invSurNeighborArray = new Node[invSurNeighbors.size()];
		int counter = 0;
		
		for (WebID x : invSurNeighbors) {
			invSurNeighborArray[counter] = x.getNode();
			counter++;
		}
		return invSurNeighborArray;
	}

	@Override
	public NodeInterface getFold() {
		
		return nullCheck(foldID);
	}

	@Override
	public NodeInterface getSurrogateFold() {
		
		return nullCheck(surrogateFoldID);

	}

	@Override
	public NodeInterface getInverseSurrogateFold() {
		
		return nullCheck(invSurrogateFoldID);

	}
	private Node nullCheck(WebID w){
		if (w == null)
			return null;
		else return w.getNode();
	}
	@Override
	public NodeInterface getParent() {
		WebID parentNode = null;
		
		if (this.webID.getValue() != 0) {
			parentNode = new WebID(this.webID.getValue() ^ ((int) Math.pow(2, this.height - 1)));
		}
		return nullCheck(parentNode);
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
			neighborArray[counter] = x.getNode();
			counter++;
		}

		return neighborArray;
	}
	
	
	//TODO: make proxy methods for these
	public void addNeighborDown(WebID w){
		neighborDown.add(w);
	}
	
	private void addDoubleNeighborDown(WebID webID2) {
		doubleNeighborDown.add(webID2);

		
	}
	
	private void removeDoubleNeighborDown(WebID webID2) {
		doubleNeighborDown.remove(webID2);

		
	}

	
	
	
	
	public void addNeighbor(WebID neighborId) {
		this.neighbors.add(neighborId);
	}

	

	
	/**
	 * The function called by the HyperWeb that will removed a node from the hyperWeb
	 * we assume that the hyperweb is not empty when we call the dfkdjkfd().getNode function, as
	 * we should not be able to remove from an empty hyperweb
	 * 
	 * there will be special cases if the highest node available to be disconnected has
	 * a webID of either one or zero
	 */

	public void getConnectionsFrom(Node n) {
		
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
		Node parent = parentID.getNode();
		parent.decreaseHeight();
		
		parent.setCurrentChild(parent.getCurrentChildNodeID());
		
		ArrayList<WebID> neighborList = new ArrayList<WebID>();
		ArrayList<WebID> surNeighborList = new ArrayList<WebID>();
		neighborList.addAll(this.neighbors);
		surNeighborList.addAll(this.surNeighbors);
		// set parent of this node as surNeighbor to my neighbors 
		System.out.println("ID to remove: " + this.getWebID());
		for (WebID neighborID : neighborList) {
			if (!neighborID.equals(parentID)) {
				neighborID.getNode().addSurNeighbor(parentID);
			}
			neighborID.getNode().neighbors.remove(this.getWebID());
		}
		for (WebID neighborID : this.neighbors) {
			System.out.println("neighbors of  " + neighborID + ": " + neighborID.getNode().neighbors);
		}
		
		// remove all invSurNeighbors that point to this node
		for (WebID surNeighborID : surNeighborList) {
			surNeighborID.getNode().removeInvSurNeighbor(this.getWebID());
		}
		this.getFoldState().removeFoldsOf(this);
		
		parent.updateNeighborHeightRelations();
		parent.updatePointers();
		
		this.removePointersTwoHopsAway();
	}
	
	

	// assume this.height < the new height of the child
	// in other words, the parent's height has NOT already been incremented
	public WebID getChildNodeID() {
		// no height means no children or no neighbors
		if (this.getHeight() == 0) {
			return null;
		}
		
		//I need to run through the list of neighbors
		int childid = ((int) Math.pow(2, this.getHeight())) | this.getWebId();
		
		
		//TODO: make sure this is good, I added it to make sure it grabbed a valid webID
		//update: turns out this is actually creating a new web id... 
		return new WebID(childid);
//		for (WebID w:this.getNeighborList()){
//			
//			if (w.getValue() == childid){
//				return w;
//				
//			}
//		}
		
		
		
	}
	
	public ArrayList<WebID> getSurNeighbors() {
		return surNeighbors;
	}
	public void setSurNeighbors(ArrayList<WebID> surNeighbors) {
		this.surNeighbors = surNeighbors;
	}
	public ArrayList<WebID> getInvSurNeighbors() {
		return invSurNeighbors;
	}
	public void setInvSurNeighbors(ArrayList<WebID> invSurNeighbors) {
		this.invSurNeighbors = invSurNeighbors;
	}
	public Set<WebID> getSelfDown() {
		return selfDown;
	}
	public void setSelfDown(Set<WebID> selfDown) {
		this.selfDown = selfDown;
	}
	public Set<WebID> getNeighborDown() {
		return neighborDown;
	}
	public void setNeighborDown(Set<WebID> neighborDown) {
		this.neighborDown = neighborDown;
	}
	public Set<WebID> getDoubleNeighborDown() {
		return doubleNeighborDown;
	}
	public void setDoubleNeighborDown(Set<WebID> doubleNeighborDown) {
		this.doubleNeighborDown = doubleNeighborDown;
	}
	public Set<WebID> getSelfUp() {
		return selfUp;
	}
	public void setSelfUp(Set<WebID> selfUp) {
		this.selfUp = selfUp;
	}
	public Set<WebID> getNeighborUp() {
		return neighborUp;
	}
	public void setNeighborUp(Set<WebID> neighborUp) {
		this.neighborUp = neighborUp;
	}
	public Set<WebID> getDoubleNeighborUp() {
		return doubleNeighborUp;
	}
	public void setDoubleNeighborUp(Set<WebID> doubleNeighborUp) {
		this.doubleNeighborUp = doubleNeighborUp;
	}
	public Set<WebID> getLowerNeighbors() {
		return lowerNeighbors;
	}
	public void setLowerNeighbors(Set<WebID> lowerNeighbors) {
		this.lowerNeighbors = lowerNeighbors;
	}
	public Set<WebID> getHigherNeighbors() {
		return higherNeighbors;
	}
	public void setHigherNeighbors(Set<WebID> higherNeighbors) {
		this.higherNeighbors = higherNeighbors;
	}
	public GlobalObjectId getGid() {
		return gid;
	}
	public void setGid(GlobalObjectId gid) {
		this.gid = gid;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public WebID getCurrentChild() {
		return currentChild;
	}
	
	public void removeNeighborDown(WebID webID){
		neighborDown.remove(webID);
	}
	public void setNeighbors(ArrayList<WebID> neighbors) {
		this.neighbors = neighbors;
	}
	public void setContents(Contents contents) {
		this.contents = contents;
	}
	// assume height has already been decremented/incremented
	public WebID getCurrentChildNodeID() {
		// no height means no children or no neighbors
		if (this.height == 0) {
			return null;
		}
		
		//TODO: changed to make sure web id's are valid and have gid
		int childid = ((int) Math.pow(2, this.height-1)) ^ webID.getValue();
		if (childid == 0) {
			return null;
		}
		for (WebID w:this.getNeighborList()){
			
			if (w.getValue() == childid){
				return w;
				
			}
		}
		
		
		
		
		return null;
	}

	public WebID getParentNodeID() {
		int childid = this.webID.getValue() ^ Integer.highestOneBit(this.webID.getValue());
		
		//TODO: changed to make sure web id's are valid and have gid

		for (WebID w:this.getNeighborList()){
			
			if (w.getValue() == childid){
				return w;
				
			}
		}
		return null;
	}

	
	/*
	 * "State Checker" Methods
	 * --------------------------------------------------------------
	 */
	public void updateAllNeighborTypes() {
		WebID neighborID = null;
		WebID surNeighborID = null;

		for (int i = 0; i < this.getHeight(); i++) {
			// flip one bit of the checking node's webID to get a neighborID
			int neighborInt = this.getWebId() ^ ((int) Math.pow(2, i));
			
			for (WebID w:this.getNeighborList()){
				
				if (w.getValue() == neighborInt){
					neighborID = w;
					break;
				}
				System.err.println("no neighbor found");
			}
			

			if (neighborID != null && neighborID.getNode() != null) {
				this.addNeighbor(neighborID);
			} else {
				// the parent node neighbor will never be null, so we can get the parents of the
				// other would-be neighbors by removing the highest order 1 bit.
				// because of the above condition, I'm assuming every neighborID that makes
				// it to this point will begin with a 1 and have the same number of bits as the
				// this.webID; therefore XOR the highest bit to cancel it
//				
//				this.getWebId();
				int surNeighborInt = neighborInt ^ Integer.highestOneBit(this.getWebId());
				
				for (WebID w:this.getNeighborList()){
					
					if (w.getValue() == surNeighborInt){
						surNeighborID = w;
						
					}
				}
				
				
				if (surNeighborID != null && surNeighborID.getNode() != null) {
					this.addSurNeighbor(surNeighborID);
				}
			}
		}

	}
	
	public void updateNeighborHeightRelations() {
		Node neighbor;
		System.out.println("update");
		for (WebID neighborID : this.getNeighborList()) {
			neighbor = neighborID.getNode();
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
	
	public void removePointersTwoHopsAway() {
		ArrayList<HashSet<Node>> twoHopsAway = new ArrayList<HashSet<Node>>();
		// get ALL neighbors and neighbors neighbors
		twoHopsAway = this.getAll2Hops();
		
		HashSet<Node> neighborList = twoHopsAway.get(0);
		HashSet<Node> doubleNeighborList = twoHopsAway.get(1);
		
		// loop through neighbors
		for (Node n : neighborList) {
			n.neighborDown.remove(this.getWebID());
			n.neighborUp.remove(this.getWebID());
			n.updateDownState();
			n.updateUpState();
		}
		
		// loop through neighbors neighbors
		for (Node n : doubleNeighborList) {
			n.doubleNeighborDown.remove(this.getWebID());
			n.doubleNeighborUp.remove(this.getWebID());
			n.updateDownState();
			n.updateUpState();
		} 
	}
	
	public void updatePointers() {
		ArrayList<HashSet<Node>> twoHopsAway = new ArrayList<HashSet<Node>>();
		// get ALL neighbors and neighbors neighbors
		twoHopsAway = this.getAll2Hops();
		
		HashSet<Node> neighborList = twoHopsAway.get(0);
		HashSet<Node> doubleNeighborList = twoHopsAway.get(1);
		
		// loop through neighbors
		for (Node n : neighborList) {
			if (n.hasDownPointers()) {
				this.neighborDown.add(n.getWebID());
			}
			else {
				this.neighborDown.remove(n.getWebID());
			}
			
			if (n.hasUpPointers() && n.getWebID().minBitRep() >= this.getWebID().minBitRep()) {
				this.neighborUp.add(n.getWebID());
			}
			else if (!n.hasUpPointers() && n.getWebID().minBitRep() >= this.getWebID().minBitRep()) {
				this.neighborUp.remove(n.getWebID());
			}
		}
		
		// loop through neighbors neighbors
		for (Node n : doubleNeighborList) {
			if (n.hasDownPointers()) {
				this.doubleNeighborDown.add(n.getWebID());
			}
			else {
				this.doubleNeighborDown.remove(n.getWebID());
			}
			
			if (n.hasUpPointers() && n.getWebID().minBitRep() >= this.getWebID().minBitRep()) {
				this.neighborUp.add(n.getWebID());
			}
			else if (!n.hasUpPointers() && n.getWebID().minBitRep() >= this.getWebID().minBitRep()) {
				this.neighborUp.remove(n.getWebID());
			}
		}
		this.updateDownState();
		this.updateUpState();
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
			n.addNeighborDown(this.getWebID());
			
			n.updateDownState();
		}
		
		// loop through neighbors neighbors
		for (Node n : doubleNeighborList) {
			// we don't want to update neighbors with lower heights than us
			if (this.getHeight() > n.getHeight()) {
				continue;
			}
			n.addDoubleNeighborDown(this.getWebID());
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
			//n.getNeighborDown().remove(this.getWebID());
			n.removeNeighborDown(this.getWebID());

			n.updateDownState();
		}
		
		// loop through neighbors neighbors
		for (Node n : doubleNeighborList) {
			// we don't want to update neighbors with lower heights than us
			if (this.getHeight() > n.getHeight()) {
				continue;
			}
			n.removeDoubleNeighborDown(this.getWebID());
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
	
	public WebID slideUp() {
		if (this.selfUp.size() > 0) {
			return this.selfUp.iterator().next();
		}
		else if (this.neighborUp.size() > 0) {
			return this.neighborUp.iterator().next();
		}
		else if (this.doubleNeighborUp.size() > 0) {
			return this.doubleNeighborUp.iterator().next();
		}
		else if (this.currentChild != null) {
			return this.currentChild;
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
		return (this.invSurrogateFoldID != null || 
				this.selfUp.size() > 0 ||
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
			if (this.getHeight() < n.getHeight() ||  this.getWebID().minBitRep() < n.getWebID().minBitRep()) {
				continue;
			}
			n.neighborUp.add(this.getWebID());
			n.updateUpState();
		}
		
		// loop through neighbors neighbors
		for (Node n : doubleNeighborList) {
			// we don't want to update neighbors with higher heights than us
			if (this.getHeight() < n.getHeight() ||  this.getWebID().minBitRep() < n.getWebID().minBitRep()) {
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
	
	/**
	 * Function that will send a command from one node to another taking the shorest path between
	 * the two nodes. The Send First Node checks if the fold or inverse surrogate fold is closer
	 * to the endNode. If it is not, then it will only look though the neighbors of the start node.
	 * 
	 * We assume that there is an end node and a start node inside the dfkdjkfd(). If given a node that
	 * is not in the hyperweb, we return a null and say that there is no node and the package can't be delivered
	 * 
	 * 
	 * @param startNone	the node that we are sending the command from
	 * @param endNode	the node that will be recieving the command from the start node 
	 */
	public Node sendFirstNode(int end) {
		//compare the closeness of a neighbor of the start node, and the fold of the node
		WebID foldID = this.getFoldID();
		if(foldID == null) {
			foldID = this.getSurrogateFoldID();
		}
		
		// all neighbors will have the same distance from the start node, so just grab the first one
		WebID neighborID = this.getNeighborList().get(0);
		if(neighborID == null) {
			neighborID = this.getInvSurNeighborList().get(0);
		}
		
		//compare the two webIDs for which one will get us closer to the final node
		int commonFoldID = foldID.getNumberBitsInCommon(end);
		int commonNeighborID = neighborID.getNumberBitsInCommon(end);
		
		if(commonNeighborID > commonFoldID) {
			//go through the neighbors
			return this;
		} else {
			//go to the fold and then go through the neighbors
			return foldID.getNode();
		}
	}
	
	/**
	 * The function that will get closer to the end node by hopping through the dfkdjkfd(). 
	 * we first check if the start node is the end node (this meaning that we have reached the 
	 * end node) and deliver the command
	 * 
	 * We then check all of the inverse surrogate neighbors for any neighbor which is closer to the end node.
	 * Each hop through an inverse surrogate neighbor will get us 2 digits closer
	 * 
	 * Next we check for closer neighbors, making a one step closer approach to the end node
	 * 
	 * finally we check our surrogate neighbors, but only if the other lists have nothing closer. We check for
	 * any nodes that are of equal distance to the end node as we are. 
	 *
	 * @param startNode
	 * @param endNode
	 */
	public Node sendNode(int endNode) {
		//Check to see if we have arrived at the end node
		System.out.println("Start node: " + this.getWebId());
		System.out.println("End node: " + endNode);
		
		int closestID = 0;
		WebID closestWebID = null;
		for(WebID w : this.getInvSurNeighborList()){
			int currentID = w.getNumberBitsInCommon(endNode);
			if(currentID > closestID) {
				closestID = currentID;
				closestWebID = w;
			}
		}
		
		for(WebID w : this.getNeighborList()){
			int currentID = w.getNumberBitsInCommon(endNode);
			if(currentID > closestID) {
				closestID = currentID;
				closestWebID = w;
			}
		}
		
		if(closestWebID != null) {
			Node node = closestWebID.getNode();
			//sendNode(node, endNode);
			return node;
		}
		
		for(WebID w : this.getSurNeighborList()){
			int currentID = w.getNumberBitsInCommon(endNode);
			if(currentID == closestID) {
				closestID = currentID;
				closestWebID = w;
			}
		}
		
		if(closestWebID != null) {
			Node node = closestWebID.getNode();
			//sendNode(node, endNode);
			return node;
		}
		return null;
	}
	
	public void accept(SendVisitor sendVisitor) {
		sendVisitor.secondVisit(this);
	}
	
	public void accept(BroadcastVisitor broadcastVisitor, Parameters parameters) {
		
	}

	public Parameters getContents() {
		// TODO Auto-generated method stub
		return null;
	}

	public void accept(BroadcastVisitor broadcastVisitor) {
		broadcastVisitor.broadcast(this);
	}
	
	
	private Object writeReplace(){
//		create the proxy that will be returned. Possibly make it conditional
			if (SerializeHelp.getLiteral1()) // in case sometimes we want the real thing
			{
				return this;
			}
			else{
				return new NodeProxy(gid);
			}
		}
		private Object readResolve(){
//		if the object being deserialized is a proxy, but should reference to something real, than change it to a real object
			if (SerializeHelp.getLiteral1() ||  ObjectDB.getSingleton().getValue(gid.getLocalObjectId()) == null)
				return this;
			else 
				return ObjectDB.getSingleton().getValue(gid.getLocalObjectId());
		}
}