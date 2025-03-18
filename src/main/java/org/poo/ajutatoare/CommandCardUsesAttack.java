package org.poo.ajutatoare;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.fileio.ActionsInput;
import org.poo.fileio.CardInput;
import org.poo.fileio.Coordinates;

public class CommandCardUsesAttack {
    private final ActionsInput action;
    static final int ROWS = 3;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ArrayNode output;
    private final OutputJson out;
    private final GameTable gameTable;

    /**
     *
     * @param objectMapper
     * @param output
     * @param out
     * @param gameTable
     * @param action
     */
    public CommandCardUsesAttack(final ObjectMapper objectMapper, final ArrayNode output,
                                 final OutputJson out, final GameTable gameTable,
                                 final ActionsInput action) {
        this.output = output;
        this.out = out;
        this.gameTable = gameTable;
        this.action = action;
    }

    /**
     * implements the logic behind a card's attack
     */

    public void cardAttack() {
        Coordinates attacker = new Coordinates();
        attacker.setX(action.getCardAttacker().getX());
        attacker.setY(action.getCardAttacker().getY());

        Coordinates atacat = new Coordinates();
        atacat.setX(action.getCardAttacked().getX());
        atacat.setY(action.getCardAttacked().getY());

        /// making the coordinates right
        attacker.setX(ROWS - attacker.getX());
        atacat.setX(ROWS - atacat.getX());


        int ok1 = 1, ok2 = 1, ok3 = 1, ok4 = 1;

        if (attacker.getX() <= 1 && atacat.getX() <= 1 || atacat.getX() >= 2
                && attacker.getX() >= 2) {
            ok1 = 0;
            String error2 = "Attacked card does not belong to the enemy.";
            out.setOutputJson(action, error2, objectMapper, output, "cardUsesAttack");

        }

        CardInput attackerCard = gameTable.getCard(attacker.getX(), attacker.getY());

        if (attackerCard.getUsed() == 1 && ok1 == 1) {
            ok2 = 0;
            String error3 = "Attacker card has already attacked this turn.";
            out.setOutputJson(action, error3, objectMapper, output, "cardUsesAttack");
        }

        if (attackerCard.getFrozen() > 0 && ok1 == 1 && ok2 == 1) {
            ok3 = 0;
            out.setOutputJson(action, "Attacker card is frozen.", objectMapper,
                    output, "cardUsesAttack");
        }

        if (ok1 == 1 && ok2 == 1 && ok3 == 1) {
            int playerAttacked = 0, playerAttacker = 0;
            if (attacker.getX() < 2) {
                playerAttacker = 1;
                playerAttacked = 2;
            } else {
                playerAttacker = 2;
                playerAttacked = 1;
            }
            if (gameTable.verifyTankRow(playerAttacked) == 1
                    && gameTable.verifyTankCard(atacat.getX(), atacat.getY()) == 0) {
                ok4 = 0;
                String error1 = "Attacked card is not of type 'Tank'.";
                out.setOutputJson(action, error1, objectMapper, output,
                        "cardUsesAttack");
            }
        }

        /// the card attacks
        if (ok1 == 1 && ok2 == 1 && ok3 == 1 && ok4 == 1) {
            attackerCard.setUsed(1);
            CardInput cardAtacat = gameTable.getCard(atacat.getX(), atacat.getY());
            cardAtacat.decreaseHealth(attackerCard.getAttackDamage());
            if (cardAtacat.getHealth() <= 0) {
                gameTable.killCard(atacat.getX(), atacat.getY());
            }
        }
    }
}
