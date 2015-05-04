package com.mygdx.Dominion.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;


public class UIConfig {

	public static final float width = 1280;
	public static final float height = 720;
	public static final int length = 600;
	
	//Cardtexturesize
	public static final int textureWidth  = 296;
	public static final int textureHeight = 473;
	public static final float factor =  (float)textureHeight/(float)textureWidth;
	
	public static final float labelPosWidth = width/50;
	
	//Cardsize
	public static final float cWidth  = width/12;
	public static final float cHeight = cWidth*factor;
	public static final float mouseOverCardWidth = width/6;
	public static final float mouseOverCardHeight = mouseOverCardWidth*factor;
	public static final float floatingCardWidth = width/8;
	public static final float floatingCardHeight = floatingCardWidth*factor;
	
	
	public static final float boardCardWidth  = width/10;
	public static final float boardCardHeight = boardCardWidth*factor;
	
	
	//Turnangle for Cards when displaying in hand
	public static final  int turnAngle = 30;
	
	//Board Rectangle and other data
	public static final float boardX      = width/5;
	public static final float boardY      = 2*height/5;
	public static final float boardWidth  = (width-boardX*2)*3/4-cWidth/2;
	public static final float boardHeight = height - boardY;

	//Overlap of Cards on Board => step to next card

	
	public static final float buyX = boardX+boardWidth+cWidth/2;
	public static final float buyY = 0;
	public static final float buyWidth = width - buyX;
	public static final float buyHeight = height - buyY;
	
	public static final float handX = boardX + boardWidth/2;
	
	public static final  float defaultstep   = boardCardWidth * 3/4;
	public static final float heightStep = boardHeight/20;

	public static final float buyImgSize =  (width/12);
	public static final float coinSize = buyImgSize/4;
	public static final Color coinColor = Color.BLACK;
	
	public static final float cardPreviewWidth = (float) (mouseOverCardWidth);
	public static final float cardPreviewHeight = (float) (mouseOverCardHeight);
	//PAdding for Button in Buy Window
	public static final float padding = width/100;
	public static final Image previewCardBackground = new Image(new Texture("dominion_textures/cointexture.png"));

	
}
