package com.mygdx.Dominion.Network.Requests;

import com.mygdx.Dominion.model.Card;

public class CardBoughtRequest {

	private Card c;
	
	public CardBoughtRequest(Card c){
		this.c = c;
	}
	
	public Card getCard()
	{
		return c;
	}
}
