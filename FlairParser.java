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
      private Stack<Object> semanticStack = new Stack<Object>();
      private FlairScanner tokenStream;
      private Token lastToken;
    
      public static void main(String[] args){
         FlairParser parse = new FlairParser(args[0]);
         System.out.println(parse.parse());
        
      }
    
      public FlairParser(String flairFile){
         tokenStream = new FlairScanner(flairFile);
         this.createTable();
      }
    
    //Determines whether the stream of tokens are syntactically correct.
      public FlairAbstractSyntaxTree parse()
      {
         //$ to end of token stream, and get the first token
         tokenStream.addToken(new FlairToken(FlairToken.EOS));
         FlairToken currentToken = tokenStream.getToken();
         ArrayList<String> states = new ArrayList<String>();
        
         for(int i = 1; i<parseTable.length; i++)
         {
            states.add(parseTable[i][0][0]);
         }
        
         ArrayList<String> tokenList = new ArrayList<String>();
         for(int j = 1; j<parseTable[0].length; j++)
         {
            tokenList.add(parseTable[0][j][0]);
         }
        
        
        //push $ and beginning token onto parse stack
         parseStack.push("$");
         parseStack.push("PROGRAM");
        
         while(!parseStack.peek().equals("$"))
         {
         	//
         	//For testing to see the stack vs token
            System.out.println("stack = " + parseStack.peek() + "   token = " + currentToken.getType());
         	//
         	
            if(tokenList.contains(parseStack.peek()))
            {
               if(parseStack.peek().equals(currentToken.getType()))
               {
                  parseStack.pop();
                  tokenStream.deleteToken();
                  lastToken = currentToken;
                  currentToken = tokenStream.getToken();
               }
               else
               {
                  System.out.println("Error with " + currentToken.getType());
                  return new FlairAbstractSyntaxTree();
               }
            }
            
            else if(states.contains(parseStack.peek()))
            {
               String firstValue = parseTable[states.indexOf(parseStack.peek())+1][tokenList.indexOf(currentToken.getType())+1][0];
               System.out.println(firstValue + " " + currentToken.getValue());
            	 
            	 //test to see if allowed to be empty
               if(firstValue.equals("E"))
               {
                  firstValue = parseTable[states.indexOf(parseStack.peek())+1][tokenList.indexOf("$")+1][0];
                  if(!firstValue.equals("E"))
                  {
                     parseStack.pop();
                  }	  
                  else
                  {
                     System.out.println("Error with " + currentToken.getType());
                     return new FlairAbstractSyntaxTree();
                  }
               }
                
                //not an error
               else
               {
                
                  if(firstValue.equals(""))
                  {
                     parseStack.pop();
                  }  
                  else
                  {
                     String[] holding = parseTable[states.indexOf(parseStack.peek())+1][tokenList.indexOf(currentToken.getType())+1];
                     parseStack.pop();
                     for(int i = holding.length; i>0; i--)
                     {
                        parseStack.push(holding[i-1]);
                     }
                  }
               }
            }
            
            else if(parseStack.peek().startsWith("make"))
            {
                //this.chooseAction(parseStack.peek());
               parseStack.pop();
            }
         }
        
         return (FlairAbstractSyntaxTree) semanticStack.pop();  
      }
    
      private void chooseAction(String action)
      {
         if(action.equals("makeProgram"))
         {
            parseStack.push(new Program(semanticStack.pop(), semanticStack.pop(), semanticStack.pop(), semanticStack.pop()));
         }
         else if(action.equals("makeIntNum")) //use just the token
         {
            parseStack.push(new IntegerNum(lastToken));
         }
         else if(action.equals("makeRealNum"))
         {
            parseStack.push(new RealNum(lastToken));
         }
         else if(action.equals("makeID"))
         {
            parseStack.push(new Identifier(lastToken));
         }
         else if(action.equals("makeType"))
         {
            parseStack.push(new Identifier(lastToken));
         }
         else if(action.equals("makeFuncCall"))
         {
            parseStack.push(new FunctionCall(semanticStack.pop(), semanticStack.pop()));
         }
         else if(action.equals("makeFuncDef"))
         {
            parseStack.push(new FuncDef(semanticStack.pop(), semanticStack.pop()));
         }
         else if(action.equals("makeVarDef"))
         {
            parseStack.push(new VarDef(semanticStack.pop(), semanticStack.pop()));
         }
         else if(action.equals("makeFuncHeading"))
         {
            parseStack.push(new VarDef(semanticStack.pop(), semanticStack.pop(), semanticStack.pop()));
         }
         else if(action.equals("makeFuncBody"))
         {
            parseStack.push(new VarDef(semanticStack.pop(), semanticStack.pop()));
         }
         else if(action.equals("makeParameters"))
         {
            parseStack.push(new Parameters(semanticStack.pop()));
         }
         else if(action.equals("makeParameter"))
         {
            parseStack.push(new Parameter(semanticStack.pop()));
         }
         else if(action.equals("makeAssignStat"))
         {
            parseStack.push(new AssignStatement(semanticStack.pop(), semanticStack.pop()));
         }
         else if(action.equals("makeIfStat"))
         {
            parseStack.push(new IfStatement(semanticStack.pop(), semanticStack.pop(), semanticStack.pop()));
         }
         else if(action.equals("makeWhileStat"))
         {
            parseStack.push(new WhileStatement(semanticStack.pop(), semanticStack.pop()));
         }
         else if(action.equals("makeReturnStat"))
         {
            parseStack.push(new ReturnStatement(semanticStack.pop()));
         }
         else if(action.equals("makeCompStat"))
         {
            parseStack.push(new CompStatement(semanticStack.pop()));
         }
         
         
         
         
         
         //Not Complete
         
         else if(action.equals("makeExp"))
         {
            parseStack.push(new Expression(semanticStack.pop()));
         }
         else if(action.equals("makeNegExp"))
         {
            parseStack.push(new NegateExp(semanticStack.pop()));
         }
         else if(action.equals("makeAddExp"))
         {
            parseStack.push(new AdditionExp(semanticStack.pop()));
         }
         else if(action.equals("makeSubExp"))
         {
            parseStack.push(new SubtractExp(semanticStack.pop()));
         }
         else if(action.equals("makeMultExp"))
         {
            parseStack.push(new MultiplicationExp(semanticStack.pop()));
         }
         else if(action.equals("makeDivExp"))
         {
            parseStack.push(new DivisionExp(semanticStack.pop()));
         }
         else if(action.equals("makeArgs"))
         {
            parseStack.push(new Arguments(semanticStack.pop()));
         }
         else if(action.equals("makeArgument"))
         {
            parseStack.push(new Argument(semanticStack.pop()));
         }
      }
    
    
    /*
    ** The parse table
    ** 2D array that hold string arrays
    **
    **
    **
    **
    **
    */
      private void createTable()
      {
         parseTable = new String[][][]{
               {new String[] {"E"}, new String[] {"identifier"}, new String[] {"integerNum"}, new String[] {"realNum"}, new String[] {"program"}, new String[] {"var"},
                  new String[] {"function"}, new String[] {"integer"}, new String[] {"real"}, new String[] {"begin"}, new String[] {"end"}, new String[] {"if"},
                  new String[] {"then"}, new String[] {"else"}, new String[] {"while"}, new String[] {"do"}, new String[] {"print"}, new String[] {"return"}, new String[] {":="}, new String[] {"="},
                  new String[] {"!="}, new String[] {"<"}, new String[] {"<="}, new String[] {">"}, new String[] {">="}, new String[] {"+"}, new String[] {"-"}, new String[] {"*"}, new String[] {"/"},
                  new String[] {"{"}, new String[] {"}"}, new String[] {";"}, new String[] {"."}, new String[] {","}, new String[] {":"}, new String[] {"("}, new String[] {")"}, new String[] {"$"}},
               
               {new String[] {"PROGRAM"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"program", "identifier", "makeID", "(", "PARAMETERS", "makeParamaters", ")", ";", "DECS", "makeDecs", "COMPS", "makeCompStat", "makeProgram"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
               
               {new String[] {"DECS"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"VARDECS", "FUNCDECS"}, new String[] {"VARDECS", "FUNCDECS"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
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
               
               {new String[] {"VARDEC"}, new String[] {"identifier", "makeID", ":", "TYPE", "makeType", ";"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
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
               
               {new String[] {"FUNCDEC"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"FUNCHEADING", "FUNCBODY", ";"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
               
               {new String[] {"FUNCHEADING"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"function", "identifier", "makeID", "(", "PARAMETERS", "makeParamaters", ")", ":", "TYPE", "makeType", "makeFunc"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
               
               {new String[] {"PARAMETERS"}, new String[] {"PARAMLIST"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {""}},
               
               {new String[] {"PARAMLIST"}, new String[] {"PARAMETER", "makeParamater", "PARAMLIST'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
               
               {new String[] {"PARAMLIST'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {",", "PARAMLIST"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {""}},
               
               {new String[] {"PARAMETER"}, new String[] {"identifier", "makeID", ":", "type", "makeType"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
               
               {new String[] {"FUNCBODY"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"VARDECS", "COMPS"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"VARDECS", "COMPS"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
               
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
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {";", "STATLIST"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {""}},
               
               {new String[] {"STATEMENT"}, new String[] {"ASSIGNSTAT"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"COMPS"}, new String[] {"E"}, new String[] {"IFSTAT"}, new String[] {"E"}, new String[] {"E"}, new String[] {"WHILESTAT"}, new String[] {"E"},
                  new String[] {"FUNCCALL"}, new String[] {"return", "EXPRESSION"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {""}},
               
               {new String[] {"ASSIGNSTAT"}, new String[] {"identifier", "makeID", ":=", "EXPRESSION"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
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
               
               {new String[] {"FACTOR"}, new String[] {"identifier", "makeID", "FACTOR'"}, new String[] {"LITERAL"}, new String[] {"LITERAL"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"(", "EXPRESSION", ")"}, new String[] {"E"}, new String[] {"E"}},
               
               {new String[] {"FACTOR'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"(", "ARGS", ")"}, new String[] {"E"}, new String[] {""}},
               
               {new String[] {"FUNCCALL"}, new String[] {"identifier", "makeID", "(", "ARGS", ")"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"print", "(", "ARGS", ")", "makePrintStat"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
               
               {new String[] {"ARGS"}, new String[] {"ARGLIST", "makeArgs"}, new String[] {"ARGLIST", "makeArgs"}, new String[] {"ARGLIST", "makeArgs"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"ARGLIST", "makeArgs"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"ARGLIST", "makeArgs"}, new String[] {"E"}, new String[] {""}},
               
               {new String[] {"ARGLIST"}, new String[] {"EXPRESSION", "ARGLIST'"}, new String[] {"EXPRESSION", "ARGLIST'"}, new String[] {"EXPRESSION", "ARGLIST'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"EXPRESSION", "ARGLIST'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"EXPRESSION", "ARGLIST'"}, new String[] {"E"}, new String[] {"E"}},
               
               {new String[] {"ARGLIST'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {",", "ARGLIST"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {""}},
               
               {new String[] {"LITERAL"}, new String[] {"E"}, new String[] {"integerNum", "makeIntNum"}, new String[] {"realNum", "makeRealNum"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
               
               {new String[] {"RETURNSTAT"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"return", "EXPRESSION", "makeReturnStat"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
               };
      }
   }