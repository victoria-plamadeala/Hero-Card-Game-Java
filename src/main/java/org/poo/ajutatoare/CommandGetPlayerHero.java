package org.poo.ajutatoare;

import lombok.Getter;
import lombok.Setter;
import org.poo.fileio.CardInput;

public class CommandGetPlayerHero {
    @Getter
    @Setter
    private String command = "getPlayerHero";
    private final int playerIdx;
    private final CardInput hero;

    public CommandGetPlayerHero(final int playerIdx, final CardInput hero) {
        this.command = "getPlayerHero";
        this.playerIdx = playerIdx;
        this.hero = hero;
    }

    /**
     *
     * @return player index (1 or 2) - (0 or 1)
     */
    public int getPlayerIdx() {
        return playerIdx;
    }

    /**
     *
     * @return the player's hero
     */
    public CardInput getHero() {
        return hero;
    }

}

