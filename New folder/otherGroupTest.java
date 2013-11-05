import static org.junit.Assert.*;
import hypeerweb.Models.HyPeerWeb;
import hypeerweb.Models.Node;
import hypeerweb.Models.Parameters;
import hypeerweb.Models.Visitor.VisitorBroadcast;
import hypeerweb.Models.Visitor.VisitorSend;
import hypeerweb.simulation.Validator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class otherGroupTest {

	public static HyPeerWeb myWeb;
	
    @BeforeClass
    public static void setUpClass() {
    	myWeb = HyPeerWeb.getMainHyPeerWeb();
    	Validator v = new Validator(myWeb);
    	for(int i = 0; i < 256; i++) {
    		myWeb.addNode(new Node());
    		v.validate();
    	}
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception
    {
    	
    }
    
    @After
    public void tearDown() {
    }
	
	@Test
	public void testSend() {
		//VisitorSend myVisitor = new VisitorSend();
		//myVisitor.visit(new Node(), new Parameters());
	}
	
	@Test
	public void testBroadcastFromZero(){
		VisitorBroadcast myVisitor = new VisitorBroadcast();
		myVisitor.visit((Node)myWeb.getNode(100), new Parameters());
		myVisitor.visit((Node)myWeb.getNode(100), new Parameters());
	}
	
	public void testBroadcastFromTen() {
		
	}

}
