package model;
import utils.*;

public class WriteHeapStmt implements Statement, java.io.Serializable {
    private Expression expr;
    private String var;

    public WriteHeapStmt(Expression expr, String var) {
        this.expr = expr;
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState p) throws InterpreterException {
        IHeap heap = p.getHeap();
        ISymbolTable t = p.getSymbolTable();
        int resExpr = this.expr.evaluate(t, heap);
        if (t.contains(var)) {
            Object index = t.getValue(var);
            heap.setValue((Integer)index, resExpr);
        } else {
            throw new InterpreterException("no such variable " + this.var + " in heap");
        }

        System.out.println(this);
        return null;
    }

    @Override
    public String toString() {
        return " " + this.var + " = " + this.expr + " ";
    }
}
