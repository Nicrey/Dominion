package com.mygdx.Dominion.UI.model;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.Dominion.UI.UIConfig;
import com.mygdx.Dominion.model.Card;

public class FloatingCard {
	
	Card card;
	Rectangle cardRect;
	
	public FloatingCard(Card card,float x, float y )
	{
		cardRect = new Rectangle();
		cardRect.height = UIConfig.cHeight;
		cardRect.width = UIConfig.cWidth;
		cardRect.setPosition(x-UIConfig.cWidth/2, UIConfig.height-y-UIConfig.cHeight/2);
		this.card = card;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public Rectangle getCardRect() {
		return cardRect;
	}

	public void setCardRect(Rectangle cardRect) {
		this.cardRect = cardRect;
	}

	public void updatePosition(float x, float y) {
		cardRect.setPosition(x-UIConfig.cWidth/2, UIConfig.height-y-UIConfig.cHeight/2);
	}
	
}
