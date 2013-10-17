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
public class Insertable extends NodeState {
	
	private static NodeState singleton = null;
	
	public static NodeState getSingleton() {
		if (singleton == null) {
			singleton = new Insertable();
		}
		return singleton;
	}
	
	private Insertable() {};

	@Override
	public void addToNode(Node parent) {
		/*
		 * insert 
		 * if LN 
		 * 	 changeState(SS) 
		 * else 
		 * 	 broadcast stateChange(Insertable)
		 */
		parent.insertChildNode();

		Node child = Node.getNode(parent.getChildNodeID());

		System.out.println("childid: " + child.getWebId());
		System.out.println("parentid: " + parent.getWebId());
		//System.out.println("Node State:" + parent.getNodeState());
		List<WebID> lowerNeighborsList = parent.checkLowerNeighbors();

		for (WebID neighborID : parent.getNeighborList()) {
			lowerNeighborsList.addAll(Node.getNode(neighborID)
					.checkLowerNeighbors());
		}

		NodeState state;
		if (lowerNeighborsList.size() > 0) {
			state = SlipperySlope.getSingleton();
			child.setNodeState(SlipperySlope.getSingleton());
			child.broadcastNodeStateChange(state.getNodeStateInt());
		} else {
			state = Insertable.getSingleton();
			parent.setNodeState(Insertable.getSingleton());
			child.setNodeState(Insertable.getSingleton());
			parent.broadcastNodeStateChange(state.getNodeStateInt());
			child.broadcastNodeStateChange(state.getNodeStateInt());
		}

	}

	@Override
	public int getNodeStateInt() {
		return 0;
	}
}
