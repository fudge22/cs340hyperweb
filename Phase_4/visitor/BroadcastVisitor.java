package visitor;

import java.util.ArrayList;

import model.Node;
import model.WebID;

public class BroadcastVisitor extends Visitor{
	
	/**
	 * Default Constructor
	 */
	public BroadcastVisitor(){
		super();
	}
	
	/**
	 * visit method for the broadcast
	 */
	@Override
	public void visit(Node node) {
		/*
		 * find all children with id's with trailing 0's
		 * flip the trailing zeroes to create a list of possible neighbors to check
		 * if you send to the neighbors, check their children.
		 * 
		 */
		intermediateOperation(node);
		String testNodeBSR = Integer.toBinaryString(node.getWebID().getValue());
		int heightOfNode = node.getHeight();
		int lengthOfStringRepresentation = testNodeBSR.length();
		int leadingZeroesToAdd = heightOfNode-lengthOfStringRepresentation;
		
		//add leading zeroes
		for(int a = 0; a < leadingZeroesToAdd;a++){
			testNodeBSR = "0" + testNodeBSR;
		}
		ArrayList<String> listOfNodesToTest = getPossibleNeighbors(testNodeBSR);
		
		ArrayList<String> ListOfMoreNodesToTest = getPossibleNeighbors("1" + testNodeBSR);
		ArrayList<WebID> ListOfInverseSurrogateNeighborsWithWebId = (ArrayList<WebID>) node.getInvSurNeighborList();
		ArrayList<String> ListOfInverseSurrogateNeighbors = new ArrayList<String>();
		for(int i = 0; i < ListOfInverseSurrogateNeighborsWithWebId.size(); i++){
			String inverseSurrogateNeighborTest = Integer.toBinaryString(ListOfInverseSurrogateNeighborsWithWebId.get(i).getValue());
			ListOfInverseSurrogateNeighbors.add(inverseSurrogateNeighborTest);
		}
		
		for(int index = 0; index < ListOfMoreNodesToTest.size(); index++){
			for(int i = 0; i < ListOfInverseSurrogateNeighbors.size(); i++){
				if(ListOfMoreNodesToTest.get(index).compareTo(ListOfInverseSurrogateNeighbors.get(i)) == 0){
					listOfNodesToTest.add(ListOfMoreNodesToTest.get(index));
				}
			}
		}
		// TODO broadcast an unimplemented message to the current node
		
		for(int index = 0; index < listOfNodesToTest.size(); index++){
			WebID tempWebId = new WebID(Integer.parseInt(listOfNodesToTest.get(index), 2));
			Node tempNode = Node.getNode(tempWebId);
			if(tempNode != null){
				tempNode.accept(this);
			}
		}
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
		for(int index = 0; index < testNodeBSR.length();index++){
			if(testNodeBSR.substring(index, index+1).equals("1")){
				returnValue = index;
			}
		}
		return returnValue;
	}
	
	/**
	 * This is the operation that needs to be performed on a node as it is traversed to the target node. (Track the path)
	 * 
	 * @param node
	 */
	protected void intermediateOperation(Node node){
		System.out.println("Visiting node " + node.getWebId() + " through vistor pattern, trying to broadcast.");
	}
}
