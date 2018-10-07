package players;

import java.util.*;

import Graphic.MyWindow;
import mainPackage.Card;
import mainPackage.Game;
import mainPackage.Rule;

import players.Player;

/**
 * Defines the user as a player, and how he can play through the application and the interface.
 * Also defines the effect of changing color during the game.
 * Inherits from the class Player.
 * @author Benjamin Héroult
 * @see Player
 * 
 */
public class PhysicalPlayer extends Player {
	//------------------singleton-------------------------
	private static PhysicalPlayer instance = null;
		
	/**
	 * static method called instead of the constructor, to assure that the class PhysicalPlayer is instantiated only once.
	 * @param name
	 * name entered by the user for his player
	 * @return the value of the variable that allow to keep in memory the number of class' instances
	 */
	public final static PhysicalPlayer getInstance(String name) {
		if (PhysicalPlayer.instance == null) {
	    	synchronized(PhysicalPlayer.class) {
	        	if (PhysicalPlayer.instance == null) {
	        		PhysicalPlayer.instance = new PhysicalPlayer(name);
	            }
	    	}
		}
		return PhysicalPlayer.instance;
	}
	
	//Constructor
	/**
	 * Creates an instantiation of the class, and initiates its attributes
	 * @param name
	 * name entered by the user for his player
	 */
	private PhysicalPlayer (String name) {
		super();
		this.name = name;
		this.cardToTake = 0;
	}
	
	//Methods
	/**
	 * Allows the physical player to play a card, and calls the method executeEffect to execute the effect possibly associated.
	 * @param card_to_play
	 * the card the player wants to play
	 * @param game
	 * used to call the method executeEffect
	 * @return
	 * true if the player played successfully the card, false if and error occurred or if the player has to replay
	 */
	public boolean playCard(Card card_to_play, Game game) {		
		if((card_to_play.getColor() == Game.getPile().getCurrentColor()) || (card_to_play.getValue() == Game.getPile().getCurrentValue())) {	//card played
			Game.pile.addCard(card_to_play);
			this.hand.remove(card_to_play);
			return this.executeEffect(card_to_play, game);	//true if played and finished, false if played but has to replay
		}
		else {	//card cannot be played
			this.errorScan(2);
			return false;
		}
	}
	
	/**
	 * Makes the physical player pick cards as penalty if necessary, makes it choose witch card to play and if it wants to say card.
	 * @param game
	 * used to call the method playCard
	 */
	public void play(Game game) {
		boolean cardPlayed = false;
		boolean answered = false;
		
		this.seeHand();
		MyWindow.displayUserHand(this);
		
		// control if the player has to pick cards as penalty
		if(this.cardToTake != 0) {	//if penalty
			if (Rule.getCardStopAttack() != null) {	//sees if a card to stop attacks exists, and if so offers to play this card
				Iterator<Card> it = this.hand.iterator();
				boolean found = false;
				Card currentCard = null;
				while (it.hasNext() && found == false) { // check the hand
					if (currentCard.getValue() == Rule.getCardStopAttack()) {
						int answer = this.askStopAttack();
						if (answer == 1) {
							cardPlayed = this.playCard(currentCard, game);
							this.cardToTake = 0;
						}
						found = true;
					}
				}
			}
			for (int i=0 ; i<this.cardToTake ; i++) {	//makes pick the cards needed for penalty
				this.playerTakesCard();
				this.takeCard();
				MyWindow.displayUserHand(this);
			}
			this.cardToTake = 0;
			this.seeHand();
		}
		
		while (cardPlayed == false) {	//while the physical player has to play
			int card = this.askCard();
			if(card>=1 && card<=this.hand.size()) {	//try to play a card
				cardPlayed = this.playCard(this.getHand().get(card-1), game);
			}
			else if(card == 0) { //pick a card, so has to replay
				this.takeCard();
				MyWindow.displayUserHand(this);
				this.seeHand();
			}
			else if(card == 66) {	//quit
				this.wantToQuit = true;
				cardPlayed = true;
				answered = true;
			}
			else {
				this.errorScan(1);
			}
		}
		
		//say card
		int size = this.getHand().size();
		while(answered == false && size >= 1) {
			int answer = this.askSay();
			
			if(answer == 0 && size >= 2) {	//doesn't say card and has more than one card
				answered = true;
			}
			else if(answer == 0 && size == 1) {	//forgets to say card
				answered = true;
				this.sayAgainstCard();
				this.falt();
				for (int i=0 ; i<2 ; i++) {
					this.takeCard();
					MyWindow.displayUserHand(this);
				}
			}
			else if(answer == 1 && size == 1) {	//says card and has only one card in hand
				this.sayCard();
				answered = true;
			}
			else if(answer == 1 && size >= 2) {	//says card but has more than one card
				this.sayCard();
				answered = true;
				this.falt();
				for (int i=0 ; i<2 ; i++) {
					this.takeCard();
					MyWindow.displayUserHand(this);
				}
			}
			else {
				this.errorScan(1);
			}
		}
		
		if (this.hand.size() == 0) {	//marks it finished if no cards in hand
			this.finish = true;
		}
	}
		
//--------------------------------------EFFECTS-------------------------------------
	public void changeColor() { 
		System.out.println("Le joueur change de couleur");
		MyWindow.setUserConsole("Le joueur change de couleur");
		boolean colorChosen = false;	//to verify the input
		int color = -1;
		boolean error = false;
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Quelle couleur voulez vous choisir ?" + '\n' + " (1 : Pic, 2 : Coeur, 3 : Carreau ou 4 : Trefle)");
		MyWindow.addInfosUserConsole("Quelle couleur voulez vous choisir ?" + '\n' + " (1 : Pic, 2 : Coeur, 3 : Carreau ou 4 : Trefle)");
			
		while (colorChosen != true) {
			if (Game.playGraphic == true) {
				color = MyWindow.askSomething(4);
			}
			else {
				try {
					color = scan.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Saisie invalide. Réessayez");
					MyWindow.addInfosUserConsole("Saisie invalide. Réessayez");
					scan = new Scanner(System.in);
					error = true;
				}
			}
			if (error == false) {
				switch (color) {
				case 1:
				case 2:
				case 3:
				case 4:
					colorChosen = true;
					Game.getPile().setCurrentColor(Card.COLORS[color-1]);
					System.out.println("La nouvelle couleur est : " + Card.COLORS[color-1]);
					break;
				default:
					System.out.println("Saisie invalide. Réessayez");
					MyWindow.addInfosUserConsole("Saisie invalide. Réessayez");
					break;
				}				
			}
			error = false;
		}
	}
}
