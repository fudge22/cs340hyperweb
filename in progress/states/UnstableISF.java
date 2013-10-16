package states;

import model.Node;
import model.WebID;

//Unstable Inverse Surrogate Fold
// this naming convention because the node would have a fold and an inverse surrogate fold
public class UnstableISF extends FoldState {
	
	private static UnstableISF singleton = null;
	
	/**
	 * Return a reference to the inverse surrogate fold singleton that will be used 
	 * when updating and removing nodes from the HyPeerWeb
	 * 
	 * @return a reference to the inverse Surrogate fold singleton
	 */
	public static FoldState getSingleton() {
		if (singleton == null) {
			singleton = new UnstableISF();
		}
		return singleton;
	}
	
	private UnstableISF() {};

	/**
	 * In this function, we assume that the node being updated has had a
	 * child added and that the node has both a fold and an inverse
	 * surrogate fold
	 * 
	 * @param parent	The node that is getting a new neighbor/child
	 * @param child		The new node being inserted to the HyPeerWeb
	 */
	public void updateFoldOf(Node parent, Node child) {
		WebID childId = child.getWebID();

		// make the fold of the child the inverse surrogate fold of node
		child.setFoldID(parent.getInvSurrogateFoldID());
		child.setSurrogateFoldID(null);
		child.setInvSurrogateFoldID(null);

		// tell node's inverse surrogate fold that it has a new fold
		Node.getNode(parent.getInvSurrogateFoldID()).setFoldID(childId);

		// tell node's inverse surrogate fold to remove its surrogate fold
		Node.getNode(parent.getInvSurrogateFoldID()).setSurrogateFoldID(null);

		// tell node's inverse surrogate fold to update its foldState
		Node.getNode(parent.getInvSurrogateFoldID()).setFoldState(StableFold.getSingleton());

		// make node forget it's inverse surrogate fold
		parent.setInvSurrogateFoldID(null);

		// update node's state to stable
		parent.setFoldState(StableFold.getSingleton());
		child.setFoldState(StableFold.getSingleton());

	}

	/**
	 * I am not sure how to do this, I have some good pictures of possibilities, but not much else
	 * 
	 * @param d	The node to be removed
	 */
	@Override
	public void removeFoldsOf(Node d) {
		
		
	}
	/**
	 * returns an integer value of this type of fold state which should always be two
	 * For use when adding a node to a database or getting info from the database
	 * 
	 * @return	2, being the integer value associated with this node state
	 */
	@Override
	public int getFoldStateInt() {
		return 2;
	}
}
