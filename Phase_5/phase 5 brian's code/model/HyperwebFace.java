package model;

import visitor.SendBroadcast;
import exceptions.HyperWebException;
import exceptions.VisitorException;
import gui.GUISender;

public class HyperwebFace {
	
	private static HyperWeb myWeb;
	
	public HyperwebFace() {
		myWeb = HyperWeb.getHyPeerWeb();
	}
	
	public void HyperwebStart() {
		
	}

	public HyperWeb recieveSingleton() {
		return HyperWeb.getHyPeerWeb();
	}
	
	/**
	 * Function to add a random node to the Hyperweb
	 * 
	 * @return Node that was added to the Hyperweb or null if there was a problem
	 */
	public Node receiveAdd(){
		Node myNode = null;
		try {
			myNode = myWeb.addNode();
		} catch (HyperWebException e) {
			return null;
		}
		return myNode;
	}
	
	/**
	 * Function to add a specific node to the Hyperweb
	 * 
	 * @return Node that was added to the Hyperweb or null if there was a problem
	 */
	public Node receiveAdd(int i){
		Node myNode = null;
		try {
			myNode = myWeb.addNode();
		} catch (HyperWebException e) {
			return null;
		}
		return myNode;
	}
	
	/**
	 * Function to delete a random node to the Hyperweb
	 * 
	 * @return Node that was added to the Hyperweb or null if there was a problem
	 */
	public Node recieveDelete(){
		Node myNode = null;
		try {
			myWeb.removeNode();
		} catch (HyperWebException e) {
			return null;
		}
		return myNode;
	}
	
	/**
	 * Function to delete a specific node to the Hyperweb
	 * 
	 * @return Node that was added to the Hyperweb or null if there was a problem
	 */
	public Node recieveDelete(int i){
		Node myNode = null;
		try {
			myWeb.removeNode(i);
		} catch (HyperWebException e) {
			return null;
		}
		return myNode;
	}
	
	public String recieveSend(GUISender myVisit, int endNode){
		String visitPath;
		try {
			myVisit.send(endNode);
		} catch (VisitorException e) {
			return null;
		}
		visitPath = (String) myVisit.getParameters().get("finalString");
		return visitPath;
	}
	
	public String recieveBroadcast(SendBroadcast myCast, int startNode){
		String visitPath;
		try {
			myCast.send(startNode);
		} catch (VisitorException e) {
			return null;
		}
		visitPath = (String) myCast.getParameters().get("finalString");
		return visitPath;
	}
	
	public void recieveClose(){
		
	}

		
}
