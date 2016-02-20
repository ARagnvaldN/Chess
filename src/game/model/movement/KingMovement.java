package game.model.movement;

import java.awt.Point;
import java.util.ArrayList;

/**
	 * A strategy class for the Chess KING piece.
	 * 
	 * @author Andreas Nylund
	 * @author Hanna Granholm
	 *
	 */

public class KingMovement implements MoveStrategy {

	@Override
	public ArrayList<Point> getValidMovement(Point point, int[][] matrix,
			int piece) {
		
		ArrayList<Point> valid = new ArrayList<Point>();
		
		for(int x=-1;x<2;x++){
			for(int y=-1;y<2;y++){
				if(point.x+x >= 0 && point.x+x <= 7 && 
						point.y+y >= 0 &&point.y+y <= 7 && 
						matrix[point.x+x][point.y+y] != -1)
					valid.add(new Point(point.x+x, point.y+y));
			}
		}
		
		return valid;
	}

	

}
