package com.mygdx.Dominion.Network.Requests;

import com.mygdx.Dominion.model.GameData;

public class PlayTreasuresResponse {

	GameData data; 
	
	
	public PlayTreasuresResponse(){
		data = null;
	}
	
	public PlayTreasuresResponse(GameData data){
		this.data = data;
	}
	
	public GameData getData(){
		return data;
	}
}
