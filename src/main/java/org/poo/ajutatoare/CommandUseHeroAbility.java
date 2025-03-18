package org.poo.ajutatoare;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.fileio.ActionsInput;
import org.poo.fileio.CardInput;

import java.util.ArrayList;

public class CommandUseHeroAbility {
    private ArrayList<Player> players;
    private int currentPlayer;
    private ArrayNode output;
    private OutputJson out;
    private ActionsInput action;
    private ObjectMapper objectMapper;
    static final int ROWS = 3;
    private GameTable gameTable;

    public CommandUseHeroAbility(final ArrayList<Player> players, final int currentPlayer,
                                 final ArrayNode output,final OutputJson out,
                                 final ActionsInput action, final ObjectMapper objectMapper,
                                 final GameTable gameTable) {
        this.players = players;
        this.currentPlayer = currentPlayer;
        this.output = output;
        this.out = out;
        this.action = action;
        this.objectMapper = objectMapper;
        this.gameTable = gameTable;
    }

    /**
     *
     * @param frozenCont = to set the frozen flag if cards are going to be frozen
     * @return frozenCont incremented
     */

    public int heroAbility(final int frozenCont) {

        int ok1 = 1, ok2 = 1;
        Player player = players.get(currentPlayer - 1);
        CardInput hero = players.get(currentPlayer - 1).gethero();

        assert hero != null;
        if (player.getMana() < hero.getMana()) {
            ok1 = 0;
            String error = "Not enough mana to use hero's ability.";
            out.outputHeroAbility(action, error, objectMapper, output);
        }
        if (ok1 == 1 && hero.getUsed() == 1) {
            ok2 = 0;
            String error = "Hero has already attacked this turn.";
            out.outputHeroAbility(action, error, objectMapper, output);
        }
        String regex1 = "Lord Royce|Empress Thorina";
        if (ok1 == 1 && ok2 == 1 && hero.getName().matches(regex1)) {
            int row = ROWS - action.getAffectedRow();
            int goodRow = 1;
            if (currentPlayer == 1 && row < 2 || currentPlayer == 2 && row > 1) {
                String error = "Selected row does not belong to the enemy.";
                out.outputHeroAbility(action, error, objectMapper, output);
                goodRow = 0;
            }
            int col = 0;
            if (goodRow == 1 && hero.getName().equals("Lord Royce")) {
                CardInput[] cards = gameTable.returnRow(row);
                for (CardInput card : cards) {
                    if (card != null) {
                        card.setFrozen(frozenCont + 1);
                    }
                    col++;
                }
                hero.setUsed(1);
                players.get(currentPlayer - 1).addMana(-hero.getMana());

            }
            if (goodRow == 1 && hero.getName().equals("Empress Thorina")) {

                CardInput[] cards = gameTable.returnRow(row);
                int i = 0;
                int maxHealth = 0;
                CardInput cardMaxHealth = cards[0];
                for (CardInput card : cards) {
                    if (card != null && cardMaxHealth.getHealth() < card.getHealth()) {

                        cardMaxHealth = card;
                        maxHealth = i;
                    }
                    i++;
                }

                gameTable.killCard(row, maxHealth);
                hero.setUsed(1);
                players.get(currentPlayer - 1).addMana(-hero.getMana());
            }
        }
        String regex = "General Kocioraw|King Mudface";
        if (ok1 == 1 && ok2 == 1 && hero.getName().matches(regex)) {
            int goodRow = 1;
            int row = ROWS - action.getAffectedRow();
            if (currentPlayer == 1 && row > 1 || currentPlayer == 2 && row < 2) {
                String error = "Selected row does not belong to the current player.";
                out.outputHeroAbility(action, error, objectMapper, output);
                goodRow = 0;
            }
            if (goodRow == 1 && hero.getName().equals("General Kocioraw")) {
                hero.setUsed(1);
                hero.setUsed(1);
                CardInput[] cards = gameTable.returnRow(row);
                for (CardInput card : cards) {
                    if (card != null) {
                        card.increaseAttackDamage(1);
                    }
                }
                players.get(currentPlayer - 1).addMana(-hero.getMana());
            }
            if (goodRow == 1 && hero.getName().equals("King Mudface")) {
                hero.setUsed(1);
                hero.setUsed(1);
                CardInput[] cards = gameTable.returnRow(row);
                for (CardInput card : cards) {
                    if (card != null) {
                        card.increaseHealth(1);
                    }
                }
                players.get(currentPlayer - 1).addMana(-hero.getMana());
            }
        }
        return frozenCont + 1;
    }
}
