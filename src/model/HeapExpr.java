package model;
import utils.*;

public class HeapExpr implements Expression, java.io.Serializable {
    private String name;

    public HeapExpr(String name) {
        this.name = name;
    }

    @Override
    public int evaluate(ISymbolTable<String, Integer> s, IHeap<Integer> heap) throws InterpreterException {
        if (s.contains(this.name)) {
            Integer index = s.getValue(this.name);
            if (heap.contains(index)) {
                return heap.getValue(index);
            } else {
                throw new InterpreterException("no such variable " + this.name);
            }
        } else {
            throw new InterpreterException("no such variable " + this.name);
        }
    }

    @Override
    public String toString() {
        return this.name;
    }
}
