package com.mygdx.Dominion.model;

import java.util.ArrayList;

public class Board {

	private ArrayList<Player> players;
	private ArrayList<Card> playedCards;
	private IntegerCardList buyableActionCards;
	private IntegerCardList buyableVictoryCards;
	private IntegerCardList buyableTreasureCards;
	private ArrayList<Card> completeCardSet;
	private int numberOfBuyableActionCards = 1;
	
	public Board()
	{
		
		players = new ArrayList<Player>();
		buyableActionCards = new IntegerCardList();
		buyableVictoryCards = new IntegerCardList();
		buyableTreasureCards = new IntegerCardList();
		completeCardSet = new ArrayList<Card> ();
		playedCards = new ArrayList<Card>();
		
		
		//Create Players
		for(int i = 0; i < Options.getInstance().getPlayerCount();i++)
		{
			players.add(new Player("Player "+ i));
		}
		assert(Options.getInstance().getPlayerCount() == players.size());
		
		//Create Cards
		completeCardSet.addAll(GameUtils.getCardSet("default"));
		
		//Create Buyable Cards
		createBuyableCards();
		
		//Initializes Decks for Players with 7 Coppers 3 Estates
		initializeDecksForPlayers();
		
		//initial card Draw
		for(Player p : players)
		{
			p.drawCards(5);
		}
		
	}

	private void initializeDecksForPlayers() {
		
		for(Player p : players)
		{
			for(int i = 0; i < 7; i++)
			{
				p.addCardToGraveyard(new Card(GameUtils.CARD_COPPER));
			}
			for(int i = 0; i < 3; i++)
			{
				p.addCardToGraveyard(new Card(GameUtils.CARD_ESTATE));
			}
			p.shuffleGraveyardIntoDeck();
	
		}
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
		System.out.println(randCards.size());
		
		for(int i = 0; i < numberOfBuyableActionCards; i++)
		{
			int random = (int) (Math.random() * randCards.size());
			buyableActionCards.addCard(randCards.remove(random));
		}
		
		
		
	}

	public void addCardToBoard(Card c) {
		playedCards.add(c);
	}
	
	public Player getPlayer(int index)
	{
		return players.get(index);
	}

	public int getPlayerCount()
	{
		return players.size();
	}

	public void putPlayedInCardsInGraveyard(int currentPlayer) {
		Player p = players.get(currentPlayer);
		for(Card c: playedCards)
		{
			p.addCardToGraveyard(c);
		}
		playedCards.clear();
	}

	public ArrayList<Card> getBoard() {
		return playedCards;
	}

	
		
	
}
