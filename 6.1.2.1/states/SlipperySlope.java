package states;

import java.io.Serializable;


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
public class SlipperySlope extends DownState implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3594730371846562359L;

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
	public Node addToNode(Node parent) {
		Node lowerNode = parent;
		// keep sliding as long as we are slippery
		WebID slide = null;
		try {
		while (lowerNode.getDownState() instanceof SlipperySlope) {
			
			 slide = lowerNode.slideDown();
			 lowerNode = slide.getNode();
			
		}
		}catch (Exception e){
			System.out.println("dumb");
		}
		return lowerNode.getDownState().addToNode(lowerNode);
	}

	@Override
	public int getDownStateInt() {
		return SlipperySlope.STATE_INT;
	}


}
