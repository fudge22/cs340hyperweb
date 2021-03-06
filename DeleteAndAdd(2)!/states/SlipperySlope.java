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
public class SlipperySlope extends DownState {
	
	private static DownState singleton = null;
	
	public static final int STATE_INT = 1;
	
	public static DownState getSingleton() {
		if (singleton == null) {
			singleton = new SlipperySlope();
		}
		return singleton;
	}
	
	private SlipperySlope() {};
	
	@Override
	public void addToNode(Node parent) {
		Node lowerNode = parent;
		// keep sliding as long as we are slippery
		WebID slide = null;
		try {
		while (lowerNode.getDownState().equals(SlipperySlope.getSingleton())) {
			 slide = lowerNode.slideDown();
			 lowerNode = Node.getNode(slide);
			
		}
		}catch (Exception e){
			System.out.println("dumb");
		}
		// lowerNode should be insertable at this point
		lowerNode.getDownState().addToNode(lowerNode);
	}

	@Override
	public int getDownStateInt() {
		return SlipperySlope.STATE_INT;
	}

}
