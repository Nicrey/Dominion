package com.mygdx.Dominion.model;

import java.util.ArrayList;
import java.util.Collections;

import com.mygdx.Dominion.Controller.EffectParser;

public class Player {

	private String name;
	private ArrayList<Card> hand;
	private ArrayList<Card> graveyard;
	private ArrayList<Card> deck;
	private int actions;
	private int gold;
	private int buys;

	
	public Player(String name)
	{
		this.name = name;
		actions = 0;
		gold = 0;
		buys = 0;
		hand = new ArrayList<Card>();
		graveyard = new ArrayList<Card>();
		deck = new ArrayList<Card>();
	}
	
	public Player()
	{
		this.name = "No Name";
		actions = 0;
		gold = 0;
		buys = 0;
		hand = new ArrayList<Card>();
		graveyard = new ArrayList<Card>();
		deck = new ArrayList<Card>();
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public ArrayList<Card> getHand() {
		return hand;
	}


	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}
	
	public void drawCard()
	{
		if(deck.size() > 0 )
		{
			hand.add(deck.remove(0));
			return;
		}
		
		if(graveyard.size() > 0)
		{
			deck.addAll(graveyard);
			graveyard.clear();
			shuffleDeck();
			drawCard();
		}
		
		
	}
	
	public void drawCards(int amount) {
		for(int i = 0; i < amount;i++)
		{
			drawCard();
		}
	}


	public void discardCards() {
		graveyard.addAll(hand);
		hand.clear();
	}

	
	public boolean playCard(Card card)
	{
		for(Card c : hand)
		{
			if( c.equals(card))
			{
				hand.remove(card);
				return true;
			}
		}
		return false;
	}

	public void addCardToGraveyard(Card card)
	{
		graveyard.add(card);
	}
	

	public void shuffleGraveyardIntoDeck() {
		deck.addAll(graveyard);
		graveyard.clear();
		shuffleDeck();
	}
	

	public void putDeckInGraveyard() {
		graveyard.addAll(deck);
		deck.clear();
	}
	
	public void shuffleDeck()
	{
		Collections.shuffle(deck);
	}
	

	public int getActions() {
		return actions;
	}


	public void setActions(int actions) {
		this.actions = actions;
	}
	
	public void addActions(int amount)
	{
		actions = actions+amount;
	}
	
	public void reduceActions()
	{
		actions= actions-1;
	}


	public int getGold() {
		return gold;
	}


	public void setGold(int gold) {
		this.gold = gold;
	}
	
	public void addGold(int amount)
	{
		gold = gold + amount;
	}

	public void reduceGold(int amount)
	{
		gold = gold-amount;
	}

	public int getBuys() {
		return buys;
	}


	public void setBuys(int buys) {
		this.buys = buys;
	}
	
	public void reduceBuys()
	{
		buys = buys-1;
	}
	
	public void addBuys(int amount)
	{
		buys = buys + amount;
	}


	public ArrayList<Card> getTreasureCardsInHand() {
		ArrayList<Card> ret = new ArrayList<Card>();
		for(Card c: hand)
		{
			if(c.getType() == GameUtils.CARDTYPE_TREASURE)
			{
				ret.add(c);
			}
		}
		return ret;
	}


	public ArrayList<Card> getActionCardsInHand() {
		ArrayList<Card> ret = new ArrayList<Card>();
		for(Card c: hand)
		{
			if(c.getType() == GameUtils.CARDTYPE_ACTION)
			{
				ret.add(c);
			}
		}
		return ret;
	}


	public boolean hasDefenseCard() {
		for(Card c : hand)
		{
			if(EffectParser.isDefenseCard(c.getEffect()))
				return true;
		}
		return false;
	}


	public Card getDefenseCard() {
		for(Card c : hand)
		{
			if(EffectParser.isDefenseCard(c.getEffect()))
				return c;
		}
		return null;
	}
	
	public void addCurse() {
		this.graveyard.add(new Card(GameUtils.CARD_CURSE));
	}


	public Card lookAtNextCard() {
		if(deck.size() > 0 )
			return deck.get(0);
		
		if(graveyard.size() > 0)
		{
			deck.addAll(graveyard);
			graveyard.clear();
			shuffleDeck();
			return lookAtNextCard();
		}
		return null;
		
	}


	public void discardTopDeckCard() {
		if(deck.size() > 0 )
		{
			graveyard.add(deck.remove(0));
			return;
		}
		
		if(graveyard.size() > 0)
		{
			deck.addAll(graveyard);
			graveyard.clear();
			shuffleDeck();
			discardTopDeckCard();
		}
		
	}


	public int getCompleteDeckSize() {
		return deck.size() + graveyard.size() + hand.size();
	}
	public int getHandSize()
	{
		return hand.size();
	}


	public int getDeckSize() {
		return deck.size();
	}


	public ArrayList<Card> getGraveyard() {
		return graveyard;
	}

	public void clear() {
		actions = 0;
		gold = 0;
		buys = 0;
		hand = new ArrayList<Card>();
		graveyard = new ArrayList<Card>();
		deck = new ArrayList<Card>();
	}

	public ArrayList<Card> getVictoryCards() {
		ArrayList<Card> ret = new ArrayList<Card>();
		ArrayList<Card> completeDeck = (ArrayList<Card>) hand.clone();
		completeDeck.addAll(deck);
		completeDeck.addAll(graveyard);
		for(Card c : completeDeck){
			if(c.getType() == GameUtils.CARDTYPE_VICTORY || c.getType() == GameUtils.CARDTYPE_CURSE)
				ret.add(c);
		}
		return ret;
	}







	
}
