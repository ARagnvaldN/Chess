package game.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import game.controller.ChessController;
import game.model.ChessBoard;
import game.model.ChessLogic;
import game.model.Clock;
import game.model.score.ScoreBoard;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A class describing a Chess view.
 * <p>
 * Contains buttons and panels to represent the Chess Game visually.
 * 
 * @author Andreas Nylund
 * @author Hanna Granholm
 *
 */

@SuppressWarnings("serial")
public class ChessView extends JPanel implements Observer{
	
	private NewGameDialog newGameDialog;
	private HighScoreDialog highScoreDialog;
	
	private JPanel letterPanel;
	private JPanel numberPanel;
	private JPanel infoPanel;
	private JPanel boardPanel;
	
	private JLabel whiteTimeLabel;
	private JLabel blackTimeLabel;
	private JLabel turnLabel;
	
	private ArrayList<TileView> tiles;
	
	
	/**
	 * Constructor for creating the Chess View.
	 * 
	 * @param newGameDialog Dialog for starting a new game
	 * @param highScoreDialog Dialog for displaying high score
	 */
	public ChessView(NewGameDialog newGameDialog, HighScoreDialog highScoreDialog){
		this.newGameDialog = newGameDialog;
		this.highScoreDialog = highScoreDialog;
		this.setLayout(new BorderLayout());
		this.setBackground(Color.black);
		
		//NORTH
		letterPanel = new JPanel();
		add(letterPanel, BorderLayout.NORTH);
		letterPanel.setLayout(new GridLayout(1,9));

			letterPanel.add(new JLabel(""));
			letterPanel.add(new JLabel("A"));
			letterPanel.add(new JLabel("B"));
			letterPanel.add(new JLabel("C"));
			letterPanel.add(new JLabel("D"));
			letterPanel.add(new JLabel("E"));
			letterPanel.add(new JLabel("F"));
			letterPanel.add(new JLabel("G"));
			letterPanel.add(new JLabel("H"));
		
		//WEST
		numberPanel = new JPanel();
		add(numberPanel, BorderLayout.WEST);
		numberPanel.setLayout(new GridLayout(8,1));
		numberPanel.setPreferredSize(new Dimension(15,0));
		
			numberPanel.add(new JLabel("1"));
			numberPanel.add(new JLabel("2"));
			numberPanel.add(new JLabel("3"));
			numberPanel.add(new JLabel("4"));
			numberPanel.add(new JLabel("5"));
			numberPanel.add(new JLabel("6"));
			numberPanel.add(new JLabel("7"));
			numberPanel.add(new JLabel("8"));
		
		
		//SOUTH
		infoPanel = new JPanel();
		add(infoPanel, BorderLayout.SOUTH);
		infoPanel.setLayout(new GridLayout(1,3));
		
		infoPanel.add(new JLabel("WhiteTime: "));
		whiteTimeLabel = new JLabel("--");
		infoPanel.add(whiteTimeLabel);
		infoPanel.add(new JLabel("BlackTime: "));
		blackTimeLabel = new JLabel("--");
		infoPanel.add(blackTimeLabel);
		
		infoPanel.add(new JLabel("Turn: "));
		turnLabel = new JLabel("--");
		infoPanel.add(turnLabel);
		
		//CENTER
		boardPanel = new JPanel();
		add(boardPanel, BorderLayout.CENTER);
		boardPanel.setLayout(new GridLayout(8,8));
		boardPanel.setPreferredSize(new Dimension(400,400));
		tiles = new ArrayList<TileView>();
		
			for(int x=0;x<8;x++){
				for(int y=0;y<8;y++){
					TileView tv = new TileView(new Point(y,x), x+y);
					tiles.add(tv);
					boardPanel.add(tv);
				}
			}
			
	
	}
	
	/**
	 * Displays the dialog for starting a new game.
	 * 
	 */
	public void showNewGameDialog(){
		newGameDialog.setVisible(true);
	}


	@Override
	public void update(Observable o, Object arg) {

		if(o instanceof ChessBoard)
			updateBoard( (ChessBoard) o );
		
		else if(o instanceof Clock)
			updateTime( (Clock) o );
		
		else if(o instanceof ScoreBoard){
			ScoreBoard sb = (ScoreBoard) o;
			newGameDialog.updatePlayers(sb);
			highScoreDialog.updatePlayers(sb);
		}
		
	}
	
	/**
	 * Updates the visual representation of the model chess board.
	 * 
	 * @param board ChessBoard logic.
	 */
	private void updateBoard(ChessBoard board){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				tiles.get(i*8+j).setPiece(board.getPiece(new Point(j, i)));
			}
		}
		repaint();
	}
	
	/**
	 * Updates the visual representation of the clock.
	 * 
	 * @param o Clock model.
	 */
	private void updateTime(Clock clock){

		Time[] time = clock.getTime();
		
		whiteTimeLabel.setText( time[0].toString() );
		blackTimeLabel.setText( time[1].toString() );
		
		if(clock.getTurn() == ChessLogic.BLACK)
			turnLabel.setText("Black");
		else
			turnLabel.setText("White");
		
		repaint();
	}
	
	/**
	 * Adds the controller as a listener to each tile.
	 * 
	 * @param controller The controller of this app
	 */
	public void addListener(ChessController controller){
		for(TileView tile:tiles)
			tile.addActionListener(controller);
	}

}
