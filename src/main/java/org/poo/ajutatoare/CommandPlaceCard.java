package org.poo.ajutatoare;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;
import org.poo.fileio.ActionsInput;
import org.poo.fileio.CardInput;

public class CommandPlaceCard {
    private final ActionsInput action;
    private final OutputJson out;
    private final ArrayNode output;
    private final ObjectMapper objectMapper;
    private final ArrayList<Player> players;
    private final int currentPlayer;
    static final int ROWS = 3;
    private final GameTable gameTable;

    public CommandPlaceCard(final ActionsInput action, final GameTable gameTable,
                            final OutputJson out, final ArrayNode output,
                            final ObjectMapper objectMapper,
                            final int currentPlayer,
                            final ArrayList<Player> players) {
        this.action = action;
        this.gameTable = gameTable;
        this.out = out;
        this.output = output;
        this.objectMapper = objectMapper;
        this.currentPlayer = currentPlayer;
        this.players = players;
    }

    /**
     * implements the logic when a card is placed on the table
     */

    public void placeCard() {
        int handIdx = action.getHandIdx();
        Player currentPlayer = players.get(this.currentPlayer - 1);

        CardInput card = currentPlayer.getCards().get(handIdx);

        if (card.getMana() > currentPlayer.getMana()) {
            out.outPlaceCard(action, "Not enough mana to place card on table.",
                    objectMapper, output, handIdx);
        } else {
            String regex = "Goliath|Warden|The Ripper|Miraj";
            int row = (card.getName().matches(regex)) ? 1 : 0;
            if (this.currentPlayer == 2) {
                if (row == 0) {
                    row = ROWS;
                } else {
                    row = 2;
                }
            }

            int pos = gameTable.verifyRow(row);
            if (pos == -1) {
                String error = "Cannot place card on table since row is full.";
                out.outPlaceCard(action, error, objectMapper, output, handIdx);
            } else {
                gameTable.putCard(card, row, pos);
                card.setUsed(0);
                card.setFrozen(0);
                currentPlayer.getCards().remove(card);
                int manaCard = card.getMana();
                manaCard = manaCard * (-1);
                currentPlayer.addMana(manaCard);
            }
        }
    }

}

