package utils;
import java.util.Map;

public interface IFileTable<E,F> {
    void add(E key, F value);
    boolean contains(E key);
    F getValue(E key) throws InterpreterException ;
    void setValue(E key, F value);
    void remove(E key);
    int size();
    Iterable<Map.Entry<E,F>> getAll();
}
