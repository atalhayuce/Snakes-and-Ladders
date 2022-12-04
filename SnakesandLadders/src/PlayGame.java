import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

public class PlayGame extends JFrame {
	private Boolean playsoundMode = true;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txAddPlayer;
	private JButton btnRollDice;
	private JLabel lbDice;
	private JLabel lbBiscuit;
	private JLabel lbStick;
	private JLabel lbPBiscuitCnt;
	private JLabel lbPStickCnt;
	private JLabel lbPName;
	private JLabel lbBoard;
	private Canvas pFigure0;
	private Canvas pFigure1;
	private Canvas pFigure2;
	private JLabel lbAddPlayer;
	private JButton btAddPlayer;
	private JLabel lbRandomBiscuit0;
	private JLabel lbRandomBiscuit1;
	private JLabel lbRandomStick0;
	private JLabel lbRandomStick1;
	private JProgressBar progressBar;

	private String newPlayerName;
	private int playerCnt = 0;
	private int maxPlayerCnt = 3;
	private int playerTurn = 0;

	private int currentPosition = 0;
	private String currentPlayerName;
	private int currentPBiscuitCnt;
	private int currentPStickCnt;
	private Color currentPColour;
	// private String currentDirectory = System.getProperty("user.dir");


	private int diceValue;

	private ArrayList<Color> pcolour = new ArrayList<Color>();
	private ArrayList<Player> myPlayers = new ArrayList<Player>();
	private ArrayList<BoardCell> myBoardCell = new ArrayList<BoardCell>();
	private ArrayList<Integer> biscuits = new ArrayList<Integer>();
	private ArrayList<Integer> sticks = new ArrayList<Integer>();
	private ArrayList<Integer> snakeHeads = new ArrayList<Integer>();
	private ArrayList<Integer> snakeTails = new ArrayList<Integer>();
	private ArrayList<Integer> ladderHeads = new ArrayList<Integer>();
	private ArrayList<Integer> ladderStarts = new ArrayList<Integer>();

	private ImageIcon[] diceImage = {
			createImageIcon("dice1.png"), createImageIcon("dice2.png"), createImageIcon("dice3.png"),
			createImageIcon("dice4.png"), createImageIcon("dice5.png"), createImageIcon("dice6.png") };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayGame frame = new PlayGame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PlayGame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				// Welcome message
				String msg = "Welcome to the Coding Squad's Snakes & Ladders Game." + "\n" + "\n"
						+ "Collect Biscuits or Sticks feeding the next snake not to move down to" + "\n"
						+ "the tail square and creating an extra 10 steps on the next ladder which you encounter all along the way."
						+ "\n" + "\n" + "Type the players' names and click the dice to start!" + "\n" + "\n"
						+ " HAVE FUN! ";

				JOptionPane.showMessageDialog(null, msg, "Info", 1);
				playSound(1);
			}
		});
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setTitle("Snakes and Ladders Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1380, 1042);
		contentPane = new JPanel();
		contentPane.setMaximumSize(new Dimension(1500, 1150));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txAddPlayer = new JTextField();
		txAddPlayer.setBounds(1005, 52, 202, 26);
		contentPane.add(txAddPlayer);
		txAddPlayer.setColumns(10);

//Player colour arraylist is filled here
//Functional Requirement 2.2
		pcolour.add(Color.BLUE);
		pcolour.add(Color.RED);
		pcolour.add(Color.ORANGE);

//Functional Requirement 1.2	

//Snake heads and tails are filled here
//Functional Requirement 1.3
//Functional Requirement 1.4	
		snakeHeads.add(45);
		snakeHeads.add(43);
		snakeHeads.add(65);
		snakeHeads.add(88);
		snakeHeads.add(98);

		snakeTails.add(15);
		snakeTails.add(21);
		snakeTails.add(44);
		snakeTails.add(48);
		snakeTails.add(64);
//Ladder heads and starts are filled here
		ladderHeads.add(32);
		ladderHeads.add(37);
		ladderHeads.add(70);
		ladderHeads.add(75);
		ladderHeads.add(80);
		ladderHeads.add(91);

		ladderStarts.add(8);
		ladderStarts.add(18);
		ladderStarts.add(50);
		ladderStarts.add(54);
		ladderStarts.add(60);
		ladderStarts.add(72);


		lbAddPlayer = new JLabel("Enter player's name (max 3 players)");
		lbAddPlayer.setBounds(1005, 21, 323, 33);
		contentPane.add(lbAddPlayer);

		btAddPlayer = new JButton("Add Player");
		btAddPlayer.setToolTipText("Click to add player to the game");
		btAddPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				newPlayerName = txAddPlayer.getText();
				// if player name is not typed it warns the user otherwise player is created
				if (newPlayerName.equals(null) || newPlayerName.equals("")) {
					JOptionPane.showMessageDialog(null, "Please type player's name.", "Warning", 2);
				} else {
					createPlayer(newPlayerName);
				}
			}
		});
		btAddPlayer.setBounds(1005, 88, 105, 33);
		contentPane.add(btAddPlayer);

		btnRollDice = new JButton("Roll Dice");
		btnRollDice.setToolTipText("Click to roll the dice");
		btnRollDice.setEnabled(false);
		btnRollDice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // roll dice click event

				String msg;
				refreshScreen(); // code starts to run by setting current players information to related
									// variables

				if (playerCnt > 0) { // if there is at least 1 player and the dice is rolled new player cannot be
										// added
					lbAddPlayer.setVisible(false);
					btAddPlayer.setVisible(false);
					txAddPlayer.setVisible(false);
					progressBar.setVisible(true);

				}

				// Functional Requirement 3.2
				diceValue = rollDice();
				lbDice.setIcon(diceImage[diceValue - 1]); // according to dice value dice image is set

				if (currentPosition == 0) { // if player rolls the dice for the first time because the position is 0
					// Functional Requirement 2.2
					msg = currentPlayerName + " rolled " + diceValue + " and moved to "
							+ Integer.toString(currentPosition + diceValue);

					JOptionPane.showMessageDialog(null, msg, "Info", 1);
					// Functional Requirement 3.3
					move(currentPosition + diceValue); // player figure moves to a new position
				}

				else if (currentPosition + diceValue == 100) { // if players new position is 100, player wins
					// Functional Requirement 3.1
					// Functional Requirement 3.3
					playSound(6);
					progressBar.setValue(100);

					msg = "******************** Game Over! ********************" + "\n\n" + "Congratulations "
							+ currentPlayerName + ", you just won the game! Do you wish to play again? ";

					// it is asked to players if they want to play again
					JDialog.setDefaultLookAndFeelDecorated(true);
					int response = JOptionPane.showConfirmDialog(null, msg, "Confirm", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.NO_OPTION) {

						System.exit(0); // if players choose NO option program finishes
					} else if (response == JOptionPane.YES_OPTION) {
						playSound(4);
						restartGame();

					} else if (response == JOptionPane.CLOSED_OPTION) {
						System.exit(0); // if players click the cross sign of the dialog box, program finishes
					}
				}

				else if (currentPosition + diceValue < 100) { // if the player moves on the board this part runs
					// Functional Requirement 2.2
					msg = currentPlayerName + " rolled " + diceValue + " and moved from " + currentPosition + " to "
							+ Integer.toString(currentPosition + diceValue);

					JOptionPane.showMessageDialog(null, msg, "Info", 1);
					// Functional Requirement 3.3
					move(currentPosition + diceValue); // player figure moves to a new position
				}

				else if (currentPosition + diceValue > 100) { // if the player exceeds the cell 100 game gives warning
																// message. player stays current position
					// Functional Requirement 3.3
					playSound(7);
					msg = "You exceed 100, wait for the next turn! ";
					JOptionPane.showMessageDialog(null, msg, "Warning", 2);
				}


				// game checks if the position contains biscuit or not
				if (biscuits.contains(currentPosition + diceValue) == true) {
					playSound(12);
					// Functional Requirement 3.6
					currentPBiscuitCnt = currentPBiscuitCnt + 1;
					myPlayers.get(playerTurn).setBiscuitCnt(currentPBiscuitCnt); // players biscuit count is set
					removeBiscuit(currentPosition + diceValue); // biscuit is removed from the board
					msg = "You got a biscuit! Total biscuit(s): " + currentPBiscuitCnt;
					JOptionPane.showMessageDialog(null, msg, "Info", 1);

				}
				// game checks if the position contains stick or not
				if (sticks.contains(currentPosition + diceValue) == true) {
					playSound(11);
					// Functional Requirement 3.7
					currentPStickCnt = currentPStickCnt + 1;
					myPlayers.get(playerTurn).setStickCnt(currentPStickCnt); // players stick count is set
					removeStick(currentPosition + diceValue); // stick is removed from the board
					msg = "You got a stick! Total stick(s): " + currentPStickCnt;
					JOptionPane.showMessageDialog(null, msg, "Info", 1);

				}

				int newPosition = checkSnakeLadder(currentPosition + diceValue);
				// if the cell contains snake head or ladder start, player goes to new position
				if (newPosition > -1) { // -1 means there is no snake or ladder at this position
					move(newPosition);

				}

				// Functional Requirement 3.5
				if (diceValue == 6) { // if the dice value is 6 player gains another turn otherwise game switches to
										// the next player

					playSound(5);
					msg = "You get another bonus chance! ";
					JOptionPane.showMessageDialog(null, msg, "Info", 1);

				} else {
					nextPlayer(); // switching to next player
				}


			}
		});
		btnRollDice.setBounds(1005, 163, 105, 31);
		contentPane.add(btnRollDice);

		lbDice = new JLabel("");
		lbDice.setIcon(createImageIcon("dice4.png"));
		lbDice.setBounds(1005, 207, 112, 100);
		contentPane.add(lbDice);

		lbBiscuit = new JLabel("");
		lbBiscuit.setIcon(createImageIcon("biscuit40_opac.png"));
		lbBiscuit.setBounds(1004, 425, 68, 68);
		contentPane.add(lbBiscuit);

		lbStick = new JLabel("");
		lbStick.setIcon(createImageIcon("stick40_opac.png"));
		lbStick.setBounds(1004, 501, 48, 89);
		contentPane.add(lbStick);

		lbPBiscuitCnt = new JLabel("");
		lbPBiscuitCnt.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbPBiscuitCnt.setBounds(1062, 444, 98, 26);
		contentPane.add(lbPBiscuitCnt);

		lbPStickCnt = new JLabel("");
		lbPStickCnt.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbPStickCnt.setBounds(1062, 527, 98, 26);
		contentPane.add(lbPStickCnt);

		lbPName = new JLabel("");
		lbPName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbPName.setBounds(1004, 383, 156, 26);
		contentPane.add(lbPName);

		pFigure0 = new Canvas();
		pFigure0.setVisible(false);
		pFigure0.setBackground(Color.BLUE);
		pFigure0.setBounds(1050, 900, 30, 35);
		contentPane.add(pFigure0);

		pFigure1 = new Canvas();
		pFigure1.setVisible(false);
		pFigure1.setBackground(Color.RED);
		pFigure1.setBounds(1100, 900, 30, 35);
		contentPane.add(pFigure1);

		pFigure2 = new Canvas();
		pFigure2.setVisible(false);
		pFigure2.setBackground(Color.ORANGE);
		pFigure2.setBounds(1150, 900, 30, 35);
		contentPane.add(pFigure2);

		lbRandomBiscuit0 = new JLabel("");
		lbRandomBiscuit0.setIcon(createImageIcon("biscuit40.png"));
		lbRandomBiscuit0.setBounds(1010, 799, 45, 40);
		contentPane.add(lbRandomBiscuit0);

		lbRandomBiscuit1 = new JLabel("");
		lbRandomBiscuit1.setIcon(createImageIcon("biscuit40.png"));
		lbRandomBiscuit1.setBounds(1062, 799, 45, 40);
		contentPane.add(lbRandomBiscuit1);

		lbRandomStick0 = new JLabel("");
		lbRandomStick0.setIcon(createImageIcon("stick40.png"));
		lbRandomStick0.setBounds(1013, 849, 45, 40);
		contentPane.add(lbRandomStick0);

		lbRandomStick1 = new JLabel("");
		lbRandomStick1.setIcon(createImageIcon("stick40.png"));
		lbRandomStick1.setBounds(1065, 849, 45, 40);
		contentPane.add(lbRandomStick1);

		lbBoard = new JLabel("");
		lbBoard.setIcon(createImageIcon("board_n_wbs.png"));
		lbBoard.setBounds(0, 0, 1000, 1000);
		contentPane.add(lbBoard);

		JToggleButton btnSoundOnOff = new JToggleButton("Sound Off");
		btnSoundOnOff.setSelected(true);
		btnSoundOnOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnSoundOnOff.isSelected() == true) {
					playsoundMode = true;
					btnSoundOnOff.setText("Sound Off");
				} else {
					playsoundMode = false;
					btnSoundOnOff.setText("Sound On");
				}
			}
		});
		btnSoundOnOff.setBounds(1005, 941, 131, 26);
		contentPane.add(btnSoundOnOff);

		progressBar = new JProgressBar();
		progressBar.setVisible(false);
		progressBar.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		progressBar.setBounds(1005, 354, 146, 21);
		contentPane.add(progressBar);

		JButton btnNewButton = new JButton("Restart");
		btnNewButton.setToolTipText("Click to restart the game");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSound(4);
				restartGame();
				nextPlayer();
			}
		});
		btnNewButton.setBounds(1005, 975, 131, 25);
		contentPane.add(btnNewButton);

		fillBoardCells(); // this method prepares the board for the new game
		placeBiscuitsandSticks(); // this method places biscuits and sticks on the board randomly

		// System.out.println("current directory: " + currentDirectory);
	}


	public void refreshScreen() {
		// this method refreshes current players variables and info on the screen just
		// after player turn changes

		// Functional Requirement 2.2
		currentPlayerName = myPlayers.get(playerTurn).getPlayerName();
		currentPBiscuitCnt = myPlayers.get(playerTurn).getBiscuitCnt();
		currentPStickCnt = myPlayers.get(playerTurn).getStickCnt();
		currentPColour = myPlayers.get(playerTurn).getPlayerColour();
		currentPosition = myPlayers.get(playerTurn).getPlayerPosition();

		lbPName.setText("Player Name: " + currentPlayerName);
		lbPBiscuitCnt.setText("Biscuit(s): " + Integer.toString(currentPBiscuitCnt));
		lbPStickCnt.setText("Stick(s): " + Integer.toString(currentPStickCnt));
		progressBar.setValue(currentPosition);

		// labels colour are set here according to the player's colour
		lbPName.setForeground(currentPColour);
		lbPBiscuitCnt.setForeground(currentPColour);
		lbPStickCnt.setForeground(currentPColour);
		progressBar.setForeground(currentPColour);

	}

	public void nextPlayer() {
		// this method switches the player and sets the player turn variable
		if (playerTurn + 1 < playerCnt) {
			playerTurn = playerTurn + 1;
		} else {
			playerTurn = 0;
		}
		refreshScreen();
	}

	public int rollDice() {
		// this method generates a random dice value between 1-6
		int randomMax = 6;
		int randomMin = 1;
		playSound(8);
		Random rand = new Random();
		int a = rand.nextInt(randomMax - randomMin + 1) + (randomMin);
		return a;

	}


	public void fillBoardCells() {

		// board cells are created here from 1 towards to 100
		// Functional Requirement 1.1

		int cellid, cellWidth = lbBoard.getWidth() / 10, cellHeight = lbBoard.getHeight() / 10, coorY, coorX;

		coorX = 0;
		coorY = cellHeight * 10 - 50;
		cellid = 1;
		myBoardCell.add(new BoardCell(0, 1050, 900));

		while (cellid <= 100) {

			for (int j = 1; j <= 10; j++) {
				BoardCell cell = new BoardCell(cellid, coorX, coorY);
				myBoardCell.add(cell);

				cellid = cellid + 1;
				coorX = coorX + cellWidth;
			}

			coorX = coorX - cellWidth;
			coorY = coorY - cellHeight;
			for (int j = 1; j <= 10; j++) {
				BoardCell cell = new BoardCell(cellid, coorX, coorY);
				myBoardCell.add(cell);

				cellid = cellid + 1;
				coorX = coorX - cellWidth;
			}

			coorX = coorX + cellWidth;
			coorY = coorY - cellHeight;
		}

		for (int j = 0; j < myBoardCell.size(); j++) {

			System.out.println("index id:" + j + " Cell id:" + myBoardCell.get(j).getcellid() + " x coordinate:"
					+ myBoardCell.get(j).getX() + " y coordinate:" + myBoardCell.get(j).getY());

		}

	}
	public void placeBiscuitsandSticks() {
		// this method places the biscuits and sticks on to the board

		int myRandom = 0;
		int myCounter = 0;
		int randomMax = 40;
		int randomMin = 2;
		biscuits.clear();
		sticks.clear();
		// Functional Requirement 1.5
		while (myCounter < 2) {

			myRandom = (int) Math.floor((Math.random() * (randomMax - randomMin + 1)) + randomMin); // formula is ((max
																									// - min + 1) + min)
																									// so the number
																	// is generated between 2 and 40 according to the
																	// formula

			// biscuits are placed to the cells where there is no snakes and ladders
			if (biscuits.contains(myRandom) == false && snakeHeads.contains(myRandom) == false
					&& snakeTails.contains(myRandom) == false && ladderHeads.contains(myRandom) == false
					&& ladderStarts.contains(myRandom) == false) {

				biscuits.add(myRandom);
				myCounter = myCounter + 1;
			}
		}

		myCounter = 0;
		// Functional Requirement 1.7
		while (myCounter < 2) {

			myRandom = (int) Math.floor((Math.random() * (randomMax - randomMin + 1)) + randomMin); // formula is ((max
																									// - min + 1) + min)
																									// so the number
																	// is generated between 2 and 40 according to the
																	// formula
			// sticks are placed to the cells where there is no biscuits, snakes and ladders
			if (sticks.contains(myRandom) == false && biscuits.contains(myRandom) == false
					&& snakeHeads.contains(myRandom) == false && snakeTails.contains(myRandom) == false
					&& ladderHeads.contains(myRandom) == false && ladderStarts.contains(myRandom) == false) {

				sticks.add(myRandom);
				myCounter = myCounter + 1;
			}
		}

		int bisPosition, x = 0, y = 0;

		// biscuit icons are placed where there is cells with biscuits
		for (int i = 0; i < biscuits.size(); i++) {
			bisPosition = biscuits.get(i);

			for (int j = 0; j < myBoardCell.size(); j++) {
				if (myBoardCell.get(j).getcellid() == bisPosition) {
					x = myBoardCell.get(j).getX();
					y = myBoardCell.get(j).getY();

				}

				switch (i) {
				case 0:
					lbRandomBiscuit0.setBounds(x + 50, y - 40, 45, 40);
					lbRandomBiscuit0.setText(Integer.toString(bisPosition));
					break;

				case 1:
					lbRandomBiscuit1.setBounds(x + 50, y - 40, 45, 40);
					lbRandomBiscuit1.setText(Integer.toString(bisPosition));
					break;
				}

			}

		}

		int stiPosition;
		x = 0;
		y = 0;
		// stick icons are placed where there is cells with sticks

		for (int i = 0; i < sticks.size(); i++) {
			stiPosition = sticks.get(i);

			for (int j = 0; j < myBoardCell.size(); j++) {
				if (myBoardCell.get(j).getcellid() == stiPosition) {
					x = myBoardCell.get(j).getX();
					y = myBoardCell.get(j).getY();

				}

				switch (i) {
				case 0:
					lbRandomStick0.setBounds(x + 50, y - 40, 45, 40);
					lbRandomStick0.setText(Integer.toString(stiPosition));
					break;

				case 1:
					lbRandomStick1.setBounds(x + 50, y - 40, 45, 40);
					lbRandomStick1.setText(Integer.toString(stiPosition));
					break;
				}

			}

		}

	}

	public void removeBiscuit(int position) {
		// this method removes the biscuit which is taken by the player
		// Functional Requirement 3.8
		for (int i = 0; i < biscuits.size(); i++) {
			if (biscuits.get(i) == position) {
				biscuits.remove(i);
				// break;
			}
		}

		if (lbRandomBiscuit0.getText().equals(Integer.toString(position))) {
			lbRandomBiscuit0.setText("Removed");
			lbRandomBiscuit0.setVisible(false);
		}

		if (lbRandomBiscuit1.getText().equals(Integer.toString(position))) {
			lbRandomBiscuit1.setText("Removed");
			lbRandomBiscuit1.setVisible(false);
		}
	}

	public void removeStick(int position) {
		// this method removes the stick which is taken by the player
		// Functional Requirement 3.8
		for (int i = 0; i < sticks.size(); i++) {
			if (sticks.get(i) == position) {
				sticks.remove(i);
				// break;
			}
		}

		if (lbRandomStick0.getText().equals(Integer.toString(position))) {
			lbRandomStick0.setText("Removed");
			lbRandomStick0.setVisible(false);
		}

		if (lbRandomStick1.getText().equals(Integer.toString(position))) {
			lbRandomStick1.setText("Removed");
			lbRandomStick1.setVisible(false);
		}
	}

	public int checkSnakeLadder(int position) {

		// this method checks the cell if it contains snake head or ladder start
		// if player has any biscuit, player gives one biscuit to snake and remains in
		// the same cell otherwise player moves down to the snake tail
		// if the player has any stick, player gives stick to ladder and jumps ten more
		// step after the ladder head otherwise player moves up to the ladder head
		String msg;

		for (int i = 0; i < snakeHeads.size(); i++) {

			if (snakeHeads.get(i) == position) {
				playSound(2);

				currentPBiscuitCnt = myPlayers.get(playerTurn).getBiscuitCnt();
				if (currentPBiscuitCnt > 0) {
					// Functional Requirement 3.8
					currentPBiscuitCnt = currentPBiscuitCnt - 1;
					myPlayers.get(playerTurn).setBiscuitCnt(currentPBiscuitCnt);

					msg = "You encountered a snake!" + "\n"
							+ " You had biscuit(s) and gave it to snake so you do not have to move down to the tail square."
							+ "\n" + "Now you have " + currentPBiscuitCnt + " biscuit(s).";

					JOptionPane.showMessageDialog(null, msg, "Info", 1);
					// Functional Requirement 3.6
					return -1;
				}
				else {

				msg = "You encountered a snake! You are moving down ";
				JOptionPane.showMessageDialog(null, msg, "Warning", 2);
				// Functional Requirement 3.4
				return snakeTails.get(i);
			}
		}
	}

	for (int i = 0; i < ladderStarts.size(); i++) {

		if (ladderStarts.get(i) == position) {
			playSound(9);
			currentPStickCnt = myPlayers.get(playerTurn).getStickCnt();

			if (currentPStickCnt > 0 && ladderHeads.get(i) < 91) {
				// Functional Requirement 3.7
				// Functional Requirement 3.8
				currentPStickCnt = currentPStickCnt - 1;
				myPlayers.get(playerTurn).setStickCnt(currentPStickCnt);

				msg = "You encountered a ladder! You had stick(s) and got extra 10 move. Now you have "
						+ currentPStickCnt + " stick(s).";

				JOptionPane.showMessageDialog(null, msg, "Info", 1);
				// Functional Requirement 3.7
				return ladderHeads.get(i) + 10;

			} else {

			msg = "You encountered a ladder! You are moving up ";

			JOptionPane.showMessageDialog(null, msg, "Info", 1);
			// Functional Requirement 3.4
			return ladderHeads.get(i);
		}
		}
	}

		return -1;
	}

	public void move(int newPosition) {
		// this method moves the player's figure to the new position on the board
		playSound(3);
		int coorX = 0, coorY = 0;

		coorX = myBoardCell.get(newPosition).getX();
		coorY = myBoardCell.get(newPosition).getY();
		
		switch (playerTurn) {
		case 0:
			pFigure0.setBounds(coorX + 5, coorY, 30, 35);
			myPlayers.get(0).setPlayerPosition(newPosition);
			break;

		case 1:
			pFigure1.setBounds(coorX + 35, coorY, 30, 35);
			myPlayers.get(1).setPlayerPosition(newPosition);
			break;
			
		case 2:
			pFigure2.setBounds(coorX + 70, coorY, 30, 35);
			myPlayers.get(2).setPlayerPosition(newPosition);
			break;
			

		}
		
	}
	
	public void createPlayer(String newPName) {
		// this method creates player object for the game
		// Functional Requirement 2.1
		if (playerCnt < maxPlayerCnt) {
			// Functional Requirement 2.2
			Player player = new Player(playerCnt, newPName, pcolour.get(playerCnt));
			myPlayers.add(player);
			btnRollDice.setEnabled(true);
			playerCnt = playerCnt + 1;

			switch (playerCnt) {
			case 1:


				pFigure0.setVisible(true);

				break;

			case 2:


				pFigure1.setVisible(true);

				break;

			case 3:

				pFigure2.setVisible(true);

				// when the game reaches the max player count the objects which are used to
				// create new players are made invisible
				lbAddPlayer.setVisible(false);
				btAddPlayer.setVisible(false);
				txAddPlayer.setVisible(false);

				break;

			}
			refreshScreen();
			playSound(4);
			txAddPlayer.setText ("");
			JOptionPane.showMessageDialog(null, "Player " + newPName + " added to the game.", "Info", 1);
			

		}
	}

	public void restartGame() {
		// when the players wants to restart the game this method reset variables and
		// starts the game for this players again

		String msg;
		playerTurn = playerCnt - 1;
		diceValue = 4;

		for (int i = 0; i < myPlayers.size(); i++) {
			myPlayers.get(i).setBiscuitCnt(0);
			myPlayers.get(i).setStickCnt(0);
			myPlayers.get(i).setPlayerPosition(0);

		}

		pFigure0.setBounds(myBoardCell.get(0).getX(), myBoardCell.get(0).getY(), 30, 35);
		pFigure1.setBounds(myBoardCell.get(0).getX() + 50, myBoardCell.get(0).getY(), 30, 35);
		pFigure2.setBounds(myBoardCell.get(0).getX() + 100, myBoardCell.get(0).getY(), 30, 35);

		lbRandomBiscuit0.setVisible(true);
		lbRandomBiscuit1.setVisible(true);
		lbRandomStick0.setVisible(true);
		lbRandomStick1.setVisible(true);

		placeBiscuitsandSticks();

		msg = "Game has been restarted again. Good Luck everyone. ";

		JOptionPane.showMessageDialog(null, msg, "Info", 1);
		playSound(10);

	}


	public ImageIcon createImageIcon(String fileName) {
		// This method returns icon file name to ImageIcon type file path
		java.net.URL imgURL = getClass().getResource(fileName);

		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + fileName);
			return null;
		}
	}

	public void playSound(int i) {

		// this method plays sound effects
		if (playsoundMode == true) {
			String path = "tada.wav";

			switch (i) {

			case 1:
				path = "gong.wav";
				break;
			case 2:
				path = "shakeTail.wav";
				break;
			case 3:
				path = "playermove.wav";
				break;
			case 4:
				path = "ready.wav";
				break;
			case 5:
				path = "tada.wav";
				break;
			case 6:
				path = "winner.wav";
				break;
			case 7:
				path = "exceed100.wav";
				break;
			case 8:
				path = "diceThrow.wav";
				break;
			case 9:
				path = "applause.wav";
				break;
			case 10:
				path = "begin.wav";
				break;
			case 11:
				path = "stick.wav";
				break;
			case 12:
				path = "biscuit2.wav";
				break;

			}


			AudioFormat format;
			DataLine.Info info;
			Clip clip;

			try {

				java.net.URL url = this.getClass().getResource(path);
				InputStream in = url.openStream();

				AudioInputStream stream = AudioSystem.getAudioInputStream(new BufferedInputStream(in));
				format = stream.getFormat();
				info = new DataLine.Info(Clip.class, format);
				clip = (Clip) AudioSystem.getLine(info);
				clip.open(stream);
				clip.start();

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error: " + this.getClass().getClassLoader().getResourceAsStream("path"));

			}

		}
	}
}
