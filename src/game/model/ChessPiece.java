package game.model;

/**
 * A class defining the different chess pieces and their corresponding integer and string.
 * 
 * @author Andreas Nylund
 * @author Hanna Granholm
 *
 */

public abstract class ChessPiece {

	public static final int 					NO_PIECE = 0,
												WHITE_PAWN = 1,
												WHITE_ROOK = 2,
												WHITE_KNIGHT = 3,
												WHITE_BISHOP = 4,
												WHITE_QUEEN = 5,
												WHITE_KING = 6,
												BLACK_PAWN = 7,
												BLACK_ROOK = 8,
												BLACK_KNIGHT = 9,
												BLACK_BISHOP = 10,
												BLACK_QUEEN = 11,
												BLACK_KING = 12;
	
	public static final String[] pieceTab = {	"No piece",
												"White, pawn",
												"White, rook",
												"White, knight",
												"White, bishop",
												"White, queen",
												"White, king",
												"Black, pawn",
												"Black, rook",
												"Black, knight",
												"Black, bishop",
												"Black, queen",
												"Black, king"	};
	
	/**
	 * A method for identifying the color of a piece.
	 * 
	 * @param piece Piece to be identified.
	 * @return Color of specified piece.
	 */
	public static int getColor(int piece){
		if(piece > 6)
			return ChessLogic.BLACK;
		else if(piece > 0)
			return ChessLogic.WHITE;
		else
			return -1;
	}
	
}
