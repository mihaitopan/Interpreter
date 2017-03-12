package model;
import utils.*;

public class WhileStmt implements Statement, java.io.Serializable {
    private Expression expr;
    private Statement stmt;

    public WhileStmt(Expression expr, Statement stmt) {
        this.expr = expr;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState p) throws InterpreterException {
        ISymbolTable t = p.getSymbolTable();
        IHeap heap = p.getHeap();
        int resExpr = this.expr.evaluate(t, heap);

        if (resExpr != 0) {
            IExeStack exe = p.getExeStack();
            exe.push(this);
            exe.push(this.stmt);
        }

        return null;
    }

    @Override
    public String toString() {
        return " " + this.expr + " ";
    }
}
