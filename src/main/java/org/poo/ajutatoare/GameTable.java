package org.poo.ajutatoare;


import org.poo.fileio.CardInput;

public class GameTable {
    static final int ROWS = 4;
    static final int COLS = 5;
    static final int LAST_COLUMN = 4;
    private CardInput[][] matrice = new CardInput[ROWS][COLS];

    /**
     *
     * @param card = the card that has to be placed
     * @param x =row
     * @param y = col
     */

    public void putCard(final CardInput card, final int x, final int y) {
        matrice[x][y] = card;
    }

    /**
     *
     * @param row = the row that needs to be checked
     * @return 1 / 0 if the row is full / or not
     */

    public int verifyRow(final int row) {
        for (int i = 0; i < COLS; i++) {
            if (matrice[row][i] == null) {
                return i;
            }
        }
        return -1;
    }

    /**
     *
     * @param row = the row
     * @param col = the column
     * @return the card on [row][col]
     */

    public CardInput getCard(final int row, final int col) {
        return matrice[row][col];
    }

    /**
     * @param player = player index
     * @return 1/0 if the given player still has cards of type tank
     */
    public int verifyTankRow(final int player) {
        int row;
        if (player == 1) {
            row = 1;
        } else {
            row = 2;
        }
        for (int i = 0; i < ROWS; i++) {
            if (matrice[row][i] != null && matrice[row][i].getName().matches("Goliath|Warden")) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * @param x - x index (row index)
     * @param y - y index (column index)
     * @return 1/0 if the card on [x][y] is of type tank or not
     */

    public int verifyTankCard(final int x, final int y) {
        if (matrice[x][y] != null && matrice[x][y].getName().matches("Goliath|Warden")) {
            return 1;
        }
        return 0;
    }

    /**
     * the function removes the card on position [row][column]
     *
     * @param row = row index
     * @param col = column index
     */

    public void killCard(final int row, final int col) {
        //int LAST_COLUMN = 4;
        matrice[row][col] = null;
        for (int i = col; i < LAST_COLUMN; i++) {
            matrice[row][i] = matrice[row][i + 1];
        }
        matrice[row][LAST_COLUMN] = null;
    }

    /**
     * @param row = the row index
     * @return the whole row
     */
    public CardInput[] returnRow(final int row) {
        CardInput[] cards = new CardInput[COLS];
        for (int i = 0; i < COLS; i++) {
            cards[i] = matrice[row][i];
        }
        return cards;
    }
}
