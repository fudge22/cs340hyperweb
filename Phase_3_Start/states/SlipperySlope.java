package states;

import java.util.HashSet;

import model.Node;

/*
 * a node is in the "Slippery Slope" state if any of these conditions are
 * true: - it has neighbors who are a lower height - it has surrogate
 * neighbors - it has a surrogate fold
 * 
 * the node will change to the "Insertable" state when none of the above
 * conditions are true
 */
public class SlipperySlope extends NodeState {
	
	private static NodeState singleton = null;
	
	public static NodeState getSingleton() {
		if (singleton == null) {
			singleton = new SlipperySlope();
		}
		return singleton;
	}
	
	private SlipperySlope() {};
	
	@SuppressWarnings("unused")
	@Override
	public void addToNode(Node parent) {
		/*
		 * if SN 
		 * 		insert at SN 
		 * if SF 
		 * 		insert at SF 
		 * if LN 
		 * 		insert at LN
		 * N 
		 * NN
		 */

		// TODO do we need to account for folds?
		
		Node receivingParent = parent.findHole();

		if (receivingParent == null) {
			// need to check neighbors neighbors as well
			HashSet<Node> twoHopsAway = new HashSet<Node>();

			// get neighbors and neighbors neighbors
			twoHopsAway = parent.getAll2Hops();

			for (Node n : twoHopsAway) {
				receivingParent = n.findHole();
				// found a place to insert
				if (receivingParent != null) {
					break;
				}
			}
			// if nothing found within two hops
			if (receivingParent == null) {
				receivingParent = parent;
			}

		}
		receivingParent.setNodeState(Insertable.getSingleton());
		receivingParent.getNodeState().addToNode(receivingParent);

	}

	@Override
	public int getNodeStateInt() {
		return 1;
	}

}
