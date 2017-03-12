package model;
import utils.*;

public class BooleanExpr implements Expression, java.io.Serializable {
    private String operator;
    private Expression first, second;

    public BooleanExpr(String operator, Expression first, Expression second) {
        this.operator = operator;
        this.first = first;
        this.second = second;
    }

    @Override
    public int evaluate(ISymbolTable s, IHeap heap) throws InterpreterException {
        int resFirst = this.first.evaluate(s, heap);
        int resSecond = this.second.evaluate(s, heap);

        switch(this.operator) {
            case "<":
                return resFirst < resSecond ? 1:0;
            case "<=":
                return resFirst <= resSecond ? 1:0;
            case "==":
                return resFirst == resSecond ? 1:0;
            case "!=":
                return resFirst != resSecond ? 1:0;
            case ">=":
                return resFirst >= resSecond ? 1:0;
            case ">":
                return resFirst > resSecond ? 1:0;
            default:
                throw new InterpreterException("invalid operator");
        }
    }

    @Override
    public String toString() {
        return "" + this.first + " " + this.operator + " " + this.second;
    }
}
