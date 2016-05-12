package UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;


public class UIConfig {

	public static final float width = 1280;
	public static final float height = 720;
	public static final float menuWidth = 300;
	public static final float menuHeight = 300;
	public static final int length = 600;
	
	//Cardtexturesize
	public static final int textureWidth  = 296;
	public static final int textureHeight = 473;
	public static final float factor =  (float)textureHeight/(float)textureWidth;
	
	public static final float labelPosWidth = width/50;

	
	//Cardsize
	public static final float cWidth  = width/12;
	public static final float cHeight = cWidth*factor;
	public static final float mouseOverCardWidth = width/5;
	public static final float mouseOverCardHeight = mouseOverCardWidth*factor;
	public static final float floatingCardWidth = width/8;
	public static final float floatingCardHeight = floatingCardWidth*factor;
	public static final float cardPreviewWidth =  (mouseOverCardWidth);
	public static final float cardPreviewHeight =  (mouseOverCardHeight);
	public static final float boardCardWidth  = width/10;
	public static final float boardCardHeight = boardCardWidth*factor;
	
	public static final float labelY = height * 3/10;
	public static final float labelstep = height * 1/20;
	//Turnangle for Cards when displaying in hand
	public static final  int turnAngle = 30;
	
	//Board Rectangle and other data
	public static final float boardX      = width/5;
	public static final float boardY      = 2*height/5;
	public static final float boardWidth  = (width-boardX*2)*3/4-cWidth/2;
	public static final float boardHeight = height - boardY;

	public static final float handX = boardX + boardWidth/2;
	
	public static final float deckX  = labelPosWidth ;
	public static final float deckY  = 15;
	public static final float deckWidth  = width/9;
	public static final float deckHeight = labelY-labelstep;
	public static final float deckSizeWidth = deckWidth/2.5f;
	public static final float deckSizeHeight = deckSizeWidth * factor;
	public static final float deckStep = deckSizeHeight/60;
	public static final float graveyardX = deckX + deckSizeWidth + 5;
	
	public static final float hiddenDeckX = labelPosWidth; 
	public static final float hiddenDeckY = labelY +labelstep * 5.7f;
	public static final float hiddenDeckWidth = deckSizeWidth/1.5f;
	public static final float hiddenDeckHeight = hiddenDeckWidth * factor;
	public static final float hiddenGraveyardX = hiddenDeckX + hiddenDeckWidth +5;
	public static final float hiddenDeckStep = (height - 3*(hiddenDeckHeight-10) - hiddenDeckY)/3;
	public static final float hiddenDeckCardStep = deckStep;
	public static final float hiddenHandX = hiddenGraveyardX + hiddenDeckWidth+5;
	public static final float hiddenPlayerX = hiddenHandX + hiddenDeckHeight +25;
	//Overlap of Cards on Board => step to next card

	
	public static final float buyX = boardX+boardWidth+cWidth/2;
	public static final float buyY = 0;
	public static final float buyWidth = width - buyX;
	public static final float buyHeight = height - buyY;
	
	
	
	public static final  float defaultstep   = boardCardWidth * 3/4;
	public static final float heightStep = boardHeight/20;

	public static final float buyImgSize =  (width/12);
	public static final float coinSize = buyImgSize/4;
	
	public static final Color coinColor = Color.BLACK;
	public static final Color wrongColor = new Color(0.5f,0,0, 1);
	public static final Color rightColor = new Color(0,0.5f,0, 0.5f);
	public static final Color buyColor = new Color(0.1f,0.1f,0.1f,0.65f);
	public static final Color noBuyColor = new Color(0.1f,0.1f,0.1f,0.8f);
	public static final Color turnOverColor = new Color(0,1.0f,0,0.9f);
	public static final Color labelColor = Color.BLACK;
	public static final Color windowColor = new Color(0,0.8f,0,1.0f);
	public static final Color disabledColor = new Color(1, 1, 1, 0.5f);
	
	//PAdding for Button in Buy Window
	public static final float padding = width/100;
	public static final Image previewCardBackground = new Image(new Texture("dominion_textures/cointexture.png"));
	public static final String cardBackStr = "dominion_textures/texture_cardback.jpg";
	
	public static final Texture cardBack = new Texture("dominion_textures/texture_cardback.jpg");
	
	public static final Texture roundButton = new Texture("dominion_textures/texture_count.png");
	

	public static final float playerLabelX = boardX+boardWidth/2;
	public static final float playerLabelY =  boardY - height/10;

	


}

