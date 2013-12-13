package Phase6;

import model.HyperWeb;
import model.HyperwebFace;

public class Server {	
	public static final int facadeID = -2147483648;//make the local id the same across all machines
	public static void main(String[] args){
		ObjectDB.setFileLocation("Database.db");
		ObjectDB.getSingleton().restore(null);
        System.out.println("Server::main(String[]) ObjectDB = ");
        
        
        ObjectDB.getSingleton().store(new LocalObjectId(facadeID), HyperwebFace.getSingleton());
//        ObjectDB.getSingleton().
        
        ObjectDB.getSingleton().dump();
		try{
			PeerCommunicator.createPeerCommunicator();
		}catch(Exception e){
			System.err.println(e.getMessage());
			System.err.println(e.getStackTrace());
		}
	}
}
