package players;

import java.util.*;

import Graphic.MyWindow;
import mainPackage.Game;
import mainPackage.Rule;
import mainPackage.Card;

/**
 * Defines other players as AI, how they play and the strategy they use
 * Also defines the effect of changing color during the game.
 * Inherits from the class Player.
 * @author Benjamin Héroult
 * @see Player
 */
public class AI extends Player {
	/**
	 * strategy of the AIs
	 */
	private static Strategy strategy1;
	
	/**
	 * Creates an instance of the class and defines attributes
	 * @param name
	 * name of the AI
	 * @param strategy
	 * strategy chosen by the player (by choosing the difficulty level)
	 */
	public AI (String name, Strategy strategy) {
		super();
		AI.strategy1 = strategy;
		this.name = name;
		this.cardToTake = 0;
	 }
	
	/**
	 * Makes the AI play a card, and calls the method executeEffect to execute the effect possibly associated.
	 * @param card_to_play
	 * the card to play
	 * @param game
	 * used to execute the effects
	 * @return
	 * true if the player played successfully the card, false if and error occurred or if the player has to replay
	 */
	public boolean playCard(Card card_to_play, Game game) {
		Game.getPile().addCard(card_to_play);
		this.hand.remove(card_to_play);	
		return this.executeEffect(card_to_play, game);
	}
	
	/**
	 * Makes the AI pick cards as penalty if necessary, makes it choose witch card to play and if it wants to say card.
	 * @param game
	 * used to call the method playCard
	 */
	public void play(Game game) {
		boolean hasPlayed = false;
		
		// control if the player has to pick cards as penalty
		if(this.cardToTake != 0) {	//if penalty
			if (Rule.getCardStopAttack() != null) {	//if a card that cans stop attack exists, and if so plays this card
				Iterator<Card> it = this.hand.iterator();
				boolean found = false;
				Card currentCard = null;
				while (it.hasNext() && found == false) { // checks the hand
					currentCard = it.next();
					if (currentCard.getValue() == Rule.getCardStopAttack()) {
						this.cardToTake = 0;
						hasPlayed = this.playCard(currentCard, game);
						found = true;
					}
				}
			}
			for (int i=0 ; i<this.cardToTake ; i++) {	//makes pick the cards needed for penalty
				this.playerTakesCard();
				this.takeCard();
			}
			this.cardToTake = 0;
		}
		
		if (hasPlayed == false) {
			AI.strategy1.strategy(game);
		}
		
		if (this.hand.size() == 1) {	//makes it say card if only one card in hand
			this.sayCard();
		}
		else if (this.hand.size() == 0) {	//marks it finished if no cards in hand
			this.finish = true;
		}
	}
	

//----------------------------------EFFETCS----------------------------------------
	public void changeColor() { 
		System.out.println("Le joueur change de couleur");
		MyWindow.addInfosUserConsole("Le joueur change de couleur");
		
		//multiplies and then divides per 10 to increase possibilities, otherwise a to high probability to get always the same
		double var = Math.random() * (4 * 10);
		int color = (int)var/10; 
		Game.getPile().setCurrentColor(Card.COLORS[color]);
		System.out.println("La nouvelle couleur est : " + Card.COLORS[color]);
		MyWindow.addInfosUserConsole("Nouvelle couleur : " + Card.COLORS[color]);
	}
}

