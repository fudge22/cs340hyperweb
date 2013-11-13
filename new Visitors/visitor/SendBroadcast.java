package visitor;

import model.Node;
import model.WebID;

/**
 * class to add contents to a node
 *
 */
public class SendBroadcast extends SendVisitor{
	
	public SendBroadcast(){
		super();
	}
	
	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}
	
	public void addParameter(String key, Object value) {
		this.parameters.set(key, value);
	}
	
	/**
	 * A funciton that will let us start at any ID and then move to node Zero to broadcast the message
	 * @param nodeID	the node wish to start at
	 */
	@Override
	public void send(int startID){
		Node startNode = Node.getNode(new WebID(startID));
		if(parameters == null) {
			parameters = new Parameters();
		}
		super.visit(startNode, parameters);
	}
	
	@Override
	protected void intermediateOperation(Node node){
	}
	
	@Override
	protected void targetOperation(Node node){
		BroadcastVisitor myVisit = new BroadcastVisitor();
		myVisit.visit(Node.getNode(new WebID(0)), parameters);
	}

	
	
	
}
