package com.mygdx.Dominion.model;

import com.badlogic.gdx.graphics.Texture;

public class GameUtils {
	

	//Gamemodes
	public static final int ALLCARDS 	  = -1;
	public static final int NOCARDS       = 0; //No Cards playable
	public static final int ACTIONCARDS   = 1; //Actioncards
	public static final int TREASURECARDS = 2; //Goldcardsplayable
	public static final int DEFENSECARDS  = 3;
	
	//Types of Cards
	public static final int ACTION   = 0;
	public static final int TREASURE = 1;
	public static final int VICTORY  = 2;
	public static final int CURSE 	 = 3; 
	//Name of Cards-----------------------------------------------------------------------------------------------------//

	//Action Cards
	public static final String NAME_VILLAGE = "Dorf"; 
	
	//Treasure Cards
	public static final String NAME_COPPER = "Kupfer";
	
	//Victory Cards
	public static final String NAME_ESTATE = "Anwesen";

	//Effects of Cards-----------------------------------------------------------------------------------------------------//
	// Actioneffects between || copper between _ Victory between *
	//Action Cards
	public static final String EFFECT_VILLAGE = "|A+2,D+1|"; //+2 Actions +1 Draw
	
	//Treasure Cards
	public static final String EFFECT_COPPER = "_1_";
	
	//Victory Cards
	public static final String EFFECT_ESTATE = "*1*";
	//----------------------------------------------------------------------------------------------------------------//
	//Textures of Cards-----------------------------------------------------------------------------------------------//
	//Action Cards
	public static final Texture TEXTURE_VILLAGE =new Texture("dominion_textures/texture_village.jpg");
	
	//Treasure Cards
	public static final Texture TEXTURE_COPPER =new Texture("dominion_textures/texture_copper.jpg");
	
	//victory Cards
	public static final Texture TEXTURE_ESTATE =new Texture("dominion_textures/texture_estate.jpg");
	//--------------------------------------------------------------------------------------------------------------//
	//Cost of Cards-------------------------------------------------------------------------------------------------//
	//Action Cards
	public static final int COST_VILLAGE= 3;
	
	//Treasure Cards
	public static final int COST_COPPER = 0;
	
	//Victory Cards
	public static final int COST_ESTATE = 2;
	
	//--------------------------------------------------------------------------------------------------------------//
	//Cards---------------------------------------------------------------------------------------------------------//
	//Action Cards
	public static final Card CARD_VILLAGE = new Card(NAME_VILLAGE, TEXTURE_VILLAGE, COST_VILLAGE, ACTION, EFFECT_VILLAGE);
	
	//Treasure Cards
	public static final Card CARD_COPPER = new Card(NAME_COPPER, TEXTURE_COPPER, COST_COPPER, TREASURE, EFFECT_COPPER);
	
	//Victory Cards
	public static final Card CARD_ESTATE = new Card(NAME_ESTATE, TEXTURE_ESTATE, COST_ESTATE, VICTORY, EFFECT_ESTATE);
	

}
