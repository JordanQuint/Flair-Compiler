/*
  Compiler Project: Type Checker Assignment 03/29/12
  Authors: Andrew, Jordan, and Zane
  FlairSymbolTable class
*/

import java.util.*;
import java.io.*;

public class FlairSymbolTable{

    private HashMap<String,String> mainVars = new HashMap<String,String>();
	 private ArrayList<HashMap<String,String>> functionVars = new ArrayList<HashMap<String,String>>();

    public FlairSymbolTable(Program p){
	     this.generateMainVars(p);
		  this.generateFunctionVars(p);
	 }
	 
	 public void generateMainVars(Program p){
	     ArrayList<VarDec> vdecs = p.decs().vdecs().variables();
		  for (int i=0;i<vdecs.size();i++){
		      if(mainVars.containsKey(vdecs.get(i).identifier())){
				    System.out.println("Main variable declared more than once");
					 System.exit(0);
				}
		      mainVars.put(vdecs.get(i).identifier().word(), vdecs.get(i).type().selection());
		  }
	 }
	 
	 public void generateFunctionVars(Program p){
	 
	 }
	 
	 public boolean isMainVar(String var){
	     return true;
	 }
	 
	 public boolean isFuncVar(String var){
	     return true;
	 }
	 
	 public String toString(){
	     return "";
	 }
}