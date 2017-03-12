package utils;

public interface IExeStack<E> {
    void push(E elem);
    E top();
	E pop();
    boolean isEmpty();
	Iterable<E> getAll();
}
