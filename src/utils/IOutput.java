package utils;

public interface IOutput<E> {
    void add(E elem);
	int size();
    Iterable<E> getAll();
}
