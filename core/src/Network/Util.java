package Network;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.esotericsoftware.kryo.Kryo;
import Network.Requests.CardBoughtRequest;
import Network.Requests.CardBoughtResponse;
import Network.Requests.CardPlayedRequest;
import Network.Requests.CardPlayedResponse;
import Network.Requests.GameOverResponse;
import Network.Requests.PlayTreasuresRequest;
import Network.Requests.PlayTreasuresResponse;
import Network.Requests.StartGameRequest;
import Network.Requests.TurnEndRequest;
import Network.Requests.TurnEndResponse;
import Network.Requests.UpdateStateRequest;
import Network.Requests.UpdateStateResponse;
import Model.Board;
import Model.Card;
import Model.GameData;
import Model.IntegerCardList;
import Model.Player;

public class Util {

	
	public static void register(Kryo kryo)
	{
		kryo.register(Player.class);
		kryo.register(ArrayList.class);
		kryo.register(String.class);
		kryo.register(GameData.class);
		kryo.register(Board.class);
		kryo.register(IntegerCardList.class);
		kryo.register(Card.class);
		kryo.register(Texture.class);
		kryo.register(CardBoughtRequest.class);
		kryo.register(CardPlayedRequest.class);
		kryo.register(StartGameRequest.class);
		kryo.register(TurnEndRequest.class);
		kryo.register(TurnEndResponse.class);
		kryo.register(UpdateStateRequest.class);
		kryo.register(UpdateStateResponse.class);
		kryo.register(CardPlayedResponse.class);
		kryo.register(CardBoughtResponse.class);
		kryo.register(PlayTreasuresRequest.class);
		kryo.register(PlayTreasuresResponse.class);
		kryo.register(GameOverResponse.class);
	}
}
