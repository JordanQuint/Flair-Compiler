/*
  Compiler Project: Parser Part 1 Assignment 03/08/12
  Authors: Andrew, Jordan, and Zane
  FlairAbstractSyntaxTree class
*/

import java.util.*;
import java.io.*;

public class FlairAbstractSyntaxTree{
    public String toString(){
	     return "";
	 }    
}

class Program extends FlairAbstractSyntaxTree{
    private Identifier name;
	 private Parameters parameter;
	 private Declaration decs;
	 private Statement stats;
	 
	 public Program(Identifier n, Parameters p, Declaration d, Statement s){
	     name = n;
		  parameter = p;
		  decs = d;
		  stats = s;
	 }
	 
    public void allowVisit(FlairVisitor visitor){
	     visitor.visit(this);
	 }
	 
    public String toString(){
	     return "program\n" + name + parameters + decs + stats;
	 }
}


//DECLARATIONS
class Declaration extends FlairAbstractSyntaxTree{
    public void allowVisit(FlairVisitor visitor){
	     visitor.visit(this);
	 }    
    public String toString(){
	     return "";
	 }
}

class VarDec extends Declaration{
    public void allowVisit(FlairVisitor visitor){
	     visitor.visit(this);
	 }
     public String toString(){
	     return ident + type + "\n";
	 }
}

class FuncDec extends Declaration{
    public void allowVisit(FlairVisitor visitor){
	     visitor.visit(this);
	 }
	 
	 public String toString(){
	     return "";
	 }
}


//STATEMENTS
class Statement extends FlairAbstractSyntaxTree{
    public void allowVisit(FlairVisitor visitor){
	     visitor.visit(this);
	 }
	 
	 public String toString(){
	     return "";
	 }
}

class PrintStatement extends Statement{
    public void allowVisit(FlairVisitor visitor){
	     visitor.visit(this);
	 }
	 
	 public String toString(){
	     return "";
	 }    
}

class AssignStatement extends Statement{
    public void allowVisit(FlairVisitor visitor){
	     visitor.visit(this);
	 }
	 
	 public String toString(){
	     return "";
	 }    
}

class IfStatement extends Statement{
    public void allowVisit(FlairVisitor visitor){
	     visitor.visit(this);
	 }
	 
	 public String toString(){
	     return "";
	 }    
}

class WhileStatement extends Statement{
    public void allowVisit(FlairVisitor visitor){
	     visitor.visit(this);
	 }
	 
	 public String toString(){
	     return "";
	 } 
}

class ReturnStatement extends Statement{
    public void allowVisit(FlairVisitor visitor){
	     visitor.visit(this);
	 }
	 
	 public String toString(){
	     return "";
	 } 
}


//EXPRESSIONS
class Expression extends FlairAbstractSyntaxTree{
    public void allowVisit(FlairVisitor visitor){
	     visitor.visit(this);
	 }
	 
	 public String toString(){
	     return "";
	 }
}

class NegateExp extends Expression{
    public void allowVisit(FlairVisitor visitor){
	     visitor.visit(this);
	 }
	 
	 public String toString(){
	     return "";
	 }
}

class AdditionExp extends Expression{
    public void allowVisit(FlairVisitor visitor){
	     visitor.visit(this);
	 }
	 
	 public String toString(){
	     return "";
	 }
}

class SubtractExp extends Expression{
    public void allowVisit(FlairVisitor visitor){
	     visitor.visit(this);
	 }
	 
	 public String toString(){
	     return "";
	 }
}

class MultiplicationExp extends Expression{
    public void allowVisit(FlairVisitor visitor){
	     visitor.visit(this);
	 }
	 
	 public String toString(){
	     return "";
	 }
}

class DivisionExp extends Expression{
    public void allowVisit(FlairVisitor visitor){
	     visitor.visit(this);
	 }
	 
	 public String toString(){
	     return "";
	 }
}

class FunctionCall extends Expression{
    public void allowVisit(FlairVisitor visitor){
	     visitor.visit(this);
	 }
	 
	 public String toString(){
	     return "";
	 }
}

class IntegerNum extends Expression{
    private String number;
	 
    public IntegerNum(Token n){
	     number = n.getValue();
	 }
	 
    public String toString(){
	     return "Integer = " + number;
	 }
	 
    public void allowVisit(FlairVisitor visitor){
	     visitor.visit(this);
	 }    
}

class RealNum extends Expression{
    private String number;
	 
    public RealNum(Token n){
	     number = n.getValue();
	 }
	 
    public String toString(){
	     return "Real = " + number;
	 }
	 
    public void allowVisit(FlairVisitor visitor){
	     visitor.visit(this);
	 }    
}

class Identifier extends Expression{
    private String word;
	 
    public RealNum(Token w){
	     word = w.getValue();
	 }
	 
    public String toString(){
	     return "Identifier = " + number;
	 }
	 
    public void allowVisit(FlairVisitor visitor){
	     visitor.visit(this);
	 }    
}


//TYPE
class Type extends FlairAbstractSyntaxTree{
    private String selection;
	 
    public RealNum(Token t){
	     selection = t.getValue();
	 }
	 
    public String toString(){
	     return "Type = " + number;
	 }
	 
    public void allowVisit(FlairVisitor visitor){
	     visitor.visit(this);
	 }
	 
}


//PARAMETERS
class Parameters extends FlairAbstractSyntaxTree{
    public void allowVisit(FlairVisitor visitor){
	     visitor.visit(this);
	 }
	 
	 public String toString(){
	     return "";
	 }
}

class Parameter extends Parameters{
    public void allowVisit(FlairVisitor visitor){
	     visitor.visit(this);
	 }
	 
	 public String toString(){
	     return "";
	 }
}


//ARGUMENTS
class Arguments extends FlairAbstractSyntaxTree{
    public void allowVisit(FlairVisitor visitor){
	     visitor.visit(this);
	 }
	 
	 public String toString(){
	     return "";
	 } 
}

class Argument extends Arguments{
    public void allowVisit(FlairVisitor visitor){
	     visitor.visit(this);
	 }
	 
	 public String toString(){
	     return "";
	 }
}