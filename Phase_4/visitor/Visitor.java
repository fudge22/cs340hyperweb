package visitor;

import model.Node;

/**
 * Interface for the visitor pattern.
 *
 */
public interface Visitor {
	public void visit(Node node, Parameters parameters);
}
