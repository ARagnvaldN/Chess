package game.model.score;

import java.util.Comparator;

/**
 * A class for comparing Chess Players
 * 
 * @author Andreas Nylund
 * @author Hanna Granholm
 *
 */

public class PlayerComparator implements Comparator<Player> {

	@Override
	public int compare(Player p1, Player p2) {
		if(p1.getRating() < p2.getRating())
			return 1;
		else if(p1.getRating() > p2.getRating())
			return -1;
		else
			return 0;
	}

}
