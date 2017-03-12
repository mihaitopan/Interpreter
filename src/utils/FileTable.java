package utils;
import java.util.*;

public class FileTable<E,F> implements IFileTable<E,F>, java.io.Serializable {
    private Map<E,F> map;

    public FileTable() {
        this.map = new HashMap<>();
    }

    @Override
    public void add(E key, F value) {
        this.map.putIfAbsent(key, value);
    }

    @Override
    public boolean contains(E key) {
        return this.map.containsKey(key);
    }

    @Override
    public F getValue(E key) throws InterpreterException {
        F value = this.map.get(key);
        if (value == null) {
            throw new InterpreterException("no such key");
        }
        return value;
    }

    @Override
    public void setValue(E key, F value) {
        this.map.put(key, value);
    }

    @Override
    public void remove(E key) {
        this.map.remove(key);
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public Iterable<Map.Entry<E,F>> getAll() {
        return this.map.entrySet();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        if (this.size() == 0) {
            s.append("\t[]\n");
        }

        for (E key: this.map.keySet()) {
            s.append("\t" + key + ": ");
            s.append(this.map.get(key) + "\n");
        }

        return "File Table\n" + s.toString();
    }
}
