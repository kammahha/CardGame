package cardgame;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DeckTest {

    Deck deck;
    Card card1;
    ArrayList<Card> cardArray;

    @Before
    public void setUp() {
        deck = new Deck(1);
        card1 = new Card(2);
        cardArray = new ArrayList<>();
    }

    @Test
    public void testDeck() {
        Assert.assertNotNull(deck);
    }

    @Test
    public void testSetInitialHand() {
        deck.setInitialHand(card1, card1, card1, card1);

        cardArray.add(card1);
        cardArray.add(card1);
        cardArray.add(card1);
        cardArray.add(card1);
        Assert.assertEquals(cardArray, deck.dHand);
    }

    @Test
    public void testGetHand() {
        deck.setInitialHand(card1, card1, card1, card1);
        Assert.assertEquals("deck 1 contents: 2 2 2 2", deck.printHand());
    }

    @Test
    public void testCardAdded() {
        Card addCard = new Card(3);
        deck.cardAdded(addCard);
        Assert.assertEquals(3, deck.dHand.get(0).value);
    }

    @Test
    public void testCardTaken() {
        deck.setInitialHand(card1, card1, card1, card1);
        Card removedCard = deck.cardTaken();
        Assert.assertEquals(2, removedCard.value);
        Assert.assertEquals("deck 1 contents: 2 2 2", deck.printHand());
    }
}