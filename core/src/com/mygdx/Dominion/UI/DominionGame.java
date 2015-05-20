package com.mygdx.Dominion.UI;

import java.io.IOException;

import com.badlogic.gdx.Game;

public class DominionGame extends Game {

	

	@Override
	public void create() {
		// TODO Auto-generated method stub
		DominionUI game = new DominionUI();
		MenuScreen menu = null;
		try {
			 menu = new MenuScreen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setScreen(menu);
		
		
	}

}
