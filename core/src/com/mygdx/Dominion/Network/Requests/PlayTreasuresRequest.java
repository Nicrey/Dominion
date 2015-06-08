package com.mygdx.Dominion.Network.Requests;

public class PlayTreasuresRequest {

	private int index;
	
	public PlayTreasuresRequest(){
		index = -1;
	}
	
	public PlayTreasuresRequest(int index){
		this.index = index;
	}
	
	public int getIndex(){
		return index;
	}
}
