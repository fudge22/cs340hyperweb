package model;

import database.Database;
import exceptions.DatabaseException;
import exceptions.WebIDException;
import simulation.HyPeerWebInterface;
import simulation.NodeInterface;

public class HyperWeb implements HyPeerWebInterface {

	private Database db;
	public HyperWeb(){
		Node.initialize();
		try {
			Database.initialize();
		} catch (DatabaseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/*
		db = Database.getInstance();
		try {
			Node.loadHyperWeb(db.getDatabaseAccessor().loadHyperWeb());
		} catch (WebIDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	*/
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
	
	public void removeNode() {
		Node.removeNode();
	}

}
