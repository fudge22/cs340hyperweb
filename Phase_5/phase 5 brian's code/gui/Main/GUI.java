package gui.Main;


import gui.Main.HyPeerWebDebugger;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import model.HyperWeb;

/**
 * The central GUI used to display information about the HyPeerWeb and debug information
 * 
 * @author Matthew Smith
 *
 */
public class GUI extends JFrame
{
	private static GUI singleton = null;
	
	/** Main Debugger Panel**/
	private HyPeerWebDebugger debugger;
	
	private HyperWeb hypeerweb;
	private JScrollPane scrollPane;
	
	/**
	 * Creates and initializes the GUI as being the root
	 */
	public GUI(HyperWeb hypeerweb){
		this.hypeerweb = hypeerweb;
		this.setTitle("HyPeerWeb DEBUGGER V 1.1");
		this.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent we) {
				shutdown();
			    System.exit(0);
			  }
			});
		
		debugger = new HyPeerWebDebugger(this);
		scrollPane = new JScrollPane(debugger);
		scrollPane.setPreferredSize(new Dimension(debugger.WIDTH+20, debugger.HEIGHT));
		
		this.getContentPane().add(scrollPane);
		
		this.pack();
	}
	
	private void shutdown(){
		hypeerweb.close();
	}
	
	public static GUI getSingleton(HyperWeb hypeerweb){
		if(singleton == null){
			try{
				singleton = new GUI(hypeerweb);
						
				singleton.setVisible(true);
			}
			catch(Exception e)	{
				JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR",JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				hypeerweb.close();
				System.exit(1);
			}
		}
		return singleton;
	}
	
	/**
	 * Start Point of the Program
	 */
	public static void main (String[] args){
		GUI gui = GUI.getSingleton(HyperWeb.getHyPeerWeb());
	}

	/**
	 * Retrieves the HyPeerWeb Debugging Panel
	 * @return HyPeerWebDebugger
	 */
	public HyPeerWebDebugger getHyPeerWebDebugger() {
		return debugger;
	}
	
	public HyperWeb getHyPeerWeb(){
		return hypeerweb;
	}
	
	public void printToTracePanel(Object msg){
		debugger.getTracePanel().print(msg);
	}
	
	public void finalize(){
		hypeerweb.close();
	}

}
