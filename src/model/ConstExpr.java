package model;
import utils.*;

public class ConstExpr implements Expression, java.io.Serializable {
	private int value;
	
	public ConstExpr(int value) {
		this.value = value;
	}
	
	@Override
	public int evaluate(ISymbolTable s, IHeap heap) {
		return this.value;
	}
	
	@Override
	public String toString() {
		return "" + this.value;
	}
}
