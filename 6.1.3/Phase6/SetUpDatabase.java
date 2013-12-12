package Phase6;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.HyperWeb;
import model.SerializeHelp;
import model.WebID;

public class SetUpDatabase {
	static WebID w;
	public static void main(String[] args){
		w = new WebID(0);
		ObjectDB.getSingleton().store(w.getGid().getLocalObjectId(), w);
		
		SerializeHelp.setLiteral();
		saveDb();
		loadDb();
		System.out.println(w);
		SerializeHelp.release();
		
	}
	
	public static void saveDb() {
		String fileLocation = "oj.db";
		
		
    	try{
    		ObjectOutputStream oos =
    			new ObjectOutputStream(
    				new BufferedOutputStream(
    					new FileOutputStream(fileLocation)));
    		oos.write(LocalObjectId.getNextId());
    		oos.writeObject(w);
    		oos.flush();
    		oos.close();
    	} catch(Exception e){
    		System.err.println("In communicator.ObjectDB::save(String) -- ERROR could not save ObjectDB");
    		e.printStackTrace();
    	}		
		
		
	}
	public static WebID loadDb() {
		String fileLocation = "oj.db";
		WebID w1 = null;
		try {
    		ObjectInputStream ois =
    			new ObjectInputStream(
    				new BufferedInputStream(
    					new FileInputStream(fileLocation)));
    	    int nextId = ois.read();
    		LocalObjectId.setNextId(nextId);
    		w1 = (WebID)ois.readObject();
    		ois.close();
    	} catch(Exception e){
//    		LocalObjectId.setNextId(LocalObjectId.INITIAL_ID);
//    		hashTable = new Hashtable<LocalObjectId, Object>();
    		e.printStackTrace();
    		w1 = null;
    	}
		
		return w1;
	}
}
