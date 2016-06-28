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
    private String mFullText;
    private String mShortText;
    private Set<Tag> mTagsSet;

    private static List<Quote> sQuotesList = new ArrayList<Quote>();

    // CONSTRUCTORS
    // TODO: When creating a new Quote in QuoteDisplay it is easiest if I can create an empty Quote. That would mean no text and no title though, so what should we do? I think a generic time stamp title would be nice.
    Quote() {
        this(null, null, null, null);
    }

    Quote(String title, String author, String quoteText, Set<Tag> tags) {
        Log.i(LOG_TAG, "Starting Quote constructor");

        // Initialize the Author field
        Log.d(LOG_TAG, "New Quote author parameter: " + author);
        mAuthor = author;
        Log.i(LOG_TAG, "New Quote author: " + mAuthor);

        // Initialize the Text fields - setQuoteText sets full and short text
        Log.d(LOG_TAG, "New Quote text parameter: " + quoteText);
        setQuoteText(quoteText);
        Log.i(LOG_TAG, "New Quote text (full): " + mFullText);
        Log.i(LOG_TAG, "New Quote text (short): " + mFullText);

        // Initialize the Title field
        Log.d(LOG_TAG, "New Quote title parameter: " + title);
        if (title == null || title.equals("")) {
            mTitle = generateGenericTitle();
        } else {
            mTitle = title;
        }
        Log.i(LOG_TAG, "New Quote title: " + mTitle);

        // Initialize the Tags field
        Log.d(LOG_TAG, "New Quote tags parameter: " + (tags != null ? tags.toString() : null));
        mTagsSet = new HashSet<>();
        setTags(tags);
        Log.i(LOG_TAG, "New Quote tags: " + this.getTagsAsString());

        // TODO: Insert into database and assign ID to mID

        Log.i(LOG_TAG, "Adding New Quote to static Quotes List");
        sQuotesList.add(this);

        Log.i(LOG_TAG, "Ending Quote constructor");
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
    String getFullText() {
        return mFullText;
    }

    // TODO: 6/28/16 Test this
    /**
     * Returns the first 10 words of the quote text
     * @return the first 10 words of the text of the quote
     */
    String getShortText() {
        return mShortText;
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
        mFullText = quoteText;
        // todo: maybe make the length user configurable as a stretch goal (using a preferences file)?
        mShortText = getFirstWordsAsString(mFullText, 10);
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
        if (!mFullText.equals(quote.mFullText)) return false;
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
        if (mFullText != null && !mFullText.equals("")) {
            // Get no more than the first five words of the quote
            return getFirstWordsAsString(mFullText, 5);
        } else {
            return "";
        }
    }

    // TODO: 6/28/16 Test this
    /**
     * Helper function for Quote::getGenericTitle() and Quote::setQuoteText()
     * Returns an array containing the first requested number of words
     * from the text parameter. Adds ellipses (...) if text is longer than length.
     * @param text the text to get the words from
     * @param length how many words to return
     * @return an array of the first words in text
     */
    private String[] getFirstWordsAsArray(String text, int length) {
        Log.i(LOG_TAG, "Starting getFirstWordsAsArray");
        // if there is a newline in text then we only want words from the first line
        String[] newlineSplitArray = text.split("\n");
        // store an array of the split first line of the text here so we know how many words there are
        String[] firstLineSplitArray = newlineSplitArray[0].split(" ");
        // copy over the first length number of words to return
        String[] firstWordsArray;
        if (firstLineSplitArray.length >= length) {
            // if the first line is longer than the desired length we can copy up to length
            firstWordsArray = Arrays.copyOf(firstLineSplitArray, length);
        } else {
            // if the first line is too short than we only want to copy what it has so we don't get NULL values
            firstWordsArray = Arrays.copyOf(firstLineSplitArray, firstLineSplitArray.length);
        }

        Log.d(LOG_TAG, "newlineSplitArray: " + TextUtils.join(" \\n ", newlineSplitArray));
        Log.d(LOG_TAG, "newlineSplitArray length: " + newlineSplitArray.length);
        Log.d(LOG_TAG, "firstLineSplitArray: " + TextUtils.join(" ", firstLineSplitArray));
        Log.d(LOG_TAG, "firstLineSplitArray: " + firstLineSplitArray.length);
        Log.d(LOG_TAG, "firstWordsArray: " + TextUtils.join(" ", firstWordsArray));
        Log.d(LOG_TAG, "firstWordsArray length: " + firstWordsArray.length);
        assert(firstWordsArray.length <= length);

        // Add an ellipses at the end if the quote had more than the requested length or more than one line
        if (firstLineSplitArray.length > length || newlineSplitArray.length > 1) {
            // create a new longer array to hold the ellipses
            String[] arrayWithEllipses = new String[firstWordsArray.length + 1];
            // copy over the first length number of words
            int i;
            for (i = 0; i < firstWordsArray.length; i++) {
                arrayWithEllipses[i] = firstWordsArray[i];
                Log.d(LOG_TAG, "arrayWithEllipses: " + TextUtils.join(" ", arrayWithEllipses));
            }
            // add the ellipses at the end of the array
            arrayWithEllipses[i] = "...";
            Log.d(LOG_TAG, "arrayWithEllipses final: " + TextUtils.join(" ", arrayWithEllipses));

            // and finally point our firstWordsArray at the new array with the ellipses
            firstWordsArray = arrayWithEllipses;

            Log.d(LOG_TAG, "firstWordsArray with ellipses: " + TextUtils.join(" ", firstWordsArray));
        }

        Log.i(LOG_TAG, "Ending getFirstWordsAsArray");
//        if (firstWordsArray.length < length) {
            return firstWordsArray;
//        }

//        // TODO: Ask Ethan when this might be necessary. It was only giving me NullPointerExceptions after refactoring.
//        Log.d(LOG_TAG, "firstWordsArray before secondary split: " + TextUtils.join(" ", firstWordsArray));
//        String[] secondarySplit = firstWordsArray[length - 1].split(" ");
//        Log.d(LOG_TAG, "secondarySplit: " + TextUtils.join(" ", secondarySplit));
//        firstWordsArray[length - 1] = secondarySplit[0];
//        Log.d(LOG_TAG, "firstWordsArray after secondary split: " + TextUtils.join(" ", firstWordsArray));
//
//        return firstWordsArray;
    }

    // TODO: 6/28/16 Test this
    /**
     * Helper function for Quote::getGenericTitle() and Quote::setQuoteText()
     * Returns a String containing the first requested number of words, separated by spaces,
     * from the text parameter. Adds ellipses (...) if text is longer than length.
     * @param text the text to get the words from
     * @param length how many words to return
     * @return a single String of the first words in text
     */
    private String getFirstWordsAsString(String text, int length) {
        Log.i(LOG_TAG, "Starting getFirstWordsAsString");

        Log.d(LOG_TAG, "text: " + text);
        Log.d(LOG_TAG, "length: " + length);

        // call the other function so we don't duplicate code
        String[] firstWordsArray = getFirstWordsAsArray(text, length);

        Log.d(LOG_TAG, "firstWordsArray: " + TextUtils.join(" ", firstWordsArray));

        // Join the first length number or fewer words back into a String
        String firstWordsString = TextUtils.join(" ", firstWordsArray);

        Log.d(LOG_TAG, "firstWordsString: " + firstWordsString);

        Log.i(LOG_TAG, "Ending getFirstWordsAsString");
        return firstWordsString;
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
        return Collections.unmodifiableList(sQuotesList);
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

        return Collections.unmodifiableList(titles);
    }

    /**
     * Returns a list of the text of every Quote in the static list
     * @return the list of texts
     */
    static List<String> getQuoteFullTextsList() {
        // Add every quote's text to a List
        List<String> texts = new ArrayList<>();

        for (Quote quote : sQuotesList) {
            texts.add(quote.getFullText());
        }

        return Collections.unmodifiableList(texts);
    }

    /**
     * Returns a list of the short version of the text of every Quote in the static list
     * @return the list of texts
     */
    static List<String> getQuoteShortTextsList() {
        // Add every quote's text to a List
        List<String> shortTexts = new ArrayList<>();

        for (Quote quote : sQuotesList) {
            shortTexts.add(quote.getShortText());
        }

        return Collections.unmodifiableList(shortTexts);
    }

    /**
     * Returns a list of the author of every Quote in the static list
     * @return the list of authors
     */
    static List<String> getQuoteAuthorsList() {
        // Add every quote's author to a List
        List<String> authors = new ArrayList<>();

        for (Quote quote : sQuotesList) {
            authors.add(quote.getAuthor());
        }

        return Collections.unmodifiableList(authors);
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
        assert(quotes != null);
        assert(sQuotesList != null);

        // clear the current Set if it isn't empty already
        if (!sQuotesList.isEmpty()) { sQuotesList.clear(); }

        return sQuotesList.addAll(quotes);
    }

    /**
     * Sets the static list of Quotes to the array of quotes
     * @param quotes the array to assign to the static list
     * @return whether the list was successfully set
     */
    static boolean setQuotesList(Quote[] quotes) {
        // This removes all existing quotes, so clear the list before adding the new quotes
        assert(quotes != null);
        assert(sQuotesList != null);

        // clear the current Set if it isn't empty already
        if (!sQuotesList.isEmpty()) { sQuotesList.clear(); }

        return Collections.addAll(sQuotesList, quotes);
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



    // FIXME: for testing purposes only, move to QuoteTest before launching product
    static void generateGenericQuotes() {
        // TODO: Ask Ethan which title goes to which quote
        String[] titles = { "Hamlet",
                "Romeo and Juliet",
                "A Midsummer Night's Dream",
                "Macbeth",
                "Julius Caesar",
                null };

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
        String quoteTextShortNoTitle = "I'm hungry!";
        String quoteTextFiveWordsNoTitle= "That's the best you've got?";
        String quoteTextSixWordsNoTitle = "It's hard to think when hungry.";

        new Quote(titles[0], author, quoteTextHuge, null);
        new Quote(titles[1], author, quoteTextLong, null);
        new Quote(titles[2], author, quoteTextSixWords, null);
        new Quote(titles[3], author, quoteTextFiveWords, null);
        new Quote(titles[4], author, quoteTextShort, null);
        new Quote(titles[5], "Ethan Stewart", quoteTextNewLine, null);
        new Quote(titles[5], "Reed Harston", quoteTextShortNoTitle, null);
        new Quote(titles[5], "Reed Harston", quoteTextFiveWordsNoTitle, null);
        new Quote(titles[5], "Reed Harston", quoteTextSixWordsNoTitle, null);
    }

}
