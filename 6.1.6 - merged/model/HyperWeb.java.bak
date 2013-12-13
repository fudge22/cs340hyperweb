package model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import Phase6.GlobalObjectId;
import Phase6.ObjectDB;
import Phase6.PortNumber;
import database.Database;
import exceptions.DatabaseException;
import exceptions.WebIDException;
import simulation.HyPeerWebInterface;
import simulation.NodeInterface;
import states.Deletable;
import states.Incline;
import states.Insertable;
import states.StableFold;
import Phase6.*;
public class HyperWeb implements HyPeerWebInterface, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2694109925339295894L;
	private Database db;
	//create an object db
	//public ObjectDB odb = ObjectDB.getSingleton();

	private static  HyperWeb hw;
	public  HashMap<WebID, Node> nodes;

	private  boolean debug = false;
	private  String lastPrintState = "";
	private  String currentPrintState = "";
	private  int iterationCount = 0;
	private  int startPointInfo = -1;
	private  int insertPointInfo = -1;
	private  int insertedNodeInfo = -1;
	
//	private static int portNum = 3000;
	
	
	private HyperwebFaceProxy next;

	
	
	public  StringBuilder operationList = new StringBuilder();
	public static GlobalObjectId hyperwebSeg = new GlobalObjectId();
	
	
	public  void initialize(/*GlobalObjectId hyperwebSeg*/) {
		nodes = new HashMap<WebID, Node>();
	}
	public  Node getNode(WebID id) {
		return nodes.get(id);// if null, return null node
	}
	public static void changePort(int p){
		Phase6.PortNumber.setApplicationsPortNumber(new PortNumber(p));
	}
	public HyperWeb(){
		
		
//		PortNumber.setApplicationsPortNumber(new PortNumber(portNum));
		initialize();
		
		
	
	}
	public void setNext(HyperwebFaceProxy nxt){
		next = nxt;
		saveDb();
	}
	public static HyperWeb getSingleton(){
		
		
		if (hw == null ){
			HyperWeb hwdb = loadDb();
				if ( hwdb == null){
					System.out.println("Hyperweb created");
					hw = new HyperWeb();
				}
				else
				{
					hw = hwdb;
				}
		}
		
		return hw;
	}
	
	@Override
	public NodeInterface[] getOrderedListOfNodes() {
		return allNodes();
	}

	@Override
	public NodeInterface getNode(int webId) {
		WebID myID = null;
		try {
			myID = new WebID(webId);
		} catch (WebIDException e) {
			e.printStackTrace();
		}
		return myID.getNode();
	}
	
	public  Node addNode() {
		Node returnNode = null;
		
		//if you are adding to all empty hws then do the first one
		
		Node insertPoint = getRandomNode(null);
		
		//get random node
		//if the random node is 0 than check if it has neighbors, if it has none than add second
		if (insertPoint == null){
			
			returnNode = HyperWeb.getSingleton().addToEmptyHyperWeb();// base case with no nodes in the

		}
		else if (insertPoint.getWebId() >= 0 && insertPoint.getNeighborList().size() > 0)//it is missing the case that 0 is randomly selected, but it isn't the only node
			returnNode = HyperWeb.getSingleton().addToHyperWeb(insertPoint);
			
		else if (insertPoint.getWebId() == 0 && insertPoint.getNeighborList().size() == 0)
			{
			
				returnNode = HyperWeb.getSingleton().addSecondNode(insertPoint);// base case with only one node in the

				
			}
			
		
		

		saveDb();
		return returnNode;
	}
//	public  void loadHyperWeb(HashMap<WebID, Node> loadHyperWeb) {
//		hw.nodes = Database.getInstance().getDatabaseAccessor().loadHyperWeb();
//
//	}
	public  NodeInterface[] allNodes() {
		Node[] nodeArray = new Node[hw.nodes.size()];
		hw.nodes.values().toArray(nodeArray); // the toArray() function fills in
											// the array nodeArray
		return nodeArray;

	}

	
	public  Node addToEmptyHyperWeb() {
		WebID temp = new WebID(0);
		putWrapper(temp, new Node(temp, 0));
		
		
		temp.getNode().setUpState(Deletable.getSingleton());//maybe change this to not use hw.nodes
		saveDb();
		return temp.getNode();
	}

	public  Node addSecondNode(Node node0) {
		WebID firstID = node0.getWebID();
		WebID secondID = new WebID(1);

		putWrapper(secondID, new Node(secondID, 0));
		
		Node first = node0;
		Node second = secondID.getNode();
		
		first.addNeighbor(secondID);
		first.setFoldID(secondID);
		first.setHeight(1);
		
		second.addNeighbor(firstID);
		second.setFoldID(firstID);
		second.setHeight(1);
		
		first.setDownState(Insertable.getSingleton());
		second.setDownState(Insertable.getSingleton());
		
		first.setUpState(Incline.getSingleton());
		second.setUpState(Deletable.getSingleton());
		
		first.setFoldState(StableFold.getSingleton());
		second.setFoldState(StableFold.getSingleton());
		
		first.setCurrentChild(second.getWebID());
		saveDb();
		return second;

	}
	public  Node removeNode() {
		Node randomStartNode = HyperWeb.getSingleton().getRandomNode(null);
		Node randomDeleteNode = HyperWeb.getSingleton().getRandomNode(null);
		
//		debugInfo("Starting at node " + randomStartNode.getWebId());
		
		Node disconnectPoint = randomStartNode.getUpState().findEdgeNodeFrom(randomStartNode);
		WebID disconnectID = disconnectPoint.getWebID();
		
//		debugInfo("Trying to disconnect " + disconnectPoint.getWebId());
//		debugInfo("Trying to delete " + randomDeleteNode.getWebId());
		
		// if the node we are trying to delete is an edge node, just delete it
		if (randomDeleteNode.getUpState() instanceof Deletable) {
			disconnectPoint.disconnect();
		} else {
			// disconnect and replace deleted
			disconnectPoint.disconnect();
			disconnectPoint.getConnectionsFrom(randomDeleteNode);
			hw.nodes.put(randomDeleteNode.getWebID(), disconnectPoint);
			
		}
		Node temp = hw.nodes.get(disconnectID);
		
		hw.nodes.remove(disconnectID);
		
//		updateInfoString();
//		printHyperWeb();
		saveDb();
		return temp;
	}

	public Node removeNode(int StartNode) {
		Node randomStartNode = HyperWeb.getSingleton().getRandomNode(null);
		Node randomDeleteNode = new WebID(StartNode).getNode(); //would care about this being wrong, but delete doesn't work...
		
//		debugInfo("Starting at node " + randomStartNode.getWebId());
		
		Node disconnectPoint = randomStartNode.getUpState().findEdgeNodeFrom(randomStartNode);
		WebID disconnectID = disconnectPoint.getWebID();
		
//		debugInfo("Trying to disconnect " + disconnectPoint.getWebId());
//		debugInfo("Trying to delete " + randomDeleteNode.getWebId());
		
		// if the node we are trying to delete is an edge node, just delete it
		if (randomDeleteNode.getUpState() instanceof Deletable) {
			disconnectPoint.disconnect();
		} else {
			// disconnect and replace deleted
			disconnectPoint.disconnect();
			disconnectPoint.getConnectionsFrom(randomDeleteNode);
			hw.nodes.put(randomDeleteNode.getWebID(), disconnectPoint);
			
		}
		Node temp = hw.nodes.get(disconnectID);
		
		hw.nodes.remove(disconnectID);
		
		hw.updateInfoString();
		hw.printHyperWeb();
		return temp;
	}

	private  void emptyHyperWeb() {
		hw = null;
		
	}
	
	public void putWrapper(WebID w, Node n){
		hw.nodes.put(w, n);
		if (w.getGid() != null) { // do I need to make sure that the the local object is on this machine?
			ObjectDB.getSingleton().store(w.getGid().getLocalObjectId(), w);
			ObjectDB.getSingleton().store(n.gid.getLocalObjectId(), n);
		}
	}
	
	private void removeWrapper(WebID w){
		hw.nodes.remove(w);
		
		if (w.getGid() != null) { // do I need to make sure that the the local object is on this machine?
			
			ObjectDB.getSingleton().remove(hw.nodes.get(w).gid.getLocalObjectId());
			ObjectDB.getSingleton().remove(w.getGid().getLocalObjectId());
			}
	}
	
	
	private  void debugInfo(String info) {
		if (debug) {
			System.out.println(info);
			System.out.println();
		}
	}
	
	public  void updateInfoString() {
		if (debug) {
//			lastPrintState = currentPrintState;
			currentPrintState = "";
			currentPrintState += "iteration: " + iterationCount + "\n";
			currentPrintState += "# Nodes in HW: " + hw.nodes.size() + "\n";
			currentPrintState += "\n";
			for (WebID w : hw.nodes.keySet()) {
				Node n1 = w.getNode();
				currentPrintState += "WebID: " + w.getValue() + "\n";
				currentPrintState += "Height: " + w.getNode().getHeight() + "\n";
	
				if (n1.getFoldState() != null) {
					currentPrintState += "FoldState: " + n1.getFoldState().getClass().getName()
							+ "\n";
				} else {
					currentPrintState += "FoldState: null\n";
				}
	
				if (n1.getFoldID() != null) {
					currentPrintState += "FoldID: " + (w.getNode()).getFoldID().getValue() + "\n";
				} else {
					currentPrintState += "FoldID: null \n";
	
				}
	
				if (n1.getSurrogateFoldID() != null) {
					currentPrintState += "SurrogateFoldID: "
							+ w.getNode().getSurrogateFoldID().getValue() + "\n";
				} else {
					currentPrintState += "SurrogateFoldID: null\n";
				}
	
				if (n1.getInvSurrogateFoldID() != null) {
					currentPrintState += "InverseSurrogateFold: "
							+ w.getNode().getInvSurrogateFoldID().getValue() + "\n";
				} else {
					currentPrintState += "InverseSurrogateFold: null \n";
				}
				currentPrintState += "Neighbors:\n";
				for (WebID neighbor : w.getNode().getNeighborList()) {
					currentPrintState += "Neighbor: " + neighbor.getValue() + "\n";
	
				}
				currentPrintState += "Inverse Surrogate Neighbors:\n";
				for (WebID invNeighbor : w.getNode().getInvSurNeighborList()) {
					currentPrintState += "Inverse Neighbor: " + invNeighbor.getValue() + "\n";
	
				}
				currentPrintState += "Surrogate Neighbors:\n";
				for (WebID surNeighbor : w.getNode().getSurNeighborList()) {
					currentPrintState += "Surrogate Neighbor: " + surNeighbor.getValue() + "\n";
	
				}
				
				if (n1.currentChild != null) {
					currentPrintState += "Current Child: " + n1.currentChild
							+ "\n";
	
				} else {
					currentPrintState += "Current Child: null\n";
				}
				
				
				if (n1.getDownState() != null) {
					currentPrintState += "Down State: " + n1.getDownState().getClass().getName()
							+ "\n";
	
				} else {
					currentPrintState += "Down State: null\n";
				}
				
				if (n1.getUpState() != null) {
					currentPrintState += "Up State: " + n1.getUpState().getClass().getName()
							+ "\n";
	
				} else {
					currentPrintState += "Up State: null\n";
				}
				
				currentPrintState += "Self Down:\n";
				currentPrintState += n1.selfDown.toString() + "\n";
				
				currentPrintState += "Neighbor Down:\n";
				currentPrintState += n1.neighborDown.toString() + "\n";
				
				currentPrintState += "Double Neighbor Down:\n";
				currentPrintState += n1.doubleNeighborDown.toString() + "\n";
				
				currentPrintState += "Self Up:\n";
				currentPrintState += n1.selfUp.toString() + "\n";
				
				currentPrintState += "Neighbor Up:\n";
				currentPrintState += n1.neighborUp.toString() + "\n";
				
				currentPrintState += "Double Neighbor Up:\n";
				currentPrintState += n1.doubleNeighborUp.toString() + "\n";
				currentPrintState += "\n";
	
			}
			currentPrintState += "\n----------------------------------------------------------------------------------------------------\n";
			
			operationList.append(currentPrintState);
			iterationCount++;
		}
	}
	
	public  void printHyperWeb() {
		if (debug) {
			System.out.println(currentPrintState);
		}
	}
	public  Node getRandomNode(GlobalObjectId globalObjectId2) {
		Random generator = new Random();
		int randomInsertionPoint = generator.nextInt(2147483647);
		
		WebID insertPointID = null;
		Node insertPoint  = null;
		if (hw != null && hw.nodes != null && hw.nodes.size() != 0){ //get random node from currentSegment
			for (WebID id : hw.nodes.keySet()) {
				insertPointID = id;
				break;
			}
			return insertPointID.getNode();
			
		}
		
		if (insertPointID == null) { // there are no nodes on this segment
			if (globalObjectId2 == null){
				insertPoint = next.receiveRandomNode(new GlobalObjectId());

			}
			else if (!globalObjectId2.onSameMachineAs(new GlobalObjectId())){///maybe works...
				insertPoint = next.receiveRandomNode(globalObjectId2);
			}
			
		}
			
		if (insertPoint == null)
			return null;
		else {
			System.out.println("class: " + insertPoint.getClass());
			
			WebID inserting = insertPoint.getWebID();
			insertPoint = getNearNode( randomInsertionPoint,inserting).getNode();
			
		}
	
			


		
		
		return insertPoint;
	}
	private static GlobalObjectId getGlobalObjectId() {
		try {
			String machineName = InetAddress.getLocalHost().getHostAddress();
			PortNumber p =new PortNumber(PeerCommunicator.getSingleton().serverSocket.getLocalPort());
			return new GlobalObjectId(machineName, p, null);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
	}
	private  WebID getNearNode(int bigNumber, WebID startID){
		WebID start = startID;
		int highest = start.getNumberBitsInCommon(bigNumber);
	
		boolean reached = false;
		while (!reached){
		HashSet<WebID> relations = new HashSet<WebID>();
				
		Node check = start.getNode();
		relations.addAll(check.getNeighborList());
		relations.addAll(check.getSurNeighborList());
		relations.addAll(check.getInvSurNeighborList());
		if (check.getFoldID() != null)
			relations.add(check.getFoldID());
		if (check.getSurrogateFoldID() != null)
			relations.add(check.getSurrogateFoldID());
		reached = true;
		for (WebID w : relations){
			int number  = w.getNumberBitsInCommon(bigNumber);
			if (number > highest){
				start = w;
				
				reached = false;
				
				highest = number;
			}
			
		}
		
		}
		return start;
	}
	public  Node addToHyperWeb(Node insertPoint) {
		
		
		startPointInfo = insertPoint.getWebId();
		debugInfo("Trying to insert at " + insertPoint.getWebId());
		Node returnNode = insertPoint.getDownState().addToNode(insertPoint);
		System.out.println();
		return returnNode;
	}
	public Node getSpecialNode(int start) {
		int randomInsertionPoint = start;
		
		WebID insertPointID = null;
		
		for (WebID id : nodes.keySet()) {
			insertPointID = id;
			break;
		}
		
		Node insertPoint  = getNearNode( randomInsertionPoint,insertPointID).getNode();
		// function to find closest node

		
		return insertPoint;
	}
	public  Node addToHyperWebSpecial(int start) {
		Node insertPoint = getSpecialNode(start);
		
		startPointInfo = insertPoint.getWebId();
		debugInfo("Trying to insert at " + insertPoint.getWebId());
		insertPoint.getDownState().addToNode(insertPoint);
		
		return nodes.get(0);
	}
	public void close() {
		// TODO Do something when you close the gui for the hyperweb
		hw = null;
	}
	public LocalObjectId getLocalObjectId() {
		// TODO Auto-generated method stub
		return this.hyperwebSeg.getLocalObjectId();
	}
	public void clear() {
		hw.nodes.clear();
		
	}
	public void saveDb() {
		
		String fileLocation = getGlobalObjectId().getPortNumber().toString() + "/Datbase.db";
		File f= new File(fileLocation);
		
		f.getParentFile().mkdirs();
		SerializeHelp.makeLiteral1();

    	try{
    		ObjectOutputStream oos =
    			new ObjectOutputStream(
    				new BufferedOutputStream(
    					new FileOutputStream(f)));
    		oos.write(LocalObjectId.getNextId());
    		oos.writeObject(hw);
    		oos.flush();
    		oos.close();
    	} catch(Exception e){
    		System.err.println("In communicator.ObjectDB::save(String) -- ERROR could not save ObjectDB");
    		e.printStackTrace();
    	}		
		SerializeHelp.release1();

		
	}
	public static HyperWeb loadDb() {
		String fileLocation = getGlobalObjectId().getPortNumber().toString() + "/Datbase.db";
		HyperWeb hwdb = null;
		SerializeHelp.makeLiteral1();
		try {
    		ObjectInputStream ois =
    			new ObjectInputStream(
    				new BufferedInputStream(
    					new FileInputStream(fileLocation)));
    	    int nextId = ois.read();
    		LocalObjectId.setNextId(nextId);
    		hwdb = (HyperWeb)ois.readObject();
    		ois.close();
    	} catch(Exception e){
//    		LocalObjectId.setNextId(LocalObjectId.INITIAL_ID);
//    		hashTable = new Hashtable<LocalObjectId, Object>();
    		e.printStackTrace();
    		hwdb = null;
    	}
		SerializeHelp.release1();
		return hwdb;
	}
	

}
