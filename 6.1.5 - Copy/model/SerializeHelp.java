package model;

import java.util.concurrent.Semaphore;

public class SerializeHelp {

	private static boolean literal = false;
	private static Semaphore s = new Semaphore(1000);
	
	public static boolean makeLiteral1() {
//		try {
//			s.acquire();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		literal = true;
		boolean temp = literal;
		return temp;
		
	}
	
	public static void release1(){
//		s.release();
		literal = false;
	}

	public static boolean getLiteral1() {
		
		return literal;
	}
	
	}

