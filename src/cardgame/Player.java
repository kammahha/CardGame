package cardgame;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Player implements Runnable {

    private int id; // so player1 is gonna be 0
    private ArrayList<Card> hand = new ArrayList<>();
    private ArrayList<String> output = new ArrayList<>();
    private boolean dontWait;
    private int howManyRounds = 0;

    public int returnSize() {
        return output.size();
    }

    Player(int id) {
        this.id = id;
    }

    public synchronized String handToString()
    {
        String handString = "";
        for (int i = 0; i < hand.size(); i ++)
        {
            handString = handString + hand.get(i).value + " ";
        }
        //System.out.println(this.id + ": " + handString);
        return handString;
    }

//    Player(int id, boolean dontWait){
//        this(id);
//        this.dontWait = dontWait;
//    }

    /**
     * Sets initial hand and adds initial hand to output array
     * @param c1 1st card distributed to the player
     * @param c2 2nd card distributed to the player
     * @param c3 3rd card distributed to the player
     * @param c4 4th card distributed to the player
     */

    public void setInitialHand(Card c1, Card c2, Card c3, Card c4) {
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        String firstHand = ("player " + id + " initial hand: " + c1.value + " " + c2.value + " " + c3.value + " " + c4.value);
        output.add(firstHand);
    }


    public synchronized Card drawCard(Deck deck)
    {
        return deck.cardTaken();
    }

    public synchronized void discardCard(Card card, Deck deck)
    {
        deck.cardAdded(card);
    }

    /**
     *  This method does the action of picking and discarding a card.
     * @param deckLeft the deck that the player will be picking up a card from
     * @param deckRight the deck that the player will be discarding a card to
     */

    public synchronized void myAction(Deck deckLeft, Deck deckRight) {

        // checks to see if deck is non-empty
        if (deckLeft.dHand.size() > 0) {
            howManyRounds ++;
            mostRounds();

            Card addCard = drawCard(deckLeft);
            // removes card from deck and adds to hand
            hand.add(addCard);

            String drawing = ("player " + this.id + " draws a " + addCard.value + " from deck " + deckLeft.id);
            output.add(drawing);

            /*
                Checks each card to see if it equals player ID. Discards the first
                card that isn't equalling ID since that will be the oldest card
                in the hand
             */
            boolean done = false;
            for (int i = 0; i < hand.size(); i++) {

                if (!((hand.get(i).value) == (id))) {

                    Card removeCard = hand.remove(i);
                    discardCard(removeCard, deckRight);

                    String discarding = ("Player " + this.id + " discards a " + removeCard.value + " to deck " + (deckRight.id));
                    output.add(discarding);
                    done = true;
                    break;
                }
            }
            if (!done)
            {
                Card removeCard = hand.remove(hand.size()-1);
                discardCard(removeCard, deckRight);

                String discarding = ("2. Player " + this.id + " discards a " + removeCard.value + " to deck " + (deckRight.id));
                output.add(discarding);
            }
            checkHand();
            mostRounds();

        }
    }


    public synchronized void mostRounds() {
        if (this.howManyRounds > CardGame.rounds) {
            CardGame.rounds = this.howManyRounds;
        }
    }


    /**
     * This method checks the hand when the cards are distributed and after the joint
     * action of picking and discarding a card takes place to see if the player won.
     * It then adds the current hand to output array if the cards aren't equal to each
     * other.
     */
    public synchronized void checkHand() {
        if (!CardGame.endGame && this.hand.get(0).value == this.hand.get(1).value && this.hand.get(0).value == this.hand.get(2).value && this.hand.get(0).value == this.hand.get(3).value) {
            whoWonCheck();

        } else {
            String currentHand = ("Player " + this.id + " current hand is " + this.hand.get(0).value + " " + this.hand.get(1).value + " " + this.hand.get(2).value + " " + this.hand.get(3).value);
            output.add(currentHand);
        }
    }


    public synchronized void whoWonCheck()
    {
        if (CardGame.whoWon == 0)
        {
            CardGame.endGame = true;
            CardGame.whoWon = this.id;
            String wins = ("Player " + CardGame.whoWon + " wins");
            this.output.add(wins);
        }
    }



    @Override
    public void run() {
        checkHand();
        while (!CardGame.endGame) {
            myAction(CardGame.decksList.get(((this.id-1) % CardGame.nofPlayers)), CardGame.decksList.get((this.id) % CardGame.nofPlayers));
            mostRounds();
        }

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        while (this.howManyRounds < CardGame.rounds)
        {
            myAction(CardGame.decksList.get(((this.id-1) % CardGame.nofPlayers)), CardGame.decksList.get((this.id) % CardGame.nofPlayers));
            mostRounds();
        }

        if (CardGame.whoWon != id)
        {
            output.add(("Player " + CardGame.whoWon + " has informed player " + this.id + " that player " + CardGame.whoWon + " has won"));
            output.add(("Player " + this.id + " exits"));
            String currentHand = ("Player " + this.id + " hand: " + this.hand.get(0).value + " " + this.hand.get(1).value + " " + this.hand.get(2).value + " " + this.hand.get(3).value);
            output.add(currentHand);
        }
        else
        {
            System.out.println(CardGame.rounds);
            String exits = ("Player " + CardGame.whoWon + " exits");
            String finalHand = ("Player " + CardGame.whoWon + " final hand is " + this.hand.get(0).value + " " + this.hand.get(1).value + " " + this.hand.get(2).value + " " + this.hand.get(3).value);
            this.output.add(exits);
            this.output.add(finalHand);
            System.out.println("Player " + CardGame.whoWon + " has won");
        }

        String filePlayer = "Player" + this.id + "_output.txt";
        String fileDeck = "Deck" + this.id + "_output.txt";
        try {
            PrintWriter out1 = new PrintWriter(new FileWriter(filePlayer));
            PrintWriter out2 = new PrintWriter(new FileWriter(fileDeck));


            for (int j = 0; j < this.returnSize(); j++) {
                out1.println(this.output.get(j));
            }

            String deckOutput = CardGame.decksList.get((this.id-1)).getHand();
            System.out.println("player " + id + " hand: " + handToString());
            out2.println(deckOutput);

            out1.close();
            out2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

