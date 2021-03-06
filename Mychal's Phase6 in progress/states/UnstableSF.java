package states;

import java.io.Serializable;

import model.HyperWeb;
import model.Node;
import model.WebID;

public class UnstableSF extends FoldState implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8697348158875868262L;
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
		Node parentSurFold = parent.getSurrogateFoldID().getNode();
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
	 * Should never be called here because an edge node will never be in this fold state
	 * (this node should have a child. in other words, this node is not an edge node)
	 * 
	 * @param d	the node that will be removed
	 */
	@Override
	public void removeFoldsOf(Node d) {
		System.err.println("can't disconnect here because it this node should "
				+ "have a child (in other words, this is not an edge node");
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
