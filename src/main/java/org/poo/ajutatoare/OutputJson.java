package org.poo.ajutatoare;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.ActionsInput;
import org.poo.fileio.CardInput;

import java.util.ArrayList;

public class OutputJson {
    static final int ROWS = 3;
    static final int COLS = 5;
    public OutputJson(final ArrayNode output) {
    }


    /**
     *  la comenzile: CardUsesAttack CardUsesAbility
     * @param action
     * @param error
     * @param objectMapper
     * @param output
     */
    public void setOutputJson(final ActionsInput action, final String error,
                              final ObjectMapper objectMapper, final ArrayNode output,
                              final String command) {
        ObjectNode errorNode = objectMapper.createObjectNode();
        errorNode.put("command", command);

        ObjectNode coordAttacker = objectMapper.createObjectNode();

        coordAttacker.put("x", action.getCardAttacker().getX());
        coordAttacker.put("y", action.getCardAttacker().getY());
        errorNode.set("cardAttacker", coordAttacker);

        ObjectNode coordAcked = objectMapper.createObjectNode();
        coordAcked.put("x", action.getCardAttacked().getX());
        coordAcked.put("y", action.getCardAttacked().getY());
        errorNode.set("cardAttacked", coordAcked);
        errorNode.put("error", error);
        output.add(errorNode);
    }

    /**
     *
     * @param action
     * @param error
     * @param objectMapper
     * @param output
     */

    public void outputHero(final ActionsInput action, final  String error,
                           final ObjectMapper objectMapper, final ArrayNode output) {
        com.fasterxml.jackson.databind.node.ObjectNode errorNode = objectMapper.createObjectNode();
        errorNode.put("command", "useAttackHero");
        /// cardurile

        com.fasterxml.jackson.databind.node.ObjectNode CoorAcker = objectMapper.createObjectNode();

        CoorAcker.put("x", action.getCardAttacker().getX());
        CoorAcker.put("y", action.getCardAttacker().getY());
        errorNode.set("cardAttacker", CoorAcker);
        errorNode.put("error", error);
        output.add(errorNode);
    }

    /**
     *
     * @param action
     * @param error
     * @param objectMapper
     * @param output
     */

    public void outputHeroAbility(final ActionsInput action, final String error,
                                  final ObjectMapper objectMapper, final ArrayNode output) {
        ObjectNode errorNode = objectMapper.createObjectNode();
        errorNode.put("command", "useHeroAbility");
        errorNode.put("affectedRow", action.getAffectedRow());
        errorNode.put("error", error);
        output.add(errorNode);
    }

    /**
     *
     * @param action
     * @param error
     * @param objectMapper
     * @param output
     * @param handIdx
     */

    public void outPlaceCard(final ActionsInput action, final String error,
                             final ObjectMapper objectMapper, final ArrayNode output,
                             final int handIdx) {
        ObjectNode errorNode = objectMapper.createObjectNode();
        errorNode.put("command", "placeCard");
        errorNode.put("handIdx", handIdx);
        errorNode.put("error", error);
        output.add(errorNode);
    }

    /**
     *
     * @param c
     * @param objectMapper
     * @param output
     */

    public void outCardDeck(final CommandGetPlayerDeck c, final ObjectMapper objectMapper,
                            final ArrayNode output) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", "getPlayerDeck");
        objectNode.put("playerIdx", c.getPlayerIdx());
        com.fasterxml.jackson.databind.node.ObjectNode deckNode = objectMapper.createObjectNode();
        ArrayNode cardsArray = objectMapper.createArrayNode();

        for (CardInput card : c.getOutput().getCards()) {
            ObjectNode cardNode = objectMapper.createObjectNode();
            cardNode.put("mana", card.getMana());
            cardNode.put("attackDamage", card.getAttackDamage());
            cardNode.put("health", card.getHealth());
            cardNode.put("description", card.getDescription());

            ArrayNode colorsArray = objectMapper.createArrayNode();
            for (String color : card.getColors()) {  // Assuming you have a method to get the colors
                colorsArray.add(color);
            }
            cardNode.set("colors", colorsArray);
            cardNode.put("name", card.getName());
            cardsArray.add(cardNode);
        }
        //.set("cards", cardsArray);
        objectNode.set("output", cardsArray);
        output.add(objectNode);
    }

    /**
     *
     * @param c
     * @param objectMapper
     * @param output
     */

    public void outHero(final CommandGetPlayerHero c, final ObjectMapper objectMapper,
                        final ArrayNode output) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", "getPlayerHero");
        objectNode.put("playerIdx", c.getPlayerIdx());

        ObjectNode heroNode = objectMapper.createObjectNode();
        CardInput hero = c.getHero();
        heroNode.put("mana", hero.getMana());
        //heroNode.put("attackDamage", hero.getAttackDamage());
        //heroNode.put("health", hero.getHealth());
        heroNode.put("description", hero.getDescription());

        ArrayNode colorsArray = objectMapper.createArrayNode();
        for (String color : hero.getColors()) {
            colorsArray.add(color);
        }
        heroNode.set("colors", colorsArray);
        heroNode.put("name", hero.getName());
        heroNode.put("health", hero.getHealth());
        //System.out.println(hero.getHealth());
        objectNode.set("output", heroNode);
        output.add(objectNode);
    }

    /**
     *
     * @param objectMapper
     * @param output
     * @param jucatorCurent
     */

    public void outPlayerTurn(final ObjectMapper objectMapper, final ArrayNode output,
                              final int jucatorCurent) {
        com.fasterxml.jackson.databind.node.ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", "getPlayerTurn");
        objectNode.put("output", jucatorCurent);
        output.add(objectNode);
    }

    /**
     *
     * @param action
     * @param objectMapper
     * @param output
     * @param players
     */

    public void outGetCardsInHand(final ActionsInput action, final ObjectMapper objectMapper,
                                  final ArrayNode output, final ArrayList<Player> players) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", "getCardsInHand");
        objectNode.put("playerIdx", action.getPlayerIdx());
        ObjectNode cardsNode = objectMapper.createObjectNode();
        ArrayNode cardsArray = objectMapper.createArrayNode();
        for (CardInput card : players.get(action.getPlayerIdx() - 1).getCards()) {
            ObjectNode cardNode = objectMapper.createObjectNode();
            cardNode.put("mana", card.getMana());
            cardNode.put("attackDamage", card.getAttackDamage());
            cardNode.put("health", card.getHealth());
            cardNode.put("description", card.getDescription());
            ArrayNode colorsArray = objectMapper.createArrayNode();
            for (String color : card.getColors()) {  // Assuming you have a method to get the colors
                colorsArray.add(color);
            }
            cardNode.set("colors", colorsArray);
            cardNode.put("name", card.getName());
            cardsArray.add(cardNode);
        }
        objectNode.set("output", cardsArray);
        output.add(objectNode);
    }

    /**
     *
     * @param action
     * @param objectMapper
     * @param output
     * @param players
     */

    public void outPlayerMana(final ActionsInput action, final ObjectMapper objectMapper,
                              final ArrayNode output, final ArrayList<Player> players) {
        com.fasterxml.jackson.databind.node.ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", "getPlayerMana");
        objectNode.put("playerIdx", action.getPlayerIdx());
        objectNode.put("output", players.get(action.getPlayerIdx() - 1).getMana());
        output.add(objectNode);
    }

    /**
     *
     * @param objectMapper
     * @param output
     * @param gameTable
     */
    public void outCardsOnTable(final ObjectMapper objectMapper, final ArrayNode output,
                                final GameTable gameTable) {
        com.fasterxml.jackson.databind.node.ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", "getCardsOnTable");

        // Array to hold rows of cards
        ArrayNode rowsArray = objectMapper.createArrayNode();

        // Loop through rows and columns on the board
        for (int i = ROWS; i >= 0; i--) {
            ArrayNode rowArray = objectMapper.createArrayNode();  // Each row's cards

            for (int j = 0; j < COLS; j++) {
                CardInput card = gameTable.getCard(i, j);
                if (card != null) {
                    ObjectNode cardNode = objectMapper.createObjectNode();

                    // Add card details
                    cardNode.put("mana", card.getMana());
                    cardNode.put("attackDamage", card.getAttackDamage());
                    cardNode.put("health", card.getHealth());
                    cardNode.put("description", card.getDescription());
                    cardNode.put("name", card.getName());

                    // Add colors array
                    ArrayNode colorsArray = objectMapper.createArrayNode();
                    for (String color : card.getColors()) {
                        colorsArray.add(color);
                    }
                    cardNode.set("colors", colorsArray);

                    // Add card to the current row array
                    rowArray.add(cardNode);
                }
            }

            // Add the row of cards to the rows array
            rowsArray.add(rowArray);
        }

        // Attach rows array to the output JSON under "table"
        objectNode.set("output", rowsArray);

        // Add objectNode to the output
        output.add(objectNode);
    }

    /**
     *
     * @param action
     * @param objectMapper
     * @param output
     * @param players
     * @param gameTable
     */

    public void outCardAtPosition(final ActionsInput action, final ObjectMapper objectMapper,
                                  final ArrayNode output, final ArrayList<Player> players,
                                  final GameTable gameTable) {
        com.fasterxml.jackson.databind.node.ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", "getCardAtPosition");
        objectNode.put("x", action.getX());
        objectNode.put("y", action.getY());

        int x = action.getX();
        int y = action.getY();

        // Crearea unui obiect JSON pentru coordonate cu cheile "x" și "y"
        ObjectNode coordinates = objectMapper.createObjectNode();
        coordinates.put("x", x); // Adăugăm coordonata x
        coordinates.put("y", y); // Adăugăm coordonata y

        // Setăm coordonatele în obiectul răspuns
        ///objectNode.set("cardAttacker", coordinates);

        x = ROWS - x;

        // Verificăm dacă există o carte în acea poziție
        CardInput cardAtPosition = gameTable.getCard(x, y);

        if (cardAtPosition != null) {
            // Dacă există o carte, adăugăm detalii despre aceasta
            ObjectNode cardNode = objectMapper.createObjectNode();
            cardNode.put("mana", cardAtPosition.getMana());
            cardNode.put("attackDamage", cardAtPosition.getAttackDamage());
            cardNode.put("health", cardAtPosition.getHealth());
            cardNode.put("description", cardAtPosition.getDescription());

            ArrayNode colorsArray = objectMapper.createArrayNode();
            for (String color : cardAtPosition.getColors()) {
                colorsArray.add(color);
            }
            cardNode.set("colors", colorsArray);
            cardNode.put("name", cardAtPosition.getName());
            objectNode.set("output", cardNode);
            //}
        } else {
            // Dacă nu există carte, adăugăm un mesaj de eroare
            objectNode.put("output", "No card available at that position.");
        }

        // Adăugăm răspunsul la output
        output.add(objectNode);
    }

    /**
     *
     * @param objectMapper
     * @param gameTable
     * @param output
     */
    public void outFrozenCards(final ObjectMapper objectMapper, final GameTable gameTable,
                               final ArrayNode output, final int frozenCont) {
        com.fasterxml.jackson.databind.node.ObjectNode objectNode =
                objectMapper.createObjectNode();
        objectNode.put("command", "getFrozenCardsOnTable");
        ArrayNode rowsArray = objectMapper.createArrayNode();
        for (int i = ROWS; i >= 0; i--) {
            for (int j = 0; j < COLS; j++) {
                CardInput card = gameTable.getCard(i, j);
                if (card != null) {
                    if (card.getFrozen() == frozenCont && frozenCont > 0) {
                        com.fasterxml.jackson.databind.node.ObjectNode cardNode =
                                objectMapper.createObjectNode();

                        cardNode.put("mana", card.getMana());
                        cardNode.put("attackDamage", card.getAttackDamage());
                        cardNode.put("health", card.getHealth());
                        cardNode.put("description", card.getDescription());
                        cardNode.put("name", card.getName());

                        ArrayNode colorsArray = objectMapper.createArrayNode();
                        for (String color : card.getColors()) {
                            colorsArray.add(color);
                        }
                        cardNode.set("colors", colorsArray);
                        rowsArray.add(cardNode);
                    }
                }
            }
        }
        objectNode.set("output", rowsArray);
        output.add(objectNode);
    }

}


