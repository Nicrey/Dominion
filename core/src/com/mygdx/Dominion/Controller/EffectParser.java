package com.mygdx.Dominion.Controller;

import com.mygdx.Dominion.model.Board;
import com.mygdx.Dominion.model.Card;
import com.mygdx.Dominion.model.Player;

public class EffectParser {
	
	private DominionController game;
	
	public EffectParser(DominionController game)
	{
		this.game = game;
	}
	
	public void resolveEffect(Card c)
	{
		Player p = game.getTurnPlayer();
		p.addActions(2);
		
	}
	
	public void resolveSingleEffect(String s)
	{
		
	}
}
