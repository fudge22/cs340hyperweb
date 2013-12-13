package gui.newWindows;

import exceptions.VisitorException;
import gui.GUISender;
import gui.GUIface;
import gui.Main.GUI;
import gui.Main.HyPeerWebDebugger;
import gui.mapper.NodeListing;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Phase6.GlobalObjectId;
import Phase6.LocalObjectId;
import Phase6.PortNumber;
import model.HyperWeb;
import model.HyperWebProxy;
import model.HyperwebFace;
import model.HyperwebFaceProxy;
import model.WebID;

public class JoinWindowPanel
	extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2789642773282035956L;
	protected GUI main;
	protected JLabel HostLabel;
	protected JLabel PortLabel;
	protected JTextField Host;
    protected JTextField Port;
    protected JButton joinButton;
    protected JoinWindow jw;
    
    public JoinWindowPanel(GUI main, JoinWindow joinWindow) {
        //super(new GridBagLayout());
    	super(new GridLayout(2, 1));
    	this.main = main;
    	jw = joinWindow;
    	
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

		
		Host.setText("127.0.0.1");
		Port.setText("8080");
		
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
    	//now I need to create the hyperwebFace object to connect the gui to to the other process
    	//GUIface.getSingleton().setHyperwebConnection(host, port);
    	PortNumber newPort = new PortNumber(port);
		GlobalObjectId hyperwebID = new GlobalObjectId(host, newPort, new LocalObjectId(-2147483648));
		
    	GUI.getSingleton().addHyperWeb(new HyperwebFaceProxy(hyperwebID));
    	GUIface.getSingleton().setDestination(hyperwebID);
//    	GUI.getSingleton().addHyperWeb(HyperwebFace.getSingleton());
  
//    	NodeListing nodeListing = main.getHyPeerWebDebugger().getMapper().getNodeListing();
//		DefaultListModel listModel = nodeListing.getNodeListModel();
//
//		//needs to use the proxy here to access all of these values, maybe a new method is needed to return
//		//all of the different keys
//		for (WebID key : HyperWeb.getSingleton().nodes.keySet()) { //added to import
//			listModel.addElement(key);
//			nodeListing.increaseListSize();
//			
//		}
    	 	
    	jw.dispose();
    }

private void sendButtonPressed2(){
    
    	String host = this.Host.getText();
    	int port;
    	//now I need to create the hyperwebFace object to connect the gui to to the other process
    	//GUIface.getSingleton().setHyperwebConnection(host, port);
    	
//		GlobalObjectId hyperwebID = new GlobalObjectId(host, newPort, new LocalObjectId(-2147483648));
//		
//    	GUI.getSingleton().addHyperWeb(new HyperwebFaceProxy(new GlobalObjectId(hyperwebID)));
//    	GUI.getSingleton().addHyperWeb(HyperwebFace.getSingleton());
  
//    	NodeListing nodeListing = main.getHyPeerWebDebugger().getMapper().getNodeListing();
//		DefaultListModel listModel = nodeListing.getNodeListModel();
//
//		//needs to use the proxy here to access all of these values, maybe a new method is needed to return
//		//all of the different keys
//		for (WebID key : HyperWeb.getSingleton().nodes.keySet()) { //added to import
//			listModel.addElement(key);
//			nodeListing.increaseListSize();
//			
//		}
    	
        try {
            port = Integer.parseInt(this.Port.getText());
        }
        catch (Exception e) {
            port = 3000;
        }
        
        if (host == "")
        {
            host = "127.0.0.1";
        }
        
        if (main.getCurrentSegment() == null) {
        	main.setCurrentSegment(new GlobalObjectId(host,new PortNumber(port),new LocalObjectId(-100)));
        }
        
        main.connectToSegment();
        
        main.printToTracePanel("\nConnecting to HyPeerWeb segment.");
//        main.getHyPeerWebDebugger().getStandardCommands().closeJoinSessionWindow();
    }

}
