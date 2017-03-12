package model;
import utils.*;

public class NewStmt implements Statement, java.io.Serializable {
    private Expression expr;
    private String var;

    public NewStmt(Expression expr, String var) {
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
            Integer index = heap.getNewFree();
            heap.add(resExpr);
            t.add(var, index);
        }

        System.out.println(this);
        return null;
    }

    @Override
    public String toString() {
        return " " + this.var + " = " + this.expr + " ";
    }
}
