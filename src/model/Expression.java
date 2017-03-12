package model;
import utils.*;

public interface Expression {
	int evaluate(ISymbolTable<String, Integer> st, IHeap<Integer> heap) throws InterpreterException;
}
