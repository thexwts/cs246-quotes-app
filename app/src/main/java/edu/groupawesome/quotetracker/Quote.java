package edu.groupawesome.quotetracker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by reed on 5/27/16.
 */
class Quote {

    // A big hunk of test used for testing
    private static final String longText =
            "Material is the metaphor.\n\n A material metaphor is the unifying theory of a rationalized space and a system of motion. The material is grounded in tactile reality, inspired by the study of paper and ink, yet technologically advanced and open to imagination and magic.\n Surfaces and edges of the material provide visual cues that are grounded in reality. The use of familiar tactile attributes helps users quickly understand affordances. Yet the flexibility of the material creates new affordances that supercede those in the physical world, without breaking the rules of physics.\n The fundamentals of light, surface, and movement are key to conveying how objects move, interact, and exist in space and in relation to each other. Realistic lighting shows seams, divides space, and indicates moving parts.\n";


    // public variables for intents, used for testing
    final static String QUOTE_LIST_INDEX = "edu.teamawesome.cs246.quotes.QUOTE_LIST_INDEX";
    final static int TITLE_LENGTH = 10;  // maybe make this not final so the user can set the length in settings

    // for creating a date and time stamp in generic title
    // set this up once so we don't keep making copies of it all the time **the S is for testing only
    final static private SimpleDateFormat sdf = new SimpleDateFormat("MMM d, y H:m:s:SSS a");

    // Maintain a global list of quotes and their titles
    // This list of quotes is stored where everyone can access it, and any new Quote gets added to it automatically
    // in the constructor. ** When we move to a DB then this will have to die, but we could still probably
    // add 'this' to the DB the same way 'this' is added to this List.
    private static List<Quote> quotesList = new ArrayList<Quote>();
    private static List<String> quoteTitlesList;  // maybe make a custom addQuote that automatically adds the title too... or something

    // FOR TESTING PURPOSES ONLY
//    static {
//        // create a list of 15 "Hello world!" strings for our ListView test
//        for (int i = 0; i < 15; i++) {
//            Quote newQuote = new Quote(longText, null, null, null); //, "Hello " + (i+1));
//
//            // sleep just a little bit so we can see the difference in time in the names
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    // constructors
    public Quote() {
        this(null, null, null, null);
    }

    public Quote(String quote, String title, String author, String tag) {
        // we have to have at least the quote text, the rest is optional
//        assert(quote != null);
        // LONGTEXT IS FOR TESTING ONLY
        this.quote = (quote == null ? longText : quote);
        this.title = (title == null ? generateTitle() : title);
        this.tag = (tag == null ? "" : tag);
        this.author = (author == null ? "" : author);

        // add every new Quote to the list automatically for testing purposes
//        quotesList.add(this);
    }

    private String generateTitle() {
        // get the current time and format it into a string and set it as the generic title
        return sdf.format(new Date().getTime());
    }

    // private variables
    private String title;
    private String quote;
    private String author;
    private Integer ID;

    // fix this, I know it won't be a String
    private String tag;

    // getters and setters
    public String getQuote() {
        return quote;
    }

    public Integer getID() {
        return ID;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getTitle() {
        // make this get the first five words of the quote, not the first so many letters
        if (title == null) {
            title = quote.substring(0,TITLE_LENGTH);
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setID(Integer ID) {this.ID = ID;}

    // the idea was to retrieve from the quotesList without allowing it to be modified, but it didn't work
//    public static List<Quote> getQuotesList() {
//        return quotesList;
//    }

    public static Quote getQuoteAtIndex(int index) {
        return quotesList.get(index);
    }

    public static boolean quotesListContains(Quote quote) {
        return quotesList.contains(quote);
    }

    public static List<String> getQuoteTitlesList() {
        // Make a new list of titles from our complete list.
        // Costly and slow I know, not the Best Way, especially once we have a lot of quotes,
        // but if the title of one of the quotes changes we need its new name.
        quoteTitlesList = new ArrayList<String>();
        for (Quote aQuote : Quote.quotesList) {
            quoteTitlesList.add(aQuote.getTitle());
        }
        return quoteTitlesList;
    }

    public static void addToQuotesList(Quote newQuote) {
        quotesList.add(newQuote);
    }


}
