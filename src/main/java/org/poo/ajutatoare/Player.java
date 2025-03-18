package org.poo.ajutatoare;

import org.poo.fileio.CardInput;

import java.util.ArrayList;

public final class Player {
    private int index;
    private Deck deck;
    private ArrayList<CardInput> cards;
    private CardInput hero;
    private int mana;

    public Player(final int index, final Deck deck, final ArrayList<CardInput> cards, final CardInput hero) {
        this.deck = deck;
        this.index = index;
        this.cards = cards;
        this.hero = hero;
        this.mana = 1;
    }

    public int getIndex() {
        return index;
    }
    public Deck getDeck() {
        return deck;
    }
    public ArrayList<CardInput> getCards() {
        return cards;
    }

    /**
     *
     * @return the player's hero
     */
    public CardInput gethero() {
        return hero;
    }
    public int getMana() {
        return mana;
    }

    /**
     *
     * @param newMana = new mana added to player's mana
     */

    public void addMana(final int newMana) {
        this.mana += newMana;
    }

}
