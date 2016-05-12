package Controller;

import java.io.Serializable;
import java.util.ArrayList;

import Network.DominionClient;
import UI.DominionUI;
import Model.Board;
import Model.Card;
import Model.GameData;
import Model.GameUtils;
import Model.Player;

public class DominionController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3063402530657842508L;
	
	public static final int ACTIONCARDPHASE = 0;
	public static final int TREASURECARDPHASE = 1;
	public static final int ENDPHASE = 2;
	private final int controllerIndex;
	
	private DominionClient server;
	private DominionUI view; 
	private int currentPlayer;
	private Board game;
	private int state;
	private GameData updatedGameData;

	private boolean stateUpdate = false;
	
	
	public DominionController(DominionUI view, int controllerIndex )
	{
		this.controllerIndex = controllerIndex;
		state = 0;
		this.view = view;
		game = new Board("default");
		currentPlayer = 0;
		resetPlayerAttributes();
		
	}
	
	public void setServer(DominionClient server)
	{
		this.server = server;
		server.setIndex(controllerIndex);
		server.setController(this);

	}
	

	
	private void resetPlayerAttributes()
	{
		getTurnPlayer().setActions(1);
		getTurnPlayer().setGold(0);
		getTurnPlayer().setBuys(1);
	}
	
	
	private void updateState()
	{
		
		if(controllerIndex == currentPlayer && !stateUpdate){
			server.updateStateRequest();
			stateUpdate = true;
		}
	
	}
	
	public void updateStateEvent() {
		
		if(state == ACTIONCARDPHASE)
		{
			state = TREASURECARDPHASE;
			stateUpdate = false;
			return;
		}
		
	}
	
	public void endTurn()
	{
		if(getTurnPlayerIndex() == controllerIndex)
			server.endTurnRequest();
	}
	
	public void endTurnEvent() throws InterruptedException
	{
		if(view.isDisabled())
			return;
		Thread end = new Thread(new endTurn());
		end.start();
	}
	
	private class endTurn implements Runnable
	{

		@Override
		public void run() {
			view.disableUI();
			while(view.isRendering())
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			getTurnPlayer().discardCards();

			while(view.isRendering())
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			game.putPlayedInCardsInGraveyard(currentPlayer);
		
			
			while(view.isRendering())
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			getTurnPlayer().drawCards(5);
			
			if(currentPlayer == game.getPlayerCount()-1)
				currentPlayer = 0;
			else
				currentPlayer++;
			if(view.isRendering())
				try {
					Thread.sleep(1);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			view.showNewPlayer();
			state = ACTIONCARDPHASE;

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while(view.isRendering())
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		
			resetPlayerAttributes();
			view.stopShowingNewPlayer();
			view.enableUI();
			updateGameData();
		}
		
	}
	

	public void endActions() {
		if(state == ACTIONCARDPHASE )
			updateState();
	}
	

	public void cardPlayed(Card c)
	{
		if(view.isDisabled())
			return;
	
		if(state == ACTIONCARDPHASE && c.getType() != GameUtils.CARDTYPE_ACTION)
		{
			return;
		}
		if(state == TREASURECARDPHASE && c.getType() != GameUtils.CARDTYPE_TREASURE)
		{
			return;
		}
		if(getTurnPlayer().getActions() <= 0 && state == ACTIONCARDPHASE)
		{
			return;
		}
		
		if(getTurnPlayerIndex() == controllerIndex)
			server.cardPlayedRequest(c, getTurnPlayer().getHand().indexOf(c));
	}
	/**
	 * called whenever a card is played
	 * Resolves the effects of the card and adds it to the board as a played card
	 * and removes it from the hand of the player
	 * @param c the card that was played
	 */
	public void cardPlayedEvent(Card c)
	{
		
		if(view.isDisabled())
			return;
		
		if(state == ACTIONCARDPHASE && c.getType() != GameUtils.CARDTYPE_ACTION)
		{
			return;
		}
		if(state == TREASURECARDPHASE && c.getType() != GameUtils.CARDTYPE_TREASURE)
		{
			return;
		}
		if(getTurnPlayer().getActions() <= 0 && state == ACTIONCARDPHASE)
		{
			return;
		}
		
		getTurnPlayer().playCard(c);
		game.addCardToBoard(c);
		

		if(c.getType() == GameUtils.CARDTYPE_ACTION)
			getTurnPlayer().reduceActions();
		
		if(getTurnPlayer().getActions() == 0
				&& state == ACTIONCARDPHASE
				&& !EffectParser.givesAdditionalActions(c.getEffect()))
		{
			updateStateEvent();
		}
		
		Thread resolve = new Thread( new EffectParser(this,c));
		resolve.start();
	
	}
	
	public void cardBought(Card c)
	{
		if(view.isDisabled())
			return;
		if(getTurnPlayer().getBuys() <= 0)
		{
			return;
		}
		if(getTurnPlayer().getGold() < c.getCost())
		{
			return;
		}
		
		if(game.getRemainingCards(c) <= 0)
			return;
		
		if(getTurnPlayerIndex() == controllerIndex)
			server.cardBoughtRequest(c);
	}

	/**
	 * called whenever a card is bought 
	 * creates a copy of that card and adds it to the players deck
	 * also reduces gold and buys the player has left
	 * @param c  the card that is bought
	 */
	public void cardBoughtEvent(Card c)
	{
		if(view.isDisabled())
			return;
		if(getTurnPlayer().getBuys() <= 0)
		{
			return;
		}
		if(getTurnPlayer().getGold() < c.getCost())
		{
			return;
		}
		
		if(game.getRemainingCards(c) <= 0)
			return;
		
		getTurnPlayer().reduceBuys();
		getTurnPlayer().reduceGold(c.getCost());
		
		Thread showCard = new Thread(new showCardThread(c));
		showCard.start();
		
		
		if(game.isProvincesEmpty() || game.getEmptiedCardStacks() >= 3)
			System.out.println("ENDE!");
		
	}

	private class showCardThread implements Runnable
	{
		Card c;
		
		public showCardThread(Card c) {
			this.c = c;
		}

		@Override
		public void run() {
			view.disableUI();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(view.isRendering())
				try {
					Thread.sleep(1);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			view.showBoughtCard(c);
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			getTurnPlayer().addCardToGraveyard(new Card(c));
			game.buyCard(c);
			
			view.stopShowingCard();
	
			view.enableUI();
			updateGameData();
		}
		
	}
	
	public Player getTurnPlayer()
	{
		return game.getPlayer(currentPlayer);
	}

	public int getState()
	{
		return state;
	}


	public void playTreasures(){
		if(view.isDisabled())
			return;
		server.playTreasuresRequest();
	}
	
	public void playTreasuresEvent() {
		if(view.isDisabled())
			return;
		ArrayList<Card> treasures;
		treasures = getTurnPlayer().getTreasureCardsInHand();
		for(Card c: treasures)
		{
			getTurnPlayer().playCard(c);
			game.addCardToBoard(c);
			EffectParser.parseSingleTreasureCard(c.getEffect(), getTurnPlayer());
		}
		updateGameData();
	}


	public ArrayList<Card> getBoard() {
		return game.getBoard();
	}


	public boolean isCardPlayable(Card card) {
		if(state == ACTIONCARDPHASE && card.getType() == GameUtils.CARDTYPE_ACTION)
		{
			return true;
		}
		if(state == TREASURECARDPHASE && card.getType() == GameUtils.CARDTYPE_TREASURE)
			return true;
		return false;
	}


	public Board getGameData() {
		return game;
	}


	public void update() {
		if(view.isDisabled())
			return;
		if(state == ACTIONCARDPHASE && getTurnPlayer().getActionCardsInHand().size() == 0 )
			updateState();

		
	}


	public ArrayList<Player> getDefenselessPlayers() {
		ArrayList<Player> attackedPlayers = new ArrayList<Player>();
		for(Player p : game.getPlayers())
		{
			if(!p.hasDefenseCard() && p != getTurnPlayer())
				attackedPlayers.add(p);
		}
		return attackedPlayers;
	}


	public void addCurseToPlayer(Player p) {
		
		if(game.getRemainingCards(GameUtils.CARD_CURSE) <= 0)
			return;
		p.addCurse();
		game.reduceCurses();
	}


	public void drawForOtherPlayers(int amount) {
		if(view.isDisabled())
			return;
		for(Player p : game.getPlayers())
		{
			if(p != getTurnPlayer())
				p.drawCards(amount);
		}
	}


	public DominionUI getView() {
		return view;
	}


	public boolean isBuyable(Card c) {
		if(c.getCost() <= getTurnPlayer().getGold() && getTurnPlayer().getBuys() > 0)
			return true;
		return false;
	}


	public boolean isTurnOver() {
		if(state == TREASURECARDPHASE && getTurnPlayer().getBuys() == 0 )
			return true;
		return false;
	}

	public void updateGameData()
	{
		
		if(updatedGameData == null)
			return;
		game = updatedGameData.getBoard();
		currentPlayer = updatedGameData.getPlayer();
		state = updatedGameData.getState();
		stateUpdate = false;
		
	}
	
	public int getControllerIndex()
	{
		return controllerIndex;
	}


	public int getTurnPlayerIndex() {
		return currentPlayer;
	}

	public void setNewGameData(GameData gameData) {
		updatedGameData = gameData;
	}

	public void syncWithServer() {
		server.sync();
	}

	
}
