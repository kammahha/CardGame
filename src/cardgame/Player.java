package cardgame;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Player implements Runnable {

    int id;
    ArrayList<Card> hand = new ArrayList<>();
    ArrayList<String> output = new ArrayList<>();
    int playerRounds = 0;
//    boolean dirCreated = false;


    /**
     * Constructor for creating players
     * @param id Player has its own id
     */
    Player(int id) {
        this.id = id;
    }


    /**
     * Sets initial hand and adds initial hand to output array
     * @param c1 1st card distributed to the player
     * @param c2 2nd card distributed to the player
     * @param c3 3rd card distributed to the player
     * @param c4 4th card distributed to the player
     */

    public void setInitialHand(Card c1, Card c2, Card c3, Card c4) {
        this.hand.add(c1);
        this.hand.add(c2);
        this.hand.add(c3);
        this.hand.add(c4);
        String firstHand = ("player " + this.id + " initial hand:" + this.handToString());
        this.output.add(firstHand);
    }

    /**
     * Gets the output ArrayLst size to use in creating the output files
     * @return Output ArrayList size
     */

    public int getOutputSize() {
        return this.output.size();
    }

    /**
     *  This methods makes it easier to add to an output file as it converts the players
     *  hand to a String
     * @return Player's hand which has been converted to a String
     */
    public synchronized String handToString() {
        String handString = "";

        for (int i = 0; i < this.hand.size(); i ++)
        {
            handString = handString + " " + this.hand.get(i).value;
        }

        return handString;
    }

    /**
     * This method takes the deck as a parameter and calls a function that will
     * take a card from that deck
     * @param deck The deck that the player takes a card from
     * @return The card that is drawn from the deck by a player
     */
    public synchronized Card drawCard(Deck deck)
    {
        return deck.cardTaken();
    }

    /**
     * This method takes the discarded card from the player and calls the cardAdded()
     * method
     * @param card Gets card from the player
     * @param deck Gets the deck that needs to add the card
     */


    public synchronized void discardCard(Card card, Deck deck)
    {
        deck.cardAdded(card);
    }

    /**
     * Checks to see if the number of rounds of a certain player is the highest
     * compared to all other players. This sets a number for all other players to
     * reach when one has won to ensure that there are 4 cards in the decks at
     * the end.
     */

    public synchronized void mostRounds() {
        if (this.playerRounds > CardGame.rounds) {
            CardGame.rounds = this.playerRounds;
        }
    }

    /**
     *  This method does the action of picking and discarding a card.
     * @param deckLeft the deck that the player will be picking up a card from
     * @param deckRight the deck that the player will be discarding a card to
     */

    public synchronized void myAction(Deck deckLeft, Deck deckRight)
    {
        if (deckLeft.dHand.size() > 0)
        {
            playerRounds ++;
            mostRounds();

            Card addCard = drawCard(deckLeft);
            hand.add(addCard);

            String drawing = ("player " + this.id + " draws a " + addCard.value + " from deck " + deckLeft.id);
            output.add(drawing);

            /*
                Checks each card to see if it equals player ID. Discards the first
                card that isn't equalling ID since that will be the oldest card
                in the hand
             */
            boolean done = false; // k - for cases like 5 ones, so if no cards will get removed in the for loop, the 'if' will run

            for (int i = 0; i < hand.size(); i++) {

                if (!((hand.get(i).value) == (id))) {

                    Card removeCard = hand.remove(i);
                    discardCard(removeCard, deckRight);

                    String discarding = ("player " + this.id + " discards a " + removeCard.value + " to deck " + (deckRight.id));
                    output.add(discarding);
                    done = true;
                    break;
                }
            }

            if (!done)
            {
                Card removeCard = hand.remove(hand.size()-1);
                discardCard(removeCard, deckRight);

                String discarding = ("player " + this.id + " discards a " + removeCard.value + " to deck " + (deckRight.id));
                output.add(discarding);
            }
            checkHand();
            mostRounds();
        }
    }

    /**
     *  Once checkHand checks that a player has won, it calls this function which
     *  adds to the output ArrayList and makes endGame true so that other players
     *  are aware that someone has won.
     */

    public synchronized void whoWonCheck()
    {
        if (CardGame.whoWon == 0)
        {
            CardGame.endGame = true;
            CardGame.whoWon = this.id;
            String wins = ("player " + CardGame.whoWon + " wins");
            this.output.add(wins);
        }
    }


    /**
     * This method checks the hand when the cards are distributed and after the joint
     * action of picking and discarding a card takes place to see if the player won.
     * It then adds the current hand to output array if the cards aren't equal to each
     * other or calls the whoWonCheck method if a player wins
     */


    public synchronized void checkHand()
    {
        if (!CardGame.endGame && this.hand.get(0).value == this.hand.get(1).value && this.hand.get(0).value == this.hand.get(2).value && this.hand.get(0).value == this.hand.get(3).value)
        {
            whoWonCheck();
        }
        else
        {
            // Doesn't print current hand if a player wins with initial hand
            if (this.playerRounds != 0) {
                String currentHand = ("player " + this.id + " current hand is" + this.handToString());
                this.output.add(currentHand);
            }
        }
    }
//
//    public void makeDir(){
//
//    }


    /**
     * This method starts the game and continues the game until endGame is true. It checks
     * the mostRounds() to make all players end with the same number of rounds. The game is
     * continued until all players each mostRounds(). Final strings are then added to the
     * output ArrayList. Output files are created for each player and deck. PrintWriter is used
     * to write to the files from the output ArrayList.
     */

    @Override
    public void run()
    {
        // Checks for initial winning hand
        checkHand();

        // Game starts
        while (!CardGame.endGame)
        {
            myAction(CardGame.decksList.get(((this.id-1) % CardGame.nofPlayers)), CardGame.decksList.get((this.id) % CardGame.nofPlayers));
            // Compares rounds each time a thread does myAction
            mostRounds();
        }

        // Sleeps to allow mostRounds() to be updated for all threads
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //Plays the game until all player reach same number of rounds
        while (this.playerRounds < CardGame.rounds)
        {
            myAction(CardGame.decksList.get(((this.id-1) % CardGame.nofPlayers)), CardGame.decksList.get((this.id) % CardGame.nofPlayers));
            mostRounds();
        }

        // Adds to output ArrayList
        if (CardGame.whoWon != id)
        {
            this.output.add(("player " + CardGame.whoWon + " has informed player " + this.id + " that player " + CardGame.whoWon + " has won"));
            this.output.add(("player " + this.id + " exits"));
            this.output.add("player " + this.id + " hand:" + this.handToString());
        }
        else
        {
            this.output.add("player " + CardGame.whoWon + " exits");
            this.output.add("player " + CardGame.whoWon + " final hand is" + this.handToString());
            System.out.println("player " + CardGame.whoWon + " has won");
        }

        // Sleeps to ensure all threads are finished and decks have 4 cards at the end
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        String filePlayer = "Output Files/player" + this.id + "_output.txt";
        String fileDeck = "Output Files/deck" + this.id + "_output.txt";




//        if(!dirCreated) {
//            File dir = new File("Output Files");
//            if (!dir.exists()) {
//                dir.mkdir();
//            } else {
//                String[] files = dir.list();
//                for (String f : files) {
//                    File currentfile = new File(dir.getPath(), f);
//                    currentfile.delete();
//                }
//                dir.mkdir();
//            }
//            dirCreated = true;
//        }
        // Tries to creates output files and catches if an exception is reached
        try {
            PrintWriter out1 = new PrintWriter(new FileWriter(filePlayer));
            PrintWriter out2 = new PrintWriter(new FileWriter(fileDeck));
            // Iterates through output ArrayList for each line and adds to file
            for (int j = 0; j < this.getOutputSize(); j++)
            {
                out1.println(this.output.get(j));
            }

            // Adds to deck output file
            String deckOutput = CardGame.decksList.get((this.id-1)).printHand();
            out2.println(deckOutput);

            out1.close();
            out2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

