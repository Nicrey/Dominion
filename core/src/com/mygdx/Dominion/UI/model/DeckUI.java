package com.mygdx.Dominion.UI.model;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.mygdx.Dominion.Controller.DominionController;
import com.mygdx.Dominion.UI.UIConfig;
import com.mygdx.Dominion.model.Card;

public class DeckUI extends Button {

	DominionController game; 
	Image bg1;
	Image bg2;
	Label decksize;
	Label graveyardsize;
	Skin skin;
	private float padding =  UIConfig.coinSize/2;
	private float ypadding = UIConfig.coinSize;
	private float bgpad = UIConfig.coinSize/2;
	private float fontoff = 5;
	
	public DeckUI(DominionController game,Skin skin)
	{
		this.skin = skin;
		this.game = game;
		decksize = new Label("",skin);
		
		decksize.setPosition(UIConfig.deckX+padding, UIConfig.deckY +ypadding);
		this.decksize.setColor(UIConfig.labelColor);
		graveyardsize = new Label("",skin);
		graveyardsize.setPosition(UIConfig.graveyardX+padding, UIConfig.deckY + ypadding);
		this.graveyardsize.setColor(UIConfig.labelColor);
		
		bg1 = new Image(UIConfig.roundButton);
		bg2 = new Image(UIConfig.roundButton);
	
		bg1.setPosition(UIConfig.deckX+padding - bgpad + fontoff, UIConfig.deckY+ypadding - bgpad );
		bg2.setPosition(UIConfig.graveyardX+padding - bgpad + fontoff, UIConfig.deckY +ypadding - bgpad);
		bg1.setSize(UIConfig.coinSize*1.2f, UIConfig.coinSize*1.2f);
		bg2.setSize(UIConfig.coinSize*1.2f, UIConfig.coinSize*1.2f);
		add(bg1);
		add(bg2);
		add(decksize);
		add(graveyardsize);
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		
		decksize.setText(game.getTurnPlayer().getDeckSize() + "");
		ArrayList<Card> grave = game.getTurnPlayer().getGraveyard();
		graveyardsize.setText(grave.size() + "");
		if(grave.size() < 10)
			graveyardsize.setPosition(UIConfig.graveyardX+padding+fontoff/2, UIConfig.deckY + ypadding);
		else
			graveyardsize.setPosition(UIConfig.graveyardX+padding-fontoff/4, UIConfig.deckY + ypadding);
		
		if(game.getTurnPlayer().getDeckSize() < 10)
			decksize.setPosition(UIConfig.deckX+padding+fontoff/2, UIConfig.deckY +ypadding);
		else
			decksize.setPosition(UIConfig.deckX+padding-fontoff/4, UIConfig.deckY +ypadding);
			
			
		int i;
		for( i= 0; i < grave.size()-1; i++)
			if( i > 30)
				break;
			else
				batch.draw(UIConfig.cardBack, UIConfig.graveyardX ,
					UIConfig.deckY+ i * UIConfig.deckStep, 
					UIConfig.deckSizeWidth, 
					UIConfig.deckSizeHeight);
		
		if(grave.size() > 0)
			batch.draw(grave.get(grave.size()-1).getTexture(), UIConfig.graveyardX,
					UIConfig.deckY + i * UIConfig.deckStep, 
					UIConfig.deckSizeWidth,
					UIConfig.deckSizeHeight);
	
	
	
		for(i = 0; i < game.getTurnPlayer().getDeckSize(); i++)
			if(i > 30)
				break;
			else
				batch.draw(UIConfig.cardBack, UIConfig.deckX,
					UIConfig.deckY + i * UIConfig.deckStep,
					UIConfig.deckSizeWidth,
					UIConfig.deckSizeHeight);
		
		
		
		Array<Actor> children = getChildren();
		for ( i = 0; i < children.size; i++)
			children.get(i).draw(batch, parentAlpha);
		
		
	}

}
