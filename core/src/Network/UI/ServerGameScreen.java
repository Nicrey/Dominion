package Network.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.Align;
import UI.DominionGame;
import UI.UIConfig;

public class ServerGameScreen implements Screen{

	private DominionGame game;
	private Skin skin;
	private Table table;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Stage stage;
	private ScrollPane logPane;
	private TextArea log;
	private String text;
	
	public ServerGameScreen(DominionGame dominionGame) {
		this.game = dominionGame;
		text = "";
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, UIConfig.menuWidth, UIConfig.menuHeight);
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		Gdx.graphics.setWindowedMode((int)UIConfig.menuWidth, (int)UIConfig.menuHeight);
		
		initializeUI();
	}

	private void initializeUI() {
		table  = new Table();
		table.setSize(UIConfig.menuWidth, UIConfig.menuHeight);
		table.align(Align.center);
		stage.addActor(table);
		
		Label header = new Label("Server Play Log",skin);
		log = new TextArea("Game Started",skin);
		logPane = new ScrollPane(log,skin);
		table.add(header);
		table.row();
		table.add(logPane).width(300).height(250f);
		table.row();
		
	}

	public void log(String s)
	{
		System.out.println(s);
		if(text.equals(""))
			text = s;
		else
			text = text + "\n" + s;
		
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(!text.equals(""))
			log.appendText(text + "\n");
		text = "";
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
