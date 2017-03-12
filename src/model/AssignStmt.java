package model;
import utils.*;

public class AssignStmt implements Statement, java.io.Serializable {
	private Expression expr;
	private String var;
	
	public AssignStmt(Expression expr, String var) {
		this.expr = expr;
		this.var = var;
	}

	@Override
	public PrgState execute(PrgState p) throws InterpreterException {
		ISymbolTable t = p.getSymbolTable();
        IHeap heap = p.getHeap();
		int resExpr = this.expr.evaluate(t, heap);
		if (t.contains(var)) {
			t.setValue(var, resExpr);
		} else {
			t.add(var, resExpr);
		}

		System.out.println(this);
		return null;
	}
	
	@Override
	public String toString() {
		return " " + this.var + " = " + this.expr + " ";
	}
}
