package players;

import mainPackage.Card;
import mainPackage.Game;

/**
 * Lists the methods that needs to be implemented to apply effects on some cards.
 * Implemented in the class Player.
 * @author Benjamin Héroult
 * @see Player
 */
public interface Effect {	
	/**
	 * Allows the player to choose a new Color
	 */
	public void changeColor();	//1
	/**
	 * Allows the player to play ones again
	 */
	public void replay();	//2
	/**
	 * Makes the next player pass his turn
	 */
	public void nextPlayerPass();	//3
	/**
	 * Executes the fact of changing the direction of the game
	 */
	public void changeDirection();	//4
	/**
	 * Affects a penalty to the next player, by adding 1 to his count of cards to take
	 * @param game
	 * used to call the method changePlayerTemp
	 */
	public void draw1Card(Game game);	//5
	/**
	 * Affects a penalty to the next player, by adding 3 to his count of cards to take
	 * @param game
	 * used to call the method changePlayerTemp
	 */
	public void draw3Card(Game game);	//6
	/**
	 * Avoids the player to pick cards as penalties from other players.
	 */
	public void stopAttacks();	//7
    
	/**
	 * Verifies if the card has an effect affected, and if so, execute this effect.
	 * @param card_to_play
	 * the card that might have an effect
	 * @param game
	 * used to call effects, mainly to defines the next player
	 * @return
	 * if the player finished his turn, true ; false if he must replay
	 */
	public boolean executeEffect(Card card_to_play, Game game);
	/**
	 * Uses the function nextPlayer(int playerPos, Player currentPlayer) from the class Round to add cards to pick for the next player
	 * Used with the functions drawCard.
	 * @param number
	 * the number of cards to add to pick to the next player
	 * @see mainPakage.Round.nextPlayer(int playerPos, Player currentPlayer)
	 */
	public void changePlayerTemp(int number);
}
