package repository;
import model.*;
import utils.InterpreterException;
import java.util.*;

public interface IRepository {
	void addPrgState(PrgState ps);
	List<PrgState> getPrgStates();
	void setPrgStates(List<PrgState> those);
	void logPrgState(PrgState ps) throws InterpreterException;
	void serialize(PrgState ps, String filename) throws InterpreterException;
	PrgState deserialize(String filename) throws InterpreterException;
	void clearFile() throws InterpreterException;
}
