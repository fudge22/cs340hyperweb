package model;

import java.util.HashMap;
import java.util.List;

import simulation.NodeInterface;

public class Node implements NodeInterface {
	private static HashMap<Integer, Node> nodes;
	private int webID;
	private int parentID;
	private int height;
	private int foldID;
	private int surrogateFoldID;
	private int invSurrogateFoldID;
	private List<Integer> neighbors;
	private List<Integer> surNeighbors;

	public Node(int webID, int height, int foldID, int surrogateFoldID,
			int invSurrogateFoldID, List<Integer> neighbors,
			List<Integer> surNeighbors) {
		this.webID = webID;
		this.height = height;
		this.foldID = foldID;
		this.surrogateFoldID = surrogateFoldID;
		this.invSurrogateFoldID = invSurrogateFoldID;
		this.neighbors = neighbors;
		this.surNeighbors = surNeighbors;
		//nodes.put(this.webID, this); automatically insert node into map

	}
	
	public static void initialize(){
		
		nodes = new HashMap<Integer, Node>();
	}

	public Node(int webID) {// create dummy Node
		this.webID = webID;
		this.height = -1;
		this.foldID = -1;
		this.surrogateFoldID = -1;
		this.invSurrogateFoldID = -1;
		//nodes.put(this.webID, this);// automatically insert node into map

	}

	public static  Node getNode(int id) {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeInterface[] getInverseSurrogateNeighbors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeInterface getFold() {
		

		return getNode(foldID);
		// return null;// foldID;//use hash set to get
	}

	@Override
	public NodeInterface getSurrogateFold() {
		// TODO Auto-generated method stub
		return getNode(surrogateFoldID);
		// return null;
	}

	@Override
	public NodeInterface getInverseSurrogateFold() {
	
		return getNode(invSurrogateFoldID);
		
	}

	@Override
	public NodeInterface getParent() {
		// TODO Auto-generated method stub
		return getNode(parentID);
		// return null;
	}

	@Override
	public int compareTo(NodeInterface node) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return null;
	}
	public static Node[] allNodes(){
		
		
		return (Node[]) nodes.values().toArray();
		
		
	}

	public static void addNode(int webId) {
		
		
	}

}
