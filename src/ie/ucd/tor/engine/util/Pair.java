package ie.ucd.tor.engine.util;

/**
 * Reference: https://stackoverflow.com/questions/156275/what-is-the-equivalent-of-the-c-pairl-r-in-java
 */
public class Pair<T1, T2> {

	private T1 A;
	private T2 B;

	public Pair(T1 a, T2 b) {
		A = a;
		B = b;
	}

	public boolean equals(Object other) {
		if (other instanceof Pair otherPair) {
			return ((this.A == otherPair.getA() || (this.A != null && otherPair.getA() != null && this.A.equals(otherPair.getA()))) && (this.B == otherPair.getB() || (this.B != null && otherPair.getB() != null && this.B.equals(otherPair.getB()))));
		}

		return false;
	}

	public T1 getA() {
		return A;
	}

	public T2 getB() {
		return B;
	}

	public void setA(T1 a) {
		A = a;
	}

	public void setB(T2 b) {
		B = b;
	}

}
