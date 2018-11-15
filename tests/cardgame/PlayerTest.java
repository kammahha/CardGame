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
    public void tearDown() throws Exception{
    }

    @Test
    public void getOutputSize() {
        player.output.add("Hello World");
        player.output.add("Hello");
        Assert.assertEquals(2, player.getOutputSize());



    }

    @Test
    public void handToString() {

    }

    @Test
    public void setInitialHand() {

    }

    @Test
    public void drawCard() {

    }

    @Test
    public void discardCard() {

    }

    @Test
    public void mostRounds() {

    }

    @Test
    public void myAction() {

    }

    @Test
    public void checkHand() {
    }

    @Test
    public void whoWonCheck() {
    }
}