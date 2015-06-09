package com.mygdx.Dominion.Network.Requests;

import java.util.ArrayList;

import com.mygdx.Dominion.model.Card;
import com.mygdx.Dominion.model.Player;

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
