package com.mygdx.Dominion.Network.Controller;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.mygdx.Dominion.Controller.EffectParser;
import com.mygdx.Dominion.model.Board;
import com.mygdx.Dominion.model.Card;
import com.mygdx.Dominion.model.GameData;
import com.mygdx.Dominion.model.GameUtils;
import com.mygdx.Dominion.model.Player;

public class DominionServerController {

	public static final int ACTIONCARDPHASE = 0;
	public static final int TREASURECARDPHASE = 1;

	Board game;
	int currentPlayer;
	int state;
	DominionServerEffectParser parser;

	public DominionServerController() {

		game = new Board("default");
		currentPlayer = 0;
		state = 0;
		resetPlayerAttributes();
		parser = new DominionServerEffectParser(this);

	}

	private void resetPlayerAttributes() {
		getTurnPlayer().setActions(1);
		getTurnPlayer().setGold(0);
		getTurnPlayer().setBuys(1);
	}

	public Player getTurnPlayer() {
		return game.getPlayer(currentPlayer);
	}

	public void cardBoughtEvent(Card c) {
		if (getTurnPlayer().getBuys() <= 0) {
			return;
		}
		if (getTurnPlayer().getGold() < c.getCost()) {
			return;
		}

		if (game.getRemainingCards(c) <= 0)
			return;

		getTurnPlayer().reduceBuys();
		getTurnPlayer().reduceGold(c.getCost());
		getTurnPlayer().addCardToGraveyard(new Card(c));
		game.buyCard(c);

		
	}

	public boolean cardPlayedEvent(Card c) {

		if (state == ACTIONCARDPHASE
				&& c.getType() != GameUtils.CARDTYPE_ACTION) {
			return false;
		}
		if (state == TREASURECARDPHASE
				&& c.getType() != GameUtils.CARDTYPE_TREASURE) {
			return false;
		}
		if (getTurnPlayer().getActions() <= 0 && state == ACTIONCARDPHASE) {
			return false;
		}

		if(!getTurnPlayer().playCard(c))
			return false;
		game.addCardToBoard(c);

		parser.resolveEffect(c);

		if (c.getType() == GameUtils.CARDTYPE_ACTION)
			getTurnPlayer().reduceActions();

		if (getTurnPlayer().getActions() == 0 && state == ACTIONCARDPHASE
				&& !EffectParser.givesAdditionalActions(c.getEffect())) {
			updateStateEvent();
		}
		return true;

	}

	public boolean endTurnEvent() {

		getTurnPlayer().discardCards();

		game.putPlayedInCardsInGraveyard(currentPlayer);

		getTurnPlayer().drawCards(5);

		if (currentPlayer == game.getPlayerCount() - 1)
			currentPlayer = 0;
		else
			currentPlayer++;

		state = ACTIONCARDPHASE;

		resetPlayerAttributes();
		if(game.isGameOver()){
			return true;
		}
		return false;
	}

	public void updateStateEvent() {
		if (state == ACTIONCARDPHASE) {
			state = TREASURECARDPHASE;
			return;
		}
		
	}

	public GameData getGameData() {
		return new GameData(game, currentPlayer, state);
	}
	
	public Board getBoard()
	{
		return game;
	}

	public ArrayList<Player> getDefenselessPlayers() {
		ArrayList<Player> attackedPlayers = new ArrayList<Player>();
		for (Player p : game.getPlayers()) {
			if (!p.hasDefenseCard() && p != getTurnPlayer())
				attackedPlayers.add(p);
		}
		return attackedPlayers;
	}

	public void drawForOtherPlayers(int amount) {
		for(Player p : game.getPlayers())
		{
			if(p != getTurnPlayer())
				p.drawCards(amount);
		}
	}

	public void addCurseToPlayer(Player p) {
		if(game.getRemainingCards(GameUtils.CARD_CURSE) <= 0)
			return;
		p.addCurse();
		game.reduceCurses();
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void playTreasuresEvent() {
		ArrayList<Card> treasures;
		treasures = getTurnPlayer().getTreasureCardsInHand();
		for(Card c: treasures)
		{
			cardPlayedEvent(c);
		}
	}


}
