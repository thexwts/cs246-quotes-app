/**
 * Created by Ethan Stewart on 6/7/2016.
 */

package edu.groupawesome.quotetracker;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class Quote {

    // TODO: Do we really need something like this? This is currently here so we can compile. QuoteDisplayActivity needs it for its intent.
    public static final String QUOTE_LIST_INDEX = "edu.groupawesome.quotetracker.QUOTE_LIST_INDEX";

    // MEMBER VARIABLES
    private int mID;
    private String mTitle;
    private String mAuthor;
    private String mQuoteText;
//    private List<Tag> mTags;

    private static List<Quote> sQuotesList;

    // CONSTRUCTORS
    public Quote() {
        this(null, null, null);
    }

    // TODO: Add List of Tags to constructor
    // TODO: Add logic to get id from database insertion
    public Quote(String title, String author, String quoteText) {
        // Initialize the fields
        mAuthor = author;
        mQuoteText = quoteText;

        mTitle = title;
        System.out.println(String.format("mTitle = %s -- title = %s", mTitle, title));
        if (mTitle == null || mTitle.equals("")) {
            System.out.println("mTitle was null, preparing to generate generic title");
            mTitle = generateGenericTitle();
        }
        System.out.println("After genericTitle, mTitle = " + mTitle + "\n");

        // Add this new Quote to the static List
        if (sQuotesList == null) {
            sQuotesList = new ArrayList<Quote>();
        }
        sQuotesList.add(this);
    }

    // GETTERS/SETTERS

    /**
     * Returns the author
     * @return the author's name
     */
    public String getAuthor() {
        return mAuthor;
    }

    /**
     * Returns the quote text
     * @return the text of the quote
     */
    public String getQuoteText() {
        return mQuoteText;
    }

//    public List<Tag> getTags() {
//        return mTags;
//    }

    /**
     * Returns the title
     * @return the title of the quote
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Sets the author's name
     * @param author the name of the author
     */
    public void setAuthor(String author) {
        mAuthor = author;
    }

    /**
     * Sets the text of the quote
     * @param quoteText the text of the quote
     */
    public void setQuoteText(String quoteText) {
        mQuoteText = quoteText;
    }

    // METHODS

//    /**
//     * Adds a Tag to the Quote
//     * @param tag the Tag to be added
//     * @return whether the Tag was successfully added
//     */
//    public boolean addTag(Tag tag) {
//        return true;
//    }

//    public boolean addAllTags(List<Tag> tags) {
//        return true;
//    }

//    public boolean addAllTags(Tag[] tags) {
//        return true;
//    }

    /**
     * Returns the first five words of the quote followed by an ellipses, the first five or fewer
     * words of the quote if a newline is encountered, or the entire text of the quote if the quote
     * has fewer than five words
     * @return the generic title
     */
    private String generateGenericTitle() {
        // Get no more than the first five words of the quote
        String[] spaceSplit = getFirstFiveWords(mQuoteText);

        // Join the five or fewer words back into a String
        String genericTitle = TextUtils.join(" ", spaceSplit);

        // Add an ellipses at the end if the quote had more than five words
        if (mQuoteText.split(" ").length > 5 || mQuoteText.split("\n").length > 1) {
            genericTitle += "...";
        }

        return genericTitle;
    }

    private String[] getFirstFiveWords(String text) {
        String[] firstFive = text.split(" ", 5);

        String[] newlineSplit = text.split("\n");
        if (newlineSplit.length > 1) {
            firstFive = newlineSplit[0].split(" ", 5);
        }

        if (firstFive.length < 5) {
            return firstFive;
        }

        String[] secondarySplit = firstFive[4].split(" ");
        firstFive[4] = secondarySplit[0];

        return firstFive;
    }

    // FIXME: Needs specification for which tag to remove?
    public boolean removeTag() {
        return true;
    }

    // STATIC METHODS

    /**
     * Adds an entire list of Quote objects to the static list of quotes
     * @param quotes the list of Quotes to add
     * @return whether the quotes were added successfully
     */
    public static boolean addAllQuotes(List<Quote> quotes) {
        return true;
    }

    /**
     * Adds an entire array of Quote objects to the static list of quotes
     * @param quotes the array of Quotes to add
     * @return whether the quotes were added successfully
     */
    public static boolean addAllQuotes(Quote[] quotes) {
        return true;
    }

    /**
     * Adds a Quote object to the static list of quotes
     * @param quote the Quote object to add
     * @return whether the quote was added successfully
     */
    public static boolean addQuote(Quote quote) {
        return true;
    }

    /**
     * Returns the list of all Quote objects
     * @return the list of quotes
     */
    public static List<Quote> getQuotesList() {
        return sQuotesList;
    }

    /**
     * Returns a list of the title of every Quote in the static list
     * @return the list of titles
     */
    public static List<String> getQuoteTitlesList() {
        List<String> titles = new ArrayList<>();

        for (Quote quote : sQuotesList) {
            titles.add(quote.getTitle());
        }

        return titles;
    }

    /**
     * Removes the specified Quote object to remove
     * @param quote the Quote object to remove
     * @return whether the quote was successfully removed
     */
    public static boolean removeQuote(Quote quote) {
        return true;
    }

    /**
     * Sets the static list of Quotes to the List parameter
     * @param quotes the List to assign to the static list
     * @return whether the list was successfully set
     */
    public static boolean setQuotesList(List<Quote> quotes) {
        return true;
    }

    /**
     * Sets the static list of Quotes to the array of quotes
     * @param quotes the array to assign to the static list
     * @return whether the list was successfully set
     */
    public static boolean setQuotesList(Quote[] quotes) {
        return true;
    }

    public void setID(Integer ID) {
        mID = ID;
    }

    public int getmID() {
        return mID;
    }


    // TODO: Fix this
    public String getTag() {
        return "Tag";
    }


    public void setTitle(String title) {
        mTitle = title;
    }

    // TODO: Fix this
    public void setTag(String tag) {
        //
    }

    // TODO: Return the actual result
    public static boolean quotesListContains(Quote mQuote) {
        return false;
    }

    // TODO
    public static void addToQuotesList(Quote mQuote) {
    }

    // TODO
    public static Quote getQuoteAtIndex(int quoteIndex) {
        return sQuotesList.get(quoteIndex);
    }
}
