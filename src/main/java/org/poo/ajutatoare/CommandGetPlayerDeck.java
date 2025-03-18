package org.poo.ajutatoare;

public class CommandGetPlayerDeck {
    private String Command = "getPlayerDeck";
    private final int PlayerIdx;
    private final Deck Output;

    public CommandGetPlayerDeck(final int playerIdx, final Deck output) {
        this.Command = "getPlayerDeck";
        this.PlayerIdx = playerIdx;
        this.Output = output;
    }

    /**
     *
     * @return command
     */

    public String getCommand() {
        return Command;
    }

    /**
     *
     * @return player idx
     */
    public int getPlayerIdx() {
        return PlayerIdx;
    }

    /**
     *
     * @return output
     */
    public Deck getOutput() {
        return Output;
    }

}

