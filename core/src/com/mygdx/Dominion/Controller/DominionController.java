package com.mygdx.Dominion.Controller;

import com.mygdx.Dominion.UI.DominionUI;
import com.mygdx.Dominion.model.Board;
import com.mygdx.Dominion.model.Card;
import com.mygdx.Dominion.model.GameUtils;
import com.mygdx.Dominion.model.Player;

public class DominionController {

	private static final int ACTIONCARDPHASE = 0;
	private static final int TREASURECARDPHASE = 1;
	private static final int ENDPHASE = 2;
	
	private DominionUI view; 
	private int currentPlayer;
	private Board game;
	private int state;
	
	
	public DominionController(DominionUI view )
	{
		state = 0;
		this.view = view;
		game = new Board();
		currentPlayer = 0;
	}
	
	
	private void resetPlayerAttributes()
	{
		getTurnPlayer().setActions(1);
		getTurnPlayer().setGold(0);
		getTurnPlayer().setBuys(1);
	}
	
	private void updateState() {
		if(state == ACTIONCARDPHASE)
		{
			state = TREASURECARDPHASE;
		}
		if(state == TREASURECARDPHASE)
		{
			state = ENDPHASE;
		}
		if(state == ENDPHASE)
		{
			state = ACTIONCARDPHASE;		
		}
	}
	
	
	public void endTurn()
	{
		game.putPlayedInCardsInGraveyard(currentPlayer);
		getTurnPlayer().discardCards();
		getTurnPlayer().drawCards(5);
		//TODO: Win
		if(currentPlayer == game.getPlayerCount()-1)
			currentPlayer = 0;
		else
			currentPlayer++;
		
		state = ACTIONCARDPHASE;
		resetPlayerAttributes();
	}
	

	
	/**
	 * called whenever a card is played
	 * Resolves the effects of the card and adds it to the board as a played card
	 * and removes it from the hand of the player
	 * @param c the card that was played
	 */
	public void cardPlayed(Card c)
	{
		if(state == ACTIONCARDPHASE && c.getType() != GameUtils.CARDTYPE_ACTION)
		{
			return;
		}
		if(state == TREASURECARDPHASE && c.getType() != GameUtils.CARDTYPE_TREASURE)
		{
			return;
		}
		if(getTurnPlayer().getActions() <= 0)
		{
			return;
		}
		EffectParser.resolveEffect(game, c,currentPlayer);
		getTurnPlayer().playCard(c);
		game.addCardToBoard(c);
		getTurnPlayer().reduceActions();
		
		if(getTurnPlayer().getActions() == 0)
		{
			updateState();
		}
	}
	
	

	/**
	 * called whenever a card is bought 
	 * creates a copy of that card and adds it to the players deck
	 * also reduces gold and buys the player has left
	 * @param c  the card that is bought
	 */
	public void cardBought(Card c)
	{
		if(getTurnPlayer().getBuys() <= 0)
		{
			return;
		}
		if(getTurnPlayer().getGold() < c.getCost())
		{
			return;
		}
		
		getTurnPlayer().addCardToGraveyard(new Card(c));
		getTurnPlayer().reduceBuys();
		getTurnPlayer().reduceGold(c.getCost());
	}

	
	
	public Player getTurnPlayer()
	{
		return game.getPlayer(currentPlayer);
	}
	
}
