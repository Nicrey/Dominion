package com.mygdx.Dominion.model;

import java.util.ArrayList;
import java.util.Collections;

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
	
	@SuppressWarnings("unchecked")
	public void drawCard()
	{
		if(deck.size() > 0 )
		{
			hand.add(deck.remove(0));
			return;
		}
		
		if(graveyard.size() > 0)
		{
			deck = (ArrayList<Card>) graveyard.clone();
			graveyard.clear();
			shuffleDeck();
		}
		
		
	}
	
	public boolean playCard(Card card)
	{
		for(Card c : hand)
		{
			if( c == card)
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


	public int getGold() {
		return gold;
	}


	public void setGold(int gold) {
		this.gold = gold;
	}


	public int getBuys() {
		return buys;
	}


	public void setBuys(int buys) {
		this.buys = buys;
	}
	
	
}
