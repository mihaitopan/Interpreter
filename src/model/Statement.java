package model;
import utils.*;

public interface Statement {
	PrgState execute(PrgState p) throws InterpreterException;
}
