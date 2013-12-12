package model;

import java.util.concurrent.Semaphore;

public class SerializeHelp {

	private static boolean literal = false;
	private static Semaphore s = new Semaphore(1000);
	
	public static boolean getLiteral() {
		try {
			s.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s.release();
		boolean temp = literal;
		return temp;
		
	}
	public static void setLiteral () {
		try {
			s.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		literal = true;
		
	}
	public static void release(){
		s.release();
		literal = false;
	}
	}

