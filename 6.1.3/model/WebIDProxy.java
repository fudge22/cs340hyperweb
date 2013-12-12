package model;

import Phase6.Command;
import Phase6.GlobalObjectId;
import Phase6.ObjectDB;
import Phase6.PeerCommunicator;



public class WebIDProxy
    extends WebID
{
//    public WebIDProxy(int id) {
//		super(id);
//		// TODO Auto-generated constructor stub
//	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2776665788100883916L;
	private GlobalObjectId globalObjectId;

    public WebIDProxy(GlobalObjectId globalObjectId, int id){
    	super(id);    
    	this.globalObjectId = globalObjectId;
    }

    public boolean equals(java.lang.Object p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "java.lang.Object";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.WebID", "equals", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Boolean)result;
    }

    public java.lang.String toString(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.WebID", "toString", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (java.lang.String)result;
    }
//	private Object writeReplace(){
////		create the proxy that will be returned. Possibly make it conditional
//			if (SerializeHelp.getLiteral()) // in case sometimes we want the real thing
//				return this;
//			else
//				return new WebIDProxy(gid, id);
//		}
		private Object readResolve(){
//		if the object being deserialized is a proxy, but should reference to something real, than change it to a real object
			if (SerializeHelp.getLiteral() ||  ObjectDB.getSingleton().getValue(globalObjectId.getLocalObjectId()) == null)
				return this;
			else 
				return ObjectDB.getSingleton().getValue(globalObjectId.getLocalObjectId());
		}
//    public int hashCode(){
//        String[] parameterTypeNames = new String[0];
//        Object[] actualParameters = new Object[0];
//        Command command = new Command(globalObjectId.getLocalObjectId(), "model.WebID", "hashCode", parameterTypeNames, actualParameters, true);
//        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
//        return (Integer)result;
//    }

    public int getValue(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.WebID", "getValue", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Integer)result;
    }

    public Integer getId(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.WebID", "getId", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Integer)result;
    }

    public void setValue(int p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "int";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.WebID", "setValue", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

//    public static long getSerialversionuid(){
//        String[] parameterTypeNames = new String[0];
//        Object[] actualParameters = new Object[0];
//        Command command = new Command(globalObjectId.getLocalObjectId(), "model.WebID", "getSerialversionuid", parameterTypeNames, actualParameters, true);
//        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
//        return (Long)result;
//    }

    public GlobalObjectId getGid(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.WebID", "getGid", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (GlobalObjectId)result;
    }

    public int getNumberBitsInCommon(model.WebID p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "model.WebID";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.WebID", "getNumberBitsInCommon", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Integer)result;
    }

    public model.Node getNode(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.WebID", "getNode", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (model.Node)result;
    }

    public int minBitRep(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "model.WebID", "minBitRep", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Integer)result;
    }
    



}
