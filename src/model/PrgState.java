package model;
import utils.*;

public class PrgState implements java.io.Serializable {
    private int id;
    private Statement initialProg;
    private IExeStack<Statement> exeStack;
    private ISymbolTable<String, Integer> symTable;
	IHeap<Integer> heap;
	private IFileTable<Integer, FileData> fileTable;
    private IOutput<Integer> output;

	public PrgState(Statement ip, 
					IExeStack<Statement> es,
					ISymbolTable<String, Integer> st,
					IHeap<Integer> heap,
                    IFileTable<Integer, FileData> ft,
                    IOutput<Integer> o) {
        this.id = Generator.generatePrgStateId();
		this.initialProg = ip;
		this.exeStack = es;
		this.exeStack.push(ip);
		this.symTable = st;
		this.heap = heap;
        this.fileTable = ft;
		this.output = o;
	}

	public boolean isNotCompleted() {
		return !this.exeStack.isEmpty();
	}

	public PrgState oneStep() throws InterpreterException{
		if (this.exeStack.isEmpty()) {
			throw new InterpreterException("exestack is empty");
		}
		Statement stmt = this.exeStack.pop();
		return stmt.execute(this);
	}

	public int getId() {
		return this.id;
	}

    public Statement getStatement() {
		return this.initialProg;
    }
    public void setStatement(Statement ip) {
		this.initialProg = ip;
    }
	
	public IExeStack<Statement> getExeStack() {
		return this.exeStack;
    }
    public void setExeStack(IExeStack<Statement> es) {
		this.exeStack = es;
    }
	
	public ISymbolTable<String, Integer> getSymbolTable() {
		return this.symTable;
    }
    public void setSymbolTable(ISymbolTable<String, Integer> st) {
		this.symTable = st;
    }

	public IHeap<Integer> getHeap() {
		return this.heap;
	}
	public void setHeap(IHeap<Integer> heap) {
		this.heap = heap;
	}

	public IFileTable<Integer, FileData> getFileTable() {
		return this.fileTable;
	}
	public void setFileTable(IFileTable<Integer, FileData> ft) {
		this.fileTable = ft;
	}
	
	public IOutput<Integer> getOutput() {
		return this.output;
    }
    public void setOutput(IOutput<Integer> o) {
		this.output = o;
    }

    @Override
	public String toString() {
		StringBuilder s = new StringBuilder();
        s.append(this.id);
		s.append(this.exeStack);
		s.append(this.symTable);
		s.append(this.heap);
		s.append(this.fileTable);
		s.append(this.output);
		return s.toString();
	}
}
