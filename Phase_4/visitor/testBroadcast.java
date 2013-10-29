package visitor;

import simulation.Validator;
import model.HyperWeb;
import model.Node;
import model.WebID;

public class testBroadcast {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HyperWeb hw = new HyperWeb();	
		Validator v = new Validator(hw);
		
		for(int i = 0; i < 32; i++) {
			//System.out.println(i);
			hw.addNode();
			v.validate();
		}
		BroadcastVisitor newVisitor = new BroadcastVisitor();
		newVisitor.visit(Node.getNode(new WebID(0)));

	}

}
