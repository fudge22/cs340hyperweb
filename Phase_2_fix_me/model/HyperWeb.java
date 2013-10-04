package model;

import node.Node;
import database.Database;
import simulation.HyPeerWebInterface;
import simulation.NodeInterface;

public class HyperWeb implements HyPeerWebInterface {

	private Database db;
	public HyperWeb(){
		Node.initialize();
		db = Database.getInstance();
		Node.loadHyperWeb(db.getDatabaseAccessor().loadHyperWeb());
	
	}
	
	@Override
	public NodeInterface[] getOrderedListOfNodes() {
		// TODO Auto-generated method stub
		return Node.allNodes();
	}

	@Override
	public NodeInterface getNode(int webId) {
		// TODO Auto-generated method stub
		return Node.getNode(webId);
	}
	public void addNode()
	{
		Node.addNode();
		
	}

}
