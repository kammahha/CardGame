package cardgame;

import java.util.ArrayList;

public class Deck {

    int id;
    ArrayList<Card> dHand = new ArrayList<Card>();

    /**
     * Assigns id to the deck
     * @param id identifier for the decks
     */
    Deck(int id) {
        this.id = id;
    }

    /**
     * Sets initial hand of deck.
     * @param c1 1st card distributed to the player
     * @param c2 2nd card distributed to the player
     * @param c3 3rd card distributed to the player
     * @param c4 4th card distributed to the player
     */

    void setInitialHand(Card c1, Card c2, Card c3, Card c4){
        dHand.add(c1);
        dHand.add(c2);
        dHand.add(c3);
        dHand.add(c4);
    }


    /**
     * Gets the final deck contents so that it can be printed to the output
     * file
     * @return String variable called hand
     */
    synchronized String printHand() {
        String hand = "deck " + this.id + " contents:";
        for (int i = 0; i < dHand.size(); i ++)
        {
            hand = hand + " " + dHand.get(i).value;
        }
        return hand;
    }

    /**
     * Adds a card to the deck when a player discards it
     * @param card Card that the player discards
     */

    synchronized void cardAdded(Card card) {
        dHand.add(card);
    }

    /**
     * Removes the first card from the deck hand
     * @return The card that has been removed
     */

    synchronized Card cardTaken() {
        return dHand.remove(0);
    }
}
