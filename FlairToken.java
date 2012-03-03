/*
  Compiler Project: Scanner Assignment 02/09/12
  Authors: Andrew, Jordan, and Zane
  FlairToken class
*/

public class FlairToken
{
    public static final String Program = "PROGRAM";
    public static final String Var = "VAR";
    public static final String Function = "FUNCTION";
    public static final String Integer = "INTEGER";
    public static final String Real = "REAL";
	 public static final String IntegerNumber = "INTEGERNUMBER";
    public static final String RealNumber = "REALNUMBER"; 
    public static final String Begin = "BEGIN";
    public static final String End = "END";
    public static final String If = "IF";
    public static final String Then = "THEN";
    public static final String Else = "ELSE";
	 public static final String While = "WHILE";
	 public static final String Do = "DO";
	 public static final String Print = "PRINT";
	 public static final String Return = "RETURN";
	 public static final String Identifier = "IDENTIFIER";
	 public static final String Assignment = ":=";
	 public static final String Equals = "=";
	 public static final String NotEquals = "!=";
	 public static final String LessThan = "<";
	 public static final String LessThanEqual = "<=";
	 public static final String GreaterThan = ">";
	 public static final String GreaterThanEqual = ">=";
	 public static final String Plus = "+";
	 public static final String Minus = "-";
	 public static final String Multiply = "*";
	 public static final String Divide = "/";
	 public static final String LeftBrace = "{";
	 public static final String RightBrace = "}";
	 public static final String SemiColon = ";";
	 public static final String Period = ".";
	 public static final String Comma = ",";
	 public static final String Colon = ":";
	 public static final String LeftParentheses = "(";
	 public static final String RightParentheses = ")";

    private String type;
    private Object value;

    public FlairToken(String theType){
       this(theType, null);}
    
    public FlairToken(String theType, String theValue){
        type = theType;
        value = theValue;}

    public String getType(){
	     return type;}
	 
    public Object getValue(){
	     return value;}
}