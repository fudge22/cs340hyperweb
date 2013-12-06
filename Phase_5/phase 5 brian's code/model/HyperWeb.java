package model;

import javax.xml.crypto.NodeSetData;

import database.Database;
import exceptions.DatabaseException;
import exceptions.HyperWebException;
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
	
	public Node addNode() throws HyperWebException {
		try {
			return Node.addNode();
		} catch (WebIDException e) {
			throw new HyperWebException();
		}
	}
	
	public Node removeNode() throws HyperWebException {
		try {
			return Node.removeNode();
		} catch (WebIDException e) {
			throw new HyperWebException();
		}
	}
	
	public Node removeNode(int i) throws HyperWebException {
		try{
			return Node.removeNode(i);
		} catch (WebIDException e) {
			throw new HyperWebException();
		}
	}
	public void close() {
		// TODO Auto-generated method stub
		
	}

	public static HyperWeb getHyPeerWeb() {
		//if hyperwebsingleton is null instantiate
		if(hyperwebsingleton == null){
			hyperwebsingleton = new HyperWeb();
		}
		
		return hyperwebsingleton;
	}

	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void clear() {
		// TODO Auto-generated method stub
		
	}

	
	public void addSpecial(int i) {
		try {
			Node.addNode(i);
		} catch (WebIDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void print() {
		Node.printHyperWeb();
	}

	

}
