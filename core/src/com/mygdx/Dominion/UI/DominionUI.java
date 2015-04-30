package com.mygdx.Dominion.UI;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.Dominion.Controller.DominionController;
import com.mygdx.Dominion.UI.model.CustomLabel;
import com.mygdx.Dominion.UI.model.DominionImageButton;
import com.mygdx.Dominion.UI.model.FloatingCard;
import com.mygdx.Dominion.model.Board;
import com.mygdx.Dominion.model.Card;
import com.mygdx.Dominion.model.GameUtils;
import com.mygdx.Dominion.model.IntegerCardList;



public class DominionUI extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer test;
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
	
	Table buyArea;	
	
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
		test = new ShapeRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(false,UIConfig.width,UIConfig.height);
		
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		//Labels
		createLabels();
		//Buttons
		createButtons();
		//BuyArea
		createBuyArea();
	
		handCards = new ArrayList<Polygon>();
		activeCard = null;
	
	}
	




	@Override
	public void render () {
		updateData();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		batch.begin();
		drawCardsOnBoard();
		drawCardsInHand();
		drawActiveCard();
		//batch.draw(img, 0, 0);

		
		batch.end();
		
		Rectangle boardRect        = new Rectangle();
		boardRect.x      = UIConfig.boardX;
		boardRect.y      = UIConfig.boardY;
		boardRect.height = UIConfig.boardHeight;
		boardRect.width  = UIConfig.boardWidth;
		
		Rectangle buyRect        = new Rectangle();
		buyRect.x      = UIConfig.buyX;
		buyRect.y      = UIConfig.buyY;
		buyRect.height = UIConfig.buyHeight;
		buyRect.width  = UIConfig.buyWidth;

		test.setAutoShapeType(true);
		test.begin();
		test.rect(boardRect.x, boardRect.y, boardRect.width, boardRect.height);
		test.rect(buyRect.x, buyRect.y, buyRect.width, buyRect.height);
		test.end();
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
		
		stage.act();
		stage.draw();
	}



	private void updateData() {

		game.update();
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
				batch.draw(hand.get(i).getTexture(), UIConfig.handX-UIConfig.cWidth/2 , 0 ,UIConfig.cWidth/2, -250 , UIConfig.cWidth, UIConfig.cHeight, 1, 1, (float) (-(UIConfig.turnAngle/Math.sqrt(hand.size()))*((i+0.5)-(hand.size()/2))) , 0, 0, UIConfig.textureWidth,UIConfig.textureHeight , false, false);
			}
		}
		else {
			//Odd Number of Cards
			for(int i = 0; i < hand.size() ; i++){
				//Draw Card and rotate it around middle => One Card straight others rotated
				batch.draw(hand.get(i).getTexture(), UIConfig.handX-UIConfig.cWidth/2 , 0 ,UIConfig.cWidth/2, -250 , UIConfig.cWidth, UIConfig.cHeight, 1, 1, (float) (-(UIConfig.turnAngle/Math.sqrt(hand.size()))*(i-(hand.size()/2))) , 0, 0, UIConfig.textureWidth,UIConfig.textureHeight , false, false);
			}	
		}			
	}
	

	private void updateHandPolygons() {
		Polygon pol ;
		ArrayList<Card> hand = getActualHand();
		float x= UIConfig.handX-UIConfig.cWidth/2;
		//Card Edges before Rotation
		float[] vertices = {x,0.0f, x+UIConfig.cWidth,0.0f, x+UIConfig.cWidth, UIConfig.cHeight, x, UIConfig.cHeight};
		//Clearing old Cards
		handCards.clear();
		for(int i=0; i< hand.size();i++){
			//Creating new Polygon => rotating it => adding it to handCards for each Card in Hand
			pol = new Polygon();
			pol.setVertices(vertices);
			pol.setOrigin(UIConfig.handX, -250);
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

	
	public void cardBought(int id) {
		
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//									UI CREATION
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void createLabels()
	{
		playerLabel = new Label(game.getTurnPlayer().getName() , skin);
		playerLabel.setPosition(UIConfig.labelPosWidth, UIConfig.height * 9/10);
		actionLabel = new Label(Integer.toString(game.getTurnPlayer().getActions()) , skin);
		actionLabel.setPosition(UIConfig.labelPosWidth, UIConfig.height * 1/10);
		buyLabel = new Label(Integer.toString(game.getTurnPlayer().getBuys()) , skin);
		buyLabel.setPosition(UIConfig.labelPosWidth, (float) (UIConfig.height * 1.5/10));
		goldLabel = new Label(Integer.toString(game.getTurnPlayer().getGold()) , skin);
		goldLabel.setPosition(UIConfig.labelPosWidth, UIConfig.height * 2/10);
		stage.addActor(playerLabel);
		stage.addActor(actionLabel);
		stage.addActor(buyLabel);
		stage.addActor(goldLabel);
	}

	private void createButtons() {
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
	}
	
	private void createBuyArea() {
		Board b = game.getGameData();
		final IntegerCardList v = b.getBuyableVictoryCards();
		IntegerCardList t = b.getBuyableTreasureCards();
		IntegerCardList a = b.getBuyableActionCards();
		
		buyArea= new Table();
		buyArea.left().top();
		buyArea.setPosition(UIConfig.buyX, UIConfig.buyY);
		buyArea.setSize(UIConfig.buyWidth, UIConfig.buyHeight);
		
		setUpVictoryCards(v);
		
		setUpTreasureCards(t);
		
		setUpActionCards(a);
		
		
	    stage.addActor(buyArea);
	}

	private void setUpActionCards(IntegerCardList a) {
		for(int k = 0; k < (float)a.size()/4+0.5 ; k++)
		{
			for(int i = 0; i < 4 && k*4+i<a.size(); i++)
			{
				final Card c = a.getCard(i);
				Texture reg2= new Texture("dominion_textures/cointexture.png");
				Image image2 = new Image(reg2);
				TextureRegion reg= new TextureRegion(a.getCard(i).getTexture(), 0.0f, 0.0f, 1f,0.5f);
				Image image = new Image(reg);
				image.setSize(UIConfig.buyImgSize, UIConfig.buyImgSize);
				DominionImageButton btn = new DominionImageButton( image2 , image, " "+c.getCost(),skin);
				btn.setSize(UIConfig.buyImgSize,UIConfig.buyImgSize);
				
				btn.addListener(new ClickListener(){
					@Override
					public void clicked(InputEvent event, float x, float y) {
						System.out.println("Show Card " + c.getName());
						//showCard(c);
					}
				});
				
				buyArea.add(btn).width(UIConfig.buyImgSize).height(UIConfig.buyImgSize).colspan(2);
			}
			buyArea.row();
			
			for(int i = 0; i < 4&& k*4+i<a.size();i++)
			{
				final Card c = a.getCard(i);
							
				TextButton buy = new TextButton("Kaufen", skin);
				buy.addListener(new ClickListener(){
					@Override
					public void clicked(InputEvent event, float x, float y) {
						game.cardBought(c);
					}
				});
				
				CustomLabel remainingCards = new CustomLabel(a.getRemainingCards(i) + "", skin , game.getGameData(), GameUtils.CARDTYPE_ACTION, i);
				buyArea.add(buy);
				buyArea.add(remainingCards);
			}
			
			buyArea.row();
		}

	}





	private void setUpTreasureCards(IntegerCardList t) {
		
		
		for( int i = 0; i < t.size(); i++)
		{
			Texture reg2= new Texture("dominion_textures/cointexture.png");
			Image image2 = new Image(reg2);
			final Card c = t.getCard(i);
			TextureRegion reg= new TextureRegion(t.getCard(i).getTexture(), 0.0f, 0.0f, 1f,0.5f);
			Image image = new Image(reg);
			image.setSize(UIConfig.buyImgSize, UIConfig.buyImgSize);
			DominionImageButton btn = new DominionImageButton( image2 , image, " "+c.getCost(),skin);
			btn.setSize(UIConfig.buyImgSize,UIConfig.buyImgSize);
			
			btn.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					System.out.println("Show Card " + c.getName());
					//showCard(c);
				}
			});

			buyArea.add(btn).width(UIConfig.buyImgSize).height(UIConfig.buyImgSize).colspan(2);
		}
		
		buyArea.row();
		
		for(int i = 0; i < t.size();i++)
		{
			final Card c = t.getCard(i);
						
			TextButton buy = new TextButton("Kaufen", skin);
			buy.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.cardBought(c);
				}
			});
			
			CustomLabel remainingCards = new CustomLabel(t.getRemainingCards(i) + "", skin , game.getGameData(), GameUtils.CARDTYPE_TREASURE, i);
			buyArea.add(buy);
			buyArea.add(remainingCards);
		}
		
		buyArea.row();
	}





	private void setUpVictoryCards(IntegerCardList v) {
	
		
		for( int i = 0; i < v.size(); i++)
		{
			Texture reg2= new Texture("dominion_textures/cointexture.png");
			Image image2 = new Image(reg2);
			final Card c = v.getCard(i);
			TextureRegion reg= new TextureRegion(v.getCard(i).getTexture(), 0.0f, 0.0f, 1f,0.5f);
			 Image image = new Image(reg);
			image.setSize(UIConfig.buyImgSize, UIConfig.buyImgSize);
			
			
			final Image imagebig = new Image(v.getCard(i).getTexture());
			imagebig.setSize(UIConfig.cardPreviewWidth, UIConfig.cardPreviewHeight);
			final Container<Image> dig = new Container<Image>();
			dig.setSize(UIConfig.cardPreviewWidth, UIConfig.cardPreviewHeight);
			dig.pad(20);
			dig.setColor(100, 100, 100, 100);
			
			dig.setBackground(imagebig.getDrawable());
			
			
			
			final DominionImageButton btn = new DominionImageButton( image2 , image, " "+c.getCost(),skin);
			btn.setSize(UIConfig.buyImgSize,UIConfig.buyImgSize);
			
			btn.addListener(new InputListener(){				
				@Override
				public void enter(InputEvent event, float x, float y,
						int pointer, Actor fromActor) {
					dig.setPosition(UIConfig.width/2-UIConfig.cardPreviewWidth/2, UIConfig.boardY);
					stage.addActor(dig);
					super.enter(event, x, y, pointer, fromActor);
				}

				@Override
				public void exit(InputEvent event, float x, float y,
						int pointer, Actor toActor) {
					// TODO Auto-generated method stub
					dig.remove();
					super.exit(event, x, y, pointer, toActor);
				}

				
				

			
			});

			buyArea.add(btn).width(UIConfig.buyImgSize).height(UIConfig.buyImgSize).colspan(2);
		}
		
		buyArea.row();

		
		
		for(int i = 0; i < v.size();i++)
		{
			final Card c = v.getCard(i);
						
			TextButton buy = new TextButton("Kaufen", skin);
			buy.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.cardBought(c);
				}
			});
			
			CustomLabel remainingCards = new CustomLabel(v.getRemainingCards(i) + "", skin , game.getGameData(), GameUtils.CARDTYPE_VICTORY, i);
			buyArea.add(buy);
			buyArea.add(remainingCards);
		}
		
		buyArea.row();
	}




}
