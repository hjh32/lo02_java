package mainPackage;

/*import java.util.*;

import groupOfCards.Pile;
import players.AI;
import players.PhysicalPlayer;
import players.Player;*/

/**
 * Defines a card, its attributes and the method to access it.
 * @author Benjamin Héroult
 *
 */
public class Card {
	//Attributes
    public final static String[] COLORS = {"Pic", "Coeur", "Carreau", "Trefle"};	//used in
    public final static String[] VALUES = {"Deux", "Trois", "Quatre", "Cinq", "Six", "Sept", "Huit", "Neuf", "Dix", "Valet", "Dame", "Roi", "As"};	//same as previous
    private String color;
    private String value;
    
    //Constructor
    /**
     * Instantiates a new card by defining its color and value.
     * @param value
     * value of the card
     * @param color
     * color of the card
     */
    public Card (String value, String color) {
    	this.color = color;
    	this.value = value;
    }

    //Methods
    /**
     * Give access to card's color
     * @return the color of the card
     */
    public String getColor() {
	return this.color;
    }

    /**
     * Give access to card's value
     * @return the value of the card
     */
    public String getValue() {
	return this.value;
    }

    /**
     * Defines what will be showed when the card is printed in a console. 
     * Automatically called
     */
    public String toString() {
    	StringBuffer sb = new StringBuffer();
    	sb.append(this.value);
    	sb.append(" de ");
    	sb.append(this.color);
    	return sb.toString();
    }	
}
