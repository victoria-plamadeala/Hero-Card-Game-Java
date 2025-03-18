package org.poo.ajutatoare;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.fileio.CardInput;
import org.poo.fileio.ActionsInput;
import java.util.ArrayList;

public class CommandUseAttackHero {
    private ActionsInput action;
    static final int ROWS = 3;
    private GameTable gameTable;
    private ObjectMapper objectMapper;
    private ArrayNode output;
    private OutputJson out;
    static final String ERROR = "Attacked card is not of type 'Tank'.";
    static final String ERROR2 = "Attacker card has already attacked this turn.";
    private ArrayList<Player> players;
    private Statistics statistics;

    public CommandUseAttackHero(final Statistics statistics, final GameTable gameTable,
                                final ObjectMapper objectMapper, final ActionsInput action,
                                final OutputJson out, final ArrayList<Player> players,
                                final ArrayNode output) {
        this.gameTable = gameTable;
        this.objectMapper = objectMapper;
        this.action = action;
        this.out = out;
        this.players = players;
        this.output = output;
        this.statistics = statistics;
    }

    /**
     * implements the logic when a hero card is attacked
     */

    public void cardAttackHero() {
        CardInput cardAttacker;
        int xAttacker = action.getCardAttacker().getX();
        xAttacker = ROWS - xAttacker;

        cardAttacker = gameTable.getCard(xAttacker, action.getCardAttacker().getY());

        int ok1 = 1, ok2 = 1, ok3 = 1;

        if (cardAttacker.getFrozen() > 0) {
            ok1 = 0;
            out.outputHero(action, "Attacker card is frozen.", objectMapper, output);
        }
        if (ok1 == 1 && cardAttacker.getUsed() == 1) {
            ok2 = 0;
            out.outputHero(action, ERROR2, objectMapper, output);
        }
        int playerAttacker = 0, playerAttacked = 0;
        if (ok1 == 1 && ok2 == 1) {
            if (xAttacker < 2) {
                playerAttacker = 1;
                playerAttacked = 2;
            } else {
                playerAttacker = 2;
                playerAttacked = 1;
            }
        }
        if (gameTable.verifyTankRow(playerAttacked) == 1 && ok1 == 1 && ok2 == 1) {
            ok3 = 0;
            out.outputHero(action, ERROR, objectMapper, output);

        }

        if (ok1 == 1 && ok2 == 1 && ok3 == 1) {
            cardAttacker.setUsed(1);
            CardInput hero = players.get(playerAttacked - 1).gethero();
            hero.decreaseHealth(cardAttacker.getAttackDamage());

            if (hero.getHealth() <= 0) {
                com.fasterxml.jackson.databind.node.ObjectNode errorNode =
                        objectMapper.createObjectNode();
                if (playerAttacker == 2) {
                    errorNode.put("gameEnded", "Player two killed the enemy hero.");
                    statistics.setWinsPlayer2(statistics.getWinsPlayer2() + 1);
                } else {
                    errorNode.put("gameEnded", "Player one killed the enemy hero.");
                    statistics.setWinsPlayer1(statistics.getWinsPlayer1() + 1);
                }
                output.add(errorNode);
                statistics.setGameIsOver(1);
            }
        }
    }
}

