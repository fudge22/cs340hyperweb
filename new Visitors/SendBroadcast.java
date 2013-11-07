package visitor;

import model.Node;
import model.WebID;

/**
 * class to add contents to a node
 *
 */
public class SendBroadcast extends SendVisitor{

	protected static WebID myID;
	
	public SendBroadcast(){
		super();
	}
	
	public void visit(int nodeID){
		Node startNode = Node.getNode(new WebID(nodeID));
		super.visit(startNode);
	}
	
	@Override
	protected void intermediateOperation(Node node){
	}
	
	@Override
	protected void targetOperation(Node node){
		BroadcastVisitor myVisit = new BroadcastVisitor();
		myVisit.visit(Node.getNode(new WebID(0)));
	}
	
	
}
