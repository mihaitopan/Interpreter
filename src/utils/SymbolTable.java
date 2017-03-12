package utils;
import java.util.*;

public class SymbolTable<E,F> implements ISymbolTable<E,F>, java.io.Serializable {
    private Map<E,F> map;
	
	public SymbolTable() {
		this.map = new HashMap<>(); // HashMap - not thread safe
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
    public int size() {
        return this.map.size();
    }
	
	@Override
	public Iterable<Map.Entry<E,F>> getAll() {
		return this.map.entrySet();
	}

	@Override
	public Map<E,F> getMap() {
		return this.map;
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

		return "Symbol Table\n" + s.toString();
    }

	@Override
	public ISymbolTable clone(){
		ISymbolTable newST = new SymbolTable();
		for(Map.Entry entry: getAll()){
			newST.setValue(entry.getKey(),entry.getValue());
		}
		return newST;
	}
}
