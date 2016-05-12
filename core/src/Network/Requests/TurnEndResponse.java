package Network.Requests;

import Model.GameData;

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
