package states;

import java.util.List;

import model.Node;
import model.WebID;

/*
 * a node is in the "Insertable" state if ALL of these conditions are true:
 * - it has no neighbors who are a lower height - it has no surrogate
 * neighbors - it has no surrogate fold
 * 
 * the node will change to the "Slippery Slope" state when any of the above
 * conditions are false
 */
public class Deletable extends UpState {
	
	private static UpState singleton = null;
	
	public static final int STATE_INT = 0;
	
	public static UpState getSingleton() {
		if (singleton == null) {
			singleton = new Deletable();
		}
		return singleton;
	}
	
	private Deletable() {};

	@Override
	public Node findEdgeNodeFrom(Node n) {
		return n;
	}

	@Override
	public int getUpStateInt() {
		return STATE_INT;
	}
}
