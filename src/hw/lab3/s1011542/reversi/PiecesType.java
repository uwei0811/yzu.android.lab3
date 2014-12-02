package hw.lab3.s1011542.reversi;

public enum PiecesType {
	
	/* �Ѥl����
	 *  EMPTY = �ŵ� (�e�e�e)
	 *  WHITE = �մ�
	 *  BLACK = �´�
	 *  POSSIBLE = �i�H�U����m
	 *  FORBIDDON = ����U����l �]�Ǧ�^
	 */
	EMPTY(1),
	WHITE(2),
	BLACK(3),
	POSSIBLE(4), 
	FORBIDDEN(5);
	
	int value;
	
	private PiecesType(int value) {
		this.value = value;
	}
	
	public int value() {
        return value;
    }
	
	
}
