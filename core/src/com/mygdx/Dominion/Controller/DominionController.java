package com.mygdx.Dominion.Controller;

import com.mygdx.Dominion.UI.DominionUI;
import com.mygdx.Dominion.model.Board;

public class DominionController {

	DominionUI view; 
	int currentPlayer;
	Board game;
	
	public DominionController(DominionUI view )
	{
		this.view = view;
		game = new Board();
	}
	
	
	private void reset()
	{
		
	}
}
