package Model;

import java.util.ArrayList;

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
		
		sort();
		
	}
	
	private void sort()
	{
		cardList.sort((i1,i2) -> i1.compareTo(i2));
	}
	
	public void reduceCard(Card c)
	{
		int j = 0;
		int i;
		for( i = 0; i < cardList.size(); i++)
			if(cardList.get(i).getName().equals(c.getName()))
			{
				j = integerList.get(i);
				break;
			}
		
		
		j--;
		integerList.set(i, j);
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
		for(int i = 0; i < cardList.size(); i++)
			if(cardList.get(i).getName().equals(c.getName()))
				return integerList.get(i);
		
		return -1;
	}

	
}
