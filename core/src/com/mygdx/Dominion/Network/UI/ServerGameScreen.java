package com.mygdx.Dominion.Network.UI;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Align;
import com.esotericsoftware.kryonet.Client;
import com.mygdx.Dominion.Network.DominionServer;
import com.mygdx.Dominion.UI.DominionGame;
import com.mygdx.Dominion.UI.UIConfig;

public class ServerGameScreen implements Screen{

	private DominionGame game;
	private Skin skin;
	private VerticalGroup vg;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Stage stage;
	private ScrollPane logPane;
	private TextArea log;
	
	public ServerGameScreen(DominionGame dominionGame) {
		this.game = dominionGame;

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, UIConfig.menuWidth, UIConfig.menuHeight);
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		Gdx.graphics.setDisplayMode((int)UIConfig.menuWidth, (int)UIConfig.menuHeight, false);
		
		initializeUI();
	}

	private void initializeUI() {
		vg = new VerticalGroup();
		vg.setSize(UIConfig.menuWidth, UIConfig.menuHeight);
		vg.align(Align.center);
		stage.addActor(vg);
		
		Label header = new Label("Server Play Log",skin);
		log = new TextArea("Game Started",skin);
		logPane = new ScrollPane(log);
		
		
		vg.addActor(header);
		vg.addActor(logPane);
	}

	public void log(String s)
	{
		System.out.println(s);
		log.appendText(s);
		
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
