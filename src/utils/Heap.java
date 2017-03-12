package utils;
import java.util.*;

public class Heap<V> implements IHeap<V>, java.io.Serializable {
    private Map<Integer,V> map;
    private Integer newFree;

    public Heap() {
        this.map = new HashMap<>();
        this.newFree = new Integer(1);
    }

    @Override
    public Integer add(V value) {
        this.map.putIfAbsent(this.newFree, value);
        this.newFree++;
        return this.newFree - 1;
    }

    @Override
    public boolean contains(Integer key) {
        return this.map.containsKey(key);
    }

    @Override
    public Integer getNewFree() {
        return this.newFree;
    }

    @Override
    public V getValue(Integer key) throws InterpreterException {
        V value = this.map.get(key);
        if (value == null) {
            throw new InterpreterException("no such key");
        }
        return value;
    }

    @Override
    public void setValue(Integer key, V value) {
        this.map.put(key, value);
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public Iterable<Map.Entry<Integer,V>> getAll() {
        return this.map.entrySet();
    }

    @Override
    public Map<Integer,V> getMap() {
        return this.map;
    }

    @Override
    public void setAll(Map<Integer, V> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        if (this.size() == 0) {
            s.append("\t[]\n");
        }

        for (Integer key: this.map.keySet()) {
            s.append("\t" + key + ": ");
            s.append(this.map.get(key) + "\n");
        }

        return "Heap\n" + s.toString();
    }
}
