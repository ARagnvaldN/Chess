package game.model.AI;

import java.util.Comparator;

/**
 * A class for comparing values of white moves, lower the better.
 * 
 * @author Andreas Nylund
 * @author Hanna Granholm
 *
 */

public class WhiteComparator implements Comparator<ComputerMove> {

	@Override
	public int compare(ComputerMove move1, ComputerMove move2) {
		if(move1.getValue() > move2.getValue())
			return 1;
		else if(move1.getValue() < move2.getValue())
			return -1;
		else
			return 0;
	}

}
