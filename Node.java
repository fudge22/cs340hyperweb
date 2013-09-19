package node;

import java.util.List;


public class Node {
	
	private int webID;
	private int height;
	private int foldID;
	private int surrogateFoldID;
	private int invSurrogateFoldID;
	private List<Integer> neighbors;
	private List<Integer> surNeighbors;
	
	public Node(int webID, int height, int foldID, int surrogateFoldID, int invSurrogateFoldID, List<Integer> neighbors, List<Integer> surNeighbors) {
		this.webID = webID;
		this.height = height;
		this.foldID = foldID;
		this.surrogateFoldID = surrogateFoldID;
		this.invSurrogateFoldID = invSurrogateFoldID;
		this.neighbors = neighbors;
		this.surNeighbors = surNeighbors;
				
	}
	
	public Node(int webID) {
		this.webID = webID;
		this.height = -1;
		this.foldID = -1;
		this.surrogateFoldID = -1;
		this.invSurrogateFoldID = -1;
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

	public List<Integer> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(List<Integer> neighbors) {
		this.neighbors = neighbors;
	}

	public List<Integer> getSurNeighbors() {
		return surNeighbors;
	}

	public void setSurNeighbors(List<Integer> surNeighbors) {
		this.surNeighbors = surNeighbors;
	}
	
	
	
	

}
