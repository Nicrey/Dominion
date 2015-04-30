package com.mygdx.Dominion.UI;

import com.badlogic.gdx.graphics.Color;


public class UIConfig {

	public static final float width = 1280;
	public static final float height = 720;
	public static final int length = 600;
	
	public static final float labelPosWidth = width/50;
	
	//Cardsize
	public static final float cWidth  = width/9;
	public static final float cHeight = cWidth*1.5f;
	
	public static final float boardCardWidth  = width/10;
	public static final float boardCardHeight = boardCardWidth*1.5f;
	
	
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
	//Cardtexturesize
	public static final int textureWidth  = 296;
	public static final int textureHeight = 473;
	public static final float buyImgSize =  (width/12);
	public static final float coinSize = buyImgSize/4;
	public static final Color coinColor = Color.BLACK;
	
	public static final float cardPreviewWidth = (float) (cWidth*1.5);
	public static final float cardPreviewHeight = (float) (cHeight*1.5);
	//PAdding for Button in Buy Window
	public static final float padding = width/100;
	
}
