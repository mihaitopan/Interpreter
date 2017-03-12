package utils;

public class Generator {
	private static int fileTableId = 0, heapId = 0, prgStateId = 0;
	public static int generateFileTableId() {
		return ++fileTableId;
	}
	public static int generateHeapId() {
		return ++heapId;
	}
	public static int generatePrgStateId() {
		return ++prgStateId;
	}
}
