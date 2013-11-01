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
		
		// make sure child has no surFold or invSurFold
		WebID childSurFoldID = child.getSurrogateFoldID();
		if (childSurFoldID != null) {
			child.removeSurFold(childSurFoldID);
		}
		
		WebID childInvSurFoldID = child.getInvSurrogateFoldID();
		if (childInvSurFoldID != null) {
			child.removeInvSurFold(childInvSurFoldID);
		}

		// Change the fold of parents's old fold to the child
		parentsFold.setFoldID(childId);

		// next set parents's surrogate Fold to its old fold
		parent.addSurFold(parentsFoldId);

		// Change the inv surrogate fold of parent's old fold to parent
		parentsFold.addInvSurFold(parentId);

		// change the status of the old fold
		parentsFold.setFoldState(UnstableISF.getSingleton());
		child.setFoldState(StableFold.getSingleton());

		// change the fold status of parent
		parent.setFoldState(UnstableSF.getSingleton());
		
		// Set parent fold to null
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
		

	
			Node fold = (Node) d.getFold();
			Node parent = (Node) d.getParent();
			
			
			
			if (parent.getFoldState().equals(StableFold.getSingleton())){
				
				if (d.getWebID().equals(new WebID(1)))
				{
				    fold.setFoldID(null);
				    
					return;
				}
				
				if (d.getWebID().equals(new WebID(0)))
				return;
				
			
				parent.addInvSurFold(fold.getWebID());
				fold.setFoldID(null);
				fold.addSurFold(parent.getWebID());
				parent.setFoldState(UnstableISF.getSingleton());
				((Node)parent.getInverseSurrogateFold()).setFoldState(UnstableSF.getSingleton());
				
				
				
			}
			else if (parent.getFoldState().equals(UnstableSF.getSingleton())) //should always be either stable or unstable surrogate fold state
			{
				parent.setFoldID(parent.getSurrogateFoldID());
				parent.removeSurFold(parent.getSurrogateFoldID());
				Node parentFold = Node.getNode(parent.getFoldID());
				parentFold.setFoldID(parent.getWebID());
				parentFold.removeInvSurFold(parentFold.getInvSurrogateFoldID());
				parent.setFoldState(StableFold.getSingleton());
				parentFold.setFoldState(StableFold.getSingleton());
			} else if (parent.getFoldState().equals(UnstableISF.getSingleton())) {
				parent.setFoldID(d.getFoldID());
				fold.setFoldID(parent.getWebID());
				fold.removeInvSurFold(fold.getInvSurrogateFoldID());
				
				
				
				System.err.println("should always be either stable or unstable surrogate fold state?");
			
			
			
			
			}
			else{
				System.err.println("this is really impossible");

			}
			
			
		
		
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
		Node invSurFold = (Node) fold.getInverseSurrogateFold();
		
		invSurFold.setFoldID(fold.getWebID());
		
		WebID surFoldToRemove = invSurFold.getSurrogateFoldID();
		if (surFoldToRemove != null) {
			invSurFold.removeSurFold(surFoldToRemove);
		}
		
		fold.setFoldID(invSurFold.getWebID());
		WebID invSurFoldToRemove = fold.getInvSurrogateFoldID();
		if (invSurFoldToRemove != null) {
			fold.removeSurFold(invSurFoldToRemove);
		}
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
