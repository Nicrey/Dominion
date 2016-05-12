package UI.Model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import Controller.DominionController;
import UI.UIConfig;
import Model.Card;

public class CustomLabel extends Button {

	private DominionController game;
	private Card c;
	private Label text;
	private Image bg;
	

	public CustomLabel(CharSequence text, Skin skin, DominionController game, Card c) {
		
		ImageTextButtonStyle style = new ImageTextButtonStyle();
		this.setStyle(style);
		this.bg = new Image(UIConfig.roundButton);		
		this.text = new Label(text, skin);
		this.text.setColor(Color.BLACK);
		
		this.addActor(bg);
		this.addActor(this.text);
	

		this.game = game;
		this.c = c;
	}

	@Override
	public void act(float delta) {
		this.setText("" + game.getGameData().getRemainingCards(c));
		
		if(game.getGameData().getRemainingCards(c) == 0)
		{
			bg.setColor(UIConfig.disabledColor);
			text.setColor(UIConfig.wrongColor);
		}
			

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {

		
		bg.setPosition(this.getX(), this.getY());
		bg.setSize(UIConfig.coinSize, UIConfig.coinSize);
		text.setPosition(this.getX(), this.getY() );
		text.setSize(UIConfig.coinSize, UIConfig.coinSize);
		text.setAlignment(Align.center);
		
		super.draw(batch, parentAlpha);
		Array<Actor> children = getChildren();
		for (int i = 0; i < children.size; i++)
			children.get(i).draw(batch, parentAlpha);

	}

	private void setText(String string) {
		text.setText(string);

	}

}
