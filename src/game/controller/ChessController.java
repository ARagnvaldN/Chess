package game.controller;

import game.model.ChessBoard;
import game.model.ChessLogic;
import game.model.ChessPiece;
import game.model.Clock;
import game.model.UnrealPieceException;
import game.model.score.Player;
import game.model.score.ScoreBoard;
import game.view.Chess;
import game.view.ChessView;
import game.view.HighScoreDialog;
import game.view.NewGameDialog;
import game.view.TileView;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;

public class ChessController implements ActionListener {

	private static final int 	PLAY = 0,
								PAUSE = 1;
	private ChessLogic model;
	private ChessView view;
	private ChessBoard board;
	private ScoreBoard scoreBoard;
	private NewGameDialog newGameDialog;
	private HighScoreDialog highScoreDialog;
	private Clock clock;
	private Point currentClick;
	private int gameState = PLAY;
	
	private JDialog addPlayerDialog;
	
	/**
	 * Constructor for ChessController, the controller.
	 * <p>
	 * Connects observers to observable and adds this as a listener to all objects with
	 * action events.
	 * 
	 * @param model The Chess Model
	 * @param view The Chess view
	 * @param board The Chess Board
	 * @param scoreBoard The Chess score board
	 * @param newGameDialog The dialog for starting a new game
	 * @param highScoreDialog The dialog for displaying highscore
	 */
	public ChessController(	ChessLogic model, 
							ChessView view, 
							ChessBoard board, 
							ScoreBoard scoreBoard,
							NewGameDialog newGameDialog,
							HighScoreDialog highScoreDialog){
		
		this.model = model;
		this.view = view;
		this.scoreBoard = scoreBoard;
		this.board = board;
		this.newGameDialog = newGameDialog;
		this.highScoreDialog = highScoreDialog;
		clock = new Clock(this);
		
		//Add the view as an observer to the board, clock and score board
		scoreBoard.addObserver(view);
		board.addObserver(view);
		clock.addObserver(view);
		
		scoreBoard.updateScoreBoard();
		
		//Add listeners to the buttons
		view.addListener(this);
		newGameDialog.addController(this);
		highScoreDialog.addController(this);
	}
	
	/**
	 * Implementation of actionPerfomed from the ActionListener Interface
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		if(ae.getSource() instanceof TileView && gameState == PLAY){
			
			action( (TileView) ae.getSource() );
		
		} else if(ae.getSource() instanceof Timer && gameState == PLAY){
			
			clock.addTime(model.getTurn());
			
		} else if(ae.getSource() instanceof JMenuItem) {
			
			action( (JMenuItem) ae.getSource() );
			
		} else if(ae.getSource() instanceof JButton) {
			
			action( (JButton) ae.getSource() );
			
		} else if(ae.getSource() instanceof JTextField){
			
			action( (JTextField) ae.getSource() );
		}
		
	}
	
	/**
	 * Handles all actions from TileViews.
	 * 
	 * @param tileView The tile that was clicked
	 */
	private void action(TileView tileView){
		
		if(currentClick == null){
			
			if( board.getPiece(tileView.getPoint()) != ChessPiece.NO_PIECE)
				currentClick = tileView.getPoint();
			
		} else {
			try {
				if(!model.move(currentClick, tileView.getPoint()))
					JOptionPane.showMessageDialog(view, 
							ChessPiece.pieceTab[board.getPiece(currentClick)] +
							" can't move there.",
							"Illegal Move",
							JOptionPane.ERROR_MESSAGE);
				
			} catch (UnrealPieceException e) {
				e.printStackTrace();
			}finally {
				currentClick = null;
				if(model.checkMate() != -1){

					JOptionPane.showMessageDialog(view, ChessLogic.colorTab[model.checkMate()] +
							" has won!");
					clock.stopClock();
					System.out.println("EHERE!");
					model.gameOver(clock.getTime()[model.getTurn()]);
				}
			}
		}
	}
	
	
	/**
	 * Handles all the MenuItem actions.
	 * 
	 * @param jmi The JMenuItem that was clicked
	 */
	private void action(JMenuItem jmi){
		
		if(jmi.getText().equals(Chess.EXIT_STR))
			System.exit(0);
		else if(jmi.getText().equals(Chess.PAUSE_STR)){
			if(gameState == PLAY) 
				gameState = PAUSE;
			else
				gameState = PLAY;
			
		} else if(jmi.getText().equals(Chess.NEWGAME_STR)){
			view.showNewGameDialog();
			
		}else if(jmi.getText().equals(Chess.SHOWSCORE_STR)){
			highScoreDialog.setVisible(true);
			
		}else if(jmi.getText().equals(Chess.RESETSCORE_STR)){
			scoreBoard.reset();
			
		}else if(jmi.getText().equals(Chess.HELP_STR)){
			JOptionPane.showMessageDialog(view, 
					"Pawns: Pawns can only move forward. On their first move," +
					"\nthey can move one or two squares. Afterwards, they can " +
					"\nmove only one square at a time. They can capture an enemy" +
					"\npiece by moving one square forward diagonally." +
					"\n\nBishops: Bishops can move any number of squares diagonally." +
					"\n\nKnights: Knights can move only in an L-shape, one square up" +
					"\nand two over, or two squares over and one down, or any such" +
					"\ncombination of one-two or two-one movements in any direction." +
					"\n\nRooks: Rooks can move any number of squares, up and down and " +
					"\nside to side." +
					"\n\nQueens: Queens can move any number of squares along ranks," +
					"\nfiles and diagonals." +
					"\n\nKings: Kings can move one square at a time in any direction." +
					"\n\nCheck: An attack on a king by either an opposing piece or an " +
					"\nopposing pawn is called check. When in check, a player must do" +
					"\none of the following:" +
					"\n\nMove the king so that it’s no longer under attack." +
					"\n\nBlock the attack by interposing a piece between the king and " +
					"\nthe attacker." +
					"\n\nCapture the attacking piece." +
					"\n\nCheckmate: When a king is in check and can’t perform any of " +
					"\nthe preceding moves, it has been checkmated. If your king is " +
					"\ncheckmated, you lose the game. The term checkmate is commonly " +
					"\nshortened to simply mate.",
					"How to play", JOptionPane.INFORMATION_MESSAGE);
			
		}else if(jmi.getText().equals(Chess.ABOUT_STR)){
			JOptionPane.showMessageDialog(view, 
					"Det här är en jättebra version av Schack, med magiska enhörnignar." +
					"\n" +
					"\n\"Schack är ett bräd- och strategispel för två deltagare. Lånordet 'schack'" +
					"\nhärstammar från det persiska ordet shah, som betyder 'kung'. Spelet " +
					"\nanses ofta vara 'kungen av strategispel', tillsammans med bridge och go. " +
					"\n" +
					"\nSchack är ett spel med fullständig information. Detta innebär att allt " +
					"\nsom händer på spelplanen är synligt för båda spelarna. Det är ett rent" +
					"\nskicklighetsspel utan inslag av tur och det finns teoretiskt en optimal" +
					"\nstrategi som ännu är okänd. " +
					"\n" +
					"\nSpelet schack har ett mycket stort antal spelmöjligheter, trots att " +
					"\nutgångsställningen är densamma i varje parti. Det finns exempelvis fler " +
					"\nmöjliga sekvenser av drag än atomer i det observerbara universumet.\"" +
					"\n(Från http://sv.wikipedia.org/wiki/Schack/)" +
					"\n" +
					"\nSkapat av Hanna Granholm och Andreas Nylund." +
					"\n(C) 2013",
					"About",
					JOptionPane.PLAIN_MESSAGE);
		}
		
	}
	
	/**
	 * Handles all the actions from JButtons.
	 * 
	 * @param button Button that was clicked
	 */
	private void action(JButton button){
		
		if(button.getText().equals(NewGameDialog.ADDPLAYER_STR)){
			addPlayerDialog = new JDialog();
			addPlayerDialog.setLayout(new BorderLayout());
			addPlayerDialog.setSize(300, 150);
			
			addPlayerDialog.add(new JLabel("Name: "), BorderLayout.WEST);
			JTextField createText = new JTextField();
			createText.addActionListener(this);
			addPlayerDialog.add(createText, BorderLayout.CENTER);

			addPlayerDialog.setVisible(true);
			
		} else if(button.getText().equals(NewGameDialog.STARTGAME_STR)){
			startGame();
			
		}else if(button.getText().equals(HighScoreDialog.CLOSE_STR)){
			highScoreDialog.setVisible(false);
		}
	}
	
	/**
	 * Handles all JTextField action events.
	 * 
	 * @param text The text field that was entered
	 */
	private void action(JTextField text){
		scoreBoard.addPlayer(new Player(text.getText()));
		addPlayerDialog.setVisible(false);
		
	}
	
	/**
	 * The method that starts a new game. Called from New Game Dialog.
	 */
	private void startGame(){
		//START NEW GAME
				Player[] p = newGameDialog.getPlayerChoice();
				model.startNewGame(p[0], p[1]);
				clock.reset();
				clock.startClock();
				newGameDialog.setVisible(false);
	}

}
