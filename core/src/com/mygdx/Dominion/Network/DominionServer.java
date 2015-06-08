package com.mygdx.Dominion.Network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.Dominion.Network.Controller.DominionServerController;
import com.mygdx.Dominion.Network.Requests.CardBoughtRequest;
import com.mygdx.Dominion.Network.Requests.CardBoughtResponse;
import com.mygdx.Dominion.Network.Requests.CardPlayedRequest;
import com.mygdx.Dominion.Network.Requests.CardPlayedResponse;
import com.mygdx.Dominion.Network.Requests.GameOverResponse;
import com.mygdx.Dominion.Network.Requests.PlayTreasuresRequest;
import com.mygdx.Dominion.Network.Requests.PlayTreasuresResponse;
import com.mygdx.Dominion.Network.Requests.StartGameRequest;
import com.mygdx.Dominion.Network.Requests.TurnEndRequest;
import com.mygdx.Dominion.Network.Requests.TurnEndResponse;
import com.mygdx.Dominion.Network.Requests.UpdateStateRequest;
import com.mygdx.Dominion.Network.Requests.UpdateStateResponse;
import com.mygdx.Dominion.Network.UI.ServerGameScreen;
import com.mygdx.Dominion.model.Card;
import com.mygdx.Dominion.model.GameData;

public class DominionServer {

	private Server server;
	private ServerGameScreen ui;
	private DominionServerController controller;

	public DominionServer(Server s) {
		this.server = s;
		this.server.addListener(new GameListener());
		System.out
				.println("neuer server" + this.server.getConnections().length);
	}

	public void setUI(ServerGameScreen serverScreen) {
		this.ui = serverScreen;
	}

	public void setController(DominionServerController con) {
		controller = con;
		GameData data = new GameData(con.getBoard(), 0, 0);

		for (int i = 0; i < server.getConnections().length; i++)
			server.sendToUDP(i + 1, new StartGameRequest(i, data));
	}

	private class GameListener extends Listener {
		public void received(Connection connection, Object object) {
			
			
			if(object instanceof TurnEndRequest){
				TurnEndRequest request = (TurnEndRequest) object;
				if(request.getIndex() != controller.getCurrentPlayer())
					return;
				if(controller.endTurnEvent()){
					GameOverResponse response = new GameOverResponse(controller.getBoard().getGameEndVictoryCards(),
																		controller.getBoard().getWinner(),
																		controller.getBoard().getGameEndPoints());
				}else{
					TurnEndResponse response = new TurnEndResponse(controller.getGameData());
					ui.log("Turn End for Player:" + controller.getBoard().getPlayer(request.getIndex()).getName());
					server.sendToAllUDP(response);
				}
			}
			
			if(object instanceof CardPlayedRequest){
				CardPlayedRequest request = (CardPlayedRequest) object;
				if(request.getIndex() != controller.getCurrentPlayer())
					return;
				if(request.getCardIndex() > controller.getTurnPlayer().getHandSize())
				{
					ui.log("Hand is not the same on Client and Server");
					server.sendToAllTCP(controller.getGameData());
					return;
				}
				Card actualCard = controller.getTurnPlayer().getHand().get(request.getCardIndex());
				if(!actualCard.getName().equals( request.getCard().getName())){
					ui.log("Hand is not the same on Client and Server");
					server.sendToAllTCP(controller.getGameData());
					return;
				}
					
				if(!controller.cardPlayedEvent(actualCard)){
					ui.log("Card could not be played by Server");
					return;
				}
				CardPlayedResponse response = new CardPlayedResponse(controller.getGameData(), request.getCard());
				ui.log("Card Played by Player: " + controller.getTurnPlayer().getName() + " -> " + request.getCard().getName());
				server.sendToAllUDP(response);
			}
			
			if(object instanceof CardBoughtRequest){
				CardBoughtRequest request = (CardBoughtRequest) object;
				if(request.getIndex() != controller.getCurrentPlayer())
					return;
				controller.cardBoughtEvent(request.getCard());
				CardBoughtResponse response = new CardBoughtResponse(controller.getGameData(), request.getCard());
				ui.log("Card Bought by Player: " + controller.getTurnPlayer().getName() + " -> " + request.getCard().getName());
				server.sendToAllUDP(response);
			
			}
			
			if(object instanceof UpdateStateRequest){
				UpdateStateRequest request = (UpdateStateRequest) object;
				if(request.getIndex() != controller.getCurrentPlayer())
					return;
				controller.updateStateEvent();
				ui.log("State updated");
				UpdateStateResponse response = new UpdateStateResponse(controller.getGameData());
				server.sendToAllUDP(response);
			}
			
			if(object instanceof PlayTreasuresRequest){
				PlayTreasuresRequest request = (PlayTreasuresRequest) object;
				if(request.getIndex() != controller.getCurrentPlayer())
					return;
				controller.playTreasuresEvent();
				ui.log("Player " + controller.getTurnPlayer().getName()+ " played all treasures");
				PlayTreasuresResponse response = new PlayTreasuresResponse(controller.getGameData());
				server.sendToAllUDP(response);
			}
			
			if(object instanceof String){
				if(object.equals("Sync")){
					server.sendToAllTCP(controller.getGameData());
				}
			}
		}
	}
}
