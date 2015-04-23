package com.mygdx.Dominion.Controller;

import com.mygdx.Dominion.model.Board;
import com.mygdx.Dominion.model.Card;
import com.mygdx.Dominion.model.Player;

public class EffectParser {

	
	public static void resolveEffect(Board board, Card c, int currentPlayer)
	{
		Player p =board.getPlayer(currentPlayer);
		p.addActions(2);
	}
}
