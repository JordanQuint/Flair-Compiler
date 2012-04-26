/*
  Compiler Project: Parser 04/19/12
  Authors: Andrew, Jordan, and Zane
  FlairAbstractSyntaxTree class
*/

   import java.util.*;
   import java.io.*;

   public class FlairAbstractSyntaxTree
   { 
      public String toString()
      {
         return "";
      }    
   }

   class Program extends FlairAbstractSyntaxTree{
      private Identifier name;
      private Parameters parameters;
      private Declarations decs;
      private CompStatement cstat;
    
      public Program(Identifier n, Parameters p, Declarations d, CompStatement c)
		{
         name = n;
         parameters = p;
         decs = d;
         cstat = c;
      }
		
		public Identifier name(){
		    return name;
		}
		
		public Parameters parameters(){
		    return parameters;
		}
		
		public Declarations declarations(){
		    return decs;
		}
		
		public CompStatement compStatement(){
		    return cstat;
		}
    
	   /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/
    
      public String toString()
      {
         return "\n\n\nprogram\n" + "\t" + "name(" + name.toString() + ")\n\tparameters(" + parameters.toString() + ")" + "\n\t" +
                decs.toString() + "\n\t" + cstat.toString();
      }
   }


//DECLARATIONS
   class Declarations extends FlairAbstractSyntaxTree
   {
     private VarDecs vdecs;
     private FuncDecs fdecs;
     
     public Declarations(VarDecs v, FuncDecs f)
     {
	  	 vdecs = v;
       fdecs = f;
     }
     
      /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/ 
         
      public String toString()
      {
		    return "Var Decs: " + vdecs.toString() + "\tFunc Decs: " + fdecs.toString();
      }
   }
	
	class VarDecs extends FlairAbstractSyntaxTree
   {	
		private ArrayList<VarDec> variables = new ArrayList<VarDec>();
		 
		 public VarDecs(ArrayList<VarDec> vds){
		     for(int i=0; i<vds.size(); i++){
		         variables.add((VarDec)vds.get(i));
			  }
		 }
		 
		 public VarDecs(){
		 
		 }
		 
		 /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/

       public String toString()
       {
		     String theVars = "";
		     for(int z = 0; z<variables.size(); z++){
			     theVars = theVars + variables.get(z);
			  }
           return theVars;
       }
   }
	
	class FuncDecs extends FlairAbstractSyntaxTree
   {
     private ArrayList<FuncDec> functions = new ArrayList<FuncDec>();
		 
		 public FuncDecs(ArrayList<FuncDec> fds){
		     for(int i=0; i<fds.size(); i++){
		         functions.add((FuncDec)fds.get(i));
			  }
		 }
		 
		 public FuncDecs(){
		 
		 }
		 
		 /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/

       public String toString()
       {
		     String theFuncs = "";
		     for(int z = 0; z<functions.size(); z++){
			     theFuncs = theFuncs + functions.get(z) + "\n";
			  }
           return theFuncs;
       }
   }
	
	

   class VarDec extends FlairAbstractSyntaxTree
   {
     private Identifier identifier;
     private Type type;
     
     public VarDec(Identifier i, Type t){
       identifier = i;
       type = t;
     }
	  
	  public Identifier name(){
	      return identifier;
	  }
	  
	  public Type theType(){
	      return type;
	  }
     
      /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/
      
      public String toString(){
         return identifier + " : " + type + "\n";
      }
   }

   class FuncDec extends FlairAbstractSyntaxTree
   {
     private FuncHead fh;
	  private FuncBody fb;
     
     public FuncDec(FuncHead h, FuncBody b)
     {
       fh = h;
       fb = b;
     }
	  
	  public FuncHead head()
	  {
		 return fh;
	  }
	  
	  public FuncBody body()
	  {
		 return fb;
	  } 
     
     /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/
    
     public String toString()
     {
	    return fh.toString() + fb.toString();
     }
   }
	
	class FuncHead extends FlairAbstractSyntaxTree{
	    private Identifier id;
		 private Parameters params;
		 private Type type;
		 
		 public FuncHead(Identifier i, Parameters p, Type t){
		     id = i;
			  params = p;
			  type = t;
		 }
		 
		 public Identifier getID(){
		     return id;
		 }
		 
		 public Parameters getParams(){
		     return params;
		 }
		 
		 public Type getType(){
		     return type;
		 }
		 
		 /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/
		 
		 public String toString()
       {
	        return "function " + id.toString() + " (" + params.toString() + ") : " + type.toString();
       }
	
	}
	
	class FuncBody extends FlairAbstractSyntaxTree
	{
	    private VarDecs vdecs;
		 private CompStatement cstat;
		 
		 public FuncBody(VarDecs v, CompStatement c){
		     vdecs = v;
			  cstat = c;
		 }
		 
		 public VarDecs getV(){
		     return vdecs;
		 }
		 
		 public CompStatement getC(){
		     return cstat;
		 }
		 
		 /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/
		 
		 public String toString()
       {
	        return vdecs.toString() + cstat.toString();
       }
	}
	
	
	
	
	class Literal extends Expression
   {
     
   }

   class IntegerNum extends Literal
   {
      private String number;
    
      public IntegerNum(String n)
      {
         number = n;
      }
    
      public String toString()
      {
         return number;
      }
    
      /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/    
   }

   class RealNum extends Literal
   {
      private String number;
    
      public RealNum(String n)
      {
         number = n;
      }
    
      public String toString()
      {
         return number;
      }
    
      /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/    
   }

   class Identifier extends Expression
   {
      private String word;
    
      public Identifier(String w)
      {
         word = w;
      }
    
      public String toString()
      {
         return "Variable " + word;
      }
    
      /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/    
   }


//TYPE
   class Type extends FlairAbstractSyntaxTree
   {
      private String selection;
    
      public Type(String t)
      {
         selection = t;
      }
    
      public String toString()
      {
         return "Type = " + selection;
      }
    
      /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/
    
   }
	
	class Parameter extends FlairAbstractSyntaxTree
	{
	    private Identifier id;
		 private Type theType;
		 
		 public Parameter(Identifier i, Type t)
		 {
		     id = i;
			  theType = t;
		 }
		 
		 public Identifier getID()
		 {
		     return id;
		 }
		 
		 public Type getTheType()
		 {
		     return theType;
		 }
		 
		 /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/
		 
		 
	}
	
	class Parameters extends FlairAbstractSyntaxTree
	{
	    private ArrayList<Parameter> params = new ArrayList<Parameter>();
		 
		 public Parameters(ArrayList<Parameter> paramss){
		     for(int i=0; i<paramss.size(); i++){
		         params.add((Parameter)paramss.get(i));
			  }
		 }
		 
		 public Parameters(){
		 
		 }
		 
		 /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/

       public String toString()
       {
		     String theParams = "";
		     for(int z = 0; z<params.size(); z++){
			     theParams = theParams + params.get(z) + "\n";
			  }
           return theParams;
       }
	}
	
	class Statement extends FlairAbstractSyntaxTree
   {
     /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/

     public String toString()
     {
       return "";
     }
   }
	
	class Statements extends FlairAbstractSyntaxTree{
	    private ArrayList<Statement> sss = new ArrayList<Statement>();
		 
		 public Statements(ArrayList<Statement> ssss){
		     for(int i=0; i<ssss.size(); i++){
		         sss.add((Statement)ssss.get(i));
			  }
		 }
		 
		 /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/

       public String toString()
       {
		     String theStats = "";
		     for(int q = 0; q<sss.size(); q++){
			     theStats = theStats + sss.get(q) + "\n";
			  }
           return theStats;
       }
		 
	}
	
	class AssignStatement extends Statement{
	    private Identifier id;
		 private Expression exp;
		 
		 public AssignStatement(Identifier i, Expression e){
		     id=i;
			  exp=e;
		 }
		 
		 public Identifier getID(){
		     return id;
		 }
		 
		 /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/

       public String toString()
       {
           return id + " a= " + exp;
       }
		 
	}
	
	class IfStatement extends Statement{
	    private Comparison comp;
		 private Statement stat1;
		 private Statement stat2;
		 
		 public IfStatement(Comparison c, Statement s1, Statement s2){
		     comp=c;
			  stat1=s1;
			  stat2=s2;
		 }
		 
		 public Comparison getComp(){
		     return comp;
		 }
		 
		 /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/

       public String toString()
       {
           return "if " + comp + " then " + stat1 + " else " + stat2;
       }
	}
	
	class WhileStatement extends Statement{
	    private Comparison comp;
		 private Statement stat;
		 
		 public WhileStatement(Comparison c, Statement s){
		     comp=c;
			  stat=s;
		 }
		 
		 public Comparison getComp(){
		     return comp;
		 }
		 
		 /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/

       public String toString()
       {
           return "while " + comp + " do " + stat;
       }
	}
	
	class ReturnStatement extends Statement{
	    private Expression exp;
		 
		 public ReturnStatement(Expression e){
		     exp = e;
		 }
		 		 
		 /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/

       public String toString()
       {
           return "return " + exp;
       }
	}
	
	class CompStatement extends Statement{
	    private Statements stats;
		 
		 public CompStatement(Statements s){
			  stats=s;
		 }
		 
		 /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/

       public String toString()
       {
           return "begin\n" + stats + "\nend";
       }
	}
	
	class PrintStatement extends Statement{
	    private Expression exp;
		 
		 public PrintStatement(Expression e){
		     exp=e;
		 }
		 
		 /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/

       public String toString()
       {
           return "return " + exp;
       }
	}
	
	class Expression extends FlairAbstractSyntaxTree{
	   
	}
	
	class NegExp extends Expression{
	    private Expression exp;
		 
		 public NegExp(Expression e){
		     exp = e;
		 }
	    
		 /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/

       public String toString()
       {
           return "- " + exp;
       }
	}
	
	class AddExp extends Expression{
	    private Expression exp1;
		 private Expression exp2;
		 
		 public AddExp(Expression e1, Expression e2){
		     exp1 = e1;
			  exp2 = e2;
		 }
	    
		 /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/

       public String toString()
       {
           return exp1 + " + " + exp2;
       }
	
	}
	
	class SubExp extends Expression{
	    private Expression exp1;
		 private Expression exp2;
		 
		 public SubExp(Expression e1, Expression e2){
		     exp1 = e1;
			  exp2 = e2;
		 }
	    
		 /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/

       public String toString()
       {
           return exp1 + " - " + exp2;
       }
	}
	
	class MultExp extends Expression{
	    private Expression exp1;
		 private Expression exp2;
		 
		 public MultExp(Expression e1, Expression e2){
		     exp1 = e1;
			  exp2 = e2;
		 }
	    
		 /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/

       public String toString()
       {
           return exp1 + " * " + exp2;
       }
	}
	
	class DivExp extends Expression{
	    private Expression exp1;
		 private Expression exp2;
		 
		 public DivExp(Expression e1, Expression e2){
		     exp1 = e1;
			  exp2 = e2;
		 }
	    
		 /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/

       public String toString()
       {
           return exp1 + " / " + exp2;
       }
	}
	
	class FuncCall extends Expression{
	    private Identifier id;
		 private Arguments args;
		 
		 public FuncCall(Identifier i, Arguments a){
		     id = i;
			  args = a;
		 }
	    
		 /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/

       public String toString()
       {
           return id + " /n " + args;
       }
	}
	
	class Arguments extends FlairAbstractSyntaxTree{
	    private ArrayList<Expression> exps = new ArrayList<Expression>();
		 
		 public Arguments(ArrayList<Expression> es){
		     for(int i=0; i<es.size(); i++){
		         exps.add((Expression)es.get(i));
			  }
		 }
		 
		 /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/

       public String toString()
       {
		     String theExps = "";
		     for(int z = 0; z<exps.size(); z++){
			     theExps = theExps + exps.get(z) + "\n";
			  }
           return theExps;
       }
	}
	
	class Comparison extends FlairAbstractSyntaxTree{
	    private Expression exp1;
		 private CompareOp op;
		 private Expression exp2;
		 
		 public Comparison(Expression e1, CompareOp c, Expression e2){
		     exp1 = e1;
			  op = c;
			  exp2 = e2;
		 }
		 
		 /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/

       public String toString()
       {
           return exp1 + " " + op + " " + exp2;
       }
		 
	}
	
	class CompareOp extends FlairAbstractSyntaxTree{
	    private String op;
		 
		 public CompareOp(String o){
		     op = o;
		 }
		 
		 /*
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
		*/

       public String toString()
       {
           return op;
       }
	
	}