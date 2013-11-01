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

import simulation.Validator;


public class NodeTests {

	private static HyperWeb hw;
	private static Validator v;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		hw = new HyperWeb();	
		v = new Validator(hw);
	}

	@AfterClass
	public static void tearDownAfterClass() {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	@Test
	public void DeleteMoreNodes() {
		for (int i = 0; i < 2000; i++) {
			System.out.println(i);
			hw.addNode();
			v.validate();
		}
		
		for (int i = 0; i < 1000; i++) {
			hw.removeNode();
		}
		
		System.out.println("INSERTING AGAIN!!!!!!");
		
		for (int i = 0; i < 1000; i++) {
			System.out.println(i);
			hw.addNode();
			v.validate();
		}
	}
	
	/*
	@Test 
	public void testSend() {
		for(int i = 0; i < 32; i++) {
			//System.out.println(i);
			hw.addNode();
			v.validate();
		}
		Visitor newVisitor = new SendVisitor(new WebID(3));
		newVisitor.visit(Node.getNode(new WebID(0)));
		
		newVisitor = new SendVisitor(new WebID(5));
		newVisitor.visit(Node.getNode(new WebID(10)));
		
		newVisitor = new SendVisitor(new WebID(15));
		newVisitor.visit(Node.getNode(new WebID(0)));
		
		int foo = Integer.parseInt("1001", 2);
		System.out.println(foo);
	}
*/
}
