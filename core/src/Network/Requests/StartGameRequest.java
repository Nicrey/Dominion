package Network.Requests;

import java.util.ArrayList;

import Model.GameData;
import Model.Player;

public class StartGameRequest {

	
	public final int index;
	public final GameData gameData;
	
	public StartGameRequest(){
		index = 0;
		gameData = null;
	}
	public StartGameRequest( int index, GameData gameData){
		this.index = index;
		this.gameData = gameData;
	}
}
