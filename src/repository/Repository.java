package repository;
import model.*;
import java.util.*;
import java.io.*;
import utils.*;

public class Repository implements IRepository {
	private List<PrgState> repo;
	private String filename;

	public Repository(String filename) {
		this.repo = new ArrayList<>();
		this.filename = filename;
	}

	public Repository(String filename, PrgState ps) {
		this.repo = new ArrayList<>();
		this.filename = filename;
        this.repo.add(ps);
	}

	@Override
	public void addPrgState(PrgState ps) {
		this.repo.add(ps);
	}

	@Override
	public List<PrgState> getPrgStates() {
		return this.repo;
	}

	@Override
	public void setPrgStates(List<PrgState> those) {
		this.repo = those;
	}

	public void logPrgState(PrgState ps) throws InterpreterException {
		try( PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.filename, true))) ) {
			logFile.println("\n-------------");
            logFile.println("\nID: " + ps.getId());
			logFile.println("\nExeStack: ");
			for (Statement stmt: ps.getExeStack().getAll()) {
				logFile.println("\t" + stmt);
			}
			logFile.println("\nSymbolTable: ");
			for (Map.Entry<String,Integer> entry: ps.getSymbolTable().getAll()) {
				logFile.println("\t" + entry.getKey() + "-->" + entry.getValue());
			}
			logFile.println("\nHeap: ");
			for (Map.Entry<Integer,Integer> entry: ps.getHeap().getAll()) {
				logFile.println("\t" + entry.getKey() + "-->" + entry.getValue());
			}
            logFile.println("\nOutput: ");
            for(Integer i: ps.getOutput().getAll()) {
                logFile.println("\t" + i.toString());
            }
			logFile.println("\nFileTable: ");
			for(Map.Entry<Integer, FileData> entry: ps.getFileTable().getAll()) {
				logFile.println("\t" + entry.getKey()+"-->"+entry.getValue());
			}
		} catch (IOException e) {
            throw new InterpreterException(e.getMessage());
        }
	}

	@Override
	public void serialize(PrgState ps, String filename) throws InterpreterException {
		try (ObjectOutputStream theStream =
					 new ObjectOutputStream(new FileOutputStream(filename))) {
			theStream.writeObject(ps);
		} catch (Exception e) {
			throw(new InterpreterException(e.getMessage()));
		}
	}

	@Override
	public PrgState deserialize(String filename) throws InterpreterException {
		try (ObjectInputStream theStream =
					 new ObjectInputStream(new FileInputStream(filename))) {
			Object obj = theStream.readObject();
			if (obj instanceof PrgState) {
				return (PrgState)obj;
			}
			throw(new InterpreterException("..."));
		} catch (Exception e) {
			throw(new InterpreterException(e.getMessage()));
		}
	}

	@Override
	public void clearFile() throws InterpreterException {
		try {
			FileWriter fileOut = new FileWriter(filename);
			fileOut.write("");
			fileOut.close();
		} catch(Exception e){
			throw new InterpreterException(e.getMessage());
		}
	}
}
