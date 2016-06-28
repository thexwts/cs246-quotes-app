/**
 * Created by Ethan Stewart on 6/8/2016.
 */

package edu.groupawesome.quotetracker;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class QuoteTest {

    @Test
    public void testGenericTitle() throws Exception {
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

        Quote quoteHuge = new Quote(title, author, quoteTextHuge, null);
        Quote quoteLong = new Quote(title, author, quoteTextLong, null);
        Quote quoteSix = new Quote(title, author, quoteTextSixWords, null);
        Quote quoteFive = new Quote(title, author, quoteTextFiveWords, null);
        Quote quoteShort = new Quote(title, author, quoteTextShort, null);
        Quote quoteNewLine = new Quote(title, "Ethan Stewart", quoteTextNewLine, null);

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
    public void testGetTitle() throws Exception {
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
            quotes[i] = new Quote(titles[i], author, quoteText, null);
        }

        assertEquals("Hamlet", quotes[0].getTitle());
        assertEquals("Julius Caesar", quotes[1].getTitle());
        assertEquals("Romeo and Juliet", quotes[2].getTitle());
        assertEquals("Macbeth", quotes[3].getTitle());
        assertEquals("A Midsummer Night's Dream", quotes[4].getTitle());
        assertEquals("Lorem ipsum dolor sit amet,...", quotes[5].getTitle());
    }

    @Test
    public void test_getQuoteTitlesList_return() throws Exception {
        // since we don't have a clear function for the QuotesList (on purpose) we need to 'clear' it by
        // setting it to an empty List so the next test function will have a clear Quotes list
        Quote.setQuotesList(new ArrayList<Quote>());
        Quote.generateGenericQuotes();

        // we want to make sure if the List returned by getQuoteTitlesList is unmodifiable
        List<String> titles = Quote.getQuoteTitlesList();

        // we want to make sure the Strings in the List returned by getQuoteTitlesList can't be changed
        boolean failed = false;
        try {
            // I want this to fail
            titles.set(0, "Test");
        } catch(UnsupportedOperationException ex) {
            failed = true;
        }
        assertTrue(failed);

        // we want to make sure if the List returned by getQuoteTitlesList can't be added to
        failed = false;
        try {
            // I want this to fail
            titles.add("Test");
        } catch(UnsupportedOperationException ex) {
            failed = true;
        }
        assertTrue(failed);

        // we want to make sure the List returned by getQuoteTitlesList can't be cleared
        failed = false;
        try {
            // I want this to fail
            titles.clear();
        } catch(UnsupportedOperationException ex) {
            failed = true;
        }
        assertTrue(failed);

        // we want to make sure the List returned by getQuoteTitlesList can't have contents removed
        failed = false;
        try {
            // I want this to fail
            titles.remove(0);
        } catch(UnsupportedOperationException ex) {
            failed = true;
        }
        assertTrue(failed);


        // since we don't have a clear function for the QuotesList (on purpose) we need to 'clear' it by
        // setting it to an empty List so the next test function will have a clear Quotes list
        Quote.setQuotesList(new ArrayList<Quote>());
    }
}