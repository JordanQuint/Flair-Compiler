/*
  Compiler Project: Parser Part 1 Assignment 03/08/12
  Authors: Andrew, Jordan, and Zane
  FlairAbstractSyntaxTree class
*/

   import java.util.*;
   import java.io.*;

   public class FlairAbstractSyntaxTree
   {
     private Program program;
     
     public FlairAbstractSyntaxTree(Program p)
     {
       program = p;
     }
     
      public String toString()
      {
         return program.toString();
      }    
   }

   class Program extends FlairAbstractSyntaxTree
   {
      private Identifier name;
      private Parameters parameter;
      private Declaration decs;
      private StatementList stats;
    
      public Program(Identifier n, Parameters p, Declaration d, StatementList s)
		{
         name = n;
         parameter = p;
         decs = d;
         stats = s;
      }
    
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      }
    
      public String toString()
      {
         return "program\n" + name.toString() + "(" + parameter.toString() + ");" +
                decs.toString() + stats.toString() + ".";
      }
   }


//DECLARATIONS
   class Declaration extends FlairAbstractSyntaxTree
   {
     private ArrayList<VarDec> variables;
     private ArrayList<FuncDec> functions;
     
     public Declaration(VarDec v, FuncDec f)
     {
	  	variables = new ArrayList<VarDec>();
       variables.add(v);
		 functions = new ArrayList<FuncDec>();
       functions.add(f);
     }
     
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      } 
         
      public String toString()
      {
        String response = "";
        if (variables.size() > 0) //can have zero vardecs
        {
          for (VarDec variable : variables)
          {
            response += variable.toString();
          }
        }
        
        response += "\n";
        
        if (functions.size() > 0) //can have zero funcdecs
        {
          for (FuncDec function : functions)
          {
            response += function.toString();
          }
        }
        
        return response;
      }
      
      public void addVarDec(VarDec addition)
      {
        variables.add(addition);
      }
      
      public void addFuncDec(FuncDec addition)
      {
        functions.add(addition);
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


//STATEMENTS
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
   
   //A StatementList can contain several Statements that are themselves StatementLists
   class StatementList extends Statement
   {
     private ArrayList<Statement> list;
     
     public StatementList(Statement initial)
     {
       list.add(initial);
     }
     
     public void allowVisit(FlairVisitor visitor)
     {
        visitor.visit(this);
     }
   
     public String toString()
     {
       String response = "";
       for(Statement statement : list)
       {
         if (response != "")
         {
           response += ";\n";
         }
         response += statement.toString();
       }
       
       return "begin\n" + response + "\nend";
     }
     
     public void add(Statement addition)
     {
       list.add(addition);
     }
   }

   class PrintStatement extends Statement
   {
      private Expression expression;
      
      public PrintStatement(Expression e)
      {
        expression = e;
      }
      
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      }
    
      public String toString()
      {
         return "print " + expression.toString();
      }    
   }

   class AssignStatement extends Statement
   {
     private Identifier name;
     private Expression expression;
     
     public AssignStatement(Identifier i, Expression e)
     {
       name = i;
       expression = e;
     }
     
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      }
    
      public String toString()
      {
         return name.toString() + " := " + expression.toString();
      }
   }

   class IfStatement extends Statement
   {
     private Comparison comparison;
     private Statement then_statement;
     private Statement else_statement;
     
     public IfStatement(Comparison c, Statement t, Statement e)
     {
       comparison = c;
       then_statement = t;
       else_statement = e;
     }
     
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      }
    
      public String toString()
      {
         return "if " + comparison.toString() + " then\n" + then_statement.toString() +
                "\nelse\n" + else_statement.toString();
      }
   }

   class WhileStatement extends Statement
   {
      private Comparison comparison;
      private Statement statement;
      
      public WhileStatement(Comparison c, Statement s)
      {
        comparison = c;
        statement = s;
      }
     
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      }
    
      public String toString()
      {
         return "while " + comparison.toString() + " do\n" + statement.toString();
      }
   }

   class ReturnStatement extends Statement
   {
     private Expression expression;
     
     public ReturnStatement(Expression e)
     {
       expression = e;
     }
     
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      }
    
      public String toString()
      {
         return "return " + expression.toString();
      }
   }
   
//COMPARISON
  class Comparison extends FlairAbstractSyntaxTree
  {
    private Expression left;
    private CompareOp operator;
    private Expression right;
    
    public Comparison(Expression l, CompareOp o, Expression r)
    {
      left = l;
      operator = o;
      right = r;
    }
    
    public void allowVisit(FlairVisitor visitor)
    {
      visitor.visit(this);
    }
    
    public String toString()
    {
      return left.toString() + " " + operator.toString() + " " + right.toString();
    }
  }
  
  class CompareOp extends FlairAbstractSyntaxTree
  {
    private String op;
    
    public CompareOp(String o)
    {
      if (o.equals("=") || o.equals("<") || o.equals(">") || o.equals("<=") || o.equals(">=") || o.equals("!="))
      {
        op = o;
      }
    }
    
    public String toString()
    {
      return "op";
    }
  }


//EXPRESSIONS
   class Expression extends FlairAbstractSyntaxTree
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

   class NegateExp extends Expression
   {
     private Expression exp;
     
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      }
    
      public String toString()
      {
         return "";
      }
   }

   class AdditionExp extends Expression
   {
     private Expression exp;
     private Term term;
     
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      }
    
      public String toString()
      {
         return "";
      }
   }

   class SubtractExp extends Expression
   {
     private Expression exp;
     private Term term;
     
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      }
    
      public String toString()
      {
         return "";
      }
   }
   
   class Term extends FlairAbstractSyntaxTree
   {
   //placeholder superclass for the three types of terms below
     public String toString()
     {
        return "";
     }
   }

   class MultiplicationTerm extends Term
   {
     private Term term;
     private Factor factor;
     
     public MultiplicationTerm(Term t, Factor f)
     {
       term = t;
       factor = f;
     }
     
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      }
    
      public String toString()
      {
         return term.toString() + " * " + factor.toString();
      }
   }

   class DivisionTerm extends Term
   {
      private Term term;
      private Factor factor;

      public DivisionTerm(Term t, Factor f)
      {
        term = t;
        factor = f;
      }

       public void allowVisit(FlairVisitor visitor)
       {
          visitor.visit(this);
       }

       public String toString()
       {
          return term.toString() + " / " + factor.toString();
       }
   }
   
   class Factor extends Term
   {
     private Expression exp = null;
     private FunctionCall ftn = null;
     private Identifier var = null;
     private Literal val = null;
     
     public Factor(Expression e)
     {
       exp = e;
     }
     
     public Factor(FunctionCall f)
     {
       ftn = f;
     }
     
     public Factor(Identifier v)
     {
       var = v;
     }
     
     public Factor(Literal v)
     {
       val = v;
     }
     
     public void allowVisit(FlairVisitor visitor)
     {
       visitor.visit(this);
     }
     
     public String toString()
     {
       if (exp != null)
         return "( " + exp.toString() + " )";
       else if (ftn != null)
         return ftn.toString();
       else if (var != null)
         return var.toString();
       else if (val != null)
         return val.toString();
     }
   }

   class FunctionCall extends Expression
   {
     private Identifier name;
     private Arguments args;
     
     public FunctionCall(Identifier n, Arguments a)
     {
       name = n;
       args = a;
     }
     
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      }
    
      public String toString()
      {
         return name.toString() + " ( " + args.toString() + " ) ";
      }
   }
   
   class Literal extends FlairAbstractSyntaxTree
   {
     
   }

   class IntegerNum extends Literal
   {
      private String number;
    
      public IntegerNum(FlairToken n)
      {
         number = (String)n.getValue();
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
    
      public RealNum(FlairToken n)
      {
         number = (String)n.getValue();
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
    
      public Type(FlairToken t)
      {
         selection = t.getValue();
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


//PARAMETERS
   class Parameters extends FlairAbstractSyntaxTree
   {
     private ArrayList<Parameter> list;
     
     public Parameters(Parameter p)
     {
       list = new ArrayList<Parameter>();
       list.add(p);
     }
     
     public void add(Parameter addition)
     {
       list.add(addition);
     }
     
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      }
    
      public String toString()
      {
         String response = "";
         for(Parameter p : list)
         {
           if (response == "")
             response += p.toString();
           else
             response += ", " + p.toString();
         }
         return response;
      }
   }

   class Parameter extends Parameters
   {
     private Identifier name;
     private Type type;
     
     public Parameter(Identifier i, Type t)
     {
       name = i;
       type = t;
     }
     
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      }
    
      public String toString()
      {
         return name.toString() + " : " + type.toString();
      }
   }


//ARGUMENTS
   class Arguments extends FlairAbstractSyntaxTree
   {
     private ArrayList<Expression> list;
     
     public Arguments(Expression a)
     {
       list = new ArrayList<Expression>();
       list.add(a);
     }
     
     public void add(Expression a)
     {
       list.add(a);
     }
     
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      }
    
      public String toString()
      {
        String response = "";
        
        for(Expression e : list)
        {
          if (response == "")
            response += e.toString();
          else
            response += ", " + e.toString();
        }
        
         return response;
      } 
   }