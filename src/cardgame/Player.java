package cardgame;

import java.util.ArrayList;

public class Player implements Runnable {

    int id; // so player1 is gonna be 0

    ArrayList<Card> hand = new ArrayList<Card>();

    Player (int id)
    {
        this.id = id;
    }

    public synchronized void setInitialHand(Card c1, Card c2, Card c3, Card c4){
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        System.out.println("player " + id + " initial hand: " + hand.get(0).value + " " + hand.get(1).value + " " + hand.get(2).value + " " + hand.get(3).value);
    }

    public ArrayList<Card> getHand()
    {
        return hand;
    }

    public synchronized void myAction (Deck deckLeft, Deck deckRight) {
        // taking a card
        if (deckLeft.dHand.size() > 0)
        {
            Card addCard = deckLeft.dHand.remove(0);
            hand.add(addCard);
            System.out.println("Player " + id + " draws a " + addCard.value + " from deck " + deckLeft.id);
            // discarding a card
            for (int i = 0; i < hand.size(); i++ ){
                if(!((hand.get(i).value) == (id))) {
                    Card removeCard = hand.remove(i); // we need to apply strategy here
                    deckRight.dHand.add(removeCard);
                    System.out.println("Player " + id + " discards a " + removeCard.value + " to deck " + (deckRight.id));
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
            System.out.println("Player " + this.id + " final hand is " + this.hand.get(0).value + " " + this.hand.get(1).value + " " + this.hand.get(2).value + " " + this.hand.get(3).value);
        }
        else
            System.out.println("Player " + this.id + " current hand is " + this.hand.get(0).value + " " + this.hand.get(1).value + " " + this.hand.get(2).value + " " + this.hand.get(3).value);

    }

    @Override
    public void run() {
        while(!CardGame.endGame)
        {
            myAction(CardGame.decksList.get((this.id % CardGame.nofPlayers)), CardGame.decksList.get((this.id + 1) % CardGame.nofPlayers));
        }
    }
}
