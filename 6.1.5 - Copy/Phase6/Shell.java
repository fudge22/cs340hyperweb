package Phase6;
import java.io.IOException;

/**
 * Not really needed for the example but if you need to execute from within java a command on the command line of
 * another machine, this shows you how to do it.  You will have to set up ssh so no password is needed.  See
 * http://www.csua.berkeley.edu/~ranga/notes/ssh_nopass.html
 * @author Scott
 *
 */
public class Shell {
	public Process executeCommand(String command){
		try {
			return Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Shell(){}
	
	public static void main(String[] args){
		System.out.println("Begin");
		Shell shell = new Shell();
		shell.executeCommand("ssh -i $HOME/.ssh/id_dsa jvisker@schizo.cs.byu.edu");
		shell.executeCommand("mkdir jumbalya");
		System.out.println("End");
	}
}
