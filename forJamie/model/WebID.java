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
		WebID other = (WebID) o;
		return this.id == other.id;
	}
	
	public String toString() {
		return "" + id;
	}
	
}
