package tests;

import static org.junit.Assert.*;

import model.HyperWeb;
import model.Node;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exceptions.HyperWebException;

import simulation.Validator;

public class FoldTests {

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
	public void removingFoldStates() {
		addMoreNodes();
		
	}
	
	public void addMoreNodes() {
		for (int i = 0; i < 8; i++) {
			try {
				hw.addNode();
			} catch (HyperWebException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			v.validate();
		}
	}
	
}
