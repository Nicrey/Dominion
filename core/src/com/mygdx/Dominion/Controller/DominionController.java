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
	
	
	public void endTurn() throws InterruptedException
	{
		if(view.isDisabled())
			return;
		Thread end = new Thread(new endTurn());
		end.start();
	}
	
	private class endTurn implements Runnable
	{

		@Override
		public void run() {
			view.disableUI();
			while(view.isRendering())
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			getTurnPlayer().discardCards();

			while(view.isRendering())
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			game.putPlayedInCardsInGraveyard(currentPlayer);
		
			
			while(view.isRendering())
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			getTurnPlayer().drawCards(5);
			
			if(currentPlayer == game.getPlayerCount()-1)
				currentPlayer = 0;
			else
				currentPlayer++;
			
			view.showNewPlayer();
			state = ACTIONCARDPHASE;

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while(view.isRendering())
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		
			resetPlayerAttributes();
			view.stopShowingNewPlayer();
			view.enableUI();
		}
		
	}
	

	public void endActions() {
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
		if(view.isDisabled())
			return;
		
		if(state == ACTIONCARDPHASE && c.getType() != GameUtils.CARDTYPE_ACTION)
		{
			return;
		}
		if(state == TREASURECARDPHASE && c.getType() != GameUtils.CARDTYPE_TREASURE)
		{
			return;
		}
		if(getTurnPlayer().getActions() <= 0 && state == ACTIONCARDPHASE)
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
		if(view.isDisabled())
			return;
		if(getTurnPlayer().getBuys() <= 0)
		{
			return;
		}
		if(getTurnPlayer().getGold() < c.getCost())
		{
			return;
		}
		
		if(game.getRemainingCards(c) <= 0)
			return;
		
		getTurnPlayer().addCardToGraveyard(new Card(c));
		game.buyCard(c);
		getTurnPlayer().reduceBuys();
		getTurnPlayer().reduceGold(c.getCost());
		
		if(game.isProvincesEmpty() || game.getEmptiedCardStacks() >= 3)
			System.out.println("ENDE!");
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
		if(view.isDisabled())
			return;
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
		if(view.isDisabled())
			return;
		if(state == ACTIONCARDPHASE && getTurnPlayer().getActionCardsInHand().size() == 0)
			updateState();

		
	}


	public ArrayList<Player> getDefenselessPlayers() {
		ArrayList<Player> attackedPlayers = new ArrayList<Player>();
		for(Player p : game.getPlayers())
		{
			if(!p.hasDefenseCard() && p != getTurnPlayer())
				attackedPlayers.add(p);
		}
		return attackedPlayers;
	}


	public void addCurseToPlayer(Player p) {
		if(view.isDisabled())
			return;
		
		if(game.getRemainingCards(GameUtils.CARD_CURSE) <= 0)
			return;
		p.addCurse();
		game.reduceCurses();
	}


	public void drawForOtherPlayers(int amount) {
		if(view.isDisabled())
			return;
		for(Player p : game.getPlayers())
		{
			if(p != getTurnPlayer())
				p.drawCards(amount);
		}
	}


	public DominionUI getView() {
		return view;
	}


	public boolean isBuyable(Card c) {
		if(c.getCost() <= getTurnPlayer().getGold() && getTurnPlayer().getBuys() > 0)
			return true;
		return false;
	}


	public boolean isTurnOver() {
		if(state == TREASURECARDPHASE && getTurnPlayer().getBuys() == 0 )
			return true;
		return false;
	}


	
}
