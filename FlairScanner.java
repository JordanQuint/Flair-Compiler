/*
  Compiler Project: Scanner Assignment 02/23/12
  Authors: Andrew, Jordan, and Zane
  FlairScanner class
*/

import java.util.*;
import java.io.*;

public class FlairScanner
{
    private String[] candidates;
    private ArrayList<FlairToken> tokens;
    
    public FlairScanner(String origFile)
    {
        File originalFile = new File(origFile);
        this.flairFileToStrings(originalFile);
        
        try{
            this.tokenize();
            
        }
        catch(IOException e){
            System.out.println("Syntax Error");
        }
    }

    public void flairFileToStrings(File pathname)
    {
        try{
        Scanner in = new Scanner(pathname);
        String fileString = "";
        while(in.hasNextLine()){
            fileString = fileString.concat(in.nextLine());
            fileString = fileString + " ";
        }
        in.close();    
        fileString = fileString.replaceAll("[\n\r\t]", " ");
        String[] delimiters = { "\\+", "\\*", "/", "\\{", "}", ";", "\\.", ",", "\\(", "\\)"};
        System.out.println("Before delimeters: "+fileString);
        for(int i=0; i<delimiters.length; i++){
            fileString = fileString.replaceAll("" + delimiters[i] + "", " " + delimiters[i] + " ");
        }
        
        //resolve greater than or equal
        fileString = fileString.replaceAll(">=", " >= ");
        fileString = fileString.replaceAll(" =", " = ");
        fileString = fileString.replaceAll("> ", " > ");
        
        //resolve less than or equal
        fileString = fileString.replaceAll("<=", " <= ");
        fileString = fileString.replaceAll(" =", " = ");
        fileString = fileString.replaceAll("< ", " < ");
        
        //resolve does not equal
        fileString = fileString.replaceAll("!=", " != ");
        fileString = fileString.replaceAll(" =", " = ");
        fileString = fileString.replaceAll("! ", " ! ");
        
        //resolve assignment
        fileString = fileString.replaceAll(":=", " := ");
        fileString = fileString.replaceAll(" =", " = ");
        fileString = fileString.replaceAll(": ", " : ");
        System.out.println("After:             " +fileString);
        
        //resolve negative numbers
        fileString = fileString.replaceAll("-", " -");
        fileString = fileString.replaceAll("  ", " ");
 
        candidates = fileString.split("\\s+");
        System.out.println(fileString);
        }
        
        catch(IOException e){
            System.out.println("Syntax Error");
        }
    }
    
    /*
    ** Creates the tokens
    **
    **   Under revision to handle the negative identifer vs negative number issue
    **
    **
    */
    public void tokenize() throws IOException
    {
        //Sequence of successful tokens
        tokens = new ArrayList<FlairToken>();
        
        for(int token = 0; token<candidates.length; token++){
            //is it a number
            if(candidates[token].substring(0,1).matches("[\\d]")){
            	FlairToken tokenToAdd = numberCheck(token);
            	if(((String) tokenToAdd.getValue()).contains(".")){
            		token=token+2;
            	}
               tokens.add(tokenToAdd);
               }
            //not a number
            else{   
                if(candidates[token].equals("program")){
                    tokens.add(new FlairToken(FlairToken.Program));
                }
                else if(candidates[token].equals("var")){
                    tokens.add(new FlairToken(FlairToken.Var));
                }
                else if(candidates[token].equals("function")){
                    tokens.add(new FlairToken(FlairToken.Function));
                }
                else if(candidates[token].equals("integer")){
                    tokens.add(new FlairToken(FlairToken.Integer));
                } 
                else if(candidates[token].equals("real")){
                    tokens.add(new FlairToken(FlairToken.Real));
                }
                else if(candidates[token].equals("begin")){
                    tokens.add(new FlairToken(FlairToken.Begin));
                }
                else if(candidates[token].equals("end")){
                    tokens.add(new FlairToken(FlairToken.End));
                }
                else if(candidates[token].equals("if")){
                    tokens.add(new FlairToken(FlairToken.If));
                }
                else if(candidates[token].equals("then")){
                    tokens.add(new FlairToken(FlairToken.Then));
                }
                else if(candidates[token].equals("else")){
                    tokens.add(new FlairToken(FlairToken.Else));
                }
                else if(candidates[token].equals("while")){
                    tokens.add(new FlairToken(FlairToken.While));
                }
                else if(candidates[token].equals("do")){
                    tokens.add(new FlairToken(FlairToken.Do));
                }
                else if(candidates[token].equals("print")){
                    tokens.add(new FlairToken(FlairToken.Print));
                }
                else if(candidates[token].equals("return")){
                    tokens.add(new FlairToken(FlairToken.Return));
                }
                else if(candidates[token].equals(":=")){
                	tokens.add(new FlairToken(FlairToken.Assignment));
                }
                else if(candidates[token].equals(":")){
                        tokens.add(new FlairToken(FlairToken.Colon));
                }
                else if(candidates[token].equals("=")){
                    tokens.add(new FlairToken(FlairToken.Equals));
                }
                else if(candidates[token].equals("!=")){
                	tokens.add(new FlairToken(FlairToken.NotEquals));
                }
                else if(candidates[token].equals(">")){
                	tokens.add(new FlairToken(FlairToken.GreaterThan));
                }
                else if(candidates[token].equals(">=")){
                	tokens.add(new FlairToken(FlairToken.GreaterThanEqual));
                }
                else if(candidates[token].equals("<")){
                	tokens.add(new FlairToken(FlairToken.LessThan));
                }
                else if(candidates[token].equals("<=")){
                	tokens.add(new FlairToken(FlairToken.LessThanEqual));
                }
                else if(candidates[token].equals("+")){
                    tokens.add(new FlairToken(FlairToken.Plus));
                }
                else if(candidates[token].substring(0,1).matches("-")){
                     if(candidates[token].length() == 1){
                     tokens.add(new FlairToken(FlairToken.Minus));}
                     else{              	 
                    	FlairToken tokenToAdd = numberCheck(token);
                     	if(((String) tokenToAdd.getValue()).contains(".")){
                     		token=token+2;
                     	}
                     	tokenToAdd.setValue("-"+tokenToAdd.getValue());
                        tokens.add(tokenToAdd);
                   }
                }
                else if(candidates[token].equals("*")){
                   tokens.add(new FlairToken(FlairToken.Multiply));
                }
                else if(candidates[token].equals("/")){
                   tokens.add(new FlairToken(FlairToken.Divide));
                }
                else if(candidates[token].equals("{")){
                   for(int i = token + 1;  i<candidates.length; i++){
                       if(candidates[i].equals("}")){
                           token = i; 
                           i = candidates.length;}
                       else if(i == candidates.length - 1){
                           throw new IOException("Comment doesn't close");}
                   }
                }
                else if(candidates[token].equals(";")){
                   tokens.add(new FlairToken(FlairToken.SemiColon));
                }
                else if(candidates[token].equals(".")){
                   tokens.add(new FlairToken(FlairToken.Period));
                }
                else if(candidates[token].equals(",")){
                   tokens.add(new FlairToken(FlairToken.Comma));
                }
                else if(candidates[token].equals("(")){
                   tokens.add(new FlairToken(FlairToken.LeftParentheses));
                }
                else if(candidates[token].equals(")")){
                   tokens.add(new FlairToken(FlairToken.RightParentheses));
                }
                else{
                   tokens.add(identifierCheck(candidates[token]));
                }
            }//end of else
        }//end of for       
    }
       
   
    public FlairToken numberCheck(int token) throws IOException
    {
    	if(candidates[token].contains("")){
    		candidates[token]=candidates[token].replace("-", "");
    		}
    	if(candidates[token].contains("e") || candidates[token+1].equals(".")){
    		//number e in it like 12e5
    		if(candidates[token].contains("e")){
    			if(candidates[token].matches("(0|[1-9][0-9]*)e(0|[1-9][0-9]*)")){
    				String[] splitUp = candidates[token].split("e");
    				if((Long.parseLong(splitUp[0]) < new Long("4294967296")) && (Long.parseLong(splitUp[1]) < new Long("4294967296"))){
    					return new FlairToken(FlairToken.RealNumber, candidates[token]);
    					}
    				else{
    					throw new IOException("Wrong format for number");
    					}
    				}
    			else{
    				throw new IOException("Wrong format for number");
    				}
    			}
    		//number containing a decimal and possibly and e
    		else if(candidates[token+1].equals(".")){
    			if(candidates[token].matches("(0|[1-9][0-9]*)") && candidates[token+2].matches("[0-9]+|([0-9]+e(0|[1-9][0-9]*))")){
    				if((Long.parseLong(candidates[token]) < new Long("4294967296"))){
    					//exponent for real number situation
    					if(candidates[token+2].contains("e")){
    						if((Long.parseLong(candidates[token+2].substring(candidates[token+2].indexOf("e")+1))) < new Long("4294967296")){
    							return new FlairToken(FlairToken.RealNumber, candidates[token] + "." + candidates[token+2]);
    							}
    						else{
    							throw new IOException("Wrong format for number");
    						}
    					}
    					//no exponent in later part
    					else if((Long.parseLong(candidates[token+2]) < new Long("4294967296"))){
    						return new FlairToken(FlairToken.RealNumber, candidates[token] + "." + candidates[token+2]);
    						}
    					else{
    						throw new IOException("Wrong format for number");
    						}
    					}
    				else{
    					throw new IOException("Wrong format for number");
    					}
    				}
    			else{
    				throw new IOException("Wrong format for number");
    				}
    			}
    		else{
    			throw new IOException("Wrong format for number");
    			}
    		}
    //integer
    else{
    	if(candidates[token].matches("0|[1-9][0-9]*")){
    		if(Long.parseLong(candidates[token]) < new Long("4294967296")){
    			return new FlairToken(FlairToken.IntegerNumber, candidates[token]);
    			}
    		else{
    			throw new IOException("Out of range");
    			}
    		}
    	else{
    		throw new IOException("Wrong format for number");
    		}
    	} 
    }
    
    //Checks to see if the string fits the format of an identifier
    public FlairToken identifierCheck(String theWord) throws IOException
    {
        if(theWord.length() < 257){
            if(theWord.matches("([a-zA-Z]|[0-9])+")){
                return new FlairToken(FlairToken.Identifier, theWord);
            }
            else{
                throw new IOException("Wrong format for identifier");
            }
        }
        else{
            throw new IOException("Wrong format for identifier");
        }
    }
    
    protected FlairToken getToken(){
        return tokens.get(0);
    }
    
    protected FlairToken peek(){
        return tokens.get(1);
    }
    
    protected void deleteToken(){
        tokens.remove(0);
    }
    
    protected void addToken(FlairToken newToken){
        tokens.add(newToken);
    }
}