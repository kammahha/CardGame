package cardgame;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CardTest {

    Card card;

    @Before
    public void setUp() {
        card = new Card(1);
    }

    @Test
    public void testCardValue() {
        Assert.assertEquals(1, card.value);
    }
}