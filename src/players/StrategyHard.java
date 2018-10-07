package players;

import java.util.*;


import mainPackage.Card;
import mainPackage.Game;

/**
 * Defines a hard strategy, when physical player wants to play with more difficult.
 * @author Junhao Hu
 *
 */
public class StrategyHard implements Strategy{
	/**
	 * Provide a method，looking for the cards.
     * AI first try if he can play without using the cards with effects
     * If can't, AI choose the cards with effects to play
     * The card that AI plays is  allowed by the rules
	 * @param game
	 * 
	 */
	public void strategy(Game game) {
		AI currentPlayer=(AI) Game.round.getCurrentPlayer();
		List <Card> handCard=currentPlayer.getHand();
		boolean cardPlayed = false;

		while(cardPlayed ==false) {

			List <Card> cardOK = new ArrayList <Card> ();
			boolean hasEffect=false;
			Card currentCard = null;
			int i=0;	

			while(i<handCard.size()) {
				currentCard = handCard.get(i);
				if((currentCard.getColor() == Game.getPile().getCurrentColor()) || (currentCard.getValue() == Game.getPile().getCurrentValue())) {
					cardOK.add(currentCard);
				}
				i++;	
			}

			if(cardOK.size()==0)
			{
				currentPlayer.playerTakesCard();
				currentPlayer.takeCard();
			}

			else {	
				for(int j=0;j<cardOK.size();j++) {
					Card theCard=(Card)cardOK.get(j);
					if(game.effectsActives.containsKey(theCard.getValue())) {
						hasEffect=true;
					}
					if(hasEffect==false) {
						cardPlayed=true;
						currentPlayer.playCard(theCard, game);
						break;
					}         
					if(j==cardOK.size()-1){
						Card theCard1=(Card)cardOK.get(0);
						cardPlayed=true;
						currentPlayer.playCard(theCard1, game);
					}
				}
			}
		}	
	}
}
