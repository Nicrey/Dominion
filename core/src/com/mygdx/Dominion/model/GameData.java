package com.mygdx.Dominion.model;

import java.io.Serializable;

public class GameData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8268623994508727036L;

	private Board board;
	private int player;
	private int state;

	public GameData(){
		board = null;
		player = 0;
		state = 0;
	}
	public GameData(Board b, int turnPlayer, int state) {
		this.board = b;
		this.player = turnPlayer;
		this.state = state;
	}

	public Board getBoard() {
		return board;
	}

	public int getPlayer() {
		return player;
	}

	public int getState() {
		return state;
	}

}
