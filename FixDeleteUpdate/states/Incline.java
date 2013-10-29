package states;

import model.Node;

/*
 * a node is in the "Slippery Slope" state if any of these conditions are
 * true: - it has neighbors who are a lower height - it has surrogate
 * neighbors - it has a surrogate fold
 * 
 * the node will change to the "Insertable" state when none of the above
 * conditions are true
 */
public class Incline extends UpState {
	
	private static UpState singleton = null;
	
	public static final int STATE_INT = 1;
	
	public static UpState getSingleton() {
		if (singleton == null) {
			singleton = new Incline();
		}
		return singleton;
	}
	
	private Incline() {};
	
	@Override
	public Node findEdgeNodeFrom(Node n) {
		Node higherNode = n;
		if ( higherNode == null) {
			System.out.println("hi");
		}
		if ( higherNode.getUpState() == null) {
			System.out.println("bob");
		}
		// keep sliding as long as we are slippery
		while (higherNode.getUpState().equals(Incline.getSingleton())) {
			higherNode = Node.getNode(higherNode.slideUp());
		}
		// higherNode should be deletable at this point
		return higherNode;
	}

	@Override
	public int getUpStateInt() {
		return Incline.STATE_INT;
	}

}
