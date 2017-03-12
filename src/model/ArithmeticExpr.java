package model;
import utils.*;

public class ArithmeticExpr implements Expression, java.io.Serializable {
	private char operator;
	private Expression first, second;
	
	public ArithmeticExpr(char operator, Expression first, Expression second) {
		this.operator = operator;
		this.first = first;
		this.second = second;
	}
	
	@Override
	public int evaluate(ISymbolTable s, IHeap heap) throws InterpreterException {
		int resFirst = this.first.evaluate(s, heap);
		int resSecond = this.second.evaluate(s, heap);
		
		switch(operator) {
			case '+':
				return resFirst + resSecond;
			case '-':
				return resFirst - resSecond;
			case '*':
				return resFirst * resSecond;
			case '/':
				if (0 == resSecond) {
					throw new InterpreterException("you try divide by 0!");
				}
				return resFirst / resSecond;
			default:
				throw new InterpreterException("invalid operator");
		}
	}
	
	@Override
	public String toString() {
		return "" + this.first + " " + this.operator + " " + this.second;
	}
}
