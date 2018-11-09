package cardgame;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Player implements Runnable {

    private int id; // so player1 is gonna be 0
    private ArrayList<Card> hand = new ArrayList<>();
    private ArrayList<String> output = new ArrayList<>();

    public int returnSize() {
        return output.size();
    }

    Player(int id) {
        this.id = id;
    }


    public synchronized void setInitialHand(Card c1, Card c2, Card c3, Card c4) {
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        String firstHand = ("player " + id + " initial hand: " + c1.value + " " + c2.value + " " + c3.value + " " + c4.value);
        output.add(firstHand);
    }


    public synchronized void myAction(Deck deckLeft, Deck deckRight) {

        // taking a card
        if (deckLeft.dHand.size() > 0) {
            Card addCard = Deck.removeCard(deckLeft.dHand);
            hand.add(addCard);

            String drawing = ("player " + this.id + " draws a " + addCard.value + " from deck " + deckLeft.id);
            output.add(drawing);

            // discarding a card
            for (int i = 0; i < hand.size(); i++) {
                if (!((hand.get(i).value) == (id))) {
                    Card removeCard = hand.remove(i); // we need to apply strategy here
                    Deck.addCard(deckRight.dHand,removeCard);

                    String discarding = ("Player " + this.id + " discards a " + removeCard.value + " to deck " + (deckRight.id));
                    output.add(discarding);
                    break;
                }
            }
            checkHand();
        }
    }


    public synchronized void checkHand() {
        if (hand.get(0).value == hand.get(1).value && hand.get(0).value == hand.get(2).value && hand.get(0).value == hand.get(3).value) {
            CardGame.endGame = true;
            CardGame.whoWon = this.id;
            String wins = ("Player " + this.id + " wins");
            String exits = ("Player " + this.id + " exits");
            String finalHand = ("Player " + this.id + " final hand is " + this.hand.get(0).value + " " + this.hand.get(1).value + " " + this.hand.get(2).value + " " + this.hand.get(3).value);
            output.add(wins);
            output.add(exits);
            output.add(finalHand);
            System.out.println("Player " + CardGame.whoWon + " has won");
        } else {
            String currentHand = ("Player " + this.id + " current hand is " + this.hand.get(0).value + " " + this.hand.get(1).value + " " + this.hand.get(2).value + " " + this.hand.get(3).value);
            output.add(currentHand);
        }
    }


    @Override
    public void run() {
        while (!CardGame.endGame) {
            myAction(CardGame.decksList.get(((this.id-1) % CardGame.nofPlayers)), CardGame.decksList.get((this.id) % CardGame.nofPlayers));
        }
        if (CardGame.whoWon != this.id)
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
