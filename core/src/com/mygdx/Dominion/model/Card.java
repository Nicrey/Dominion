package com.mygdx.Dominion.model;

import com.badlogic.gdx.graphics.Texture;

public class Card {

	private String name;
	private Texture texture;
	private int cost;
	private int type;
	private String effect;
	
	public Card(String name, Texture cardTexture, int cost, int type, String effect)
	{
		this.name = name;
		this.texture = cardTexture;
		this.cost = cost;
		this.type = type;
		this.effect = effect;
	}
	
	public Card(Card c)
	{
		this.name = c.name;
		this.texture = c.texture;
		this.cost = c.cost;
		this.type = c.type;
		this.effect = c.effect;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Texture getTexture() {
		return texture;
	}
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getEffect() {
		return effect;
	}
	public void setEffect(String effect) {
		this.effect = effect;
	}
	
	
	
	
}
