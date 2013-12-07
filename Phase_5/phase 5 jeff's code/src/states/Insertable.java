package states;

import java.io.Serializable;

import model.Node;

/*
 * a node is in the "Insertable" state if ALL of these conditions are true:
 * - it has no neighbors who are a lower height - it has no surrogate
 * neighbors - it has no surrogate fold
 * 
 * the node will change to the "Slippery Slope" state when any of the above
 * conditions are false
 */
public class Insertable extends DownState implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6165624193075765267L;

	private static DownState singleton = null;
	
	public static final int STATE_INT = 0;
	
	public static DownState getSingleton() {
		if (singleton == null) {
			singleton = new Insertable();
		}
		return singleton;
	}
	
	private Insertable() {};

	@Override
	public Node addToNode(Node parent) {
		return parent.insertChildNode();
	}

	@Override
	public int getDownStateInt() {
		return STATE_INT;
	}
}


