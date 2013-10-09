package simulation;

import model.HyperWeb;

public class runVal {

	public static void main(String[] args) {
		HyperWeb hw = new HyperWeb();
		hw.addNode();
	//	hw.addNode();
		
		Validator v = new Validator(hw);
		
		v.validate();
	
	}

}
