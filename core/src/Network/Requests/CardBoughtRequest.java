package Network.Requests;

import Model.Card;

public class CardBoughtRequest {

	private Card c;
	private int index;
	private int state;
	
	public CardBoughtRequest(){
		c = null;
		index = -1;
		state = -1;
	}
	public CardBoughtRequest(Card c, int state, int index){
		this.c = c;
		this.state = state;
		this.index = index;
	}
	
	public Card getCard()
	{
		return c;
	}
	
	public int getIndex(){
		return index;
	}
	
	public int getState(){
		return state;
	}
}
