/**
 * Contains classes to create the groups of cards in the game, except players' hands.
 */
package groupOfCards;

import java.util.*;

import mainPackage.Card;
import mainPackage.Game;
import players.Player;
//import mainPackage.Game;

/**
 * Defines a deck, the group of cards from where a player takes a card where he cannot plays one from his hand.
 * @author Benjamin Héroult
 *
 */
public class Deck {
	//------------------singleton-------------------------
	private static Deck instance = null;
		
	/**
	 * static method called instead of the constructor, to assure that the class Deck is instantiated only once.
	 * @return the value of the variable that allow to keep in memory the number of class' instances
	 */
	public final static Deck getInstance() {
		if (Deck.instance == null) {
			synchronized(Deck.class) {
				if (Deck.instance == null) {
					Deck.instance = new Deck();
				}
	        }
		}
		return Deck.instance;
	}
	
	//Attributes
	public List <Card> cards = new ArrayList <Card> ();
	
	//Constructor
	/**
	 * Instantiates a new deck. 
	 * Nothing specific.
	 */
	protected Deck(){
		
	}
	
	//Methods
	/**
	 * Gives a card from the deck to the player, when distributing or when a player has to pick one.
	 * Also control the fact that there is still at least 5 cards in the deck (takes some from the pile to
	 * @param player
	 * the player concerned
	 */
	public void giveCardToPLayer (Player player) {
		player.addCardToHand(this.cards.get(0));
		this.cards.remove(0);
		
		//re-attributes some cards if there is not enough 
		if(this.cards.size() < 5) {
			for (int i=1 ; i<Game.getPile().cards.size() ; i++) {
				this.cards.add(Game.getPile().cards.get(i));
				Game.getPile().cards.remove(i);
			}
		}
	}
	
	/**
	 * Creates the deck with all the cards.
	 * @param nb_jeux
	 * number of decks (1 or 2), so 52 or 104 cards
	 */
	public void createCards(int nb_jeux) {
		String value;
		String color;
		
		for (int n=1 ; n<=nb_jeux ; n++) {
			for (int i=0 ; i<4 ; i++){
				color = Card.COLORS[i];
				for (int j=0 ; j<13 ; j++){
					value = Card.VALUES[j];
					this.cards.add(new Card(value, color));
				}
			}
		}
	}
}
