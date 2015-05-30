package com.mygdx.Dominion.Network.Requests;

import com.mygdx.Dominion.model.Card;

public class CardPlayedRequest {

	private Card c;
	
	public CardPlayedRequest(Card c)
	{
		this.c = c;
	}
	
	public Card getCard(){
		return c;
	}
	
}
