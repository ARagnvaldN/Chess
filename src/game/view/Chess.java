package game.view;

import game.controller.ChessController;
import game.model.ChessBoard;
import game.model.ChessLogic;
import game.model.score.ScoreBoard;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * A class describing a Chess game.
 * 
 * @author Andreas Nylund
 * @author Hanna Granholm
 *
 */

@SuppressWarnings("serial")
public class Chess extends JFrame{
	
	public static final String 	EXIT_STR = "Exit",
								NEWGAME_STR = "New Game...",
								PAUSE_STR = "Pause/Resume",
								SHOWSCORE_STR = "Show Score...",
								RESETSCORE_STR = "Reset Score",
								HELP_STR = "How to play...",
								ABOUT_STR = "About...";
	
	/**
	 * Constructor for the Chess Game. Every other constructor is called from here.
	 * Model, view and controller are created from here as well as the different 
	 * dialogs and menus.
	 * 
	 */
	public Chess(){
		
		//SET FRAME ICON
		ClassLoader cl = this.getClass().getClassLoader();
		ImageIcon ii = new ImageIcon(cl.getResource("game/resources/b_knight.png"));
		setIconImage(ii.getImage());
		
		//DIALOGS
		NewGameDialog newGameDialog = new NewGameDialog(this);
		HighScoreDialog highScoreDialog = new HighScoreDialog(this);
		
		//Create objects
		ChessBoard board = new ChessBoard();
		ScoreBoard scoreBoard = ScoreBoard.getInstance();
		ChessLogic model = new ChessLogic(scoreBoard, board);
		ChessView view = new ChessView(newGameDialog, highScoreDialog);
		ChessController controller = new ChessController(	model, 
															view, 
															board, 
															scoreBoard, 
															newGameDialog,
															highScoreDialog);
		
		//Set frame
		add(view);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(200,200);
		pack();
		setResizable(false);
		setVisible(true);
		
		//Menu
		JMenuBar menuBar = new JMenuBar();
		
		//FILE MENU
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
			
			//Start NEW GAME...
			JMenuItem newGameItem = new JMenuItem(NEWGAME_STR);
			fileMenu.add(newGameItem);
			newGameItem.addActionListener(controller);
			
			//Pause Game
			JMenuItem pauseItem = new JMenuItem(PAUSE_STR);
			fileMenu.add(pauseItem);
			pauseItem.addActionListener(controller);
			
			//------
			fileMenu.addSeparator();
			
			//Exit
			JMenuItem exitItem = new JMenuItem(EXIT_STR);
			fileMenu.add(exitItem);
			exitItem.addActionListener(controller);
		
		//HIGHSCORE MENU
		JMenu scoreMenu = new JMenu("HighScore");
		menuBar.add(scoreMenu);
		
			//Show HIGHSCORE
			JMenuItem showScoreItem = new JMenuItem(SHOWSCORE_STR);
			scoreMenu.add(showScoreItem);
			showScoreItem.addActionListener(controller);
			
			//Reset HIGHSCORE
			JMenuItem resetScoreItem = new JMenuItem(RESETSCORE_STR);
			scoreMenu.add(resetScoreItem);
			resetScoreItem.addActionListener(controller);
			
		//ABOUT MENU
		JMenu aboutMenu = new JMenu("About");
		menuBar.add(aboutMenu);
		
			//HELP...
			JMenuItem helpItem = new JMenuItem(HELP_STR);
			aboutMenu.add(helpItem);
			helpItem.addActionListener(controller);
		
			//ABOUT...
			JMenuItem aboutItem = new JMenuItem(ABOUT_STR);
			aboutMenu.add(aboutItem);
			aboutItem.addActionListener(controller);
		
		setJMenuBar(menuBar);
		
		
		
	}
	
	/**
	 * Main for Chess, creates a new Chess game.
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		new Chess();
	}

}
