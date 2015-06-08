package com.mygdx.Dominion.Network.UI;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.Dominion.Network.Util;
import com.mygdx.Dominion.Network.Requests.CardBoughtRequest;
import com.mygdx.Dominion.Network.Requests.CardPlayedRequest;
import com.mygdx.Dominion.Network.Requests.StartGameRequest;
import com.mygdx.Dominion.Network.Requests.TurnEndRequest;
import com.mygdx.Dominion.Network.Requests.UpdateStateRequest;
import com.mygdx.Dominion.UI.DominionGame;
import com.mygdx.Dominion.UI.UIConfig;
import com.mygdx.Dominion.model.Board;
import com.mygdx.Dominion.model.Card;
import com.mygdx.Dominion.model.GameData;
import com.mygdx.Dominion.model.IntegerCardList;
import com.mygdx.Dominion.model.Player;

public class LobbyScreenServer implements Screen {

	DominionGame game;
	Server server;
	private ArrayList<TextField> connectedPlayers;
	private ArrayList<Player> conPlayers;
	private Skin skin;
	private VerticalGroup vg;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Stage stage;

	public LobbyScreenServer(DominionGame dominionGame) throws IOException {
		this.game = dominionGame;
		conPlayers = new ArrayList<Player>();

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, UIConfig.menuWidth, UIConfig.menuHeight);
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		initializeUI();

		startServer();
	}

	private void initializeUI() {
		vg = new VerticalGroup();
		vg.setSize(UIConfig.menuWidth, UIConfig.menuHeight);
		vg.align(Align.center);
		connectedPlayers = new ArrayList<TextField>();
		stage.addActor(vg);
		
		Label header = new Label("Server Gestartet",skin);
		Label header2 = new Label("Verbundene Spieler:", skin);
		Label error = new Label("",skin);
		vg.addActor(header);
		vg.addActor(header2);

		
		TextButton startGameBtn = new TextButton("Spiel starten!", skin);
		startGameBtn.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(connectedPlayers.size() >= 2)
				{
					game.initializeServerGame(conPlayers,server);
				}
				else
				{
					error.setText("Zu wenige Spieler um ein Spiel zu starten");
				}
					
			}
			
		});
		
		vg.addActor(error);
		vg.addActor(startGameBtn);
		
	}

	public void startServer() throws IOException {
		server = new Server(65536,8192);
		server.start();
		
		server.bind(54555, 54777);
		Kryo kryo = server.getKryo();

		Util.register(kryo);

		server.addListener(new LobbyServerListener());

	}
	
	private class LobbyServerListener extends Listener{
		
		ArrayList<Connection> registeredConnections;
		
		public LobbyServerListener()
		{
			registeredConnections = new ArrayList<Connection>();
		}
		
		public void received(Connection connection, Object object) {
			
			if(registeredConnections.contains(connection))
				server.sendToTCP(registeredConnections.indexOf(connection), "Already Connected");
			else
				registeredConnections.add(connection);
			
			if (object instanceof Player) {
				Player request = (Player) object;
				System.out.println(request.getName());
				conPlayers.add(request);
				connectedPlayers.add(new TextField(request.getName(), skin));
				vg.addActorAt(2 + connectedPlayers.size()-1, connectedPlayers.get(connectedPlayers.size() - 1));
				ArrayList<Player> response = conPlayers;
				server.sendToAllTCP(response);
			}
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
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
