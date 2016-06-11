/**
 * Created by Ethan Stewart on 6/8/2016.
 */

package edu.groupawesome.quotetracker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class QuoteTest {
    @Test
    public void testGenericTitle() {
        String title = null;
        String author = "William Shakespeare";
        String quoteTextHuge = "Cowards die many times before their deaths; The valiant never taste " +
                "of death but once.\nOf all the wonders that I yet have heard, it seems to me most " +
                "strange that men should fear;\nSeeing that death, a necessary end, will come when " +
                "it will come";
        String quoteTextLong = "To thine own self be true, and it must follow, as the night " +
                "the day, thou canst not then be false to any man.";
        String quoteTextSixWords = "I have not slept one wink.";
        String quoteTextFiveWords = "Nothing will come of nothing.";
        String quoteTextShort = "Et tu, Brute!";
        String quoteTextNewLine = "Haikus are tricky\nI'm not very good at them\nRefrigerator";

        Quote quoteHuge = new Quote(title, author, quoteTextHuge);
        Quote quoteLong = new Quote(title, author, quoteTextLong);
        Quote quoteSix = new Quote(title, author, quoteTextSixWords);
        Quote quoteFive = new Quote(title, author, quoteTextFiveWords);
        Quote quoteShort = new Quote(title, author, quoteTextShort);
        Quote quoteNewLine = new Quote(title, "Ethan Stewart", quoteTextNewLine);

        assertEquals("Cowards die many times before...", quoteHuge.getTitle());
        assertEquals("To thine own self be...", quoteLong.getTitle());
        assertEquals("I have not slept one...", quoteSix.getTitle());
        assertEquals("Nothing will come of nothing.", quoteFive.getTitle());
        assertEquals("Et tu, Brute!", quoteShort.getTitle());
        assertEquals("Haikus are tricky...", quoteNewLine.getTitle());

//        assertEquals("null...", quoteHuge.getTitle());
//        assertEquals("null...", quoteLong.getTitle());
//        assertEquals("null...", quoteSix.getTitle());
//        assertEquals("null", quoteFive.getTitle());
//        assertEquals("null", quoteShort.getTitle());
//        assertEquals("null...", quoteNewLine.getTitle());
    }

    @Test
    public void testGetTitle() {
        String[] titles = { "Hamlet",
                            "Julius Caesar",
                            "Romeo and Juliet",
                            "Macbeth",
                            "A Midsummer Night's Dream",
                            null };
        String author = "William Shakespeare";
        String quoteText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
                "eiusmod tempor incididunt ut labore et dolore magna aliqua.";
        Quote[] quotes = new Quote[titles.length];

        for (int i = 0; i < titles.length; i++) {
            quotes[i] = new Quote(titles[i], author, quoteText);
        }

        assertEquals("Hamlet", quotes[0].getTitle());
        assertEquals("Julius Caesar", quotes[1].getTitle());
        assertEquals("Romeo and Juliet", quotes[2].getTitle());
        assertEquals("Macbeth", quotes[3].getTitle());
        assertEquals("A Midsummer Night's Dream", quotes[4].getTitle());
        assertEquals("Lorem ipsum dolor sit amet,...", quotes[5].getTitle());
    }
}