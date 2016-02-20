package game.model.score;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;


/**
 * A class describing a ScoreBoard
 * 
 * @author Andreas Nylund
 * @author Hanna Granholm
 *
 */

public class ScoreBoard extends Observable implements Serializable{
	
	private static final long serialVersionUID = 1649160479602044234L;
	private static final String FILENAME = "score.dat";
	
	private static ScoreBoard instance = null;
	private ArrayList<Player> players;
	private final int K = 16;
	
	/**
	 * Constructor method, creates a new ScoreBoard.
	 * 
	 */
	private ScoreBoard(){
		players = new ArrayList<Player>();
	}
	
	/**
	 * Returns a reference to the ScoreBoard.
	 * 
	 * @return Reference to ScoreBoard.
	 */
	public static ScoreBoard getInstance(){
		if(instance == null){
			
			//Check if there is a saved score board
			File f = new File("score.dat");
			if(f.exists())
				loadScoreBoard();
			else
				instance = new ScoreBoard();
		}
		
		return instance;
	}
	
	/**
	 * Adds a player to the score board.
	 * 
	 * @param p Player to be added to the score board.
	 */
	public void addPlayer(Player p){
		players.add(p);
		
		updateScoreBoard();
	}
	
	/**
	 * Prints the score board to the console.
	 * 
	 */
	public void printScoreBoard(){
		for(Player p:players){
			System.out.println(p.toString());
		}
	}
	
	/**
	 * Updates the score board with the result of a game.
	 * 
	 * @param winner The player that won
	 * @param loser The player that lost
	 */
	public void updateScoreBoard(Player winner, Player loser, Time time){
		
		winner.setRating(calculateWinRating(winner.getRating(), loser.getRating()));
		loser.setRating(calculateLoseRating(loser.getRating(), winner.getRating()));
		
		//Set best time if shorter than best time so far
		if(winner.getBestTime().compareTo(time) > 0 ||
				winner.getBestTime().equals(new Time(0)))
			
			winner.setBestTime(time);
			
		//Count up noOfGames
		winner.addGame();
		loser.addGame();
		
		updateScoreBoard();
	}
	
	/**
	 * Updates the score board, sorts the entries, saves it and notifies observers.
	 * 
	 */
	public void updateScoreBoard(){
			
			sortScoreBoard();
			saveScoreBoard();
			
			setChanged();
			notifyObservers();
		}
	
	/**
	 * Sorts the entries on the score board according to rating.
	 * 
	 */
	private void sortScoreBoard(){
		Collections.sort(players, new PlayerComparator());
	}
	
	/**
	 * Calculates the new rating for the winner of a game of Chess.
	 * <p>
	 * The calculating method uses the Elo rating system.
	 * 
	 * @param A The winner
	 * @param B The loser
	 * @return The new rating of the winner
	 */
	private double calculateWinRating(double A, double B){
		
		double E = 1 / (1 + Math.pow(10, (B - A)/400));
		
		double newRating = A + K * (1 - E); 
		
		return Math.round(newRating);
	}
	
	/**
	 * Calculates the new rating for the loser of a game of Chess.
	 * <p>
	 * The calculating method uses the Elo rating system.
	 * 
	 * @param A The loser
	 * @param B The winner
	 * @return The new rating of the winner
	 */
	private double calculateLoseRating(double A, double B){
			
			double E = 1 / (1 + Math.pow(10, (B - A)/400));
			
			double newRating = A + K * (0 - E); 
			
			return Math.round(newRating);
		}
	
	/**
	 * Saves the score board to the file.
	 * 
	 */
	private void saveScoreBoard(){
		try
	      {
	         FileOutputStream outFile =
	        		 			new FileOutputStream(FILENAME);
	         ObjectOutputStream out =
	                            new ObjectOutputStream(outFile);
	         out.writeObject(instance);
	         out.close();
	         outFile.close();
	      }catch(IOException e)
	      {
	    	  e.printStackTrace();
	      }
	}
	
	/**
	 * Loads the score board from file.
	 * 
	 */
	private static void loadScoreBoard(){
		try
	      {
	         FileInputStream inFile =
	        		 			new FileInputStream(FILENAME);
	         ObjectInputStream in =
	                            new ObjectInputStream(inFile);
	         instance = (ScoreBoard) in.readObject();
	         in.close();
	         inFile.close();
	      }catch(IOException e){
	          e.printStackTrace();
	      }catch(ClassNotFoundException e){
	    	  e.printStackTrace();
	      }
	}
	
	/**
	 * Returns the score board as a list of players.
	 * 
	 * @return List of players
	 */
	public ArrayList<Player> getScoreBoard(){
		return players;
	}

	/**
	 * Resets the score board, removes all entries.
	 */
	public void reset() {
		players = new ArrayList<Player>();
		updateScoreBoard();
		
	}
	
	
}
