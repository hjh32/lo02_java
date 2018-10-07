package InterfaceUser;

import java.util.Scanner;
import mainPackage.Game;

/**
 * Lists the functions needed for interactions between the application and the user to initialize the game. 
 * Implemented in the class Game.
 * @author Benjamin Héroult
 * @see Game
 */
public interface InitGame {
	/**
	 * Asks to the user his username
	 * @param scan
	 * avoids to restart a new scan
	 * @return
	 * the name entered by the user to define his username
	 */
	public String initPlayer(Scanner scan);
	/**
	 * Asks the number of players desired. 
	 * Verifies if the entry is a number and in the right interval (1 to 6) .
	 * @param scan
	 * avoids to restart a new scan
	 * @return
	 * the number of players chosen
	 */
	public int choosePlayers(Scanner scan);
	/**
	 * Asks the number of decks desired. 
	 * Verifies if the entry is a number and in the right interval (1 or 2).
	 * @param scan
	 * avoids to restart a new scan
	 * @return
	 * the number of decks chosen
	 */
	public int initCards(Scanner scan);
	/**
	 * Asks the rule that the game musts use, and call the method to define it. 
	 * Verifies if the entry is a number and in the right interval (1 or 2).
	 * @param scan
	 * avoids to restart a new scan
	 */
	public void chooseRule(Scanner scan);
	/**
	 * Displays the final ranking, at the end of the game.
	 */
	public void displayRanking();
	/**
	 * Informs the user of the beginning of the game.
	 */
	public void beginGame();
	
	/**
	 * Uses the attribute players_name to displays all players in the game.
	 */
	public void seePlayers();
	
	/**
	 * Asks the difficulty level wanted by the user.
	 * @param scan
	 * avoids to restart a new scan
	 * @return
	 * the number representing the difficulty level
	 */
	public int difficulty(Scanner scan);
}
