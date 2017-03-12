package model;
import utils.*;

public class PrintStmt implements Statement, java.io.Serializable {
    private Expression expr;

    public PrintStmt(Expression expr) { this.expr = expr; }

    @Override
    public PrgState execute(PrgState p) throws InterpreterException {
        IOutput o = p.getOutput();
        ISymbolTable t = p.getSymbolTable();
        IHeap heap = p.getHeap();
        int resExpr = this.expr.evaluate(t, heap);
        o.add(resExpr);

        System.out.println(this);
        return null;
    }

    @Override
    public String toString() {
        return " " + this.expr + " ";
    }
}
