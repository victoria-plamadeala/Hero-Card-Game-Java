package org.poo.ajutatoare;


import org.poo.fileio.CardInput;

import java.util.ArrayList;

public class CommandEndPlayerTurn {
    static final int ROWS = 3;
    static final int COLS = 5;
    private GameTable gameTable;
    private int jucatorCurent;
    private int tura;
    static final int MAX_ROUND = 10;
    static final int MANA = 10;
    private ArrayList<Player> players;

    /**
     * @param gameTable
     * @param jucatorCurent
     * @param tura
     * @param players
     */

    public CommandEndPlayerTurn(final GameTable gameTable, final int jucatorCurent,
                                final int tura, final ArrayList<Player> players) {
        this.gameTable = gameTable;
        this.jucatorCurent = jucatorCurent;
        this.tura = tura;
        this.players = players;
    }

    /**
     * implements the logic when one player is done
     */

    public void endPlayer() {
        for (int i = 0; i <= ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                CardInput card = gameTable.getCard(i, j);
                if (jucatorCurent == 1 && i < 2 && card != null) {
                    card.setFrozen(0);
                } else if (jucatorCurent == 2 && i > 1 && card != null) {
                    card.setFrozen(0);
                }
            }
        }


        jucatorCurent = (jucatorCurent == 1) ? 2 : 1;
        tura++;
        if (tura % 2 == 0) {
            int round = tura / 2;
            if (round <= MAX_ROUND) {
                players.get(0).addMana(round);
                players.get(1).addMana(round);
            } else {
                players.get(0).addMana(MANA);
                players.get(1).addMana(MANA);
            }

            int j = 1;
            for (Player currentPlayer : players) {
                if (!currentPlayer.getDeck().getCards().isEmpty()) {
                    CardInput newCard = currentPlayer.getDeck().getCards().get(0);
                    newCard.setHandIdx(currentPlayer.getCards().size());
                    newCard.setUsed(0);
                    currentPlayer.getCards().add(newCard);
                    currentPlayer.getDeck().getCards().remove(0);
                }
                j++;
            }

            /// resetting the cards on the game table
            for (int i = 0; i <= ROWS; i++) {
                for (int k = 0; k < COLS; k++) {
                    CardInput card = gameTable.getCard(i, k);
                    if (card != null) {
                        card.setUsed(0);
                    }
                }
            }


            for (Player player : players) {
                player.gethero().setUsed(0);
            }
        }
    }
}
