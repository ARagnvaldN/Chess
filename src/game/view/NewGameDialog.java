package game.view;

import game.controller.ChessController;
import game.model.score.Player;
import game.model.score.ScoreBoard;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A class describing a dialog for starting a new game.
 * 
 * @author Andreas Nylund
 * @author Hanna Granholm
 *
 */

@SuppressWarnings("serial")
public class NewGameDialog extends JDialog {
	
	public static final String 	STARTGAME_STR = "Start Game",
								ADDPLAYER_STR = "New player";
	private JPanel playerPanel;
	private JPanel p1Panel;
	private JPanel p2Panel;
	private JPanel buttonPanel;
	private JComboBox<Player> p1Combo;
	private JComboBox<Player> p2Combo;
	private JButton newPlayerButton;
	private JButton startGameButton;
	
	/**
	 * Constructor for this dialog.
	 * 
	 * @param frame The frame to link this dialog to
	 */
	public NewGameDialog(JFrame frame){
	//NEW GAME DIALOG
			new JDialog(frame, "New Game");
			setLayout(new BorderLayout());
			setSize(500, 200);
			
			//Choose player panel
			playerPanel = new JPanel();
				p1Panel = new JPanel();
				p1Combo = new JComboBox<Player>();
				p1Panel.add(new JLabel("Player 1, White"));
				p1Panel.add(p1Combo);
				p2Panel = new JPanel();
				p2Combo = new JComboBox<Player>();
				p2Panel.add(new JLabel("Player 2, Black"));
				p2Panel.add(p2Combo);
				playerPanel.add(p1Panel);
				playerPanel.add(p2Panel);
				
			add(playerPanel, BorderLayout.CENTER);
			
			//Buttons panel
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridLayout(1,2));
			newPlayerButton = new JButton(ADDPLAYER_STR);
			buttonPanel.add(newPlayerButton);
			startGameButton = new JButton(STARTGAME_STR);
			buttonPanel.add(startGameButton);
			
			add(buttonPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Update the players to be selected for a new game.
	 * 
	 * @param sb ScoreBoard containing players
	 */
	public void updatePlayers(ScoreBoard sb){
		ArrayList<Player> players = sb.getScoreBoard();
		
		//Clear old lists
		p2Combo.removeAllItems();
		p1Combo.removeAllItems();
		
		//Add computer
		p2Combo.addItem(new Player(true));
		
		//Add players
		for(Player p:players){
			p1Combo.addItem(p);
			p2Combo.addItem(p);
		}
		
			
	}
	
	/**
	 * Adds the specified controller as a listener to this dialogs buttons.
	 * 
	 * @param controller This apps controller
	 */
	public void addController(ChessController controller) {
		newPlayerButton.addActionListener(controller);
		startGameButton.addActionListener(controller);
		
	}
	
	/**
	 * Returns the selected players from the combo boxes.
	 * 
	 * @return The selected players from the combo boxes
	 */
	public Player[] getPlayerChoice(){
		
		Player p1 = (Player) p1Combo.getSelectedItem();
		Player p2 = (Player) p2Combo.getSelectedItem();
		
		Player[] players = new Player[2];
		players[0] = p1;
		players[1] = p2;
		
		return players;
	}
	
}
