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
	
	
	public GUIface() {
		globalObjectId = new GlobalObjectId();
		localObjectId = new LocalObjectId();
		myHyperwebTest = new HyperwebFace();
	}
	
	public static GUIface getSingleton() {
		if(GUIfaceSingleton == null)
		{
			GUIfaceSingleton = new GUIface();
		}
		return GUIfaceSingleton;
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
		return myHyperwebTest.recieveDelete();
	}
	
	public static Node sendDelete(int i) {
		getSingleton();
		return myHyperwebTest.recieveDelete(i);
	}
	
	public static String sendSend(GUISender myVisit, int endNode) {
		getSingleton();
		return myHyperwebTest.recieveSend(myVisit, endNode);
	}
	
	public static String sendBroadcast(SendBroadcast myCast, int startNode) {
		getSingleton();
		return myHyperwebTest.recieveBroadcast(myCast, startNode);
	}
	
	public void sendClose() {
		
	}

	public static HyperWeb getHyperwebSingleton() {
		getSingleton();
		return myHyperweb.recieveSingleton();
	}

	public void setHyperwebConnection(String host, int port) {
		PortNumber newPort = new PortNumber(port);
		GlobalObjectId hyperwebID = new GlobalObjectId(host, newPort, localObjectId);
		myHyperweb = new HyperwebFaceProxy(hyperwebID);
		
	}
}
