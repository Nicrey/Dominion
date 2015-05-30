
package com.mygdx.Dominion.model;

import java.util.ArrayList;

/**
 * Options for the game
 * This class saves all the information need to setup a game.
 */



public class Options {

	private static Options options = new Options();
	private int playerCount;
	private String winner = "";
	public static ArrayList<Player> players;
	
	
	private Options()
	{
		this.playerCount = players.size();
	}
	
	public int getPlayerCount()
	{
		return playerCount;
	}
	
	public static Options getInstance() {
		return options;
	}

	public void setPlayerCount(int counter) {
		this.playerCount = counter;
	}

	public void setWinner(String name) {
		this.winner = name;
	}

	public String getWinner() {
		return winner;
	}

	public void setPlayers(ArrayList<Player> players) {
		Options.players = players;
	}

	
}
