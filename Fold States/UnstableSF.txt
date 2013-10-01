package model;

public class UnstableSF extends FoldState {
	
	UnstableSF() {
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see node.FoldState#updateFold(node.Node)
	 * In this function, we know that the node does not have a fold but does instead have an 
	 * 	surrogate fold. We want to make the HyperWeb as effecient as possible, thus when we
	 * 	get to this node, we see that there is a hole and we can insert here as the node's
	 * 	fold 
	 * 
	 * The purpose here now is to add a fold to node instead of a surrogate fold
	 */
	public void updateFold(Node node) {
		
		 //get the child of the surrogate fold of node
		 int childID = Node.getNode(node.getSurrogateFoldID()).getChildNodeID(); /*<- get child would be a bitwise 
		 function	that would append the 1 to the front of the binary representation of 
		 the surrogate fold*/
		  
		 //set the fold of node to the child
		 node.setFoldID(childID);
		  
		 //set the fold of the child to this node
		 Node.getNode(childID).setFoldID(node.getWebId());
		  
		 //tell the surrogate fold of node to forget the inverse surrogate fold
		 Node.getNode(node.getSurrogateFoldID()).setInverseSurrogateFold(-1);
		 
		 //change the state of node's surrogate fold
		 Node.getNode(node.getSurrogateFoldID()).setFoldStatus(new StableFold());
		  
		 //have node forget its surrogate fold
		 node.setSurrogateFoldID(-1);
		 
		 //update node's status
		 node.setFoldStatus(new StableFold());
		 
		return;
	}

}
