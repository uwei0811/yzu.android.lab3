package hw.lab3.s1011542.reversi;

public class PiecesPos {

	public Integer i = -1;
	public Integer j = -1;
	
	
	public PiecesPos( Integer i, Integer j) {
		this.i = i;
		this.j = j;
	}
	
	public PiecesPos() {
		this.i = -1;
		this.j = -1;
	}

	@Override
	public boolean equals(Object v) {
		boolean retVal = false;

		if (v instanceof PiecesPos) {
			PiecesPos ptr = (PiecesPos) v;
			retVal = ptr.i == this.i && ptr.j == this.j;
		}

		return retVal;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 13237 * hash + ( this.i != null? this.i.hashCode() : 0) + ( this.j != null? this.j.hashCode() : 0);
		return hash;
	}

}
