package com.mygdx.Dominion.Controller;

import java.util.ArrayList;

import com.mygdx.Dominion.model.Card;
import com.mygdx.Dominion.model.GameUtils;
import com.mygdx.Dominion.model.Player;

/**
 * Effect Parser that is used to parse an effect of a card and executing it An
 * effect is of the format "eff1|eff2|eff3"
 * 
 * @author Tim
 *
 */
public class EffectParser implements Runnable {

	private DominionController game;
	private Card c;

	public EffectParser(DominionController game, Card c) {
		this.game = game;
		this.c = c;
	}

	public void setCard(Card c) {
		this.c = c;
	}

	/**
	 * resolves the whole effect of a card by resolving every single one
	 * sequentially
	 * 
	 * @param c
	 * @throws InterruptedException
	 */
	public void resolveEffect(Card c) throws InterruptedException {
		for (String s : c.getEffect().split("\\|")) {

			resolveSingleEffect(formatString(s));
		}

	}

	private static String formatString(String s) {
		return s.toLowerCase();
	}

	// A => Gain a Number of Actions
	// D => Draw a Number of Cards
	// G => Gain a Number of Gold
	// B => Gain a Number of Buys
	// C => Give every other player an amount of Curses ( C,+1,Atk is the
	// correct syntax to define a curse attack)
	// => This is always considered an attack!
	// MIN => Effect of the mine card
	// LIB => Effect of the library Card
	// DOP => DRAW OTHER PLAYERS, Lets all other players draw an amount of Cards
	// THR => Effect of the throne room card
	// THF => Effect of the thief card
	// SPY => Effect of the Spy card
	// RMD => Effect of the Remodel Card
	// MLD => Effect of the Moneylender Card
	// MIL => Effect of the Militia Card, Discard of all players down to amount
	// GRD => Effect of the Gardens Card
	// FST => Effect of the Feast Card
	// BRC => Effect of the Bureaucrat Card
	// WKS => Gain a card up to amount gold, WORKSHOP effect
	// CEL => Effect of the cellar card
	// THR => effect of Chapel, Trash a number of cards
	// DEF => DEFENSIVE card that can be used against attacks
	// ADV => EFFECt of the adventurer card
	// CAN => chancellor
	/**
	 * resolvess a single card effect This starts new threads for certain
	 * effects like adventurer to not be instant
	 * 
	 * @param s
	 * @throws InterruptedException
	 */
	private void resolveSingleEffect(String s) throws InterruptedException

	{

		String[] parts = s.split(",");
		ArrayList<Player> attackedPlayers = new ArrayList<Player>();

		if (isAttackCard(s))
			attackedPlayers = game.getDefenselessPlayers();

		// Normal Card Effects
		if (parts[0].equals("a"))
			game.getTurnPlayer().addActions(Integer.parseInt(parts[1]));
		if (parts[0].equals("d"))
			runDrawEffect(Integer.parseInt(parts[1]));
		if (parts[0].equals("g"))
			game.getTurnPlayer().addGold(Integer.parseInt(parts[1]));
		if (parts[0].equals("b"))
			game.getTurnPlayer().addBuys(Integer.parseInt(parts[1]));
		if (parts[0].equals("dop"))
			game.drawForOtherPlayers(Integer.parseInt(parts[1]));
		if (parts[0].equals("can"))
			game.getTurnPlayer().putDeckInGraveyard();

		if (parts[0].equals("adv"))
			runAdventurerEffect();

		// Attack Cards
		if (parts[0].equals("c"))
			runWitchEffect();

		// Victory Effects

		game.updateGameData();

	}

	/**
	 * checks if the effect of a card has an ATK to signify that it is an attack
	 * card
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isAttackCard(String s) {
		if (formatString(s).contains("atk"))
			return true;
		return false;
	}

	/**
	 * checks if the effect of a card has an DEF to signify that it is an
	 * defense card
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isDefenseCard(String s) {
		if (formatString(s).contains("def"))
			return true;
		return false;
	}

	public static boolean givesAdditionalActions(String s) {
		if (formatString(s).contains("a+"))
			return true;
		return false;
	}
	
	public static boolean parseSingleTreasureCard(String s, Player p){
		if(s.length() > 4)
			return false;
		String[] parts = s.split(",");
		if (parts[0].equals("g"))
			p.addGold(Integer.parseInt(parts[1]));
		return true;
		
	}

	public void runDrawEffect(int amount) {
		game.getView().disableUI();

		for (int i = 0; i < amount; i++) {

			try {
				Thread.sleep(500);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
			if (game.getTurnPlayerIndex() == game.getControllerIndex())
				game.getView().showCardWithText(
						game.getTurnPlayer().lookAtNextCard(), "Karte gezogen");
			else
				game.getView().showCardWithText(
						GameUtils.CARD_BACK, "Karte gezogen");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
			game.getView().stopShowingCard();
			game.getTurnPlayer().drawCard();

		}
		game.getView().enableUI();
	}

	public void runAdventurerEffect() {
		int count = 0;
		int deckcount = 0;
		game.getView().disableUI();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		int possibleDraws = game.getTurnPlayer().getCompleteDeckSize()
				- game.getTurnPlayer().getHandSize()
				+ game.getTurnPlayer().getDeckSize();
		while (count < 2 && deckcount < possibleDraws) {

			if (game.getTurnPlayer().lookAtNextCard() == null)
				break;

			game.getView().showCardForAdventurer(
					game.getTurnPlayer().lookAtNextCard());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			game.getView().stopShowingCard();
			if (game.getTurnPlayer().lookAtNextCard().getType() == GameUtils.CARDTYPE_TREASURE) {
				count++;
				game.getTurnPlayer().drawCard();
			} else
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

	public void runWitchEffect() {
		game.getView().disableUI();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		for (Player p : game.getGameData().getPlayers()) {
			if (p == game.getTurnPlayer())
				continue;
			if (!p.hasDefenseCard()) {
				game.getView().showDefenseOrCurseCard(GameUtils.CARD_CURSE, p);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				game.getView().stopShowingCard();
				game.addCurseToPlayer(p);
			} else {
				game.getView().showDefenseOrCurseCard(p.getDefenseCard(), p);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				game.getView().stopShowingCard();
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		game.getView().enableUI();
	}

	@Override
	public void run() {
		try {
			resolveEffect(c);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int parseVictoryCard(Card c, Player p) {
		for (String s : c.getEffect().split("\\|")) {
			s = formatString(s);
			if(s.startsWith("v")){
				String[] parts = s.split(",");
				return Integer.parseInt(parts[1]);
			}
			if(s.startsWith("grd")){
				return p.getCompleteDeckSize()/10;
			}
		}
		return 0;
	}
}
