package gui.newWindows;

import gui.Main.GUI;

public class JoinWindow extends PopUpWindow {
	public JoinWindow(GUI main, String title){
		super(main, title);
		super.setSize(400, 130);
	}
	@Override
	protected void addPanel() {
		panel = new JoinWindowPanel(main);
		this.add(panel);
	}
}
