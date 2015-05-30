package com.mygdx.Dominion.Network.Requests;

import java.util.ArrayList;

import com.mygdx.Dominion.model.GameData;
import com.mygdx.Dominion.model.Player;

public class StartGameRequest {

	
	public final ArrayList<Player> players;
	public final int index;
	public final GameData gameData;
	
	public StartGameRequest(ArrayList<Player> players, int index, GameData gameData){
		this.players = players;
		this.index = index;
		this.gameData = gameData;
	}
}
