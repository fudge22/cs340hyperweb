package model;

import java.io.Serializable;

import Phase6.GlobalObjectId;
import Phase6.ObjectDB;
import exceptions.WebIDException;

public class WebID implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2065008733997140841L;
	private GlobalObjectId gid;
	int id;
	
	
	public WebID(int id) {
		
		if (id < 0) {
			throw new WebIDException();
		}
		this.id = id;
		gid = new GlobalObjectId();
	}
	public WebID(int id, int i) {//no global object id
		
		if (id < 0) {
			throw new WebIDException();
		}
		this.id = id;
	}
//	
	private Object writeReplace(){
//	create the proxy that will be returned. Possibly make it conditional
		if (SerializeHelp.getLiteral()) // in case sometimes we want the real thing
			return this;
		else
			return new WebIDProxy(gid, id);
	}
	private Object readResolve(){
//	if the object being deserialized is a proxy, but should reference to something real, than change it to a real object
		if (SerializeHelp.getLiteral() ||  ObjectDB.getSingleton().getValue(gid.getLocalObjectId()) == null)
			return this;
		else 
			return ObjectDB.getSingleton().getValue(gid.getLocalObjectId());
		
			
		
		
		
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public GlobalObjectId getGid() {
		return gid;
	}
	public Integer getId() {
		System.out.println("achievement");
		return id;
	}
	public int getValue() {
		return id;
	}

	public void setValue(int id) {
		if (id < 0) {
			throw new WebIDException();
		}
		this.id = id;
	}
	
	public int hashCode() {
        return id;
    }
	
	public boolean equals(Object o) {
		WebID other = (WebID) o;
		return this.id == other.id;
	}
	
	public String toString() {
		return "" + id;
	}
	private  int countNumZero(String binString)
	{
	    int counter = 0;
	    char compare = '0';
	    for (int x = 0; x< binString.length(); x++){
	    	if (binString.charAt(x) == compare){
	    		counter++;
	    	}
	    }
	   return counter;
	}
	public int getNumberBitsInCommon( WebID w){
		int a = this.id ^ w.getValue();
		String binString = Integer.toBinaryString(a);
		String pad = "";
		for (int x = 0; x< 32 - binString.length(); x++){
			pad += "0";
		}
		String countNum = pad + binString;
		
		int count = countNumZero(countNum);

		return count;
	}
	
	public Node getNode()
	{
		return HyperWeb.getSingleton().getNode(this);
		
		
		
	}
	public int minBitRep() {
		return Integer.SIZE - Integer.numberOfLeadingZeros(this.id);
	}
}
