package com.mygdx.Dominion.Network;

import com.esotericsoftware.kryonet.Client;
import com.mygdx.Dominion.model.Card;

public class DominionClient {

	private Client c;
	
	public DominionClient(Client c)
	{
		this.c = c;
	}
	
	public void endTurnRequest()
	{
		
	}
	
	public void cardBoughtRequest(Card c)
	{
		
	}
	
	public void cardPlayedRequest(Card c)
	{
		
	}

	public void updateStateRequest() {

	}
	
}
