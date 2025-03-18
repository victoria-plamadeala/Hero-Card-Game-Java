

# Tema POO  - GwentStone
# made by Plamadeala Victoria 325CAb

## Skel Structure

* src/
  * fileio/ - contains classes used to read data from the json files
  * main/
      * Main - the Main class runs the checker on your implementation. Add the entry point to your implementation in it. Run Main to test your implementation from the IDE or from command line.
      * Test - run the main method from Test class with the name of the input file from the command line and the result will be written
        to the out.txt file. Thus, you can compare this result with ref.
  * helpers/ - contains some helper classes 

  Most important classes are:

## Statistics

An instance of 'Statistics' will keep important details of the games, such as how many games each players has won, or how many games were in total.

## Player

In order to keep the each player's information there will be used instances of 'Player', in which are kept the decks, the cards in hand, the hero and so on.

## Deck

A class that keeps only the number of cards in the deck and the cards.

## GameTable

This class has the task of keeping the game table up to date, having methods such as verifying if a specified row is full, of to return a card on a given position, and so on.

## OutputJson

Whenever something has to be printed, this class is used, having methods for each special type of output.

## Command[...]

These classes were made for a cleaner code and visibility, special for a certain command.

