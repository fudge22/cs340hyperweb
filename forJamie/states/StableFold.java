package states;

import model.Node;
import model.WebID;
import exceptions.WebIDException;

public class StableFold extends FoldState {

	private static StableFold stableFold = null;
	
	/**
	 * Returns a stable fold singleton for nodes to reference when inserting
	 * and removing nodes from the HyPeerWeb
	 * 
	 * @return the stableFold singleton
	 */
	public static StableFold getSingleton() {
		if(stableFold == null) {
			stableFold = new StableFold();
		}
		return stableFold;
	}
	
	/**
	 * A method which is created for updating folds within the HyPeerWeb when a new node
	 * is added. We assume that the parent node is a node in a stableFoldState meaning it
	 * has a fold and no surrogate fold or inverse surrogate fold.
	 * 
	 * @param Parent	The node in the HyPeerWeb which is getting a new neighbor/child
	 * @param child 	a new node being added to the HyPeerWeb
	 */
	public void updateFoldOf(Node parent, Node child) throws WebIDException {

		WebID childId = child.getWebID();
		WebID parentId = parent.getWebID();
		WebID parentsFoldId = parent.getFoldID();
		
		Node parentsFold = Node.getNode(parentsFoldId);
		
		if(parentsFoldId == null) {
			System.err.print("null parent");
//			System.out.println("childid: " + childId.getValue());
//			System.out.println("parentid: " + parentId.getValue());
		}

//		System.out.println("childid: " + childId.getValue());
//		System.out.println(" parentid: " + parentId.getValue());
		
		// give the child the fold of the parent node
		child.setFoldID(parentsFoldId);
		child.setSurrogateFoldID(null);
		child.setInvSurrogateFoldID(null);

		// Change the fold of node's old fold to the child
		parentsFold.setFoldID(childId);

		// next set node's surrogate Fold to its old fold
		parent.setSurrogateFoldID(parentsFoldId);

		parentsFold.setInvSurrogateFoldID(parentId);
		// Change the Surrogatefold of node's old fold to node

		// change the status of the old fold
		parentsFold.setFoldState(UnstableISF.getSingleton());
		child.setFoldState(getSingleton());

		// change the fold status of node

		parent.setFoldState(UnstableSF.getSingleton());
		// Set my fold to null
		parent.setFoldID(null);

		return;
	}

	
	/**
	 * Given an node to remove (node deletion), we will update deletion's folds with new fold WebIDs
	 * There are two different situations when considering the fold of deletion when it is in 
	 * a stable state: If the fold of deletion is in a stable state as well, or an inverse 
	 * surrogate fold state. 
	 * 
	 * The if statement at the very beginning is to check if the fold is in a Stable state
	 * or not. If the fold of deletion is not in a stable state, it goes into another function
	 * 
	 * The funciton then prepared the fold of deletion to forget about deletionand remember d's parent
	 * as the surrogate fold. 
	 * 
	 * @param deletion		the Node which is to be removed from the HyPeerWeb
	 * @param replacement	the Node that will replace the deletion node
	 */
	@Override
	public void removeFoldsOf(Node deletion, Node replacement) {
		
		//tell the fold that the fold is being removed
		Node fold = (Node) deletion.getFold();
		Node newSurrogateFold = (Node) deletion.getFold().getParent();
		
		//if the Replacement node is null, we need to tell the folds that 
		//the deletion node will be leaving
		if(replacement == null) {
			
			if(((Node) deletion.getFold()).getFoldState() != getSingleton()) {
				removeInvFolds(deletion);
			}
			
			//The fold of deletion is going to be removed and will get the parent of deletion as the surrogate fold
			fold.setFoldState(UnstableSF.getSingleton());
			fold.setFoldID(null);
			fold.setSurrogateFoldID(newSurrogateFold.getWebID());
			
			//the parent of deletion will get to know the old fold of d
			newSurrogateFold.setFoldState(UnstableISF.getSingleton());
			newSurrogateFold.setInvSurrogateFoldID(fold.getWebID());
		} else {
			//inform the folds of the replacement node that it is leaving
			replacement.getFoldState().removeFoldsOf(replacement, null);
			
			//all the fold info from the deleted node will replace the fold data in the replacement node
			replacement.setFoldID(deletion.getFoldID());
			replacement.setSurrogateFoldID(deletion.getSurrogateFoldID());
			replacement.setInvSurrogateFoldID(deletion.getInvSurrogateFoldID());
			replacement.setFoldState(deletion.getFoldState());
				
		}
	}
	
	/**
	 * The node that is to be deleted (node deletion) will tell its fold that deletion will be removed
	 * and to prepare accrdingly.  
	 * 
	 * the fold of deletion (node deletionfold) and the inverse surrogate fold of dfold will then become
	 * each other's fold and deletion can be removed
	 * 
	 * @param deletion	The node that will be deleted from the HyPeerWeb
	 */
	private void removeInvFolds(Node deletion) {
		
		//d needs to cut all of it's connections and set it's fold to become folds with another node
		Node fold = (Node) deletion.getFold();
		Node invSurFold = (Node) deletion.getFold().getInverseSurrogateFold();
		
		invSurFold.setFoldID(fold.getWebID());
		invSurFold.setSurrogateFoldID(null);
		
		fold.setFoldID(invSurFold.getFoldID());
		fold.setInvSurrogateFoldID(null);
	}

	/**
	 * returns an intiger value of this type of fold state which should always be zero
	 * For use when adding a node to a database or getting info from the database
	 * 
	 * @return	0, being the integer value associated with this node state
	 */
	@Override
	public int getFoldStateInt() {
		return 0;
	}
	
}
