/*
  Compiler Project: Scanner Assignment 02/23/12
  Authors: Andrew, Jordan, and Zane
  FlairToken class
*/

public class FlairToken
{
    public static final String Program = "program";
    public static final String Var = "var";
    public static final String Function = "function";
    public static final String Integer = "integer";
    public static final String Real = "real";
	 public static final String IntegerNumber = "integerNum";
    public static final String RealNumber = "realNum"; 
    public static final String Begin = "begin";
    public static final String End = "end";
    public static final String If = "if";
    public static final String Then = "then";
    public static final String Else = "else";
	 public static final String While = "while";
	 public static final String Do = "do";
	 public static final String Print = "print";
	 public static final String Return = "return";
	 public static final String Identifier = "identifier";
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
	 public static final String EOS = "$";

    private String type;
    private String value;

    public FlairToken(String theType){
       this(theType, null);}
    
    public FlairToken(String theType, String theValue){
        type = theType;
        value = theValue;}

    public String getType(){
	     return type;}
	 
    public String getValue(){
	     return value;}
}