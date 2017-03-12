package model;
import java.io.BufferedReader;

public class FileData {
	private String filename;
	private BufferedReader br;
	public FileData(String filename, BufferedReader br) {
		this.filename = filename;
		this.br = br;
	}

	public String getFileName() {
		return this.filename;
	}
	public void setFileName(String filename) {
		this.filename = filename;
	}

	public BufferedReader getReader() {
		return this.br;
	}
	public void setReader(BufferedReader br) {
		this.br = br;
	}

	public String toString() {
		return this.filename;
	}
}
