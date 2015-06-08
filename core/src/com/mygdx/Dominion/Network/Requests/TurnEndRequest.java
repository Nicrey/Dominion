package com.mygdx.Dominion.Network.Requests;

public class TurnEndRequest {

	
	private int playerIndex;
	
	public TurnEndRequest(){
		playerIndex = 17;
	}
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
