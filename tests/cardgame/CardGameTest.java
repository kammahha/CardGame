package cardgame;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

public class CardGameTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void cardDistribution() {
    }

    @Test
    public void creatingPlayersDecks() {
    }

    @Test
    public void testGetPath() {
        CardGame.nofPlayers = 4;
        String path = "pack.txt";
        InputStream in = new ByteArrayInputStream(path.getBytes());
        System.setIn(in);
        Assert.assertEquals(true, CardGame.getPath());
    }
}