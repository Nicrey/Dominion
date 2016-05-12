package Network.Requests;

import java.util.ArrayList;

import Model.Card;
import Model.Player;

public class GameOverResponse {

	ArrayList<ArrayList<Card>> victoryCardsForPlayers;
	Player winner;
	ArrayList<Integer> points;
	ArrayList<Player> players;
	
	public GameOverResponse(){
		
	}
	public GameOverResponse(ArrayList<ArrayList<Card>> vicCards,Player winner, ArrayList<Integer> points, ArrayList<Player> players){
		victoryCardsForPlayers = vicCards;
		this.winner = winner;
		this.points = points;
		this.players = players;
	}
	
	public ArrayList<ArrayList<Card>> getVictoryCards(){
		return victoryCardsForPlayers;
	}
	
	public Player getWinner(){
		return winner;
	}
	
	public ArrayList<Integer> getPoints(){
		return points;
	}
	
	public ArrayList<Player> getPlayers(){
		return players;
	}
}
