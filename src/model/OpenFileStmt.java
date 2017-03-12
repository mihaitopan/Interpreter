package model;
import utils.*;
import java.io.*;
import java.util.*;

public class OpenFileStmt implements Statement, java.io.Serializable {
	private String filename;
	private String varName;
	
	public OpenFileStmt(String filename, String varName) {
		this.filename = filename;
		this.varName = varName;
	}
	
	public PrgState execute(PrgState p) throws InterpreterException {
		if (isOpen(this.filename, p.getFileTable())) {
			throw new InterpreterException("file already open");
		}
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.filename));
			FileData fd = new FileData(this.filename, br);
			int id = FileIdGenerator.generateId();
			p.getFileTable().add(id, fd);
			p.getSymbolTable().add(this.filename, id);
		} catch (IOException e) {
			throw new InterpreterException("no such file");
		}
		return null;
	}
	
	boolean isOpen(String filename, IFileTable<Integer, FileData> ft) {
		for (Map.Entry<Integer, FileData> it: ft.getAll()) {
			if (filename.equals(it.getValue().getFileName())) {
                return true;
            }
		}
        return false;
	}

	public String toString() {
		return " " + this.filename + " OPENED";
	}
}
