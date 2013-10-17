package states;

import model.Node;
import model.WebID;
import exceptions.WebIDException;

public class UnstableSF extends FoldState {
	
	private static UnstableSF surFoldSingleton = null;
	
	/**
	 * returns a reference to the surroagte fold singleton to be used by nodes when
	 * adding and removing from the HyPeerWeb
	 * 
	 * @return a reference to the surrogate fold Singelton
	 */
	public static  UnstableSF getSingleton() {
		if(surFoldSingleton == null) {
			surFoldSingleton = new UnstableSF();
		}
		return surFoldSingleton;
	}
	
	/**
	 * @see node.FoldState#updateFold(node.Node) In this function, we know
	 * that the node does not have a fold but does instead have an surrogate
	 * fold. We want to make the HyperWeb as effecient as possible, thus
	 * when we get to this node, we see that there is a hole and we can
	 * insert here as the node's fold
	 * 
	 * The purpose here now is to add a fold to node instead of a surrogate
	 * fold
	 * 
	 * @param parent	The node that is getting a new neighbor/child
	 * @param child 	A new node being added to the HyPeerWeb
	 */
	public void updateFoldOf(Node parent, Node child) throws WebIDException {
		WebID childID = child.getWebID();

		// set the fold of node to the child
		parent.setFoldID(childID);

		// set the fold of the child to this node
		child.setFoldID(parent.getWebID());

		// tell the surrogate fold of node to forget the inverse surrogate fold
		Node.getNode(parent.getSurrogateFoldID()).setInvSurrogateFoldID(
				null);

		// change the state of node's surrogate fold
		Node.getNode(parent.getSurrogateFoldID()).setFoldState(
				StableFold.getSingleton());

		// have node forget its surrogate fold
		parent.setSurrogateFoldID(null);

		// update node's status
		parent.setFoldState(StableFold.getSingleton());
		child.setFoldState(StableFold.getSingleton());

		return;
	}

	
	/**
	 * This function assumes that the node being deleted has a surrogate fold but no fold and no
	 * Inverse Surrogate fold. It also assumes that the replacement node is not null, as we will always
	 * look for a higher node to replace the node with.
	 * 
	 * The replacement node will grab all of the information from the node that is to be deleted
	 * 
	 * In such a case where the replacement node is not null, the node to be removed sends over the data
	 * about its different folds then goes off to die. The WebID of the deletion node should have already
	 * been added to the replacement node. 
	 * 
	 * @param deletion		the node that will be removed
	 * @param replacement	the node that will replace the deletion node
	 */
	@Override
	public void removeFoldsOf(Node deletion, Node replacement) {
		
		/*
		if(replacement == null) {
			Node surrogateFold = (Node) deletion.getSurrogateFold();
			surrogateFold.setFoldStatus(StableFold.getStableSingleton());
			surrogateFold.setInvSurrogateFoldID(null);
			deletion.setSurrogateFoldID(null);
		
		} 
		*/
		
		if(replacement != null) {
			replacement.getFoldState().removeFoldsOf(replacement, null);
			replacement.setFoldID(deletion.getFoldID());
			replacement.setSurrogateFoldID(deletion.getSurrogateFoldID());
			replacement.setInvSurrogateFoldID(deletion.getInvSurrogateFoldID());
			replacement.setFoldState(deletion.getFoldState());
		} else {
			//throw an error
		}
		
	}
	
	/**
	 * returns an intiger value of this type of fold state which should always be one
	 * For use when adding a node to a database or getting info from the database
	 * 
	 * @return	1, being the integer value associated with this node state
	 */
	@Override
	public int getFoldStateInt() {
		return 1;
	}

}
