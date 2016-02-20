package game.model.score;

import java.io.Serializable;
import java.sql.Time;

/**
 * A class describing a Chess player.
 * 
 * @author Andreas Nylund
 * @author Hanna Granholm
 *
 */

public class Player implements Serializable{
	
	private static final long serialVersionUID = -8590087605582155066L;
	
	private String name;
	private double rating;
	private Time bestTime;
	private int noOfGames;
	boolean isComputer;
	private final int 	ONE_HOUR = 3600000;
	
	/**
	 * A constructor, creating a new player with a name and beginner rating.
	 * 
	 * @param name Name of the player.
	 */
	public Player(String name){
		this.name = name;
		rating = 1200;
		noOfGames = 0;
		bestTime = new Time(-ONE_HOUR);
	}
	
	/**
	 * Constructor for creating a computer player.
	 * <p>
	 * NOTE!: Should not be used for creating human players.
	 * 
	 * @param b Whether player is a computer player or not.
	 */
	public Player(boolean b){
		this.name = "COMPUTER";
		rating = 1500;
		noOfGames = 0;
		bestTime = new Time(-ONE_HOUR);
		isComputer = b;
	}
	
	/**
	 * Adds a played game to this player.
	 */
	public void addGame(){
		noOfGames++;
	}
	
	@Override
	public String toString() {
		return name + ", Rating: " + rating + ", Best Time: "
				+ bestTime + ", Games played: " + noOfGames;
	}

	/**
	 * Queries this player on whether it is a computer.
	 * 
	 * @return Whether this player is a computer.
	 */
	public boolean isComputer(){
		return isComputer;
	}
	
	/**
	 * Returns the name of the player.
	 * 
	 * @return The name of the player.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of a player to the specified string.
	 * 
	 * @param name New name of the player
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets this player's rating.
	 * 
	 * @return Player rating
	 */
	public double getRating() {
		return rating;
	}
	
	/**
	 * Sets the new rating of this player.
	 * 
	 * @param rating New rating of player
	 */
	public void setRating(double rating) {
		this.rating = rating;
	}
	
	/**
	 * Returns the current best time of this player.
	 * 
	 * @return Best time of this player.
	 */
	public Time getBestTime() {
		return bestTime;
	}
	
	/**
	 * Sets the new best time of this player.
	 * 
	 * @param bestTime New best time of this player
	 */
	public void setBestTime(Time bestTime) {
		this.bestTime = bestTime;
	}
	
	/**
	 * Returns the number of games this player has played.
	 * 
	 * @return Number of games this player has played.
	 */
	public int getNoOfGames() {
		return noOfGames;
	}
	
	
	
	
}
