package Network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import Controller.DominionController;
import Network.Requests.CardBoughtRequest;
import Network.Requests.CardBoughtResponse;
import Network.Requests.CardPlayedRequest;
import Network.Requests.CardPlayedResponse;
import Network.Requests.GameOverResponse;
import Network.Requests.PlayTreasuresRequest;
import Network.Requests.PlayTreasuresResponse;
import Network.Requests.TurnEndRequest;
import Network.Requests.TurnEndResponse;
import Network.Requests.UpdateStateRequest;
import Network.Requests.UpdateStateResponse;
import Model.Card;
import Model.GameData;

public class DominionClient {

	private Client client;
	private int index;
	private DominionController controller;
	
	public DominionClient(Client c)
	{
		this.client = c;
		c.addListener(new GameListener());
		
	}
	
	public void endTurnRequest()
	{
		client.sendTCP(new TurnEndRequest(index));
	}
	
	public void cardBoughtRequest(Card c)
	{
		client.sendTCP(new CardBoughtRequest(c,controller.getState(), index));
	}
	
	public void cardPlayedRequest(Card c, int cardIndex)
	{
		client.sendTCP(new CardPlayedRequest(c, controller.getState(), index, cardIndex));
	}

	public void updateStateRequest() {
		client.sendTCP(new UpdateStateRequest(index));
	}
	

	public void playTreasuresRequest() {
		client.sendTCP(new PlayTreasuresRequest(index));
	}
	
	public void sync(){
		client.sendTCP("Sync");
	}

	public void setIndex(int controllerIndex) {
		index =controllerIndex;
	}

	public void setController(DominionController dominionController) {
		controller = dominionController;
		sync();
	}
	
	private class GameListener extends Listener{
		
		public void received(Connection connection, Object object) {
			if(object instanceof TurnEndResponse)
			{
				TurnEndResponse response = (TurnEndResponse) object;
				controller.setNewGameData(response.getData());
				try {
					controller.endTurnEvent();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(object instanceof CardPlayedResponse){
				CardPlayedResponse response = (CardPlayedResponse) object;
				controller.setNewGameData(response.getData());
				controller.cardPlayedEvent(controller.getTurnPlayer().getHand().get(response.getIndex()));
			}
			
			if(object instanceof CardBoughtResponse){
				CardBoughtResponse response = (CardBoughtResponse) object;
				controller.setNewGameData(response.getData());
				controller.cardBoughtEvent(response.getCard());
			}
			
			if(object instanceof UpdateStateResponse){
				UpdateStateResponse response = (UpdateStateResponse) object;
				controller.setNewGameData(response.getData());
				controller.updateStateEvent();
			}
			
			if(object instanceof PlayTreasuresResponse){
				PlayTreasuresResponse response = (PlayTreasuresResponse) object;
				controller.setNewGameData(response.getData());
				controller.playTreasuresEvent();
			}
			
			if(object instanceof GameData){
				controller.setNewGameData((GameData)object);
				controller.updateGameData();
			}
			
			if(object instanceof GameOverResponse){
				controller.getView().getApplication().showGameEndScreen((GameOverResponse) object);
			}
		}
	}

}

