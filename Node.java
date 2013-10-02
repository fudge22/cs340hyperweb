package node;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import database.Database;
import database.DatabaseException;
import simulation.NodeInterface;

public class Node implements NodeInterface {
	private static HashMap<Integer, Node> nodes;
	private int webID;

	private int height;
	private int foldID;
	private int surrogateFoldID;
	private int invSurrogateFoldID;
	int parent;
	private List<Integer> neighbors;
	private List<Integer> surNeighbors;
	private List<Integer> invSurNeighbors;
	private FoldState foldState;

	public Node(int webID, int height, int foldID, int surrogateFoldID,
			int invSurrogateFoldID, List<Integer> neighbors,
			List<Integer> surNeighbors, List<Integer> invSurNeighbors2,
			int foldState, int insertableState, int selfFlat,
			int neighborsFlat, int nofneighborsFlat) {
		this.webID = webID;
		this.height = height;
		this.foldID = foldID;
		this.surrogateFoldID = surrogateFoldID;
		this.invSurrogateFoldID = invSurrogateFoldID;
		this.neighbors = neighbors;
		this.surNeighbors = surNeighbors;
		this.invSurNeighbors = invSurNeighbors2;

		// flat is 0 or 1?

	}

	public static void initialize() {

		nodes = new HashMap<Integer, Node>();
	}

	public Node(int webID) {// create dummy Node
		this.webID = webID;
		this.height = -1;
		this.foldID = -1;
		this.surrogateFoldID = -1;
		this.invSurrogateFoldID = -1;
		// nodes.put(this.webID, this);// automatically insert node into map

	}

	public static Node getNode(int id) {
		return nodes.get(id);// if null, return null node
	}

	public int getWebID() {
		return webID;
	}

	public void setWebID(int webID) {
		this.webID = webID;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setParent(int webId) {
		parent = webId;
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

	// public List<Integer> getNeighbors() {
	// return neighbors;
	// }

	public void setNeighbors(List<Integer> neighbors) {
		this.neighbors = neighbors;
	}

	public List<Integer> getSurNeighbors() {
		return surNeighbors;
	}

	public void setSurNeighbors(List<Integer> surNeighbors) {
		this.surNeighbors = surNeighbors;
	}

	@Override
	public int getWebId() {

		return webID;
	}

	@Override
	public NodeInterface[] getSurrogateNeighbors() {

		NodeInterface[] surNeighborArray = new Node[surNeighbors.size()];
		int counter = 0;
		for (int x : surNeighbors) {
			surNeighborArray[counter] = getNode(x);
			counter++;
		}

		return surNeighborArray;
	}

	@Override
	public NodeInterface[] getInverseSurrogateNeighbors() {
		NodeInterface[] invSurNeighborArray = new Node[invSurNeighbors.size()];
		int counter = 0;
		for (int x : invSurNeighbors) {
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

	@Override
	public NodeInterface getParent() { // originally brian and I thought that
										// you couldn't store the parent
										// after though I have realized that the
										// parent can be stored, a node doesn't
										// know it's child
										// after all 1 01 001 0001 all are
										// represented by 1 and their parent is
										// 0

		return getNode(parent);
	}

	@Override
	public int compareTo(NodeInterface node) {
		// jamie - i am not sure if this completely works not, but I am assuming
		// so

		// compare both webid

		if (webID < node.getWebId())
			return 1;
		else if (webID < node.getWebId())
			return -1;
		else
			return 0;
	}

	@Override
	public NodeInterface[] getNeighbors() {

		NodeInterface[] neighborArray = new Node[neighbors.size()];
		int counter = 0;
		for (int x : neighbors) {
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
		Node addedNode = null;
		if (nodes.size() == 0) {

			addedNode = addToEmptyHyperWeb();// base case with no nodes in the
												// hyperweb

		} else if (nodes.size() == 1) {
			addedNode = addSecondNode();// base case with only one node in the
										// hyperweb

		} else {

			//do logic 
			
			
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

	
	public void addNeighbor(int neighborId) {

		this.neighbors.add(neighborId);
	}

	public static Node addToHyperWeb() {
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		ArrayList<Integer> surNeighbors = new ArrayList<Integer>();
		ArrayList<Integer> invSurNeighbors = new ArrayList<Integer>();
		//get parent id and check the state

		//getNode(0).getFoldState().addNode(); Is this what we are going for
		
		nodes.put(0, new Node(0, 0, -1, -1, -1, neighbors, surNeighbors,
				invSurNeighbors, 0, 0, 0, 0, 0));// we inserted an empty list
													// into surNeighbors so it
													// always exists in the
													// future
		nodes.get(0).setParent(-1); // it has no parent
		return nodes.get(0);
	}
	
	public static Node addToEmptyHyperWeb() {
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		ArrayList<Integer> surNeighbors = new ArrayList<Integer>();
		ArrayList<Integer> invSurNeighbors = new ArrayList<Integer>();
		// right now we have been using negative ids(well -1) to represent null
		// integers. This works well as long as we don't actually insert
		// them into the hashmap. The hashmap will always return a null for a
		// key it does not have.

		nodes.put(0, new Node(0, 0, -1, -1, -1, neighbors, surNeighbors,
				invSurNeighbors, 0, 0, 0, 0, 0));// we inserted an empty list
													// into surNeighbors so it
													// always exists in the
													// future
		nodes.get(0).setParent(-1); // it has no parent
		return nodes.get(0);
	}

	public static Node addSecondNode() {
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		ArrayList<Integer> surNeighbors = new ArrayList<Integer>();
		ArrayList<Integer> invSurNeighbors = new ArrayList<Integer>();
		neighbors.add(0);

		nodes.put(1, new Node(1, 1, 0, -1, -1, neighbors, surNeighbors,
				invSurNeighbors, 0, 0, 0, 0, 0));
		nodes.get(0).setHeight(1);
		nodes.get(1).setHeight(1);
		nodes.get(0).addNeighbor(1);
		nodes.get(0).setFoldID(1);
		nodes.get(1).setParent(0);

		return nodes.get(1);

	}

	public static void loadHyperWeb(HashMap<Integer, Node> loadHyperWeb) {
		nodes = Database.getInstance().getDatabaseAccessor().loadHyperWeb();

	}

	public void setFoldStatus(FoldState fstate) {

		foldState = fstate;
	}

	public int getChildNodeID() {

		return (1 << height) | webID;
	}
	public FoldState getFoldState(){
		return foldState;
	
	}

}
