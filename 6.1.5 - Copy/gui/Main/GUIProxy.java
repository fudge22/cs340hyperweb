package gui.Main;

import Phase6.*;
public class GUIProxy
    extends GUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6345099822344764118L;
	private GlobalObjectId globalObjectId;
	private static GUIProxy singleton;

    public GUIProxy(GlobalObjectId globalObjectId) {
        this.globalObjectId = globalObjectId;
    }

    public static GUIProxy getSingleton(GlobalObjectId globalID) {
        if (singleton == null) {
            singleton = new GUIProxy(globalID);
        }
        
        return singleton;
    }
    
    public void changeGUI(GlobalObjectId globalID) {
        globalObjectId = globalID;
    }
    

    public gui.Main.HyPeerWebDebugger getHyPeerWebDebugger(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "gui.Main.GUI", "getHyPeerWebDebugger", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (gui.Main.HyPeerWebDebugger)result;
    }

    public void printToTracePanel(java.lang.Object p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "java.lang.Object";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "gui.Main.GUI", "printToTracePanel", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void addHyperWeb(model.HyperwebFace p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "model.HyperwebFace";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "gui.Main.GUI", "addHyperWeb", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public model.HyperwebFace getHyPeerWeb(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "gui.Main.GUI", "getHyPeerWeb", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (model.HyperwebFace)result;
    }

}
