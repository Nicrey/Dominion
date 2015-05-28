package com.mygdx.Dominion.model;

import java.io.Serializable;

public class GameData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8268623994508727036L;

	private Board board;
	private int player;

	public GameData(Board b, int turnPlayer) {
		this.board = b;
		this.player = turnPlayer;
	}

	public Board getBoard() {
		return board;
	}

	public int getPlayer() {
		return player;
	}

}
