package cardgame;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.Assert.*;

public class CardGameTest {

    @Test
    public void cardDistribution() {
    }

    @Test
    public void creatingPlayersDecks() {

    }

    @Test
    public void testGetPathTrue() {
        CardGame.nofPlayers = 4;
        assertEquals(true, CardGame.getPath("pack.txt"));
    }

    @Test
    public void testGetPathShort() {
        CardGame.nofPlayers = 5;
        assertEquals(false, CardGame.getPath("pack.txt"));
    }

    @Test
    public void testGetPathLong() {
        CardGame.nofPlayers = 2;
        assertEquals(false, CardGame.getPath("pack.txt"));
    }

    @Test
    public void testGetPathNegativeValue() {
        CardGame.nofPlayers = 4;
        assertEquals(false, CardGame.getPath("pack-incorrect-value.txt"));
    }

    @Test
    public void testGetPathIncorrectValue() {
        CardGame.nofPlayers = 4;
        assertEquals(false, CardGame.getPath("pack-incorrect-value.txt"));
    }

    @Test
    public void testGetNoOfPlayersInt() {
        assertEquals(true, CardGame.getNoOfPlayers(4));
        assertEquals(false, CardGame.getNoOfPlayers(-4));
    }
}