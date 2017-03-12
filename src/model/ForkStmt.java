package model;
import utils.*;

import java.util.Map;

public class ForkStmt implements Statement {
    private Statement stmt;

    public ForkStmt(Statement stmt) { this.stmt = stmt; }

    @Override
    public PrgState execute(PrgState p) throws InterpreterException {
		ISymbolTable st = p.getSymbolTable();
		ISymbolTable newSt = st.clone();
		PrgState newPs = new PrgState(stmt, new ExeStack<>(), newSt, p.getHeap(),
										p.getFileTable(), p.getOutput());
		return newPs;
    }

    @Override
    public String toString() {
        return " fork[" + this.stmt + "] ";
    }
}
