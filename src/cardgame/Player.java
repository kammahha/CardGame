package cardgame;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Player implements Runnable {

    private int id;
    public ArrayList<Card> hand = new ArrayList<>();
    public ArrayList<String> output = new ArrayList<>();
    private int playerRounds = 0;



    Player(int id)
    {
        this.id = id;
    }



    public int getOutputSize()
    {
        return this.output.size();
    }



    public synchronized String handToString()
    {
        String handString = "";

        for (int i = 0; i < this.hand.size(); i ++)
        {
            handString = handString + this.hand.get(i).value + " ";
        }

        return handString;
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
        String firstHand = ("player " + this.id + " initial hand: " + this.handToString());
        this.output.add(firstHand);
    }



    public synchronized Card drawCard(Deck deck)
    {
        return deck.cardTaken();
    }



    public synchronized void discardCard(Card card, Deck deck)
    {
        deck.cardAdded(card);
    }



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
     * This method checks the hand when the cards are distributed and after the joint
     * action of picking and discarding a card takes place to see if the player won.
     * It then adds the current hand to output array if the cards aren't equal to each
     * other.
     */



    public synchronized void checkHand()
    {
        if (!CardGame.endGame && this.hand.get(0).value == this.hand.get(1).value && this.hand.get(0).value == this.hand.get(2).value && this.hand.get(0).value == this.hand.get(3).value)
        {
            whoWonCheck();
        }
        else
        {
            String currentHand = ("player " + this.id + " current hand is " + this.handToString());
            this.output.add(currentHand);
        }
    }



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



    @Override
    public void run()
    {
        checkHand();
        while (!CardGame.endGame)
        {
            myAction(CardGame.decksList.get(((this.id-1) % CardGame.nofPlayers)), CardGame.decksList.get((this.id) % CardGame.nofPlayers));
            mostRounds();
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        while (this.playerRounds < CardGame.rounds)
        {
            myAction(CardGame.decksList.get(((this.id-1) % CardGame.nofPlayers)), CardGame.decksList.get((this.id) % CardGame.nofPlayers));
            mostRounds();
        }


        if (CardGame.whoWon != id)
        {
            this.output.add(("player " + CardGame.whoWon + " has informed player " + this.id + " that player " + CardGame.whoWon + " has won"));
            this.output.add(("player " + this.id + " exits"));
            this.output.add("player " + this.id + " hand: " + this.handToString());
        }
        else
        {
            System.out.println(CardGame.rounds);
            this.output.add("player " + CardGame.whoWon + " exits");
            this.output.add("player " + CardGame.whoWon + " final hand is " + this.handToString());
            System.out.println("player " + CardGame.whoWon + " has won");
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        String filePlayer = "player" + this.id + "_output.txt";
        String fileDeck = "deck" + this.id + "_output.txt";


        try {
            PrintWriter out1 = new PrintWriter(new FileWriter(filePlayer));
            PrintWriter out2 = new PrintWriter(new FileWriter(fileDeck));

            for (int j = 0; j < this.getOutputSize(); j++)
            {
                out1.println(this.output.get(j));
            }


            String deckOutput = CardGame.decksList.get((this.id-1)).getHand();
            System.out.println("player " + this.id + " hand: " + this.handToString());
            out2.println(deckOutput);

            out1.close();
            out2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

