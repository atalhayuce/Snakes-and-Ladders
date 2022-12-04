//x, y coordinates, fillboardcells
public class BoardCell {

	// fields
	private int cid;
	private int x;
	private int y;

	// constructor
	public BoardCell(int f_cid, int f_x, int f_y) {

		cid = f_cid;
		x = f_x;
		y = f_y;
	}

	// Methods

	public int getcellid() {
		return cid;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setboardcell(int f_cid, int f_x, int f_y) {
		cid = f_cid;
		x = f_x;
		y = f_y;
	}

}
