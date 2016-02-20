package game.model;

import java.awt.Point;
import java.util.Observable;

/**
 * A model of a Chess Board. Each of the tiles represented by integers.
 * 
 * @author Andreas Nylund
 * @author Hanna Granholm
 *
 */
public class ChessBoard extends Observable{
	
	private int[][] tiles;
	
	/**
	 * Constructor for the Chess Board.
	 */
	public ChessBoard(){
		tiles = new int[8][8];
	}
	
	/**
	 * Copy Constructor for Chess Board. Returns a copy of the specified Chess Board.
	 * 
	 * @param b A Chess Board
	 */
	public ChessBoard(ChessBoard b){
		tiles = new int[8][8];
		for(int x=0;x<8;x++){
			for(int y=0;y<8;y++){
				tiles[x][y] = b.tiles[x][y];
			}
		}
	}
	
	/**
	 * Resets the Chess Board to starting set-up. 
	 * 
	 */
	public void newSetOfTiles(){
		tiles = new int[8][8];
		
		//ADD ROOKS
		tiles[0][0] = ChessPiece.BLACK_ROOK;
		tiles[7][0] = ChessPiece.BLACK_ROOK;
		tiles[0][7] = ChessPiece.WHITE_ROOK;
		tiles[7][7] = ChessPiece.WHITE_ROOK;
		
		//ADD KNIGHTS
		tiles[1][0] = ChessPiece.BLACK_KNIGHT;
		tiles[6][0] = ChessPiece.BLACK_KNIGHT;
		tiles[1][7] = ChessPiece.WHITE_KNIGHT;
		tiles[6][7] = ChessPiece.WHITE_KNIGHT;
		
		//ADD BISHOPS
		tiles[2][0] = ChessPiece.BLACK_BISHOP;
		tiles[5][0] = ChessPiece.BLACK_BISHOP;
		tiles[2][7] = ChessPiece.WHITE_BISHOP;
		tiles[5][7] = ChessPiece.WHITE_BISHOP;
		
		//ADD QUEENS
		tiles[3][0] = ChessPiece.BLACK_QUEEN;
		tiles[3][7] = ChessPiece.WHITE_QUEEN;
		
		//ADD KINGS
		tiles[4][0] = ChessPiece.BLACK_KING;
		tiles[4][7] = ChessPiece.WHITE_KING;
		
		//ADD PAWNS
		for(int i = 0;i<8;i++){
			tiles[i][1] = ChessPiece.BLACK_PAWN;
			tiles[i][6] = ChessPiece.WHITE_PAWN;
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Sets the Piece value of the specified tile.
	 * 
	 * @param p Point containing coordinates of tile.
	 * @param i Piece to be placed at coordinate.
	 */
	public void setTile(Point p, int i){
		tiles[p.x][p.y] = i;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * A method for retrieveing the current piece on a specified tile.
	 * 
	 * @param point Coordinate of a tile.
	 * @return The piece currently at specified tile.
	 */
	public int getPiece(Point point){
			
			int piece = tiles[point.x][point.y];
			
			return piece;
		}
	
	/**
	 * Returns a compilation of the board as a string.
	 * 
	 * @return The board layout as a String.
	 */
	public String toString(){
		String s = "";
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				s += "[" + ChessPiece.pieceTab[tiles[j][i]] + "]";
			}
			s += "\n";
		}
		return s;
	}
	
	/**
	 * Creates a matrix representation of the board in black and white.
	 * <p>
	 * The returned matrix will display 1 for each piece which color != color
	 * and -1 for each piece which color == color. And 0 for each empty tile.
	 * 
	 * @param color The color of interest.
	 * @return A representation of the board in only colors.
	 */
	public int[][] getColorMatrix(int color){
			
			int[][] matrix = new int[8][8];
			int a, b;
			
			if(color == ChessLogic.BLACK){	//BLACK
				a = -1;
				b = 1;
			} else {						//WHITE
				a = 1;
				b = -1;
			}
			
				for(int i = 0;i<8;i++){
					for(int j=0;j<8;j++){
						if(tiles[i][j] > 6)
							matrix[i][j] = a;
						else if(tiles[i][j] == 0)
							matrix[i][j] = 0;
						else
							matrix[i][j] = b;
					}
					
				}
			
			return matrix;
		}
	
/**
 * Clears the board of all pieces.
 * 
 */
	public void clear() {
		tiles = new int[8][8];
		setChanged();
		notifyObservers();
	}
	

}
