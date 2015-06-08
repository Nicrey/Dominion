package com.mygdx.Dominion.Network;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.esotericsoftware.kryo.Kryo;
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
import com.mygdx.Dominion.model.Board;
import com.mygdx.Dominion.model.Card;
import com.mygdx.Dominion.model.GameData;
import com.mygdx.Dominion.model.IntegerCardList;
import com.mygdx.Dominion.model.Player;

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
