package game.model;

import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.Observable;

import javax.swing.Timer;


/**
 * A simple Chess Clock.
 * 
 * @author Andreas Nylund
 * @author Hanna Granholm
 */

public class Clock extends Observable{
	
	private static int 	whiteTimeElapsed,
						blackTimeElapsed;
	
	private Timer 		timer;
	private final int 	ONE_HOUR = 3600000;
	private int turn;
	
	/**
	 * Constructor for the clock.
	 * <p>
	 * The specified actionlistener will listen for the clock ticks.
	 * 
	 * @param controller
	 */
	public Clock(ActionListener controller){
		whiteTimeElapsed = 0;
		blackTimeElapsed = 0;
		timer = new Timer(250, controller);
	}
	
	/**
	 * Starts the clock.
	 */
	public void startClock(){
		timer.start();
	}
	
	/**
	 * The clock ticks, preferably called by the actionlistener on each action event.
	 * <p>
	 * The specified color specifies which player is currently playing and adds time to
	 * his/her clock.
	 * 
	 * @param currentTurn The current player's color.
	 */
	public void addTime(int currentTurn){
		turn = currentTurn;
		if(currentTurn == ChessLogic.BLACK)
			blackTimeElapsed += 250;
		else
			whiteTimeElapsed += 250;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Returns the player currently making a move.
	 * 
	 * @return Color of player currently making a move.
	 */
	public int getTurn(){
		return turn;
	}
	
	/**
	 * A method for reading the time elapsed from the clock.
	 * <p>
	 * The returned array is ordered after color.
	 * time[ChessLogic.WHITE] being white time elapsed etc.
	 * 
	 * 
	 * @return An array of time elapsed for each player.
	 */
	public Time[] getTime(){
		Time[] time = new Time[2];
		
		time[ChessLogic.WHITE] = new Time(whiteTimeElapsed-ONE_HOUR);
		time[ChessLogic.BLACK] = new Time(blackTimeElapsed-ONE_HOUR);
		
		return time;
	}
	
	/**
	 * Resets the clock to zero.
	 */
	public void reset() {
		blackTimeElapsed = whiteTimeElapsed = 0;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Stops the clock.
	 */
	public void stopClock() {
		timer.stop();
		
	}

}
