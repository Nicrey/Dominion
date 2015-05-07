package com.mygdx.Dominion.Controller;

import java.util.ArrayList;

import com.mygdx.Dominion.model.Card;
import com.mygdx.Dominion.model.GameUtils;
import com.mygdx.Dominion.model.Player;

public class EffectParser {
	
	private DominionController game;
	
	public EffectParser(DominionController game)
	{
		this.game = game;
	}
	
	public void resolveEffect(Card c)
	{
		for(String s : c.getEffect().split("\\|"))
		{
			
			resolveSingleEffect(formatString(s));
		}
		
	}
	
	private static String formatString(String s)
	{
		return s.toLowerCase();
	}
	
	// 		A  => Gain a Number of Actions
	//		D  => Draw a Number of Cards
	//		G  => Gain a Number of Gold
	//		B  => Gain a Number of Buys
	//		C  => Give every other player an amount of Curses ( C,+1,Atk is the correct syntax to define a curse attack)
	//		MIN  => Effect of the mine card 
	//		LIB  => Effect of the library Card
	//		DOP  => DRAW OTHER PLAYERS, Lets all other players draw an amount of Cards
	//		THR  => Effect of the throne room card
	//		THF  => Effect of the thief card
	//		SPY  => Effect of the Spy card
	//		RMD  => Effect of the Remodel Card
	//		MLD  => Effect of the Moneylender Card
	//		MIL  => Effect of the Militia Card, Discard of all players down to amount
	//		GRD  => Effect of the Gardens Card
	//		FST  => Effect of the Feast Card
	//		BRC  => Effect of the Bureaucrat Card	
	//		WKS  => Gain a card up to amount gold, WORKSHOP effect
	//		CEL  => Effect of the cellar card
	//		THR  => effect of Chapel, Trash a number of cards
	//		DEF  => DEFENSIVE card that can be used against attacks
	//		ADV  => EFFECt of the adventurer card
	//		CAN  => chancellor
	private void resolveSingleEffect(String s)
	
	{
		
		String[] parts = s.split(",");
		ArrayList<Player> attackedPlayers = new ArrayList<Player>();
		
		if(isAttackCard(s))
			attackedPlayers = game.getDefenselessPlayers();
		
		//Normal Card Effects
		if(parts[0].equals("a"))
			game.getTurnPlayer().addActions(Integer.parseInt(parts[1]));
		if(parts[0].equals("d"))
			game.getTurnPlayer().drawCards(Integer.parseInt(parts[1]));
		if(parts[0].equals("g"))
			game.getTurnPlayer().addGold(Integer.parseInt(parts[1]));
		if(parts[0].equals("b"))
			game.getTurnPlayer().addBuys(Integer.parseInt(parts[1]));
		if(parts[0].equals("dop"))
			game.drawForOtherPlayers(Integer.parseInt(parts[1]));
		if(parts[0].equals("can"))
			game.getTurnPlayer().putDeckInGraveyard();
		
		if(parts[0].equals("adv"))
		{
			Thread effect = new Thread(new adventurerEffect());
			effect.start();
			
		}
		
		//Attack Cards
		if(parts[0].equals("c"))
		{
			for(Player p : attackedPlayers)
				game.addCurseToPlayer(p);
		}
		
		//Victory Effects
	
		
	}

	private static boolean isAttackCard(String s) {
		if(formatString(s).contains("atk"))
			return true;
		return false;
	}

	public static boolean isDefenseCard(String s) {
		if(formatString(s).contains("def"))
			return true;
		return false;
	}
	
	private class adventurerEffect implements Runnable
	{

		@Override
		public void run() {
			int count = 0;
			int deckcount = 0;
			game.getView().disableUI();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			int possibleDraws = game.getTurnPlayer().getCompleteDeckSize()-game.getTurnPlayer().getHandSize()+game.getTurnPlayer().getDeckSize();
			while(count < 2 && deckcount < possibleDraws)
			{
				
				if(game.getTurnPlayer().lookAtNextCard() == null)
					break;
				
				game.getView().showCardForAdventurer(game.getTurnPlayer().lookAtNextCard());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				game.getView().stopShowingCard();
				if(game.getTurnPlayer().lookAtNextCard().getType() == GameUtils.CARDTYPE_TREASURE)
				{
					count++;
					game.getTurnPlayer().drawCard();
				}else
					game.getTurnPlayer().discardTopDeckCard();
				deckcount++;
				try {
					Thread.sleep(300);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			game.getView().enableUI();
		}
		
	}
}
