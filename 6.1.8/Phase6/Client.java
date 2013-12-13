package Phase6;

import gui.GUISender;

import java.net.InetAddress;

import visitor.SendBroadcast;
import model.HyperwebFaceProxy;
import model.Node;
import model.WebID;

public class Client {
	@SuppressWarnings("deprecation")
	public static void main(String[] args){
		//Before running the client, run SetUpDatabase, and start the Server.
		ObjectDB.setFileLocation("ObjectDB.db");
		try{
    		String myIPAddress = InetAddress.getLocalHost().getHostAddress();
			//String myIPAddress = "128.187.80.130";
			GlobalObjectId serverGlobalObjectId =
				new GlobalObjectId(myIPAddress,
						           PortNumber.DEFAULT_PORT_NUMBER,
								   null);
			
			
			PortNumber newPort = new PortNumber(49200);
			GlobalObjectId hyperwebID = new GlobalObjectId("128.187.80.130", newPort, new LocalObjectId(-2147483648));
			
			
			
			HyperwebFaceProxy hw = new HyperwebFaceProxy(hyperwebID);
			PeerCommunicator.createPeerCommunicator(new PortNumber(49153));
			GUISender g = new GUISender();
			g.addParameter("message", "hi");
			System.out.println(hw.receiveSend(g, 1));
			

			SendBroadcast myCast = new SendBroadcast();
			myCast.addParameter("message", "bbbbbb");
//	    	myCast.addParameter("message", messageBox.getText());
//			myCast.addParameter("message", messageBox.getText());
			
			String output = hw.receiveBroadcast(myCast, 0);
	    	if(output == null) {
	    		output = "error broadcasting everywhere";
	    	}
		
	    	System.out.println(output);

		} catch(Exception e){
		    System.err.println(e.getMessage());
		    e.printStackTrace();
		}
	}
}
