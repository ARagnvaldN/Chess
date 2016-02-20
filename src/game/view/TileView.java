package game.view;

import java.awt.Color;
import java.awt.Point;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * A class describing a Chess tile visually.
 * 
 * @author Andreas Nylund
 * @author Hanna Granholm
 *
 */

@SuppressWarnings("serial")
public class TileView extends JButton{
	
	private Point position;
	private Icon[] images;
	
	/**
	 * Constructor for a new tile. 
	 * Creates it at the specified coordinates and coloring every other tile brown and tan.
	 * All images of pieces are loaded.
	 * 
	 * @param p The coordinate of the tile
	 * @param color Number of tiles created before this one
	 */
	public TileView(Point p, int color){
		
		position = p;
		if(color % 2 == 0)
			setBackground(new Color(238,154,73));
		else 
			setBackground(new Color(210,105,30));
		images = getIcons();
		setIcon(images[0]);
	}
	
	/**
	 * Loads all images of pieces into an array.
	 * 
	 * @return An array of all the 12 ChessPieces ordered according to ChessPiece
	 */
	private Icon[] getIcons(){
		
		Icon[] icons = new Icon[13];
			
			ClassLoader cl = this.getClass().getClassLoader();
			URL imageURL;
			ImageIcon icon;
		
			icons[0] = null;
			
			imageURL = cl.getResource("game/resources/w_pawn.png");
			icon = new ImageIcon(imageURL);
			icons[1] = icon;
			
			imageURL = cl.getResource("game/resources/w_rook.png");
			icon = new ImageIcon(imageURL);
			icons[2] = icon;
			
			imageURL = cl.getResource("game/resources/w_knight.png");
			icon = new ImageIcon(imageURL);
			icons[3] = icon;
			
			imageURL = cl.getResource("game/resources/w_bishop.png");
			icon = new ImageIcon(imageURL);
			icons[4] = icon;
			
			imageURL = cl.getResource("game/resources/w_queen.png");
			icon = new ImageIcon(imageURL);
			icons[5] = icon;
			
			imageURL = cl.getResource("game/resources/w_king.png");
			icon = new ImageIcon(imageURL);
			icons[6] = icon;
			
			imageURL = cl.getResource("game/resources/b_pawn.png");
			icon = new ImageIcon(imageURL);
			icons[7] = icon;
			
			imageURL = cl.getResource("game/resources/b_rook.png");
			icon = new ImageIcon(imageURL);
			icons[8] = icon;
			
			imageURL = cl.getResource("game/resources/b_knight.png");
			icon = new ImageIcon(imageURL);
			icons[9] = icon;
			
			imageURL = cl.getResource("game/resources/b_bishop.png");
			icon = new ImageIcon(imageURL);
			icons[10] = icon;
			
			imageURL = cl.getResource("game/resources/b_queen.png");
			icon = new ImageIcon(imageURL);
			icons[11] = icon;
			
			imageURL = cl.getResource("game/resources/b_king.png");
			icon = new ImageIcon(imageURL);
			icons[12] = icon;

		
		return icons;
	}
	
	/**
	 * Returns the coordinate of this tile.
	 * 
	 * @return Point containing coordinates of this tile
	 */
	public Point getPoint(){
		return position;
	}
	
	/**
	 * Sets the image of the tile to the specified piece.
	 * 
	 * @param piece Piece to be shown at this tile
	 */
	public void setPiece(int piece){
		setIcon(images[piece]);
	}
	
}
