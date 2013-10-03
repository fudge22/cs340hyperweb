package tests;
import static org.junit.Assert.*;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Node;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import database.Database;
import exceptions.DatabaseException;


public class DatabaseTests {


	private static Database db;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			Database.initialize();
			db = Database.getInstance();
		}
		catch (DatabaseException e) {
			System.out.println("Could not initialize database: " + e.getMessage());
			e.printStackTrace();
		}  
		
		// build the test database tables
		try {
			db.startTransaction();
			db.buildTables(new File("database/indexer_server.sql"));
			db.endTransaction(true);
		} catch (SQLException e) {
			db.endTransaction(false);
			System.out.println("ServerException, rolling back transaction");
		}
		
		try {
			Node node1 = new Node(1);
			Node node2 = new Node(2);
			Node node3 = new Node(3);
			db.startTransaction();
			db.getDatabaseAccessor().addNode(node1);
			db.getDatabaseAccessor().addNode(node2);
			db.getDatabaseAccessor().addNode(node3);
			db.getDatabaseAccessor().addNode(new Node(4));
			db.getDatabaseAccessor().addNode(new Node(5));
			db.getDatabaseAccessor().addNode(new Node(6));
			db.getDatabaseAccessor().addNode(new Node(7));
			
			ArrayList<Integer> neighborList = new ArrayList<Integer>();
			neighborList.add(4);
			neighborList.add(5);
			db.getDatabaseAccessor().addNeighbors(node2.getWebID(), neighborList);
			/* Uncomment once the required methods are added to the accessor
			db.getDatabaseAccessor().addSurNeighbor(node3, 6);
			db.getDatabaseAccessor().addSurNeighbor(node3, 7);
			*/
			db.endTransaction(true);
		} catch (SQLException e) {
			db.endTransaction(false);
			System.out.println("Server Exception when creating a new node for testing");
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetNode() {
		try {
			int webID = 4;
			db.startTransaction();
			Node result = db.getDatabaseAccessor().getNode(webID);
			db.endTransaction(true);
			assertNotNull("getNode returned null.", result);
		} catch (Exception e) {
			db.endTransaction(false);
			fail("An error occurred. The node was not retrieved.");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddNode() {
		try {
			Node node = new Node(8);
			db.startTransaction();
			Boolean result = db.getDatabaseAccessor().addNode(node);
			db.endTransaction(true);
			assertTrue("addNode returned false.", result);
		} catch (Exception e) {
			db.endTransaction(false);
			fail("An error occurred. The node was not added.");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDeleteNode() {
		try {
			Node node = new Node(1);// dummy node with webID of 1
			db.startTransaction();
			Boolean result = db.getDatabaseAccessor().deleteNode(node);
			db.endTransaction(true);
			assertTrue("deleteNode returned false.", result);
		} catch (Exception e) {
			db.endTransaction(false);
			fail("An error occurred. The node was not deleted.");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddNeighbors() {
		try {
			ArrayList<Integer> neighborList = new ArrayList<Integer>();
			neighborList.add(10);
			neighborList.add(11);
			db.startTransaction();
			Boolean result = db.getDatabaseAccessor().addNeighbors(9, neighborList);
			db.endTransaction(true);
			assertTrue("addNeighbor returned false.", result);
		} catch (Exception e) {
			db.endTransaction(false);
			fail("An error occurred. The neighbor was not added.");
			e.printStackTrace();
		}
	}

	@Test
	public void testGetNeighbors() {
		try {
			db.startTransaction();
			List<Integer> results = db.getDatabaseAccessor().getNeighbors(2);
			db.endTransaction(true);
			assertNotNull("getNeighbors returned null.", results);
		} catch (Exception e) {
			db.endTransaction(false);
			fail("An error occurred. The node's neighbors were not retrieved.");
			e.printStackTrace();
		}
	}
	
	/*	Uncomment once the required methods are added to the accessor
	
	public void testDeleteNeighbor() {
		try {
			Node node = new Node(2);
			db.startTransaction();
			Boolean result = db.getDatabaseAccessor().deleteNeighbor(node, 5); // dummy neighbor with webid 5
			db.endTransaction(true);
			assertTrue("deleteNeighbor returned false.", result);
		} catch (Exception e) {
			db.endTransaction(false);
			fail("An error occured. The neighbor was not deleted");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddSurNeighbor() {
		try {
			Node node = new Node(3);
			int surNeighbor = 11;
			db.startTransaction();
			Boolean result = db.getDatabaseAccessor().addSurNeighbor(node, surNeighbor);
			db.endTransaction(true);
			assertTrue("addSurrgNeighbor returned false.", result);
		} catch (Exception e) {
			db.endTransaction(false);
			fail("An error occurred. The surrogate neighbor was not added.");
			e.printStackTrace();
		}
	}

	@Test
	public void testGetSurNeighbors() {
		try {
			Node node = new Node(3);
			db.startTransaction();
			Boolean result = db.getDatabaseAccessor().getSurNeighbors(node);
			db.endTransaction(true);
			assertTrue("getSurrgNeighbors returned false.", result);
		} catch (Exception e) {
			db.endTransaction(false);
			fail("An error occurred. The node's surrogate neighbors were not retrieved.");
			e.printStackTrace();
		}
	}
	
	public void testDeleteSurNeighbor() {
		try{
			Node node = new Node(3);
			db.startTransaction();
			Boolean result = db.getDatabaseAccessor().deleteSurNeighbor(node, 7); // dummy sur neighbor with webid 7
			db.endTransaction(true);
			assertTrue("deleteSurNeighbor returned false.", result);
		} catch (Exception e) {
			db.endTransaction(false);
			fail("An error occured. The SurNeighbor was not deleted");
			e.printStackTrace();
		}
	}
	*/
}
