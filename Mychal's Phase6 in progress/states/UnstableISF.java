package states;

import java.io.Serializable;

import model.HyperWeb;
import model.Node;
import model.WebID;

//Unstable Inverse Surrogate Fold
// this naming convention because the node would have a fold and an inverse surrogate fold
public class UnstableISF extends FoldState implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9197676791836264112L;
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
		Node parentInvSurFold = parent.getInvSurrogateFoldID().getNode();

		// make the fold of the child the inverse surrogate fold of parent
		child.setFoldID(parent.getInvSurrogateFoldID());
		
		// make sure child has no surFold or invSurFold
		WebID childSurFoldID = child.getSurrogateFoldID();
		if (childSurFoldID != null) {
			child.removeSurFold(childSurFoldID);
		}
		
		WebID childInvSurFoldID = child.getInvSurrogateFoldID();
		if (childInvSurFoldID != null) {
			child.removeInvSurFold(childInvSurFoldID);
		}

		// set child as parent inverse surrogate fold's new fold
		parentInvSurFold.setFoldID(childId);

		// tell parent inverse surrogate fold to remove its surrogate fold
		WebID surFoldToRemove = parentInvSurFold.getSurrogateFoldID();
		if (surFoldToRemove != null) {
			parentInvSurFold.removeSurFold(surFoldToRemove);
		}

		// tell parent inverse surrogate fold to update its foldState
		parentInvSurFold.setFoldState(StableFold.getSingleton());

		// make parent remove it's inverse surrogate fold
		WebID invSurFoldToRemove = parent.getInvSurrogateFoldID();
		if (invSurFoldToRemove != null) {
			parent.removeInvSurFold(invSurFoldToRemove);
		}

		// update parent's state to stable
		parent.setFoldState(StableFold.getSingleton());
		child.setFoldState(StableFold.getSingleton());

	}

	/**
	 * Should never be called here because an edge node will never be in this fold state
	 * (it has up pointers in the form of a inv sur fold)
	 * 
	 * @param d	The node to be removed
	 */
	@Override
	public void removeFoldsOf(Node d) {
		System.err.println("can't disconnect here because it has an inv sur fold (up pointer)");
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
