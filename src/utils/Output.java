package utils;
import java.util.*;

public class Output<E> implements IOutput<E>, java.io.Serializable {
    private ArrayList<E> array;
	
	public Output() {
		this.array = new ArrayList<>();
	}
	
	@Override
    public void add(E elem) {
		this.array.add(elem);
    }
	
	@Override
    public int size() {
		return this.array.size();
    }

    @Override
	public Iterable<E> getAll(){
		return array;
	}

	@Override
    public String toString() {
		return "Output\n\t" + this.array.toString() + "\n";
    }
}
