package InterfaceUser;
import players.Player;

/**
 * Lists the functions needed for interactions between the application and the user to play the game. 
 * Implemented in the class Player.
 * @author Benjamin Héroult
 * @see Player
 */
public interface PlayerPlay {
	/**
	 * Makes the player say "CARD".
	 */
	public void sayCard();
	/**
	 * Makes theplayer say "AGAINST CARD".
	 */
	public void sayAgainstCard();
	/**
	 * Indicates to the player that it made a mistake.
	 */
	public void falt();
	
	/**
	 * Indicates the number of cards still in the player's hand.
	 */
	public void getCardsInHand();
	/**
	 * Indicates the cards still in the player's hand.
	 */
	public void seeHand();
	
	/**
	 * Indicates to the player that the entry is incorrect.
	 * @param arg
	 * the type of error (simple wrong entry as 1 or wrong card played as 2)
	 */
	public void errorScan(int arg);
	
	/**
	 * Asks the player witch card it wants to play among those in hand.
	 * @return
	 * the number of the card to play (substract 1 to use it in a card's collection)
	 */
	public int askCard();
	/**
	 * Asks the player if he wants to szy "CARD".
	 * @return
	 * the answer : 0 for no, 1 for yes
	 */
	public int askSay();
	/**
	 * Asks the player if it wants to use his card to stop the attack currently directed toward him.
	 * @return
	 * the answer : 0 for no, 1 for yes
	 */
	public int askStopAttack();
	
	/**
	 * Indicates that the player is taking a card from the deck.
	 */
	public void playerTakesCard();
}
