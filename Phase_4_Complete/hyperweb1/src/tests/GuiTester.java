package tests;

import gui.Main.GUI;
import model.HyperWeb;

public class GuiTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HyperWeb hw = new HyperWeb();
		for(int i = 0; i < 10; i++) {
			//System.out.println(i);
			hw.addNode();
		}
		GUI gui = new GUI(hw);
	}

}
