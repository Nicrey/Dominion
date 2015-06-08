package com.mygdx.Dominion.UI;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.Dominion.Controller.DominionController;
import com.mygdx.Dominion.UI.model.CustomLabel;
import com.mygdx.Dominion.UI.model.DeckUI;
import com.mygdx.Dominion.UI.model.DominionImageButton;
import com.mygdx.Dominion.UI.model.FloatingCard;
import com.mygdx.Dominion.UI.model.HiddenDeckUI;
import com.mygdx.Dominion.model.Board;
import com.mygdx.Dominion.model.Card;
import com.mygdx.Dominion.model.GameUtils;
import com.mygdx.Dominion.model.IntegerCardList;
import com.mygdx.Dominion.model.Player;

public class DominionUI extends Game implements Screen{
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;

	private OrthographicCamera camera;
	private Stage stage;
	private Skin skin;
	private Label playerLabel;
	private Label actionLabel;
	private Label buyLabel;
	private Label goldLabel;

	private TextButton endTurnBtn;
	private TextButton treasureBtn;
	private Image showCard;
	private TextButton showCardBackground;
	private Dialog showWindow;

	private Table buyArea;
	private DeckUI deckArea;
	private HiddenDeckUI[] opponentsDecks;

	private ArrayList<Polygon> handCards;
	private FloatingCard activeCard;

	public float stepAction = 0;
	public float stepTreasure = 0;

	private boolean disableUI = false;
	private boolean rendering = false;

	private DominionController game;
	private DominionGame application;

	private final int viewIndex;
	private FitViewport viewport;
	private Rectangle boardRect;
	private Rectangle buyRect;
	private FPSLogger logger;
	private Vector2 resultVector; 
	
	public DominionUI(int index, DominionGame application){
		this.viewIndex = index;
		this.application = application;
		game = new DominionController(this,viewIndex);
	}
	public DominionController getController()
	{
		return game;
	}
	@Override
	public void create() {
		resultVector = new Vector2();
		Gdx.graphics.setDisplayMode((int)UIConfig.width, (int)UIConfig.height, false);
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, UIConfig.width, UIConfig.height);
		
		skin = new Skin(Gdx.files.internal("uiskin.json"));

		// Labels
		createLabels();
		playerLabel.setText(game.getGameData().getPlayer(viewIndex).getName());
		
		// Buttons
		createButtons();
		// BuyArea
		createBuyArea();
		// create Deck and Graveyard elements
		createDeckArea();
		//
		createHiddenDeckArea();

		handCards = new ArrayList<Polygon>();
		activeCard = null;


		boardRect = new Rectangle();
		boardRect.x = UIConfig.boardX;
		boardRect.y = UIConfig.boardY;
		boardRect.height = UIConfig.boardHeight;
		boardRect.width = UIConfig.boardWidth;
		
		buyRect = new Rectangle();
		buyRect.x = UIConfig.buyX;
		buyRect.y = UIConfig.buyY;
		buyRect.height = UIConfig.buyHeight;
		buyRect.width = UIConfig.buyWidth;
		
		logger = new FPSLogger();
	}

	@Override
	public void render() {
		
		rendering = true;
		logger.log();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		buyArea.fire(new Event());
	
		shapeRenderer.setAutoShapeType(true);
		updateData();
		
		batch.begin();
		drawCardsOnBoard();
		if (showWindow == null)
			drawCardsInHand();
		// drawActiveCard();
		// batch.draw(img, 0, 0);

		batch.end();


	

		shapeRenderer.begin();
		shapeRenderer.rect(boardRect.x, boardRect.y, boardRect.width,
				boardRect.height);
		shapeRenderer.rect(buyRect.x, buyRect.y, buyRect.width, buyRect.height);
		shapeRenderer.end();

		if(showWindow != null)
			showWindow.toFront();
		stage.draw();
		stage.act();

		if (disableUI) {
			if (activeCard != null)
				putActiveCardBackInHand();
		} else {

			checkHandMouseOver();
			// Rightclick to instantly put cards on the field
			if (Gdx.input.justTouched()) {
				if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)
						&& !Gdx.input.isButtonPressed(Input.Buttons.LEFT)
						&& game.getTurnPlayerIndex() == viewIndex) {
					if (activeCard == null) {
						createNewActiveCard();
						if (activeCard != null)
							putActiveCardOnBoard();
						updateData();
					}

				}

			}

			// Checking left mouseclick for dragging cards around
			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				if (activeCard != null) {
					dragActiveCardAround();
				} else {
					createNewActiveCard();
				}
			} else {
				if (activeCard != null) {
					if (isActiveCardOverBoard()) {
						putActiveCardOnBoard();
					} else {
						putActiveCardBackInHand();
					}
				} else {

				}
			}

		}

		batch.begin();
		if(viewIndex != game.getTurnPlayerIndex())
			putActiveCardBackInHand();
		drawActiveCard();
		batch.end();
		rendering = false;
	}

	private void updateData() {

		game.update();
		actionLabel.setText("Aktionen: " + game.getTurnPlayer().getActions());
		buyLabel.setText("Transaktionen: " + game.getTurnPlayer().getBuys());
		goldLabel.setText("Gold: " + game.getTurnPlayer().getGold());

		endTurnBtn.setVisible(true);
		treasureBtn.setVisible(true);
		

		if (game.getState() == DominionController.ACTIONCARDPHASE)
			endTurnBtn.setText("Aktionen beenden");
		else
			endTurnBtn.setText("Zug beenden");

		if (game.getState() == DominionController.TREASURECARDPHASE)
			treasureBtn.setVisible(true);
		else
			treasureBtn.setVisible(false);
		if (game.getTurnPlayer().getTreasureCardsInHand().size() == 0)
			treasureBtn.setVisible(false);
		if (game.getTurnPlayer().getBuys() == 0)
			treasureBtn.setColor(UIConfig.disabledColor);
		else
			treasureBtn.setColor(Color.WHITE);

		if (disableUI) {
			endTurnBtn.setVisible(false);
			treasureBtn.setVisible(false);
		} else {
			endTurnBtn.setVisible(endTurnBtn.isVisible() ? true : false);
			treasureBtn.setVisible(treasureBtn.isVisible() ? true : false);
		}
		
		if(game.getTurnPlayerIndex() != viewIndex)
		{
			disableAllButtons();
		}

		if (game.isTurnOver())
			endTurnBtn.setColor(UIConfig.turnOverColor);
		else
			endTurnBtn.setColor(Color.WHITE);
		updateHandPolygons();

	}
	

	private void disableAllButtons() {
		endTurnBtn.setVisible(false);
		treasureBtn.setVisible(false);
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Drawing Methods for Boards and Hand
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	// draws the board with treasure cards on the bottom, all other cards above
	// them
	// i and j count the number of cards in each row
	private void drawCardsOnBoard() {

		int i = 0;
		int j = 0;
		ArrayList<Card> board = game.getBoard();
		int countTreasure = 0;
		for (Card c : board) {
			if (c.getType() == GameUtils.CARDTYPE_TREASURE)
				countTreasure++;
		}
		if (countTreasure > 0)
			stepTreasure = (UIConfig.boardWidth - UIConfig.boardCardWidth)
					/ (countTreasure);
		if (board.size() > countTreasure)
			stepAction = (UIConfig.boardWidth - UIConfig.boardCardWidth)
					/ ((board.size() - countTreasure));
		if (stepTreasure > UIConfig.defaultstep)
			stepTreasure = UIConfig.defaultstep;
		if (stepAction > UIConfig.defaultstep)
			stepAction = UIConfig.defaultstep;

		for (Card c : board) {
			if (c.getType() == GameUtils.CARDTYPE_TREASURE) {
				batch.draw(c.getTexture(), UIConfig.boardX + i * stepTreasure,
						UIConfig.boardY, UIConfig.boardCardWidth,
						UIConfig.boardCardHeight, 0, 0, UIConfig.textureWidth,
						UIConfig.textureHeight, false, false);
				i++;
			} else {
				batch.draw(c.getTexture(), UIConfig.boardX + j * stepAction,
						UIConfig.boardY + UIConfig.boardCardHeight
								+ UIConfig.heightStep, UIConfig.boardCardWidth,
						UIConfig.boardCardHeight, 0, 0, UIConfig.textureWidth,
						UIConfig.textureHeight, false, false);
				j++;
			}
		}

	}

	private void checkHandMouseOver() {
		Polygon pol = null;
		ArrayList<Card> hand = getActualHand();
		if (activeCard != null)
			return;
		// starting at handCards.size() to get the uppermost card => most right
		// card in hand
		for (int i = handCards.size() - 1; i >= 0; i--) {
			pol = handCards.get(i);
			Rectangle rect = pol.getBoundingRectangle();
			// check for cards in hand that are gonna be scaled / heigt- because
			// of y axis being upside down in mouse inputs
			if (pol.contains(Gdx.input.getX(),
					UIConfig.height - Gdx.input.getY())) {
				float center = rect.getX() + rect.getWidth() / 2;
				drawBorder(hand.get(i), center - UIConfig.mouseOverCardWidth
						/ 2, 0, 0, 0, UIConfig.mouseOverCardWidth,
						UIConfig.mouseOverCardHeight, 0);
				batch.begin();

				batch.draw(hand.get(i).getTexture(), center
						- UIConfig.mouseOverCardWidth / 2, 0,
						UIConfig.mouseOverCardWidth,
						UIConfig.mouseOverCardHeight);
				batch.end();

				break;
			}
		}
	}

	private void drawCardsInHand() {

		ArrayList<Card> hand = getActualHand();
		// Rendering Cards
		if (hand.size() % 2 == 0) {
			// even number of Cards
			for (int i = 0; i < hand.size(); i++) {
				// Draw Card and rotate it around middle => no nonrotated cards
				float turnangle = (float) (-(UIConfig.turnAngle / Math
						.sqrt(hand.size())) * ((i + 0.5) - (hand.size() / 2)));
				batch.end();
				drawBorder(hand.get(i), UIConfig.handX - UIConfig.cWidth / 2,
						0, UIConfig.cWidth / 2, -250, UIConfig.cWidth,
						UIConfig.cHeight, turnangle);
				batch.begin();
				batch.draw(hand.get(i).getTexture(), UIConfig.handX
						- UIConfig.cWidth / 2, 0, UIConfig.cWidth / 2, -250,
						UIConfig.cWidth, UIConfig.cHeight, 1, 1, turnangle, 0,
						0, UIConfig.textureWidth, UIConfig.textureHeight,
						false, false);

			}
		} else {
			// Odd Number of Cards
			for (int i = 0; i < hand.size(); i++) {
				// Draw Card and rotate it around middle => One Card straight
				// others rotated
				float turnangle = (float) (-(UIConfig.turnAngle / Math
						.sqrt(hand.size())) * (i - (hand.size() / 2)));
				batch.end();
				drawBorder(hand.get(i), UIConfig.handX - UIConfig.cWidth / 2,
						0, UIConfig.cWidth / 2, -250, UIConfig.cWidth,
						UIConfig.cHeight, turnangle);
				batch.begin();
				batch.draw(hand.get(i).getTexture(), UIConfig.handX
						- UIConfig.cWidth / 2, 0, UIConfig.cWidth / 2, -250,
						UIConfig.cWidth, UIConfig.cHeight, 1, 1, turnangle, 0,
						0, UIConfig.textureWidth, UIConfig.textureHeight,
						false, false);

			}
		}

	}

	private void drawBorder(Card card, float x, float y, float ox, float oy,
			float cwidth, float cheight, float turnangle) {

		if(viewIndex != game.getTurnPlayerIndex())
			return;
		
		if (!game.isCardPlayable(card)) {
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(UIConfig.wrongColor);
			shapeRenderer.rect(x - 3, y, ox + 3, oy, cwidth + 6, cheight + 3,
					1, 1, turnangle);
			shapeRenderer.setColor(Color.WHITE);
			shapeRenderer.end();
		}
		if (game.isCardPlayable(card)) {
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(UIConfig.rightColor);
			shapeRenderer.rect(x - 3, y, ox + 3, oy, cwidth + 6, cheight + 3,
					1, 1, turnangle);
			shapeRenderer.setColor(Color.WHITE);
			shapeRenderer.end();
		}
	}

	private void updateHandPolygons() {
		Polygon pol;
		ArrayList<Card> hand = getActualHand();
		float x = UIConfig.handX - UIConfig.cWidth / 2;
		// Card Edges before Rotation
		float[] vertices = { x, 0.0f, x + UIConfig.cWidth, 0.0f,
				x + UIConfig.cWidth, UIConfig.cHeight, x, UIConfig.cHeight };
		// Clearing old Cards
		handCards.clear();
		for (int i = 0; i < hand.size(); i++) {
			// Creating new Polygon => rotating it => adding it to handCards for
			// each Card in Hand
			pol = new Polygon();
			pol.setVertices(vertices);
			pol.setOrigin(UIConfig.handX, -250);
			if (hand.size() % 2 == 0) {
				pol.setRotation((float) (-(UIConfig.turnAngle / Math.sqrt(hand
						.size())) * ((i + 0.5) - (hand.size() / 2))));
			} else {
				pol.setRotation((float) (-(UIConfig.turnAngle / Math.sqrt(hand
						.size())) * (i - (hand.size() / 2))));
			}
			

			handCards.add(pol);

		}

	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Active Selected Card Methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// Creates a new active Card from the position of the mousepointer
	private void createNewActiveCard() {
		Polygon pol = null;
		ArrayList<Card> hand = getActualHand();
		// starting at activeCards.size() to get the uppermost card => most
		// right card in hand
		for (int i = handCards.size() - 1; i >= 0; i--) {
			pol = handCards.get(i);
			// check for cards in hand that are gonna be dragged / heigt-
			// because of y axis being upside down in mouse inputs
			if (pol.contains(Gdx.input.getX(),
					UIConfig.height - Gdx.input.getY())) {
				// Checks if card is playable in momentary gamestate
				if (game.isCardPlayable(hand.get(i))) {
					Vector3 mousePos = new Vector3();
					mousePos.set(Gdx.input.getX(),
							UIConfig.height - Gdx.input.getY(), 0);
					camera.unproject(mousePos);
					activeCard = new FloatingCard(hand.get(i), mousePos.x,
							mousePos.y);
				} else {

				}

				break;
			}
		}
	}

	// draws the active card at the position of the mousepointer
	private void drawActiveCard() {
		if (activeCard != null) {
			batch.draw(activeCard.getCard().getTexture(), activeCard
					.getCardRect().getX(), activeCard.getCardRect().getY(), 0,
					0, activeCard.getCardRect().getWidth(), activeCard
							.getCardRect().getHeight(), 1, 1, 0, 0, 0,
					UIConfig.textureWidth, UIConfig.textureHeight, false, false);
		}
	}

	private void dragActiveCardAround() {
		Vector3 mousePos = new Vector3();
		mousePos.set(Gdx.input.getX(), UIConfig.height - Gdx.input.getY(), 0);
		camera.unproject(mousePos);
		activeCard.updatePosition(mousePos.x, mousePos.y);

	}

	private void putActiveCardBackInHand() {
		activeCard = null;
	}

	private boolean isActiveCardOverBoard() {
		Rectangle boardRect = new Rectangle();
		boardRect.x = UIConfig.boardX;
		boardRect.y = UIConfig.boardY;
		boardRect.height = UIConfig.boardHeight;
		boardRect.width = UIConfig.boardWidth;
		return boardRect.contains(activeCard.getCardRect().getCenter(resultVector
				));
	}

	private void putActiveCardOnBoard() {
		game.cardPlayed(activeCard.getCard());
		activeCard = null;
	}

	private ArrayList<Card> getActualHand() {
		ArrayList<Card> ret =(ArrayList<Card>) game.getGameData().getPlayer(viewIndex).getHand().clone();
		if (activeCard != null)
			ret.remove(activeCard.getCard());
		return ret;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// UI CREATION
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void createLabels() {
		playerLabel = new Label(game.getTurnPlayer().getName(), skin);
		playerLabel.setPosition(UIConfig.playerLabelX - playerLabel.getWidth()
				/ 2, UIConfig.playerLabelY);
		actionLabel = new Label(Integer.toString(game.getTurnPlayer()
				.getActions()), skin);
		actionLabel.setPosition(UIConfig.labelPosWidth, UIConfig.labelY);
		buyLabel = new Label(Integer.toString(game.getTurnPlayer().getBuys()),
				skin);
		buyLabel.setPosition(UIConfig.labelPosWidth, UIConfig.labelY
				+ UIConfig.labelstep);
		goldLabel = new Label(Integer.toString(game.getTurnPlayer().getGold()),
				skin);
		goldLabel.setPosition(UIConfig.labelPosWidth, UIConfig.labelY
				+ UIConfig.labelstep * 2);
		stage.addActor(playerLabel);
		stage.addActor(actionLabel);
		stage.addActor(buyLabel);
		stage.addActor(goldLabel);
	}

	private void createButtons() {
		endTurnBtn = new TextButton("Aktionen beenden", skin);
		endTurnBtn.setPosition(UIConfig.labelPosWidth, UIConfig.labelY
				+ UIConfig.labelstep * 3);
		endTurnBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (game.getState() == DominionController.ACTIONCARDPHASE) {
					game.endActions();
					return;
				}
				if (game.getState() == DominionController.TREASURECARDPHASE) {
					game.endTurn();
					return;
				}
			}
		});

		treasureBtn = new TextButton("Aktionen beenden", skin);
		treasureBtn.setText("Gold auslegen");
		treasureBtn.setPosition(UIConfig.labelPosWidth, UIConfig.labelY
				+ UIConfig.labelstep * 4.25f);
		treasureBtn.addListener(new ClickListener() {
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
		System.out.println("Buy Area Size " +b.getBuyableActionCards().size() + "\n");
		final IntegerCardList v = b.getBuyableVictoryCards();
		IntegerCardList t = b.getBuyableTreasureCards();
		IntegerCardList a = b.getBuyableActionCards();

		buyArea = new Table();
		buyArea.left().top();
		buyArea.setPosition(UIConfig.buyX, UIConfig.buyY);
		buyArea.setSize(UIConfig.buyWidth, UIConfig.buyHeight);

		setUpVictoryCards(v);

		setUpTreasureCards(t);

		setUpActionCards(a);

		stage.addActor(buyArea);
	}

	private void setUpActionCards(IntegerCardList a) {
		for (int k = 0; k < (float) a.size() / 4 + 0.5; k++) {
			for (int i = 0; i < 4 && k * 4 + i < a.size(); i++) {
				final Card c = a.getCard(i + k * 4);
				Texture reg2 = new Texture("dominion_textures/cointexture.png");
				Image image2 = new Image(reg2);
				TextureRegion reg = new TextureRegion(c.getTexture(), 0.0f,
						0.0f, 1f, 0.5f);
				Image image = new Image(reg);
				image.setSize(UIConfig.buyImgSize, UIConfig.buyImgSize);

				final Image imagebig = new Image(c.getTexture());
				imagebig.setSize(UIConfig.cardPreviewWidth,
						UIConfig.cardPreviewHeight);
				final Container<Image> dig = new Container<Image>(imagebig);
				dig.setSize(UIConfig.cardPreviewWidth,
						UIConfig.cardPreviewHeight);
				dig.pad(20);
				dig.setColor(100, 100, 100, 100);

				dig.setBackground(UIConfig.previewCardBackground.getDrawable());
				final DominionImageButton btn = new DominionImageButton(image2,
						image, " " + c.getCost(), skin, c, game);
				btn.setSize(UIConfig.buyImgSize, UIConfig.buyImgSize);

				btn.addListener(new InputListener() {
					@Override
					public void enter(InputEvent event, float x, float y,
							int pointer, Actor fromActor) {
						dig.setPosition(UIConfig.width / 2
								- UIConfig.cardPreviewWidth / 2,
								UIConfig.boardY);
						stage.addActor(dig);
						dig.toBack();
						super.enter(event, x, y, pointer, fromActor);
					}

					@Override
					public void exit(InputEvent event, float x, float y,
							int pointer, Actor toActor) {
						dig.remove();
						super.exit(event, x, y, pointer, toActor);
					}
				});
				buyArea.add(btn).width(UIConfig.buyImgSize)
						.height(UIConfig.buyImgSize).colspan(2);
			}
			buyArea.row();

			for (int i = 0; i < 4 && k * 4 + i < a.size(); i++) {
				final Card c = a.getCard(i + k * 4);
				
				TextButton buy = new TextButton("Kaufen", skin);
				CustomLabel remainingCards = new CustomLabel(
						a.getRemainingCards(i + k * 4) + "", skin,
						game, c);
				
				
				
				buy.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						game.cardBought(c);
						

					}
				});
				buyArea.addListener(new EventListener() {

					

					@Override
					public boolean handle(Event event) {
						
					
						if(game.getTurnPlayer().getGold() >= c.getCost() 
								&& game.getGameData().getRemainingCards(c) > 0
								&& game.getTurnPlayer().getBuys() > 0)
							buy.setColor(UIConfig.turnOverColor);
						else
							buy.setColor(Color.WHITE);
						
						if(isDisabled())
							buy.setColor(Color.WHITE);
						
						if (game.getGameData().getRemainingCards(c) == 0) {
							buy.setColor(UIConfig.disabledColor);
							remainingCards.setColor(UIConfig.wrongColor);
						}
						
						return true;
					}
					
					
				});
				
				buyArea.add(buy);
				buyArea.add(remainingCards).prefWidth(UIConfig.coinSize).prefHeight(UIConfig.coinSize);
			}

			buyArea.row();
		}

	}

	private void setUpTreasureCards(IntegerCardList t) {

		for (int i = 0; i < t.size(); i++) {
			Texture reg2 = new Texture("dominion_textures/cointexture.png");
			Image image2 = new Image(reg2);
			final Card c = t.getCard(i);
			TextureRegion reg = new TextureRegion(t.getCard(i).getTexture(),
					0.0f, 0.0f, 1f, 0.5f);
			Image image = new Image(reg);
			image.setSize(UIConfig.buyImgSize, UIConfig.buyImgSize);

			final Image imagebig = new Image(t.getCard(i).getTexture());
			imagebig.setSize(UIConfig.cardPreviewWidth,
					UIConfig.cardPreviewHeight);
			final Container<Image> dig = new Container<Image>(imagebig);
			dig.setSize(UIConfig.cardPreviewWidth, UIConfig.cardPreviewHeight);
			dig.pad(20);
			dig.setColor(100, 100, 100, 100);

			dig.setBackground(UIConfig.previewCardBackground.getDrawable());
			final DominionImageButton btn = new DominionImageButton(image2,
					image, " " + c.getCost(), skin, c, game);
			btn.setSize(UIConfig.buyImgSize, UIConfig.buyImgSize);

			btn.addListener(new InputListener() {
				@Override
				public void enter(InputEvent event, float x, float y,
						int pointer, Actor fromActor) {
					dig.setPosition(UIConfig.width / 2
							- UIConfig.cardPreviewWidth / 2, UIConfig.boardY);
					stage.addActor(dig);
					dig.toBack();
					super.enter(event, x, y, pointer, fromActor);
				}

				@Override
				public void exit(InputEvent event, float x, float y,
						int pointer, Actor toActor) {
					dig.remove();
					super.exit(event, x, y, pointer, toActor);
				}
			});
			
			

			buyArea.add(btn).width(UIConfig.buyImgSize)
					.height(UIConfig.buyImgSize).colspan(2);
		}

		buyArea.row();

		for (int i = 0; i < t.size(); i++) {
			final Card c = t.getCard(i);

			TextButton buy = new TextButton("Kaufen", skin);
			CustomLabel remainingCards = new CustomLabel(t.getRemainingCards(i)
					+ "", skin, game, c);
			buy.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.cardBought(c);
				}
			});

		
			buyArea.add(buy);
			buyArea.add(remainingCards).prefWidth(UIConfig.coinSize).prefHeight(UIConfig.coinSize);
			
			buyArea.addListener(new EventListener() {

				

				@Override
				public boolean handle(Event event) {
					if(buy.getColor() == UIConfig.disabledColor)
						return true;
					if(game.getTurnPlayer().getGold() >= c.getCost() 
							&& game.getGameData().getRemainingCards(c) > 0
							&& game.getTurnPlayer().getBuys() > 0)
						buy.setColor(UIConfig.turnOverColor);
					else
						buy.setColor(Color.WHITE);
					
					if(isDisabled())
						buy.setColor(Color.WHITE);
					if (game.getGameData().getRemainingCards(c) == 0) {
						buy.setColor(UIConfig.disabledColor);
						remainingCards.setColor(UIConfig.wrongColor);
					}
					return true;
				}
				
				
			});
		}

		buyArea.row();
	}

	private void setUpVictoryCards(IntegerCardList v) {

		for (int i = 0; i <= v.size(); i++) {

			Texture reg2 = new Texture("dominion_textures/cointexture.png");
			Image image2 = new Image(reg2);
			final Card c;
			if (i == v.size())
				c = GameUtils.CARD_CURSE;
			else
				c = v.getCard(i);

			TextureRegion reg = new TextureRegion(c.getTexture(), 0.0f, 0.0f,
					1f, 0.5f);
			Image image = new Image(reg);
			image.setSize(UIConfig.buyImgSize, UIConfig.buyImgSize);

			final Image imagebig = new Image(c.getTexture());
			imagebig.setSize(UIConfig.cardPreviewWidth,
					UIConfig.cardPreviewHeight);
			final Container<Image> dig = new Container<Image>(imagebig);
			dig.setSize(UIConfig.cardPreviewWidth, UIConfig.cardPreviewHeight);
			dig.pad(20);
			dig.setColor(100, 100, 100, 100);

			dig.setBackground(UIConfig.previewCardBackground.getDrawable());
			final DominionImageButton btn = new DominionImageButton(image2,
					image, " " + c.getCost(), skin, c, game);
			btn.setSize(UIConfig.buyImgSize, UIConfig.buyImgSize);

			btn.addListener(new InputListener() {
				@Override
				public void enter(InputEvent event, float x, float y,
						int pointer, Actor fromActor) {
					dig.setPosition(UIConfig.width / 2
							- UIConfig.cardPreviewWidth / 2, UIConfig.boardY);
					stage.addActor(dig);
					dig.toBack();
					super.enter(event, x, y, pointer, fromActor);
				}

				@Override
				public void exit(InputEvent event, float x, float y,
						int pointer, Actor toActor) {
					dig.remove();
					
					super.exit(event, x, y, pointer, toActor);
				}
			});

			buyArea.add(btn).width(UIConfig.buyImgSize)
					.height(UIConfig.buyImgSize).colspan(2);
		}

		buyArea.row();

		for (int i = 0; i <= v.size(); i++) {
			final Card c;
			if (i == v.size())
				c = GameUtils.CARD_CURSE;
			else
				c = v.getCard(i);
			TextButton buy = new TextButton("Kaufen", skin);

			CustomLabel remainingCards = new CustomLabel(game.getGameData()
					.getRemainingCards(c) + "", skin, game, c);
			
			buy.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.cardBought(c);
					
				}
			});

			
			buyArea.add(buy);
			buyArea.add(remainingCards).prefWidth(UIConfig.coinSize).prefHeight(UIConfig.coinSize);
			
			buyArea.addListener(new EventListener() {

				

				@Override
				public boolean handle(Event event) {
					if(buy.getColor() == UIConfig.disabledColor)
						return true;
					if(game.getTurnPlayer().getGold() >= c.getCost() 
							&& game.getGameData().getRemainingCards(c) > 0
							&& game.getTurnPlayer().getBuys() > 0)
						buy.setColor(UIConfig.turnOverColor);
					else
						buy.setColor(Color.WHITE);
					
					if(isDisabled())
						buy.setColor(Color.WHITE);
					if (game.getGameData().getRemainingCards(c) == 0) {
						buy.setColor(UIConfig.disabledColor);
						remainingCards.setColor(UIConfig.wrongColor);
					}
					return true;
				}
				
				
			});
		
		}

		buyArea.row();
	}

	private void createDeckArea() {
		deckArea = new DeckUI(game, skin);
		stage.addActor(deckArea);
	}
	
	private void createHiddenDeckArea(){
		opponentsDecks = new HiddenDeckUI[game.getGameData().getPlayerCount()-1];
		
		int index = 0;
		for(int i = 0; i < opponentsDecks.length;i++)
		{
			index = (viewIndex + i + 1) % game.getGameData().getPlayerCount()-1;
			opponentsDecks[i] = new HiddenDeckUI(game, skin, index,i);
			stage.addActor(opponentsDecks[i]);
		}
		
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Other Methods mainly for displaying effects
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void showCardForAdventurer(Card c) {
		showCard = new Image(c.getTexture());
		showCard.setSize(UIConfig.mouseOverCardWidth,
				UIConfig.mouseOverCardHeight);
		float boardCenterX = UIConfig.boardX + UIConfig.boardWidth / 2;
		showCard.setPosition(boardCenterX - UIConfig.mouseOverCardWidth / 2,
				UIConfig.boardY);
		showCardBackground = new TextButton("", skin);
		float borderSize = 5;
		float doublebrd = borderSize * 2;
		showCardBackground.setSize(UIConfig.mouseOverCardWidth + doublebrd,
				UIConfig.mouseOverCardHeight + doublebrd);
		showCardBackground.setPosition(boardCenterX
				- UIConfig.mouseOverCardWidth / 2 - borderSize, UIConfig.boardY
				- borderSize);
		if (c.getType() == GameUtils.CARDTYPE_TREASURE)
			showCardBackground.setColor(UIConfig.turnOverColor);
		else
			showCardBackground.setColor(UIConfig.wrongColor);
		stage.addActor(showCardBackground);
		stage.addActor(showCard);
	}
	
	public void showBoughtCard(Card c)
	{
		showCardWithText(c, "Karte gekauft!" );		
	}
	public void showCardWithText(Card c, String text)
	{
		float boardCenterX = UIConfig.boardX + UIConfig.boardWidth / 2;
		showCard = new Image(c.getTexture());
		showCard.setSize(UIConfig.mouseOverCardWidth,
				UIConfig.mouseOverCardHeight);
		showCard.setPosition(boardCenterX - UIConfig.mouseOverCardWidth / 2,
				UIConfig.boardY);
		showCardBackground = new TextButton(text, skin);
		showCardBackground.getLabel().setAlignment(Align.bottom);
		showCardBackground.getLabel().setColor(UIConfig.coinColor);
		showCardBackground.align(Align.bottom);
		float borderSize = 10;
				float bigBorder = borderSize + borderSize*3;
		showCardBackground.setSize(UIConfig.mouseOverCardWidth+ borderSize*2,
				UIConfig.mouseOverCardHeight + bigBorder);
		showCardBackground.setPosition(boardCenterX
				- UIConfig.mouseOverCardWidth / 2 - borderSize , UIConfig.boardY
				- bigBorder + borderSize);
		
		stage.addActor(showCardBackground);
		stage.addActor(showCard);
	}
	
	public void showDefenseOrCurseCard(Card c, Player p)
	{
		showCardWithText(c, p.getName());
	}

	public void stopShowingCard() {
		showCard.remove();
		showCardBackground.remove();
	}

	public void disableUI() {
		disableUI = true;
	}

	public void enableUI() {
		disableUI = false;
	}

	public void showNewPlayer() {
		showWindow = new Dialog("", skin);
		float size = 300;
		showWindow.setPosition(UIConfig.boardX + UIConfig.boardWidth / 2 - size
				/ 2, UIConfig.boardY + UIConfig.boardHeight / 2);
		showWindow.setColor(UIConfig.windowColor);

		Label playerLab = new Label(" " + game.getTurnPlayer().getName()
				+ "s Zug!", skin);
		playerLab.setAlignment(Align.center);
		showWindow.text(playerLab);

		showWindow.setSize(size, 50);
		
		stage.addActor(showWindow);
	}

	public void showPlayerHand(Player p) {
		showWindow = new Dialog("", skin);

	}

	public void stopShowingNewPlayer() {
		showWindow.clear();
		showWindow.remove();
		showWindow = null;
	}

	public boolean isRendering() {
		return rendering;
	}

	public boolean isDisabled() {
		return disableUI;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
		this.create();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		this.render();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

}
