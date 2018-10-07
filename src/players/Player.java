package players;

import java.util.*;

import Graphic.MyWindow;
import InterfaceUser.PlayerPlay;
import mainPackage.Card;
import mainPackage.Game;

/**
 * Abstract class that defines all general characteristics of a player. 
 * This is where the actions of the effects are defined (excepting the change of color) and where the interface for a player is implemented.
 * @author Benjamin Héroult
 *
 */
public abstract class Player implements Effect , PlayerPlay {
	//---------------Attributes------------------
	/**
	 * Name of the player.
	 */
	protected String name;
	/**
	 * List of the cards that the player owns.
	 */
	protected List <Card> hand = new ArrayList <Card> ();
	/**
	 * True if the player played all its cards.
	 */
	protected boolean finish = false;
	/**
	 * Indicates the number of cards the player will probably has to take on its next turn.
	 */
	protected int cardToTake;
	/**
	 * True if the user wants to quit.
	 */
	public boolean wantToQuit = false;
	
	//--------------Methods-----------------------
	/**
	 * Defines how the player's behavior and how he will play. Empty.
	 * @param game
	 * transmission of game's instantiation to use some of it's functions
	 */
	public void play (Game game) {
		
	}
	/**
	 * Makes the player play a card from his hand.
	 * @param card_to_play
	 * the card the player wants to play
	 */
	public void playCard(Card card_to_play) {
		
	}
	/**
	 * Makes the player pick a card from the deck.
	 */
	public void takeCard() {
		Game.getDeck().giveCardToPLayer(Game.getPlayers().get(this.name));
	}
	
	/**
	 * Gives the name of the player.
	 * @return
	 * the player's name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gives the collection of cards representing the player's hand.
	 * @return
	 * the collection of cards for the hand
	 */
	public List <Card> getHand(){
		return this.hand;
	}
	/**
	 * Allows to add a card to the player's hand.
	 * @param card
	 * the card to add
	 */
	public void addCardToHand(Card card) {
		this.hand.add(card);
	}
	/**
	 * Adds a certain number of cards to pick for the player, the next time he will have to play.
	 * Used in cases of penalties.
	 * @param number
	 * the number of cards to pick
	 */
	public void addCardToTake(int number) {
		this.cardToTake += number;
	}
	
	/**
	 * Gives access to the player's status (true if he played all his cards in hand).
	 * @return
	 * the player's status
	 */
	public boolean hasFinished() {
		return this.finish;
	}
	
//-------------------------------------INTERFACE--------------------------------------------------
	public void sayCard() {
		System.out.println("CARTE !");
		MyWindow.setInfos("CARTE !");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MyWindow.setInfos("");
	}
	public void sayAgainstCard() {
		System.out.println("CONTRE-CARTE !");
		MyWindow.setInfos("CONTRE-CARTE !");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MyWindow.setInfos("");
	}
	public void falt() {
		System.out.println("FAUTE ! pioche 2 cartes");
		MyWindow.setInfos("FAUTE ! pioche 2 cartes");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MyWindow.setInfos("");
	}
	
	public void getCardsInHand() {
		System.out.println("Il reste " + this.getHand().size() + " carte(s) au joueur");
	}
	public void seeHand() {
		System.out.println("Cartes : " + this.getHand());
	}
	
	public void errorScan(int arg) {
		switch(arg) {
		case 1 :
			System.out.println("Saisie invalide. Veuillez recommencer");
			break;
		case 2 :
			System.out.println("Vous ne pouvez pas jouer cette carte. Veuillez réessayer.");
			break;
		}
	}
	
	public int askCard() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Que voulez vous jouer ? (taper un numéro entre 1 et " + this.hand.size() + ", 0 pour la pioche)"); 
		MyWindow.setUserConsole("Que voulez vous jouer ? (cliquez sur le numéro correspondant à la carte)");
		System.out.println("taper 66 pour quitter");
		
		int card = -1;
		boolean error = true;
		
		while (error == true) {
			error = false;
			if (Game.playGraphic == true) {
				card = MyWindow.askSomething(this.hand.size());
			} 
			else {
				try {
					card = scan.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Saisie invalide. Réessayez");
					MyWindow.addInfosUserConsole("Saisie invalide. Réessayez");
					scan = new Scanner(System.in);
					error = true;
				}
			}
		}
		return card;
	}
	public int askSay() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Voulez-vous dire Carte ? (0 pour non et 1 pour oui)");
		MyWindow.setUserConsole("Voulez-vous dire Carte ? (0 pour non et 1 pour oui)");
		int answer = -1;
		boolean error = true;
		
		while (error == true) {
			error = false;
			if(Game.playGraphic == true) {
				answer = MyWindow.askYesNo();
			}
			else {
				try {
					answer = scan.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Saisie invalide. Réessayez");
					MyWindow.addInfosUserConsole("Saisie invalide. Réessayez");
					scan = new Scanner(System.in);
					error = true;
				}
			}
		}	
		return answer;
	}
	public int askStopAttack() {	//demande si la carte stopAttack doit être jouée
		Scanner scan = new Scanner(System.in);
		System.out.println("Vous possédez une carte pour bloquer les attaques, et vous en subissez justement une. Voulez-vous l'utiliser ? (0 pour non et 1 pour oui)");
		MyWindow.setUserConsole("Vous possédez une carte pour bloquer les attaques, et vous en subissez justement une. Voulez-vous l'utiliser ? (0 pour non et 1 pour oui)");
		int answer = -1;
		boolean error = true;
		boolean saisie = false;
		
		while (saisie == false) {
			if(Game.playGraphic == true) {
				answer = MyWindow.askYesNo();
			}
			else {
				try {
					answer = scan.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Saisie invalide. Réessayez");
					MyWindow.addInfosUserConsole("Saisie invalide. Réessayez");
					scan = new Scanner(System.in);
					error = true;
				}
			}
			if ((answer == 1 || answer == 0) && error == false) {
				saisie = true;
			}
			if (error == false && (answer != 1 && answer != 0)) {
				System.out.println("Saisie invalide. Réessayez");
				MyWindow.addInfosUserConsole("Saisie invalide. Réessayez");
			}
			error = false;
		}
		return answer;
	}
	
	public void playerTakesCard() {
		System.out.println("Le joueur pioche");
		MyWindow.addInfosUserConsole("Le joueur pioche");
	}
	
//------------------------------------EFFECTS------------------------------------------------------------------------
	//execution
	public boolean executeEffect(Card card_to_play, Game game) {
		boolean hasPlayed = true;
		if(game.effectsActives.containsKey(card_to_play.getValue())) {
			for(int i=0 ; i<game.effectsActives.get(card_to_play.getValue()).size() ; i++) {
				int element = game.effectsActives.get(card_to_play.getValue()).get(i);
				switch (element) {
				case 1 :
					this.changeColor();
					break;
				case 2 :
					this.replay();
					hasPlayed = false;
					break;
				case 3 :
					this.nextPlayerPass();
					break;
				case 4 :
					this.changeDirection();
					break;
				case 5 :
					this.draw1Card(game);
					break;
				case 6 :
					this.draw3Card(game);
					break;
				case 7 :
					this.stopAttacks();
					break;
				default :
					break;
				}
			}
		}	
		return hasPlayed;
	}
	
	//effets
	public void replay() {
		System.out.println("Le joueur rejoue");
		MyWindow.addInfosUserConsole("Le joueur rejoue");
	}
		
	public void nextPlayerPass() {
		System.out.println("Le prochain joueur passe son tour");
		MyWindow.addInfosUserConsole("Le prochain joueur passe son tour");
		Game.round.nextPlayer();
	}
		
	public void changeDirection(){
		System.out.println("Le jeu change de sens");
		MyWindow.addInfosUserConsole("Le jeu change de sens");
		Game.round.setDirection();
	}	
	
	public void changePlayerTemp(int number) { 	// adds cards to take
		Player playerTemp = null;
		playerTemp = Game.round.nextPlayer(Game.round.getPlayerPosition(), Game.round.getCurrentPlayer());
		playerTemp.addCardToTake(number);
	}
	
	public void draw1Card(Game game) {	//fait piocher au joueur suivant sans recours
		System.out.println("Le prochain joueur prend 1 carte de plus");
		MyWindow.addInfosUserConsole("Le prochain joueur prend 1 carte de plus");
		this.changePlayerTemp(1);
	}

	public void draw3Card(Game game) {	//fait piocher, sauf si moyen de contrer
		System.out.println("Le prochain joueur prend 3 cartes de plus");
		MyWindow.addInfosUserConsole("Le prochain joueur prend 3 cartes de plus");
		this.changePlayerTemp(3);
	}
		
	public void stopAttacks() {
		System.out.println("Le joueur contre l'attaque");
		MyWindow.addInfosUserConsole("Le joueur contre l'attaque");
		this.cardToTake = 0;
	}	

		
}
