/*
  Compiler Project: Type Checker Assignment 03/29/12
  Authors: Andrew, Jordan, and Zane
  FlairTypeChecker class
*/

import java.util.*;
import java.io.*;

public class FlairTypeChecker{

    private FlairParser fp;
	 
	 public FlairTypeChecker(FlairParser yo/*FlairSymbolTable theTable*/){
	     fp=yo;//table = theTable;
		  Program ft = (Program)fp.parse();
		  this.checkProgram((Program)ft);
	 }
	 
	 public static void main(String[] args){
	     FlairParser parse = new FlairParser(args[0]);
		  FlairTypeChecker hi = new FlairTypeChecker(parse);
	 }
	 
	 public void checkProgram(Program p){
	     checkFunctionNames(p);
		  //checkFunctionReturns(p);
		  illegalReturnStatements(p);
		  //argumentCheck(p);
	 }
	 
	 public void checkFunctionNames(Program p){
	     ArrayList<FuncDec> functions = p.decs().fdecs().functions();
		  for(int i=0;i<functions.size();i++){
		      //checks for user print function
		      if(functions.get(i).head().getID().word().equals("print")){
				    System.out.println("User-defined print function");
					 System.exit(0);
				}
				
				//checks for duplicate function names
				int same=0;
				for(int j=0;j<functions.size();j++){
				    if(functions.get(i).head().getID().word().equals(functions.get(j).head().getID().word())){
					     same = same + 1;
					 }
				}

				if(same>1){
				    System.out.println("Some functions share the same name");
					 System.exit(0);
				}
				
		  }
		  
		  System.out.println("Successful 1");
	 
	 }

	 public void checkFunctionReturns(Program p){
	     ArrayList<FuncDec> functions = p.decs().fdecs().functions();
	     for(int i=0; i<functions.size(); i++){
		      if(!this.checkFunction(functions.get(i).body())){
				    System.out.println("Some function doesn't have a return statement");
					 System.exit(0);
				}
		  }
	 }
	 
	 public boolean checkFunction(FuncBody h){
		  Statement hs = h.cstat().stats();
		  private ArrayList<Statement> hss = hs.sss();
		  int stop = 0;
		  while(stop==0){
		      for(int i=0; i<hss.size(); i++){
				    if(hss.get(
				}
		  }
		  return false;
	 }
	 
	 public void illegalReturnStatements(Program p){
	     CompStatement pcstat = p.cstat();
		  ArrayList<Statement> ps = pcstat.stats().sss();
		  for(int i=0; i<ps.size(); i++){
		      System.out.println(ps.get(i).getClass());
		      if(ps.get(i) instanceof ReturnStatement){
				    System.out.println("Return statement must be in a function");
					 System.exit(0);
				}
		  }
		  System.out.println("Successful 3");
	 }
	 
	 public void argumentCheck(Program p){
	     CompStatement pcstat = p.compStatement();
		  ArrayList<Statement> ps = pcstat.statements();
		  ArrayList<FuncDecs> functions = p.getFuncDecs();
		  for(int i=0; i<ps.size(); i++){
		      if(ps.get(i) instanceof FuncCall){
				    ArrayList<Parameter> yo = ps.get(i).arguments();
					 for(int j=0;j<functions.size();j++){
					     if(functions.get(j).name().equals(ps.get(i).name())){
						      if(functions.get(j).narguments == ps.get(i).narguments()){
								}
								else{
								    System.out.println("Incorrect number of arguments");
					             System.exit(0);
								}
						  }
					 }
				}
		  }
		  
	 }
	 /*
	 public void checkArguments(Expression e){
	 
	 }
	 
	 public void checkFunction(Expression e){
	 
	 }
	 */
}