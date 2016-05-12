package Network.Controller;

import java.util.ArrayList;

import Controller.EffectParser;
import Model.Card;
import Model.GameUtils;
import Model.Player;

public class DominionServerEffectParser {

	
	private DominionServerController game;

	public DominionServerEffectParser(DominionServerController game) {
		this.game = game;
	}
	public void resolveEffect(Card c)  {
		for (String s : c.getEffect().split("\\|")) {

			resolveSingleEffect(formatString(s));
		}

	}

	private static String formatString(String s) {
		return s.toLowerCase();
	}
	
	private void resolveSingleEffect(String s)

	{

		String[] parts = s.split(",");
		ArrayList<Player> attackedPlayers = new ArrayList<Player>();

		if (EffectParser.isAttackCard(s))
			attackedPlayers = game.getDefenselessPlayers();

		// Normal Card Effects
		if (parts[0].equals("a"))
			game.getTurnPlayer().addActions(Integer.parseInt(parts[1]));
		if (parts[0].equals("d"))
			runDrawEffect(Integer.parseInt(parts[1]));
		if (parts[0].equals("g"))
			game.getTurnPlayer().addGold(Integer.parseInt(parts[1]));
		if (parts[0].equals("b"))
			game.getTurnPlayer().addBuys(Integer.parseInt(parts[1]));
		if (parts[0].equals("dop"))
			game.drawForOtherPlayers(Integer.parseInt(parts[1]));
		if (parts[0].equals("can"))
			game.getTurnPlayer().putDeckInGraveyard();

		if (parts[0].equals("adv"))
			runAdventurerEffect();

		// Attack Cards
		if (parts[0].equals("c"))
			runWitchEffect();

		// Victory Effects
	}

	public void runDrawEffect(int amount) {
		for (int i = 0; i < amount; i++) {
			game.getTurnPlayer().drawCard();
		}
	}

	public void runAdventurerEffect() {
		int count = 0;
		int deckcount = 0;
		int possibleDraws = game.getTurnPlayer().getCompleteDeckSize()
				- game.getTurnPlayer().getHandSize()
				+ game.getTurnPlayer().getDeckSize();
		while (count < 2 && deckcount < possibleDraws) {

			if (game.getTurnPlayer().lookAtNextCard() == null)
				break;

			if (game.getTurnPlayer().lookAtNextCard().getType() == GameUtils.CARDTYPE_TREASURE) {
				count++;
				game.getTurnPlayer().drawCard();
			} else
				game.getTurnPlayer().discardTopDeckCard();
			deckcount++;
			
		}
	}

	public void runWitchEffect() {
		for (Player p : game.getBoard().getPlayers()) {
			if (p == game.getTurnPlayer())
				continue;
			if (!p.hasDefenseCard()) {
				game.addCurseToPlayer(p);
			}
		}
	}
}
	
