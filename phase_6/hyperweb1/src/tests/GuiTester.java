package tests;

import exceptions.HyperWebException;
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
			try {
				hw.addNode();
			} catch (HyperWebException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		GUI gui = new GUI(hw);
	}

}
