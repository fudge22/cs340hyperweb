
public class DatabaseTests {


	private static Database db;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			Database.initialize();		
		}
		catch (ServerException e) {
			System.out.println("Could not initialize database: " + e.getMessage());
			e.printStackTrace();
		}  
		
		db = new Database();
		
		// build the test database tables
		try {
			db.startTransaction();
			db.buildTables(new File("database/indexer_server.sql"));
			db.endTransaction(true);
		} catch (SQLException e) {
			db.endTransaction(false);
			System.out.println("ServerException, rolling back transaction");
		}
		
		// import test info if needed
		String[] args = {"./test/server/testInfo.xml"};
		DataImporter.main(args);
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
	public void testAddNode() {
		try {
			Node node = new Node();
			db.startTransaction();
			Boolean result = db.getNodes().addNode(node);
			db.endTransaction(true);
			assertTrue("addNode returned false.", result);
		} catch (Exception e) {
			db.endTransaction(false);
			fail("An error occurred. The node was not added.");
			e.printStackTrace();
		}
	}

	@Test
	public void testGetNode() {
		try {
			Node node = new Node();
			db.startTransaction();
			Boolean result = db.getNodes().getNode(node);
			db.endTransaction(true);
			assertTrue("getNode returned false.", result);
		} catch (Exception e) {
			db.endTransaction(false);
			fail("An error occurred. The node was not retrieved.");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddNeighbor() {
		try {
			Node node = new Node();
			Node neighbor = new Node();
			db.startTransaction();
			Boolean result = db.getNodes().addNeighbor(node, neighbor);
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
			Node node = new Node();
			db.startTransaction();
			Boolean result = db.getNodes().getNeighbors(node);
			db.endTransaction(true);
			assertTrue("getNeighbors returned false.", result);
		} catch (Exception e) {
			db.endTransaction(false);
			fail("An error occurred. The node's neighbors were not retrieved.");
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testAddSurrgNeighbor() {
		try {
			Node node = new Node();
			Node sNeighbor = new Node();
			db.startTransaction();
			Boolean result = db.getNodes().addSurrgNeighbor(node, sNeighbor);
			db.endTransaction(true);
			assertTrue("addSurrgNeighbor returned false.", result);
		} catch (Exception e) {
			db.endTransaction(false);
			fail("An error occurred. The surrogate neighbor was not added.");
			e.printStackTrace();
		}
	}

	@Test
	public void testGetSurrgNeighbors() {
		try {
			Node node = new Node();
			db.startTransaction();
			Boolean result = db.getNodes().getSurrgNeighbors(node);
			db.endTransaction(true);
			assertTrue("getSurrgNeighbors returned false.", result);
		} catch (Exception e) {
			db.endTransaction(false);
			fail("An error occurred. The node's surrogate neighbors were not retrieved.");
			e.printStackTrace();
		}
	}
	
	/* might not need tests below because they will be in the table already? (getNode)
	
//	@Test
//	public void testAddSurrgFold() {
//		try {
//			Node node = new Node();
//			Node sFold = new Node();
//			db.startTransaction();
//			Boolean result = db.getNodes().addSurrgFold(node, sFold);
//			db.endTransaction(true);
//			assertTrue("addSurrgFold returned false.", result);
//		} catch (Exception e) {
//			db.endTransaction(false);
//			fail("An error occurred. The surrogate fold was not added.");
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testGetSurrgFold() {
//		try {
//			Node node = new Node();
//			db.startTransaction();
//			Boolean result = db.getNodes().getSurrgFold(node);
//			db.endTransaction(true);
//			assertTrue("getSurrgFold returned false.", result);
//		} catch (Exception e) {
//			db.endTransaction(false);
//			fail("An error occurred. The node's surrogate fold was not retrieved.");
//			e.printStackTrace();
//		}
//	}
	
//	@Test
//	public void testAddFold() {
//		try {
//			Node node = new Node();
//			Node fold = new Node();
//			db.startTransaction();
//			Boolean result = db.getNodes().addFold(node, fold);
//			db.endTransaction(true);
//			assertTrue("addFold returned false.", result);
//		} catch (Exception e) {
//			db.endTransaction(false);
//			fail("An error occurred. The fold was not added.");
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testGetFold() {
//		try {
//			Node node = new Node();
//			db.startTransaction();
//			Boolean result = db.getNodes().getFold(node);
//			db.endTransaction(true);
//			assertTrue("getFold returned false.", result);
//		} catch (Exception e) {
//			db.endTransaction(false);
//			fail("An error occurred. The node's fold was not retrieved.");
//			e.printStackTrace();
//		}
//	}
}
