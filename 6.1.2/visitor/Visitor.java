package visitor;

import java.io.Serializable;

import model.Node;

/**
 * Interface for the visitor pattern.
 *
 */
abstract public class Visitor implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2849796851599091330L;

	abstract public void visit(Node node, Parameters parameters);
}
