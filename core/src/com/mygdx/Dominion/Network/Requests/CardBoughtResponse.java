package com.mygdx.Dominion.Network.Requests;

import com.mygdx.Dominion.model.Card;
import com.mygdx.Dominion.model.GameData;

public class CardBoughtResponse {
	
	private Card c;
	private GameData data;
	
	public CardBoughtResponse(){
		c = null;
		data = null;
	}
	
	public CardBoughtResponse(GameData data, Card c){
		this.data = data;
		this.c = c;
	}
	
	public GameData getData(){
		return data;
	}
	
	public Card getCard(){
		return c;
	}
}
