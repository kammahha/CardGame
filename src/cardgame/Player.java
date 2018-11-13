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

    /**
     *  This method does the action of picking and discarding a card.
     * @param deckLeft the deck that the player will be picking up a card from
     * @param deckRight the deck that the player will be discarding a card to
     */

    public void myAction(Deck deckLeft, Deck deckRight) {

        // checks to see if deck is non-empty
        if (deckLeft.dHand.size() > 0) {
            Card addCard = deckLeft.dHand.remove(0);
            // removes card from deck and adds to hand
            hand.add(addCard);

            String drawing = ("player " + this.id + " draws a " + addCard.value + " from deck " + deckLeft.id);
            output.add(drawing);

            /*
                Checks each card to see if it equals player ID. Discards the first
                card that isn't equalling ID since that will be the oldest card
                in the hand
             */
            for (int i = 0; i < 4; i++) {

                if (!((hand.get(i).value) == (id))) {

                    Card removeCard = hand.remove(i);
                    deckRight.dHand.add(removeCard);

                    String discarding = ("Player " + this.id + " discards a " + removeCard.value + " to deck " + (deckRight.id));
                    output.add(discarding);
                    break;
                }
            }
            howManyRounds ++;
            checkHand();
        }
    }


//    public synchronized int enoughRounds()
//    {
//        if (CardGame.rounds < this.howManyRounds)
//            return this.howManyRounds;
//        else
//            return CardGame.rounds;
//    }



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
            //CardGame.whoWonOutputSize = this.output.size();
            CardGame.rounds = this.howManyRounds;
            String wins = ("Player " + this.id + " wins");
            String exits = ("Player " + this.id + " exits");
            String finalHand = ("Player " + this.id + " final hand is " + this.hand.get(0).value + " " + this.hand.get(1).value + " " + this.hand.get(2).value + " " + this.hand.get(3).value);
            output.add(wins);
            output.add(exits);
            output.add(finalHand);
            System.out.println("Player " + CardGame.whoWon + " has won");
        }
    }



    @Override
    public void run() {
        checkHand();
        //while (!CardGame.endGame && this.howManyRounds <= CardGame.rounds)
        while (!CardGame.endGame) {
            myAction(CardGame.decksList.get(((this.id-1) % CardGame.nofPlayers)), CardGame.decksList.get((this.id) % CardGame.nofPlayers));
        }
        for (int i = 0; i < CardGame.nofPlayers; i ++)
        {
            if (CardGame.rounds <= CardGame.playersList.get(i).howManyRounds)
            {
                CardGame.rounds = CardGame.playersList.get(i).howManyRounds;
            }
        }
        while (CardGame.rounds != this.howManyRounds)
        {
            myAction(CardGame.decksList.get(((this.id-1) % CardGame.nofPlayers)), CardGame.decksList.get((this.id) % CardGame.nofPlayers));
        }

        if (CardGame.whoWon != id)
        {
            output.add(("Player " + CardGame.whoWon + " has informed player " + this.id + " that player " + CardGame.whoWon + " has won"));
            output.add(("Player " + this.id + " exits"));
            String currentHand = ("Player " + this.id + " hand: " + this.hand.get(0).value + " " + this.hand.get(1).value + " " + this.hand.get(2).value + " " + this.hand.get(3).value);
            output.add(currentHand);
        }

        String filePlayer = "Player" + this.id + "_output.txt";
        String fileDeck = "Deck" + this.id + "_output.txt";
        try {
            PrintWriter out1 = new PrintWriter(new FileWriter(filePlayer));
            PrintWriter out2 = new PrintWriter(new FileWriter(fileDeck));

            //int diff = CardGame.whoWonOutputSize - this.returnSize();

            for (int j = 0; j < this.returnSize(); j++) {
                out1.println(this.output.get(j));
            }

            String deckOutput = (CardGame.decksList.get(this.id % CardGame.nofPlayers).getHand());
            out2.println(deckOutput);

            out1.close();
            out2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

