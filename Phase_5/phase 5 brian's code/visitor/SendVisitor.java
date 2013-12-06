package visitor;

import exceptions.VisitorException;
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
	 * @throws VisitorException 
	 */
	public SendVisitor(int endID) throws VisitorException {
		try {
			parameters = new Parameters();
			targetID = new WebID(endID);
			parameters.set("targetID", targetID);
		} catch (WebIDException e) {
			badInputOperation();
			throw new VisitorException (e);
		}
	}
	
	public Parameters getParameters() {
		return parameters;
	}

	
	public void send(int startNode) throws VisitorException {
		WebID startID = null;
		try{
			startID = new WebID(startNode);
		} catch (WebIDException e) {
			badInputOperation();
			throw new VisitorException();
		}
		Node start = Node.getNode(startID);
		if(start == null || Node.getNode(targetID) == null) {
			badInputOperation();
			throw new VisitorException();
		}
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
	public void visit(Node node, Parameters newParameters)  {
		if(node.getWebID().equals(targetID)) {
			targetOperation(node, this.parameters);
		} else {
			intermediateOperation(node, this.parameters);
			node = node.sendFirstNode(targetID);
			secondVisit(node);
		}
	}
	
	public void secondVisit(Node node) {
		if(node.getWebID().equals(targetID)) {
			targetOperation(node, this.parameters);
		} else {
			intermediateOperation(node, this.parameters);
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
	protected void intermediateOperation(Node node, Parameters parameters){
		System.out.println("Visiting node " + node.getWebId() + " through vistor pattern and " + 
				"trying to get to node " + targetID.toString());
	}
	
	/**
	 * This is the operation that needs to be performed on the target node.
	 * 
	 * @param node
	 * @param parameters
	 */
	protected void targetOperation(Node node, Parameters myParameters){
		System.out.println(node.toString() + "\npackage delivered\n");
		System.out.println(myParameters.get("message"));
		return;
	}

}
