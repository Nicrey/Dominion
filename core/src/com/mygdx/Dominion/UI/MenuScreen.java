package com.mygdx.Dominion.UI;

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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.Dominion.model.Player;

public class MenuScreen implements Screen {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Stage stage;
    private Skin skin;
    private TextField ip;
    private TextField name;
    private TextButton startServer;
    private TextButton connect;
    private VerticalGroup vg;
    private Label status;
    private ArrayList<TextField> connectedPlayers;
    
	public MenuScreen() throws IOException {
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, UIConfig.width, UIConfig.height);

		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		vg = new VerticalGroup();
		vg.setSize(UIConfig.menuWidth, UIConfig.menuHeight);
		ip = new TextField("IP", skin);
		name = new TextField("Playername" , skin);
		status = new Label("Not Connected", skin);
		startServer = new TextButton("Start Server" , skin);
		connect = new TextButton("Connect to IP" ,skin);
		connectedPlayers = new ArrayList<TextField>();
		vg.addActor(ip);
		vg.addActor(name);
		vg.addActor(connect);
		vg.addActor(startServer);
		vg.addActor(status);
		
		stage.addActor(vg);
		startServer.addListener(new ClickListener(){

			private boolean serverStarted=false;

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(!serverStarted)
				{
					try {
				
						startServer();
					} catch (IOException e) {
						status.setText("Setting up Server Failed");
					}
					serverStarted = true;
					status.setText("Server started");
				}
			}
			
		});
		
		connect.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				 Client client = new Client();
				 	Kryo kryo = client.getKryo();
				   
				    client.start();
				    
				    try {
						client.connect(1000, "192.168.0.17", 54555, 54777);
					} catch (IOException e) {
						System.out.println("Exception connect");
						client.stop();
						System.exit(0);
					}
				   
				    kryo.register(Player.class);
				    kryo.register(ArrayList.class);
				    kryo.register(String.class);
				    
				    Player request = new Player(name.getText());
				    request.addActions(2);
				    client.sendTCP(request);
				    
				    client.addListener(new Listener() {
				        public void received (Connection connection, Object object) {
				           if (object instanceof String) {
				              String response = (String)object;
				              System.out.println(response);
				           }
				        }
				     });
				    status.setText("Connected to Server");
			}
		});
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	public void startServer() throws IOException
	{
		Server server = new Server();
	    server.start();
	    server.bind(54555, 54777);
	    Kryo kryo = server.getKryo();
	    
	    kryo.register(Player.class);
	    kryo.register(ArrayList.class);
	    kryo.register(String.class);
	    
	    server.addListener(new Listener() {
	        

			public void received (Connection connection, Object object) {
	           if (object instanceof Player) {
	              Player request = (Player)object;
	              System.out.println(request.getName());
	              connectedPlayers.add(new TextField(request.getName(), skin));
	              vg.addActor(connectedPlayers.get(connectedPlayers.size()-1));
	              String response = new String();
	              server.sendToAllTCP("New Player joined: " + request.getName());
	              response = "Danke " + request.getName();
	              connection.sendTCP(response);
	           }
	        }
	     });
	    
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
		batch.dispose();
	}

}
