package model;

import exceptions.WebIDException;

public class WebID {
	
	int id;
	
	public WebID(int id) throws WebIDException {
		if (id < 0) {
			throw new WebIDException();
		}
		this.id = id;
	}
	
	/* 
	 * find the number of bits in an int by subtracting the size of 
	 * a Java int (32) by the number of leading zeros in the number.
	 * example: the decimal number 7 is represented by 111. It is represented
	 * in java by 32 bits, so it would have 29 leading zeros. 32-29 = 3 bits
	 */
	public int numberOfBits() {
		if (id == 0) {
			return 1;
		} else {
			return Integer.SIZE - Integer.numberOfLeadingZeros(id);
		}
	}

	public int getValue() {
		return id;
	}

	public void setValue(int id) throws WebIDException {
		if (id < 0) {
			throw new WebIDException();
		}
		this.id = id;
	}
	
	public int hashCode() {
        return id;
    }
	
}
