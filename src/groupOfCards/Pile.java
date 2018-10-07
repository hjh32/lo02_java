package groupOfCards;
import Graphic.MyWindow;
import mainPackage.Card;

/**
 * Defines a pile, where a player lays the card he wants to play.
 * It owns his own property to keep in memory the value of the card at the top, and the current color, that can be different from the current card.
 * @author Benjamin Héroult
 *
 */
public class Pile extends Deck{
	//------------------singleton-------------------------
	private static Pile instance = null;
			
	/**
	 * static method called instead of the constructor, to assure that the class Pile is instantiated only once.
	 * @return the value of the variable that allow to keep in memory the number of class' instances
	 */
	public final static Pile getInst() {
		if (Pile.instance == null) {
			synchronized(Pile.class) {
				if (Pile.instance == null) {
					Pile.instance = new Pile();
				}
			}
		}
		return Pile.instance;
	}
	
	//Attributes
	/**
	 * Color used as reference when playing a card
	 */
	private String currentColor;
	/**
	 * Value used as reference when playing a card
	 */
	private String currentValue;
	
	/**
	 * Creates an instance of the class and calls the attributes from the parent's class Deck.
	 */
	private Pile(){
		super();
	}
	
	/**
	 * Adds a card to the pile, and puts it on the top. Changes the current color and the current value of the game.
	 * @param cardToAdd
	 * the card to add to the pile
	 */
	public void addCard(Card cardToAdd) {
		this.cards.add(0, cardToAdd);
		this.currentColor = cardToAdd.getColor();
		this.currentValue = cardToAdd.getValue();
		MyWindow.setPile(this.currentValue, this.currentColor);
	}
	
	/**
	 * Changes the current color of the game.
	 * @param color
	 * new color wanted
	 */
	public void setCurrentColor(String color) {
		this.currentColor = color;
	}
	/**
	 * Provides an access to the current color of the game.
	 * @return the current color of the game
	 */
	public String getCurrentColor() {
		return this.currentColor;
	}
	/**
	 * Changes the current value of the game.
	 * @param value
	 * new value wanted
	 */
	public void setCurrentValue(String value) {
		this.currentValue = value;
	}
	/**
	 * Provides an access to the current value of the game.
	 * @return
	 * the current value of the name
	 */
	public String getCurrentValue() {
		return this.currentValue;
	}
}
