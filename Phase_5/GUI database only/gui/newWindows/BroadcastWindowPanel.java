package gui.newWindows;

import exceptions.VisitorException;
import gui.Main.GUI;
import gui.Main.HyPeerWebDebugger;

import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;

import model.HyperWeb;

import visitor.SendBroadcast;

public class BroadcastWindowPanel
	extends JPanel
{
	protected GUI main;
	protected JLabel startingNodeLabel;
	protected JLabel messageBoxLabel;
    protected JTextField startingNode;
    protected JTextField messageBox;
    protected JButton broadcastButton;

    public BroadcastWindowPanel(GUI main) {
        //super(new GridBagLayout());
    	super(new GridLayout(3, 1));
    	this.main = main;
    	
    	startingNodeLabel = new JLabel("Starting Node");
    	messageBoxLabel = new JLabel("Message");

        startingNode = new JTextField(3);
        messageBox = new JTextField(20);	
        
		//Build the send button
		broadcastButton = new JButton("Broadcast Message");
		broadcastButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				broadcastButtonPressed();				
			} 
			public void windowClosing(WindowEvent we) {
				setBroadcastWindowToNull();  
			} });
		
		JPanel startingEndingNodePanel = new JPanel();
		startingEndingNodePanel.add(startingNodeLabel);
		startingEndingNodePanel.add(startingNode);
		this.add(startingEndingNodePanel);
		
		JPanel messageNodePanel = new JPanel();
		messageNodePanel.add(messageBoxLabel);
		messageNodePanel.add(messageBox);
		this.add(messageNodePanel);
		
		this.add(broadcastButton);

    }
    
    private void setBroadcastWindowToNull(){
    	main.getHyPeerWebDebugger().getStandardCommands().setBroadcastWindowToNull();
    }
    
    
    private void broadcastButtonPressed(){
    	int startNode = Integer.parseInt(startingNode.getText());
    	SendBroadcast myCast = new SendBroadcast();
    	myCast.addParameter("message", messageBox.getText());
    	String output = gui.GUIface.sendBroadcast(myCast, startNode);
    	if(output == null) {
    		output = "error broadcasting everywhere";
    	}
    	HyperWeb myWeb = gui.GUIface.getHyperwebSingleton();
		GUI myGui = GUI.getSingleton(myWeb);
		myGui.printToTracePanel(output);
    }
}