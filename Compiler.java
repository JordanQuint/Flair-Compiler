/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package compiler.project;

/**
 *
 * @author jordanquint
 */
public class Compiler {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
  	
  	//validate that args[0] exists and is a filename
  	if (!((new File(args[0])).exists()))
  	{
  		System.out.println("You must supply a valid filename.");
  		System.exit(1);
  	}
    
    Scanner scanner = new Scanner(args[0]);
    
    
    //Parser
    
    //Semantic Analyzer
    
    //Optimize abstract syntax tree
    
    //Prepare for generation
    
    //Generate code
  }
}
