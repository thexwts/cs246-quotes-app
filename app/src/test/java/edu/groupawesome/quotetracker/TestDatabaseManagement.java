package edu.groupawesome.quotetracker;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Created by JL5372 on 6/8/2016.
 */
public class TestDatabaseManagement {

//    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
//    }
    @Test
    public void testaddQouteToDB() throws Exception {
        DatabaseManagement a = new DatabaseManagement();
        Quote quote = new Quote();
        boolean returnValue =  a.addQuoteToDB(quote);
        System.out.println(returnValue);
        assertTrue(returnValue);
    }

    @Test
    public void testretrieveQuoteFromDB(){
        DatabaseManagement a = new DatabaseManagement();
        Quote dbQuote = a.retrieveQuoteFromDB(1);
        System.out.println(dbQuote.getAuthor() + " : " + dbQuote.getID());
        assertEquals(0, (int)dbQuote.getID());
    }
}