package cardgame;

import org.junit.*;

import org.junit.Test;

import javax.print.attribute.standard.JobOriginatingUserName;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class PlayerTest {

    private Player player;
    private Card card1;
    private Card card2;
    private Card card3;
    private ArrayList<Card> cardArray;
    private Deck deck1;
    private Deck deck2;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    // before rarther than BeforeClass because we want it ti be executed
    // before each test and not once before all tests REPORT!!!

    /**
     * Creates all of these before tests are started
     */
    @Before
    public void setUpClass() {
        player = new Player(1);
        card1 = new Card(1);
        card2 = new Card(2);
        card3 = new Card(3);
        cardArray = new ArrayList<>();
        deck1 = new Deck(1);
        deck2 = new Deck(2);
        CardGame.whoWon = 0;
        CardGame.endGame = false;

        // After this all System.out.println() statements will come to ourContent stream.
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Resets the variables after tests have finished
     */
    @After
    public void tearDown() {
        // Reset the System.out
        System.setOut(originalOut);
        CardGame.playersList.clear();
        CardGame.decksList.clear();
        player.playerRounds = 0;
    }

    /**
     * Checks if player exists
     */
    @Test
    public void testPlayer() {
        Assert.assertNotNull(player);
    }

    /**
     *  Adds cards to cardArray and sees if the cards are actually added using
     *  the setInitialHand method.
     */

    @Test
    public void testSetInitialHand() {
        player.setInitialHand(card2, card2, card2, card2);
        // Creating our own array to compare with
        cardArray.add(card2);
        cardArray.add(card2);
        cardArray.add(card2);
        cardArray.add(card2);

        // Compares created array and the hand set by the method
        Assert.assertEquals(cardArray, player.hand);
    }

    /**
     * Adds Strings to output ArrayList and checks if the size is what's
     * given
     */
    @Test
    public void testGetOutputSize() {
        // Adding data to output
       player.output.add("Hello World!");
       player.output.add("Hi World!");
       // Compares actual size and the size given out by the method
       Assert.assertEquals(2, player.getOutputSize());
    }

    /**
     * Checks if the method changes the ArrayList into String
     */
    @Test
    public void testHandToString() {
        player.setInitialHand(card3, card3, card3, card3);

        Assert.assertEquals(" 3 3 3 3", player.handToString());
    }

    /**
     * Checks to see if the card drawn by the method is correct. This is
     * done by the player in the Player class
     */
    @Test
    public void testDrawCard() {
        // Adding cards to deck
        deck1.setInitialHand(card1, card2, card2, card2);
        Card drawnCard = player.drawCard(deck1);
        // Compares the expected card of 1 to what is actually drawn
        Assert.assertEquals(1, drawnCard.value);
    }

    /**
     * Checks to see if the removed card is of the correct value. ????????????????????
     */
    @Test
    public void testDiscardCard() {
        // Setting up the deck
        player.setInitialHand(card1, card2, card2, card2);
        Card discardedCard = player.hand.remove(0);
        player.discardCard(discardedCard, deck1);

        Assert.assertEquals(1, deck1.dHand.get(0).value);
    }

    /**
     * Checks to see if the value of rounds changes depending on the
     * player's number of rounds
     */
    @Test
    public void testMostRounds5() {
        player.playerRounds = 3;
        CardGame.rounds = 5;
        player.mostRounds();

        // Case when rounds is higher than player rounds
        Assert.assertEquals(5, CardGame.rounds);
    }

    @Test
    public void testMostRounds3() {
        player.playerRounds = 3;
        CardGame.rounds = 1;
        player.mostRounds();

        // Case when rounds is smaller than player rounds
        Assert.assertEquals(3, CardGame.rounds);
    }

    /**
     *   Checks to see if the deck contains the correct 3 cards after
     *   a player picks up one card
     */
    @Test
    public void testMyActionLeftDeck() {

        player.setInitialHand(card1, card1, card1, card2);
        deck1.setInitialHand(card1, card1, card1, card1);
        deck2.setInitialHand(card2, card2, card2, card2);

        player.myAction(deck1, deck2);
        // Creating expected array
        cardArray.add(card1);
        cardArray.add(card1);
        cardArray.add(card1);

        // Checks remaining cards in deck
        Assert.assertEquals(cardArray, deck1.dHand);

    }

    /**
     *  Checks if the right deck contains correct 5 cards after a player
     *  discards a card from its hand to the deck
     */
    @Test
    public void testMyActionRightDeck() {
        player.setInitialHand(card1, card1, card1, card2);
        deck1.setInitialHand(card1, card1, card1, card1);
        deck2.setInitialHand(card2, card2, card2, card2);

        player.myAction(deck1, deck2);

        // Creating expected array of rightDeck
        cardArray.add(card2);
        cardArray.add(card2);
        cardArray.add(card2);
        cardArray.add(card2);
        cardArray.add(card2);

        // Checks remaining cards in deck
        Assert.assertEquals(cardArray, deck2.dHand);
    }

    /**
     * It tests the situation where 5th card is also the preferred
     * card
     */
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

    /**
     * Checks if endGame becomes true when the method is called
     */
    @Test
    public void testWhoWonCheckEndGame() {
        player.whoWonCheck();
        Assert.assertTrue(CardGame.endGame);
    }

    /**
     * Checks if the correct id of the winning player is being
     * added
     */
    @Test
    public void testWhoWonCheckId() {
        player.whoWonCheck();
        Assert.assertEquals(1, CardGame.whoWon);
    }

    /**
     * Checks if hand has all same 4 cards, if it wins or not according
     * to the method checkHand
     */
    @Test
    public void testCheckHandWon() {
        player.setInitialHand(card1, card1, card1, card1);
        player.checkHand();
        // Should give id 1 as it calls whoWonCheck method
        Assert.assertEquals(1, CardGame.whoWon);
    }

    /**
     *  Checks if a player's hand doesn't contain all the same cards, does
     *  it still enter the whoWonCheck method and set an id for whoWon
     */
    @Test
    public void testCheckHandNotWon() {
        player.setInitialHand(card1, card1, card2, card2);
        player.checkHand();
        // Shouldn't give id 1
        Assert.assertNotEquals(1, CardGame.whoWon);
    }

    /**
     * Tests to see if the correct output is being given and
     * the right number of rounds are played.
     */
    @Test
    public void testRunWinning(){
        CardGame.nofPlayers = 1;
        CardGame.rounds = 0;

        player.setInitialHand(card1, card1, card1, card2);
        deck1.setInitialHand(card1, card1, card1, card1);

        CardGame.playersList.add(player);
        CardGame.decksList.add(deck1);

        player.run();

        assertEquals("player 1 has won" + System.getProperty("line.separator"), outContent.toString());

        assertEquals(1, player.playerRounds);
        assertEquals(1, CardGame.rounds);
        assertTrue(CardGame.endGame);
        assertEquals(1, CardGame.whoWon);
    }

    /**
     * Tests the output ArrayList to see if it produces a correct size
     * after these actions are done
     */
    @Test
    public void testRunNotWinning(){
        CardGame.nofPlayers = 2;
        CardGame.rounds = 1;
        CardGame.endGame = true;
        CardGame.whoWon = 1;
        Player player2 = new Player(2);

        player.setInitialHand(card2, card1, card1, card1);
        deck1.setInitialHand(card1, card1, card2, card2);
        player2.setInitialHand(card1, card1, card2, card2);
        deck2.setInitialHand(card2, card1, card2, card2);

        CardGame.playersList.add(player);
        CardGame.decksList.add(deck1);
        CardGame.playersList.add(player2);
        CardGame.decksList.add(deck2);

        player2.run();

        // 7 because initial hand, draw, discard, current, informed, exits, final
        assertEquals(7, player2.getOutputSize());
    }
}