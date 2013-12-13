package Phase6;

import model.HyperWeb;
import model.HyperwebFace;

public class Server {	
	public static final int facadeID = -2147483648;//make the local id the same across all machines
	public static void main(String[] args){
		int port = Integer.parseInt(args[0]);
		
		
		PortNumber p = new PortNumber(port);
		ObjectDB.setFileLocation(port+ "/Database.db");//maybe put it in a folder corresponding to the port i.e. 8080/Database.db
		ObjectDB.getSingleton().restore(null);
        System.out.println("Server::main(String[]) ObjectDB = ");
        
        ObjectDB.getSingleton().store(new LocalObjectId(facadeID), HyperwebFace.getSingleton());
        ObjectDB.getSingleton().save();
//        ObjectDB.getSingleton().
        PortNumber.setApplicationsPortNumber(p);
//        
        ObjectDB.getSingleton().dump();
		try{
			PeerCommunicator.createPeerCommunicator(p);
	        HyperwebFace.getSingleton().setNext(args[1], args[2]); //sets the hyperweb in ring topology

			System.out.println(PeerCommunicator.getSingleton().serverSocket.getLocalPort());
			System.out.println(PeerCommunicator.getSingleton().serverSocket.getLocalSocketAddress());
			

		}catch(Exception e){
			System.err.println(e.getMessage());
			System.err.println(e.getStackTrace());
		}
	}
}

//public class Server {	
//	public static void main(String[] args){
//		ObjectDB.setFileLocation("Database.db");
//		ObjectDB.getSingleton().restore(null);
//        System.out.println("Server::main(String[]) ObjectDB = ");
//        ObjectDB.getSingleton().dump();
//		try{
//			PeerCommunicator.createPeerCommunicator();
//			System.out.println("server port: " + PeerCommunicator.getSingleton().serverSocket.getLocalPort());
//		}catch(Exception e){
//			System.err.println(e.getMessage());
//			System.err.println(e.getStackTrace());
//		}
//	}
//}