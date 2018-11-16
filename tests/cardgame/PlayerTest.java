package cardgame;

import org.junit.*;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerTest {

    Player player = new Player(1);

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testPlayer() {
        Assert.assertNotNull(player);
    }


    @Test
    public void testSetInitialHand() {
        Card card = new Card(2);
        player.setInitialHand(card, card, card, card);
        ArrayList<Card> cardArray = new ArrayList<>();
        cardArray.add(card);
        cardArray.add(card);
        cardArray.add(card);
        cardArray.add(card);
        Assert.assertEquals(cardArray, player.hand);
    }

    @Test
    public void testGetOutputSize() {
       player.output.add("Hello World!");
       player.output.add("Hi World!");
       Assert.assertEquals(2, player.getOutputSize());
    }

    @Test
    public void testHandToString() {
        Card card = new Card(3);
        player.setInitialHand(card, card, card, card);
        Assert.assertEquals(" 3 3 3 3", player.handToString());
    }

    @Test
    public void testDrawCard() {
        Deck deck = new Deck(1);
        Card card1 = new Card(1);
        Card card2 = new Card(2);
        deck.setInitialHand(card1, card2, card2, card2);
        Card drawnCard = player.drawCard(deck);
        Assert.assertEquals(1, drawnCard.value);
    }

    @Test
    public void testDiscardCard() {
        Deck deck = new Deck(1);
        Card card1 = new Card(1);
        Card card2 = new Card(2);
        player.setInitialHand(card1, card2, card2, card2);
        Card discardedCard = player.hand.remove(0);
        player.discardCard(discardedCard, deck);
        Assert.assertEquals(1, discardedCard.value);
        Assert.assertEquals(1, deck.dHand.get(0).value);
    }

    @Test
    public void testMostRounds() {
        player.playerRounds = 3;
        CardGame.rounds = 5;
        player.mostRounds();
        Assert.assertEquals(5, CardGame.rounds);
        CardGame.rounds = 1;
        player.mostRounds();
        Assert.assertEquals(3, CardGame.rounds);
    }

    @Test
    public void testMyActionLeftDeck() {
        Card card1 = new Card(1);
        Card card2 = new Card(2);
        player.setInitialHand(card1, card1, card1, card2);

        Deck leftDeck = new Deck(1);
        leftDeck.setInitialHand(card1, card1, card1, card1);

        Deck rightDeck = new Deck(2);
        rightDeck.setInitialHand(card2, card2, card2, card2);

        player.myAction(leftDeck, rightDeck);

        ArrayList<Card> cardArray = new ArrayList<>();
        cardArray.add(card1);
        cardArray.add(card1);
        cardArray.add(card1);

        Assert.assertEquals(cardArray, leftDeck.dHand);
    }

    @Test
    public void testMyActionRightDeck() {
        Card card1 = new Card(1);
        Card card2 = new Card(2);
        player.setInitialHand(card1, card1, card1, card2);

        Deck leftDeck = new Deck(1);
        leftDeck.setInitialHand(card1, card1, card1, card1);

        Deck rightDeck = new Deck(2);
        rightDeck.setInitialHand(card2, card2, card2, card2);

        player.myAction(leftDeck, rightDeck);

        ArrayList<Card> cardArray = new ArrayList<>();
        cardArray.add(card2);
        cardArray.add(card2);
        cardArray.add(card2);
        cardArray.add(card2);
        cardArray.add(card2);

        Assert.assertEquals(cardArray, rightDeck.dHand);
    }

    @Test
    public void testWhoWonCheckEndGame() {
        player.whoWonCheck();
        Assert.assertTrue(CardGame.endGame);
    }

    @Test
    public void testWhoWonCheckId() {
        player.whoWonCheck();
        Assert.assertEquals(1, CardGame.whoWon);
    }

    @Test
    public void testCheckHandWon() {
        Card card1 = new Card(1);
        player.setInitialHand(card1, card1, card1, card1);
        player.checkHand();
        Assert.assertEquals(1, CardGame.whoWon);
    }

    @Test
    public void testCheckHandNotWon() {
        Player player = new Player(2);
        Card card1 = new Card(1);
        Card card2 = new Card(2);
        player.setInitialHand(card1, card1, card1, card2);
        player.checkHand();
        Assert.assertNotEquals(2, CardGame.whoWon);
    }
}