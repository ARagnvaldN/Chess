package game.view;


import game.controller.ChessController;
import game.model.score.Player;
import game.model.score.ScoreBoard;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A class describing a dialog for displaying high score.
 * 
 * @author Andreas Nylund
 * @author Hanna Granholm
 *
 */

@SuppressWarnings("serial")
public class HighScoreDialog extends JDialog{

	public static final String CLOSE_STR = "Close";
	private JButton closeButton;
	private JLabel[] playerLabels;
	
	/**
	 * Consrtuctor for this dialog.
	 * 
	 * @param frame The frame to which the dialog will be linked
	 */
	public HighScoreDialog(JFrame frame){
		new JDialog(frame, "New Game");
		setLayout(new BorderLayout());
		setSize(500,400);
		
		//Header
		JLabel header = new JLabel("---- High Scores ----");
		add(header, BorderLayout.NORTH);
		
		JPanel centerPanel = new JPanel(new GridLayout(10,1));
		JPanel westPanel = new JPanel(new GridLayout(10,1));
		add(centerPanel, BorderLayout.CENTER);
		add(westPanel, BorderLayout.WEST);
		
		//Player and number labels
		playerLabels = new JLabel[10];
		for(int i=0;i<10;i++){
			JLabel numL = new JLabel(Integer.toString(i+1)+". ");
			playerLabels[i] = new JLabel("-------------");
			centerPanel.add(playerLabels[i]);
			westPanel.add(numL);
		}
		
		//Close button
		closeButton = new JButton("Close");
		add(closeButton, BorderLayout.SOUTH);
	}
	
	/**
	 * Resets all labels to be empty.
	 */
	public void emptyFields(){
		for(int i=0;i<10;i++){
			playerLabels[i].setText("--------------");

		}
	}
	
	/**
	 * Adds the ten most proficient players from the score board.
	 * 
	 * @param sb The score board logic.
	 */
	public void updatePlayers(ScoreBoard sb){
		ArrayList<Player> players = sb.getScoreBoard();
		
		if(players.isEmpty())
			emptyFields();
		else
			for(int i=0;i<10 && i<players.size();i++)
				playerLabels[i].setText( players.get(i).toString() );
	}
	
	/**
	 * Adds the specified controller as a listener to the buttons.
	 * 
	 * @param controller The apps controller
	 */
	public void addController(ChessController controller){
		closeButton.addActionListener(controller);
	}
	
}
