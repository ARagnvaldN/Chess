package game.model.movement;

import java.awt.Point;
import java.util.ArrayList;

/**
 * An abstract strategy class for the Chess pieces that move along lines.
 * 
 * @author Andreas Nylund
 * @author Hanna Granholm
 *
 */

public abstract class LineMover implements MoveStrategy {
	
	protected int[][] matrix;
	protected ArrayList<Point> valid;
	
	@Override
	public abstract ArrayList<Point> getValidMovement(Point point, int[][] matrix,
			int piece);
	
	/**
	 * Returns a valid line of tiles that can be moved along this line.
	 * <p>
	 * For example, the code
	 * <code> getValidLine(tile, 1, 0); </code>
	 * Will add all valid tiles to the right of the specified tile.
	 * 
	 * 
	 * @param point Starting tile.
	 * @param dx Movement along x-axis per iteration.
	 * @param dy Movement along y-axis per iteration.
	 */
	protected void getValidLine(Point point,int dx,int dy){
		
		Point thisPoint = new Point(point.x,point.y);
		
		//Check if point is within board
		if(thisPoint.x+dx >= 0 && thisPoint.y+dy >= 0
					&& thisPoint.x+dx <= 7 && thisPoint.y+dy <= 7) {
			
			thisPoint.x += dx;
			thisPoint.y += dy;
			
			if(matrix[thisPoint.x][thisPoint.y] == 1){
				valid.add(thisPoint);
			}else if(matrix[thisPoint.x][thisPoint.y] == -1){
				return;
			}else{
				valid.add(thisPoint);
				getValidLine(new Point(thisPoint.x, thisPoint.y), dx, dy);
			}
		}
		
	}

}
