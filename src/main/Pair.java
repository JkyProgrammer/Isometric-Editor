package main;

public class Pair<T1, T2> {
	public T1 a;
	public T2 b;
	
	public Pair (T1 aa, T2 bb) {
		a = aa;
		b = bb;
	}
	
	public String toString () {
		return "Pair(" + a.toString() + ", " + b.toString() + ")";
	}
}
