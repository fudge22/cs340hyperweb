package tests;

import static org.junit.Assert.*;

import model.HyperWeb;
import model.Node;
import model.WebID;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exceptions.HyperWebException;
import exceptions.VisitorException;

import simulation.Validator;
import gui.SendVisitor;

public class WhiteboxTests {

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
		hw = new HyperWeb();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * A test to see if the loop at line 1306 which looks through each and every one of the
	 * inverse surrogate neighbors are closer. We want to test to see if the code works when
	 * there are zero items int the list. 
	 */

	@Test
	public void visitorLoopTestingZero() {
		for(int i = 0; i < 4; i++){
			try {
				hw.addNode();
			} catch (HyperWebException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		SendVisitor visit;
		try {
			visit = new SendVisitor(2);
			visit.send(0);
		} catch (VisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	/**
	 * A test to see if the loop at line 1315 which looks through each and every one of the
	 * inverse surrogate neighbors are closer. We want to test to see if the code works when
	 * there is only one item in the list. 
	 */
	@Test
	public void visitorLoopTestingOne() {
		for(int i = 0; i < 1; i++){
			try {
				hw.addNode();
			} catch (HyperWebException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		SendVisitor visit;
		try {
			visit = new SendVisitor(1);
			visit.send(0);
		} catch (VisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * A test to see if the loop at line 1315 which looks through each and every one of the
	 * inverse surrogate neighbors are closer. We want to test to see if the code works when
	 * there are many items in the list. This list will cause any neighbor to have at most 
	 * 15 neighbors.
	 * we test with 16384 nodes
	 */
	@Test
	public void visitorLoopTestingMany() {
		int test = 256;
		for(int i = 0; i < test; i++) {
			try {
				hw.addNode();
			} catch (HyperWebException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(Integer.toBinaryString(test));
		SendVisitor visit;
		try {
			visit = new SendVisitor(3);
			visit.send(0);
		} catch (VisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * A test of the comparison at line 1317 to see if it works when a new webID is further
	 * from the targetID than the last ID given
	 * by going from node two to node 3, we can test if node Zero ends up being closer than
	 * node 3
	 */
	@Test
	public void visitorRelationalTestingLess() {
		for(int i = 0; i < 4; i++) {
			try {
				hw.addNode();
			} catch (HyperWebException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		SendVisitor visit;
		try {
			visit = new SendVisitor(3);
			visit.send(2);
		} catch (VisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * A test of the comparison at line 1317 to see if it works when a new webID is the same
	 * distacnce from the target node as the last given ID
	 */
	@Test
	public void visitorRelationalTestingEqual() {
		for(int i = 0; i < 8; i++) {
			try {
				hw.addNode();
			} catch (HyperWebException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		SendVisitor visit;
		try {
			visit = new SendVisitor(3);
			visit.send(0);	
		} catch (VisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * A test of the comparison at line 1317 to see if it works when a new webID is greater
	 * distance from the target node as the last given ID
	 */
	@Test
	public void visitorRelationalTestingGreater() {
		for(int i = 0; i < 4; i++) {
			try {
				hw.addNode();
			} catch (HyperWebException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		SendVisitor visit;
		try {
			visit = new SendVisitor(2);
			visit.send(0);
		} catch (VisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	/**
	 * Test to see if something we ask for is out of bounds
	 * In this test, we will check to see if having a negative one thousand for a target webID causes problems
	 */
	@Test
	public void internalBoundryValueTestingNThousand(){
		for(int i = 0; i < 16; i++){
			try {
				hw.addNode();
			} catch (HyperWebException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		SendVisitor visit;
		try {
			visit = new SendVisitor(-1000);
			visit.send(0);
		} catch (VisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Test to see if something we ask for is out of bounds
	 * In this test, we will check to see if having a negative ten for a target webID causes problems
	 */
	@Test
	public void internalBoundryValueTestingNTen(){
		for(int i = 0; i < 16; i++){
			try {
				hw.addNode();
			} catch (HyperWebException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		SendVisitor visit = null;
		try {
			visit = new SendVisitor(-10);
			visit.send(0);
		} catch (VisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Test to see if something we ask for is out of bounds
	 * In this test, we will check to see if having a negative one for a target webID causes problems
	 */
	@Test
	public void internalBoundryValueTestingNOne(){
		for(int i = 0; i < 16; i++){
			try {
				hw.addNode();
			} catch (HyperWebException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		SendVisitor visit = null;
		try {
			visit = new SendVisitor(-1);
			visit.send(0);
		} catch (VisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Test to see if something we ask for is out of bounds
	 * In this test, we will check to see if having a zero for a target webID causes problems
	 */
	@Test
	public void internalBoundryValueTestingZero(){
		for(int i = 0; i < 16; i++){
			try {
				hw.addNode();
			} catch (HyperWebException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		SendVisitor visit;
		try {
			visit = new SendVisitor(0);
			visit.send(0);
		} catch (VisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * Test to see if something we ask for is out of bounds
	 * In this test, we will check to see if having a positive 1 for a target webID causes problems
	 */
	@Test
	public void internalBoundryValueTestingOne(){
		for(int i = 0; i < 16; i++){
			try {
				hw.addNode();
			} catch (HyperWebException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		SendVisitor visit;
		try {
			visit = new SendVisitor(1);
			visit.send(0);
		} catch (VisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * Test to see if something we ask for is out of bounds
	 * In this test, we will check to see if having a positive ten for a target webID causes problems
	 */
	@Test
	public void internalBoundryValueTestingTen(){
		for(int i = 0; i < 16; i++){
			try {
				hw.addNode();
			} catch (HyperWebException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		SendVisitor visit;
		try {
			visit = new SendVisitor(10);
			visit.send(0);
		} catch (VisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * Test to see if something we ask for is out of bounds
	 * In this test, we will check to see if having a positive one thousand for a target webID causes problems
	 */
	@Test
	public void internalBoundryValueTestingThousand(){
		for(int i = 0; i < 16; i++){
			try {
				hw.addNode();
			} catch (HyperWebException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		SendVisitor visit;
		try {
			visit = new SendVisitor(1000);
			visit.send(0);
		} catch (VisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * Test to see if we ever hit the last loop of the sendVisitor code, in which we look for surrogate neighbors
	 * while finding the last node. in previous cases, we have gone though every other instance of the variables
	 * in the funcitons, being greater than, less than, or equal. Now we are testing the line coverage of the loop
	 * where there may or may not be surrogate neighbors
	 */
	@Test
	public void visitorDataflowTestSurrogate() {
		for(int i = 0; i < 70; i++){
			try {
				hw.addNode();
			} catch (HyperWebException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		SendVisitor visit;
		try {
			visit = new SendVisitor(70);
			visit.send(18);
		} catch (VisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void visitorDataflowTestSurrogate3() {
		for(int i = 0; i < 4500; i++){
			try {
				hw.addNode();
			} catch (HyperWebException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		SendVisitor visit;
		try {
			visit = new SendVisitor(100);
			visit.send(4499);
		} catch (VisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testOneNodeVisit(){
		try {
			hw.addNode();
		} catch (HyperWebException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SendVisitor visit;
		try {
			visit = new SendVisitor(0);
			visit.send(0);
		} catch (VisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	@Test
	public void visitorDataflowTestLoopSurrogate() {
		try {
			hw.addNode();
			hw.addNode();
		} catch (HyperWebException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		hw.addSpecial(0);
		hw.addSpecial(1);
		hw.addSpecial(0);
		hw.addSpecial(3);
		hw.print();
		SendVisitor visit;
		try {
			visit = new SendVisitor(4);
			visit.send(7);
		} catch (VisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
