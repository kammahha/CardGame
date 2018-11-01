package cardgame;

import java.util.ArrayList;

public class Player {

    int id; // so player1 is gonna be 0

    ArrayList<Card> hand = new ArrayList<Card>();

    Player (int id)
    {
        this.id = id;
    }

    public void setInitialHand(Card c1, Card c2, Card c3, Card c4){
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
    }

    public ArrayList<Card> getHand()
    {
        return hand;
    }

    public void takeCard(Deck deckLeft) {
        Card addCard = deckLeft.dHand.remove(0);
        hand.add(addCard);
        System.out.println("Player " + id + " draws a " + addCard.value + " from deck " + id);
    }

    public void giveCard(Deck deckRight) {
        for (int i = 0; i < hand.size(); i++ ){
            if(!hand.get(i).equals(id)){
                Card removeCard = hand.remove(i); // we need to apply strategy here
                deckRight.dHand.add(removeCard);
                System.out.println("Player " + id + " discards a " + removeCard.value + " from deck " + id+1);
                break;

            }
        }

    }
    public void checkHand(){
        // check hand
        System.out.println("Player " + id + "current hand is " + hand.get(0) + hand.get(1) + hand.get(2) + hand.get(3));
    }

}

