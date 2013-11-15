package gui;

import java.util.HashMap;
import java.util.Map;

/**
 * Parameters includes a map of <String, Integer> that maps from a WebID to a number.
 *
 */
public class Parameters {
	Map<String, Integer> map;
	
	/**
	 * default constructor
	 */
	public Parameters(){
		super();
		map = new HashMap<String,Integer>();
	}
	
	/**
	 * Constructor to add an integer as the key with a default value of 0.
	 * 
	 * @param intToString
	 */
	public Parameters(int intToString){
		super();
		map = new HashMap<String,Integer>();
		String newKey = "" + intToString;
		map.put(newKey, 0);
	}
	
	/**
	 * Returns true of false depending on whether or not there exists a key-value pair with the given key.
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsKey(String key){
		try{
			if(map.containsKey(key)){
				return true;
			}
			else{
				return false;
			}
		}
		catch(NullPointerException npe){
			return false;
		}
	}
	
	/**
	 * Retrieves an object from the parameters with the given key.
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key){
		return map.get(key);
	}
	
	/**
	 * Inserts the key-value pair (key, value) into the parameters.
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value){//command?
		map.put(key, (int)value);
	}
}
