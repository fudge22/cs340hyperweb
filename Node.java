package node;

import java.util.List;


public class Node {
	
	private int webID;
	private int height;
	private int fold;
	private int surrogateFold;
	private int invSurrogateFold;
	private List<Integer> neighbors;
	private List<Integer> surNeighbors;
	
	public Node(int webID, int height, int fold, int surrogateFold, int invSurrogateFold, List<Integer> neighbors, List<Integer> surNeighbors) {
		this.webID = webID;
		this.height = height;
		this.fold = fold;
		this.surrogateFold = surrogateFold;
		this.invSurrogateFold = invSurrogateFold;
		this.neighbors = neighbors;
		this.surNeighbors = surNeighbors;
				
	}
	
	public Node(int webID) {
		this.webID = webID;
		this.height = -1;
		this.fold = -1;
		this.surrogateFold = -1;
		this.invSurrogateFold = -1;
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

	public int getFold() {
		return fold;
	}

	public void setFold(int fold) {
		this.fold = fold;
	}

	public int getSurrogateFold() {
		return surrogateFold;
	}

	public void setSurrogateFold(int surrogateFold) {
		this.surrogateFold = surrogateFold;
	}

	public int getInvSurrogateFold() {
		return invSurrogateFold;
	}

	public void setInvSurrogateFold(int invSurrogateFold) {
		this.invSurrogateFold = invSurrogateFold;
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
