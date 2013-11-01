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
		Node.initialize();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void addMoreNodes() {
		for (int i = 0; i < 4; i++) {
			
			System.out.println(i);
			hw.addNode();
			v.validate();
		}
		
		for (int i = 0; i < 2; i++) {
			
			System.out.println(i + " delete");
			hw.removeNode();
			v.validate();
		}

for (int i = 0; i < 4; i++) {
			
			System.out.println(i);
			hw.addNode();
			v.validate();
		}
//		for (int i = 0; i < 8; i++) {
//			
//			System.out.println(i);
//			hw.addNode();
//			v.validate();
//		}
//		
//		for (int i = 0; i < 4; i++) {
//			
//			System.out.println(i + " delete");
//			hw.removeNode();
//			v.validate();
//		}
//		
//		for (int i = 0; i < 2; i++) {
//			
//			System.out.println(i);
//			hw.addNode();
//			v.validate();
//		}
//		
//		for (int i = 0; i < 4; i++) {
//			
//			System.out.println(i + " delete");
//			hw.removeNode();
//			v.validate();
//		}
//		
//		for (int i = 0; i < 10000; i++) {
//			if (i % 1000 == 0)
//			System.out.println(i);
//			hw.addNode();
//			v.validate();
//		}
//		
//		for (int i = 0; i < 5000; i++) {
//			if (i % 1000 == 0)
//			System.out.println(i + " delete");
//			hw.removeNode();
//			v.validate();
//		}
//		
//		for (int i = 0; i < 50000; i++) {
//			if (i % 1000 == 0)
//			System.out.println(i);
//			hw.addNode();
//			v.validate();
//		}
//		for (int i = 0; i < 30000; i++) {
//			if (i % 1000 == 0)
//			System.out.println(i + " delete");
//			hw.removeNode();
//			v.validate();
//		}
//		
//		for (int i = 0; i < 20000; i++) {
//			if (i % 1000 == 0)
//			System.out.println(i);
//			hw.addNode();
//			v.validate();
//		}
//		for (int i = 0; i < 50000; i++) {
//			if (i % 1000 == 0)
//			System.out.println(i + " delete");
//			hw.removeNode();
//			v.validate();
//		}
	}

	@Test
	public void testInformNeighbors() {
		hwSetup5();

		Node n1 = Node.getNode(new WebID(1));
		assertTrue(n1.getNeighborList().size() == 2);

		Node n5 = new Node(new WebID(5), 3);
		n5.getNeighborList().add(n1.getWebID());
		n5.informNeighbors();

		assertTrue(n1.getNeighborList().size() == 3);
		assertTrue(n1.getNeighborList().contains(n5.getWebID()));

		hwSetup3();

		n1 = Node.getNode(new WebID(1));
		Node n4 = Node.getNode(new WebID(4));
		Node n7 = Node.getNode(new WebID(7));

		n1.updateAllNeighborTypes();
		n4.updateAllNeighborTypes();
		n7.updateAllNeighborTypes();

		assertTrue(n1.getNeighborList().size() == 2);
		assertTrue(n4.getNeighborList().size() == 2);
		assertTrue(n7.getNeighborList().size() == 2);

		n5 = new Node(new WebID(5), 3);
		n5.getNeighborList().add(n1.getWebID());
		n5.getNeighborList().add(n4.getWebID());
		n5.getNeighborList().add(n7.getWebID());
		n5.informNeighbors();

		assertTrue(n1.getNeighborList().size() == 3);
		assertTrue(n1.getNeighborList().contains(n5.getWebID()));
		assertTrue(n4.getNeighborList().size() == 3);
		assertTrue(n4.getNeighborList().contains(n5.getWebID()));
		assertTrue(n7.getNeighborList().size() == 3);
		assertTrue(n7.getNeighborList().contains(n5.getWebID()));
	}

	@Test
	public void testInformSurNeighbors() {
		hwSetup5();

		Node n0 = Node.getNode(new WebID(0));
		Node n3 = Node.getNode(new WebID(3));

		assertTrue(n0.getInvSurNeighborList().size() == 0);
		assertTrue(n3.getInvSurNeighborList().size() == 0);

		Node n5 = new Node(new WebID(5), 3);
		n5.getSurNeighborList().add(n0.getWebID());
		n5.getSurNeighborList().add(n3.getWebID());
		n5.informSurNeighbors();

		assertTrue(n0.getInvSurNeighborList().size() == 1);
		assertTrue(n0.getInvSurNeighborList().contains(n5.getWebID()));
		assertTrue(n3.getInvSurNeighborList().size() == 1);
		assertTrue(n3.getInvSurNeighborList().contains(n5.getWebID()));

		hwSetup3();

		n0 = Node.getNode(new WebID(0));
		n3 = Node.getNode(new WebID(3));

		n0.updateAllNeighborTypes();
		n3.updateAllNeighborTypes();

		assertTrue(n0.getInvSurNeighborList().size() == 0);
		assertTrue(n3.getInvSurNeighborList().size() == 0);

		n5 = new Node(new WebID(5), 3);
		assertTrue(n5.getSurNeighborList().size() == 0);
		n5.informNeighbors();

		assertTrue(n0.getInvSurNeighborList().size() == 0);
		assertTrue(n3.getInvSurNeighborList().size() == 0);
	}

	@Test
	public void testGetParent() {
		// children
		Node n1 = new Node(new WebID(2), 2);
		Node n2 = new Node(new WebID(4), 3);
		Node n3 = new Node(new WebID(8), 4);
		Node n4 = new Node(new WebID(16), 5);
		Node n5 = new Node(new WebID(13), 4);
		Node n6 = new Node(new WebID(6), 3);
		Node n7 = new Node(new WebID(31), 5);
		Node n8 = new Node(new WebID(33), 6);

		// parents
		Node n11 = new Node(new WebID(0), 1);
		Node n22 = new Node(new WebID(0), 2);
		Node n33 = new Node(new WebID(0), 3);
		Node n44 = new Node(new WebID(0), 4);
		Node n55 = new Node(new WebID(5), 3);
		Node n66 = new Node(new WebID(2), 2);
		Node n77 = new Node(new WebID(15), 4);
		Node n88 = new Node(new WebID(1), 5);

		// getParent uses Node.getNode(), put the parents in nodes
		Node.getNodeMap().put(n11.getWebID(), n11);
		Node.getNodeMap().put(n22.getWebID(), n22);
		Node.getNodeMap().put(n33.getWebID(), n33);
		Node.getNodeMap().put(n44.getWebID(), n44);
		Node.getNodeMap().put(n55.getWebID(), n55);
		Node.getNodeMap().put(n66.getWebID(), n66);
		Node.getNodeMap().put(n77.getWebID(), n77);
		Node.getNodeMap().put(n88.getWebID(), n88);

		assertEquals(n11.getWebID().getValue(), n1.getParent().getWebId());
		assertEquals(n22.getWebID().getValue(), n2.getParent().getWebId());
		assertEquals(n33.getWebID().getValue(), n3.getParent().getWebId());
		assertEquals(n44.getWebID().getValue(), n4.getParent().getWebId());
		assertEquals(n55.getWebID().getValue(), n5.getParent().getWebId());
		assertEquals(n66.getWebID().getValue(), n6.getParent().getWebId());
		assertEquals(n77.getWebID().getValue(), n7.getParent().getWebId());
		assertEquals(n88.getWebID().getValue(), n8.getParent().getWebId());
	}

	@Test
	public void testGetChildNodeID() {
		// getChildNodeID assumes heights HAVE NOT been incremented yet
		Node n1 = new Node(new WebID(0), 1);
		Node n2 = new Node(new WebID(0), 2);
		Node n3 = new Node(new WebID(0), 3);
		Node n4 = new Node(new WebID(0), 4);
		Node n5 = new Node(new WebID(5), 3);
		Node n6 = new Node(new WebID(2), 2);
		Node n7 = new Node(new WebID(15), 4);
		Node n8 = new Node(new WebID(1), 5);

		assertEquals(new WebID(2), n1.getChildNodeID());
		assertEquals(new WebID(4), n2.getChildNodeID());
		assertEquals(new WebID(8), n3.getChildNodeID());
		assertEquals(new WebID(16), n4.getChildNodeID());
		assertEquals(new WebID(13), n5.getChildNodeID());
		assertEquals(new WebID(6), n6.getChildNodeID());
		assertEquals(new WebID(31), n7.getChildNodeID());
		assertEquals(new WebID(33), n8.getChildNodeID());

	}

	@Test
	public void testUpdateAllNeighborTypes() {
		hwSetup5();
		Node n1 = Node.getNode(new WebID(1));

		assertTrue(n1.getNeighborList().size() == 2);
		assertTrue(n1.getSurNeighborList().size() == 0);
		assertTrue(n1.getNeighborList().contains(new WebID(0)));
		assertTrue(n1.getNeighborList().contains(new WebID(3)));

		hwSetup1();
		Node n4 = Node.getNode(new WebID(4));

		n4.updateAllNeighborTypes();

		assertTrue(n4.getNeighborList().size() == 2);
		assertTrue(n4.getSurNeighborList().size() == 1);
		assertTrue(n4.getNeighborList().contains(new WebID(6)));
		assertTrue(n4.getNeighborList().contains(new WebID(0)));
		assertTrue(n4.getSurNeighborList().contains(new WebID(1)));

		hwSetup2();
		Node n7 = Node.getNode(new WebID(7));

		n7.updateAllNeighborTypes();

		assertTrue(n7.getNeighborList().size() == 1);
		assertTrue(n7.getSurNeighborList().size() == 2);
		assertTrue(n7.getNeighborList().contains(new WebID(3)));
		assertTrue(n7.getSurNeighborList().contains(new WebID(2)));
		assertTrue(n7.getSurNeighborList().contains(new WebID(1)));

		hwSetup3();
		Node n6 = Node.getNode(new WebID(6));
		n7 = Node.getNode(new WebID(7));

		n6.updateAllNeighborTypes();
		n7.updateAllNeighborTypes();

		assertTrue(n6.getNeighborList().size() == 3);
		assertTrue(n6.getSurNeighborList().size() == 0);
		assertTrue(n6.getNeighborList().contains(new WebID(4)));
		assertTrue(n6.getNeighborList().contains(new WebID(7)));
		assertTrue(n6.getNeighborList().contains(new WebID(2)));

		assertTrue(n7.getNeighborList().size() == 2);
		assertTrue(n7.getSurNeighborList().size() == 1);
		assertTrue(n7.getNeighborList().contains(new WebID(3)));
		assertTrue(n7.getNeighborList().contains(new WebID(6)));
		assertTrue(n7.getSurNeighborList().contains(new WebID(1)));
	}

	private void hwSetup1() {
		Node.initialize();

		Node n0 = new Node(new WebID(0), 3);
		Node n1 = new Node(new WebID(1), 2);
		Node n2 = new Node(new WebID(2), 3);
		Node n3 = new Node(new WebID(3), 2);
		Node n4 = new Node(new WebID(4), 3);
		Node n6 = new Node(new WebID(6), 3);

		Node.getNodeMap().put(n0.getWebID(), n0);
		Node.getNodeMap().put(n1.getWebID(), n1);
		Node.getNodeMap().put(n2.getWebID(), n2);
		Node.getNodeMap().put(n3.getWebID(), n3);
		Node.getNodeMap().put(n4.getWebID(), n4);
		Node.getNodeMap().put(n6.getWebID(), n6);
	}

	private void hwSetup2() {
		Node.initialize();

		Node n0 = new Node(new WebID(0), 3);
		Node n1 = new Node(new WebID(1), 2);
		Node n2 = new Node(new WebID(2), 2);
		Node n3 = new Node(new WebID(3), 3);
		Node n4 = new Node(new WebID(4), 3);
		Node n7 = new Node(new WebID(7), 3);

		Node.getNodeMap().put(n0.getWebID(), n0);
		Node.getNodeMap().put(n1.getWebID(), n1);
		Node.getNodeMap().put(n2.getWebID(), n2);
		Node.getNodeMap().put(n3.getWebID(), n3);
		Node.getNodeMap().put(n4.getWebID(), n4);
		Node.getNodeMap().put(n7.getWebID(), n7);
	}

	private void hwSetup3() {
		Node.initialize();

		Node n0 = new Node(new WebID(0), 3);
		Node n1 = new Node(new WebID(1), 2);
		Node n2 = new Node(new WebID(2), 3);
		Node n3 = new Node(new WebID(3), 3);
		Node n4 = new Node(new WebID(4), 3);
		Node n6 = new Node(new WebID(6), 3);
		Node n7 = new Node(new WebID(7), 3);

		Node.getNodeMap().put(n0.getWebID(), n0);
		Node.getNodeMap().put(n1.getWebID(), n1);
		Node.getNodeMap().put(n2.getWebID(), n2);
		Node.getNodeMap().put(n3.getWebID(), n3);
		Node.getNodeMap().put(n4.getWebID(), n4);
		Node.getNodeMap().put(n6.getWebID(), n6);
		Node.getNodeMap().put(n7.getWebID(), n7);
	}

	// 2 dimensional hypercube
	private void hwSetup5() {
		Node.initialize();

		Node n0 = new Node(new WebID(0), 2);
		Node n1 = new Node(new WebID(1), 2);
		Node n2 = new Node(new WebID(2), 2);
		Node n3 = new Node(new WebID(3), 2);

		Node.getNodeMap().put(n0.getWebID(), n0);
		Node.getNodeMap().put(n1.getWebID(), n1);
		Node.getNodeMap().put(n2.getWebID(), n2);
		Node.getNodeMap().put(n3.getWebID(), n3);

		n0.updateAllNeighborTypes();
		n1.updateAllNeighborTypes();
		n2.updateAllNeighborTypes();
		n3.updateAllNeighborTypes();
	}

}
