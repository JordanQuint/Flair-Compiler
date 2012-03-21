/*
  Compiler Project: Scanner Assignment 03/09/12
  Authors: Andrew, Jordan, and Zane
  FlairVisitor interface
*/

public interface FlairVisitor
{
    public void visit(Program flairProgram);
	 public void visit(Declaration flairDeclaration);
	 public void visit(VarDec flairVarDeclaration);
	 public void visit(FuncDec flairFuncDeclaration);
	 public void visit(Statement flairStatement);
	 public void visit(PrintStatement flairPrintStatement);
	 public void visit(AssignStatement flairAssignStatement);
	 public void visit(IfStatement flairIfStatement);
	 public void visit(WhileStatement flairWhileStatement);
	 public void visit(ReturnStatement flairReturnStatement);
	 public void visit(Expression flairExpression);
	 public void visit(NegateExp flairNegateExp);
	 public void visit(AdditionExp flairAdditionExp);
	 public void visit(SubtractExp flairSubtractExp);
	 public void visit(MultiplicationExp flairMultExp);
	 public void visit(DivisionExp flairDivisionExp);
	 public void visit(FunctionCall flairFunctionCall);
	 public void visit(FunctionHeading flairFunctionHeading);
	 public void visit(FunctionBody flairFunctionBody);
	 public void visit(IntegerNum flairIntegerNum);
	 public void visit(RealNum flairRealNum);
	 public void visit(Identifier flairIdentifier);
	 public void visit(Type flairType);
	 public void visit(Parameters flairParameters);
	 public void visit(Parameter flairParameter);
	 public void visit(Arguments flairArguments);
	 public void visit(Argument flairArgument);
}