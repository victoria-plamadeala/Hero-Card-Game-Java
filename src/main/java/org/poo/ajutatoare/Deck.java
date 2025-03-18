package org.poo.ajutatoare;

import org.poo.fileio.CardInput;

import java.util.ArrayList;
import java.util.Random;

import static java.util.Collections.shuffle;

public class Deck {
    private int nrCards;
    private ArrayList<CardInput> cards;

    public Deck(final int nrCards, final ArrayList<CardInput> cards) {
        this.nrCards = nrCards;
        this.cards = cards;
    }


    public Deck deepCopy() {
        // Copiem nrCards (acesta este doar un int, deci nu e nevoie de deep copy)
        // Copiem lista de carduri făcând o copie adâncă a fiecărui CardInput
        ArrayList<CardInput> copiedCards = new ArrayList<>();
        for (CardInput card : this.cards) {
            copiedCards.add(new CardInput(card));  // Folosim constructorul de copiere al lui CardInput
        }

        // Returnăm un nou obiect Deck cu lista de cărți copiată
        return new Deck(this.nrCards, copiedCards);
    }

    /**
     * setter
     */
    public void simpleDeck(final Deck deck) {
        this.nrCards = deck.nrCards;
        this.cards = deck.cards;
    }

    /**
     *
     * @return cards
     */

    public ArrayList<CardInput> getCards() {
        return cards;
    }

    /**
     *
     * @return number of cards
     */
    public int getNrCards() {
        return nrCards;
    }

    /**
     * prints cards (used for debug locally)
     */
    public void printCard() {
        for (CardInput card : cards) {
            System.out.println(card);
        }
    }

    /**
     *
     * @param shuffelSeed = to shuffle the deck
     */
    public void shuffleDeck(final int shuffelSeed) {
        Random rand = new Random(shuffelSeed);
        shuffle(cards, rand);
    }

    /**
     *
     * @return the playable cards (in hand) at the start of the game
     */

    public ArrayList<CardInput> getPlayableCards() {
        ArrayList<CardInput> playableCards = new ArrayList<>();
        if (!cards.isEmpty()) {
            playableCards.add(cards.get(0));
            playableCards.get(0).setHandIdx(0);
            cards.remove(0);
            playableCards.get(0).setUsed(0);
        }
        return playableCards;
    }

}
