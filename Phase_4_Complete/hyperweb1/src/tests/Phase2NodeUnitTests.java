package tests;

import static org.junit.Assert.*;

import java.util.HashMap;

import javax.xml.crypto.NodeSetData;

import model.HyperWeb;
import model.Node;
import model.WebID;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import simulation.Validator;

import exceptions.WebIDException;

public class Phase2NodeUnitTests {
	
	private static HyperWeb hw;
	private static Validator v;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		hw = new HyperWeb();	
		v = new Validator(hw);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setup() {
	}
	
	@After
	public void teardown() {
	}
	
	@Test
	public void AddFirstNode() throws WebIDException{
		Node firstTestNode = Node.addToEmptyHyperWeb();
		assertEquals(firstTestNode, Node.getNode(new WebID(0)));
	}
	
	@Test
	public void AddSecondNode() throws WebIDException{
		Node.addToEmptyHyperWeb();
		Node secondTestNode = Node.addSecondNode();
		assertEquals(secondTestNode, Node.getNode(new WebID(1)));
	}
	
	@Test
	public void AddThirdNode() throws WebIDException{
		Node.addToEmptyHyperWeb();
		Node.addSecondNode();
		Node.addNode(); 
		assertEquals(Node.getNode(new WebID(2)).getParent().getWebId(),0);
	}
	
	
//	public static void main(String[] args) {
//		
//		String[] testClasses = new String[] {
//				"server.ServerUnitTests"
//		};
//
//		org.junit.runner.JUnitCore.main(testClasses);
//	}
}
