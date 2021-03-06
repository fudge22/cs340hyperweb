package states;

import model.Node;

/*
 * Keeps track of the state of the node relating to its ability to become a
 * parent to a child node. Based on its state, the addNode() function will
 * behave differently
 */
public abstract class DownState {
	
	public abstract void addToNode(Node n);

	public abstract int getDownStateInt();
}
