package com.mygdx.Dominion.UI;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.Dominion.Controller.DominionController;
import com.mygdx.Dominion.UI.model.FloatingCard;
import com.mygdx.Dominion.model.Card;
import com.mygdx.Dominion.model.GameUtils;



public class DominionUI extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	OrthographicCamera camera;
	Stage stage; 
	Skin skin;
	Label playerLabel;
	Label actionLabel;
	Label buyLabel;
	Label goldLabel;
	
	TextButton endTurnBtn;
	TextButton treasureBtn;
	TextButton testbut;
	
	ArrayList<Polygon> handCards;
	FloatingCard activeCard;
	
	public float stepAction    = 0;
	public float stepTreasure  = 0;
	
	DominionController game;
	
	@Override
	public void create () {
		game = new DominionController(this);
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false,UIConfig.width,UIConfig.height);
		
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		//Labels
		playerLabel = new Label(game.getTurnPlayer().getName() , skin);
		playerLabel.setPosition(UIConfig.labelPosWidth, UIConfig.height * 9/10);
		actionLabel = new Label(Integer.toString(game.getTurnPlayer().getActions()) , skin);
		actionLabel.setPosition(UIConfig.labelPosWidth, UIConfig.height * 1/10);
		buyLabel = new Label(Integer.toString(game.getTurnPlayer().getBuys()) , skin);
		buyLabel.setPosition(UIConfig.labelPosWidth, (float) (UIConfig.height * 1.5/10));
		goldLabel = new Label(Integer.toString(game.getTurnPlayer().getGold()) , skin);
		goldLabel.setPosition(UIConfig.labelPosWidth, UIConfig.height * 2/10);
		
		//Buttons
		endTurnBtn = new TextButton("Aktionen beenden", skin);
		endTurnBtn.setPosition(UIConfig.labelPosWidth, UIConfig.height * 3/10);
		endTurnBtn.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(game.getState() == DominionController.ACTIONCARDPHASE)
				{
					game.endActions();
					return;
				}
				if(game.getState() == DominionController.TREASURECARDPHASE)
				{
					game.endTurn();
					return;
				}
			}
		});
		
		treasureBtn = new TextButton("Aktionen beenden", skin);
		treasureBtn.setText("Gold auslegen");
		treasureBtn.setPosition(UIConfig.labelPosWidth, (float) (UIConfig.height * 3.75/10));
		treasureBtn.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.playTreasures();
			}
		});
		
	
		
		stage.addActor(treasureBtn);
		stage.addActor(endTurnBtn);
		stage.addActor(playerLabel);
		stage.addActor(actionLabel);
		stage.addActor(buyLabel);
		stage.addActor(goldLabel);
	
		handCards = new ArrayList<Polygon>();
		activeCard = null;
	
	}

	@Override
	public void render () {
		updateData();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
		
		batch.begin();
		drawCardsOnBoard();
		drawCardsInHand();
		drawActiveCard();
		//batch.draw(img, 0, 0);
		
		batch.end();
		
		//Checking left mouseclick for dragging cards around TODO: LEFT MOUSECLICK FOR BUYING STUFF
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			if(activeCard!=null){
				dragActiveCardAround();
			}else{
				createNewActiveCard();
			}
		}else{
			if(activeCard!=null){
				if(isActiveCardOverBoard()){
					putActiveCardOnBoard();
				}else{
					putActiveCardBackInHand();
				}
			}else{
				
			}
		}
		
	}



	private void updateData() {
		playerLabel.setText(game.getTurnPlayer().getName());
		actionLabel.setText("Aktionen: " + game.getTurnPlayer().getActions());
		buyLabel.setText("Transaktionen: " + game.getTurnPlayer().getBuys());
		goldLabel.setText("Gold: " + game.getTurnPlayer().getGold());
		
		if(game.getState() == DominionController.ACTIONCARDPHASE)
			endTurnBtn.setText("Aktionen beenden");
		else
			endTurnBtn.setText("Zug beenden");
		
		if(game.getState() == DominionController.TREASURECARDPHASE)
			treasureBtn.setVisible(true);
		else
			treasureBtn.setVisible(false);
		if(game.getTurnPlayer().getTreasureCardsInHand().size() == 0)
			treasureBtn.setVisible(false);
		
		updateHandPolygons();
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
											//Drawing Methods for Boards and Hand
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	

	//draws the board with treasure cards on the bottom, all other cards above them
	// i and j count the number of cards in each row
	private void drawCardsOnBoard() {
		
		int i=0;
		int j=0;
		ArrayList<Card> board = game.getBoard();
		int countTreasure = 0;
		for(Card c: board)
		{
			if(c.getType() == GameUtils.CARDTYPE_TREASURE)
				countTreasure++;
		}
		if(countTreasure > 0)
			stepTreasure = UIConfig.boardWidth/(countTreasure) ;
		if(board.size()  > countTreasure)
			stepAction 	 = UIConfig.boardWidth/((board.size()-countTreasure));
		if(stepTreasure  > UIConfig.defaultstep)
			stepTreasure = UIConfig.defaultstep;
		if(stepAction    > UIConfig.defaultstep)
			stepAction   = UIConfig.defaultstep;
		
		for(Card c:board){
			if(c.getType()==GameUtils.CARDTYPE_TREASURE ){
				batch.draw(c.getTexture(), UIConfig.boardX+i*stepTreasure, UIConfig.boardY, UIConfig.boardCardWidth, UIConfig.boardCardHeight, 0, 0, UIConfig.textureWidth, UIConfig.textureHeight, false ,false);
				i++;
			}else{
				batch.draw(c.getTexture(), UIConfig.boardX+j*stepAction, UIConfig.boardY+UIConfig.boardCardHeight+UIConfig.heightStep, UIConfig.boardCardWidth, UIConfig.boardCardHeight, 0, 0, UIConfig.textureWidth, UIConfig.textureHeight, false ,false);
				j++;
			}
		}
		
	}
	
	private void drawCardsInHand() {
		
		ArrayList<Card> hand = getActualHand();
		//Rendering Cards
		if(hand.size()%2 == 0 ){
			//even number of Cards
			for(int i = 0; i < hand.size() ; i++){
				//Draw Card and rotate it around middle => no nonrotated cards
				batch.draw(hand.get(i).getTexture(), UIConfig.width/2-UIConfig.cWidth/2 , 0 ,UIConfig.cWidth/2, -250 , UIConfig.cWidth, UIConfig.cHeight, 1, 1, (float) (-(UIConfig.turnAngle/Math.sqrt(hand.size()))*((i+0.5)-(hand.size()/2))) , 0, 0, UIConfig.textureWidth,UIConfig.textureHeight , false, false);
			}
		}
		else {
			//Odd Number of Cards
			for(int i = 0; i < hand.size() ; i++){
				//Draw Card and rotate it around middle => One Card straight others rotated
				batch.draw(hand.get(i).getTexture(), UIConfig.width/2-UIConfig.cWidth/2 , 0 ,UIConfig.cWidth/2, -250 , UIConfig.cWidth, UIConfig.cHeight, 1, 1, (float) (-(UIConfig.turnAngle/Math.sqrt(hand.size()))*(i-(hand.size()/2))) , 0, 0, UIConfig.textureWidth,UIConfig.textureHeight , false, false);
			}	
		}			
	}
	

	private void updateHandPolygons() {
		Polygon pol ;
		ArrayList<Card> hand = getActualHand();
		float x= UIConfig.width/2-UIConfig.cWidth/2;
		//Card Edges before Rotation
		float[] vertices = {x,0.0f, x+UIConfig.cWidth,0.0f, x+UIConfig.cWidth, UIConfig.cHeight, x, UIConfig.cHeight};
		//Clearing old Cards
		handCards.clear();
		for(int i=0; i< hand.size();i++){
			//Creating new Polygon => rotating it => adding it to handCards for each Card in Hand
			pol = new Polygon();
			pol.setVertices(vertices);
			pol.setOrigin(UIConfig.width/2, -250);
			if(hand.size()%2 == 0){
				pol.setRotation((float) (-(UIConfig.turnAngle/Math.sqrt(hand.size()))*((i+0.5)-(hand.size()/2))) );
			}else{
				pol.setRotation((float) (-(UIConfig.turnAngle/Math.sqrt(hand.size()))*(i-(hand.size()/2))) );
			}
			handCards.add(pol);
			
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
												//Active Selected Card Methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//Creates a new active Card from the position of the mousepointer 
	private void createNewActiveCard() {
		Polygon pol = null;
		ArrayList<Card> hand = getActualHand();
		//starting at activeCards.size() to get the uppermost card => most right card in hand
		for(int i=handCards.size()-1; i>=0; i--){
			pol= new Polygon(handCards.get(i).getTransformedVertices());
			//check for cards in hand that are gonna be dragged / heigt- because of y axis being upside down in mouse inputs
			if(pol.contains(Gdx.input.getX(), UIConfig.height-Gdx.input.getY())){
				//Checks if card is playable in momentary gamestate
				if(game.isCardPlayable(hand.get(i))){
					Vector3 mousePos = new Vector3();
					mousePos.set(Gdx.input.getX(),UIConfig.height-Gdx.input.getY(),0);
					camera.unproject(mousePos);
					activeCard = new FloatingCard(hand.get(i), mousePos.x, mousePos.y);
				}else{
					//TODO: NOT PLAYABLE CARD CHOSEN
				}
					
				break;
			}
		}
	}
	
	//draws the active card at the position of the mousepointer
	private void drawActiveCard(){
		if(activeCard!=null){
			batch.draw(activeCard.getCard().getTexture(),activeCard.getCardRect().getX(), activeCard.getCardRect().getY(),0,
							0 , UIConfig.cWidth, UIConfig.cHeight, 1, 1, 0 , 0, 0, UIConfig.textureWidth,UIConfig.textureHeight , false, false);
		}
	}
	
	private void dragActiveCardAround() {
		Vector3 mousePos = new Vector3();
		mousePos.set(Gdx.input.getX(),UIConfig.height-Gdx.input.getY(),0);
		camera.unproject(mousePos);
		activeCard.updatePosition(mousePos.x, mousePos.y); 

	}

	private void putActiveCardBackInHand() {
		activeCard = null;
	}

	private boolean isActiveCardOverBoard() {
		Rectangle boardRect        = new Rectangle();
		boardRect.x      = UIConfig.boardX;
		boardRect.y      = UIConfig.boardY;
		boardRect.height = UIConfig.boardHeight;
		boardRect.width  = UIConfig.boardWidth;
		return boardRect.contains(activeCard.getCardRect().getCenter(new Vector2()));
	}

	private void putActiveCardOnBoard() {
		game.cardPlayed(activeCard.getCard());
		activeCard = null;
	}

	
	private ArrayList<Card> getActualHand()
	{
		ArrayList<Card> ret = (ArrayList<Card>) game.getTurnPlayer().getHand().clone();
		if(activeCard != null)
			ret.remove(activeCard.getCard());
		return ret;
	}
	

}
