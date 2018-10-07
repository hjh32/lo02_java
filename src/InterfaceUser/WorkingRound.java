package InterfaceUser;

import mainPackage.Round;

/**
 * Lists the methods needed to indicate actions to the user in the running of round. 
 * Implemented in the class Round.
 * @author Benjamin Héroult
 * @see Round
 */
public interface WorkingRound {
	/**
	 * Informs the user of the beginning of the game.
	 */
	public void initPlayerRound();
	/**
	 * Informs the user that the current player posed it's last card.
	 */
	public void endPlayer();
	/**
	 * Informs the user that the game is finished.
	 */
	public void endGame();
}
