package gui.newWindows;

import gui.Main.GUI;

public class JoinWindow extends PopUpWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5715441669169952868L;
	public JoinWindow(GUI main, String title){
		super(main, title);
		super.setSize(400, 130);
	}
	@Override
	protected void addPanel() {
		panel = new JoinWindowPanel(main, this);
		this.add(panel);
	}
}
