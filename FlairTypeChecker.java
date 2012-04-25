/*
  Compiler Project: Type Checker Assignment 03/29/12
  Authors: Andrew, Jordan, and Zane
  FlairTypeChecker class
*/

import java.util.*;
import java.io.*;

public class FlairTypeChecker{

    private FlairSymbolTable table;
	 
	 public FlairTypeChecker(FlairSymbolTable theTable){
	     table = theTable;
	 }
	 
	 public void checkProgram(Program p){
	     checkFunctionNames(p);
		  checkFunctionReturns(p);
		  illegalReturnStatements(p);
		  argumentCheck(p);
	 }
	 
	 public void checkFunctionNames(Program p){
	     ArrayList<FuncDecs> functions = p.getFuncDecs();
		  for(int i=0;i<functions.size();i++){
		      //checks for user print function
		      if(functions.get(i).getID().equals("print"){
				    System.out.println("User-defined print function");
					 System.exit(0);
				}
				
				//checks for duplicate function names
				int same=0;
				for(int j=0;j<functions.size(),j++){
				    if(functions.get(i).getID().equals(functions.get(j).getID())){
					     same = same + 1;
					 }
				}
				
				if(same>1){
				    System.out.println("Some functions share the same name");
					 System.exit(0);
				}
				
		  }
	 
	 }
	 
	 public void checkFunctionReturns(Program p){
	     if(s.instanceOf(PrintStatement)){
		  
		  }
		  
		  else if(s.instanceOf(AssignStatement)){
		  
		  }
		  
		  else if(s.instanceOf(IfStatement)){
		  
		  }
		  
		  else if(s.instanceOf(WhileStatement)){
		  
		  }
		  
		  else if(s.instanceOf(ReturnStatement)){
		  
		  }
		  
		  else if(s.instanceOf(CompoundStatement)){
		  
		  }
	 }
	 
	 public void illegalReturnStatements(Program p){
	     CompStatement pcstat = p.compStatement();
		  ArrayList<Statement> ps = pcstat.statements();
		  for(int i=0; i<ps.size(); i++){
		      if(ps.get(i) instanceof ReturnStatement){
				    System.out.println("Return statement must be in a function");
					 System.exit(0);
				}
		  }
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
	 
	 public void checkArguments(Expression e){
	 
	 }
	 
	 public void checkFunction(Expression e){
	 
	 }
	 
}