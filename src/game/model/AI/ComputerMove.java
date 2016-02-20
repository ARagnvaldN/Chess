package game.model.AI;

import game.model.ChessPiece;
import java.awt.Point;

/**
 * A class for storing a Chess Move and the corresponding tactical value.
 * 
 * @author Andreas Nylund
 * @author Hanna Granholm
 *
 */

public class ComputerMove {
	
	private Point startPoint;
	private Point endPoint;
	private double value;
	
	/**
	 * Constructor, creating a new move.
	 * All tactical value being seen from the black perspective.
	 * 
	 * @param startPoint Starting tile of the move.
	 * @param endPoint Ending tile of the move.
	 * @param piece The piece currently at endPoint.
	 */
	public ComputerMove(Point startPoint, Point endPoint, int piece){
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.value = getMoveValue(piece);
	}
	
	/**
	 * Calculates the tactical value of this piece being taken.
	 * 
	 * @param p Current piece to be taken.
	 * @return The tactical value of this piece.
	 */
	private int getMoveValue(int p){
		
		if(p == ChessPiece.WHITE_PAWN)
			return 1;
		else if(p == ChessPiece.WHITE_ROOK)
			return 5;
		else if(p == ChessPiece.WHITE_KNIGHT || p == ChessPiece.WHITE_BISHOP)
			return 3;
		else if(p == ChessPiece.WHITE_QUEEN)
			return 9;
		else if(p == ChessPiece.WHITE_KING)
			return 100;
		else if(p == ChessPiece.BLACK_PAWN)
			return -1;
		else if(p == ChessPiece.BLACK_ROOK)
			return -5;
		else if(p == ChessPiece.BLACK_KNIGHT || p == ChessPiece.BLACK_BISHOP)
			return -3;
		else if(p == ChessPiece.BLACK_QUEEN)
			return -9;
		else if(p == ChessPiece.BLACK_KING)
			return -100;
		else
			return 0;
	}
	
	/**
	 * Adds a value to this move.
	 * <p>
	 * Can be called to add value to a move from consecutive moves.
	 * 
	 * @param v Value to be added to this move.
	 */
	public void addValue(double v){
		value += v;
	}
	
	/**
	 * Gets the value of this move.
	 * 
	 * @return The value of this move.
	 */
	public double getValue(){
		return value;
	}

	/**
	 * Returns the starting tile of this move.
	 * 
	 * @return Starting point of this move.
	 */
	public Point getStartPoint() {
		return startPoint;
	}

	/**
	 * Returns the ending point of this move.
	 * 
	 * @return Ending tile of this move.
	 */
	public Point getEndPoint() {
		return endPoint;
	}

	@Override
	public String toString() {
		return "ComputerMove [value=" + value + "]";
	}

	
}
