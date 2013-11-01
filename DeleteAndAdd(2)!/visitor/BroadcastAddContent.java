package visitor;

import model.Node;
import model.WebID;

/**
 * class to add contents to a node
 *
 */
public class BroadcastAddContent extends BroadcastVisitor{

	protected static final String CONTENT_KEY = "BroadcastContentKey";
    protected static final String CONTENT_VALUE = "BroadcastContentValue";
	
    /**
     * implements the abstract class operation
     */
	@Override
	protected void operation(Node node, Parameters parameters) {
		node.getContents().set((String) parameters.get(CONTENT_KEY), parameters.get(CONTENT_VALUE));
	}
	
	/**
	 * creates the parameters
	 * 
	 * @param startNodeId
	 * @param key
	 * @param value
	 * @return
	 */
	public static Parameters createInitialParameters(WebID startNodeId, String key, Object value) {
		Parameters parameters = BroadcastVisitor.createInitialParameters(startNodeId.getValue());
		parameters.set(CONTENT_KEY, key);
		parameters.set(CONTENT_VALUE, value);
		return parameters;
	}
}
