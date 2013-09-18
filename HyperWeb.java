package hyPeerWeb;

import database.HyperWebAction;
import database.Database2;

public class HyperWeb {
	
	public HyperWeb() {
		
	}
	
	public static void main(String[] args) {
		HyperWebAction myClass = new HyperWebAction();
		myClass.getSingleton();
		return;
	}
}
