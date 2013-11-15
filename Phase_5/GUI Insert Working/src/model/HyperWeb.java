package model;

import database.Database;
import exceptions.DatabaseException;
import exceptions.WebIDException;
import simulation.HyPeerWebInterface;
import simulation.NodeInterface;

public class HyperWeb implements HyPeerWebInterface {

	private Database db;
	
	private static HyperWeb hyperwebsingleton;
	
	
	
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
	
	public Node addNode()
	{
		Node returnNode = null;
		try {
			returnNode = Node.addNode();
		} catch (WebIDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnNode;
	}
	
	public void removeNode() {
		try {
			Node.removeNode();
		} catch (WebIDException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		// TODO Do something when you close the gui for the hyperweb
		hyperwebsingleton = null;
	}

	public static HyperWeb getHyPeerWeb() {
		//if hyperwebsingleton is null instantiate
		if(hyperwebsingleton == null){
			hyperwebsingleton = new HyperWeb();
		}
		
		return hyperwebsingleton;
	}

}
