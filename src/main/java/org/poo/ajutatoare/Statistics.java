package org.poo.ajutatoare;

public final class Statistics {
    private int totalGamesPlayed = 0;
    private int gameIsOver = 0;
    private int winsPlayer2 = 0;
    private int winsPlayer1 = 0;

    public Statistics() {}

    public int getGameIsOver() {
        return gameIsOver;
    }

    public void setGameIsOver(final int gameIsOver) {
        this.gameIsOver = gameIsOver;
    }

    public int getWinsPlayer2() {
        return winsPlayer2;
    }

    public void setWinsPlayer2(final int winsPlayer2) {
        this.winsPlayer2 = winsPlayer2;
    }
    public int getWinsPlayer1() {
        return winsPlayer1;
    }
    public void setWinsPlayer1(final int winsPlayer1) {
        this.winsPlayer1 = winsPlayer1;
    }
    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }
    public void setTotalGamesPlayed(final int totalGamesPlayed) {
        this.totalGamesPlayed = totalGamesPlayed;
    }
}
