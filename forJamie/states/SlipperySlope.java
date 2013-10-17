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
		Node receivingParent = parent;
		Node lowerNode;
		HashSet<Node> visited = new HashSet<Node>();
		visited.add(parent);
		if(parent.getWebId() == 258) {
			System.out.println("Node State:" + parent.getNodeState());
		}
		boolean slide = true;
		do {
			lowerNode = receivingParent.findLowerNode();
			
			// lower node exists, keep looking for lower from there
			if (lowerNode == null && !visited.contains(receivingParent)) {
				// need to check neighbors neighbors as well
				HashSet<Node> twoHopsAway = new HashSet<Node>();
	
				// get neighbors and neighbors neighbors
				twoHopsAway = receivingParent.getAll2Hops();
	
				for (Node n : twoHopsAway) {
					if (!visited.contains(n)) {
						lowerNode = n.findLowerNode();
						visited.add(n);
						// found a place to slide
						if (lowerNode != null) {
							visited.add(lowerNode);
							receivingParent = lowerNode;
							break;
						}
					}
					
				}
			}
			// lower node exists that HAS NOT been visited
			else if (lowerNode != null && !visited.contains(lowerNode)) {
				visited.add(lowerNode);
				receivingParent = lowerNode;
			} 
			// stop and insert if at lower node that has been visited already. NO LOOPS
			else {
				slide = false;
			}
			
		} while(slide); // when no more down pointers, receivingParent should be null
		receivingParent.setNodeState(Insertable.getSingleton());
		receivingParent.getNodeState().addToNode(receivingParent);
		

	}

	@Override
	public int getNodeStateInt() {
		return 1;
	}

}
