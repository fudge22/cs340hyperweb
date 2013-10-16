package states;

import model.Node;
import model.WebID;

public class StableFold extends FoldState {
	
	private static StableFold singleton = null;
	
	/**
	 * Returns a stable fold singleton for nodes to reference when inserting
	 * and removing nodes from the HyPeerWeb
	 * 
	 * @return the StableFold singleton
	 */
	public static FoldState getSingleton() {
		if (singleton == null) {
			singleton = new StableFold();
		}
		return singleton;
	}
	
	private StableFold() {};

	/**
	 * A method which is created for updating folds within the HyPeerWeb when a new node
	 * is added. We assume that the parent node is a node in a stableFoldState meaning it
	 * has a fold and no surrogate fold or inverse surrogate fold.
	 * 
	 * @param Parent	The node in the HyPeerWeb which is getting a new neighbor/child
	 * @param child 	a new node being added to the HyPeerWeb
	 */
	public void updateFoldOf(Node parent, Node child) {

		WebID childId = child.getWebID();
		WebID parentId = parent.getWebID();
		WebID parentsFoldId = parent.getFoldID();

		Node parentsFold = Node.getNode(parentsFoldId);

		// give the child the fold of the parent node
		child.setFoldID(parentsFoldId);
		child.setSurrogateFoldID(null);
		child.setInvSurrogateFoldID(null);

		// Change the fold of node's old fold to the child
		parentsFold.setFoldID(childId);

		// next set node's surrogate Fold to its old fold
		parent.setSurrogateFoldID(parentsFoldId);

		parentsFold.setInvSurrogateFoldID(parentId);
		// Change the Surrogate fold of node's old fold to node

		// change the status of the old fold
		parentsFold.setFoldState(UnstableISF.getSingleton());
		child.setFoldState(StableFold.getSingleton());

		// change the fold status of node
		parent.setFoldState(UnstableSF.getSingleton());
		// Set my fold to null
		parent.setFoldID(null);

		return;
	}
	
	/**
	 * Given an node to remove (node d), we will update d's folds with new fold WebIDs
	 * There are two different situations when considering the fold of d when it is in 
	 * a stable state: If the fold of d is in a stable state as well, or an inverse 
	 * surrogate fold state. 
	 * 
	 * The if statement at the very beginning is to check if the fold is in a Stable state
	 * or not. If the fold of d is not in a stable state, it goes into another function
	 * 
	 * The funciton then prepared the fold of d to forget about d and remember d's parent
	 * as the surrogate fold. 
	 * 
	 * @param d	the Node which is to be removed from the HyPeerWeb
	 */
	@Override
	public void removeFoldsOf(Node d) {
		
		
		if(((Node) d.getFold()).getFoldState() != StableFold.getSingleton()) {
			removeInvFolds(d);
		}
		
		//tell the fold that the fold is being removed
		Node fold = (Node) d.getFold();
		Node newSurrogateFold = (Node) d.getFold().getParent();
		
		//The fold of d is going to be removed and will get the parent of d as the surrogate fold
		fold.setFoldState(UnstableSF.getSingleton());
		fold.setFoldID(null);
		fold.setSurrogateFoldID(newSurrogateFold.getWebID());
		
		//the parent of d will get to know the old fold of d
		newSurrogateFold.setFoldState(UnstableISF.getSingleton());
		newSurrogateFold.setInvSurrogateFoldID(fold.getWebID());
	}
	
	/**
	 * The node that is to be deleted (node d) will tell its fold that d will be removed
	 * and to prepare accordingly.  
	 * 
	 * the fold of d (node dfold) and the inverse surrogate fold of dfold will then become
	 * each other's fold and d can be removed
	 * 
	 * @param d	The node that will be deleted from the HyPeerWeb
	 */
	private void removeInvFolds(Node d) {
		
		//d needs to cut all of it's connections and set it's fold to become folds with another node
		Node fold = (Node) d.getFold();
		Node invSurFold = (Node) d.getFold().getInverseSurrogateFold();
		
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
