package com.mygdx.Dominion.model;

import java.util.ArrayList;

public class Board {

	private ArrayList<Player> players;
	private ArrayList<Card> playedCards;
	private IntegerCardList buyableActionCards;
	private IntegerCardList buyableVictoryCards;
	private IntegerCardList buyableTreasureCards;
	private ArrayList<Integer> remainingCards;
	private ArrayList<Card> completeCardSet;
	private int numberOfBuyableActionCards = 1;
	
	public Board()
	{
		//Create Players
		for(int i = 0; i < Options.getInstance().getPlayerCount();i++)
		{
			players.add(new Player("Player "+ i));
		}
		assert(Options.getInstance().getPlayerCount() == players.size());
		
		//Create Cards
		completeCardSet.addAll(GameUtils.getCardSet("Default"));
		
		//Create Buyable Cards
		createBuyableCards();
		
	}

	private void createBuyableCards() {
		ArrayList<Card> randCards = new ArrayList<Card>();
		
		for(Card c: completeCardSet)
		{
			if(c.getType() == GameUtils.CARDTYPE_VICTORY || c.getType() == GameUtils.CARDTYPE_CURSE)
			{
				buyableVictoryCards.addCard(c);
			}
			if(c.getType() == GameUtils.CARDTYPE_TREASURE)
			{
				buyableTreasureCards.addCard(c);
			}
			if(c.getType() == GameUtils.CARDTYPE_ACTION)
			{
				randCards.add(c);
			}
		}
		
		for(int i = 0; i < numberOfBuyableActionCards; i++)
		{
			int random = (int) (Math.random() * randCards.size());
			buyableActionCards.addCard(randCards.remove(random));
		}
		
		
		
	}
	


	
		
	
}
