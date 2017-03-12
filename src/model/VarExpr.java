package model;
import utils.*;

public class VarExpr implements Expression, java.io.Serializable {
	private String name;
	
	public VarExpr(String name) {
		this.name = name;
	}

    @Override
    public int evaluate(ISymbolTable<String, Integer> s, IHeap<Integer> heap) throws InterpreterException {
        if (s.contains(this.name)) {
            return s.getValue(this.name);
        } else {
            throw new InterpreterException("no such variable " + this.name);
        }
    }
	
	@Override
	public String toString() {
		return this.name;
	}
}
