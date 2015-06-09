package com.mygdx.Dominion.Network.UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.Dominion.Network.Requests.GameOverResponse;
import com.mygdx.Dominion.UI.UIConfig;
import com.mygdx.Dominion.model.Player;

public class WinScreen implements Screen {

	GameOverResponse winData;
	private Skin skin;
	private Table table;
	private OrthographicCamera camera;
	private Stage stage;
	
	public WinScreen(GameOverResponse winData){
		this.winData = winData;
		Gdx.graphics.setDisplayMode((int)UIConfig.menuWidth, (int)UIConfig.menuHeight, false);
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, UIConfig.menuWidth, UIConfig.menuHeight);
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		initializeUI();

	}
	
	private void initializeUI() {
		table = new Table();
		table.setSize(UIConfig.menuWidth, UIConfig.menuHeight);
		table.align(Align.center);
		stage.addActor(table);
		
		winData.getPoints();
		table.row();
		table.row();
		Map<Player, Integer> pointMap = new HashMap<Player, Integer>(); 
		
		for(int i = 0; i < winData.getPlayers().size(); i++){
			pointMap.put(winData.getPlayers().get(i), winData.getPoints().get(i));
		}
		
		@SuppressWarnings("unchecked")
		Entry<Player, Integer>[] entries = (Entry<Player, Integer>[]) pointMap
							.entrySet()
							.stream()
							.sorted((x,y) -> (Integer.compare(x.getValue(), y.getValue())))
							.toArray();
		
		for(Entry<Player,Integer> e: entries){
			table.add(new Label(((Player)e.getKey()).getName() + " -> " + (int)e.getValue(),skin));
			table.row();
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
