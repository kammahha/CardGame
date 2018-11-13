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
    private boolean Iwon = false;

    public int returnSize() {
        return output.size();
    }

    Player(int id) {
        this.id = id;
    }
    Player(int id, boolean dontWait){
        this(id);
        this.dontWait = dontWait;
    }


    public void setInitialHand(Card c1, Card c2, Card c3, Card c4) {
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        String firstHand = ("player " + id + " initial hand: " + c1.value + " " + c2.value + " " + c3.value + " " + c4.value);
        output.add(firstHand);
    }


    public void myAction(Deck deckLeft, Deck deckRight) {

        // taking a card
        //have a variable to keep track
        if (deckLeft.dHand.size() > 0) {
            Card addCard = deckLeft.dHand.remove(0);
            hand.add(addCard);

            String drawing = ("player " + this.id + " draws a " + addCard.value + " from deck " + deckLeft.id);
            output.add(drawing);

            // discarding a card
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
            CardGame.rounds = theMostRounds();
            CardGame.hadTurn.set(this.id, true);
        }
//        dontWait = false;
    }


    public synchronized int theMostRounds()
    {
        if (CardGame.rounds < this.howManyRounds)
            return this.howManyRounds;
        else
            return CardGame.rounds;
    }


//    public synchronized boolean areAllTrue()
//    {
//        for (int i = 1; i <= CardGame.nofPlayers; i ++)
//        {
//            if (!CardGame.hadTurn.get(i))
//                return false;
//        }
//        return true;
//    }


    public synchronized void checkHand() {
        if (!CardGame.endGame)
        {
            if (this.hand.get(0).value == this.hand.get(1).value && this.hand.get(1).value == this.hand.get(2).value && this.hand.get(2).value == this.hand.get(3).value)
            {
                this.Iwon = true;
                CardGame.endGame = true;
            }

        } else
            {
            String currentHand = ("Player " + this.id + " current hand is " + this.hand.get(0).value + " " + this.hand.get(1).value + " " + this.hand.get(2).value + " " + this.hand.get(3).value);
            output.add(currentHand);
            }
    }

    // call it after cardgame.endgame is already true
    public synchronized void whoWonCheck() {
        for (int i = 0; i < CardGame.nofPlayers; i++) {
            if (CardGame.playersList.get(i).Iwon) {
                if (CardGame.playersList.get(i).howManyRounds < CardGame.rounds) {
                    CardGame.whoWon = (i + 1);
                }
            }
        }
    }



        if (Iwon)
        {
            if (this.howManyRounds <= ) //what if is equal?? eg both win after 3 rounds

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
        }
    }



    @Override
    public void run() {
        checkHand();
        //while (!CardGame.endGame && this.howManyRounds <= CardGame.rounds)
        while (!CardGame.endGame) {
//            synchronized (this) {
//                while(!dontWait) {
//                    try {
//                        this.wait();
//                        break;
//                    } catch (InterruptedException ignored) {}
//                }
//            }
            myAction(CardGame.decksList.get(((this.id-1) % CardGame.nofPlayers)), CardGame.decksList.get((this.id) % CardGame.nofPlayers));
//            final Player nextPlayer = CardGame.playersList.get((id) % CardGame.playersList.size());
//            synchronized (nextPlayer){
//                nextPlayer.notify();
//            }

            synchronized (CardGame.round)
            {
                if (!areAllTrue())
                {
                    while (!areAllTrue())
                    {
                        try {
                            CardGame.round.wait();
                            break;
                        } catch (InterruptedException ignored)  {}
                    }
                    checkHand();
                    if (this.Iwon)
                    {
                        whoWonCheck();
                    }
                }
                else
                {
                    CardGame.makeTable();
                    checkHand();
                    if (this.Iwon)
                    {
                        whoWonCheck();
                    }
                    CardGame.round.notifyAll();
                }

//                CardGame.makeTable();
//                checkHand();
//                if (this.Iwon)
//                {
//                    whoWonCheck();
//                }
//                CardGame.round.notifyAll();
            }
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
            String wins = ("Player " + this.id + " wins");
            String exits = ("Player " + this.id + " exits");
            String finalHand = ("Player " + this.id + " final hand is " + this.hand.get(0).value + " " + this.hand.get(1).value + " " + this.hand.get(2).value + " " + this.hand.get(3).value);
            output.add(wins);
            output.add(exits);
            output.add(finalHand);
        }

        System.out.println("Player " + CardGame.whoWon + " has won");
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
