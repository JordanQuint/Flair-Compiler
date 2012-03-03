/*
  Compiler Project: Parser Part 1 Assignment 02/23/12
  Authors: Andrew, Jordan, and Zane
  FlairParser class
*/

import java.util.*;
import java.io.*;

public class FlairParser{

    private String[][][] parseTable;
    private Stack<String> parseStack = new Stack<String>();
	 private FlairScanner tokenStream;
	 
	 public static void main(String[] args){
	     FlairParser parse = new FlairParser(args[0]);
		  System.out.println(parse.parse());
		  
	 }
	 
	 public FlairParser(String flairFile){
	     tokenStream = new FlairScanner(flairFile);
		  this.createTable();
	 }
	 
	 //Determines whether the stream of tokens are syntactically correct.
	 public boolean parse(){
	     //$ to end of token stream, and get the first token
	     tokenStream.addToken(new FlairToken(FlairToken.EOS));
	     FlairToken currentToken = tokenStream.getToken();
		  ArrayList<String> states = new ArrayList<String>();
		  
		  for(int i = 1; i<parseTable.length; i++){
		      states.add(parseTable[i][0][0]);
		  }
		  
		  ArrayList<String> tokenList = new ArrayList<String>();
		  for(int j = 1; j<parseTable[0].length; j++){
		      tokenList.add(parseTable[0][j][0]);
		  }
		  
		  //push $ and beginning token onto parse stack
		  parseStack.push("$");
		  parseStack.push("PROGRAM");
		  
		  while(!parseStack.peek().equals("$")){
		      
				//
				//For testing to see the stack vs token
		      //System.out.println("stack = " + parseStack.peek() + "   token = " + currentToken.getType());
				//
				
		      if(tokenList.contains(parseStack.peek())){
				    if(parseStack.peek().equals(currentToken.getType())){
					     parseStack.pop();
						  if(currentToken.getType().equals("identifier") && parseStack.peek().equals("function")){
						      System.out.println(currentToken.getValue());
						  }
						  
						  else if(currentToken.getType().equals("identifier")){
						      System.out.print(currentToken.getValue());
						  }
						  tokenStream.deleteToken();
						  currentToken = tokenStream.getToken();
					 }
					 
					 else{
					     return false;
					 }
				}
				
				else{
				    String firstValue = parseTable[states.indexOf(parseStack.peek())+1][tokenList.indexOf(currentToken.getType())+1][0];
					 //System.out.println(firstValue);
					 
					 //test to see if allowed to be empty
				    if(firstValue.equals("E")){
					        firstValue = parseTable[states.indexOf(parseStack.peek())+1][tokenList.indexOf("$")+1][0];
							  if(!firstValue.equals("E")){
							      parseStack.pop();
							  }
							  
							  else{
							      return false;
							  }
					 }
					 
					 //not an error
					 else{
					 
					     if(firstValue.equals("")){
						      parseStack.pop();
						  }
						  
						  else{
						      String[] holding = parseTable[states.indexOf(parseStack.peek())+1][tokenList.indexOf(currentToken.getType())+1];
						      parseStack.pop();
								for(int i = holding.length; i>0; i--){
								    parseStack.push(holding[i-1]);
								}
						  }
					 }
				}
		  }
		  
		  return true;  
	 }
	 
	 
	 /*
	 ** The parse table
	 ** 2D array that hold string arrays
	 **
	 **
	 **          Currently EXPRESSION, EXPRESSION', STATLIST, and STATLIST' are causing problems
	 **
	 **
	 */
	 private void createTable(){
	     parseTable = new String[][][]{
		      {new String[] {"E"}, new String[] {"identifier"}, new String[] {"integerNum"}, new String[] {"realNum"}, new String[] {"program"}, new String[] {"var"},
				new String[] {"function"}, new String[] {"integer"}, new String[] {"real"}, new String[] {"begin"}, new String[] {"end"}, new String[] {"if"},
				new String[] {"then"}, new String[] {"else"}, new String[] {"while"}, new String[] {"do"}, new String[] {"print"}, new String[] {"return"}, new String[] {":="}, new String[] {"="},
				new String[] {"!="}, new String[] {"<"}, new String[] {"<="}, new String[] {">"}, new String[] {">="}, new String[] {"+"}, new String[] {"-"}, new String[] {"*"}, new String[] {"/"},
				new String[] {"{"}, new String[] {"}"}, new String[] {";"}, new String[] {"."}, new String[] {","}, new String[] {":"}, new String[] {"("}, new String[] {")"}, new String[] {"$"}},
				
				{new String[] {"PROGRAM"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"program", "identifier", "(", "PARAMETERS", ")", ";", "DECS", "COMPS"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
				
				{new String[] {"DECS"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"VARDECS", "FUNCDECS"}, new String[] {"VARDECS", "FUNCDECS"}, new String[] {"E"},
				new String[] {"E"}, new String[] {""}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"VARDECS", "FUNCDECS"}},
				
				{new String[] {"VARDECS"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"var", "VARDECLIST"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {""}},
				
				{new String[] {"VARDECLIST"}, new String[] {"VARDEC", "VARDECLIST'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
				
			   {new String[] {"VARDECLIST'"}, new String[] {"VARDEC", "VARDECLIST'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
			   new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
			   new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
		      new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
			   new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {""}},
			  
			   {new String[] {"VARDEC"}, new String[] {"identifier", ":", "TYPE", ";"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
			   new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
			   new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
		      new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
			   new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
				
				{new String[] {"FUNCDECS"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"FUNCDECLIST"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {""}},
				
				{new String[] {"FUNCDECLIST"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"FUNCDEC", "FUNCDECLIST'"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
				
				{new String[] {"FUNCDECLIST'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"FUNCDEC", "FUNCDECLIST'"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {""}},
				
				{new String[] {"FUNCDEC"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"FUNCHEADING", "FUNCBODY"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
				
				{new String[] {"FUNCHEADING"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"function", "identifier", "(", "PARAMETERS", ")", ":", "TYPE"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
				
				{new String[] {"PARAMETERS"}, new String[] {"PARAMLIST"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {""}, new String[] {""}},
				
				{new String[] {"PARAMLIST"}, new String[] {"PARAMETER", "PARAMLIST'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
				
				{new String[] {"PARAMLIST'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {",", "PARAMLIST"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {""}},
				
				{new String[] {"PARAMETER"}, new String[] {"identifier", ":", "type"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
				
				{new String[] {"FUNCBODY"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"VARDECS", "COMPS"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"VARDECS", "COMPS"}},
				
				{new String[] {"TYPE"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"integer"},
				new String[] {"real"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
				
				{new String[] {"COMPS"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"begin", "STATLIST", "end"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
				
				{new String[] {"STATLIST"}, new String[] {"STATEMENT", "STATLIST'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"STATEMENT", "STATLIST'"}, new String[] {"E"}, new String[] {"STATEMENT", "STATLIST'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"STATEMENT", "STATLIST'"}, new String[] {"E"},
				new String[] {"STATEMENT", "STATLIST'"}, new String[] {"STATEMENT", "STATLIST'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"STATEMENT", "STATLIST'"}},
				
				{new String[] {"STATLIST'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {";", "STATLIST'"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {""}},
				
				{new String[] {"STATEMENT"}, new String[] {"ASSIGNSTAT"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"COMPS"}, new String[] {"E"}, new String[] {"IFSTAT"}, new String[] {"E"}, new String[] {"E"}, new String[] {"WHILESTAT"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"return", "EXPRESSION"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {""}},
				
				{new String[] {"ASSIGNSTAT"}, new String[] {"identifier", ":=", "EXPRESSION"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
				
				{new String[] {"IFSTAT"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"if", "COMP", "then", "STATEMENT", "else", "STATEMENT"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
				
				{new String[] {"WHILESTAT"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"while", "COMP", "do", "STATEMENT"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
				
				{new String[] {"COMP"}, new String[] {"EXPRESSION", "COMPOP", "EXPRESSION"}, new String[] {"EXPRESSION", "COMPOP", "EXPRESSION"}, new String[] {"EXPRESSION", "COMPOP", "EXPRESSION"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
				
				{new String[] {"COMPOP"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"="}, new String[] {"!="}, new String[] {"<"}, new String[] {"<="}, new String[] {">"},
				new String[] {">="}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
				
				{new String[] {"EXPRESSION"}, new String[] {"TERM", "EXPRESSION'"}, new String[] {"TERM", "EXPRESSION'"}, new String[] {"TERM", "EXPRESSION'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"-", "EXPRESSION"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"TERM", "EXPRESSION'"}, new String[] {"E"}, new String[] {"TERM", "EXPRESSION'"}},
				
				{new String[] {"EXPRESSION'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"+", "TERM", "EXPRESSION'"}, new String[] {"-", "TERM", "EXPRESSION'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {""}},
				
				{new String[] {"TERM"}, new String[] {"FACTOR", "TERM'"}, new String[] {"FACTOR", "TERM'"}, new String[] {"FACTOR", "TERM'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"FACTOR", "TERM'"}, new String[] {"E"}, new String[] {"E"}},
				
				{new String[] {"TERM'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"*", "TERM"}, new String[] {"/", "TERM"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {""}},
				
				{new String[] {"FACTOR"}, new String[] {"identifier", "FACTOR"}, new String[] {"LITERAL"}, new String[] {"LITERAL"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"(", "EXPRESSION", ")"}, new String[] {"E"}, new String[] {"E"}},
				
				{new String[] {"FACTOR'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"(", "ARGS", ")"}, new String[] {"E"}, new String[] {""}},
				
				{new String[] {"FUNCCALL"}, new String[] {"identifier", "(", "ARGS", ")"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
				
				{new String[] {"ARGS"}, new String[] {"ARGLIST"}, new String[] {"ARGLIST"}, new String[] {"ARGLIST"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"ARGLIST"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"ARGLIST"}, new String[] {"E"}, new String[] {""}},
				
				{new String[] {"ARGLIST"}, new String[] {"EXPRESSION", "ARGLIST'"}, new String[] {"EXPRESSION", "ARGLIST'"}, new String[] {"EXPRESSION", "ARGLIST'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"EXPRESSION", "ARGLIST'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"EXPRESSION", "ARGLIST'"}, new String[] {"E"}, new String[] {"E"}},
				
				{new String[] {"ARGLIST'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {",", "ARGLIST'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {""}},
				
				{new String[] {"LITERAL"}, new String[] {"E"}, new String[] {"integerNum"}, new String[] {"realNum"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
				
				{new String[] {"RETURNSTAT"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"return"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
				new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
		  };
	 }
}