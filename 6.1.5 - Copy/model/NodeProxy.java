package model;

import java.util.ArrayList;

import visitor.BroadcastVisitor;
import visitor.Contents;
import visitor.Parameters;
import visitor.SendVisitor;
import Phase6.*;
public class NodeProxy
    extends Node
{
    private GlobalObjectId globalObjectId;

    public NodeProxy(GlobalObjectId globalObjectId){
        this.globalObjectId = globalObjectId;
    }

    public boolean equals(java.lang.Object p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "java.lang.Object";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "equals", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Boolean)result;
    }

//    public  Integer compareTo(N p0){
//        String[] parameterTypeNames = new String[1];
//        parameterTypeNames[0] = "java.lang.Object";
//        Object[] actualParameters = new Object[1];
//        actualParameters[0] = p0;
//        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "compareTo", parameterTypeNames, actualParameters, true);
//        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
//        return (Integer)result;
//    }
    private Object readResolve(){
//		if the object being deserialized is a proxy, but should reference to something real, than change it to a real object
			if ( ObjectDB.getSingleton().getValue(gid.getLocalObjectId()) == null){
//				SerializeHelp.release();
				return this;
			}
			else {
//				SerializeHelp.release();
				return ObjectDB.getSingleton().getValue(gid.getLocalObjectId());
			}
			
		}

    public int compareTo( Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = " Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "compareTo", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Integer)result;
    }

    public  Node getParent(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getParent", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Node)result;
    }

    public void accept(BroadcastVisitor p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "gui.BroadcastVisitor";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "accept", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void accept(SendVisitor p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "gui.SendVisitor";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "accept", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public java.util.HashSet getAllUpRelations(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getAllUpRelations", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (java.util.HashSet)result;
    }

    public  ArrayList<WebID> getInvSurNeighborList(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getInvSurNeighborList", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (ArrayList<WebID>)result;
    }

    public model.WebID getInvSurrogateFoldID(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getInvSurrogateFoldID", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (model.WebID)result;
    }

    public model.WebID getWebID(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getWebID", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (model.WebID)result;
    }

    public void setWebID(model.WebID p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "model.WebID";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "setWebID", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public int getHeight(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getHeight", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Integer)result;
    }

    public void setHeight(int p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "int";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "setHeight", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public model.WebID getFoldID(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getFoldID", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (model.WebID)result;
    }

    public void setFoldID(model.WebID p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "model.WebID";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "setFoldID", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public model.WebID getSurrogateFoldID(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getSurrogateFoldID", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (model.WebID)result;
    }

    public void setSurrogateFoldID(model.WebID p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "model.WebID";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "setSurrogateFoldID", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void setInvSurrogateFoldID(model.WebID p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "model.WebID";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "setInvSurrogateFoldID", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public  ArrayList<WebID> getNeighborList(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getNeighborList", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (ArrayList<WebID>)result;
    }

    public void setNeighborList( ArrayList<WebID> p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = " java.util.ArrayList";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "setNeighborList", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public  ArrayList<WebID> getSurNeighborList(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getSurNeighborList", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (ArrayList<WebID>)result;
    }

    public void setSurNeighborList( ArrayList<WebID> p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = " java.util.ArrayList";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "setSurNeighborList", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void setInvSurNeighborList( ArrayList<WebID> p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = " java.util.ArrayList";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "setInvSurNeighborList", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public states.FoldState getFoldState(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getFoldState", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (states.FoldState)result;
    }

    public void setFoldState(states.FoldState p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "states.FoldState";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "setFoldState", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public int getFoldStateInt(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getFoldStateInt", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Integer)result;
    }

    public states.DownState getDownState(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getDownState", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (states.DownState)result;
    }

    public void setDownState(states.DownState p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "states.DownState";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "setDownState", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public int getDownStateInt(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getDownStateInt", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Integer)result;
    }

    public states.UpState getUpState(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getUpState", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (states.UpState)result;
    }

    public void setUpState(states.UpState p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "states.UpState";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "setUpState", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public int getUpStateInt(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getUpStateInt", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Integer)result;
    }

    public void informNeighbors(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "informNeighbors", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void addNeighbor(model.WebID p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "model.WebID";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "addNeighbor", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void informSurNeighbors(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "informSurNeighbors", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void addInvSurNeighbor(model.WebID p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "model.WebID";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "addInvSurNeighbor", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void informInvSurNeighbors(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "informInvSurNeighbors", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void removeSurNeighbor(model.WebID p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "model.WebID";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "removeSurNeighbor", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void removeInvSurNeighbor(model.WebID p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "model.WebID";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "removeInvSurNeighbor", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public model.Node insertChildNode(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "insertChildNode", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (model.Node)result;
    }

    public model.WebID getChildNodeID(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getChildNodeID", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (model.WebID)result;
    }

    public void increaseHeight(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "increaseHeight", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void updateAllNeighborTypes(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "updateAllNeighborTypes", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void setCurrentChild(model.WebID p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "model.WebID";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "setCurrentChild", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void updatePointers(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "updatePointers", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void updateNeighborHeightRelations(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "updateNeighborHeightRelations", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void decreaseHeight(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "decreaseHeight", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public java.util.ArrayList getAll2Hops(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getAll2Hops", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (java.util.ArrayList)result;
    }

    public int getWebId(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getWebId", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Integer)result;
    }

    public Node[] getSurrogateNeighbors(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getSurrogateNeighbors", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Node[])result;
    }

    public Node[] getInverseSurrogateNeighbors(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getInverseSurrogateNeighbors", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Node[])result;
    }

    public  Node getFold(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getFold", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Node)result;
    }

    public  Node getSurrogateFold(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getSurrogateFold", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Node)result;
    }

    public  Node getInverseSurrogateFold(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getInverseSurrogateFold", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Node)result;
    }

    public Node[] getNeighbors(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getNeighbors", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Node[])result;
    }

    public void getConnectionsFrom(model.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "model.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getConnectionsFrom", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void disconnect(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "disconnect", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public model.WebID getParentNodeID(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getParentNodeID", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (model.WebID)result;
    }

    public model.WebID getCurrentChildNodeID(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getCurrentChildNodeID", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (model.WebID)result;
    }

    public void addSurNeighbor(model.WebID p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "model.WebID";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "addSurNeighbor", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void removePointersTwoHopsAway(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "removePointersTwoHopsAway", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void removeLowerNeighbor(model.WebID p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "model.WebID";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "removeLowerNeighbor", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void removeHigherNeighbor(model.WebID p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "model.WebID";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "removeHigherNeighbor", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void addLowerNeighbor(model.WebID p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "model.WebID";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "addLowerNeighbor", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void addHigherNeighbor(model.WebID p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "model.WebID";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "addHigherNeighbor", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void updateDownState(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "updateDownState", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void updateUpState(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "updateUpState", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public boolean hasDownPointers(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "hasDownPointers", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Boolean)result;
    }

    public boolean hasUpPointers(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "hasUpPointers", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Boolean)result;
    }

    public void addDownStatusTwoHopsAway(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "addDownStatusTwoHopsAway", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void removeDownStatusTwoHopsAway(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "removeDownStatusTwoHopsAway", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void addSurFold(model.WebID p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "model.WebID";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "addSurFold", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void removeSurFold(model.WebID p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "model.WebID";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "removeSurFold", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public model.WebID slideDown(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "slideDown", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (model.WebID)result;
    }

    public boolean knowsDownPointers(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "knowsDownPointers", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Boolean)result;
    }

    public void addUpStatusTwoHopsAway(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "addUpStatusTwoHopsAway", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void removeUpStatusTwoHopsAway(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "removeUpStatusTwoHopsAway", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void addInvSurFold(model.WebID p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "model.WebID";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "addInvSurFold", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void removeInvSurFold(model.WebID p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "model.WebID";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "removeInvSurFold", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public model.WebID slideUp(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "slideUp", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (model.WebID)result;
    }

    public boolean knowsUpPointers(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "knowsUpPointers", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Boolean)result;
    }

    public model.Node sendFirstNode(model.WebID p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "model.WebID";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "sendFirstNode", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (model.Node)result;
    }

    public model.Node sendNode(model.WebID p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "model.WebID";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "sendNode", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (model.Node)result;
    }

    public Parameters getContents(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.Node", "getContents", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Parameters)result;
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

}
