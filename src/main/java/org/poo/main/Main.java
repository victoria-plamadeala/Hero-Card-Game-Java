package org.poo.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.ajutatoare.*;
import org.poo.checker.Checker;
import org.poo.checker.CheckerConstants;
import org.poo.fileio.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implementation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(CheckerConstants.TESTS_PATH + filePath1),
                Input.class);

        ArrayNode output = objectMapper.createArrayNode();

        OutputJson out = new OutputJson(output);

        final int maxHealthHero = 30;

        Statistics statistics = new Statistics();

        ArrayList<GameInput> games = inputData.getGames();

        for (GameInput game : games) {

            statistics.setTotalGamesPlayed(statistics.getTotalGamesPlayed() + 1);
            statistics.setGameIsOver(0);
            StartGameInput startGame = game.getStartGame();
            int shuffleSeed = startGame.getShuffleSeed();

            /// deck for every player
            Deck deckPlayer1 = new Deck(inputData.getPlayerOneDecks().getNrCardsInDeck(),
                    inputData.getPlayerOneDecks().getDecks().get(startGame.getPlayerOneDeckIdx()));
            Deck deckPlayer2 = new Deck(inputData.getPlayerTwoDecks().getNrCardsInDeck(),
                    inputData.getPlayerTwoDecks().getDecks().get(startGame.getPlayerTwoDeckIdx()));

            Deck chosenDeckPlayer1 = deckPlayer1.deepCopy();
            Deck chosenDeckPlayer2 = deckPlayer2.deepCopy();

            /// shuffle the deck
            chosenDeckPlayer1.shuffleDeck(shuffleSeed);
            chosenDeckPlayer2.shuffleDeck(shuffleSeed);

            ArrayList<Deck> bothDecks = new ArrayList<>();
            bothDecks.add(chosenDeckPlayer1);
            bothDecks.add(chosenDeckPlayer2);

            /// game table
            GameTable gameTable = new GameTable();

            /// setting the heroes
            CardInput heroPlayer1 = startGame.getPlayerOneHero();
            heroPlayer1.setHealth(maxHealthHero);
            CardInput heroPlayer2 = startGame.getPlayerTwoHero();
            heroPlayer2.setHealth(maxHealthHero);

            int frozenCont = 0;
            int currentPlayer = startGame.getStartingPlayer();

            /// players get their first card in hand
            ArrayList<CardInput> playableCardsPlayer1 = chosenDeckPlayer1.getPlayableCards();
            ArrayList<CardInput> playableCardsPlayer2 = chosenDeckPlayer2.getPlayableCards();

            /// player class and array
            Player player1 = new Player(1, chosenDeckPlayer1, playableCardsPlayer1, heroPlayer1);
            Player player2 = new Player(2, chosenDeckPlayer2, playableCardsPlayer2, heroPlayer2);

            ArrayList<Player> players = new ArrayList<>();
            players.add(player1);
            players.add(player2);

            int gameIsOver = 0;
            int tura = 2;

            /// actions
            ArrayList<ActionsInput> actions = game.getActions();

            for (ActionsInput action : actions) {
                /// debug commands
                if (action.getCommand().equals("getPlayerDeck")) {
                    int random = action.getPlayerIdx();
                    CommandGetPlayerDeck c = new CommandGetPlayerDeck(random,
                            bothDecks.get(random - 1));
                    out.outCardDeck(c, objectMapper, output);
                }
                if (action.getCommand().equals("getPlayerHero")) {
                    int random = action.getPlayerIdx();
                    CommandGetPlayerHero c = new CommandGetPlayerHero(random,
                            players.get(random - 1).gethero());
                    out.outHero(c, objectMapper, output);
                }
                if (action.getCommand().equals("getPlayerTurn")) {
                    out.outPlayerTurn(objectMapper, output, currentPlayer);
                }
                if (action.getCommand().equals("getCardsInHand")) {
                    out.outGetCardsInHand(action, objectMapper, output, players);
                }
                if (action.getCommand().equals("getPlayerMana")) {
                    out.outPlayerMana(action, objectMapper, output, players);
                }
                if (action.getCommand().equals("getCardsOnTable")) {
                    out.outCardsOnTable(objectMapper, output, gameTable);
                }
                if (action.getCommand().equals("getCardAtPosition")) {
                    out.outCardAtPosition(action, objectMapper, output, players, gameTable);
                }
                if (action.getCommand().equals("getFrozenCardsOnTable")) {
                    out.outFrozenCards(objectMapper, gameTable, output, frozenCont);
                }
                if (action.getCommand().equals("getTotalGamesPlayed")) {
                    com.fasterxml.jackson.databind.node.ObjectNode objectNode =
                            objectMapper.createObjectNode();
                    objectNode.put("command", "getTotalGamesPlayed");
                    objectNode.put("output", statistics.getTotalGamesPlayed());
                    output.add(objectNode);
                }
                if (action.getCommand().equals("getPlayerOneWins")) {
                    com.fasterxml.jackson.databind.node.ObjectNode objectNode =
                            objectMapper.createObjectNode();
                    objectNode.put("command", "getPlayerOneWins");
                    objectNode.put("output", statistics.getWinsPlayer1());
                    output.add(objectNode);
                }
                if (action.getCommand().equals("getPlayerTwoWins")) {
                    com.fasterxml.jackson.databind.node.ObjectNode objectNode =
                            objectMapper.createObjectNode();
                    objectNode.put("command", "getPlayerTwoWins");
                    objectNode.put("output", statistics.getWinsPlayer2());
                    output.add(objectNode);
                }

                /// game commands
                if (action.getCommand().equals("endPlayerTurn")
                        && statistics.getGameIsOver() == 0) {
                    CommandEndPlayerTurn c = new CommandEndPlayerTurn(gameTable,
                            currentPlayer, tura, players);
                    c.endPlayer();
                    currentPlayer = (currentPlayer == 1) ? 2 : 1;
                    tura++;
                }
                if (action.getCommand().equals("placeCard")
                        && statistics.getGameIsOver() == 0) {
                    CommandPlaceCard c = new CommandPlaceCard(action, gameTable,
                            out, output, objectMapper, currentPlayer, players);
                    c.placeCard();
                }
                if (action.getCommand().equals("cardUsesAttack")
                        && statistics.getGameIsOver() == 0) {
                    CommandCardUsesAttack c = new CommandCardUsesAttack(objectMapper,
                            output, out, gameTable, action);
                    c.cardAttack();
                }
                if (action.getCommand().equals("cardUsesAbility")
                        && statistics.getGameIsOver() == 0) {
                    CommandCardUsesAbility c = new CommandCardUsesAbility(action, gameTable,
                            out, objectMapper, output);
                    c.cardUsesAbility();
                }
                if (action.getCommand().equals("useAttackHero")
                        && statistics.getGameIsOver() == 0) {
                    CommandUseAttackHero c = new CommandUseAttackHero(statistics, gameTable,
                            objectMapper, action, out, players, output);
                    c.cardAttackHero();
                }
                if (action.getCommand().equals("useHeroAbility")
                        && statistics.getGameIsOver() == 0) {
                    CommandUseHeroAbility c = new CommandUseHeroAbility(players, currentPlayer,
                            output, out, action, objectMapper, gameTable);
                    frozenCont = c.heroAbility(frozenCont);

                }
            }
        }

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), output);
    }
}
