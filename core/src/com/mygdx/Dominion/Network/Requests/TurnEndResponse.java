package com.mygdx.Dominion.Network.Requests;

import com.mygdx.Dominion.model.GameData;

public class TurnEndResponse {

	GameData data;
	
	public TurnEndResponse(){
		data = null;
	}
	public TurnEndResponse(GameData g){
		this.data = g;
	}
	
	public GameData getData(){
		return data;
	}
}
