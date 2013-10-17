package states;

import model.Node;
import model.WebID;
import exceptions.WebIDException;

public class UnstableISF extends FoldState {

	private static UnstableISF invSurFoldSingleton = null;
	
	
	/**
	 * Return a reference to the inverse surrogate fold singleton that will be used 
	 * when updating and removing nodes from the HyPeerWeb
	 * 
	 * @return a reference to the inverse Surrogate fold singleton
	 */
	public static UnstableISF getSingleton() {
		if(invSurFoldSingleton == null) {
			invSurFoldSingleton = new UnstableISF();
		}
		return invSurFoldSingleton;
	}
	
	/**
	 * In this function, we assume that the node being updated has had a
	 * child added and that the node has both a fold and an inverse
	 * surrogate fold
	 * 
	 * @param parent	The node that is getting a new neighbor/child
	 * @param child		The new node being inserted to the HyPeerWeb
	 */
	public void updateFoldOf(Node parent, Node child) throws WebIDException {
		WebID childId = child.getWebID();
		WebID parentId = parent.getWebID();

		// make the fold of the child the inverse surrogate fold of node
		child.setFoldID(parent.getInvSurrogateFoldID());
		child.setSurrogateFoldID(null);
		child.setInvSurrogateFoldID(null);

//		System.out.println("childid: " + childId.getValue());
//		System.out.println(" parentid: " + parentId.getValue());

		// tell node's inverse surrogate fold that it has a new fold
		Node.getNode(parent.getInvSurrogateFoldID()).setFoldID(childId);

		// tell node's inverse surrogate fold to remove its surrogate fold
		Node.getNode(parent.getInvSurrogateFoldID()).setSurrogateFoldID(
				null);

		// tell node's inverse surrogate fold to update its foldState
		Node.getNode(parent.getInvSurrogateFoldID()).setFoldState(
				new StableFold());

		// make node forget it's inverse surrogate fold
		parent.setInvSurrogateFoldID(null);

		// update node's state to stable
		parent.setFoldState(StableFold.getSingleton());
		child.setFoldState(StableFold.getSingleton());

	}

	/**
	 * The only way in which this funcion is to be called is when there is a replacement
	 * node that is not null. The HyPeerWeb should not be able to delete a node that has
	 * an inverse surrogate fold as it will have a higher node to replace itself with
	 * 
	 * @param deletion		The node to be removed
	 * @param replacement	the node that will replace the deleted node
	 */
	@Override
	public void removeFoldsOf(Node deletion, Node replacement) {
		if(replacement != null) {
			replacement.setFoldID(deletion.getFoldID());
			replacement.setSurrogateFoldID(deletion.getSurrogateFoldID());
			replacement.setInvSurrogateFoldID(deletion.getInvSurrogateFoldID());
			replacement.setFoldState(deletion.getFoldState());
		} else {
			//throw some kind of exception
		}
		
	}
	
	/**
	 * returns an intiger value of this type of fold state which should always be two
	 * For use when adding a node to a database or getting info from the database
	 * 
	 * @return	2, being the integer value associated with this node state
	 */
	@Override
	public int getFoldStateInt() {
		return 2;
	}
}
