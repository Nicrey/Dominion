package UI.Model;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import Controller.DominionController;
import UI.UIConfig;
import Model.Card;
import Model.Player;

public class HiddenDeckUI extends Button {
	DominionController game; 
	Image bg1;
	Image bg2;
	Image bg3;
	Label decksize;
	Label graveyardsize;
	Label handsize;
	Label playername;
	Skin skin;
	int index;
	Player player;
	private int playerIndex;
	
	public HiddenDeckUI(DominionController game,Skin skin , int playerIndex, int index )
	{
		this.skin = skin;
		this.game = game;
		decksize = new Label("",skin);
		handsize = new Label("",skin);
		graveyardsize = new Label("",skin);

		decksize.setSize(UIConfig.coinSize, UIConfig.coinSize);
		handsize.setSize(UIConfig.coinSize, UIConfig.coinSize);
		graveyardsize.setSize(UIConfig.coinSize, UIConfig.coinSize);
		decksize.setAlignment(Align.center);
		graveyardsize.setAlignment(Align.center);
		handsize.setAlignment(Align.center);
		
		decksize.setPosition(UIConfig.hiddenDeckX , UIConfig.hiddenDeckY+ index * UIConfig.hiddenDeckHeight +index * UIConfig.hiddenDeckStep );
		this.decksize.setColor(UIConfig.labelColor);
		
		graveyardsize.setPosition(UIConfig.hiddenGraveyardX, UIConfig.hiddenDeckY + index * UIConfig.hiddenDeckHeight +index *UIConfig.hiddenDeckStep);
		this.graveyardsize.setColor(UIConfig.labelColor);
		
		handsize.setPosition(UIConfig.hiddenHandX , UIConfig.hiddenDeckY+ index * UIConfig.hiddenDeckHeight +index * UIConfig.hiddenDeckStep );
		this.handsize.setColor(UIConfig.labelColor);
			
		bg1 = new Image(UIConfig.roundButton);
		bg2 = new Image(UIConfig.roundButton);
		bg3 = new Image(UIConfig.roundButton);
		
		bg1.setPosition(UIConfig.hiddenDeckX  , UIConfig.hiddenDeckY + index  * UIConfig.hiddenDeckHeight +index *UIConfig.hiddenDeckStep);
		bg2.setPosition(UIConfig.hiddenGraveyardX  , UIConfig.hiddenDeckY + index * UIConfig.hiddenDeckHeight +index *UIConfig.hiddenDeckStep );
		bg3.setPosition(UIConfig.hiddenHandX  , UIConfig.hiddenDeckY + index * UIConfig.hiddenDeckHeight +index *UIConfig.hiddenDeckStep );
		
		bg1.setSize(UIConfig.coinSize*1f, UIConfig.coinSize*1f);
		bg2.setSize(UIConfig.coinSize*1f, UIConfig.coinSize*1f);
		bg3.setSize(UIConfig.coinSize*1f, UIConfig.coinSize*1f);
		this.playerIndex = playerIndex;
		this.player = game.getGameData().getPlayer(playerIndex+1);
		this.index = index;
		playername = new Label(player.getName()+ "",skin);
		playername.setPosition(UIConfig.hiddenPlayerX,UIConfig.hiddenDeckY + index * UIConfig.hiddenDeckHeight +index *UIConfig.hiddenDeckStep );
		
		add(playername);
		add(bg1);
		add(bg2);
		add(bg3);
		add(decksize);
		add(graveyardsize);
		add(handsize);

	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		this.player = game.getGameData().getPlayer(playerIndex+1);
		
		decksize.setText(player.getDeckSize() + "");
		ArrayList<Card> grave = player.getGraveyard();
		graveyardsize.setText(grave.size() + "");
		if(player.getName() == game.getTurnPlayer().getName())
			playername.setColor(UIConfig.turnOverColor);
		else
			playername.setColor(Color.WHITE);
		handsize.setText(player.getHandSize()+ "");
			
		int i;
		for( i= 0; i < grave.size()-1; i++)
			 if( i > 30)
				break;
			else if(i+1%3 == 0)
				continue;
		    else
				batch.draw(UIConfig.cardBack, UIConfig.hiddenGraveyardX ,
					UIConfig.hiddenDeckY+ i*2/3 * UIConfig.hiddenDeckCardStep + index * UIConfig.hiddenDeckHeight +index *UIConfig.hiddenDeckStep, 
					UIConfig.hiddenDeckWidth, 
					UIConfig.hiddenDeckHeight);
		
		if(grave.size() > 0)
			batch.draw(grave.get(grave.size()-1).getTexture(), UIConfig.hiddenGraveyardX,
					UIConfig.hiddenDeckY + i*2/3 * UIConfig.hiddenDeckCardStep + index * UIConfig.hiddenDeckHeight +index *UIConfig.hiddenDeckStep, 
					UIConfig.hiddenDeckWidth,
					UIConfig.hiddenDeckHeight);
	
	
	
		for(i = 0; i < player.getDeckSize(); i++)
			if(i > 30)
				break;
			else if(i+1%3 == 0)
				continue;
			else 
				batch.draw(UIConfig.cardBack, UIConfig.hiddenDeckX,
					UIConfig.hiddenDeckY + i*2/3 * UIConfig.hiddenDeckCardStep+ index * UIConfig.hiddenDeckHeight +index *UIConfig.hiddenDeckStep ,
					UIConfig.hiddenDeckWidth,
					UIConfig.hiddenDeckHeight);
		
		float handWidth = UIConfig.hiddenDeckWidth *0.9f;
		float handHeight = UIConfig.hiddenDeckHeight *0.9f;
		for(i = 0; i < player.getHandSize(); i++)
		{
			float rotation = 0;
			if(player.getHandSize() % 2 == 1)
				rotation = 90 -(player.getHandSize()/2 - i) * 70/player.getHandSize();
			else
				rotation = 90 -(player.getHandSize()/2 -0.5f - i) * 70/player.getHandSize();
			if(i > 10)
				break;
			else
				batch.draw(UIConfig.cardBack, 
						UIConfig.hiddenHandX - handWidth/2,
						UIConfig.hiddenDeckY + index * UIConfig.hiddenDeckHeight + UIConfig.hiddenDeckHeight/2 + 25+index *UIConfig.hiddenDeckStep,
						handWidth/2,
						-25,
						handWidth,
						handHeight,
						1,
						1,
						-rotation,
						0,
						0,
						UIConfig.cardBack.getWidth(),
						UIConfig.cardBack.getHeight(),
						false,
						false
						);
			
		}	
		bg3.setPosition(UIConfig.hiddenHandX  , UIConfig.hiddenDeckY + index * UIConfig.hiddenDeckHeight+ UIConfig.hiddenDeckHeight/2 +index *UIConfig.hiddenDeckStep - UIConfig.coinSize/2  );
		handsize.setPosition(UIConfig.hiddenHandX, UIConfig.hiddenDeckY + index * UIConfig.hiddenDeckHeight+ UIConfig.hiddenDeckHeight/2 +index *UIConfig.hiddenDeckStep - UIConfig.coinSize/2 );
		playername.setPosition(UIConfig.hiddenPlayerX, UIConfig.hiddenDeckY + index * UIConfig.hiddenDeckHeight+ UIConfig.hiddenDeckHeight/2 +index *UIConfig.hiddenDeckStep - UIConfig.coinSize/2 );
		
		Array<Actor> children = getChildren();
		for ( i = 0; i < children.size; i++)
			children.get(i).draw(batch, parentAlpha);
		
		
	}
}
