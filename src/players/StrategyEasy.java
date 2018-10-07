package players;

import java.util.*;

import mainPackage.Card;
import mainPackage.Game;

/**
 * Defines a easy strategy, when physical player wants to play with less difficulty.
 * @author Junhao Hu
 * 
 */
public class StrategyEasy implements Strategy{
	/**
	 * Provides a method，looking for the cards this AI can play from his hand，once he finds the card, he plays it.
	 * The card that AI plays is  allowed by the rules
	 * @param game
	 */
	public void strategy(Game game) {
	boolean cardPlayed = false;
	boolean found = false;
	Card currentCard = null;
	
	while (cardPlayed == false) {
	        Player currentPlayer = Game.round.getCurrentPlayer();
	        if(currentPlayer instanceof AI){
	        AI currentPlayer1=(AI)currentPlayer;	
	        List <Card> handCard=currentPlayer1.getHand();
			Iterator <Card> hand = handCard.iterator();
	        while (hand.hasNext() && found == false) {	
				currentCard = hand.next();
				if((currentCard.getColor() == Game.getPile().getCurrentColor()) || (currentCard.getValue() == Game.getPile().getCurrentValue())) {
					found = true;
				}
			
			}
			if(found == false) { 	
				currentPlayer1.playerTakesCard();
				currentPlayer1.takeCard();
			}
			else {
				cardPlayed = true;
				currentPlayer1.playCard(currentCard, game);
			}
	        }
	  }
	
		
	}

}
