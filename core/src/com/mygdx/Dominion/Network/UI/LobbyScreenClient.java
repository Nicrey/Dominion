package com.mygdx.Dominion.Network.UI;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Align;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
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

public class LobbyScreenClient implements Screen {

	private Client client;
	private DominionGame game;
	private String name;
	private String ip;
	private ArrayList<TextField> connectedPlayers;
	private Skin skin;
	private VerticalGroup vg;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Stage stage;
	private Label status;
	
	public LobbyScreenClient(DominionGame dominionGame, String name, String ip) {
		this.game = dominionGame;
		this.name = name;
		this.ip = ip;

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, UIConfig.menuWidth, UIConfig.menuHeight);
		skin = new Skin(Gdx.files.internal("uiskin.json"));

		initializeUI();
		connectClient();
	}
	
	

	private void initializeUI() {
		vg = new VerticalGroup();
		vg.setSize(UIConfig.menuWidth, UIConfig.menuHeight);
		vg.align(Align.center);
		connectedPlayers = new ArrayList<TextField>();
		stage.addActor(vg);
		
		Label header = new Label("Zum Server verbunden",skin);
		Label header2 = new Label("Verbundene Spieler:", skin);
		status = new Label("",skin);
		vg.addActor(header);
		vg.addActor(header2);
		vg.addActor(status);
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

		Util.register(kryo);
		Player request = new Player(name);
		client.sendTCP(request);

		client.addListener(new ClientLobbyListener());
	}

	private class ClientLobbyListener extends Listener {

		public void received(Connection connection, Object object) {
			
			if(object instanceof StartGameRequest)
			{
				StartGameRequest response = (StartGameRequest) object;
				game.startGame(response.players, response.index, response.gameData, client);
			}
			if (object instanceof String) {
				String response = (String) object;
				status.setText(response);
			}
			if (object instanceof ArrayList<?>) {
				ArrayList<Player> response = (ArrayList<Player>) object;
				connectedPlayers.forEach((textfield) -> textfield.remove());
				connectedPlayers.clear();
				for(Player p: response){
					connectedPlayers.add(new TextField(p.getName(), skin));
					vg.addActorAt(2 + connectedPlayers.size()-1, connectedPlayers.get(connectedPlayers.size() - 1));
					status.setText("Neuer Spieler: " + p.getName());
				}
				
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
