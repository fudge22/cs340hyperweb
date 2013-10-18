package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.junit.*;

import database.Database;
import simulation.NodeInterface;
import states.FoldState;
import states.Insertable;
import states.NodeState;
import states.SlipperySlope;
import states.StableFold;
import states.UnstableISF;
import states.UnstableSF;

/**
 * Node Class for each Node within the Hypeerweb.
 * 
 *
 */
public class Node implements NodeInterface {

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
	private NodeState nodeState;
	private FoldState foldState;
	
	/**
	 * Constructor to create a new Node.
	 * 
	 * @param webID
	 * @param height
	 * @param foldID
	 * @param surrogateFoldID
	 * @param invSurrogateFoldID
	 * @param neighbors
	 * @param surNeighbors
	 * @param invSurNeighbors
	 * @param foldStateInt
	 * @param nodeStateInt
	 */
	// constructors
	public Node(WebID webID, int height, WebID foldID, WebID surrogateFoldID,
				WebID invSurrogateFoldID, List<WebID> neighbors, List<WebID> surNeighbors, 
				List<WebID> invSurNeighbors, int foldStateInt, int nodeStateInt) {
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
			this.foldState = StableFold.getSingleton();
			break;
		case 1:
			this.foldState = UnstableSF.getSingleton();
			break;
		case 2:
			this.foldState = UnstableISF.getSingleton();
			break;
		}
		
		switch (nodeStateInt) {
		case 0:
			this.nodeState = Insertable.getSingleton();
			break;
		case 1:
			this.nodeState = SlipperySlope.getSingleton();
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
	}

	/**
	 * Get the WebID
	 * @return
	 */
	// getters and setters
	public WebID getWebID() {
		return webID;
	}

	/**
	 * Set the WebID
	 * @param webID
	 */
	public void setWebID(WebID webID) {
		this.webID = webID;
	}

	/**
	 * Get the Height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Set the Height
	 * @param height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Get the FoldID
	 * @return
	 */
	public WebID getFoldID() {
		return foldID;
	}

	/**
	 * Set the FoldID
	 * @param foldID
	 */
	public void setFoldID(WebID foldID) {
		this.foldID = foldID;
	}

	/**
	 * Get the Surrogate Fold ID
	 * @return
	 */
	public WebID getSurrogateFoldID() {
		return surrogateFoldID;
	}

	/**
	 * Set the surrogate fold ID
	 * @param surrogateFoldID
	 */
	public void setSurrogateFoldID(WebID surrogateFoldID) {
		this.surrogateFoldID = surrogateFoldID;
	}

	/**
	 * get the inverse surrogate Fold ID
	 * @return
	 */
	public WebID getInvSurrogateFoldID() {
		return invSurrogateFoldID;
	}

	/**
	 * Set Inverse Surrogate Fold ID
	 * @param invSurrogateFoldID
	 */
	public void setInvSurrogateFoldID(WebID invSurrogateFoldID) {
		this.invSurrogateFoldID = invSurrogateFoldID;
	}

	/**
	 * Get a list of neighbors
	 * @return
	 */
	public List<WebID> getNeighborList() {
		return neighbors;
	}

	/**
	 * set the list of neighbors
	 * @param neighbors
	 */
	public void setNeighborList(List<WebID> neighbors) {
		this.neighbors = neighbors;
	}

	/**
	 * get a list of the surrogate neighbors
	 * @return
	 */
	public List<WebID> getSurNeighborList() {
		return surNeighbors;
	}

	/**
	 * set the list of surrogate neighbors
	 * @param surNeighbors
	 */
	public void setSurNeighborList(List<WebID> surNeighbors) {
		this.surNeighbors = surNeighbors;
	}

	/**
	 * get the list of inverse surrogate neighbors
	 * @return
	 */
	public List<WebID> getInvSurNeighborList() {
		return invSurNeighbors;
	}

	/**
	 * set the list of inverse surrogate neighbors
	 * @param invSurNeighbors
	 */
	public void setInvSurNeighborList(List<WebID> invSurNeighbors) {
		this.invSurNeighbors = invSurNeighbors;
	}

	/**
	 * get the fold state
	 * @return
	 */
	public FoldState getFoldState() {
		return foldState;
	}
	
	/**
	 * Set the fold state
	 * @param foldState
	 */
	public void setFoldState(FoldState foldState) {

		this.foldState = foldState;
	}

	/**
	 * get the integer representing the fold state
	 * @return
	 */
	public int getFoldStateInt() {
		return foldState.getFoldStateInt();
	}

	/**
	 * get the node state
	 * @return
	 */
	public NodeState getNodeState() {
		return nodeState;
	}
	
	/**
	 * set the node state
	 * @param nodeState
	 */
	public void setNodeState(NodeState nodeState) {
		this.nodeState = nodeState;
	}

	/**
	 * get the integer representing the node state.
	 * @return
	 */
	public int getNodeStateInt() {
		return nodeState.getNodeStateInt();
	}
	
	/**
	 * method to see if one node is the same as another node
	 */
	@Override
	public boolean equals(Object o) {
		Node other = (Node) o;
		return this.webID.equals(other.webID);
	}

	/**
	 * method to initialize the node
	 */
	public static void initialize() {
		nodes = new HashMap<WebID, Node>();
	}
	
	/**
	 * Get the node with the corresponding web ID
	 * @param id
	 * @return
	 */
	public static Node getNode(WebID id) {
		return nodes.get(id);// if null, return null node
	}

	/**
	 * Method to let a node's neighbors know that they are new neighbors
	 */
	public void informNeighbors() {
		for (WebID w : neighbors) {
			getNode(w).addNeighbor(this.getWebID());
		}

	}

	/**
	 * method to inform a node's surrogate neighbors that they are now surrogate neighbors.
	 */
	public void informSurNeighbors() {
		for (WebID w : surNeighbors) {
			getNode(w).addInvSurNeighbor(this.getWebID());
		}

	}
	
	/**
	 * When a new child is inserted, this method finds all of the inverse surrogate neighbors 
	 * and deletes their surrogate neighbors and then deletes the inverse surrogate neighbors.
	 * @param child
	 */
	public void informInvSurNeighbors(Node child) {
		for (WebID w : invSurNeighbors) {
			getNode(w).surNeighbors.remove(this.getWebID());
		}
		invSurNeighbors.clear();
	}

	/**
	 * Adds an inverse surrogate neighbor to a node.
	 * @param surNeighbor
	 */
	public void addInvSurNeighbor(WebID surNeighbor) {
		this.invSurNeighbors.add(surNeighbor);
	}

	/**
	 * Inserts a new child node to a node.
	 * @return
	 */
	public Node insertChildNode() {
		this.increaseHeight();

		WebID childID = this.getChildNodeID();

		Node child = new Node(childID, this.height);
		nodes.put(childID, child);

		child.updateAllNeighborTypes();
		Node parent = this;

		// we need to inform the neighbors of their new neighbor
		// and inform surrogate neighbors of their new inverse surrogate
		// neighbor
		child.informNeighbors();
		child.informSurNeighbors();
		parent.informInvSurNeighbors(child);

		parent.foldState.updateFoldOf(parent, child);

		return child;
	}

	/**
	 * increases the height of the node
	 */
	public void increaseHeight() {
		this.height++;
	}

	/*
	 * 
	 */
	/**
	 * There is a condition that exists when a node is inserted into a hyperweb
	 * and that node fills a hole, meaning that all of the nodes neighbors
	 * already exist, in which the nodes neighbors and the nodes neighbors
	 * neighbors will be in a slippery slope state prior to the insert. Once the
	 * node is inserted and fills that hole, that node, its neighbors, and its
	 * neighbors neighbors will then all need to be in the insertable state.
	 * 
	 * This method broadcasts a node state change 2 hops away.
	 * @param nodeStateInt
	 */
	public void broadcastNodeStateChange(int nodeStateInt) {
		HashSet<Node> twoHopsAway = new HashSet<Node>();

		// get neighbors and neighbors neighbors
		twoHopsAway = this.getAll2Hops();

		for (Node n : twoHopsAway) {
			if (nodeStateInt == 1) {
				n.setNodeState(SlipperySlope.getSingleton());
			} else {
				n.setNodeState(Insertable.getSingleton());
			}

		}
	}

	/**
	 * Gets a set of nodes 2 hops away from the current node. 
	 * 
	 * @return
	 */
	public HashSet<Node> getAll2Hops() {
		Node neighbor, nNeighbor;
		HashSet<Node> visited = new HashSet<Node>();

		// for each neighbor
		for (WebID neighborID : this.neighbors) {
			neighbor = getNode(neighborID);
			visited.add(neighbor);
			// for each neighbors neighbor
			for (WebID nNeighborID : neighbor.neighbors) {
				nNeighbor = getNode(nNeighborID);
				visited.add(nNeighbor);
			}
		}
		return visited;
	}

	
	
	/*
	 * Required Validator Methods
	 * --------------------------------------------------------------
	 */

	
	/**
	 * Gets the web Id in form of an integer
	 */
	@Override
	public int getWebId() {
		return webID.getValue();
	}

	/**
	 * gets a list of surrogate neighbors as an array of nodeInterface
	 */
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

	/**
	 * gets a list of inverse surrogate neighbors as an array of nodeInterface
	 */
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

	/**
	 * get the fold in form of a nodeInterface
	 */
	@Override
	public NodeInterface getFold() {
		return getNode(foldID);
	}

	/**
	 * get the surrogate fold in form of a nodeInterface
	 */
	@Override
	public NodeInterface getSurrogateFold() {
		return getNode(surrogateFoldID);

	}

	/**
	 * gets the inverse surrogate fold as a nodeInterface
	 */
	@Override
	public NodeInterface getInverseSurrogateFold() {
		return getNode(invSurrogateFoldID);

	}
	
	/**
	 * gets the parent as a nodeInterface
	 */
	@Override
	public NodeInterface getParent() {
		WebID parentNode = null;

		parentNode = new WebID(this.webID.getValue() ^ ((int) Math.pow(2, this.height - 1)));

		return getNode(parentNode);
	}

	/**
	 * compares one node to another node
	 */
	@Override
	public int compareTo(NodeInterface node) {
		if (webID.getValue() > node.getWebId()) {
			return 1;
		}
		else if (webID.getValue() < node.getWebId()) {
			return -1;
		}
		else {
			return 0;
		}
	}

	/**
	 * Gets an array in form of nodeInterface for all the neighbors of the current node
	 */
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

	/**
	 * gets an array in form of nodeInterface for all the nodes corresponding to the current node
	 * @return
	 */
	public static NodeInterface[] allNodes() {
		Node[] nodeArray = new Node[nodes.size()];
		nodes.values().toArray(nodeArray); // the toArray() function fills in
											// the array nodeArray
		return nodeArray;

	}

	/**
	 * add a new node to the hypeerweb
	 */
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

	/**
	 * a private method that prints out the hypeerweb for testing purposes
	 */
	private static void printHyperWeb() {
		String n = "";
		for (WebID w : nodes.keySet()) {
			Node n1 = getNode(w);
			n += "WebID: " + w.getValue() + "\n";
			n += "Height: " + getNode(w).getHeight() + "\n";

			if (n1.getFoldState() != null) {
				n += "FoldState: " + n1.getFoldState().getClass().getName()
						+ "\n";
			} else {
				n += "FoldState: null\n";
			}

			if (n1.getNodeState() != null) {
				n += "NodeState: " + n1.getNodeState().getClass().getName()
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

	/**
	 * add a neighbor to the curent node
	 * @param neighborId
	 */
	public void addNeighbor(WebID neighborId) {
		this.neighbors.add(neighborId);
	}

	/**
	 * adds a node to the hypeerweb
	 * @return
	 */
	public static Node addToHyperWeb() {
		Random generator = new Random();
		int randomInsertionPoint = generator.nextInt(nodes.size() - 1);
		// possibly will have to change logic later

		int count = 0;
		WebID insertPointID = new WebID(0);
		for (WebID id : nodes.keySet()) {
			if (count == randomInsertionPoint) {
				insertPointID = id;
				break;
			}
			count++;
		}
		// function to find closest node

		Node insertPoint = getNode(insertPointID);

		System.out.println("Trying to insert at " + insertPointID);
		insertPoint.getNodeState().addToNode(insertPoint);
		
		return nodes.get(0);
	}

	/**
	 * adds a new node to an empty hyperweb
	 * @return
	 */
	public static Node addToEmptyHyperWeb() {
		ArrayList<WebID> neighbors = new ArrayList<WebID>();
		// we inserted an empty list into surNeighbors so it always exists in the future
		ArrayList<WebID> surNeighbors = new ArrayList<WebID>();
		ArrayList<WebID> invSurNeighbors = new ArrayList<WebID>();
		// right now we have been using negative ids(well -1) to represent null
		// integers. This works well as long as we don't actually insert
		// them into the hashmap. The hashmap will always return a null for a
		// key it does not have.

		nodes.put(new WebID(0), new Node(new WebID(0), 0, null, null, null,
				neighbors, surNeighbors, invSurNeighbors, 0, 0));
		
		nodes.get(new WebID(0)); // it has no parent
		return nodes.get(new WebID(0));
	}

	/**
	 * adds a second node to a hypeerweb. The hypeerweb needs to have 1.
	 * @return
	 */
	public static Node addSecondNode() {
		ArrayList<WebID> neighbors = new ArrayList<WebID>();
		ArrayList<WebID> surNeighbors = new ArrayList<WebID>();
		ArrayList<WebID> invSurNeighbors = new ArrayList<WebID>();
		WebID firstId = new WebID(0);
		WebID secondId = new WebID(1);

		nodes.put(secondId, new Node(secondId, 0, null, null, null, neighbors,
				surNeighbors, invSurNeighbors, 0, 0));
		nodes.get(secondId).addNeighbor(firstId);
		nodes.get(firstId).setHeight(1);
		nodes.get(secondId).setFoldID(firstId);
		nodes.get(secondId).setHeight(1);
		nodes.get(firstId).addNeighbor(secondId);
		nodes.get(firstId).setFoldID(secondId);
//		nodes.get(secondId).setParent(firstId);

		nodes.get(firstId).setFoldState(StableFold.getSingleton());
		nodes.get(secondId).setFoldState(StableFold.getSingleton());

		return nodes.get(secondId);

	}

	/**
	 * loads the hypeerweb from the database
	 * @param loadHyperWeb
	 */
	public static void loadHyperWeb(HashMap<WebID, Node> loadHyperWeb) {
		nodes = Database.getInstance().getDatabaseAccessor().loadHyperWeb();

	}

	/**
	 * Gets the web ID of the child node of the current node.
	 * @return
	 */
	public WebID getChildNodeID() {
		// assume this.height == the new height of the child
		// in other words, the parent's height has already been incremented
		return new WebID(((int) Math.pow(2, this.height - 1)) | webID.getValue());
	}

	/**
	 * gets the web ID of the parent node to the current node
	 * @return
	 */
	public WebID getParentNodeID() {
		return new WebID(this.webID.getValue() ^ Integer.highestOneBit(this.webID.getValue()));
	}

	
	/*
	 * "State Checker" Methods
	 * --------------------------------------------------------------
	 */
	
	
	/**
	 * updates the list of neighbors and surrogate neighbors for the current node
	 */
	public void updateAllNeighborTypes() {
		ArrayList<WebID> surNeighborsList = new ArrayList<WebID>();
		ArrayList<WebID> neighborsList = new ArrayList<WebID>();
		WebID neighborID;
		WebID surNeighborID;

		for (int i = 0; i < this.height; i++) {
			// flip one bit of the checking node's webID to get a neighborID
			neighborID = new WebID(this.webID.getValue() ^ ((int) Math.pow(2, i)));

			if (getNode(neighborID) != null) {
				neighborsList.add(neighborID);
			} else {
				// the parent node neighbor will never be null, so we can get the parents of the
				// other would-be neighbors by removing the highest order 1 bit.
				// because of the above condition, I'm assuming every neighborID that makes
				// it to this point will begin with a 1 and have the same number of bits as the
				// this.webID; therefore XOR the highest bit to cancel it
				Node neighbor = new Node(neighborID, this.height);
				surNeighborID = neighbor.getParentNodeID();
				if (getNode(surNeighborID) != null) {
					surNeighborsList.add(surNeighborID);
				}
			}
		}

		// update the class variables
		neighbors = neighborsList;
		surNeighbors = surNeighborsList;

	}

	/**
	 * gets a list of type webID for all of the lower neighbors to the current node
	 * @return
	 */
	public List<WebID> checkLowerNeighbors() {
		ArrayList<WebID> lowerNeighborsList = new ArrayList<WebID>();

		for (WebID neighborID : neighbors) {
			if (getNode(neighborID).height < this.height) {
				lowerNeighborsList.add(neighborID);
			}
		}

		return lowerNeighborsList;
	}

	/**
	 * finds the lowest node, where a node can be inserted at
	 * @return
	 */
	public Node findHole() {
		Node parent = null;
		// check this node first
		if (this.surNeighbors.size() > 0) {

			parent = getNode(this.surNeighbors.get(0));

		} else if (this.surrogateFoldID != null) {
			parent = getNode(this.surrogateFoldID);

		} else if (this.checkLowerNeighbors().size() > 0) {

			parent = getNode(this.checkLowerNeighbors().get(0));
		}
		return parent;
	}
	
	/**
	 * finds a node that is at the highest location which will be used in the deletion process
	 * @return
	 */
	public Node findHighestNode(){
		
		Node highestNode = null;
		// check this node first
		if (this.invSurNeighbors.size() > 0) {

			highestNode = getNode(this.invSurNeighbors.get(0));

		} else if (this.invSurrogateFoldID != null) {
			highestNode = getNode(this.invSurrogateFoldID);

		} else if (this.checkHigherNeighbors().size() > 0) {

			highestNode = getNode(this.checkHigherNeighbors().get(0));
		}
		return highestNode;
	}

	/**
	 * gets a list of higher neighbors for the current node.
	 * @return
	 */
	public List<WebID> checkHigherNeighbors() {
		
		ArrayList<WebID> higherNeighborsList = new ArrayList<WebID>();

		for (WebID neighborID : neighbors) {
			if (getNode(neighborID).height > this.height) {
				higherNeighborsList.add(neighborID);
			}
		}

		return higherNeighborsList;
	}
	
	/*
	 * Unit Tests for Node Class methods
	 * --------------------------------------------------------------
	 */

	/**
	 * Unit tests for the node class
	 *
	 */
	public static class NodeMethodsTests {

		@BeforeClass
		public static void setUpBeforeClass() {
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

		/**
		 * tests the moethod that informs all of a node's neighbors that they are now neighbors
		 */
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

		/**
		 * tests the method that informs all of a node's surrogate neighbors that they are now surrogate neighbors
		 */
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

		/**
		 * tests the method that increases the height of a node
		 */
		@Test
		public void testIncreaseHeight() {
			Node n = new Node(new WebID(0), 1);
			int h = n.height;
			n.increaseHeight();
			assertEquals(h + 1, n.height);
		}

		/**
		 * tests the method that gets the parent
		 */
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

		/**
		 * tests the method that gets the child of a given node
		 */
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

		/**
		 * tests the method that updates all the neighbor types
		 */
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

		/**
		 * tests the method that broadcasts the node state change
		 */
		@Test
		public void testBroadcastNodeStateChange() {
			hwSetup4();
			Node n7 = getNode(new WebID(7));
			n7.setNodeState(Insertable.getSingleton());
			n7.broadcastNodeStateChange(Insertable.getSingleton().getNodeStateInt());
			Node n3 = getNode(new WebID(3));
			n3.setNodeState(Insertable.getSingleton());
			n3.broadcastNodeStateChange(Insertable.getSingleton().getNodeStateInt());

			Node n;
			for (WebID id : nodes.keySet()) {
				n = getNode(id);
				assertEquals(n.getNodeStateInt(), 0);
			}
		}

		/**
		 * creates a hyperweb with 6 nodes, not including webID 5 and 7
		 */
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

		/**
		 * creates a hyperweb with 6 nodes, not including webID 5 and 6
		 */
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

		/**
		 * creates a hypeerweb with 8 nodes
		 */
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

		/**
		 * tests a 3 dimensional hypeercube, which is used for testBroadcastNodeStateChange
		 */
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

			n0.setNodeState(SlipperySlope.getSingleton());
			n1.setNodeState(SlipperySlope.getSingleton());
			n2.setNodeState(SlipperySlope.getSingleton());
			n3.setNodeState(Insertable.getSingleton());
			n4.setNodeState(SlipperySlope.getSingleton());
			n5.setNodeState(SlipperySlope.getSingleton());
			n6.setNodeState(SlipperySlope.getSingleton());
			n7.setNodeState(Insertable.getSingleton());
		}

		/**
		 * tests a 2 dimensional hypeerweb
		 */
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
