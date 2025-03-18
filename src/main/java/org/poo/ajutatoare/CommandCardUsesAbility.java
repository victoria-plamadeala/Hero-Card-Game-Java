package org.poo.ajutatoare;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.fileio.ActionsInput;
import org.poo.fileio.CardInput;

public class CommandCardUsesAbility {
    private final int rows = 3;
    private ActionsInput action;
    private GameTable gameTable;
    private OutputJson out;
    private ArrayNode output;
    private ObjectMapper objectMapper;
    private String error1 = "Attacked card is not of type 'Tank'.";
    private String error2 = "Attacked card does not belong to the enemy.";
    private String error3 = "Attacker card has already attacked this turn.";

    public CommandCardUsesAbility(final ActionsInput action, final GameTable gameTable,
                                  final OutputJson out, final ObjectMapper objectMapper,
                                  final ArrayNode output) {
        this.action = action;
        this.gameTable = gameTable;
        this.out = out;
        this.output = output;
        this.objectMapper = objectMapper;
    }

    /**
     * implements the logic when a card uses its ability
     */
    public void cardUsesAbility() {

        int ok1 = 1, ok2 = 1;

        int auxAtacator = rows - action.getCardAttacker().getX();
        int auxAtacat = rows - action.getCardAttacked().getX();

        CardInput cardAtacator = gameTable.getCard(auxAtacator,
                action.getCardAttacker().getY());

        if (cardAtacator.getFrozen() > 0) {
            ok1 = 0;
            out.setOutputJson(action, "Attacker card is frozen.",
                    objectMapper, output, "cardUsesAbility");
        }
        if (ok1 == 1 && cardAtacator.getUsed() == 1) {
            ok2 = 0;
            out.setOutputJson(action, error3, objectMapper, output, "cardUsesAbility");
        }
        if (ok1 == 1 && ok2 == 1 && cardAtacator.getName().

                equals("Disciple")) {
            if (auxAtacator < 2 && auxAtacat < 2 || auxAtacator > 1 && auxAtacat > 1) {
                CardInput aliat = gameTable.getCard(auxAtacat,
                        action.getCardAttacked().getY());
                aliat.increaseHealth(2);
                cardAtacator.setUsed(1);
            } else {
                String error = "Attacked card does not belong to the current player.";
                out.setOutputJson(action, error, objectMapper,
                        output, "cardUsesAbility");
            }
        }

        String regex = "The Ripper|Miraj|The Cursed One";
        if (ok1 == 1 && ok2 == 1 && cardAtacator.getName().

                matches(regex)) {
            if (auxAtacator < 2 && auxAtacat < 2 || auxAtacator > 1 && auxAtacat > 1) {

                out.setOutputJson(action, error2, objectMapper,
                        output, "cardUsesAbility");

            } else {
                int playerAtacat;
                if (auxAtacat < 2) {
                    playerAtacat = 1;
                } else {
                    playerAtacat = 2;
                }

                CardInput cardAtacat = gameTable.getCard(auxAtacat,
                        action.getCardAttacked().getY());


                if (gameTable.verifyTankRow(playerAtacat) == 1
                        && gameTable.verifyTankCard(auxAtacat,
                        action.getCardAttacked().getY()) == 0) {
                    out.setOutputJson(action, error1, objectMapper, output,
                            "cardUsesAbility");
                } else {
                    cardAtacator.setUsed(1);
                    if (cardAtacator.getName().equals("The Ripper")) {
                        cardAtacat.decreaseAttackDamage(2);
                        if (cardAtacat.getAttackDamage() <= 0) {
                            cardAtacat.setAttackDamage(0);
                        }
                    }
                    if (cardAtacator.getName().equals("Miraj")) {
                        int healthAux = cardAtacator.getHealth();
                        cardAtacator.setHealth(cardAtacat.getHealth());
                        cardAtacat.setHealth(healthAux);
                    }
                    if (cardAtacator.getName().equals("The Cursed One")) {
                        if (cardAtacat.getAttackDamage() == 0) {
                            gameTable.killCard(auxAtacat,
                                    action.getCardAttacked().getY());
                        } else {
                            int atacDamage = cardAtacat.getAttackDamage();
                            cardAtacat.setAttackDamage(cardAtacat.getHealth());
                            cardAtacat.setHealth(atacDamage);
                        }
                    }
                }


            }
        }
    }
}

