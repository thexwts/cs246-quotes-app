/**
 * Created by Ethan Stewart on 6/7/2016.
 */

package edu.groupawesome.quotetracker;

import android.text.TextUtils;
import android.util.Log;

import java.util.*;

class Quote {

    private static final String LOG_TAG = "QuoteClass";

    // TODO: Do we really need something like this? This is currently here so we can compile. QuoteDisplayActivity needs it for its intent.
    static final String QUOTE_LIST_INDEX = "edu.groupawesome.quotetracker.QUOTE_LIST_INDEX";

    // TODO: Should we add time fields as well? Date created, last opened, last modified? They could then be sorted in that order on the main screen.
    // MEMBER VARIABLES
    private int mID;
    private String mTitle;
    private String mAuthor;
    private String mQuoteText;
    private Set<Tag> mTagsSet;

    private static List<Quote> sQuotesList;

    // CONSTRUCTORS
    // TODO: When creating a new Quote in QuoteDisplay it is easiest if I can create an empty Quote. That would mean no text and no title though, so what should we do? I think a generic time stamp title would be nice.
    Quote() {
        this(null, null, null, null);
    }

    Quote(String title, String author, String quoteText, Set<Tag> tags) {
        // Initialize the fields
        mAuthor = author;
        mQuoteText = quoteText;

        mTitle = title;
        if (mTitle == null || mTitle.equals("")) {
            mTitle = generateGenericTitle();
        }

        mTagsSet = new HashSet<>();
        setTags(tags);

        // TODO: Insert into database and assign ID to mID

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
    String getAuthor() {
        return mAuthor;
    }

    /**
     * Returns the quote ID
     * @return the quote ID
     */
    int getID() {
        return mID;
    }

    /**
     * Returns the quote text
     * @return the text of the quote
     */
    String getQuoteText() {
        return mQuoteText;
    }

    /**
     * Returns the attached tags
     * @return the set of tags
     */
    Set<Tag> getTags() {
        return Collections.unmodifiableSet(mTagsSet);
    }

    /**
     * Returns the tag set as a string of comma-separated tag names
     * @return the tag set as a string
     */
    String getTagsAsString() {
        String[] tagArray = new String[mTagsSet.size()];
        int i = 0;
        for (Tag tag : mTagsSet) {
            tagArray[i++] = tag.getTagName();
        }

        return TextUtils.join(", ", tagArray);
    }


    //TODO: maybe make this return the generic title if mTitle is empty instead of setting mTitle to the generic title
    // this would make the returned title match the quote text in case the quote text is changed but the title isn't
    // we could then add getRealTitle that would return mTitle directly no matter what, which we could use when editing
    // the Quote so the title will continue to stay blank even after resaving the Quote (since the field will be blank)
    // instead of permanently saving the generic title (which is what we wanted to avoid in the first place)
    /**
     * Returns the title
     * @return the title of the quote
     */
    String getTitle() {
        return mTitle;
    }

    /**
     * Sets the author's name
     * @param author the name of the author
     */
    void setAuthor(String author) {
        mAuthor = author;
    }

    /**
     * Sets the quote's ID
     * @param ID the ID to assign to the quote
     */
    void setID(int ID) {
        mID = ID;
    }

    /**
     * Sets the text of the quote
     * @param quoteText the text of the quote
     */
    void setQuoteText(String quoteText) {
        mQuoteText = quoteText;
    }

    /**
     * Sets the title of the quote
     * @param title the title of the quote
     */
    void setTitle(String title) {
        if ((title != null) && (title.length() != 0)) {
            mTitle = title;
        } else {
            mTitle = generateGenericTitle();
        }
    }

    /**
     * Sets the tag set
     */
    void setTags(Set<Tag> tags) {
        // Nothing to do if tags is null
        if (tags != null) {
            // Ensure mTagsSet isn't null
            if (mTagsSet == null) {
                mTagsSet = new HashSet<Tag>();
            }

            // This is a setter, not an adder, so clear the list before adding the new tags
            mTagsSet.clear();
            mTagsSet.addAll(tags);
        }
    }


    /**
     * Sets the tag set from a String. The String may contain one or many Tag names, separated by a comma and space.
     */
    void setTags(String tags) {
        // if the String we get is empty then we set mTagsSet to nothing
        if (tags.equals("")) {
            mTagsSet.clear();
        } else if (tags != null) {
            // Nothing to do if tags is null
            // TODO: parse input by comma <,> into seperate Tags
            String[] splitTagsNames = tags.split(",\\s+");

            // we need to store a Set of these Tags so we can set the Tags list in the Quote
            // if we simply add the new Tags we won't get rid of the deleted ones
            // setting a whole new Set will clear the old ones too
            Set<Tag> newTagsSet = new LinkedHashSet<Tag>();

            // for every Tag name we split out of the EditText we need to add a Tag to the Set
            for (String tagName : splitTagsNames) {
                // EUREKA! The parsing thing should fix my problem with duplicate Tags
                Log.d(LOG_TAG, "New tag name: " + tagName);
                Log.d(LOG_TAG, "List of current tags before adding new tag: " + newTagsSet.toString());

                // if there isn't already a Tag with that name then make a new one
                newTagsSet.add(Tag.getTagReferenceByName(tagName));

                Log.d(LOG_TAG, "Adding new tag: " + tagName);
                Log.d(LOG_TAG, "List of new tags after adding new tag: " + newTagsSet.toString());
            }

            // give mQuote the new Set of Tags
            this.setTags(newTagsSet);

            Log.d(LOG_TAG, "List of current tags after adding all new tags: " + this.getTagsAsString());

            for (Tag tag:this.getTags()) {
                Log.d(LOG_TAG, "Tags in mQuote (through iteration): " + tag.getTagName());
            }

            for (Tag tag:Tag.getTagsSet()) {
                Log.d(LOG_TAG, "Tags in TagsSet (through iteration): " + tag.getTagName());
            }

        }
    }

    // METHODS

    /**
     * Adds a Tag to the Quote
     * @param tag the Tag to be added
     * @return whether the Tag was successfully added
     */
    boolean addTag(Tag tag) {
        return mTagsSet.add(tag);
    }

    /**
     * Adds a Set of tags to the existing tag set
     * @param tags the tags to be added
     * @return whether the tags were successfully added
     */
    boolean addAllTags(Set<Tag> tags) {
        return mTagsSet.addAll(tags);
    }

    /**
     * Adds an array of tags to the existing tag set
     * @param tags the tags to be added
     * @return whether the tags were successfully added
     */
    boolean addAllTags(Tag[] tags) {
        return mTagsSet.addAll(Arrays.asList(tags));
    }

    /**
     * Indicates whether obj is a Quote object containing the
     * same values for all fields
     * @param obj the object to compare
     * @return whether this and obj contain the same values
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Quote quote = (Quote) obj;

        if (mID != quote.mID) return false;
        if (mTitle != null ? !mTitle.equals(quote.mTitle) : quote.mTitle != null) return false;
        if (mAuthor != null ? !mAuthor.equals(quote.mAuthor) : quote.mAuthor != null) return false;
        if (!mQuoteText.equals(quote.mQuoteText)) return false;
        return !(mTagsSet != null ? !mTagsSet.equals(quote.mTagsSet) : quote.mTagsSet != null);

    }

    /**
     * Returns the first five words of the quote followed by an ellipses, the first five or fewer
     * words of the quote if a newline is encountered, or the entire text of the quote if the quote
     * has fewer than five words
     * @return the generic title
     */
    private String generateGenericTitle() {
        // check for missing text before trying to operate on it
        if (mQuoteText != null && !mQuoteText.equals("")) {
            // Get no more than the first five words of the quote
            String[] spaceSplit = getFirstFiveWords(mQuoteText);

            // Join the five or fewer words back into a String
            String genericTitle = TextUtils.join(" ", spaceSplit);

            // Add an ellipses at the end if the quote had more than five words
            if (mQuoteText.split(" ").length > 5 || mQuoteText.split("\n").length > 1) {
                genericTitle += "...";
            }

            return genericTitle;
        } else {
            return "";
        }
    }

    /**
     * Helper function for Quote::getGenericTitle()
     * Returns an array containing the correct number of words
     * for a generic title from the text parameter
     * @param text the text to get the words from
     * @return an array of words
     */
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

    /**
     * Removes a tag from the tag set
     * @param tag the tag to remove
     * @return whether the tag was successfully removed
     */
    boolean removeTag(Tag tag) {
        if (mTagsSet.contains(tag)) {
            return mTagsSet.remove(tag);
        }

        // If tag wasn't in the tag set to begin with, we're good
        return true;
    }

    // STATIC METHODS

    /**
     * Adds an entire list of Quote objects to the static list of quotes
     * @param quotes the list of Quotes to add
     * @return whether the quotes were added successfully
     */
    static boolean addAllQuotes(List<Quote> quotes) {
        return sQuotesList.addAll(quotes);
    }

    /**
     * Adds an entire array of Quote objects to the static list of quotes
     * @param quotes the array of Quotes to add
     * @return whether the quotes were added successfully
     */
    static boolean addAllQuotes(Quote[] quotes) {
        return sQuotesList.addAll(Arrays.asList(quotes));
    }

    /**
     * Adds a Quote object to the static list of quotes
     * @param quote the Quote object to add
     * @return whether the quote was added successfully
     */
    static boolean addToQuotesList(Quote quote) {
        return sQuotesList.add(quote);
    }

    /**
     * Returns the list of all Quote objects
     * @return the list of quotes
     */
    static List<Quote> getQuotesList() {
        return sQuotesList;
    }

    /**
     * Returns a list of the title of every Quote in the static list
     * @return the list of titles
     */
    static List<String> getQuoteTitlesList() {
        // Add every quote's title to a List
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
    static boolean removeQuote(Quote quote) {
        if (sQuotesList.contains(quote)) {
            return sQuotesList.remove(quote);
        }

        // If the quote wasn't in the list, then we're good
        return true;
    }

    /**
     * Sets the static list of Quotes to the List parameter
     * @param quotes the List to assign to the static list
     * @return whether the list was successfully set
     */
    static boolean setQuotesList(List<Quote> quotes) {
        // This removes all existing quotes, so clear the list before adding the new quotes
        sQuotesList.clear();
        sQuotesList.addAll(quotes);
        return sQuotesList.equals(quotes);
    }

    /**
     * Sets the static list of Quotes to the array of quotes
     * @param quotes the array to assign to the static list
     * @return whether the list was successfully set
     */
    static boolean setQuotesList(Quote[] quotes) {
        // This removes all existing quotes, so clear the list before adding the new quotes
        sQuotesList.clear();
        sQuotesList.addAll(Arrays.asList(quotes));
        return sQuotesList.equals(quotes);
    }

    /**
     * Indicates whether the quote parameter is found in the
     * static list of quotes
     * @param quote the Quote object to look for
     * @return whether the quote is in the list
     */
    static boolean quotesListContains(Quote quote) {
        return sQuotesList.contains(quote);
    }

    /**
     * Returns the quote at the specified index, or null if
     * quoteIndex is not a valid index
     * @param quoteIndex
     * @return a Quote instance, or null
     */
    static Quote getQuoteAtIndex(int quoteIndex) {
        try {
            Quote quote = sQuotesList.get(quoteIndex);
            return quote;
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }

}
