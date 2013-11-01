package gui;

import model.Node;

/**
 * Sends a message from the start node (the first node to receive this visitor) the indicated target node.
 * As it traverses intermediate nodes it prints trace messages to the "TraceInformation" section of the GUI.
 * When it gets to the target, it prints the target's webId and the message.
 * 
 * <pre>
 * 		<b>Domain:</b> <i>None</i>
 * </pre>
 */
public class GUISender extends SendVisitor {
	/**
	 * The default constructor. It does nothing but call the superclass's default constructor.
	 * 
	 * @pre <i>None</i>
	 * @post super.post-condition
	 */
	public GUISender(){
		super();
	}
	
	/**
	 * Creates the parameters needed to send the GUISender visitor to the first Node.
	 * 
	 * @param target the target of the send operation.
	 * @param message  the message to be sent to the target node
	 * 
	 * @pre &exist; node (node &isin; HyPeerWeb AND node.webId = target)
	 * @post result &ne; null AND result.contains(MESSAGE_KEY) AND result.get(MESSAGE_KEY) = message
	 */
	public static Parameters createInitialParameters(int target, String message){
		//TODO Phase 5 -- replace the next line with one or more lines implementing the initialization of the parameters.
		return null;
	}

	//@Override
	/**
	 * Prints a string in the TracePanel of the GUI.  The string should contain the labeled webId of the current node
	 * (the target node) and the message.
	 * 
	 * @pre node &ne; null AND node &isin; HyPeerWeb AND parameters &ne; null AND parameters.contains(MESSAGE_KEY)
	 * @post A string with the current node's id and message should be printed on the tracePanel of the GUI.<br>
	 * Required format: "Target node = " node.getWebId() + ", message = '" parameters.get(MESSAGE_ID) "'.\n"
	 */
	//protected void targetOperation(Node node, Parameters parameters) {
		//TODO Phase 5 -- implement this method so that it satisfies the post condition.
	//}
	
	/**
	 * Prints a string in the TracePanel of the GUI.  The string should contain the labeled webId of the target node and
	 * the labeled webId of the current node.
	 * 
	 * @pre node &ne; null AND node &isin; HyPeerWeb AND parameters &ne; null AND parameters.contains(TARGET_KEY)
	 * @post A string with the target node's id and the current node's id should be printed on the tracePanel of the GUI.<br>
	 * Required format: "Sending message to node = "  parameters.get(TARGET_ID) ", currently at node " node.getWebId() ".\n"
	 */	
	//protected void intermediateOperation(Node node, Parameters parameters) {
		//TODO Phase 5 -- implement this method so that it satisfies the post condition.
	//}
	
	/**
	 * The message parameter identifier to be used to add messages to the parameter list.
	 */
	protected static final String MESSAGE_KEY = "message";
}
