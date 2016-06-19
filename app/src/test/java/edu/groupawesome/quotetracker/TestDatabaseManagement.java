package edu.groupawesome.quotetracker;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by JL5372 on 6/8/2016.
 *
 * will add better doc later
 */
public class TestDatabaseManagement {

//    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
//    }
//    @Test
//    public void testaddQouteToDB() throws Exception {
//        DatabaseManagement a = new DatabaseManagement();
//        Quote quote = new Quote();
//        boolean returnValue =  a.addQuoteToDB(quote);
//        System.out.println(returnValue);
//        assertTrue(returnValue);
//    }
//
//    @Test
//    public void testretrieveQuoteFromDB(){
//        DatabaseManagement a = new DatabaseManagement();
//        Quote dbQuote = a.retrieveQuoteFromDB(1);
//        System.out.println(dbQuote.getAuthor() + " : " + dbQuote.getID());
//        assertEquals(0, (int)dbQuote.getID());
//    }

    @Test
    public void miscDriverTest(){
        DatabaseManagement t = new DatabaseManagement();
        Quote a = t.retrieveQuoteFromDB(1);
        System.out.println(a.getAuthor());
        System.out.println(a.getID());
        System.out.println(a.getTitle());
        System.out.println(a.getQuoteText());
        System.out.println(a.getTags());

        Tag tag1 = new Tag("Testing Tag1");
        Set<Tag> sTag1 = new HashSet<>();
        sTag1.add(tag1);

        List<Quote> quoteList = t.retrieveQuotesWithTag(tag1);
        List<Quote> quoteListAll = t.retrieveAllQuotesFromDB();

        Tag tag2 = t.retrieveTagFromDB(1);
        List<Tag> tagList = t.retrieveTagsWithKeyword("testkeyword");
        List<Tag> tagListAll = t.retrieveAllTagsFromDB();
        System.out.println(tag2.getTagName());
    }
}