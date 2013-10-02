package model;

public class StableFold extends FoldState  {
	
	StableFold() {
		
	}

	public void updateFold(Node node) {
		//Start by getting the child node
		Node child = Node.getNode(node.getWebId());// + some bitwise operations)
		
		//give the child the fold of the parent node
		child.setFoldID(node.getFoldID());
		
		//Change the fold of node's old fold to the child
		Node.getNode(node.getFoldID()).setFoldID(child.getWebId());
		
		//next set node's surrogate Fold to its old fold
		node.setSurrogateFoldID(node.getFoldID());
		
		//Change the Surrogatefold of node's old fold to node
		Node.getNode(node.getFoldID()).setInvSurrogateFoldID(node.getWebId());
		//setInverseSurrogateFold(node);
		
		//change the status of the old fold
		Node.getNode(node.getFoldID()).setFoldStatus(new UnstableISF());
		
		//change the fold status of node
		node.setFoldStatus(new UnstableSF());
		
		//Set my fold to null
		node.setFoldID(-1);
		
		return;
	}
}
