package gui;

import model.Node;

/**
 * Interface for the visitor pattern.
 *
 */
abstract public class Visitor {
	abstract public void visit(Node node);
}