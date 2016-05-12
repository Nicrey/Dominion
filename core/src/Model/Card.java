package Model;

import com.badlogic.gdx.graphics.Texture;

public class Card {

	private String name;
	private String texture;
	private int cost;
	private int type;
	private String effect;
	
	public Card(){
		name = "";
		texture = "";
		cost = 0;
		type = -1;
		effect = "";
	}
	public Card(String name, String cardTexture, int cost, int type, String effect)
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
		return GameUtils.getTexture(this);
	}
	public void setTexture(String texture) {
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

	public int compareTo(Card i2) {
		return this.cost < i2.getCost() ? -1 : 1;
	}
	public String getTextureString() {
		return texture;
	}
	/*@Override
	public boolean equals(Object obj) {
		return equals((Card)obj);
	}
	
	public boolean equals(Card obj) {
		return obj.name.equals(this.name);
	}*/
	
	
	
	
	
	
}
