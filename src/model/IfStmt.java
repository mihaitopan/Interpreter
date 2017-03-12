package model;
import utils.*;

public class IfStmt implements Statement, java.io.Serializable {
    private Expression expr;
    private Statement first;
    private Statement second;

    public IfStmt(Expression expr, Statement first, Statement second) {
        this.expr = expr;
        this.first = first;
        this.second = second;
    }

    @Override
    public PrgState execute(PrgState p) throws InterpreterException {
        IExeStack exe = p.getExeStack();
        ISymbolTable t = p.getSymbolTable();
        IHeap heap = p.getHeap();
        int resExpr = this.expr.evaluate(t, heap);
        if (resExpr != 0) {
            exe.push(this.first);
        } else {
            exe.push(this.second);
        }

        System.out.println(this);
        return null;
    }

    @Override
    public String toString() {
        return " If " + this.expr + " Then " + this.first + " Else " + this.second + " ";
    }
}
