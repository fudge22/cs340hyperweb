package states;

import model.Node;

/*
 * Keeps track of the state of the node relating to its ability to become a
 * parent to a child node. Based on its state, the addNode() function will
 * behave differently
 */
public abstract class NodeState {
	
	public abstract void addToNode(Node n);

	public abstract int getNodeStateInt();

	public void removeNode(Node deleteNode) {
		// TODO Auto-generated method stub
		
	}
}
