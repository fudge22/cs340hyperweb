package hyPeerWeb;

import database.DBAccess;
import database.Database2;

public class HyPeerWeb {
	
	public HyPeerWeb() {
		
	}
	
	public static void main(String[] args) {
		DBAccess myClass = new DBAccess();
		Database2 myDatabase = myClass.getSingleton();
		return;
	}
}
