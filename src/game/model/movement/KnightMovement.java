package game.model.movement;

import java.awt.Point;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * A strategy class for the Chess knight piece.
 * 
 * @author Andreas Nylund
 * @author Hanna Granholm
 *
 */

public class KnightMovement implements MoveStrategy {

	@Override
	public ArrayList<Point> getValidMovement(Point point, int[][] matrix,
			int piece) {

		ArrayList<Point> valid = new ArrayList<Point>();
		
		
		valid.add(new Point(point.x-2, point.y-1));
		valid.add(new Point(point.x-2, point.y+1));
		valid.add(new Point(point.x-1, point.y+2));
		valid.add(new Point(point.x+1, point.y+2));
		valid.add(new Point(point.x+2, point.y+1));
		valid.add(new Point(point.x+2, point.y-1));
		valid.add(new Point(point.x+1, point.y-2));
		valid.add(new Point(point.x-1, point.y-2));
		
		for(ListIterator<Point> it = valid.listIterator();it.hasNext();){
			Point p = it.next();
			if( !( p.x >= 0 && p.y >= 0	&& p.x <= 7 && p.y <= 7 
					&& matrix[p.x][p.y] != -1) )
				it.remove();
		}
		
		
		return valid;
	}


}
