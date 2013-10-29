package visitor;

import java.util.Random;

import model.Node;

public class Print extends BroadcastVisitor{

	private static Random random = new Random();
	
	/*public RandomlyFill(Node endNode){
		super(endNode);
	}*/
	public void run(){
		Parameters parameters = new Parameters();
		//super.run(this, parameters);
	}
	
	protected void performIntOperation(Node node, Parameters parameters){
		int randomInt = random.nextInt();
		//node.setValue(randomInt);
	}
	protected void performStringOperation(Node node, Parameters parameters){
		int randomInt = random.nextInt();
		//node.setValue(Integer.toString(randomInt));
	}
}