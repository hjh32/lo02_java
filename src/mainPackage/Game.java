package mainPackage;

import java.util.*;

import javax.swing.JFrame;

import Graphic.MyWindow;
import InterfaceUser.InitGame;
import groupOfCards.*;
import players.*;
import mainPackage.Rule;

/**
 * Main class of the game. 
 * It calls every other elements, and defines the initial conditions of the game.
 * @author Benjamin  Héroult
 *
 */
public class Game implements InitGame{
	//-----------------------singleton-------------------------
	
	private static Game instance = null;
	
	/**
	 * static method called instead of the constructor, to assure that the class Game is instantiated only once.
	 * @return the value of the variable that allow to keep in memory the number of class' instances
	 */
	public final static Game getInstance() {
		if (Game.instance == null) {
			synchronized(Game.class) {
				if (Game.instance == null) {
					Game.instance = new Game();
				}
		    }
		}
			return Game.instance;
	}
	
	//-------------------------PRINCIPAL----------------------------
	//attributes
	/**
	 * Physical player of the game.
	 */
	protected PhysicalPlayer physicalPlayer;
	
	/**
	 * List associating players to their names as keys.
	 */
	private static Hashtable <String, Player> players = new Hashtable <String, Player>();
	/**
	 * List used to display names during the game ; list easier to use when manipulating players than the one above.
	 */
	private static List <String> players_name = new ArrayList <String>();
	/**
	 * List used to memorize winners and display it at the end of the game
	 */
	public static List <String> ranking = new ArrayList <String>();
	
	/**
	 * Deck of the game.
	 */
	public static Deck jeu1 = Deck.getInstance();
	
	/**
	 * Pile of the game.
	 */
	public static Pile pile = Pile.getInst();
	
	public List <String> effectsAvailables = new ArrayList <String>();	//helps to create new rules //service not available at the moment
	public Map <String, List <Integer>> effectsActives = new HashMap <String, List <Integer>>();
	
	/**
	 * Round of the game.
	 */
	public static Round round;
	
	/**
	 * Graphic window of the game.
	 */
	public MyWindow window;
	
	/**
	 * True if the user chooses to play with the graphic interface.
	 */
	public static boolean playGraphic;
	
	/**
	 * True if the game has begun ; used in the class Buttons.
	 */
	public static boolean hasBegun;
	
	//constructor
	/**
	 * Creates an instance of the class Game and defines every effect allowed in the game.
	 * @see Rule
	 */
	private Game() {
		this.effectsAvailables.add("replay");
		this.effectsAvailables.add("changeColor");
		this.effectsAvailables.add("nextPlayerPass");
		this.effectsAvailables.add("changeDirection");
		this.effectsAvailables.add("draw1Card");
		this.effectsAvailables.add("draw3Card");
		this.effectsAvailables.add("stopAttacks");
	}

	//----------------------------METHODES---------------------------------
	/**
	 * Give access to other classes to the collection of players, referenced by their name.
	 * @return the collection of players
	 */
	public static Map <String, Player> getPlayers () {
		return Game.players;
	}
	
	/**
	 * Creates "number" less 1 instance(s) of AI(s)
	 * @param number
	 * total number of players
	 * @param difficultyCoefficient
	 * number representing the difficulty level
	 */
	public void createPlayers (int number, int difficultyCoefficient) {
		String name;
		for (int i=1 ; i<number ; i++) {
			name = "AI_" + i;
			Game.players.put(name, new AI(name, this.chooseDifficulty(difficultyCoefficient)));
			Game.getPlayersName().add(name);
		}
	}
	
	/**
	 * Distributes cards to the different players.
	 * It requires the method cardPerplayer from the class Rule to determines how many cards must be distributed to each player.
	 * @param nb_jeux
	 * number of decks (1 or 2, witch means 52 or 104 cards in the game)
	 * @see mainPackageRule.cardPerPlayer()
	 * number of cards per player
	 */
	public void distribCards(int nb_jeux) {
		int nb_cards = Rule.cardPerPlayer(Game.players.size(), nb_jeux);
		String name_player;
		Player player;
		for (int i=0 ; i<nb_cards ; i++) {
			for (int j=1 ; j<Game.players.size() ; j++) {
				name_player = "AI_" + j;
				player = Game.players.get(name_player);
				Game.jeu1.giveCardToPLayer(player);
			}
			Game.jeu1.giveCardToPLayer(this.physicalPlayer);
		}
		Game.pile.addCard(Game.jeu1.cards.get(0));
		Game.jeu1.cards.remove(0);
		
		for (int j=1 ; j<Game.players.size() ; j++) {
			name_player = "AI_" + j;
			MyWindow.updatePlayer(name_player, nb_cards);
		}
	}
	
	/**
	 * Gives an access to the list of players' name
	 * @return the attribute "players_name"
	 */
	public static List <String> getPlayersName(){
		return Game.players_name;
	}
	
	/**
	 * Gives an access to the deck of the game
	 * @return the deck of the game
	 */
	public static Deck getDeck() {
		return Game.jeu1;
	}
	
	/**
	 * Gives an access to the pile of the game
	 * @return the pile of the game
	 */
	public static Pile getPile() {
		return Game.pile;
	}
	
	/**
	 * Choose the difficulty level using the answer communicated by the user, and define the strategy the AIs will use.
	 * @param difficultyCoefficient
	 * level of difficulty for the game (1 for easy, 2 for hard)
	 * @return
	 * return the strategy the AIs will use
	 */
	public Strategy chooseDifficulty(int difficultyCoefficient) {
		if (difficultyCoefficient == 1) {
			Strategy strategy = new StrategyEasy();
			return strategy;
		} else {
			Strategy strategy = new StrategyHard();
			return strategy;
		}

	}

//---------------------------------INTERFACE------------------------------------
	public String initPlayer(Scanner scan) {
		MyWindow.setUserConsole("Veuillez saisir votre nom/pseudonyme dans la console");
		System.out.println("Veuillez saisir votre nom/pseudonyme");
		String name = scan.nextLine();
		return name;
	}
	
	public void askGraphic(Scanner scan) {
		MyWindow.setUserConsole("Interface graphique : répondez dans la console");
		System.out.println("Voulez vous jouer avec l'interface graphique ? (0 pour non, 1 pour oui)");;
		int answer = -1;
		boolean saisie = false;
		boolean error = false;
		
		while (saisie != true) {
			try {
				answer = scan.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Saisie invalide. Réessayez");
				scan = new Scanner(System.in);
				error = true;
			}
			if ((answer == 0 || answer == 1) && error == false) {
				saisie = true;
			}
			if (error == false && (answer != 0 && answer != 1)) {
				System.out.println("Saisie invalide. Réessayez");
			}
			error = false;
		}
		if(answer == 0) {
			Game.playGraphic = false;
			System.out.println("Vous pouvez continuer à jouer ici et fermer la fenêtre qui vient de s'ouvrir");
		}
		else if(answer == 1) {
			Game.playGraphic = true;
			System.out.println("Veuillez passer sur la fenêtre ouverte à cet effet et l'étendre au maximum");
			MyWindow.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}
	
	public int choosePlayers(Scanner scan) {
		MyWindow.setUserConsole("Veuillez saisir le nombre de joueurs total (max 6)");
		System.out.println("Veuillez saisir le nombre de joueurs total (max 6)");
		int nb_players = -1;
		boolean saisie = false;
		boolean error = false;
		
		while (saisie != true) {
			if (Game.playGraphic == true) {
				nb_players = MyWindow.askSomething(6);
			}
			else {
				try {
					nb_players = scan.nextInt();
				} catch (InputMismatchException e) {
					MyWindow.addInfosUserConsole("Saisie invalide. Réessayez");
					System.out.println("Saisie invalide. Réessayez");
					scan = new Scanner(System.in);
					error = true;
				}
			}
			if (error == false) {
				switch (nb_players) {
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
					saisie = true;
					break;
				default:
					MyWindow.addInfosUserConsole("Saisie invalide. Réessayez");
					System.out.println("Saisie invalide. Réessayez");
					break;
				}				
			}
			error = false;
		}
		System.out.println("Vous avez choisi de faire une partie à " + nb_players + " joueurs");
		System.out.println("Les joueurs sont :");
		return nb_players;
	}
	
	public int initCards(Scanner scan) {
		System.out.println("Veuillez saisir le nombre de jeux de cartes (1 ou 2 jeux de 52 cartes, donc 52 ou 104 cartes en tout)");
		MyWindow.setUserConsole("Veuillez saisir le nombre de jeux de cartes (1 ou 2 jeux de 52 cartes, donc 52 ou 104 cartes en tout)");
		int nb_jeux = -1;
		boolean saisie = false;
		boolean error = false;
		
		while (saisie != true) {
			if (Game.playGraphic == true) {
				nb_jeux = MyWindow.askSomething(2);
			}
			else {
				try {
					nb_jeux = scan.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Saisie invalide. Réessayez");
					MyWindow.addInfosUserConsole("Saisie invalide. Réessayez");
					scan = new Scanner(System.in);
					error = true;
				}
			}
			if ((nb_jeux == 1 || nb_jeux == 2) && error == false) {
				saisie = true;
			}
			if (error == false && (nb_jeux != 1 && nb_jeux != 2)) {
				System.out.println("Saisie invalide. Réessayez");
				MyWindow.addInfosUserConsole("Saisie invalide. Réessayez");
			}
			error = false;
		}	
		return nb_jeux;
	}
	
	public void chooseRule(Scanner scan) {
		boolean ruleChosen = false;
		System.out.println("Quel règlement voulez vous choisir ? (taper 1 pour la minimale, 2 pour la montcal)");
		MyWindow.setUserConsole("Quel règlement voulez vous choisir ? (taper 1 pour la minimale, 2 pour la montcal)");
		int rule = -1;
		boolean error = false;
		
		while(ruleChosen == false) {
			if (Game.playGraphic == true) {
				rule = MyWindow.askSomething(2);
			}
			else {
				try {
					rule = scan.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Saisie invalide. Réessayez");
					MyWindow.addInfosUserConsole("Saisie invalide. Réessayez");
					scan = new Scanner(System.in);
					error = true;
				}
			}
			if (error == false) {
				switch (rule) {
				case 1:
				case 2:
					this.effectsActives = new Rule().chooseRule(rule);
					if (this.effectsActives.isEmpty() == false) {
						ruleChosen = true;
					}
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
	
	public void displayRanking() {
		System.out.println("Le classement est :");
		MyWindow.setUserConsole("Le classement est :");
		Iterator <String> it = Game.ranking.iterator();
		while (it.hasNext()) {
			String rank = it.next();
			System.out.println(" - " + rank);
			MyWindow.addInfosUserConsole(" - " + rank);
		}
		System.out.println(" - " + Game.players_name.get(0));
		MyWindow.addInfosUserConsole(" - " + Game.players_name.get(0));
	}
	
	public void beginGame() {
		System.out.println("Début du jeu");
		MyWindow.setUserConsole("Début du jeu");
	}
	
	
	public void seePlayers() {
		Iterator <String> it = Game.players_name.iterator();
		while (it.hasNext()) {
			String current = it.next();
			MyWindow.addInfosUserConsole(current);
			System.out.println(current);
		}
	}	
	
	
	public int difficulty(Scanner scan) {
		boolean difficultyChosen = false;
		boolean error = false;
		int difficulty = -1;
		System.out.println("Quelle  difficulté voulez-vous choisir ? (1 facile, 2 difficile)");
		MyWindow.setUserConsole("Quelle  difficulté voulez-vous choisir ? (1 facile, 2 difficile)");
		while (difficultyChosen == false) {
			if (Game.playGraphic == true) {
				difficulty = MyWindow.askSomething(2);
			}
			else {
				try {
					difficulty = scan.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Saisie invalide. Réessayez");
					MyWindow.addInfosUserConsole("Saisie invalide. Réessayez");
					scan = new Scanner(System.in);
					error = true;
				}
			}
			if (error == false) {
				switch (difficulty) {
				case 1:
					System.out.println("vous choisissez facile");
					MyWindow.addInfosUserConsole("vous choisissez facile");
					difficultyChosen = true;
					break;
				case 2:
					System.out.println("vous choisissez difficile ");
					MyWindow.addInfosUserConsole("vous choisissez difficile ");
					difficultyChosen = true;
					break;
				default:
					System.out.println("Saisie invalide. Réessayez");
					MyWindow.addInfosUserConsole("Saisie invalide. Réessayez");
					break;
				}
			}
			error = false;
		}
		return difficulty;
	}
	
//-------------------------------------------------------------------------------------------------------------
//----------------------------------------MAIN---------------------------------------------------
	/**
	 * mAin function of the application, where most of the classes are instantiated and the game organized.
	 * @param args
	 * No arguments needed or used.
	 */
	public static void main(String args[]) {	
		
		//creation of the game
		Game game = Game.getInstance();
				
		//creation of the window
		game.window = new MyWindow();
		MyWindow.getFrame().setVisible(true);
				
		//scanner opening
		Scanner scan = new Scanner(System.in);
		
		//physical player
		String name = game.initPlayer(scan);
		game.physicalPlayer = PhysicalPlayer.getInstance(name);
		Game.getPlayers().put(name, game.physicalPlayer);
		Game.getPlayersName().add(name);
		MyWindow.displayUserName(name);
		
		//graphic interface
		game.askGraphic(scan);
		
		// choose difficulty
		int difficultyCoefficient = game.difficulty(scan);

		// game.chooseDifficulty(difficultyCoefficient);
		
		//AIs
		int nb_players = game.choosePlayers(scan);
		game.createPlayers(nb_players, difficultyCoefficient);
		MyWindow.createHands(nb_players);
		game.seePlayers();
		
		//creation of the deck
		int nb_jeux = game.initCards(scan);
		Game.jeu1.createCards(nb_jeux);
		Collections.shuffle(Game.jeu1.cards);	//shuffling of the cards
		
		//cards' distribution
		game.distribCards(nb_jeux);
		MyWindow.displayUserHand(game.physicalPlayer);
		
		//rule's choice
		game.chooseRule(scan);
		
		game.beginGame();
		Game.hasBegun = true;
		Game.round = new Round (true, game);
		Game.round.startPlaying(game);
		
		scan.close();
		
		game.displayRanking();
	}
	
}
