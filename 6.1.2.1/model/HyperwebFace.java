package model;

import visitor.SendBroadcast;
import exceptions.HyperWebException;
import exceptions.VisitorException;
import gui.GUISender;

public class HyperwebFace {
	
//	private static HyperWeb myWeb;
	
//	public HyperwebFace() {
//	//	myWeb = HyperWeb.getSingleton();
//	}
	
	
	public HyperwebFace(){
		
	
	}
	
	public void HyperwebStart() {
		
	}

	public HyperWeb receiveSingleton() {
		return HyperWeb.getSingleton();
	}
	
	public static HyperwebFace getSingleton(){
		
		return new HyperwebFace();
		
	
	}
	/**
	 * Function to add a random node to the Hyperweb
	 * 
	 * @return Node that was added to the Hyperweb or null if there was a problem
	 */
	public Node receiveAdd(){
		Node myNode = null;
		
			myNode = HyperWeb.getSingleton().addNode();

		return myNode;
	}
	
	/**
	 * Function to add a specific node to the Hyperweb
	 * 
	 * @return Node that was added to the Hyperweb or null if there was a problem
	 */
	public Node receiveAdd(int i){
		Node myNode = null;

			myNode = HyperWeb.getSingleton().addNode();

		return myNode;
	}
	
	/**
	 * Function to delete a random node to the Hyperweb
	 * 
	 * @return Node that was added to the Hyperweb or null if there was a problem
	 */
	public Node receiveDelete(){
		Node myNode = null;

			myNode = HyperWeb.getSingleton().removeNode();

		return myNode;
	}
	
	/**
	 * Function to delete a specific node to the Hyperweb
	 * 
	 * @return Node that was added to the Hyperweb or null if there was a problem
	 */
	public Node receiveDelete(int i){
		Node myNode = null;

			myNode = HyperWeb.getSingleton().removeNode(i);

		return myNode;
	}
	
	public String receiveSend(GUISender myVisit, int endNode){
		
		System.out.println("send is called");
		String visitPath;
		try {
			myVisit.send(endNode);
		} catch (VisitorException e) {
			return null;
		}
		visitPath = (String) myVisit.getParameters().get("finalString");
		return visitPath;
	}
	
	public String receiveBroadcast(SendBroadcast myCast, int startNode){
		String visitPath;
		try {
			myCast.send(startNode);
		} catch (VisitorException e) {
			return null;
		}
		visitPath = (String) myCast.getParameters().get("pathString");
		visitPath = visitPath + "Broadcast complete.\n";
		return visitPath;
	}
	
	public void receiveClose(){
		
	}

	

		
}
