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
import states.*;
import visitor.BroadcastVisitor;
import visitor.Parameters;
import visitor.SendVisitor;

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
	private NodeState nodeState;
	private FoldState foldState;
	
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

	public NodeState getNodeState() {
		return nodeState;
	}
	
	public void setNodeState(NodeState nodeState) {
		this.nodeState = nodeState;
	}

	public int getNodeStateInt() {
		return nodeState.getNodeStateInt();
	}
	
	@Override
	public boolean equals(Object o) {
		Node other = (Node) o;
		return this.webID.equals(other.webID);
	}

	public static void initialize() {
		nodes = new HashMap<WebID, Node>();
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

	/* finds all of the inverse surrogate neighbors and deletes their surrogate
	// neighbors
	// and then deletes the inverse surrogate neighbors
	/ This is used when a child is inserted
    */
	public void informInvSurNeighbors(Node child) {
		for (WebID w : invSurNeighbors) {
			getNode(w).surNeighbors.remove(this.getWebID());
		}
		invSurNeighbors.clear();
	}

	public void addInvSurNeighbor(WebID surNeighbor) {
		this.invSurNeighbors.add(surNeighbor);
	}

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

	public void increaseHeight() {
		this.height++;
	}

	/*
	 * There is a condition that exists when a node is inserted into a hyperweb
	 * and that node fills a hole, meaning that all of the nodes neighbors
	 * already exist, in which the nodes neighbors and the nodes neighbors
	 * neighbors will be in a slippery slope state prior to the insert. Once the
	 * node is inserted and fills that hole, that node, its neighbors, and its
	 * neighbors neighbors will then all need to be in the insertable state.
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

	public HashSet<Node> getAll2Hops() {
		Node neighbor, nNeighbor;
		HashSet<Node> visited = new HashSet<Node>();

		// for each neighbor
		for (WebID neighborID : this.neighbors) {
			neighbor = getNode(neighborID);
			// we don't want to check neighbors with higher heights than us, causes loops
			if (neighbor.getHeight() > this.height) {
				continue;
			}
			visited.add(neighbor);
			// for each neighbors neighbor
			for (WebID nNeighborID : neighbor.neighbors) {
				if (!nNeighborID.equals(this.webID)) {
					nNeighbor = getNode(nNeighborID);
					visited.add(nNeighbor);
				}
			}
		}
		return visited;
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

		parentNode = new WebID(this.webID.getValue() ^ ((int) Math.pow(2, this.height - 1)));

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
	
	@SuppressWarnings("unused")
	private static void debugInfo(String info) {
		if (true) {
			System.out.println(info);
		}
	}

	private static void printHyperWeb() {
		if (debug) {
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
	}

	public void addNeighbor(WebID neighborId) {
		this.neighbors.add(neighborId);
	}
	
	private static WebID getNearNode(WebID bigNumber, WebID startID){
		WebID start = startID;
		int highest = start.getNumberBitsInCommon(bigNumber);
	
		boolean reached = false;
		
		while (!reached){
			HashSet<WebID> relations = new HashSet<WebID>();
					
			Node check = Node.getNode(start);
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
	
	public static Node addToHyperWeb() {
		Random generator = new Random();
		int randomInsertionPoint = generator.nextInt(2147483647);
		
		
		WebID insertPointID = null;
		
		for (WebID id : nodes.keySet()) {
			
				insertPointID = id;
				break;
			
		}
		
		Node insertPoint  = Node.getNode(getNearNode(new WebID( randomInsertionPoint),insertPointID));

		//System.out.println("Trying to insert at " + insertPoint.getWebId());
		insertPoint.getNodeState().addToNode(insertPoint);
		
		return nodes.get(0);
	}
	
	public static Node getRandomNode() {
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
		return insertPoint;
	}

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
	 * The function called by the HyperWeb that will removed a node from the hyperWeb
	 * we assume that the hyperweb is not empty when we call the getNode function, as
	 * we should not be able to remove from an empty hyperweb
	 * 
	 * there will be special cases if the highest node available to be disconnected has
	 * a webID of either one or zero
	 */
	public static void removeNode() {
		Node removeNode = getRandomNode();
		
		Node disconnectedNode = deleteNode(removeNode);
		if (disconnectedNode.getWebId() == 1) {
			return;
		} else if (disconnectedNode.getWebId() == 0) {
			emptyHyperWeb();
		} else {
			
		}
	}
	
	private static Node deleteNode(Node removeNode) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void loadHyperWeb(HashMap<WebID, Node> loadHyperWeb) {
		nodes = Database.getInstance().getDatabaseAccessor().loadHyperWeb();

	}

	// assume this.height == the new height of the child
	// in other words, the parent's height has already been incremented
	public WebID getChildNodeID() {
		return new WebID(((int) Math.pow(2, this.height - 1)) | webID.getValue());
	}

	public WebID getParentNodeID() {
		return new WebID(this.webID.getValue() ^ Integer.highestOneBit(this.webID.getValue()));
	}

	
	/*
	 * "State Checker" Methods
	 * --------------------------------------------------------------
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

	public List<WebID> checkLowerNeighbors() {
		ArrayList<WebID> lowerNeighborsList = new ArrayList<WebID>();

		for (WebID neighborID : neighbors) {
			if (getNode(neighborID).height < this.height) {
				lowerNeighborsList.add(neighborID);
			}
		}

		return lowerNeighborsList;
	}
	
	public List<WebID> checkHigherNeighbors() {
		ArrayList<WebID> higherNeighborsList = new ArrayList<WebID>();

		for (WebID neighborID : neighbors) {
			if (getNode(neighborID).height > this.height) {
				higherNeighborsList.add(neighborID);
			}
		}

		return higherNeighborsList;
	}


	public Node findLowerNode() {
		Node lowerNode = null;
		// check this node first
		if (this.surNeighbors.size() > 0) {

			lowerNode = getNode(this.surNeighbors.get(0));

		} else if (this.surrogateFoldID != null) {
			lowerNode = getNode(this.surrogateFoldID);

		} else if (this.checkLowerNeighbors().size() > 0) {

			lowerNode = getNode(this.checkLowerNeighbors().get(0));
		}
		return lowerNode;
	}
	
	public Node findHigherNode() {
		Node higherNode = null;
		// check this node first
		if (this.invSurNeighbors.size() > 0) {

			higherNode = getNode(this.invSurNeighbors.get(0));

		} else if (this.invSurrogateFoldID != null) {
			higherNode = getNode(this.invSurrogateFoldID);

		} else if (this.checkHigherNeighbors().size() > 0) {

			higherNode = getNode(this.checkHigherNeighbors().get(0));
		}
		return higherNode;
	}


	/**
	 * Function that will send a command from one node to another taking the shorest path between
	 * the two nodes. The Send First Node checks if the fold or inverse surrogate fold is closer
	 * to the endNode. If it is not, then it will only look though the neighbors of the start node.
	 * 
	 * We assume that there is an end node and a start node inside the hyperweb. If given a node that
	 * is not in the hyperweb, we return a null and say that there is no node and the package can't be delivered
	 * 
	 * 
	 * @param startNone	the node that we are sending the command from
	 * @param endNode	the node that will be recieving the command from the start node 
	 */
	public Node sendFirstNode(WebID endNode) {
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
		int commonFoldID = foldID.getNumberBitsInCommon(endNode);
		int commonNeighborID = neighborID.getNumberBitsInCommon(endNode);
		
		if(commonNeighborID > commonFoldID) {
			//go through the neighbors
			return this;
		} else {
			//go to the fold and then go through the neighbors
			return getNode(foldID);
		}
	}
	
	/**
	 * The function that will get closer to the end node by hopping through the hyperweb. 
	 * we first check if the start node is the end node (this meaning that we have reached the 
	 * end node) and deliver the command
	 * 
	 * We then check all of the inverse surrogate neighbors for any neighbor which is closer to the end node.
	 * Each hop through an inverse surrogate neighbor will get us 2 digits closer
	 * 
	 * Next we check for closer neighbors, making a one step closer approach to the end node
	 * 
	 * finally we check our surrogate neighbors, but only if the other lists have nothing closer. We check for
	 * any nodes that are of equal distace to the end node as we are. 
	 *
	 * @param startNode
	 * @param endNode
	 */
	public Node sendNode(WebID endNode) {
		//Check to see if we have arrived at the end node
		System.out.println("Start node: " + this.getWebId());
		System.out.println("End node: " + endNode.toString());
		
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
			Node node = getNode(closestWebID);
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
			Node node = getNode(closestWebID);
			//sendNode(node, endNode);
			return node;
		}
		return null;
	}
	
	
	/*
	 * Unit Tests for Node Class methods
	 * --------------------------------------------------------------
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
		public void testIncreaseHeight() {
			Node n = new Node(new WebID(0), 1);
			int h = n.height;
			n.increaseHeight();
			assertEquals(h + 1, n.height);
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

			n0.setNodeState(SlipperySlope.getSingleton());
			n1.setNodeState(SlipperySlope.getSingleton());
			n2.setNodeState(SlipperySlope.getSingleton());
			n3.setNodeState(Insertable.getSingleton());
			n4.setNodeState(SlipperySlope.getSingleton());
			n5.setNodeState(SlipperySlope.getSingleton());
			n6.setNodeState(SlipperySlope.getSingleton());
			n7.setNodeState(Insertable.getSingleton());
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


	public void accept(SendVisitor sendVisitor) {
		sendVisitor.secondVisit(this);
	}
	
	public void accept(BroadcastVisitor broadcastVisitor, Parameters parameters) {
		
	}

	public Parameters getContents() {
		// TODO Auto-generated method stub
		return null;
	}
}
