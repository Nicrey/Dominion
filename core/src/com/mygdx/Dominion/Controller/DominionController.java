package com.mygdx.Dominion.Controller;

import java.util.ArrayList;

import com.mygdx.Dominion.UI.DominionUI;
import com.mygdx.Dominion.model.Board;
import com.mygdx.Dominion.model.Card;
import com.mygdx.Dominion.model.GameUtils;
import com.mygdx.Dominion.model.Player;

public class DominionController {

	public static final int ACTIONCARDPHASE = 0;
	public static final int TREASURECARDPHASE = 1;
	public static final int ENDPHASE = 2;
	
	private DominionUI view; 
	private int currentPlayer;
	private Board game;
	private int state;
	private EffectParser parser;
	
	
	public DominionController(DominionUI view )
	{
		state = 0;
		this.view = view;
		game = new Board();
		currentPlayer = 0;
		resetPlayerAttributes();
		parser = new EffectParser(this);
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
			return;
		}
		if(state == TREASURECARDPHASE)
		{
			state = ENDPHASE;
			return;
		}
		if(state == ENDPHASE)
		{
			state = ACTIONCARDPHASE;		
			return;
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
	

	public void endActions() {
		System.out.println("EndActions");
		System.out.println(state);
		if(state == ACTIONCARDPHASE)
			updateState();
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
		parser.resolveEffect(c);
		getTurnPlayer().playCard(c);
		game.addCardToBoard(c);
		
		if(c.getType() == GameUtils.CARDTYPE_ACTION)
			getTurnPlayer().reduceActions();
		
		if(getTurnPlayer().getActions() == 0 && state == ACTIONCARDPHASE)
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
		game.buyCard(c);
		getTurnPlayer().reduceBuys();
		getTurnPlayer().reduceGold(c.getCost());
	}

	
	
	public Player getTurnPlayer()
	{
		return game.getPlayer(currentPlayer);
	}

	public int getState()
	{
		return state;
	}


	public void playTreasures() {
		ArrayList<Card> treasures;
		treasures = getTurnPlayer().getTreasureCardsInHand();
		for(Card c: treasures)
		{
			cardPlayed(c);
		}
	}


	public ArrayList<Card> getBoard() {
		return game.getBoard();
	}


	public boolean isCardPlayable(Card card) {
		if(state == ACTIONCARDPHASE && card.getType() == GameUtils.CARDTYPE_ACTION)
		{
			return true;
		}
		if(state == TREASURECARDPHASE && card.getType() == GameUtils.CARDTYPE_TREASURE)
			return true;
		return false;
	}


	public Board getGameData() {
		return game;
	}


	public void update() {
		if(state == ACTIONCARDPHASE && getTurnPlayer().getActionCardsInHand().size() == 0)
			updateState();

		
	}


	
}
