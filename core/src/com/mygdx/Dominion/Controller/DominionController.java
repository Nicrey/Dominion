package com.mygdx.Dominion.Controller;

import com.mygdx.Dominion.UI.DominionUI;
import com.mygdx.Dominion.model.Board;

public class DominionController {

	private static final int ACTIONCARDPHASE = 0;
	private static final int TREASURECARDPHASE = 1;
	
	private DominionUI view; 
	private int currentPlayer;
	private Board game;
	
	
	public DominionController(DominionUI view )
	{
		this.view = view;
		game = new Board();
		currentPlayer = 0;
	}
	
	
	private void reset()
	{
		currentPlayer = 0;
	}
}
