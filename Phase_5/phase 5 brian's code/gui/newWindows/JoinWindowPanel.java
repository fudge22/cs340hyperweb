package gui.newWindows;

import exceptions.VisitorException;
import gui.GUISender;
import gui.GUIface;
import gui.Main.GUI;
import gui.Main.HyPeerWebDebugger;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.HyperWeb;

public class JoinWindowPanel
	extends JPanel
{
	protected GUI main;
	protected JLabel HostLabel;
	protected JLabel PortLabel;
	protected JTextField Host;
    protected JTextField Port;
    protected JButton joinButton;

    public JoinWindowPanel(GUI main) {
        //super(new GridBagLayout());
    	super(new GridLayout(2, 1));
    	this.main = main;
    	
    	HostLabel = new JLabel("Host");
    	PortLabel = new JLabel("Port");
    	
        Host = new JTextField(15);
        Port = new JTextField(6);
        
		//Build the send button
		joinButton = new JButton("Join");
		joinButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				sendButtonPressed();				
			}
			public void windowClosing(WindowEvent we) {
				setSendWindowToNull();  
			} });

		
		JPanel startingEndingNodePanel = new JPanel();
		startingEndingNodePanel.add(HostLabel);
		startingEndingNodePanel.add(Host);
		startingEndingNodePanel.add(PortLabel);
		startingEndingNodePanel.add(Port);
		this.add(startingEndingNodePanel);
		
		this.add(joinButton);

    }
    
    private void setSendWindowToNull(){
    	main.getHyPeerWebDebugger().getStandardCommands().setJoinWindowToNull();
    }
    
    private void sendButtonPressed(){
    	String host = this.Host.getText();
    	int port = Integer.parseInt(this.Port.getText());
    	GUIface myFace = GUIface.getSingleton();
    	myFace.setHyperwebConnection(host, port);
    }
}
