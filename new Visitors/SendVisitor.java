package visitor;

import exceptions.WebIDException;
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
	
	/**
	 * A constructor that takes in an integer representing a webID of the node we want to end at
	 * @param targetID = the node that we want to end at
	 */
	public SendVisitor(int targetID) {
		try {
			myID = new WebID(targetID);
		} catch (WebIDException e) {
			badInputOperation();
		}
	}
	
	
	/**
	 * visit method
	 * @param node = the node that we want to start at
	 */
	public void visit(Node node) {
		if(node == null || Node.getNode(myID) == null) {
			badInputOperation();
			return;
		}
		if(node.getWebID().equals(myID)) {
			targetOperation(node);
		} else {
			intermediateOperation(node);
			node = node.sendFirstNode(myID);
			secondVisit(node);
		}
	}
	
	private void badInputOperation() {
		System.out.println("Problem with getting a node. \n Either the target node or the start node is null");
		
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
