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
      private Declaration decs;
      private CompStatement cstat;
    
      public Program(Identifier n, Parameters p, Declaration d, CompStatement c)
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
		
		public Declaration declarations(){
		    return decs;
		}
		
		public CompStatement compStatement(){
		    return cstat;
		}
    
      public void allowVisit(FlairVisitor visitor)
      {
          visitor.visit(this);
      }
    
      public String toString()
      {
         return "program\n" + name.toString() + "(" + parameters.toString() + ");" +
                decs.toString() + cstat.toString() + ".";
      }
   }


//DECLARATIONS
   class Declaration extends FlairAbstractSyntaxTree
   {
     private VarDecs vdecs;
     private FuncDecs fdecs;
     
     public Declaration(VarDecs v, FuncDecs f)
     {
	  	 vdecs = v;
       fecs = f;
     }
     
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      } 
         
      public String toString()
      {
		    return vdecs + fdecs;
      }
   }
	
	class VarDecs extends Declaration
   {
     private ArrayList<VarDec> variables;
     
     public VarDecs(Identifier i, Type t)
     {
       identifier = i;
       type = t;
     }
	  
	  public Identifier name()
	  {
	      return identfier;
	  }
	  
	  public Type theType()
	  {
	      return type;
	  }

     
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      }
      
      public String toString()
      {
         return identifier + ":" + type + "\n";
      }
   }
	
	class FuncDecs extends Declaration
   {
     private Identifier identifier;
     private Type type;
     
     public FuncDecs(Identifier i, Type t)
     {
       identifier = i;
       type = t;
     }
	  
	  public Identifier name()
	  {
	      return identfier;
	  }
	  
	  public Type theType()
	  {
	      return type;
	  }

     
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      }
      
      public String toString()
      {
         return identifier + ":" + type + "\n";
      }
   }
	
	

   class VarDec extends Declaration
   {
     private Identifier identifier;
     private Type type;
     
     public VarDec(Identifier i, Type t)
     {
       identifier = i;
       type = t;
     }
	  
	  public Identifier name()
	  {
	      return identfier;
	  }
	  
	  public Type theType()
	  {
	      return type;
	  }

     
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      }
      
      public String toString()
      {
         return identifier + ":" + type + "\n";
      }
   }

   class FuncDec extends Declaration
   {
     //fxn_head
     private Identifier name;
     private Parameters params;
     private Type return_type;
     
     //fxn_body
     private ArrayList<VarDec> variables;
     private StatementList stats;
     
     public FuncDec(Identifier n, Parameters p, Type t, VarDec v, StatementList s)
     {
       name = n;
       params = p;
       return_type = t;
		 
		 //should probably make another constructor if there are no vardecs?
		 variables = new ArrayList<VarDec>();
       variables.add(v);
       stats = s;
     }
	  
	  public void addVarDec(VarDec v)
	  {
		variables.add(v);
	  }
     
     public void allowVisit(FlairVisitor visitor)
     {
       visitor.visit(this);
     }
    
     public String toString()
     {
	  	String vars = "";
		for(VarDec v : variables)
		{
			vars += v.toString();
		}
	  
       return "function" + name.toString() + "(" + params.toString() + ") :" +
              return_type.toString() + "\n" + "var\n" + vars + stats.toString() + ";";
     }
   }
	
	class FuncHead extends FlairAbstractSyntaxTree
	{
	
	}
	
	class FuncBody extends FlairAbstractSyntaxTree
	{
	
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
    
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      }    
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
    
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      }    
   }

   class Identifier extends Expression
   {
      private String word;
    
      public Identifier(FlairToken w)
      {
         word = (String)w.getValue();
      }
    
      public String toString()
      {
         return word;
      }
    
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      }    
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
    
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      }
    
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
		 
		 public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      }
		 
		 
	}
	
	class Parameters extends FlairAbstractSyntaxTree
	{
	    private ArrayList<Parameter> parameters;
	}
	
	class Statement extends FlairAbstractSyntaxTree
   {
     public void allowVisit(FlairVisitor visitor)
     {
       visitor.visit(this);
     }

     public String toString()
     {
       return "";
     }
   }
	
	class Statements extends FlairAbstractSyntaxTree{
	
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
		 
		 public void allowVisit(FlairVisitor visitor)
       {
           visitor.visit(this);
       }

       public String toString()
       {
           return id + " = " + exp;
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
		 
		 public Identifier getComp(){
		     return comp;
		 }
		 
		 public void allowVisit(FlairVisitor visitor)
       {
           visitor.visit(this);
       }

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
		 
		 public Identifier getComp(){
		     return comp;
		 }
		 
		 public void allowVisit(FlairVisitor visitor)
       {
           visitor.visit(this);
       }

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
		 		 
		 public void allowVisit(FlairVisitor visitor)
       {
           visitor.visit(this);
       }

       public String toString()
       {
           return "return " + exp;
       }
	}
	
	class CompStatement extends Statement{
	    private Statements stats;
		 
		 public WhileStatement(Statements s){
			  stats=s;
		 }
		 
		 public void allowVisit(FlairVisitor visitor)
       {
           visitor.visit(this);
       }

       public String toString()
       {
           return "begin " + stats + " end";
       }
	}
	
	class PrintStatement extends Statement{
	    private Expression exp;
		 
		 public WhileStatement(Expression e){
		     exp=e;
		 }
		 
		 public void allowVisit(FlairVisitor visitor)
       {
           visitor.visit(this);
       }

       public String toString()
       {
           return "return " + exp;
       }
	}
	
	class Expression extends FlairAbstractSyntaxTree{
	
	}
	
	class NegateExp extends Expression{
	    private Expression exp;
		 
		 public NegateExp(Expression e){
		     exp = e;
		 }
	    
		 public void allowVisit(FlairVisitor visitor)
       {
           visitor.visit(this);
       }

       public String toString()
       {
           return "- " + exp;
       }
	}
	
	class AddExp extends Expression{
	    private Expression exp1;
		 private Expression exp2;
		 
		 public NegateExp(Expression e1, Expression e2){
		     exp1 = e1;
			  exp2 = e2;
		 }
	    
		 public void allowVisit(FlairVisitor visitor)
       {
           visitor.visit(this);
       }

       public String toString()
       {
           return exp1 + " + " + exp2;
       }
	
	}
	
	class SubExp extends Expression{
	    private Expression exp1;
		 private Expression exp2;
		 
		 public NegateExp(Expression e1, Expression e2){
		     exp1 = e1;
			  exp2 = e2;
		 }
	    
		 public void allowVisit(FlairVisitor visitor)
       {
           visitor.visit(this);
       }

       public String toString()
       {
           return exp1 + " - " + exp2;
       }
	}
	
	class MultExp extends Expression{
	    private Expression exp1;
		 private Expression exp2;
		 
		 public NegateExp(Expression e1, Expression e2){
		     exp1 = e1;
			  exp2 = e2;
		 }
	    
		 public void allowVisit(FlairVisitor visitor)
       {
           visitor.visit(this);
       }

       public String toString()
       {
           return exp1 + " * " + exp2;
       }
	}
	
	class DivExp extends Expression{
	    private Expression exp1;
		 private Expression exp2;
		 
		 public NegateExp(Expression e1, Expression e2){
		     exp1 = e1;
			  exp2 = e2;
		 }
	    
		 public void allowVisit(FlairVisitor visitor)
       {
           visitor.visit(this);
       }

       public String toString()
       {
           return exp1 + " / " + exp2;
       }
	}
	
	class FuncCall extends Expression{
	    private Identifier id;
		 private Expression args;
		 
		 public NegateExp(Identifier i, Expression e){
		     id = i;
			  args = e;
		 }
	    
		 public void allowVisit(FlairVisitor visitor)
       {
           visitor.visit(this);
       }

       public String toString()
       {
           return id + " /n " + args;
       }
	}
	
	class Arguments extends FlairAbstractSyntaxTree{
	
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
		 
		 public void allowVisit(FlairVisitor visitor)
       {
           visitor.visit(this);
       }

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
		 
		 public void allowVisit(FlairVisitor visitor)
       {
           visitor.visit(this);
       }

       public String toString()
       {
           return op;
       }
	
	}