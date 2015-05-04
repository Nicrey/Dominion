package com.mygdx.Dominion.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class IntegerCardList {

	private ArrayList<Card> cardList;
	private ArrayList<Integer> integerList;
	
	public IntegerCardList()
	{
		cardList = new ArrayList<Card>();
		integerList = new ArrayList<Integer>();
	}
	
	public void addCard(Card c)
	{
		
		cardList.add(c);
		if(c.getType() == GameUtils.CARDTYPE_ACTION)
		{
			integerList.add(10);
		}
		if(c.getType() == GameUtils.CARDTYPE_VICTORY)
		{
			integerList.add(12);
		}
		if(c.getType() == GameUtils.CARDTYPE_CURSE)
		{
			integerList.add(30);
		}
		if(c.getType() == GameUtils.CARDTYPE_TREASURE)
		{
			integerList.add(30);
		}
		
	}
	
	public void reduceCard(Card c)
	{
		int i = integerList.get(cardList.indexOf(c));
		i--;
		integerList.set(cardList.indexOf(c), i);
	}
	
	public Card getCard(int i)
	{
		return cardList.get(i);
	}
	
	public int getRemainingCards(int i)
	{
		return integerList.get(i);
	}

	public int size() {
		return cardList.size();
	}
	
	public int getRemainingCards(Card c)
	{
		return integerList.get(cardList.indexOf(c));
	}

	
}
