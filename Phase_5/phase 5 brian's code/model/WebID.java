package model;

import exceptions.WebIDException;

public class WebID {
	
	int id;

	public WebID(int id) {
		if (id < 0) {
			throw new WebIDException();
		}
		this.id = id;
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
		if (o == null) {
			return false;
		}
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
		for (int x = 0; x< 32 -binString.length(); x++){
			pad += "0";
		}
		String countNum = pad + binString;
		
		int count = countNumZero(countNum);

		return count;
	}
}
