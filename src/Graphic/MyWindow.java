package Graphic;

import javax.imageio.ImageIO;

import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import mainPackage.Card;
import players.PhysicalPlayer;

import java.awt.*;

/**
 * Specifies the window used as interface with the user, its initial situations and its changes. 
 * @author Benjamin Héroult
 *
 */
public class MyWindow {
	/**
	 * Frame of the window.
	 */
	private static JFrame frame;
	
	/**
	 * Title area.
	 */
	private static JLabel title;
	/**
	 * First line of the pile area.
	 */
	private static JLabel pile1;
	/**
	 * Second line of the pile area.
	 */
	private static JLabel pile2;
	/**
	 * Deck area.
	 */
	private static JLabel deck;
	/**
	 * Image of the back of a card.
	 */
	private static ImageIcon cardImage;
	/**
	 * Area containing various ways to get the user's answer.
	 */
	private static JTextField userEntry;
	/**
	 * Area where questions are asked to the user.
	 */
	private static JLabel userconsole;
	/**
	 * User's answer area, where buttons are displayed.
	 */
	protected static JPanel answer;
	/**
	 * User's name area.
	 */
	private static JLabel userName;
	/**
	 * Various and short informations area.
	 */
	private static JLabel infos;
	/**
	 * First line of user's cards area.
	 */
	private static JLabel userCards1;
	/**
	 * Second line of user's cards area, where cards are displayed.
	 */
	private static JLabel userCards2;
	
	/**
	 * Map collection containing the different player's areas, each one containing the image and the number of cards in hand for one player.
	 */
	private static Map <String, JLabel> hands = new HashMap <String, JLabel>();

	/**
	 * Create the application.
	 */
	public MyWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private static void initialize() {
		MyWindow.frame = new JFrame();
		MyWindow.frame.getContentPane().setBackground(Color.WHITE);
		MyWindow.frame.setBounds(100, 100, 1100, 800);
		MyWindow.frame.getContentPane().setLayout(null);
		
		MyWindow.title = new JLabel("Jeu du 8 am\\u00E9ricain");
		MyWindow.title.setFont(new Font("AR CARTER", Font.BOLD, 30));
		MyWindow.title.setText("Jeu du 8 am\u00E9ricain");
		MyWindow.title.setBounds(400, 10, 250, 57);
		frame.getContentPane().add(MyWindow.title);
		
		try {
			cardImage = new ImageIcon(ImageIO.read(new File("ressources/card.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MyWindow.deck = new JLabel(cardImage);	//back of a card representing the deck
		MyWindow.deck.setBounds(360, 250, cardImage.getIconWidth(), cardImage.getIconHeight());
		frame.getContentPane().add(deck);
		
		MyWindow.pile1 = new JLabel("Pile :");
		MyWindow.pile1.setBounds(520, 270, 100, 30);
		frame.getContentPane().add(pile1);
		
		MyWindow.pile2 = new JLabel("");
		MyWindow.pile2.setBounds(490, 300, 300, 30);
		frame.getContentPane().add(pile2);
		
		MyWindow.userconsole = new JLabel();	//display
		userconsole.setBounds(10, 514, 964, 103);
		frame.getContentPane().add(userconsole);
		
		answer = new JPanel();
		answer.setBounds(10, 650, 964, 53);
		frame.getContentPane().add(answer);
		
		MyWindow.infos = new JLabel("");	//display "carte !", "contre carte !", "faute !"
		infos.setBounds(400, 800, 100, 100);
		frame.getContentPane().add(infos);
		
		MyWindow.userCards1 = new JLabel(" > > Vos cartes : ");
		userCards1.setBounds(10, 450, 100, 30);
		frame.getContentPane().add(userCards1);
		
		MyWindow.userCards2 = new JLabel("");	//displays the cards in the user's hand
		userCards2.setBounds(0, 490, 964, 30);
		frame.getContentPane().add(userCards2);
		
	}
	
	/**
	 * Provides an access to the frame
	 * @return the frame
	 */
	public static  JFrame getFrame() {
		return MyWindow.frame;
	}
	
	/**
	 * Provides an access to the field containing the input from the user
	 * @return
	 * the field considered
	 */
	public static JTextField getUserEntry() {
		return MyWindow.userEntry;
	}
	
	/**
	 * Changes values of the field displaying the pile.
	 * @param value
	 * the value to display
	 * @param couleur
	 * the color to display
	 */
	public static void setPile(String value, String couleur) {
		MyWindow.pile2.setText("valeur : " + value + '\n' + " /  couleur : " + couleur);
		frame.getContentPane().repaint();
	}
	
	/**
	 * Displays a single line for instructions
	 * @param newInformations
	 * the new line to display
	 */
	public static void setUserConsole(String newInformations) {
		MyWindow.userconsole.setText(newInformations);
		frame.getContentPane().repaint();
	}
	/**
	 * Adds a new line to display, keeping what was already here
	 * @param newInformations
	 * the informations to add
	 */
	public static void addInfosUserConsole(String newInformations) {
		String previous = MyWindow.userconsole.getText();
		MyWindow.userconsole.setText(previous + "  -  " + newInformations);
		frame.getContentPane().repaint();
	}
	
	/**
	 * Creates the views of AI's hands
	 * @param nb_players
	 * the number of players to display, in addition to the physical Player
	 */
	public static void createHands(int nb_players) {
		List <Integer> positionX = new ArrayList <Integer>();
		
		switch (nb_players-1) {
		case 1 :
			positionX.add(450);
			break;
		case 2 :
			positionX.add(200);
			positionX.add(700);
			break;
		case 3 :
			positionX.add(200);
			positionX.add(450);
			positionX.add(700);
			break;
		case 4 :
			positionX.add(75);
			positionX.add(325);
			positionX.add(575);
			positionX.add(825);
			break;
		case 5 :
			positionX.add(25);
			positionX.add(225);
			positionX.add(425);
			positionX.add(625);
			positionX.add(825);
			break;			
		}
		for (int i=1 ; i<nb_players ; i++) {
			JLabel hands = new JLabel("AI_" + i + " - 0 cartes", cardImage, SwingConstants.LEFT  );
			hands.setBounds(positionX.get(i-1), 75, cardImage.getIconWidth()+100, cardImage.getIconHeight());
			frame.getContentPane().add(hands);
			MyWindow.hands.put("AI_" + i, hands);
		}
		frame.getContentPane().repaint();
	}
	
	/**
	 * Updates a specific player, by modifying its number of cards in hand.
	 * @param name
	 * the name of the players
	 * @param nb_cards
	 * the number of cards to display
	 */
	public static void updatePlayer(String name, int nb_cards) {
		hands.get(name).setText(name + " - " + nb_cards + " cartes");
		frame.getContentPane().repaint();
	}
	
	/**
	 * Displays the name of the user in the left-hand corner of the window.
	 * @param name
	 * the user's name
	 */
	public static void displayUserName(String name) {
		MyWindow.userName = new JLabel("JOUEUR : " + name);
		MyWindow.userName.setBounds(10, 0, 100, 100);
		frame.getContentPane().add(userName);
		frame.getContentPane().repaint();
	}
	
	/**
	 * Displays the cards from the user's hand.
	 * @param player
	 * the physical player
	 */
	public static void displayUserHand(PhysicalPlayer player) {
		int i = 1;
		StringBuffer sb = new StringBuffer();
		Iterator <Card> it = player.getHand().iterator();
		while (it.hasNext()) {
			Card current = it.next();
			sb.append(" (" + i + ") " + current + " | ");
			i++;
		}
		MyWindow.userCards2.setText(sb.toString());
		frame.getContentPane().repaint();
	}
	
	/**
	 * Calls the class Buttons to allow the user to answer a yes/no question.
	 * @return
	 * the answer to the question
	 */
	public static int askYesNo() {
		new Buttons();
		return Buttons.asw;
	}
	
	/**
	 * Calls the class Buttons to allow the user to answer a multiple answers question.
	 * @param nb_buttons
	 * the number of buttons (= possible answers) to display
	 * @return
	 * the answer to the question
	 */
	public static int askSomething(int nb_buttons) {
		new Buttons(nb_buttons);
		return Buttons.asw;
	}
	
	/**
	 * Displays informations about the game, such as a fault, the message "carte !", ...
	 * @param newInformations
	 * the new informations to display
	 */
	public static void setInfos(String newInformations) {
		MyWindow.infos.setText(newInformations);
	}
	
}
