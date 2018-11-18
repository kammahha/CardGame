package cardgame;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

public class CardGameTest {

    @Before
    public void setUp() {
        CardGame.nofPlayers = 4;
    }

    @After
    public void tearDown() {
        CardGame.creatingPlayersDecks(2);
        CardGame.decksList.clear();
        CardGame.playersList.clear();
        CardGame.cards.clear();
        CardGame.nofPlayers = 0;
    }

    @Test
    public void testCreatingPlayersCorrectNo() {

        // checks if there is correct number of players created
        CardGame.creatingPlayersDecks(2);
        assertEquals(2, CardGame.playersList.size());
    }
    @Test
    public void testCreatingDecksCorrectNo() {

        // checks if there is correct number of decks created
        CardGame.creatingPlayersDecks(2);
        assertEquals(2, CardGame.decksList.size());
    }
    @Test
    public void testCreatingPlayersCorrectIds() {

        // checks if the right player with a right id was created
        CardGame.creatingPlayersDecks(2);
        assertEquals(2, CardGame.playersList.get(1).id);
    }
    @Test
    public void testCreatingDecksCorrectIds() {

        // checks if the right deck with a right id was created
        CardGame.creatingPlayersDecks(2);
        assertEquals(1, CardGame.decksList.get(0).id);
    }

    /**
     *  Adds card values to a cards ArrayList to check if the initial hand when cards
     *  are distributed is what it should be for both players and decks
     */
    @Test
    public void testCardDistribution() {
        Card card1 = new Card(1);
        Card card2 = new Card(2);
        ArrayList<Card> cards = new ArrayList<>();
        CardGame.creatingPlayersDecks(1);

        cards.add(card1);
        cards.add(card2);
        cards.add(card1);
        cards.add(card2);
        cards.add(card2);
        cards.add(card1);
        cards.add(card2);
        cards.add(card1);

        CardGame.cardDistribution(cards, CardGame.playersList, CardGame.decksList, 1);

        assertEquals(" 1 2 1 2", CardGame.playersList.get(0).handToString());
        assertEquals("deck 1 contents: 2 1 2 1", CardGame.decksList.get(0).printHand());
    }

    /**
     *
     */

    @Test
    public void testGetPathTrue() {
        assertTrue(CardGame.getPath("pack.txt"));
    }

    @Test
    public void testGetPathTooShort() {
        // when the pack is too short
        assertFalse(CardGame.getPath("pack-too-short.txt"));
    }

    @Test
    public void testGetPathTooLong() {
        // when the pack is too long
        assertFalse(CardGame.getPath("pack-too-long.txt"));
    }

    @Test
    public void testGetPathNegVal() {
        // when the pack contains a negative value
        assertFalse(CardGame.getPath("pack-negative-value.txt"));
    }

    @Test
    public void testGetPathIncorrectVal() {
        // when the pack contains incorrect value
        assertFalse(CardGame.getPath("pack-incorrect-value.txt"));
    }

    @Test
    public void testGetPathNull() {
        // when the pack doesn't exist
        assertFalse(CardGame.getPath("kcap.txt"));
    }

    @Test
    public void testGetNoOfPlayersCorrect() {
        // when the number of players
        assertTrue(CardGame.getNoOfPlayers(4));
    }

    @Test
    public void testGetNoOfPlayersIncorrect() {
        // when the number of players
        assertFalse(CardGame.getNoOfPlayers(-4));
    }

}