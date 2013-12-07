package visitor;

import exceptions.VisitorException;
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
		super.addParameter(key, value);
	}
	
	/**
	 * A funciton that will let us start at any ID and then move to node Zero to broadcast the message
	 * @param nodeID	the node wish to start at
	 */
	@Override
	public void send(int startID) throws VisitorException {
		super.send(startID);
	}
	
	@Override
	protected void intermediateOperation(Node node, Parameters parameters){
		
	}
	
	@Override
	protected void targetOperation(Node node, Parameters myParameters){
		BroadcastVisitor myVisit = new BroadcastVisitor();
		myVisit.visit(new WebID(0).getNode(), myParameters);
	}

	
	
	
}
