package com.mygdx.Dominion.Network;

public class TurnEndRequest {

	
	private int playerIndex;
	
	/**
	 * Constructor for an end turn request 
	 * @param index index of the player that ended the turn 
	 */
	public TurnEndRequest(int index)
	{
		this.playerIndex = index;
	}
	
	public int getIndex(){
		return playerIndex;
	}
}
