package visitor;

import java.util.ArrayList;

import model.Node;

public abstract class BroadcastVisitor implements Visitor{
	
	protected static String STARTED_KEY;
	
	/**
	 * Default Constructor
	 */
	public BroadcastVisitor(){
		super();
		STARTED_KEY = "";
	}
	
	/**
	 * Creates the initial parameters, starting at a node
	 * 
	 * @return
	 */
	public static Parameters createInitialParameters(int webID){
		return new Parameters(webID);
	}
	
	/**
	 * Creates the default parameters. Defaults to start at node 0.
	 * 
	 * @return
	 */
	public static Parameters createInitialParameters(){
		return new Parameters(0);
	}
	
	/**
	 * 
	 * @param node
	 * @param parameters
	 */
	protected abstract void operation(Node node, Parameters parameters);
	
	/**
	 * visit method for the broadcast
	 */
	@Override
	public void visit(Node node, Parameters parameters) {
		/*
		 * find all children with id's with trailing 0's
		 * flip the trailing zeroes to create a list of possible neighbors to check
		 * if you send to the neighbors, check their children.
		 * 
		 */
		
		String testNodeBSR = Integer.toBinaryString(node.getChildNodeID().getValue());
		int heightOfNode = node.getHeight();
		int lengthOfStringRepresentation = testNodeBSR.length();
		int leadingZeroesToAdd = heightOfNode-lengthOfStringRepresentation;
		
		//add leading zeroes
		for(int a = 0; a < leadingZeroesToAdd;a++){
			testNodeBSR = "0" + testNodeBSR;
		}
		ArrayList<String> listOfNodesToTest = getPossibleNeighbors(testNodeBSR);
		
		// TODO do something with the list of possible neighbors with the swapped bits from the right-most 0's 
		//      (check their children and then inverse surrogate neighbors)
		
	}

	/**
	 * Gets all of the possible neighbors swapping out the right-most zeroes for a "1" one at a time.
	 * 
	 * @param testNodeBSR
	 * @return
	 */
	private ArrayList<String> getPossibleNeighbors(String testNodeBSR) {
		ArrayList<String> returnList = new ArrayList<String>();
		int FurthestRightOneBit = findPositionOfRightwiseBitOne(testNodeBSR);
		for(int index = FurthestRightOneBit; index < testNodeBSR.length()-1;index++){
			returnList.add(testNodeBSR.substring(0, index+1) + "1" + testNodeBSR.substring(index+2, testNodeBSR.length()));
		}
		//special case when the string is "0"
		if(testNodeBSR.length()==1){
			returnList.add("1");
		}
		return returnList;
	}

	/**
	 * Finds the index of the string "1" farthest right in a given string.
	 * 
	 * @param testNodeBSR
	 * @return
	 */
	private int findPositionOfRightwiseBitOne(String testNodeBSR) {
		int returnValue = 0;
		for(int index = 0; index < testNodeBSR.length()-1;index++){
			if(testNodeBSR.substring(index, index+1).equals("1")){
				returnValue = index;
			}
		}
		return returnValue;
	}
	
	
}
