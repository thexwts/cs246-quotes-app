package edu.groupawesome.quotetracker;


/**
 * Created by JL5372 on 6/8/2016.
 */
public class DatabaseManagement {

    public boolean addQuoteToDB(Quote quote) {

        return false;
    }

    public Quote retrieveQuoteFromDB(Integer QuoteID){

        Quote returnQuote = new Quote();
        returnQuote.setID(QuoteID);
        returnQuote.setAuthor("Aaron");
        return returnQuote;
    }
}
