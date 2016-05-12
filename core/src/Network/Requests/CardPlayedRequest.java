package Network.Requests;

import Model.Card;

public class CardPlayedRequest {

	private Card c;
	private int state;
	private int index;
	private int cardIndex;
	
	public CardPlayedRequest(){
		c = null;
		state = -1;
		index = -1;
	}
	
	public CardPlayedRequest(Card c, int state, int index, int cardIndex)
	{
		this.c = c;
		this.state = state;
		this.index = index;
		this.cardIndex = cardIndex;
	}
	
	public int getCardIndex(){
		return cardIndex;
	}
	
	public Card getCard(){
		return c;
	}
	
	public int getIndex(){
		return index;
	}
	
	public int getState(){
		return state;
	}
	
}
