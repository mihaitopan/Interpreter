package model;
import utils.*;

public class SleepStmt implements Statement, java.io.Serializable {
    private int number;

    public SleepStmt(int number) { this.number = number; }

    @Override
    public PrgState execute(PrgState p) throws InterpreterException {
        if (this.number != 0) {
            this.number -= 1;
            IExeStack stack = p.getExeStack();
            stack.push(this);
            p.setExeStack(stack);
        }

        System.out.println(this);
        return null;
    }

    @Override
    public String toString() {
        return " " + this.number + " ";
    }
}
