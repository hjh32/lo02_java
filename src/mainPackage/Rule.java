package mainPackage;

import java.util.*;
import Graphic.MyWindow;

/**
 * Defines the rules of the game, such as the number of cards per player and the effects of the cards. 
 * @author Benjamin Héroult
 *
 */
public class Rule{
	private static String carStopAttack = null;
	
	//constructor
	/**
	 * Instantiation of the class. Nothing specified.
	 */
	public Rule() {
		
	}
	
//---------------------------------------------METHODES--------------------------------------
	/**
	 * Choose how many cards must be given to each player at the beginning of the game. 
	 * Based on the rules defined on the Wikipedia page "8 américain", part 7 : "Variantes". 
	 * @param nb_players
	 * number of players in the game (2 to 6)
	 * @param nb_jeux
	 * number of group of cards in the game (1 or 2, so 52 or 104 cards)
	 * @return
	 * the number of cards each player musts receive
	 * @see https://fr.wikipedia.org/wiki/8_am%C3%A9ricain#Variantes
	 */
	public static int cardPerPlayer(int nb_players, int nb_jeux) {
		String antecedent = nb_players+","+nb_jeux;
		int cards_per_player = 0;
		switch (antecedent) {
			case "2,1" :
				cards_per_player = 10;
				break;
			case "2,2" :
				cards_per_player = 15;
				break;
			case "3,1" :
				cards_per_player = 8;
				break;
			case "3,2" :
				cards_per_player = 12;
				break;
			case "4,1" :
			case "5,1" :
			case "6,1" :
				cards_per_player = 6;
				break;
			case "4,2" :
			case "5,2" :
			case "6,2" :
				cards_per_player = 9;
				break;
			default :
				System.out.println("Erreur. Aucun cas ne prend en compte ce couple de paramètres");
				MyWindow.addInfosUserConsole("Erreur. Aucun cas ne prend en compte ce couple de paramètres");
				break;
		}
		return cards_per_player;
	}
		
	//choix de règle
	/**
	 * Defines on witch rule the game will be based, using numRules. 
	 * @param numRule
	 * number of the rule chosen
	 * @return the map collection of effects applied in the game associated to their card(s)
	 */
	public Map <String, List <Integer>> chooseRule(int numRule) {
		Map <String, List <Integer>> ruleChosen = new HashMap <String, List <Integer>>();	//map collection of the effects
		switch (numRule){
			case 1 :
				ruleChosen = this.minimale();
				break;
			case 2 :
				ruleChosen = this.monclar();
				break;
			default :
				System.out.println("Erreur. Aucun cas ne prend en compte ce paramètre");
				MyWindow.addInfosUserConsole("Erreur. Aucun cas ne prend en compte ce paramètre");
				break;
		}
		return ruleChosen;
	}
	
	/**
	 * Give access to the card that can stop an attack, if it exists. 
	 * @return the name of the card witch can stop an attack
	 */
	public static String getCardStopAttack() {
		return Rule.carStopAttack;
	}

//--------------------------------------------------------------------------------------------------
	//minimal rule
	/**
	 * Creates the map collection of the effects for the minimal version
	 * @return the map collection of the cards with the effects associated
	 */
	public Map <String, List <Integer>> minimale() {
		Map <String, List <Integer>> rule = new HashMap <String, List <Integer>>();	//contains the cards with the effects associated
		
		List <Integer> huit = new ArrayList <Integer>();	//contains the effects
		huit.add(1);	//change color ; reference used in the class player, when the effect needs to be applied
		rule.put("Huit", huit);	
		
		return rule;
	}
	
	//"Monclar" rule
	/**
	 * Creates the map collection of the effects for the "Monclar" version
	 * @return the map collection of the cards with the effects associated
	 */
	public Map <String, List <Integer>> monclar() {
		Map <String, List <Integer>> rule = new HashMap <String, List <Integer>>();
		
		List <Integer> dix = new ArrayList <Integer>();
		dix.add(2);	//replay
		rule.put("Dix", dix);
		
		List <Integer> sept = new ArrayList <Integer>();
		sept.add(3);	//nextPlayerPass
		rule.put("Sept", sept);
		
		List <Integer> valet = new ArrayList <Integer>();
		valet.add(4);	//changeDirection
		rule.put("Valet", valet);
		
		List <Integer> neuf = new ArrayList <Integer>();
		neuf.add(5);	//draw1Card
		rule.put("Neuf", neuf);
		
		List <Integer> as = new ArrayList <Integer>();
		as.add(6);	//draw3Card
		rule.put("As", as);
		
		List <Integer> huit = new ArrayList <Integer>();
		huit.add(1);	//changeColor
		huit.add(7);	//stopAttacks
		Rule.carStopAttack = "Huit";
		rule.put("Huit", huit);
		
		return rule;
	}
	
	/*
	//règle variante1
	public Map <String, List <Integer>> variante1() {
		Map <String, List <Integer>> rule = new HashMap <String, List <Integer>>();
		
		List <String> huit = new ArrayList <String>();
		huit.add("changeColor");
		rule.put("draw3Card");
		
		List <String> huit = new ArrayList <String>();
		huit.add("changeColor");
		rule.put("draw3Card");
		
		List <String> sept = new ArrayList <String>();
		sept.add("nextPlayerPass");
		rule.put("Sept", sept);
		
		List <String> valet = new ArrayList <String>();
		valet.add("changeDirection");
		rule.put("Valet", valet);
		
		List <String> huit = new ArrayList <String>();
		huit.add("changeColor");
		rule.put("draw3Card");
		
		List <String> huit = new ArrayList <String>();
		huit.add("changeColor");
		rule.put("draw3Card");
		
		List <String> six = new ArrayList <String>();
		six.add("draw1Card");
		rule.put("Six", six);
		
		List <String> neuf = new ArrayList <String>();
		neuf.add("nextPlayerPickCard");
		rule.put("Neuf", neuf);
		
		List <String> as = new ArrayList <String>();
		as.add("giveTwoCardToOther");
		rule.put("As", as);
		
		List <String> quatre = new ArrayList <String>();
		huit.add("changeColor");
		rule.put("draw3Card");
		
		List <String> huit = new ArrayList <String>();
		huit.add("changeColor");
		rule.put("draw3Card");
		
		List <String> huit = new ArrayList <String>();
		huit.add("changeColor");
		rule.put("draw3Card");
		
		List <String> huit = new ArrayList <String>();
		huit.add("changeColor");
		rule.put("draw3Card");
		
		return rule;
	}
	*/
}
