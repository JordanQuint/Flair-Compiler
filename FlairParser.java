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
      private FlairToken lastToken;
    
      public FlairParser(String flairFile){
         tokenStream = new FlairScanner(flairFile);
         this.createTable();
      }
		
		public static void main(String[] args){
	     FlairParser parse = new FlairParser(args[0]);
		  System.out.println(parse.parse());
		  
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
			int count = 0;
        
         while(!parseStack.peek().equals("$"))
         {
			System.out.println("\nCurrentToken Type      " + currentToken.getType());
			if(currentToken.getType().equals("Error")){
			    System.out.println("error" + currentToken.getType());
				 System.exit(0);
				 
			}
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
                  System.out.println("Error with1 " + currentToken.getType());
                  return new FlairAbstractSyntaxTree();
               }
            }
            
            else if(states.contains(parseStack.peek()))
            {
               String firstValue = parseTable[states.indexOf(parseStack.peek())+1][tokenList.indexOf(currentToken.getType())+1][0];
            	 
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
                     System.out.println("Error with2 " + currentToken.getType());
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
               this.chooseAction(parseStack.peek());
               parseStack.pop();
            }
				if(count>10){
				System.out.println("Top of parse stack: " + parseStack.peek() + "  Top of semantic stack: " + semanticStack.peek() + " Semantic type: " + semanticStack.peek().getClass() );}
				count = count +1;
         }
        
         return (Program) semanticStack.pop();  
      }
    
      private void chooseAction(String action)
      {
         if(action.equals("makeProgram")){
				CompStatement c = (CompStatement) semanticStack.pop();
				//Declarations d = new Declarations();
				//if(semanticStack.peek() instanceof Declarations){
				System.out.println(semanticStack.peek().toString());
				Declarations d = (Declarations) semanticStack.pop();
				System.out.println(semanticStack.peek().toString());
			   //}
				//Parameters p = new Parameters();
				//if(semanticStack.peek() instanceof Parameters){
				Parameters p = (Parameters) semanticStack.pop();
			   //}
				Identifier i = (Identifier) semanticStack.pop();
            semanticStack.push(new Program(i,p,d,c));
         }
         else if(action.equals("makeIntNum")){
            semanticStack.push(new IntegerNum(lastToken.getValue()));
         }
         else if(action.equals("makeRealNum"))
         {
            semanticStack.push(new RealNum(lastToken.getValue()));
         }
         else if(action.equals("makeID"))
         {
            semanticStack.push(new Identifier(lastToken.getValue()));
         }
         else if(action.equals("makeType"))
         {
            semanticStack.push(new Type(lastToken.getType()));
         }
         else if(action.equals("makeFuncCall")){
				Arguments a2 = (Arguments) semanticStack.pop();
				Identifier i2 = (Identifier) semanticStack.pop();
            semanticStack.push(new FuncCall(i2,a2));
         }
         else if(action.equals("makeFuncDec")){
			   FuncBody fdfb = (FuncBody) semanticStack.pop();
				FuncHead fdfh = (FuncHead) semanticStack.pop();
            semanticStack.push(new FuncDec(fdfh, fdfb));
         }
			else if(action.equals("makeFuncDecs"))
         {
			   ArrayList<FuncDec> fas = new ArrayList<FuncDec>();
				
			   while(semanticStack.peek() instanceof FuncDec){
				   fas.add((FuncDec) semanticStack.pop());
				}
				
				if(fas.size() == 0){
				   semanticStack.push(new FuncDecs());
				}
				
				else if(fas.size() > 0){
				    ArrayList<FuncDec> fasu = new ArrayList<FuncDec>();
					 for(int v=fas.size()-1;v>-1;v--){
					     fasu.add(fas.get(v));
					 }
                semanticStack.push(new FuncDecs(fasu));}
         }
         else if(action.equals("makeVarDec")){
			   Type vdt = (Type) semanticStack.pop();
				Identifier vdi = (Identifier) semanticStack.pop();
            semanticStack.push(new VarDec(vdi, vdt));
         }
			else if(action.equals("makeVarDecs"))
         {
			   ArrayList<VarDec> vas = new ArrayList<VarDec>();
			   while(semanticStack.peek() instanceof VarDec){
				   vas.add((VarDec) semanticStack.pop());
				}
				if(vas.size() == 0){
				   semanticStack.push(new VarDecs());
				}
				else if(vas.size() > 0){
				    ArrayList<VarDec> vasu = new ArrayList<VarDec>();
					 for(int b=vas.size()-1;b>-1;b--){
					     vasu.add(vas.get(b));
					 }
                semanticStack.push(new VarDecs(vasu));
				}
         }
			else if(action.equals("makeDecs")){
			   FuncDecs fdq = new FuncDecs();
			   if(semanticStack.peek() instanceof FuncDecs){
				    fdq = (FuncDecs) semanticStack.pop();
			   }
				VarDecs vdq = new VarDecs();
			   if(semanticStack.peek() instanceof VarDecs){
				    vdq = (VarDecs) semanticStack.pop();
			   }
				semanticStack.push(new Declarations(vdq, fdq));
			}
         else if(action.equals("makeFuncHeading")){
			   Type fht = (Type) semanticStack.pop();
				Parameters fhp = (Parameters) semanticStack.pop();
				Identifier fhi = (Identifier) semanticStack.pop();
            semanticStack.push(new FuncHead(fhi,fhp,fht));
         }
         else if(action.equals("makeFuncBody")){
			   CompStatement fbcs = (CompStatement) semanticStack.pop();
			   VarDecs fbvds = (VarDecs) semanticStack.pop();
            semanticStack.push(new FuncBody(fbvds, fbcs));
         }
         else if(action.equals("makeParameters"))
         {
			   ArrayList<Parameter> ps = new ArrayList<Parameter>();
			   while(semanticStack.peek() instanceof Parameter){
				   ps.add((Parameter) semanticStack.pop());
				}
				if(ps.size() == 0){
				   semanticStack.push(new Parameters());
				}
				else if(ps.size() > 0){
				    ArrayList<Parameter> psu = new ArrayList<Parameter>();
					 for(int n=ps.size()-1;n>-1;n--){
					     psu.add(ps.get(n));
					 }
                semanticStack.push(new Parameters(psu));
				}
         }
         else if(action.equals("makeParameter"))
         {
            Type pt = (Type) semanticStack.pop();
				Identifier pi = (Identifier) semanticStack.pop();
            semanticStack.push(new Parameter(pi, pt));
         }
			else if(action.equals("makeStatements"))
         {
			   ArrayList<Statement> sss = new ArrayList<Statement>();
			   while(semanticStack.peek() instanceof Statement){
				   sss.add((Statement) semanticStack.pop());
				}
				if(sss.size() >1){
				    ArrayList<Statement> sasu = new ArrayList<Statement>();
					 for(int y=sss.size()-1;y>-1;y--){
					     sasu.add(sss.get(y));}
					 
				semanticStack.push(new Statements(sasu));}
				
				else{
				semanticStack.push(new Statements(sss));
				}
         }
			//else if(action.equals("makeStatement"))
         //{
			  //ArrayList<Expression> as = new ArrayList<Expression>();
			  //if(semanticStack.peek() instanceof Expression){
				 // as.add((Expression) semanticStack.pop());
				//}
           // semanticStack.push(new Arguments(as));
         //}
         else if(action.equals("makeAssignStat")){
			   Expression ase = (Expression) semanticStack.pop();
				Identifier asi = (Identifier) semanticStack.pop();
            semanticStack.push(new AssignStatement(asi, ase));
         }
         else if(action.equals("makeIfStat")){
			   Statement iss2 = (Statement) semanticStack.pop();
				Statement iss1 = (Statement) semanticStack.pop();
				Comparison isc = (Comparison) semanticStack.pop();
            semanticStack.push(new IfStatement(isc, iss1, iss2));
         }
         else if(action.equals("makeWhileStat")){
			   Statement wss = (Statement) semanticStack.pop();
				Comparison wsc = (Comparison) semanticStack.pop();
            semanticStack.push(new WhileStatement(wsc, wss));
         }
         else if(action.equals("makeReturnStat")){
			   Expression rse = (Expression) semanticStack.pop();
            semanticStack.push(new ReturnStatement(rse));
         }
         else if(action.equals("makeCompStat"))
         {
			   Statements csss = (Statements) semanticStack.pop();
            semanticStack.push(new CompStatement(csss));
         }
			else if(action.equals("makePrintStat")){
			   System.out.println("LOOKATIT  :  " + semanticStack.peek() + "    " + semanticStack.peek().getClass());
			   Arguments pa2 = (Arguments) semanticStack.pop();
            semanticStack.push(new PrintStatement(pa2));
         }
         //else if(action.equals("makeExp"))
         //{
            //semanticStack.push(new Expression(semanticStack.pop()));
         //}
         else if(action.equals("makeNegExp"))
         {
			   Expression nee = (Expression) semanticStack.pop();
            semanticStack.push(new NegExp(nee));
         }
         else if(action.equals("makeAddExp"))
         {
			   Expression aee2 = (Expression) semanticStack.pop();
				Expression aee1 = (Expression) semanticStack.pop();
            semanticStack.push(new AddExp(aee1, aee2));
         }
         else if(action.equals("makeSubExp"))
         {
			   Expression see2 = (Expression) semanticStack.pop();
				Expression see1 = (Expression) semanticStack.pop();
            semanticStack.push(new SubExp(see1, see2));
         }
         else if(action.equals("makeMultExp"))
         {
			   Expression mee2 = (Expression) semanticStack.pop();
				Expression mee1 = (Expression) semanticStack.pop();
            semanticStack.push(new MultExp(mee1, mee2));
         }
         else if(action.equals("makeDivExp"))
         {
			   Expression dee2 = (Expression) semanticStack.pop();
				Expression dee1 = (Expression) semanticStack.pop();
            semanticStack.push(new DivExp(dee1, dee2));
         }
         else if(action.equals("makeArgs"))
         {
			   ArrayList<Expression> as = new ArrayList<Expression>();
			   while(semanticStack.peek() instanceof Expression){
				   as.add((Expression) semanticStack.pop());
				}
				if(as.size() == 0){
				   semanticStack.push(new Arguments());
				}
				else if(as.size() > 0){
				   ArrayList<Expression> asu = new ArrayList<Expression>();
					 for(int m=as.size()-1;m>-1;m--){
					     asu.add(as.get(m));
					 }
               semanticStack.push(new Arguments(asu));
				}
         }
			else if(action.equals("makeComp"))
         {
			   Expression deec2 = (Expression) semanticStack.pop();
				CompareOp co1 = (CompareOp) semanticStack.pop();
				Expression deec1 = (Expression) semanticStack.pop();
			   semanticStack.push(new Comparison(deec1,co1,deec2));
         }
			else if(action.equals("makeCompOp"))
         {
			   semanticStack.push(new CompareOp(lastToken.getType()));
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
               
               {new String[] {"PROGRAM"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"program", "identifier", "makeID", "(", "PARAMETERS", "makeParameters", ")", ";", "DECS", "makeDecs", "COMPS", "makeCompStat", "makeProgram"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
               
               {new String[] {"DECS"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"VARDECS", "makeVarDecs", "FUNCDECS", "makeFuncDecs"}, new String[] {"VARDECS", "makeVarDecs", "FUNCDECS", "makeFuncDecs"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"VARDECS", "makeVarDecs", "FUNCDECS", "makeFuncDecs"}},
               
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
               
               {new String[] {"VARDEC"}, new String[] {"identifier", "makeID", ":", "TYPE", "makeType", ";", "makeVarDec"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
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
               
               {new String[] {"FUNCDEC"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"FUNCHEADING", "makeFuncHeading", "FUNCBODY", "makeFuncBody", ";", "makeFuncDec"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
               
               {new String[] {"FUNCHEADING"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"function", "identifier", "makeID", "(", "PARAMETERS", "makeParameters", ")", ":", "TYPE", "makeType"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
               
               {new String[] {"PARAMETERS"}, new String[] {"PARAMLIST"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {""}},
               
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
               
               {new String[] {"PARAMETER"}, new String[] {"identifier", "makeID", ":", "TYPE", "makeType", "makeParameter"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
               
               {new String[] {"FUNCBODY"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"VARDECS", "makeVarDecs", "COMPS", "makeCompStat"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"VARDECS", "makeVarDecs", "COMPS", "makeCompStat"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
               
               {new String[] {"TYPE"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"integer"},
                  new String[] {"real"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
               
               {new String[] {"COMPS"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"begin", "STATLIST", "makeStatements", "end"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
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
               
               {new String[] {"STATEMENT"}, new String[] {"ASSIGNSTAT", "makeAssignStat"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"COMPS", "makeCompStat"}, new String[] {"E"}, new String[] {"IFSTAT", "makeIfStat"}, new String[] {"E"}, new String[] {"E"}, new String[] {"WHILESTAT", "makeWhileStat"}, new String[] {"E"},
                  new String[] {"PRINTSTAT", "makePrintStat"}, new String[] {"RETURNSTAT", "makeReturnStat"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {""}},
               
               {new String[] {"ASSIGNSTAT"}, new String[] {"identifier", "makeID", ":=", "EXPRESSION"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
					
					{new String[] {"PRINTSTAT"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"print", "(", "ARGS", "makeArgs", ")"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
               
               {new String[] {"IFSTAT"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"if", "COMP", "makeComp", "then", "STATEMENT", "else", "STATEMENT"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
               
               {new String[] {"WHILESTAT"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"while", "COMP", "makeComp", "do", "STATEMENT"}, new String[] {"E"},
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
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"=", "makeCompOp"}, new String[] {"!=", "makeCompOp"}, new String[] {"<", "makeCompOp"}, new String[] {"<=", "makeCompOp"}, new String[] {">", "makeCompOp"},
                  new String[] {">=", "makeCompOp"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
               
               {new String[] {"EXPRESSION"}, new String[] {"TERM", "EXPRESSION'"}, new String[] {"TERM", "EXPRESSION'"}, new String[] {"TERM", "EXPRESSION'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"-", "EXPRESSION", "makeNegExp"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"TERM", "EXPRESSION'"}, new String[] {"E"}, new String[] {"TERM", "EXPRESSION'"}},
               
               {new String[] {"EXPRESSION'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"+", "TERM", "makeAddExp", "EXPRESSION'"}, new String[] {"-", "TERM", "makeSubExp", "EXPRESSION'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {""}},
               
               {new String[] {"TERM"}, new String[] {"FACTOR", "TERM'"}, new String[] {"FACTOR", "TERM'"}, new String[] {"FACTOR", "TERM'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"FACTOR", "TERM'"}, new String[] {"E"}, new String[] {"E"}},
               
               {new String[] {"TERM'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"*", "TERM", "makeMultExp"}, new String[] {"/", "TERM", "makeDivExp"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {""}},
               
               {new String[] {"FACTOR"}, new String[] {"identifier", "FACTOR'"}, new String[] {"LITERAL"}, new String[] {"LITERAL"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"(", "EXPRESSION", ")"}, new String[] {"E"}, new String[] {"E"}},
               
               {new String[] {"FACTOR'"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"makeID", "(", "ARGS", "makeArgs", ")", "makeFuncCall"}, new String[] {"E"}, new String[] {"makeID"}},
               
               {new String[] {"FUNCCALL"}, new String[] {"identifier", "makeID", "(", "ARGS", "makeArgs", ")", "makeFuncCall"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
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
                  new String[] {"E"}, new String[] {",", "ARGLIST"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {""}},
               
               {new String[] {"LITERAL"}, new String[] {"E"}, new String[] {"integerNum", "makeIntNum"}, new String[] {"realNum", "makeRealNum"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
               
               {new String[] {"RETURNSTAT"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"return", "EXPRESSION"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"},
                  new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}, new String[] {"E"}},
               };
      }
   }