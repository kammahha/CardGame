package cardgame;

import java.util.ArrayList;

public class Deck {

    int id;
    ArrayList<Card> dHand = new ArrayList<Card>();


    Deck(int id) {
        this.id = id;
    }



    void setInitialHand(Card c1, Card c2, Card c3, Card c4){
        dHand.add(c1);
        dHand.add(c2);
        dHand.add(c3);
        dHand.add(c4);
    }



    synchronized String printHand() {
        String hand = "deck " + this.id + " contents:";
        for (int i = 0; i < dHand.size(); i ++)
        {
            hand = hand + " " + dHand.get(i).value;
        }
        System.out.println("deck " + this.id + " contents:" + hand);
        return hand;
    }



    synchronized void cardAdded(Card card) {
        dHand.add(card);
    }



    synchronized Card cardTaken() {
        return dHand.remove(0);
    }
}
