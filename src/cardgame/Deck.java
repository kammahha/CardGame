package cardgame;

import java.util.ArrayList;

public class Deck {

    int id;
    ArrayList<Card> dHand = new ArrayList<Card>();

    Deck (int id)
    {
        this.id = id;
    }

    public void setInitialHand(Card c1, Card c2, Card c3, Card c4){
        dHand.add(c1);
        dHand.add(c1);
        dHand.add(c1);
        dHand.add(c1);
    }

    public ArrayList<Card> getHand()
    {
        return dHand;
    }


}
