package cardgame;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DeckTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void Deck() {
        Deck deck = new Deck(1);
        Assert.assertNotNull(deck);
    }

    Deck deck = new Deck(1);
    Card card = new Card(2);

    @Test
    public void setInitialHand() {
        deck.setInitialHand(card, card, card, card);
        ArrayList<Card> cardArray = new ArrayList<>();
        cardArray.add(card);
        cardArray.add(card);
        cardArray.add(card);
        cardArray.add(card);
        Assert.assertEquals(cardArray, deck.dHand);
    }

    @Test
    public void getHand() {
        deck.setInitialHand(card, card, card, card);
        Assert.assertEquals("deck 1 contents: 2 2 2 2 ", deck.printHand());
    }

    @Test
    public void cardAdded() {
        Card addCard = new Card(3);
        deck.cardAdded(addCard);
        Assert.assertEquals(3, deck.dHand.get(0).value);
    }

    @Test
    public void cardTaken() {
        deck.setInitialHand(card, card, card, card);
        Card removedCard = deck.cardTaken();
        Assert.assertEquals(2, removedCard.value);
        Assert.assertEquals("deck 1 contents: 2 2 2 ", deck.printHand());
    }
}