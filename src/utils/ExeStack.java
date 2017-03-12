package utils;
import java.util.*;

public class ExeStack<E> implements IExeStack<E>, java.io.Serializable {
    private Deque<E> deque;
	private Stack<E> stack;
	
	public ExeStack() {
		this.deque = new ArrayDeque<E>();
		this.stack = new Stack<E>();
	}
	
	@Override
    public void push(E elem) {
		this.stack.push(elem);
    }

	@Override
    public E top() {
		return this.stack.peek();
    }
	
    @Override
    public E pop() {
		return this.stack.pop();
    }

    @Override
    public boolean isEmpty() {
		return this.stack.empty();
    }
	
	@Override
    public Iterable<E> getAll() {
		return this.stack;
    }
	
	@Override
    public String toString() {
		return "ExeStack\n\t" + stack.toString() + "\n";
    }
}
