
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
	public static ArrayList<Player> players = new ArrayList<Player>();
	
	
	private Options()
	{
		if(players != null)
			this.playerCount = players.size();
		else
			this.playerCount = 0;
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

	public static void setPlayers(ArrayList<Player> conplayers) {
		Options.players = new ArrayList<Player>();
		Options.players.addAll(conplayers);
		System.out.println(Options.players.size());
	}

	
}
