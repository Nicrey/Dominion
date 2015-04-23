package com.mygdx.Dominion.UI;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.Dominion.Controller.DominionController;



public class DominionUI extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	OrthographicCamera camera;
	Stage stage; 
	Skin skin;
	Label playerLabel;
	DominionController game;
	
	@Override
	public void create () {
		game = new DominionController(this);
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false,UIConfig.width,UIConfig.height);
		//img = new Texture("badlogic.jpg");
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		playerLabel = new Label(game.getTurnPlayer().getName() , skin);
		playerLabel.setPosition(UIConfig.width/10, UIConfig.height * 9/10);
		
		
		
		stage.addActor(playerLabel);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
		
		batch.begin();
		//batch.draw(img, 0, 0);
		
		batch.end();
	}
}
