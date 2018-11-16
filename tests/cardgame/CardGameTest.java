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

    @Test
    public void creatingPlayersDecks() {
        CardGame.creatingPlayersDecks(2);

        // checks if there is correct number of players created
        assertEquals(2, CardGame.playersList.size());

        // checks if there is correct number of decks created
        assertEquals(2, CardGame.decksList.size());

        // checks if the right player with a right id was created
        assertEquals(2, CardGame.playersList.get(1).id);

        // checks if the right deck with a right id was created
        assertEquals(1, CardGame.decksList.get(0).id);
    }

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


    @Test
    public void testGetPathTrue() {
        // when everything is fine
        CardGame.nofPlayers = 4;

        assertTrue(CardGame.getPath("pack.txt"));

        // when the pack is too short
        assertFalse(CardGame.getPath("pack-too-short.txt"));

        // when the pack is too long
        assertFalse(CardGame.getPath("pack-too-long.txt"));

        // when the pack contains a negative value
        assertFalse(CardGame.getPath("pack-negative-value.txt"));

        // when the pack contains incorrect value
        assertFalse(CardGame.getPath("pack-incorrect-value.txt"));

        // when the pack doesn't exist
        assertFalse(CardGame.getPath("kcap.txt"));
    }

    @Test
    public void testGetNoOfPlayersInt() {
        // when the number of players
        assertTrue(CardGame.getNoOfPlayers(4));
        assertFalse(CardGame.getNoOfPlayers(-4));
    }
}