package com.mygdx.Dominion.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.Dominion.UI.UIConfig;

public class GameUtils {
	

	//Gamemodes
	public static final int ALLCARDS 	  = -1;
	public static final int NOCARDS       = 0; //No Cards playable
	public static final int ACTIONCARDS   = 1; //Actioncards
	public static final int TREASURECARDS = 2; //Goldcardsplayable
	public static final int DEFENSECARDS  = 3;
	
	//Types of Cards
	//When adding new Cardtypes change Board methods : createBuyableCards !
	//And IntegerCardList!
	public static final int CARDTYPE_ACTION   = 0;
	public static final int CARDTYPE_TREASURE = 1;
	public static final int CARDTYPE_VICTORY  = 2;
	public static final int CARDTYPE_CURSE 	 = 3; 
	//Name of Cards-----------------------------------------------------------------------------------------------------//

	//Action Cards
	//6 cost default set
	public static final String NAME_ADVENTURER = "Abenteurer"; 
	//5 cost default set
	public static final String NAME_MARKET = "Markt";
	public static final String NAME_WITCH = "Hexe";
	public static final String NAME_FESTIVAL = "Jahrmarkt";
	public static final String NAME_LABORATORY = "Laboratorium";
	public static final String NAME_MINE = "Mine";
	public static final String NAME_LIBRARY = "Bibliothek";
	public static final String NAME_COUNCILROOM = "Ratsversammlung";
	//4 cost default set
	public static final String NAME_THRONEROOM = "Thronsaal";
	public static final String NAME_SMITHY = "Schmied";
	public static final String NAME_THIEF = "Dieb";
	public static final String NAME_SPY = "Spion";
	public static final String NAME_REMODEL = "Umbau";
	public static final String NAME_MONEYLENDER = "Geldverleiher";
	public static final String NAME_MILITIA = "Miliz";
	public static final String NAME_GARDENS = "G�rten";
	public static final String NAME_FEAST = "Festmahl";
	public static final String NAME_BUREAUCRAT = "B�rokrat";
	//3 cost default set
	public static final String NAME_WORKSHOP = "Werkstatt";
	public static final String NAME_WOODCUTTER = "Holzf�ller";
	public static final String NAME_CHANCELLOR = "Kanzler";
	public static final String NAME_VILLAGE = "Dorf"; //+2 Actions +1 Draw
	//2 cost default set
	public static final String NAME_MOAT = "Burggraben";
	public static final String NAME_CHAPEL = "Kapelle";
	public static final String NAME_CELLAR = "Keller";
	//Treasure Cards
	public static final String NAME_COPPER = "Kupfer";
	public static final String NAME_SILVER = "Silber";
	public static final String NAME_GOLD = "Gold";
	
	//Victory Cards
	public static final String NAME_ESTATE = "Anwesen";
	public static final String NAME_DUCHY = "Herzogtum";
	public static final String NAME_PROVINCE = "Provinz";
	
	public static final String NAME_CURSE = "Fluch";

	//Effects of Cards-----------------------------------------------------------------------------------------------------//
	//Effects separated by |, parameters for effect separated with , 
	//EFFECTS: 
	// 		A  => Gain a Number of Actions
	//		D  => Draw a Number of Cards
	//		G  => Gain a Number of Gold
	//		B  => Gain a Number of Buys
	//		C  => Give every other player an amount of Curses ( C,+1,Atk is the correct syntax to define a curse attack)
	//		MIN  => Effect of the mine card 
	//		LIB  => Effect of the library Card
	//		DOP  => DRAW OTHER PLAYERS, Lets all other players draw an amount of Cards
	//		THR  => Effect of the throne room card
	//		THF  => Effect of the thief card
	//		SPY  => Effect of the Spy card
	//		RMD  => Effect of the Remodel Card
	//		MLD  => Effect of the Moneylender Card
	//		MIL  => Effect of the Militia Card, Discard of all players down to amount
	//		GRD  => Effect of the Gardens Card
	//		FST  => Effect of the Feast Card
	//		BRC  => Effect of the Bureaucrat Card	
	//		WKS  => Gain a card up to amount gold, WORKSHOP effect
	//		CEL  => Effect of the cellar card
	//		THR  => effect of Chapel, Trash a number of cards
	//		DEF  => DEFENSIVE card that can be used against attacks
	//		ADV  => EFFECt of the adventurer card
	//		CAN  => CHancellor
	
	//Action Cards
	//6 cost default set
	public static final String EFFECT_ADVENTURER = "ADV";
	//5 cost default set
	public static final String EFFECT_MARKET = "A,+1|D,+1|B,+1|G,+1";
	public static final String EFFECT_WITCH = "D,+2|C,+1,ATK";
	public static final String EFFECT_FESTIVAL = "A,+2|G,+2|B,+1";
	public static final String EFFECT_LABORATORY = "A,+1|D,+2";
	public static final String EFFECT_MINE = "MIN";
	public static final String EFFECT_LIBRARY = "LIB";
	public static final String EFFECT_COUNCILROOM = "D,+4|B,+1|DOP,+1";
	//4 cost default set
	public static final String EFFECT_THRONEROOM = "THR";
	public static final String EFFECT_SMITHY = "D,+3";
	public static final String EFFECT_THIEF = "THF,ATK";
	public static final String EFFECT_SPY = "D,+1|A,+1|SPY,ATK";
	public static final String EFFECT_REMODEL = "RMD";
	public static final String EFFECT_MONEYLENDER = "MLD";
	public static final String EFFECT_MILITIA = "G,+2|MIL,+3,ATK";
	public static final String EFFECT_GARDENS = "GRD";
	public static final String EFFECT_FEAST = "FST";
	public static final String EFFECT_BUREAUCRAT = "BRC,ATK";
	//3 cost default set
	public static final String EFFECT_WORKSHOP = "WKS,+4";
	public static final String EFFECT_WOODCUTTER = "B,+1|G,+2";
	public static final String EFFECT_CHANCELLOR = "G,+2|CAN";
	public static final String EFFECT_VILLAGE = "A,+2|D,+1"; //+2 Actions +1 Draw
	//2 cost default set
	public static final String EFFECT_MOAT = "D,+2|DEF";
	public static final String EFFECT_CHAPEL = "THR,+4";
	public static final String EFFECT_CELLAR = "A,+1|CEL";
	//Treasure Cards
	public static final String EFFECT_COPPER = "G,+1";
	public static final String EFFECT_SILVER = "G,+2";
	public static final String EFFECT_GOLD = "G,+3";
	
	//Victory Cards
	public static final String EFFECT_ESTATE = "V,+1";
	public static final String EFFECT_DUCHY = "V,+3";
	public static final String EFFECT_PROVINCE = "V,+3";
	
	public static final String EFFECT_CURSE = "V,-1";
	//----------------------------------------------------------------------------------------------------------------//
	//Textures of Cards-----------------------------------------------------------------------------------------------//
	//Action Cards
	public static final String TEXTURESTR_VILLAGE = ("dominion_textures/texture_village.jpg");
	public static final String TEXTURESTR_MARKET  = ("dominion_textures/texture_market.jpg");
	public static final String TEXTURESTR_LABORATORY = ("dominion_textures/texture_laboratory.jpg");
	public static final String TEXTURESTR_WITCH = ("dominion_textures/texture_witch.jpg");
	public static final String TEXTURESTR_FESTIVAL = ("dominion_textures/texture_festival.jpg");
	public static final String TEXTURESTR_WOODCUTTER = ("dominion_textures/texture_woodcutter.jpg");
	public static final String TEXTURESTR_CHANCELLOR = ("dominion_textures/texture_chancellor.jpg");
	public static final String TEXTURESTR_MOAT = ("dominion_textures/texture_moat.jpg");
	public static final String TEXTURESTR_SMITHY = ("dominion_textures/texture_smithy.jpg");
	public static final String TEXTURESTR_ADVENTURER = ("dominion_textures/texture_adventurer.jpg");
	public static final String TEXTURESTR_GARDENS = ("dominion_textures/texture_garden.jpg");
	public static final String TEXTURESTR_COUNCILROOM = ("dominion_textures/texture_councilroom.jpg");
	//Treasure Cards
	public static final String TEXTURESTR_COPPER = ("dominion_textures/texture_copper.jpg");
	public static final String TEXTURESTR_SILVER = ("dominion_textures/texture_silver.jpg");
	public static final String TEXTURESTR_GOLD = ("dominion_textures/texture_gold.jpg");
	
	//victory Cards
	public static final String TEXTURESTR_ESTATE = ("dominion_textures/texture_estate.jpg");
	public static final String TEXTURESTR_DUCHY = ("dominion_textures/texture_duchy.jpg");
	public static final String TEXTURESTR_PROVINCE =("dominion_textures/texture_province.jpg");
	
	public static final String TEXTURESTR_CURSE = ("dominion_textures/texture_curse.jpg");
	
	public static final Texture TEXTURE_VILLAGE = new Texture("dominion_textures/texture_village.jpg");
	public static final Texture TEXTURE_MARKET  = new Texture("dominion_textures/texture_market.jpg");
	public static final Texture TEXTURE_LABORATORY = new Texture("dominion_textures/texture_laboratory.jpg");
	public static final Texture TEXTURE_WITCH = new Texture("dominion_textures/texture_witch.jpg");
	public static final Texture TEXTURE_FESTIVAL = new Texture("dominion_textures/texture_festival.jpg");
	public static final Texture TEXTURE_WOODCUTTER = new Texture("dominion_textures/texture_woodcutter.jpg");
	public static final Texture TEXTURE_CHANCELLOR = new Texture("dominion_textures/texture_chancellor.jpg");
	public static final Texture TEXTURE_MOAT = new Texture("dominion_textures/texture_moat.jpg");
	public static final Texture TEXTURE_SMITHY = new Texture("dominion_textures/texture_smithy.jpg");
	public static final Texture TEXTURE_ADVENTURER = new Texture("dominion_textures/texture_adventurer.jpg");
	public static final Texture TEXTURE_GARDENS = new Texture("dominion_textures/texture_garden.jpg");
	public static final Texture TEXTURE_COUNCILROOM = new Texture("dominion_textures/texture_councilroom.jpg");
	
	//Treasure Cards
	public static final Texture TEXTURE_COPPER = new Texture("dominion_textures/texture_copper.jpg");
	public static final Texture TEXTURE_SILVER = new Texture("dominion_textures/texture_silver.jpg");
	public static final Texture TEXTURE_GOLD = new Texture("dominion_textures/texture_gold.jpg");
	
	//victory Cards
	public static final Texture TEXTURE_ESTATE = new Texture("dominion_textures/texture_estate.jpg");
	public static final Texture TEXTURE_DUCHY = new Texture("dominion_textures/texture_duchy.jpg");
	public static final Texture TEXTURE_PROVINCE = new Texture("dominion_textures/texture_province.jpg");
	
	public static final Texture TEXTURE_CURSE = new Texture("dominion_textures/texture_curse.jpg");
	
	public static Map<String, Texture> textureMap;
	
	//--------------------------------------------------------------------------------------------------------------//
	//Cost of Cards-------------------------------------------------------------------------------------------------//
	
	//Action Cards
	public static final int COST_VILLAGE = 3;
	public static final int COST_MARKET  = 5;
	public static final int COST_LABORATORY = 5;
	public static final int COST_WITCH = 5;
	public static final int COST_FESTIVAL = 5;
	public static final int COST_WOODCUTTER = 3;
	public static final int COST_CHANCELLOR = 3;
	public static final int COST_MOAT = 2;
	public static final int COST_SMITHY = 4;
	public static final int COST_ADVENTURER = 6;
	public static final int COST_GARDEN = 4;
	public static final int COST_COUNCILROOM = 5;
	//Treasure Cards
	public static final int COST_COPPER = 0;
	public static final int COST_SILVER = 3;
	public static final int COST_GOLD = 6;
	
	//Victory Cards
	public static final int COST_ESTATE = 2;
	public static final int COST_DUCHY = 5;
	public static final int COST_PROVINCE = 8;
	
	public static final int COST_CURSE = 0;
	//--------------------------------------------------------------------------------------------------------------//
	//Cards---------------------------------------------------------------------------------------------------------//
	//Action Cards
	public static final Card CARD_VILLAGE = new Card(NAME_VILLAGE, TEXTURESTR_VILLAGE, COST_VILLAGE, CARDTYPE_ACTION, EFFECT_VILLAGE);
	public static final Card CARD_MARKET = new Card(NAME_MARKET, TEXTURESTR_MARKET, COST_MARKET,CARDTYPE_ACTION, EFFECT_MARKET);
	public static final Card CARD_LABRATORY = new Card(NAME_LABORATORY, TEXTURESTR_LABORATORY , COST_LABORATORY, CARDTYPE_ACTION,EFFECT_LABORATORY);
	public static final Card CARD_WITCH = new Card(NAME_WITCH,TEXTURESTR_WITCH, COST_WITCH, CARDTYPE_ACTION, EFFECT_WITCH);
	public static final Card CARD_FESTIVAL = new Card(NAME_FESTIVAL, TEXTURESTR_FESTIVAL, COST_FESTIVAL, CARDTYPE_ACTION, EFFECT_FESTIVAL);
	public static final Card CARD_WOODCUTTER = new Card(NAME_WOODCUTTER, TEXTURESTR_WOODCUTTER, COST_WOODCUTTER, CARDTYPE_ACTION, EFFECT_WOODCUTTER);
	public static final Card CARD_CHANCELLOR = new Card(NAME_CHANCELLOR, TEXTURESTR_CHANCELLOR, COST_CHANCELLOR, CARDTYPE_ACTION, EFFECT_CHANCELLOR);
	public static final Card CARD_MOAT = new Card(NAME_MOAT, TEXTURESTR_MOAT,COST_MOAT,CARDTYPE_ACTION, EFFECT_MOAT);
	public static final Card CARD_SMITHY = new Card(NAME_SMITHY,TEXTURESTR_SMITHY, COST_SMITHY, CARDTYPE_ACTION, EFFECT_SMITHY);
	public static final Card CARD_ADVENTURER = new Card(NAME_ADVENTURER, TEXTURESTR_ADVENTURER, COST_ADVENTURER, CARDTYPE_ACTION, EFFECT_ADVENTURER);
	public static final Card CARD_GARDENS = new Card(NAME_GARDENS, TEXTURESTR_GARDENS, COST_GARDEN, CARDTYPE_VICTORY, EFFECT_GARDENS);
	public static final Card CARD_COUNCILROOM = new Card(NAME_COUNCILROOM, TEXTURESTR_COUNCILROOM, COST_COUNCILROOM, CARDTYPE_ACTION, EFFECT_COUNCILROOM);
	
	//Treasure Cards
	public static final Card CARD_COPPER = new Card(NAME_COPPER, TEXTURESTR_COPPER, COST_COPPER, CARDTYPE_TREASURE, EFFECT_COPPER);
	public static final Card CARD_SILVER = new Card(NAME_SILVER, TEXTURESTR_SILVER, COST_SILVER, CARDTYPE_TREASURE, EFFECT_SILVER);
	public static final Card CARD_GOLD = new Card(NAME_GOLD, TEXTURESTR_GOLD, COST_GOLD, CARDTYPE_TREASURE, EFFECT_GOLD);
	
	//Victory Cards
	public static final Card CARD_ESTATE = new Card(NAME_ESTATE, TEXTURESTR_ESTATE, COST_ESTATE, CARDTYPE_VICTORY, EFFECT_ESTATE);
	public static final Card CARD_DUCHY = new Card(NAME_DUCHY, TEXTURESTR_DUCHY, COST_DUCHY, CARDTYPE_VICTORY, EFFECT_DUCHY);
	public static final Card CARD_PROVINCE = new Card(NAME_PROVINCE, TEXTURESTR_PROVINCE, COST_PROVINCE, CARDTYPE_VICTORY, EFFECT_PROVINCE);
	
	public static final Card CARD_CURSE = new Card(NAME_CURSE, TEXTURESTR_CURSE, COST_CURSE, CARDTYPE_CURSE, EFFECT_CURSE);
	
	//Other
	public static final Card CARD_BACK = new Card("Unknown", UIConfig.cardBackStr, 0 , -1, "");
	
	public static ArrayList<Card> cardSet = new ArrayList<Card>();
	
	public static Collection<? extends Card> getCardSet(String string) {
		
		
		//Add Gold Cards
		cardSet.add(CARD_COPPER);
		cardSet.add(CARD_SILVER);
		cardSet.add(CARD_GOLD);
		
		//Add Victory Cards
		cardSet.add(CARD_ESTATE);
		cardSet.add(CARD_DUCHY);
		cardSet.add(CARD_PROVINCE);
		cardSet.add(CARD_CURSE);
		
		if(string.equals("default"))
		{	
			//Add Action Cards
			cardSet.add(CARD_VILLAGE);
			cardSet.add(CARD_MARKET);
			cardSet.add(CARD_LABRATORY);
			cardSet.add(CARD_WITCH);
			cardSet.add(CARD_FESTIVAL);
			cardSet.add(CARD_WOODCUTTER);
			cardSet.add(CARD_CHANCELLOR);
			cardSet.add(CARD_MOAT);
			cardSet.add(CARD_SMITHY);
			cardSet.add(CARD_ADVENTURER);	
			cardSet.add(CARD_GARDENS);
			cardSet.add(CARD_COUNCILROOM);
		}
		
		
		
		return cardSet;
	}
	public static void initUtils(String set){
		getCardSet(set);
		textureMap = new HashMap<>();
		textureMap.put(TEXTURESTR_MARKET, TEXTURE_MARKET);
		textureMap.put(TEXTURESTR_ADVENTURER, TEXTURE_ADVENTURER);
		textureMap.put(TEXTURESTR_LABORATORY, TEXTURE_LABORATORY);
		textureMap.put(TEXTURESTR_WITCH, TEXTURE_WITCH);
		textureMap.put(TEXTURESTR_FESTIVAL, TEXTURE_FESTIVAL);
		textureMap.put(TEXTURESTR_VILLAGE, TEXTURE_VILLAGE);
		textureMap.put(TEXTURESTR_WOODCUTTER, TEXTURE_WOODCUTTER);
		textureMap.put(TEXTURESTR_CHANCELLOR, TEXTURE_CHANCELLOR);
		textureMap.put(TEXTURESTR_MOAT, TEXTURE_MOAT);
		textureMap.put(TEXTURESTR_SMITHY, TEXTURE_SMITHY);
		textureMap.put(TEXTURESTR_GARDENS, TEXTURE_GARDENS);
		textureMap.put(TEXTURESTR_COUNCILROOM, TEXTURE_GARDENS);
		
		textureMap.put(TEXTURESTR_COPPER, TEXTURE_COPPER);
		textureMap.put(TEXTURESTR_SILVER, TEXTURE_SILVER);
		textureMap.put(TEXTURESTR_GOLD, TEXTURE_GOLD);
		
		textureMap.put(TEXTURESTR_ESTATE, TEXTURE_ESTATE);
		textureMap.put(TEXTURESTR_DUCHY, TEXTURE_DUCHY);
		textureMap.put(TEXTURESTR_PROVINCE, TEXTURE_PROVINCE);
		
		textureMap.put(TEXTURESTR_CURSE, TEXTURE_CURSE);
		
		textureMap.put(UIConfig.cardBackStr, UIConfig.cardBack);
	}

	public static Texture getTexture(Card card) {
		return textureMap.get(card.getTextureString());
	}
	public static void printHand(ArrayList<Card> ret) {
		System.out.println("Hand: \n {");
		for(Card c: ret)
			System.out.print(c.getName() + "|");
		System.out.print("}");
	}
	
	

}
