package model;
import utils.*;

public class CompStmt implements Statement, java.io.Serializable {
	private Statement first;
	private Statement second;
	
	public CompStmt(Statement first, Statement second) {
		this.first = first;
		this.second = second;
	}
	
	@Override
	public PrgState execute(PrgState p) {
		IExeStack exe = p.getExeStack();
		exe.push(this.second);
		exe.push(this.first);
		return null;
	}
	
	@Override
	public String toString() {
		return "{" + this.first + ";" + this.second + "}";
	}
}
