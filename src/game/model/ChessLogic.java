package game.model;

import game.model.AI.AI;
import game.model.AI.ComputerMove;
import game.model.movement.*;
import game.model.score.Player;
import game.model.score.ScoreBoard;

import java.awt.Point;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Observable;

/**
 * A class defining the logic around the chess game and different moves.
 * 
 * @author      Andreas Nylund <nylun@kth.se>
 * @author		Hanna Granholm <hangra@kth.se>
 * @version     1.0                 
 * @since       2013-02-25      
 */

public class ChessLogic extends Observable{

	private ScoreBoard scoreBoard;
	private ChessBoard board;
	private ArrayList<Integer> rubbish;
	private Player[] currentPlayers;
	private int versus;
	private int currentTurn;
	private int check = -1;
	private AI ai;

	public static final int	WHITE = 0,
					  		BLACK = 1;
	
	public static final int VS_COMPUTER = 0,
							VS_HUMAN = 1;
	public static final String[] colorTab = {"White", "Black"};
	
	/**
	 *  Chess Logic constructor.
	 *  
	 * @param scoreBoard The Score Board.
	 * @param board The Chess Board.
	 */
	public ChessLogic(ScoreBoard scoreBoard, ChessBoard board){
		this.scoreBoard = scoreBoard;
		this.board = board;

	}
	
/**
 * Method defining the start of a new game.
 * <p>
 * Inputs can be two players, and if the second player is chosen as computer
 * the game will feature an AI making the Black moves.
 * 
 * @param p1 Player playing white.
 * @param p2 Player playing black.
 */
	public void startNewGame(Player p1, Player p2){
			
		if(p2.isComputer()){		
			versus = VS_COMPUTER;
			ai = new AI(this);
		}else 
			versus = VS_HUMAN;
		
		currentPlayers = new Player[2];
		currentPlayers[0] = p1;
		currentPlayers[1] = p2;
		
			check = -1;
			currentTurn = WHITE;
			rubbish = new ArrayList<Integer>();
			board.newSetOfTiles();
			
		}

/**
 * Makes a move for the computer player. Uses AI to get the best possible move. Then 
 * calls the move() method to carry out the move.
 * <p>
 * If none of the moves are valid, the computer surrenders.
 * <p>
 * Checks for check mate at the end of the move.
 * 
 * @throws UnrealPieceException
 */
	private void computerMove() throws UnrealPieceException{
		
		//Get the best possible next move and move
		ArrayList<ComputerMove> bestMove = ai.getBestMove(board, 10, BLACK);
		
		int i = 0;
		while(!move(bestMove.get(i).getStartPoint(), bestMove.get(i).getEndPoint())){
			i++;
			if(i == bestMove.size()){
				check = WHITE;
				break;
			}
		}
		
		//if(checkMate() != -1)
			//gameOver(new Time(0));

	}
	
	/**
	 * A method to check the current turn.
	 * 
	 * @return Returns an integer corresponding to the color that can move now.
	 */
	public int getTurn(){
		return currentTurn;
	}
	
	
	/**
	 * Swaps the current turn, from black to white or vice versa.
	 * If game is being played against a computer player, a call is made to computerMove().
	 * 
	 * @throws UnrealPieceException 
	 * 
	 */
	private void nextTurn() throws UnrealPieceException{
		
		if(currentTurn == WHITE){
			if(versus == VS_COMPUTER){
				currentTurn = BLACK;	
				computerMove();
			} else
				currentTurn = BLACK;	
		}
		else
			currentTurn = WHITE;
	}
	
	/**
	 * The method for carrying out a Chess move.
	 * <p>
	 * Makes checks for current turn and possible moves.
	 * Validates the move by calling the validateMove() method.
	 * <p>
	 * If the move is made to kill the king, check mate is set.
	 * Pawns that have crossed the board are turned into queens.
	 * <p>
	 * Adds killed pieces to the rubbish and calls nextTurn().
	 * 
	 * @param startPoint Where the move is made from.
	 * @param endPoint To where the move is made.
	 * @throws UnrealPieceException Thrown when the moved piece is not a registered piece.
	 */
	public boolean move(Point startPoint, Point endPoint) throws UnrealPieceException{
		
		//Get current piece
		int piece = board.getPiece(startPoint);
		
		//If piece is not a real piece
		if(piece < 0 || piece > 12)
			throw new UnrealPieceException();
		
		//Only currentTurn can move
		if(ChessPiece.getColor(piece) != currentTurn){
			System.out.println(piece);
			System.out.println("Not your piece!");
			return false;
		}
			
		
		//If move is allowed, and not check mate, move piece.
		if(validateMove(startPoint, endPoint, piece)){
			
			if(board.getPiece(endPoint) == ChessPiece.BLACK_KING){
				//White victory
				check = WHITE;
			} else if(board.getPiece(endPoint) == ChessPiece.WHITE_KING){
				//Black victory
				check = BLACK;
			} else if(piece == 1 && endPoint.y == 0){
				board.setTile(endPoint, ChessPiece.WHITE_QUEEN);
				
			} else if(piece == 7 && endPoint.y == 7) {
				board.setTile(endPoint, ChessPiece.BLACK_QUEEN);
				
			} else {
				board.setTile(endPoint, piece);
			}
			
			//Add piece to trash and change turn
			rubbish.add(board.getPiece(startPoint));
			board.setTile(startPoint, ChessPiece.NO_PIECE);
			nextTurn();
			
		} else {
			System.out.println("Illegal move!");
			return false;
		}
		
		
		return true;
	}
	
	/**
	 * A method for validating moves.
	 * <p>
	 * Matches the move to a list of valid moves for the specified piece and movement.
	 * 
	 * 
	 * @param startPoint The start tile.
	 * @param endPoint The end tile.
	 * @param piece The piece that want to move.
	 * @return Whether the move is valid or not.
	 * @throws UnrealPieceException Thrown when the moved piece is not a registered piece
	 */
	private boolean validateMove(Point startPoint, Point endPoint, int piece) throws UnrealPieceException{

		if(piece < 0 || piece > 12)
			throw new UnrealPieceException();
			
		MoveStrategy ms = getStrategy(piece);
		
		int[][] colorMatrix = board.getColorMatrix(ChessPiece.getColor(piece));

		ArrayList<Point> validMove = ms.getValidMovement(startPoint, colorMatrix, piece);
		
		
		if(piece == 12 || piece == 6){		//Stop king placing himself in check
			
			for(int x=0;x<8;x++){
				for(int y=0;y<8;y++){
					
					if(colorMatrix[x][y] == 1){
						MoveStrategy possiblePiece = getStrategy(board.getPiece(new Point(x,y)));
						ArrayList<Point> possibleMoves = possiblePiece.getValidMovement(new Point(x,y), 
								board.getColorMatrix(board.getPiece(new Point(x,y))), 
								board.getPiece(new Point(x,y)));
						
						if(possibleMoves.contains(endPoint)){
							System.out.println("Can't put the king in Check!");
							validMove.clear();
							break;
						}
					}
					
				}
			}
			
		}
		
		return validMove.contains(endPoint);
	}
	
	public String printBoard(){
		return board.toString();
	}
	
	/**
	 * A method for matching pieces with movement strategies.
	 * 
	 * @param piece
	 * @return A new instance of the corresponding movestrategy
	 */
	public MoveStrategy getStrategy(int piece){
		
		switch(piece){
		case 1:
			return new PawnMovement();
		case 2:
			return new RookMovement();
		case 3:
			return new KnightMovement();
		case 4:
			return new BishopMovement();
		case 5:
			return new QueenMovement();
		case 6:
			return new KingMovement();
		case 7:
			return new PawnMovement();
		case 8:
			return new RookMovement();
		case 9:
			return new KnightMovement();
		case 10:
			return new BishopMovement();
		case 11:
			return new QueenMovement();
		case 12:
			return new KingMovement();
		default: 
			return null;
		}
	}
	
	/**
	 * A method to get who put the other player in check mate.
	 * 
	 * @return Identity of person having checkmated the other
	 */
	public int checkMate(){
		return check;
	}

	/**
	 * Ends the game, updates score and clears the chess board.
	 * 
	 */
	public void gameOver(Time time) {
		
		//Update score
		if(checkMate() == BLACK)
			scoreBoard.updateScoreBoard(currentPlayers[BLACK], currentPlayers[WHITE], time);
		else
			scoreBoard.updateScoreBoard(currentPlayers[WHITE], currentPlayers[BLACK], time);
		
		//Clear board
		board.clear();
		
	}


	

	
}
