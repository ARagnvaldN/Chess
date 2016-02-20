package game.model.movement;


import java.awt.Point;
import java.util.ArrayList;

/**
 * A strategy class for the Chess bishop piece.
 * 
 * @author Andreas Nylund
 * @author Hanna Granholm
 *
 */

public class BishopMovement extends LineMover {

	@Override
	public ArrayList<Point> getValidMovement(Point point, int[][] matrix,
			int piece) {
		this.matrix = matrix;
		valid = new ArrayList<Point>();
		
		getValidLine(point,1,1);	//Down right
		getValidLine(point,1,-1);	//Up right
		getValidLine(point,-1,-1);	//Up left
		getValidLine(point,-1,1);	//Down left
		
		return valid;
	}

	

}
