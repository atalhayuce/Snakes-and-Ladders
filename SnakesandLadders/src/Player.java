import java.awt.Color;
public class Player {

	private int p_id;
	private String name;
	private Color colour;
	private int position = 0;
	private int stickCnt = 0;
	private int biscuitCnt = 0;

	public Player(int f_pid, String f_name, Color f_colour) {

		name = f_name;
		p_id = f_pid;
		colour = f_colour;

	}

	// Methods
	public String getPlayerName() {
		return name;
	}

	public int getPlayerID() {
		return p_id;
	}

	public Color getPlayerColour() {
		return colour;
	}

	public int getPlayerPosition() {
		return position;
	}

	public int getBiscuitCnt() {
		return biscuitCnt;
	}

	public int getStickCnt() {
		return stickCnt;
	}

	public void setPlayerPosition(int newPosition) {
		position = newPosition;
	}

	public void setBiscuitCnt(int newBiscuitCnt) {
		biscuitCnt = newBiscuitCnt;
	}

	public void setStickCnt(int newStickCnt) {
		stickCnt = newStickCnt;
	}

}
