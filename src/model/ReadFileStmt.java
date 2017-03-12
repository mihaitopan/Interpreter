package model;
import utils.InterpreterException;
import java.io.*;

public class ReadFileStmt implements Statement, java.io.Serializable {
	private String fileId;
	private String name;
	
	public ReadFileStmt(String fileId, String name) {
		this.fileId = fileId;
		this.name = name;
	}
	
	public PrgState execute(PrgState p)  throws InterpreterException {
		Integer e = p.getSymbolTable().getValue(this.fileId);
		if (e == null) {
			throw new InterpreterException("no such value");
		}
		FileData d = p.getFileTable().getValue(e);
		if (d == null) {
			throw new InterpreterException("no such value");
		}
		BufferedReader br = d.getReader();
		try {
			String s = br.readLine();
			int v;
			if (s == null) {
				v = 0;
			} else {
				try {
					v = Integer.parseInt(s);
				} catch (NumberFormatException ex) {
					v = 0;
				}
			}
			p.getSymbolTable().add(this.name, v);
		} catch(IOException ex) {
			throw new InterpreterException("reading error");
		}

		return null;
	}

	public String toString() {
		return " " + this.fileId + " READ";
	}
}
