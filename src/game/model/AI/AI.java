package game.model.AI;

import game.model.ChessBoard;
import game.model.ChessLogic;
import game.model.ChessPiece;
import game.model.movement.MoveStrategy;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * An artificial intelligence class for playing Chess.
 * 
 * @author Andreas Nylund
 * @author Hanna Granholm
 *
 */
public class AI {
	private ChessLogic model;
	
	/**
	 * 
	 * @param model
	 */
	public AI(ChessLogic model){
		this.model = model;
	}
	
	/**
	 * Method for selecting the best possible move for the AI.
	 * <p>
	 * This method is recursive, each following move being another call to this same method.
	 * Consecutive recursive calls will only modify the tactical value of a move.
	 * Every other recursive call will be to calculate a White move, and will give a negative
	 * tactical value.
	 * 
	 * @param board	A Chess Board to calculate from.
	 * @param noOfMoves Number of moves to calculate.
	 * @param color Color to be calculated.
	 * @return A list of all possible moves sorted by tactical value.
	 */
	public ArrayList<ComputerMove> getBestMove(ChessBoard board, int noOfMoves, int color){
			
			//Create a list of possible moves, 
			ArrayList<ComputerMove> moves = new ArrayList<ComputerMove>();
			int[][] colorMatrix = board.getColorMatrix(color);
			
			for(int x=0;x<8;x++){
				for(int y=0;y<8;y++){
					if(colorMatrix[x][y] == -1){
						
						Point startPoint = new Point(x, y);
						int piece = board.getPiece(startPoint);
						MoveStrategy ms = model.getStrategy(board.getPiece(startPoint));
						
						//Get all possible moves for this piece
						ArrayList<Point> endPoints = 
								ms.getValidMovement(startPoint,	colorMatrix, piece);
						
						for(Iterator<Point> it = endPoints.iterator();it.hasNext();){
							Point endPoint = it.next();
							int endPiece = board.getPiece(endPoint);
							//System.out.println(endPiece);
							ComputerMove newMove = new ComputerMove(startPoint, endPoint, endPiece);
							moves.add(newMove);
						}
						
					}
				}
			}
			int nextColor;
			if(color == ChessLogic.BLACK)
						nextColor = ChessLogic.WHITE;
			else
						nextColor = ChessLogic.BLACK;
			
			//For every move, add value of further moves
			if(noOfMoves > 0){
				for(Iterator<ComputerMove> it = moves.iterator();it.hasNext();){
					ChessBoard currentBoard = new ChessBoard(board);
					ComputerMove cm = it.next();
					
					//If pawn has crossed the board
						if(currentBoard.getPiece(cm.getStartPoint()) == ChessPiece.BLACK_PAWN 
								&& cm.getEndPoint().y == 7)
							
							currentBoard.setTile(cm.getEndPoint(), 
												ChessPiece.BLACK_QUEEN);
						
						else if(currentBoard.getPiece(cm.getStartPoint()) == ChessPiece.WHITE_PAWN 
								&& cm.getEndPoint().y == 0)
							
							currentBoard.setTile(cm.getEndPoint(), 
												ChessPiece.WHITE_QUEEN);
							
						else
							currentBoard.setTile(cm.getEndPoint(), 
												currentBoard.getPiece(cm.getStartPoint()));
						
					ArrayList<ComputerMove> bestMove = getBestMove(currentBoard, --noOfMoves, nextColor);
					cm.addValue(bestMove.get(0).getValue());
				}
				
			}
			
			//Sort moves according to value
			if(color == ChessLogic.BLACK)
				Collections.sort(moves, new BlackComparator());
			else 
				Collections.sort(moves, new WhiteComparator());
	
			return moves;
		}

}
