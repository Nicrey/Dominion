package com.mygdx.Dominion.model;

import java.io.Serializable;

public class GameData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8268623994508727036L;
	
	private Board board;
	private Player player;
	private int state;
	
	 public GameData(Board b, Player turnPlayer, int state )
	 {
		 this.board = b;
		 this.player = turnPlayer ;
		 this.state = state;
	 }
}
