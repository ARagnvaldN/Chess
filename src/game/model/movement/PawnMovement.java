package game.model.movement;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Strategy for pawn movement.
 * 
 * @author Andreas Nylund
 * @author Hanna Granholm
 *
 */

public class PawnMovement implements MoveStrategy {

	@Override
	public ArrayList<Point> getValidMovement(Point point, int[][] matrix, int piece) {
		
		ArrayList<Point> valid = new ArrayList<Point>();
		
		if(piece > 6){				//Black
			
			//If nothing in front of pawn
			if(matrix[point.x][point.y+1] == 0){

				//move one ahead
				valid.add(new Point(point.x,point.y+1));
				
				//If first move and nothing on tile
				if(point.y == 1 
						&& matrix[point.x][point.y+1] == 0 
						&& matrix[point.x][point.y+2] == 0)
					
					valid.add(new Point(point.x,3));
			}
			
			//down left
			if(point.x > 0 && matrix[point.x-1][point.y+1] == 1)
				valid.add(new Point(point.x-1,point.y+1));
			
			//down right
			if(point.x < 7 && matrix[point.x+1][point.y+1] == 1)
				valid.add(new Point(point.x+1,point.y+1));
			
	
			
		} else {				//White
			
			//If nothing in front of pawn
			if(matrix[point.x][point.y-1] == 0){
				
				//move one ahead
				valid.add(new Point(point.x,point.y-1));
				
				//If first move and nothing on tile
				if(point.y == 6 
						&& matrix[point.x][point.y-1] == 0
						&& matrix[point.x][point.y-1] == 0)
					
					valid.add(new Point(point.x,4));
			}
			
			//up left
			if(point.x > 0 && matrix[point.x-1][point.y-1] == 1)
				valid.add(new Point(point.x-1,point.y-1));
			
			//up right
			if(point.x < 7 && matrix[point.x+1][point.y-1] == 1)
				valid.add(new Point(point.x+1,point.y-1));
			
		}
		
		return valid;
	}

}
