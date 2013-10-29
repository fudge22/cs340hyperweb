package visitor;

import model.Node;
import model.WebID;

public class SendVisitor extends Visitor{

	protected static WebID myID;
	
	/**
	 * default constructor
	 */
	public SendVisitor(){
		myID = new WebID(0);
	}
	
	public SendVisitor(WebID targetID) {
		myID = targetID;
	}
	
	
	/**
	 * visit method
	 */
	public void visit(Node node) {
		if(node.getWebID() == myID) {
			targetOperation(node);
		} else {
			intermediateOperation(node);
			node = node.sendFirstNode(myID);
			secondVisit(node);
		}
	}
	
	public void secondVisit(Node node) {
		if(node.getWebID().equals(myID)) {
			targetOperation(node);
		} else {
			intermediateOperation(node);
			Node nextNode = node.sendNode(myID);
			if(nextNode != null) {
				nextNode.accept(this);
			}
		}
		//node.accept(this, parameters);
	}
	
	/**
	 * Created the initial parameters
	 * 
	 * @param target
	 * @return
	 */
	public static Parameters createInitialParameters(int target){
		myID = new WebID(target);
		return new Parameters(target);
	}
	
	/**
	 * This is the operation that needs to be performed on a node as it is traversed to the target node. (Track the path)
	 * 
	 * @param node
	 * @param parameters
	 */
	protected void intermediateOperation(Node node){
		System.out.println("Visiting node " + node.getWebId() + " through vistor pattern and " + 
				"trying to get to node " + myID.toString());
	}
	
	/**
	 * This is the operation that needs to be performed on the target node.
	 * 
	 * @param node
	 * @param parameters
	 */
	protected void targetOperation(Node node){
		System.out.println(node.toString() + "\npackage delivered\n");
		return;
	}
}
