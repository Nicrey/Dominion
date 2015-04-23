
package com.mygdx.Dominion.model;

/**
 * Options for the game
 * This class saves all the information need to setup a game.
 */



public class Options {

	private static Options options = new Options();
	private int playerCount;
	private String winner = "";
	
	
	private Options()
	{
		this.playerCount = 2;
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

	
}
