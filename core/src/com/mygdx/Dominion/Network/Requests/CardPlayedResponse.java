package com.mygdx.Dominion.Network.Requests;

import com.mygdx.Dominion.model.Card;
import com.mygdx.Dominion.model.GameData;

public class CardPlayedResponse {

	
	private Card c;
	private GameData data;
	private int index;
	
	public CardPlayedResponse(){
		c = null;
		data = null;
	}
	
	public CardPlayedResponse(GameData data, Card c, int index){
		this.data = data;
		this.c = c;
		this.index = index;
	}
	
	public GameData getData(){
		return data;
	}
	
	public Card getCard(){
		return c;
	}
	
	public int getIndex(){
		return index;
	}
}
