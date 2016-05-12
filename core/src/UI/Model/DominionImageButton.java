package UI.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import Controller.DominionController;
import UI.UIConfig;
import Model.Card;

public class DominionImageButton extends Button {

	Image i1;
	Image i2;
	Label text;
	Card c;
	DominionController g;
	ShapeRenderer x;
	
	public DominionImageButton(Image i1, Image i2, String text, Skin skin, Card c, DominionController g)
	{
		this.g = g;
		this.i1 = i1;
		this.i2 = i2;
		this.text = new Label(" "+text,skin);
		ImageTextButtonStyle style = new ImageTextButtonStyle();
		this.setStyle(style);
		this.text.setColor(UIConfig.coinColor);
		this.c = c;
		add(this.i2);
		add(this.i1);
		add(this.text);
		x = new ShapeRenderer();
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		i1.setPosition(this.getX(), this.getY());
		i1.setSize(UIConfig.coinSize, UIConfig.coinSize);
		i2.setPosition(this.getX(), this.getY() );
		i2.setSize(UIConfig.buyImgSize, UIConfig.buyImgSize);
		text.setPosition(this.getX(),this.getY());
		
		super.draw(batch, parentAlpha);
		Array<Actor> children = getChildren();
		for (int i = 0; i < children.size; i++)
			children.get(i).draw(batch, parentAlpha);
		batch.end();
		if(!g.isBuyable(c))
		{
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			x.begin(ShapeType.Filled);
			x.setColor(UIConfig.buyColor);
			x.rect(this.getX(), this.getY(), UIConfig.buyImgSize+2, UIConfig.buyImgSize+2);
			x.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);
		}
		if(g.getGameData().getRemainingCards(c) ==  0)
		{
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			x.begin(ShapeType.Filled);
			x.setColor(UIConfig.noBuyColor);
			x.rect(this.getX(), this.getY(), UIConfig.buyImgSize+2, UIConfig.buyImgSize+2);
			x.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);
		}
		
		if(g.getView().isDisabled())
		{
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			x.begin(ShapeType.Filled);
			x.setColor(UIConfig.buyColor);
			x.rect(this.getX(), this.getY(), UIConfig.buyImgSize+2, UIConfig.buyImgSize+2);
			x.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);
		}
		
		batch.begin();
	}
	
	
}
