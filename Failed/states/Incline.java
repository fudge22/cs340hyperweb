package states;

import model.Node;
import model.WebID;

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
		// keep sliding as long as we are slippery
		while (higherNode.getUpState().equals(Incline.getSingleton())) {
			WebID higherNodeID = higherNode.slideUp();
			higherNode = Node.getNode(higherNodeID);
			if (higherNode == null) {
				System.out.println("booga");
			}
		}
		// higherNode should be deletable at this point
		return higherNode;
	}

	@Override
	public int getUpStateInt() {
		return Incline.STATE_INT;
	}

}
