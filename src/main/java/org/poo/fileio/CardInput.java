package org.poo.fileio;

import java.util.ArrayList;

public final class CardInput {
    private int mana;
    private int attackDamage;
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private int handIdx = -1;
    private int used = 0;
    private int atacker = 0;
    private int frozen = 0;

    public CardInput() { };

    public int getMana() {
        return mana;
    }

    public void setMana(final int mana) {
        this.mana = mana;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(final int health) {
        this.health = health;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }


    public int getHandIdx() {
        return handIdx;
    }
    public void setHandIdx(final int handIdx) {
        this.handIdx = handIdx;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(final int used) {
        this.used = used;
    }

    public int getAtacker() {
        return atacker;
    }

    public void setAtacker(final int atacker) {
        this.atacker = atacker;
    }

    public int getFrozen() {
        return frozen;
    }

    /**
     *
     * @param frozenn = 1 / 0 if card is frozen / or not
     */
    public void setFrozen(int frozenn) {
        this.frozen = frozenn;
    }

    /**
     *
     * @param healthDamage = decrease
     */
    public void decreaseHealth(final int healthDamage) {
        this.health -= healthDamage;
    }

    /**
     *
     * @param healthDamage = increase
     */
    public void increaseHealth(final int healthDamage) {
        this.health += healthDamage;
    }

    /**
     *
     * @param newAttackDamage = decrease
     */
    public void decreaseAttackDamage(final int newAttackDamage) {
        this.attackDamage -= newAttackDamage;
    }

    /**
     *
     * @param newAttackDamage = increase
     */
    public void increaseAttackDamage(final int newAttackDamage) {
        this.attackDamage += newAttackDamage;
    }


    public CardInput(CardInput other) {
        this.mana = other.mana;
        this.attackDamage = other.attackDamage;
        this.health = other.health;
        this.description = other.description;
        this.colors = new ArrayList<>(other.colors);  // Copiem lista de culori
        this.name = other.name;
        this.handIdx = other.handIdx;
        this.used = other.used;
        this.atacker = other.atacker;
        this.frozen = other.frozen;
    }


    @Override
    public String toString() {
        return "CardInput{"
                + "mana="
                + mana
                + ", attackDamage="
                + attackDamage
                + ", health="
                + health
                + ", description='"
                + description
                + '\''
                + ", colors="
                + colors
                + ", name='"
                + ""
                + name
                + '\''
                + '}';
    }
}
