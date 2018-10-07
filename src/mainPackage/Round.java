package mainPackage;

import Graphic.MyWindow;
import InterfaceUser.WorkingRound;
import players.*;

/**
 * Defines how the game will progress. 
 * This is the real core of the game, where a player will be allowed to play, where the next player will be defined, ...
 * @author Benjamin Héroult
 *
 */
public class Round implements WorkingRound{
	//Attributes
	private boolean turnDirection;
	private Player currentPlayer;
	private int playerPosition;
	private boolean over;	//if the game is done
	
	private boolean start = false;
	
	//Constructor
	/**
	 * Instantiation of the class. Defines some attributes to start a game. 
	 * @param direction
	 * direction that takes the game. An analogy with a table can be useful to see how it works.
	 * @param game
	 * transmission of game's instantiation to use some of it's functions
	 */
	public Round (boolean direction, Game game){
		this.turnDirection = direction;
		this.currentPlayer = this.firstPlayer();
		this.over = false;
	}
	
	//Methods
	/**
	 * Second version of the method, used by other classes such as in a case of effects, to have an access to some functions from the next player
	 * @param playerPos
	 * position of the player used as reference to move across the list
	 * @param currentPlayer
	 * name of the current player
	 * @return the next player
	 */
	public Player nextPlayer(int playerPos, Player currentPlayer) {
		//normal direction
		if (this.turnDirection == true) {
			if(playerPos >= Game.getPlayersName().size() - 1) {	//end of list
				playerPos = 0;
				String name = Game.getPlayersName().get(playerPos);
				currentPlayer = Game.getPlayers().get(name);
			}
			else {
				playerPos ++;
				String name = Game.getPlayersName().get(playerPos);
				currentPlayer = Game.getPlayers().get(name);
			}
		}
		//opposite direction
		else {
			if(playerPos <= 0) {	//beginning of list
				playerPos = Game.getPlayersName().size() - 1;
				String name = Game.getPlayersName().get(playerPos);
				currentPlayer = Game.getPlayers().get(name);
			}
			else {
				playerPos --;
				String name = Game.getPlayersName().get(playerPos);
				currentPlayer = Game.getPlayers().get(name);
			}
		}
		return currentPlayer;
	}
	
	/**
	 * Defines who will be the next player, by using the direction of the game and the actual player.
	 */
	public void nextPlayer() {
		//normal direction
		if (this.turnDirection == true) {
			if(this.playerPosition >= Game.getPlayersName().size() - 1) {	//end of the list
				this.playerPosition = 0;
				String name = Game.getPlayersName().get(this.playerPosition);
				this.currentPlayer = Game.getPlayers().get(name);
			}
			else {
				this.playerPosition ++;
				String name = Game.getPlayersName().get(this.playerPosition);
				this.currentPlayer = Game.getPlayers().get(name);
			}
		}
		//opposite direction
		else {
			if(this.playerPosition <= 0) {	//beginning of the list
				this.playerPosition = Game.getPlayersName().size() - 1;
				String name = Game.getPlayersName().get(this.playerPosition);
				this.currentPlayer = Game.getPlayers().get(name);
			}
			else {
				this.playerPosition --;
				String name = Game.getPlayersName().get(this.playerPosition);
				this.currentPlayer = Game.getPlayers().get(name);
			}
		}
	}
	
	/**
	 * Changes the direction of the game
	 */
	public void setDirection () {
		this.turnDirection = !this.turnDirection;
	}
	
	/**
	 * Defines who will be the first player, to start the game, and its position. 
	 * @return the player who starts
	 */
	private Player firstPlayer() {
		//multiplied by 10 and then divided again by 10 in order to increase possibilities. 
		//Otherwise the probability to get the same number every time is to high
		double var = Math.random() * (Game.getPlayersName().size() * 10);
		this.playerPosition = (int)var/10; //return a number between 0 and the total number of players less 1
		String player_name = Game.getPlayersName().get(this.playerPosition);
		return Game.getPlayers().get(player_name);
	}
	
	
	/**
	 * Gives an access to who is the current player.
	 * @return the current player
	 */
	public Player getCurrentPlayer () {
		return this.currentPlayer;
	}
	/**
	 * Gives an access to position of the actual player
	 * @return the current player's position
	 */
	public int getPlayerPosition () {
		return this.playerPosition;
	}
	
//---------------------------------INTERFACE---------------------------------------------
	public void initPlayerRound() {
		System.out.println(" > Le joueur " + this.currentPlayer.getName() + " joue");
		MyWindow.setUserConsole(" > Le joueur " + this.currentPlayer.getName() + " joue");
		
		System.out.println(" > > Carte de la pile : " + Game.pile.cards.get(0));
	}
	
	public void endPlayer() {
		System.out.println(" # # Le joueur a fini");
	}
	
	public void endGame() {
		System.out.println("Fin du jeu");
		MyWindow.setUserConsole("Fin du jeu");
	}
	
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
	/**
	 * Call the method to roll out the game only if it has not been called already. 
	 * The action is kept in memory with the attribute start. 
	 * Plays the same roll as a singleton for a class.
	 * @param game
	 * to access give access game's content to playing method
	 */
	public void startPlaying(Game game) {
		if(this.start == false) {
			this.start = true;
			this.playing(game);
		}
	}
	
	/**
	 * Makes a player play as long as their is at least 2 players left.
	 * Every time that one is done, he is removed from the list containing the players and added to the list of ranking.
	 * @param game
	 * to have an access to the list of players playing or those done
	 */
	private void playing(Game game) {
		while (this.over != true) {
			this.initPlayerRound();
			
			this.currentPlayer.play(game);
			
			this.currentPlayer.getCardsInHand();	//number of cards still in hand
			
			if (currentPlayer.hasFinished() == true) {	//removes the player if finished, and adds it to the rank list
				this.endPlayer();
				Game.getPlayers().remove(Game.getPlayersName().get(playerPosition));
				Game.ranking.add(Game.getPlayersName().get(playerPosition));
				Game.getPlayersName().remove(playerPosition);
			}
			
			//put an end to the game if their is less than two players left, or if the user wants to quit
			if ((Game.getPlayers().size() == 1) || (this.currentPlayer.wantToQuit == true)){
				this.over = true;
				this.endGame();
			}
			
			if(this.currentPlayer instanceof AI) {
				MyWindow.updatePlayer(this.currentPlayer.getName(), this.currentPlayer.getHand().size());
			}
			
			this.nextPlayer();
		}
	}
}
