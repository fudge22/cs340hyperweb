package states;

import model.Node;
import model.WebID;

public class UnstableSF extends FoldState {
	
	private static UnstableSF singleton = null;
	
	/**
	 * returns a reference to the surrogate fold singleton to be used by nodes when
	 * adding and removing from the HyPeerWeb
	 * 
	 * @return a reference to the surrogate fold Singleton
	 */
	public static FoldState getSingleton() {
		if (singleton == null) {
			singleton = new UnstableSF();
		}
		return singleton;
	}
	
	private UnstableSF() {};
	
	/**
	 * @see node.FoldState#updateFold(node.Node) In this function, we know
	 * that the node does not have a fold but does instead have an surrogate
	 * fold. We want to make the HyperWeb as efficient as possible, thus
	 * when we get to this node, we see that there is a hole and we can
	 * insert here as the node's fold
	 * 
	 * The purpose here now is to add a fold to node instead of a surrogate
	 * fold
	 * 
	 * @param parent	The node that is getting a new neighbor/child
	 * @param child 	A new node being added to the HyPeerWeb
	 */
	public void updateFoldOf(Node parent, Node child) {
		// set the parent's fold to the child
		parent.setFoldID(child.getWebID());

		// set the child's fold to the parent
		child.setFoldID(parent.getWebID());
		
		// tell the surrogate fold of parent to remove their inverse surrogate fold
		Node parentSurFold = Node.getNode(parent.getSurrogateFoldID());
		WebID invSurFoldToRemove = parentSurFold.getInvSurrogateFoldID();
		if (invSurFoldToRemove != null) {
			parentSurFold.removeInvSurFold(invSurFoldToRemove);
		}

		// change the state of parent's surrogate fold
		parentSurFold.setFoldState(StableFold.getSingleton());

		// have parent remove its surrogate fold
		WebID surFoldToRemove = parent.getSurrogateFoldID();
		if (surFoldToRemove != null) {
			parent.removeSurFold(surFoldToRemove);
		}

		// update parent's status
		parent.setFoldState(StableFold.getSingleton());
		child.setFoldState(StableFold.getSingleton());

		return;
	}

	/**
	 * This function assumes that the node being deleted has a surrogate fold but no fold and no
	 * Inverse Surrogate fold. In such a case, the node to be removed (node d) only needs to tell
	 * its surrogate fold that it no longer has a surrogate fold, and to change its status to a 
	 * stable fold. 
	 * 
	 * @param d	the node that will be removed
	 */
	@Override
	public void removeFoldsOf(Node d) {
		Node surrogateFold = (Node) d.getSurrogateFold();
		surrogateFold.setFoldState(StableFold.getSingleton());
		surrogateFold.setInvSurrogateFoldID(null);
		
		d.setSurrogateFoldID(null);
	}
	
	/**
	 * returns an integer value of this type of fold state which should always be one
	 * For use when adding a node to a database or getting info from the database
	 * 
	 * @return	1, being the integer value associated with this node state
	 */
	@Override
	public int getFoldStateInt() {
		return 1;
	}

}
