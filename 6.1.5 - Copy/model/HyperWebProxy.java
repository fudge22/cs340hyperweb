package model;

import Phase6.*;
import simulation.NodeInterface;


public class HyperWebProxy
    extends HyperWeb
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7664623464146554864L;
	private GlobalObjectId globalObjectId;

//    public HyperWebProxy(GlobalObjectId globalObjectId){
//        this.globalObjectId = globalObjectId;
//    }

    public HyperWebProxy(GlobalObjectId globalObjectId){
    	super();    
    	this.globalObjectId = globalObjectId;
    }
    public void close(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.HyperWeb", "close", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void initialize(GlobalObjectId p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "peerComunicator.GlobalObjectId";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.HyperWeb", "initialize", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public NodeInterface getNode(int p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "int";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.HyperWeb", "getNode", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (NodeInterface)result;
    }

    public Node getNode(model.WebID p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "model.WebID";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.HyperWeb", "getNode", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Node)result;
    }

//    public static void changePort(int p0){
//        String[] parameterTypeNames = new String[1];
//        parameterTypeNames[0] = "int";
//        Object[] actualParameters = new Object[1];
//        actualParameters[0] = p0;
//        Command command = new Command(globalObjectId.getLocalObjectId(), "model.HyperWeb", "changePort", parameterTypeNames, actualParameters, false);
//        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
//    }

//    public static model.HyperWeb getSingleton(){
//        String[] parameterTypeNames = new String[0];
//        Object[] actualParameters = new Object[0];
//        Command command = new Command(globalObjectId.getLocalObjectId(), "model.HyperWeb", "getSingleton", parameterTypeNames, actualParameters, true);
//        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
//        return (model.HyperWeb)result;
//    }
    
    public Phase6.GlobalObjectId getGlobalObjectId(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.HyperWeb", "getGlobalObjectId", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Phase6.GlobalObjectId)result;
    }

    public NodeInterface[] getOrderedListOfNodes(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.HyperWeb", "getOrderedListOfNodes", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (NodeInterface[])result;
    }

    public NodeInterface[] allNodes(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.HyperWeb", "allNodes", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (NodeInterface[])result;
    }

    public  Node addNode(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.HyperWeb", "addNode", parameterTypeNames, actualParameters, false);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Node)result;
    }

    public Node addToEmptyHyperWeb(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.HyperWeb", "addToEmptyHyperWeb", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Node)result;
    }

    public Node addSecondNode(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.HyperWeb", "addSecondNode", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Node)result;
    }

    public Node addToHyperWeb(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.HyperWeb", "addToHyperWeb", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Node)result;
    }

    public void updateInfoString(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.HyperWeb", "updateInfoString", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void printHyperWeb(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.HyperWeb", "printHyperWeb", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void loadHyperWeb(java.util.HashMap p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "java.util.HashMap";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.HyperWeb", "loadHyperWeb", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void putWrapper(model.WebID p0, Node p1){
        String[] parameterTypeNames = new String[2];
        parameterTypeNames[0] = "model.WebID";
        parameterTypeNames[1] = "model.Node";
        Object[] actualParameters = new Object[2];
        actualParameters[0] = p0;
        actualParameters[1] = p1;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.HyperWeb", "putWrapper", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public Node removeNode(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.HyperWeb", "removeNode", parameterTypeNames, actualParameters, false);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Node)result;
    }

    public Node getRandomNode(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.HyperWeb", "getRandomNode", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Node)result;
    }

    public LocalObjectId getLocalObjectId(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.HyperWeb", "getLocalObjectId", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (LocalObjectId)result;
    }

    public boolean equals(java.lang.Object p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "java.lang.Object";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "java.lang.Object", "equals", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Boolean)result;
    }

    public java.lang.String toString(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "java.lang.Object", "toString", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (java.lang.String)result;
    }

    public int hashCode(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "java.lang.Object", "hashCode", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Integer)result;
    }
    
    public void connectGUI(GlobalObjectId guiGlobalID) {
        final String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "Phase6.GlobalObjectId";
        final Object[] actualParameters = new Object[1];
        actualParameters[0] = guiGlobalID;
        final Command command = new Command(globalObjectId.getLocalObjectId(), "model.HyperWeb", "addGUI", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

}
