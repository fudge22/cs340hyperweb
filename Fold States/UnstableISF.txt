package model;

//Unstable Inverse Surrogate Fold
//this naming convention beacuse the node would have a fold and an inverse surrogate fold
public class UnstableISF extends FoldState {

	UnstableISF() {
		
	}
	
	/*
	 * In this function, we assume that the node being updated has had a child added and that the 
	 * 	node has both a fold and an inverse surrogate fold
	 */
	public void UpdateFold(Node node) {
		Node child = Node.getNode(node.getChildNodeID());
		
		//make the fold of the child the inverse surrogate fold of node
		child.setFoldID(node.getInvSurrogateFoldID());
		
		//tell node's inverse surrogate fold that it has a new fold
		Node.getNode(node.getInvSurrogateFoldID()).setFoldID(child.getWebId());
		
		//tell node's inverse surrogate fold to remove its surrogate fold
		Node.getNode(node.getInvSurrogateFoldID()).setSurroagetFold(-1);
		
		//tell node's inverse surrogate fold to update its foldState
		Node.getNode(node.getInvSurrogateFoldID()).setFoldStatus(new StableFold()); 
		
		//make node forget it's inverse surrogate fold
		node.setInverseSurrogateFold(-1);
		
		//update node's state to stable
		node.setFoldStatus(new StableFold());
		
	}
}
