
public class Node {
	
	private int webID;
	private int height;
	private Node fold;
	private Node surrogateFold;
	private Node invSurrogateFold;
	
	public Node(int webID, int height, Node fold, Node surrogateFold, Node invSurrogateFold) {
		this.webID = webID;
		this.height = height;
		this.fold = fold;
		this.surrogateFold = surrogateFold;
		this.invSurrogateFold = invSurrogateFold;
	}
	
	public Node(int webID) {
		this.webID = webID;
		this.height = -1;
		this.fold = null;
		this.surrogateFold = null;
		this.invSurrogateFold = null;
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

	public Node getFold() {
		return fold;
	}

	public void setFold(Node fold) {
		this.fold = fold;
	}

	public Node getSurrogateFold() {
		return surrogateFold;
	}

	public void setSurrogateFold(Node surrogateFold) {
		this.surrogateFold = surrogateFold;
	}

	public Node getInvSurrogateFold() {
		return invSurrogateFold;
	}

	public void setInvSurrogateFold(Node invSurrogateFold) {
		this.invSurrogateFold = invSurrogateFold;
	}
	
	

}
