package com.mygdx.Dominion.UI;

import java.io.IOException;

import com.badlogic.gdx.Game;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.Dominion.Network.UI.LobbyScreenClient;
import com.mygdx.Dominion.Network.UI.LobbyScreenServer;
import com.mygdx.Dominion.Network.UI.MenuScreen;

public class DominionGame extends Game {

	

	@Override
	public void create() {
		// TODO Auto-generated method stub
		MenuScreen menu = null;
		try {
			menu = new MenuScreen(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		this.setScreen(menu);
		
		
	}

	public void changeToServerScreen() {
		LobbyScreenServer serverLobby = null;
		try {
			serverLobby = new LobbyScreenServer(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setScreen(serverLobby);
	}
	

	public void changeToLobby(String name, String ip) {
		LobbyScreenClient clientLobby = null;
		clientLobby = new LobbyScreenClient(this, name, ip);
	}

	public void initializeServerGame(int playercount, Server server) {
		// TODO Auto-generated method stub
		
	}


}
