package gui.Main;


import gui.Main.HyPeerWebDebugger;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import model.HyperWeb;
import model.HyperwebFace;

/**
 * The central GUI used to display information about the HyPeerWeb and debug information
 * 
 * @author Matthew Smith
 *
 */
public class GUI extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5929132479735942774L;

	private static GUI singleton = null;
	
	/** Main Debugger Panel**/
	private HyPeerWebDebugger debugger;
	
	private HyperwebFace hypeerweb;
	private JScrollPane scrollPane;
	
	/**
	 * Creates and initializes the GUI as being the root
	 */
	private GUI(){
		
				this.setTitle("HyPeerWeb DEBUGGER V 1.1");
				this.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent we) 
					{
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
//		if (hypeerweb != null) // So if you are not connected to a hyperweb segment, than you can still close
//			hypeerweb.close();
	}
	
	public void addHyperWeb(HyperwebFace hypeerweb){
		
		this.hypeerweb = hypeerweb;
		
	}
	
	public static GUI getSingleton(){
		if(singleton == null){
			try{
//				singleton = new GUI(hypeerweb);
				singleton = new GUI();
						
				singleton.setVisible(true);
			}
			catch(Exception e)	{
				JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR",JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				//hypeerweb.close();
				System.exit(1);
			}
		}
		return singleton;
	}
	
	/**
	 * Start Point of the Program
	 */
	public static void main (String[] args){
		GUI gui = GUI.getSingleton();
	}

	/**
	 * Retrieves the HyPeerWeb Debugging Panel
	 * @return HyPeerWebDebugger
	 */
	public HyPeerWebDebugger getHyPeerWebDebugger() {
		return debugger;
	}
	
	public HyperwebFace getHyPeerWeb(){
		return hypeerweb;
	}
	
	public void printToTracePanel(Object msg){
		debugger.getTracePanel().print(msg);
	}
	
//	public void finalize(){
//		hypeerweb.close();
//	}

}
