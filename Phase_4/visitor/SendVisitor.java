package visitor;

import model.Node;

public class SendVisitor implements Visitor{

	protected static String TARGET_KEY;
	
	/**
	 * default constructor
	 */
	public SendVisitor(){
		super();
		TARGET_KEY = "";
	}
	
	/**
	 * visit method
	 */
	@Override
	public void visit(Node node, Parameters parameters) {
		node.accept(this,parameters);
	}
	
	/**
	 * Created the initial parameters
	 * 
	 * @param target
	 * @return
	 */
	public static Parameters createInitialParameters(int target){
		TARGET_KEY = "" + target;
		return new Parameters(target);
	}
	
	/**
	 * This is the operation that needs to be performed on a node as it is traversed to the target node. (Track the path)
	 * 
	 * @param node
	 * @param parameters
	 */
	protected void intermediateOperation(Node node, Parameters parameters){
		// TODO this operation needs to be performed on a node as we traverse to the target node.
		
	}
	
	/**
	 * This is the operation that needs to be performed on the target node.
	 * 
	 * @param node
	 * @param parameters
	 */
	protected /*abstract*/ void targetOperation(Node node, Parameters parameters){
		// TODO this is the abstract operation to be performed on the targetNode. 
		//      It must be overridden in any concrete subclass and have abstract added to the method header.
		//      Not really sure what the subclass would be.
		
	}
}
