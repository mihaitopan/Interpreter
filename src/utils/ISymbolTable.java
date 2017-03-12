package utils;
import java.util.Map;

public interface ISymbolTable<E,F> {
    void add(E key, F value);
    boolean contains(E key);
    F getValue(E key) throws InterpreterException ;
    void setValue(E key, F value);
	int size();
	Iterable<Map.Entry<E,F>> getAll();
    Map<E,F> getMap();
    ISymbolTable clone();
}
