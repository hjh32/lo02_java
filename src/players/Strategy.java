package players;

import mainPackage.Game;
import players.StrategyEasy;
import players.StrategyHard;

/**
 * Lists the method which needs to be implemented for AIs using one of the strategies.
 * Implemented in the classes StrategyEasy and StrategyHard.
 * @author Junhao Hu
 * @see StrategyEasy 
 * @see StrategyHard
 * 
 */
public interface Strategy {
	public void strategy(Game game);
}
