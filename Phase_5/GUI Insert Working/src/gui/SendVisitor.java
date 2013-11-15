package gui;

import exceptions.WebIDException;
import model.Node;
import model.WebID;

public class SendVisitor extends Visitor{

	protected static WebID targetID;
	protected Parameters parameters;
	
	/**
	 * default constructor, sets the end node to zero
	 */
	public SendVisitor(){
		targetID = new WebID(0);
		parameters = new Parameters();
		parameters.set("targetID", targetID);
	}
	
	/**
	 * A constructor that takes in an integer representing a webID of the node we want to end at
	 * 
	 * @param targetID = the node that we want to end at
	 */
	public SendVisitor(int endID) {
		try {
			targetID = new WebID(endID);
			parameters.set("targetID", targetID);
		} catch (WebIDException e) {
			badInputOperation();
		}
	}
	
	public Parameters getParameters() {
		return parameters;
	}

	
	public void send(int startNode) {
		Node start = Node.getNode(new WebID(startNode));
		parameters.set("Start", start);
		visit(start, parameters);
	}
	
	/**
	 * Created the initial parameters
	 * 
	 * @param target
	 * @return 	the parameters that will be sent across the hyPeerWeb
	 */
	public Parameters createInitialParameters(int target){
		targetID = new WebID(target);
		parameters = new Parameters();
		parameters.set("TargetID", targetID);
		return parameters;
	}
	
	public void addParameter(String key, Object value){
		parameters.set(key, value);
	}
	
	/**
	 * visit method
	 * @param node = the node that we want to start at
	 * @param newParameters = the parameters we want to send across the hypeerweb
	 */
	public void visit(Node node, Parameters newParameters) {
		if(node == null || Node.getNode(targetID) == null) {
			badInputOperation();
			return;
		}
		if(node.getWebID().equals(targetID)) {
			targetOperation(node);
		} else {
			intermediateOperation(node);
			node = node.sendFirstNode(targetID);
			secondVisit(node);
		}
	}
	
	public void secondVisit(Node node) {
		if(node.getWebID().equals(targetID)) {
			targetOperation(node);
		} else {
			intermediateOperation(node);
			Node nextNode = node.sendNode(targetID);
			if(nextNode != null) {
				nextNode.accept(this);
			}
		}
	}
	
	
	private void badInputOperation() {
		System.out.println("Problem with getting a node. \n Either the target node or the start node is null");
		
	}

	
	/**
	 * This is the operation that needs to be performed on a node as it is traversed to the target node. (Track the path)
	 * 
	 * @param node
	 * @param parameters
	 */
	protected void intermediateOperation(Node node){
		System.out.println("Visiting node " + node.getWebId() + " through vistor pattern and " + 
				"trying to get to node " + targetID.toString());
	}
	
	/**
	 * This is the operation that needs to be performed on the target node.
	 * 
	 * @param node
	 * @param parameters
	 */
	protected void targetOperation(Node node){
		System.out.println(node.toString() + "\npackage delivered\n");
		System.out.println(this.parameters.get("message"));
		return;
	}

}
