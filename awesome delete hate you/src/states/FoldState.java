package states;

import model.Node;

public abstract class FoldState {

	// Code to update the folds of a node when a child is added to this node
	public abstract void updateFoldOf(Node p, Node c);
	
	public abstract void removeFoldsOf(Node p);

	public abstract int getFoldStateInt();
}