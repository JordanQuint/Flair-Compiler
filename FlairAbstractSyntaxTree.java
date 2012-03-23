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
         return "program\n" + name.toString() + "(" + parameters.toString() + ");" +
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
       variables = v;
       functions = f;
     }
     
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      } 
         
      public String toString()
      {
        response = "";
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
     private VarDecs variables;
     private StatementList stats;
     
     public FuncDec(Identifier n, Parameters p, Type t, VarDecs v, StatementList s)
     {
       name = n;
       params = p;
       return_type = r;
       variables = v;
       stats = s;
     }
     
     public void allowVisit(FlairVisitor visitor)
     {
       visitor.visit(this);
     }
    
     public String toString()
     {
       return "function" + name.toString() + "(" + params.toString() + ") :" +
              return_type.toString() + "\n" + variables.toString() + stats.toString() + ";";
     }
   }


//STATEMENTS
    class StatementList extends FlairAbstractSyntaxTree
    {
      private ArrayList<Statement> list;
      
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      }
    
      public String toString()
      {
        response = "";
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

   class PrintStatement extends Statement
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

   class AssignStatement extends Statement
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

   class IfStatement extends Statement
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

   class WhileStatement extends Statement
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

   class ReturnStatement extends Statement
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
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      }
    
      public String toString()
      {
         return "";
      }
   }

   class MultiplicationExp extends Expression
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

   class DivisionExp extends Expression
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

   class FunctionCall extends Expression
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

   class IntegerNum extends Expression
   {
      private String number;
    
      public IntegerNum(Token n)
      {
         number = n.getValue();
      }
    
      public String toString()
      {
         return "Integer = " + number;
      }
    
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      }    
   }

   class RealNum extends Expression
   {
      private String number;
    
      public RealNum(Token n)
      {
         number = n.getValue();
      }
    
      public String toString()
      {
         return "Real = " + number;
      }
    
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      }    
   }

   class Identifier extends Expression
   {
      private String word;
    
      public RealNum(Token w)
      {
         word = w.getValue();
      }
    
      public String toString()
      {
         return "Identifier = " + number;
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
    
      public RealNum(Token t)
      {
         selection = t.getValue();
      }
    
      public String toString()
      {
         return "Type = " + number;
      }
    
      public void allowVisit(FlairVisitor visitor)
      {
         visitor.visit(this);
      }
    
   }


//PARAMETERS
   class Parameters extends FlairAbstractSyntaxTree
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

   class Parameter extends Parameters
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


//ARGUMENTS
   class Arguments extends FlairAbstractSyntaxTree
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

   class Argument extends Arguments
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