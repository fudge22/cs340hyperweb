package tests;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class NodeTests {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
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
	public void testBitSize() {
		int num = 0;
		int num1 = 1;
		int num2 = 2;
		int num3 = 4;
		int num4 = 8;
		int num5 = 3;
		int num6 = 5;
		int num7 = 7;
		assertEquals(Integer.SIZE - Integer.numberOfLeadingZeros(num), 0);
		assertEquals(Integer.SIZE - Integer.numberOfLeadingZeros(num1), 1);
		assertEquals(Integer.SIZE - Integer.numberOfLeadingZeros(num2), 2);
		assertEquals(Integer.SIZE - Integer.numberOfLeadingZeros(num3), 3);
		assertEquals(Integer.SIZE - Integer.numberOfLeadingZeros(num4), 4);
		assertEquals(Integer.SIZE - Integer.numberOfLeadingZeros(num5), 2);
		assertEquals(Integer.SIZE - Integer.numberOfLeadingZeros(num6), 3);
		assertEquals(Integer.SIZE - Integer.numberOfLeadingZeros(num7), 3);
	}

}
