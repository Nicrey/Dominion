package com.mygdx.Dominion.Network.UI;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Screen;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.Dominion.UI.DominionGame;
import com.mygdx.Dominion.model.Player;

public class LobbyScreenClient implements Screen {

	Client client;
	DominionGame game;
	String name;
	String ip;

	public LobbyScreenClient(DominionGame dominionGame, String name, String ip) {
		this.game = dominionGame;
		this.name = name;
		this.ip = ip;
	}

	public void connectClient() {
		client = new Client();
		Kryo kryo = client.getKryo();

		client.start();

		try {
			client.connect(1000, ip, 54555, 54777);
		} catch (IOException e) {
			System.out.println("Exception connect");
			client.stop();
			System.exit(0);
		}

		kryo.register(Player.class);
		kryo.register(ArrayList.class);
		kryo.register(String.class);

		Player request = new Player(name);
		request.addActions(2);
		client.sendTCP(request);

		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof String) {
					String response = (String) object;
					System.out.println(response);
				}
			}
		});
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
