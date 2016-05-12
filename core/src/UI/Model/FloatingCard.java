package UI.Model;

import com.badlogic.gdx.math.Rectangle;
import UI.UIConfig;
import Model.Card;

public class FloatingCard {
	
	Card card;
	Rectangle cardRect;
	
	public FloatingCard(Card card,float x, float y )
	{
		cardRect = new Rectangle();
		cardRect.height = UIConfig.floatingCardHeight;
		cardRect.width = UIConfig.floatingCardWidth;
		cardRect.setPosition(x-cardRect.width/2, UIConfig.height-y-cardRect.height/2);
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
		cardRect.setPosition(x-cardRect.width/2, UIConfig.height-y-cardRect.height/2);
	}
	
}
