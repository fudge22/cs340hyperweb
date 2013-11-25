package gui.newWindows;

import exceptions.VisitorException;
import gui.GUISender;
import gui.Main.GUI;
import gui.Main.HyPeerWebDebugger;

import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;

import gui.SendVisitor;

public class SendWindowPanel
	extends JPanel
{
	protected GUI main;
	protected JLabel startingNodeLabel;
	protected JLabel endingNodeLabel;
	protected JLabel messageBoxLabel;
    protected JTextField startingNode;
    protected JTextField endingNode;
    protected JTextField messageBox;
    protected JButton sendButton;

    public SendWindowPanel(GUI main) {
        //super(new GridBagLayout());
    	super(new GridLayout(3, 1));
    	this.main = main;
    	
    	startingNodeLabel = new JLabel("Starting Node");
    	endingNodeLabel = new JLabel("Ending Node");
    	messageBoxLabel = new JLabel("Message");

        startingNode = new JTextField(3);
        endingNode = new JTextField(3);
        messageBox = new JTextField(20);	
        
		//Build the send button
		sendButton = new JButton("Send Message");
		sendButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				sendButtonPressed();				
			}
			public void windowClosing(WindowEvent we) {
				setSendWindowToNull();  
			} });

		
		JPanel startingEndingNodePanel = new JPanel();
		startingEndingNodePanel.add(startingNodeLabel);
		startingEndingNodePanel.add(startingNode);
		startingEndingNodePanel.add(endingNodeLabel);
		startingEndingNodePanel.add(endingNode);
		this.add(startingEndingNodePanel);
		
		JPanel messageNodePanel = new JPanel();
		messageNodePanel.add(messageBoxLabel);
		messageNodePanel.add(messageBox);
		this.add(messageNodePanel);
		
		this.add(sendButton);
    }
    
    private void setSendWindowToNull(){
    	main.getHyPeerWebDebugger().getStandardCommands().setSendWindowToNull();
    }
    
    private void sendButtonPressed(){
    	String message = this.messageBox.getText();
    	int startNode = Integer.parseInt(this.startingNode.getText());
    	int endNode = Integer.parseInt(this.endingNode.getText());
    	try {
			GUISender myVisit = new GUISender(endNode);
			myVisit.addParameter("message", message);
			myVisit.send(startNode);
		} catch (VisitorException e) {
			HyPeerWebDebugger debugger = main.getHyPeerWebDebugger();
			debugger.getStatus().setContent("There was an error when sending the message across the HyPeerWeb");
			//e.printStackTrace();
		}
    }
}


