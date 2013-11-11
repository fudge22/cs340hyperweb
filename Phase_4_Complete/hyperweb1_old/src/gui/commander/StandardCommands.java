package gui.commander;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.Main.GUI;
import gui.mapper.NodeListing;
import gui.newWindows.BroadcastWindow;
import gui.newWindows.SendWindow;
/**
 * Standard Commands are a basic set of commands that are needed to test a HyPeerWeb.  A open command line is available which may allow for more specific commands as is appropriate.
 * @author Matthew Smith
 * 
 * @domain Buttons - Actions taken on the HyPeerWeb
 * @domain Command Bar - Use of embedded functions to affect the HyPeerWeb
 *
 */
public class StandardCommands extends JPanel 
{
	/* Root of the GUI */
	private GUI main;
	
	/* Container for tha Command Field and Execute Button */
	private JPanel fieldPanel;
	
	/* Command Field for inputed Commands */
	//private JTextField commandField;
	
	/* Button to execute a command in the command Field */
	//private JButton executeButton;
	
	/* Conatainer for all basic command buttons */
	private JPanel buttonPanel;
	
	/* Basic buttons commands */
	private JButton insertNode, removeNode, sendNode, broadcastNode; 
	
	/* List nodes to display as targets */
	private Object[] nodeList;
	
	SendWindow sendWindow = null;
	BroadcastWindow broadcastWindow = null;
	
	/**
	 * Creates and intailizes the panel of basic commands as well as gathers a list of nodes
	 * 
	 * @param main - root of the GUI
	 */
	public StandardCommands(GUI main) {
		this.main = main;
		
		init();
		
		updateList();
	}
	
	public SendWindow getSendWindow(){
		return sendWindow;
	}
	
	public BroadcastWindow getBroadcastWindow(){
		return broadcastWindow;
	}
	
	public void setSendWindowToNull(){
		sendWindow = null;
	}
	
	public void setBroadcastWindowToNull(){
		broadcastWindow = null;
	}
	
	/**
	 * Initializes and sets up the GUI objects of the Class
	 */
	public void init()	{
		this.setLayout(new BorderLayout());
		
		//Build the open command Area
		fieldPanel = new JPanel(new BorderLayout());

		//Build the Basic Button Area
		buttonPanel = new JPanel(new GridLayout(1,4));
		
		//Build the insert button
		insertNode = new JButton("(+) Insert Node");
		insertNode.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				insertButtonPressed();				
			} });
		insertNode.setMnemonic(KeyEvent.VK_ADD);
		buttonPanel.add(insertNode);
		
		//Build the remove button
		removeNode = new JButton("(-) Delete Node");
		removeNode.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				removeButtonPressed();				
			} });
		removeNode.setMnemonic(KeyEvent.VK_SUBTRACT);
		buttonPanel.add(removeNode);
		
		//Build the send button
		sendNode = new JButton("Send Message");
		sendNode.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				sendButtonPressed();				
			} });
		sendNode.setMnemonic(KeyEvent.VK_S);
		buttonPanel.add(sendNode);
		
		//Build the broadcast button
		broadcastNode = new JButton("Broadcast");
		broadcastNode.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				broadcastButtonPressed();				
			} });
		broadcastNode.setMnemonic(KeyEvent.VK_B);
		buttonPanel.add(broadcastNode);
		
		add(buttonPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Gathers information of the nodes in the HyPeerWeb and stores them in a list of Nodes
	 */
	public void updateList()
	{
		nodeList = new Object[30];
		for(int i = 0 ; i <30; i++){
			nodeList[i]= Integer.toBinaryString(i);
		}
	}
	
	/**
	 * Inserts a Node into the HyPeerWeb
	 */
	public void insertButtonPressed()	{
		//TODO Phase 5 -- Add functionality for inserting a node.
		//I. Get the size of the "nodeListing" component.
		//II. If the size is greater than or equal to the max number of nodes allowed in a NodeListing
		//		(see constant in class NodeListing) print an error in the the "debugStatus" component.
		//III.Otherwise
	    //		A. Create a new node
		//		B. Get the id of the startNode from the HyPeerWeb using the selected index of the nodeListing component.
		//      C. If no node with the webId is found print an error in the "debugStatus" component.
		//      D. If a node is found it is the start node.  If so then
		//              1. invoke your "addToHyPeerWeb" command on the startNode passing in the new node created previously.
		//				2. Increase the nodeListing size (See the NodeListing class for details).
	}
	
	/**
	 *  Removes a node from the HyPeerWeb
	 */
	public void removeButtonPressed() {
		//TODO Phase 5 -- Add functionality for removing a node.
		//I. Get the size of the "nodeListing" component.
		//II. If the size equals 1, print an error in the the "debugStatus" component.
		//III. Otherwise
		//		A. Get the id of the startNode from the HyPeerWeb using the selected index of the nodeListing component.
		//      B. If no node with the id is found, print an error in the "debugStatus" component.
		//      C. Otherwise:
		//      	1. invoke your "removeFromHyPeerWeb" command on the node to be deleted.
		//			2. Decrease the nodeListing size (See the NodeListing class for details).
	}
	
	/**
	 *  Sends a message through the HyPeerWeb
	 */
	public void sendButtonPressed() {
		sendWindow = new SendWindow(main, "Send Message");
	}
	
	/**
	 * Broadcasts a message through the HyPeerWeb
	 */
	public void broadcastButtonPressed() {
		broadcastWindow = new BroadcastWindow(main, "Broadcast Message");
	}

}
