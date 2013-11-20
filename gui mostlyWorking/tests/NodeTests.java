package tests;

import model.HyperWeb;
import model.Node;
import model.WebID;
import visitor.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exceptions.HyperWebException;
import exceptions.VisitorException;

import simulation.Validator;

public class NodeTests {

	private static HyperWeb hw;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		hw = new HyperWeb();	
	}

	@AfterClass
	public static void tearDownAfterClass() {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		//Validator v = new Validator(hw);
		//v.validate();
	}

	@Test 
	public void testSend() {
		for(int i = 0; i < 32; i++) {
			//System.out.println(i);
			try {
				hw.addNode();
			} catch (HyperWebException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		SendVisitor newVisitor = new SendVisitor();
		newVisitor.addParameter("message", "this sucks");
		try {
			newVisitor.send(5);
		} catch (VisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test 
	public void testboadcast() {
		for(int i = 0; i < 32; i++) {
			//System.out.println(i);
			try {
				hw.addNode();
			} catch (HyperWebException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		SendBroadcast mytest = new SendBroadcast();
		mytest.addParameter("message", "parmeters!!!");
		try {
			mytest.send(10);
		} catch (VisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
