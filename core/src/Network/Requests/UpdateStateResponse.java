package Network.Requests;

import Model.GameData;

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
