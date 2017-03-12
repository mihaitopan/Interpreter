package model;
import utils.*;
import java.io.*;

public class CloseFileStmt implements Statement, java.io.Serializable {
	private String fileId;
	
	public CloseFileStmt(String fileId) {
		this.fileId = fileId;
	}
	
	public PrgState execute(PrgState p) throws InterpreterException {
		Integer e = p.getSymbolTable().getValue(this.fileId);
		if (e == null) {
			throw new InterpreterException("no such variable " + this.fileId);
		}
		FileData d = p.getFileTable().getValue(e);
		if (d == null) {
			throw new InterpreterException("no such variable " + this.fileId);
		}
		BufferedReader br = d.getReader();
		try {
			br.close();
		} catch(IOException|NumberFormatException ex) {
			throw new InterpreterException("...");
		}

        p.getFileTable().remove(e);
		return null;
	}

	public String toString(){
		return " " + this.fileId + " CLOSED";
	}
}
