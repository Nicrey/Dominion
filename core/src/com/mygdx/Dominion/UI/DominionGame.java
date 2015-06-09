package com.mygdx.Dominion.UI;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.Dominion.Network.DominionClient;
import com.mygdx.Dominion.Network.DominionServer;
import com.mygdx.Dominion.Network.Controller.DominionServerController;
import com.mygdx.Dominion.Network.Requests.GameOverResponse;
import com.mygdx.Dominion.Network.UI.LobbyScreenClient;
import com.mygdx.Dominion.Network.UI.LobbyScreenServer;
import com.mygdx.Dominion.Network.UI.MenuScreen;
import com.mygdx.Dominion.Network.UI.ServerGameScreen;
import com.mygdx.Dominion.Network.UI.WinScreen;
import com.mygdx.Dominion.model.GameData;
import com.mygdx.Dominion.model.Options;
import com.mygdx.Dominion.model.Player;

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
		this.setScreen(clientLobby);
	}

	public void initializeServerGame(ArrayList<Player> conPlayers, Server kryoServer) {
		Options.setPlayers(conPlayers);
		System.out.println(kryoServer.getConnections().length);
		DominionServer server = new DominionServer(kryoServer);
		server.setController(new DominionServerController());
		
		ServerGameScreen serverScreen = new ServerGameScreen(this);
		server.setUI(serverScreen);
		
		this.setScreen(serverScreen);
	}

	public void startGame(ArrayList<Player> players, int index,
			GameData gameData, Client c) {
		
		Options.setPlayers(players);
		DominionUI ui = new DominionUI(index, this);
		DominionClient client = new DominionClient(c);
		ui.getController().setServer(client);
		ui.getController().setNewGameData(gameData);
		ui.getController().updateGameData();
		
		this.setScreen(ui);
		
	}

	public void showGameEndScreen(GameOverResponse object) {
		WinScreen endScreen = new WinScreen(object);
		this.setScreen(endScreen);
	}


}
