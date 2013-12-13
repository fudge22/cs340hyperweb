package gui;

import gui.Main.GUIProxy;
import gui.Main.HyPeerWebDebugger;

import java.util.ArrayList;

import Phase6.*;


public class GUIObserver
{

    private ArrayList<GUIProxy> guiList;
    
    private static GUIObserver singleton = null;
    
    private GUIObserver() {
        guiList = new ArrayList<GUIProxy>();
    }

    public static GUIObserver getSingleton() {
        if (singleton == null) {
            singleton = new GUIObserver();
        }
        
        return singleton;
    }
    
    
    public void addGUI(GlobalObjectId globalID) {
        if(globalID != null) {
            guiList.add(new GUIProxy(globalID));
        }
    }

    public HyPeerWebDebugger getHyPeerWebDebugger() {
        return null;
    }

    public void printToTracePanel(Object obj) {
       for(int i = 0; i < guiList.size(); i++) {
           guiList.get(i).printToTracePanel(obj);
       }
    }
    
    public void refreshGUI()
    {
    	for(int i = 0; i < guiList.size(); i++) {
            guiList.get(i).refreshGUI();
    	}
    }

}
