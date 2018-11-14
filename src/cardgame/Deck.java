package cardgame;

import java.util.ArrayList;

public class Deck {

    int id;
    ArrayList<Card> dHand = new ArrayList<Card>();

    Deck (int id)
    {
        this.id = id;
    }

    public synchronized void setInitialHand(Card c1, Card c2, Card c3, Card c4){
        dHand.add(c1);
        dHand.add(c2);
        dHand.add(c3);
        dHand.add(c4);
    }

    public synchronized String getHand()
    {
        String hand = "";
        for (int i = 0; i < dHand.size(); i ++)
        {
            hand = hand + dHand.get(i).value + " ";
        }
        System.out.println(hand);
        return hand;
    }
//    public synchronized Card removeCard(){
//        return dHand.remove(0);
//    }
//    public synchronized void addCard(Card aCard){
//         dHand.add(aCard);
//    }
}
