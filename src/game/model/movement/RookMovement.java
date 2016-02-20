package game.model.movement;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Strategy for Chess rook movement.
 * 
 * @author Andreas Nylund
 * @author Hanna Granholm
 *
 */

public class RookMovement extends LineMover {


	@Override
	public ArrayList<Point> getValidMovement(Point point, int[][] matrix,
			int piece) {
		
		this.matrix = matrix;
		valid = new ArrayList<Point>();
		
		getValidLine(point,1,0);	//Right
		getValidLine(point,-1,0);	//Left
		getValidLine(point,0,1);	//Down
		getValidLine(point,0,-1);	//Up
		
		
		return valid;
	}
	
	


}
