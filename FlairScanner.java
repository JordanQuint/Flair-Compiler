/*
  Compiler Project: Scanner Assignment 02/09/12
  Authors: Andrew, Jordan, and Zane
  FlairScanner class
*/

import java.util.*;
import java.io.*;

public class FlairScanner
{
    private String[] candidates;
	 
    public static void main( String[] args )
    {
	     FlairScanner hello = new FlairScanner(args[0]);
		  ArrayList<FlairToken> test3;
		  
		  try{
		      test3 = hello.tokenize();
				
		      //display tokens on different lines
		      for(int i=0; i<test3.size(); i++){
		          //include semantic content for identifier
		          if(test3.get(i).getType().equals(FlairToken.Identifier)){
				        System.out.println(test3.get(i).getType() + " " + test3.get(i).getValue());}
				
				    //include semantic content for numbers
				    else if(test3.get(i).getType().equals(FlairToken.RealNumber) | (test3.get(i).getType().equals(FlairToken.IntegerNumber))){
				        System.out.println(test3.get(i).getType() + " " + test3.get(i).getValue());}
				
				    //no value so just print type
				    else{
				        System.out.println(test3.get(i).getType());}}}
		  
		  catch(IOException e){
		      System.out.println("Syntax Error");}		  
    }
	 
	 public FlairScanner(String origFile)
	 {
	     File originalFile = new File(origFile);
	     this.flairFileToStrings(originalFile);
	 }
	 
    public void flairFileToStrings(File pathname)
    {
		  try
		  {
		  Scanner in = new Scanner(pathname);
		  String fileString = "";
		  while(in.hasNextLine()){
		      fileString = fileString.concat(in.nextLine());
				fileString = fileString + " ";}
		  in.close();	  
		  fileString = fileString.replaceAll("[\n\r\t]", " ");
		  String[] delimiters = {":", "=", "!", "<", ">", "\\+", "-", "\\*", "/", "\\{", "}", ";", "\\.", ",", ":", "\\(", "\\)"};
		  for(int i=0; i<delimiters.length; i++){
		      fileString = fileString.replaceAll("" + delimiters[i] + "", " " + delimiters[i] + " ");}
		  candidates = fileString.split("\\s+");}
		  
		  catch(IOException e){
		      System.out.println("Syntax Error");}
    }
	 
	 public ArrayList<FlairToken> tokenize() throws IOException
	 {
	     //Sequence of successful tokens
	     ArrayList<FlairToken> tokens = new ArrayList<FlairToken>();
		  
		  for(int token = 0; token<candidates.length; token++){
		      //is it a number
		      if(candidates[token].substring(0,1).matches("[\\d]")){
				    //real
				    if(candidates[token].contains("e") || candidates[token+1].equals(".")){
					     //number e in it like 12e5
					     if(candidates[token].contains("e")){
						      if(candidates[token].matches("(0|[1-9][0-9]*)e(0|[1-9][0-9]*)")){
								    String[] splitUp = candidates[token].split("e");
									 if((Long.parseLong(splitUp[0]) < new Long("4294967296")) && (Long.parseLong(splitUp[1]) < new Long("4294967296"))){
								        tokens.add(new FlairToken(FlairToken.RealNumber, candidates[token]));}
									 else{
									     throw new IOException("Wrong format for number");}}
								else{
								    throw new IOException("Wrong format for number");}}
						  
						  //number containing a decimal and possibly and e
						  else if(candidates[token+1].equals(".")){
						      if(candidates[token].matches("(0|[1-9][0-9]*)") && candidates[token+2].matches("[0-9]+|([0-9]+e(0|[1-9][0-9]*))")){
								    if((Long.parseLong(candidates[token]) < new Long("4294967296"))){
									     //exponent for real number situation
									     if(candidates[token+2].contains("e")){
										      if((Long.parseLong(candidates[token+2].substring(candidates[token+2].indexOf("e")+1))) < new Long("4294967296")){
												    tokens.add(new FlairToken(FlairToken.RealNumber, candidates[token] + "." + candidates[token+2]));
									             token = token + 2;}
												else{
												    throw new IOException("Wrong format for number");}}
										  
										  //no exponent in later part
										  else if((Long.parseLong(candidates[token+2]) < new Long("4294967296"))){
										      tokens.add(new FlairToken(FlairToken.RealNumber, candidates[token] + "." + candidates[token+2]));
												token = token + 2;}
										  
										  else{
												throw new IOException("Wrong format for number");}}
								    else{
									     throw new IOException("Wrong format for number");}}
								else{
								    throw new IOException("Wrong format for number");}}
									 
						  else{
						      throw new IOException("Wrong format for number");}}
					 
					 //integer
					 else{
					     if(candidates[token].matches("0|[1-9][0-9]*")){
				            if(Long.parseLong(candidates[token]) < new Long("4294967296")){
					             tokens.add(new FlairToken(FlairToken.IntegerNumber, candidates[token]));}
					         else{
					             throw new IOException("Out of range");}}
				        else{
				            throw new IOException("Wrong format for number");}}}
				
				//not a number
				else{
	             switch (candidates[token]){	  
                case "program":
                    tokens.add(new FlairToken(FlairToken.Program));
                    break;
                case "var":
                    tokens.add(new FlairToken(FlairToken.Var));
                    break;
                case "function":
                    tokens.add(new FlairToken(FlairToken.Function));
                    break;
                case "integer":
                    tokens.add(new FlairToken(FlairToken.Integer));
                    break; 
                case "real":
                    tokens.add(new FlairToken(FlairToken.Real));
                    break;
                case "begin":
                    tokens.add(new FlairToken(FlairToken.Begin));
                    break;
			       case "end":
                    tokens.add(new FlairToken(FlairToken.End));
                    break;
			       case "if":
                    tokens.add(new FlairToken(FlairToken.If));
                    break;
			       case "then":
                    tokens.add(new FlairToken(FlairToken.Then));
                    break;
			       case "else":
                    tokens.add(new FlairToken(FlairToken.Else));
                    break;
			       case "while":
                    tokens.add(new FlairToken(FlairToken.While));
                    break;
			       case "do":
                    tokens.add(new FlairToken(FlairToken.Do));
                    break;
			       case "print":
                    tokens.add(new FlairToken(FlairToken.Print));
                    break;
			       case "return":
                    tokens.add(new FlairToken(FlairToken.Return));
                    break;
			       case ":":
					     if(candidates[token+1].equals("=")){
				            tokens.add(new FlairToken(FlairToken.Assignment));
				      	   token++;}
				        else{
				            tokens.add(new FlairToken(FlairToken.Colon));}
                    break;
			       case "=":
                    tokens.add(new FlairToken(FlairToken.Equals));
                    break;
			       case "!":
			           if(candidates[token+1].equals("=")){
                        tokens.add(new FlairToken(FlairToken.NotEquals));
						      token++;}
				        else{
				            throw new IOException("Wrong format");}
                    break;
		          case "<":
                    if(candidates[token].length() == 1){
                        tokens.add(new FlairToken(FlairToken.LessThan));}
				        else if(candidates[token+1].equals("=")){
				            tokens.add(new FlairToken(FlairToken.LessThanEqual));
						      token++;}
				        else{
				            throw new IOException("Wrong format");}
                    break;
			       case ">":
                    if(candidates[token].length() == 1){
                        tokens.add(new FlairToken(FlairToken.GreaterThan));}
				        else if(candidates[token+1].equals("=")){
				            tokens.add(new FlairToken(FlairToken.GreaterThanEqual));
						      token++;}
				        else{
				            throw new IOException("Wrong format");}
                    break;
			       case "+":
                    tokens.add(new FlairToken(FlairToken.Plus));
                    break;
			       case "-":
                   tokens.add(new FlairToken(FlairToken.Minus));
                   break;
			       case "*":
                   tokens.add(new FlairToken(FlairToken.Multiply));
                   break;
			       case "/":
                   tokens.add(new FlairToken(FlairToken.Divide));
                   break;
			       case "{":
                   tokens.add(new FlairToken(FlairToken.LeftBrace));
						 //Deals with getting rid of the comments that we're not supposed to consider
						 for(int i = token + 1;  i<candidates.length; i++){
						     if(candidates[i].equals("}")){
							      tokens.add(new FlairToken(FlairToken.RightBrace));
									token = i; 
									i = i + 100000;}
							  else if(i == candidates.length -1){
							      throw new IOException("Wrong format");}
						 }
                   break;
			       case "}":
                   tokens.add(new FlairToken(FlairToken.RightBrace));
                   break;
			       case ";":
                   tokens.add(new FlairToken(FlairToken.SemiColon));
                   break;
			       case ".":
                   tokens.add(new FlairToken(FlairToken.Period));
                   break;
			       case ",":
                   tokens.add(new FlairToken(FlairToken.Comma));
                   break;
			       case "(":
                   tokens.add(new FlairToken(FlairToken.LeftParentheses));
                   break;
			       case ")":
                   tokens.add(new FlairToken(FlairToken.RightParentheses));
                   break;
                default:
                   tokens.add(identifierCheck(candidates[token]));}}}
						 
		  return tokens;
	 }
	 	 
	 //Checks to see if the string fits the format of an identifier
	 public FlairToken identifierCheck(String theWord) throws IOException
	 {
	     if(theWord.length() < 257){
		      if(theWord.matches("[a-zA-Z]+")){
		          return new FlairToken(FlairToken.Identifier, theWord);}
				else{
				    throw new IOException("Wrong format for identifier");}}
		  else{
		      throw new IOException("Wrong format for identifier");}
	 }
}