package com.mygdx.Dominion.model;

import java.util.ArrayList;

public class Board {

	private ArrayList<Player> players;
	private ArrayList<Card> playedCards;
	private IntegerCardList buyableActionCards;
	private IntegerCardList buyableVictoryCards;
	private IntegerCardList buyableTreasureCards;
	private int buyableCurseCards;
	private int emptiedCardStacks = 0;
	private boolean provincesEmpty = false;;



	private ArrayList<Card> completeCardSet;
	private int numberOfBuyableActionCards = 10;
	
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
			p.drawCards(10);
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
			if(c.getType() == GameUtils.CARDTYPE_VICTORY )
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
		
		buyableCurseCards = 30;
		
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

	public IntegerCardList getBuyableVictoryCards() {
		return buyableVictoryCards;
	}

	public IntegerCardList getBuyableActionCards() {
		return buyableActionCards;
	}

	public IntegerCardList getBuyableTreasureCards() {
		return buyableTreasureCards;
	}

	public void buyCard(Card c) {
		boolean empty = false;
		if(c.getType() == GameUtils.CARDTYPE_ACTION)
		{
			buyableActionCards.reduceCard(c);	
			if(buyableActionCards.getRemainingCards(c) == 0)
				empty = true;
		}
		if(c.getType() == GameUtils.CARDTYPE_TREASURE)
		{
			buyableTreasureCards.reduceCard(c);	
			if(buyableTreasureCards.getRemainingCards(c) == 0)
				empty = true;
		}
		if(c.getType() == GameUtils.CARDTYPE_VICTORY)
		{
			buyableVictoryCards.reduceCard(c);
			if(buyableVictoryCards.getRemainingCards(c) == 0)
				empty = true;
			if(c == GameUtils.CARD_PROVINCE && buyableVictoryCards.getRemainingCards(c) == 0)
				this.provincesEmpty = true;
		}
		if(c.getType() == GameUtils.CARDTYPE_CURSE)
		{
			reduceCurses();
		}
		if(empty)
			this.emptiedCardStacks++;
		
	}

	public int getRemainingCards(Card c) {
		if(c.getType() == GameUtils.CARDTYPE_ACTION)
			return buyableActionCards.getRemainingCards(c);
		if(c.getType() == GameUtils.CARDTYPE_TREASURE)
			return buyableTreasureCards.getRemainingCards(c);
		if(c.getType() == GameUtils.CARDTYPE_VICTORY)
			return buyableVictoryCards.getRemainingCards(c);
		if(c.getType() == GameUtils.CARDTYPE_CURSE)
			return buyableCurseCards;
		return 0;
	}
		

	public int getEmptiedCardStacks() {
		return emptiedCardStacks;
	}

	public boolean isProvincesEmpty() {
		return provincesEmpty;
	}

	public ArrayList<Player> getPlayers() {
		// TODO Auto-generated method stub
		return players;
	}

	public void reduceCurses() {
		if(buyableCurseCards <= 0)
			return;
		buyableCurseCards--;
		if(buyableCurseCards == 0)
			this.emptiedCardStacks++;
	}
}
