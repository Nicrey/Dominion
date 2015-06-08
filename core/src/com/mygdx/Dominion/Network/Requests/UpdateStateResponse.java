package com.mygdx.Dominion.Network.Requests;

import com.mygdx.Dominion.model.GameData;

public class UpdateStateResponse {
	GameData data;
	
	public UpdateStateResponse(){
		data = null;
	}
	public UpdateStateResponse(GameData data){
		this.data = data;
	}
	
	public GameData getData(){
		return data;
	}
}
