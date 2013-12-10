package gui;
import visitor.SendBroadcast;
import exceptions.VisitorException;
import model.HyperWeb;
import model.HyperwebFace;
import model.HyperwebFaceProxy;
import model.Node;
import gui.Main.GUI;
import Phase6.*;

public class GUIface {
	
	private static HyperwebFaceProxy myHyperweb;
	private static HyperwebFace myHyperwebTest;
	private static GUIface GUIfaceSingleton;
	private static GlobalObjectId globalObjectId;
	private static LocalObjectId localObjectId;
	private static String HyperwebHostName;
	private static String HyperwebPortNumber;
	private GlobalObjectId hyperwebID;
	
	private GUIface() {
		globalObjectId = new GlobalObjectId();
		localObjectId = new LocalObjectId();
		myHyperwebTest = HyperwebFace.getSingleton();
		//		myHyperwebTest = HyperwebFace.getSingleton();

//		PortNumber newPort = new PortNumber(8080);
//		hyperwebID = new GlobalObjectId("128.187.80.130", newPort, new LocalObjectId(-2147483648));
		
		
		
		myHyperwebTest = new HyperwebFaceProxy(hyperwebID);
		
		
	}
	
	public static GUIface getSingleton() {
		if(GUIfaceSingleton == null)
		{
			GUIfaceSingleton = new GUIface();
		}
		return GUIfaceSingleton;
	}
	
	public void setDestination(GlobalObjectId g){
		hyperwebID = g;
		myHyperwebTest = new HyperwebFaceProxy(hyperwebID);

		
	}
	public static Node sendAdd() {
		getSingleton();
		return myHyperwebTest.receiveAdd();
	}
	
	public static Node sendAdd(int i) {
		getSingleton();
		return myHyperwebTest.receiveAdd(i);
	}
	
	public static Node sendDelete() {
		getSingleton();
		return myHyperwebTest.receiveDelete();
	}
	
	public static Node sendDelete(int i) {
		getSingleton();
		return myHyperwebTest.receiveDelete(i);
	}
	
	public static String sendSend(GUISender myVisit, int endNode) {
		getSingleton();
		return myHyperwebTest.receiveSend(myVisit, endNode);
	}
	
	public static String sendBroadcast(SendBroadcast myCast, int startNode) {
		getSingleton();
		return myHyperwebTest.receiveBroadcast(myCast, startNode);
	}
	
	public void sendClose() {
		
	}

	public static HyperWeb getHyperwebSingleton() {
		getSingleton();
		return myHyperwebTest.receiveSingleton();
	}

	public void setHyperwebConnection(String host, int port) {
		PortNumber newPort = new PortNumber(port);
		GlobalObjectId hyperwebID = new GlobalObjectId(host, newPort, localObjectId);
		myHyperweb = new HyperwebFaceProxy(hyperwebID);
		
	}
}
