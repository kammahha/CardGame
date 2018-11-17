package cardgame;

import org.junit.*;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerTest {

    private Player player;
    private Card card1;
    private Card card2;
    private Card card3;
    private ArrayList<Card> cardArray;
    private Deck deck1;
    private Deck deck2;


    // before rarther than BeforeClass because we want it ti be executed
    // before each test and not once before all tests
    @Before
    public void setUpClass() throws Exception{
        player = new Player(1);
        card1 = new Card(1);
        card2 = new Card(2);
        card3 = new Card(3);
        cardArray = new ArrayList<>();
        deck1 = new Deck(1);
        deck2 = new Deck(2);
        player.playerRounds = 3;
        CardGame.whoWon = 0;
        CardGame.endGame = false;
    }

    @Test
    public void testPlayer() {
        Assert.assertNotNull(player);
    }


    @Test
    public void testSetInitialHand() {
        player.setInitialHand(card2, card2, card2, card2);

        cardArray.add(card2);
        cardArray.add(card2);
        cardArray.add(card2);
        cardArray.add(card2);

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
        player.setInitialHand(card3, card3, card3, card3);

        Assert.assertEquals(" 3 3 3 3", player.handToString());
    }

    @Test
    public void testDrawCard() {
        deck1.setInitialHand(card1, card2, card2, card2);
        Card drawnCard = player.drawCard(deck1);

        Assert.assertEquals(1, drawnCard.value);
    }

    @Test
    public void testDiscardCard() {
        player.setInitialHand(card1, card2, card2, card2);
        Card discardedCard = player.hand.remove(0);
        player.discardCard(discardedCard, deck1);

        Assert.assertEquals(1, deck1.dHand.get(0).value);
    }

    @Test
    public void testMostRounds5() {
        CardGame.rounds = 5;
        player.mostRounds();

        Assert.assertEquals(5, CardGame.rounds);
    }

    @Test
    public void testMostRounds3() {
        CardGame.rounds = 1;
        player.mostRounds();

        Assert.assertEquals(3, CardGame.rounds);
    }

    @Test
    public void testMyActionLeftDeck() {
        player.setInitialHand(card1, card1, card1, card2);
        deck1.setInitialHand(card1, card1, card1, card1);
        deck2.setInitialHand(card2, card2, card2, card2);

        player.myAction(deck1, deck2);

        cardArray.add(card1);
        cardArray.add(card1);
        cardArray.add(card1);

        Assert.assertEquals(cardArray, deck1.dHand);
    }

    @Test
    public void testMyActionRightDeck() {
        player.setInitialHand(card1, card1, card1, card2);
        deck1.setInitialHand(card1, card1, card1, card1);
        deck2.setInitialHand(card2, card2, card2, card2);

        player.myAction(deck1, deck2);

        cardArray.add(card2);
        cardArray.add(card2);
        cardArray.add(card2);
        cardArray.add(card2);
        cardArray.add(card2);

        Assert.assertEquals(cardArray, deck2.dHand);
    }

    @Test
    public void testMyAction5sameCards() {
        player.setInitialHand(card1, card1, card1, card1);
        deck1.setInitialHand(card1, card1, card1, card1);
        deck2.setInitialHand(card1, card1, card1, card1);

        player.myAction(deck1, deck2);

        cardArray.add(card1);
        cardArray.add(card1);
        cardArray.add(card1);

        Assert.assertEquals(cardArray, deck1.dHand);

        cardArray.add(card1);
        cardArray.add(card1);

        Assert.assertEquals(cardArray, deck2.dHand);
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
        player.setInitialHand(card1, card1, card1, card1);
        player.checkHand();
        Assert.assertEquals(1, CardGame.whoWon);
    }

    @Test
    public void testCheckHandNotWon() {
        player.setInitialHand(card1, card1, card2, card2);
        player.checkHand();
        Assert.assertNotEquals(1, CardGame.whoWon);
    }
}