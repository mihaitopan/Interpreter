package utils;
import java.util.Map;

public interface IHeap<V> {
    Integer add(V value);
    boolean contains(Integer key);
    Integer getNewFree();
    V getValue(Integer key) throws InterpreterException;
    void setValue(Integer key, V value);
    void setAll(Map<Integer, V> map);
    int size();
    Iterable<Map.Entry<Integer,V>> getAll();
    Map<Integer,V> getMap();
}
