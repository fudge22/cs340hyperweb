package gui;

import java.util.HashMap;
import java.util.Map;

/**
 * Contents includes a map of <String, Integer> that maps from a WebID to a number.
 *
 */
public class Contents {
	Map<String, Integer> map;
	
	public Contents(){
		super();
		map = new HashMap<String,Integer>();
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
	 * Retrieves an object from the contents with the given key.
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key){
		return map.get(key);
	}
	
	/**
	 * Inserts the key-value pair (key, value) into the contents.
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value){
		map.put(key, (int)value);
	}
}
