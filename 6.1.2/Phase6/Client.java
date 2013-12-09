package Phase6;

import gui.GUISender;

import java.net.InetAddress;

import model.HyperwebFaceProxy;
import model.Node;

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
			hw.recieveSend(g, 1);
		
//System.out.println("WebID: " + l.getWebId());
//			PeerCommunicator.getSingleton().stop();
//			System.out.println("client port: " + PeerCommunicator.getSingleton().serverSocket.getLocalPort());
//
//			GlobalObjectId testClassGlobalObjectId =
//				new GlobalObjectId(myIPAddress,
//						           PortNumber.DEFAULT_PORT_NUMBER,
//						           new LocalObjectId(Integer.MIN_VALUE)
//				                  );
//			TestClassProxy proxy = new TestClassProxy(testClassGlobalObjectId);
//			proxy.setAge(60);
//			int[] a = new int[3];
//			a[0] = 1;
//			a[1] = 2;
//			a[2] = 3;
//			int[] result = proxy.testMethod(a);
//			System.out.println("result[0] = " + result[0] + ", result[1] = " + result[1] + ", result[2] = " + result[2]);
//			System.out.println("Proxy's age = " + proxy.getAge());
//
		//	PeerCommunicator.stopConnection(hyperwebID);
			//PeerCommunicator.stopThisConnection();
		} catch(Exception e){
		    System.err.println(e.getMessage());
		    e.printStackTrace();
		}
	}
}
