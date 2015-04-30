package com.mygdx.Dominion.UI.model;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.Dominion.model.Board;
import com.mygdx.Dominion.model.GameUtils;

public class CustomLabel extends Label {

	private Board game;
	private int type;
	private int index;
	
	public CustomLabel(CharSequence text, Skin skin, Board game, int type , int index) {

		super(text, skin);
		this.game = game;
		this.type = type;
		this.index = index;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void act(float delta) {

		if(type == GameUtils.CARDTYPE_ACTION)
			this.setText("" +game.getBuyableActionCards().getRemainingCards(index));
		if(type == GameUtils.CARDTYPE_TREASURE)
			this.setText("" +game.getBuyableTreasureCards().getRemainingCards(index));	
		if(type == GameUtils.CARDTYPE_VICTORY)
			this.setText("" +game.getBuyableVictoryCards().getRemainingCards(index));
	}
	
	

}
