package game.model.movement;

import java.awt.Point;
import java.util.ArrayList;

/**
 * An interface defining movement strategies for Chess.
 * 
 * @author Andreas Nylund
 * @author Hanna Granholm
 *
 */

public interface MoveStrategy{
	
	/**
	 * Method returning an arraylist of valid movements for the specified piece from the
	 * specified piece on the specified board.
	 * 
	 * @param point The starting tile.
	 * @param matrix A color matrix representation of the board, 1 being enemy color and
	 * 				 -1 being friendly.
	 * @param piece The piece at the starting tile.
	 * @return A list of valid movements for the specified piece, board and tile.
	 */
	public ArrayList<Point> getValidMovement(Point point, int[][] matrix, int piece);
	
	
}

