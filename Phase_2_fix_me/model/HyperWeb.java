package model;

import database.Database;
import exceptions.WebIDException;
import simulation.HyPeerWebInterface;
import simulation.NodeInterface;

public class HyperWeb implements HyPeerWebInterface {

	private Database db;
	public HyperWeb(){
		Node.initialize();
		db = Database.getInstance();
		try {
			Node.loadHyperWeb(db.getDatabaseAccessor().loadHyperWeb());
		} catch (WebIDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	@Override
	public NodeInterface[] getOrderedListOfNodes() {
		// TODO Auto-generated method stub
		return Node.allNodes();
	}

	@Override
	public NodeInterface getNode(int webId) {
		// TODO Auto-generated method stub
		WebID myID = null;
		try {
			myID = new WebID(webId);
		} catch (WebIDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Node.getNode(myID);
	}
	public void addNode()
	{
		try {
			Node.addNode();
		} catch (WebIDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
