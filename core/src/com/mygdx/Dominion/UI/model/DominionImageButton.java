package com.mygdx.Dominion.UI.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.Dominion.UI.UIConfig;

public class DominionImageButton extends Button {

	Image i1;
	Image i2;
	Label text;
	
	public DominionImageButton(Image i1, Image i2, String text, Skin skin)
	{
		this.i1 = i1;
		this.i2 = i2;
		this.text = new Label(" "+text,skin);
		ImageTextButtonStyle style = new ImageTextButtonStyle();
		this.setStyle(style);
		this.text.setColor(UIConfig.coinColor);
		add(this.i2);
		add(this.i1);
		add(this.text);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		int offset = -15;
		i1.setPosition(this.getX(), this.getY());
		i1.setSize(UIConfig.coinSize, UIConfig.coinSize);
		i2.setPosition(this.getX(), this.getY() );
		i2.setSize(UIConfig.buyImgSize, UIConfig.buyImgSize);
		text.setPosition(this.getX(),this.getY());
		
		super.draw(batch, parentAlpha);
		Array<Actor> children = getChildren();
		for (int i = 0; i < children.size; i++)
			children.get(i).draw(batch, parentAlpha);
	}
	
	
}
