package com.mygdx.Dominion.Network;

import com.esotericsoftware.kryonet.Server;
import com.mygdx.Dominion.Network.Controller.DominionServerController;
import com.mygdx.Dominion.Network.UI.ServerGameScreen;


public class DominionServer {

	private Server server;
	private ServerGameScreen ui;
	private DominionServerController controller;
	
	public DominionServer(Server s)
	{
		this.server = s;
	}

	public void setUI(ServerGameScreen serverScreen) {
		this.ui = serverScreen;
	}
	
	public void setController(DominionServerController con)
	{
		controller = con;
		server.sendToAllTCP(con.getGameData());
	}
}
