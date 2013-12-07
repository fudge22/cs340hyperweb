package model;

import visitor.SendBroadcast;
import exceptions.HyperWebException;
import exceptions.VisitorException;
import gui.GUISender;

public class HyperwebFace {
	
	private static HyperWeb myWeb;
	
	public HyperwebFace() {
		myWeb = HyperWeb.getSingleton();
	}
	
	public void HyperwebStart() {
		
	}

	public HyperWeb receiveSingleton() {
		return HyperWeb.getSingleton();
	}
	
	/**
	 * Function to add a random node to the Hyperweb
	 * 
	 * @return Node that was added to the Hyperweb or null if there was a problem
	 */
	public Node receiveAdd(){
		Node myNode = null;
		//try {
			myNode = HyperWeb.getSingleton().addNode();
//		} catch (HyperWebException e) {
//			return null;
//		}
		return myNode;
	}
	
	/**
	 * Function to add a specific node to the Hyperweb
	 * 
	 * @return Node that was added to the Hyperweb or null if there was a problem
	 */
	public Node receiveAdd(int i){
		Node myNode = null;
//		try {
			myNode = HyperWeb.getSingleton().addNode();
//		} catch (HyperWebException e) {
//			return null;
//		}
		return myNode;
	}
	
	/**
	 * Function to delete a random node to the Hyperweb
	 * 
	 * @return Node that was added to the Hyperweb or null if there was a problem
	 */
	public Node receiveDelete(){
		Node myNode = null;
//		try {
			myNode = HyperWeb.getSingleton().removeNode();
//		} catch (HyperWebException e) {
//			return null;
//		}
		return myNode;
	}
	
	/**
	 * Function to delete a specific node to the Hyperweb
	 * 
	 * @return Node that was added to the Hyperweb or null if there was a problem
	 */
	public Node receiveDelete(int i){
		Node myNode = null;
//		try {
			myNode = myWeb.removeNode(i);
//		} catch (HyperWebException e) {
//			return null;
//		}
		return myNode;
	}
	
	public String receiveSend(GUISender myVisit, int endNode){
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
		try {
			myCast.send(startNode);
		} catch (VisitorException e) {
			return null;
		}
		return "Broadcast Complete";
	}
	
	public void receiveClose(){
		
	}

		
}
